package com.cgg.twdinspection.gcc.ui.gcc;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.cgg.twdinspection.BuildConfig;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityGccPhotoCaptureBinding;
import com.cgg.twdinspection.gcc.interfaces.GCCOfflineInterface;
import com.cgg.twdinspection.gcc.interfaces.GCCSubmitInterface;
import com.cgg.twdinspection.gcc.room.repository.GCCOfflineRepository;
import com.cgg.twdinspection.gcc.source.inspections.InspectionSubmitResponse;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.cgg.twdinspection.gcc.source.stock.StockSubmitRequest;
import com.cgg.twdinspection.gcc.source.stock.SubmitReqCommodities;
import com.cgg.twdinspection.gcc.source.submit.GCCPhotoSubmitResponse;
import com.cgg.twdinspection.gcc.source.submit.GCCSubmitRequest;
import com.cgg.twdinspection.gcc.source.submit.GCCSubmitResponse;
import com.cgg.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.cgg.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.cgg.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.cgg.twdinspection.gcc.source.suppliers.punit.PUnits;
import com.cgg.twdinspection.gcc.viewmodel.GCCOfflineViewModel;
import com.cgg.twdinspection.gcc.viewmodel.GCCPhotoCustomViewModel;
import com.cgg.twdinspection.gcc.viewmodel.GCCPhotoViewModel;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class GCCPhotoActivity extends LocBaseActivity implements GCCSubmitInterface, ErrorHandlerInterface, GCCOfflineInterface {

    ActivityGccPhotoCaptureBinding binding;
    String PIC_NAME, PIC_TYPE;
    public Uri fileUri;
    Bitmap bm;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    int flag_entrance = 0, flag_ceiling = 0, flag_floor = 0, flag_stock_arrang1 = 0, flag_stock_arrang2 = 0, flag_machinary = 0, flag_repair = 0, flag_pUnits = 0;
    File file_repair, file_entrance, file_ceiling, file_floor, file_stock_arrang1, file_stock_arrang2, file_machinary;
    String FilePath, repairPath;
    private String officerID, divId, divName, socId, socName, inchName, suppType, suppId, godName;
    public static final String IMAGE_DIRECTORY_NAME = "GCC_IMAGES";
    private CustomProgressDialog customProgressDialog;
    SharedPreferences sharedPreferences;
    GCCPhotoViewModel viewModel;
    InspectionSubmitResponse inspectionSubmitResponse;
    StockDetailsResponse stockDetailsResponse;
    StockSubmitRequest stockSubmitRequest;
    private String randomNum;
    private String type;
    File mediaStorageDir;
    private boolean flag;
    private GCCOfflineViewModel gccOfflineViewModel;
    private GCCOfflineRepository gccOfflineRepository;
    private GCCSubmitRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gcc_photo_capture);
        if (getIntent() != null ) {
            binding.header.headerTitle.setText(getIntent().getStringExtra(AppConstants.TITLE));
            if(Objects.requireNonNull(getIntent().getStringExtra(AppConstants.TITLE)).contains("DR GODOWN")){
                type = AppConstants.OFFLINE_DR_GODOWN;
            }else  if(Objects.requireNonNull(getIntent().getStringExtra(AppConstants.TITLE)).contains("DEPOT")){
                type = AppConstants.OFFLINE_DR_DEPOT;
            }else  if(Objects.requireNonNull(getIntent().getStringExtra(AppConstants.TITLE)).contains("MFP")){
                type = AppConstants.OFFLINE_MFP;
            }else  if(Objects.requireNonNull(getIntent().getStringExtra(AppConstants.TITLE)).contains("Processing")){
                type = AppConstants.OFFLINE_P_UNIT;
            }else  if(Objects.requireNonNull(getIntent().getStringExtra(AppConstants.TITLE)).contains("PETROL")){
                type = AppConstants.OFFLINE_PETROL;
            }else  if(Objects.requireNonNull(getIntent().getStringExtra(AppConstants.TITLE)).contains("LPG")){
                type = AppConstants.OFFLINE_LPG;
            }
        } else {
            binding.header.headerTitle.setText(getString(R.string.upload_photos));
        }
        binding.header.ivHome.setVisibility(View.GONE);
        binding.btnLayout.btnNext.setText(getString(R.string.submit));
        customProgressDialog = new CustomProgressDialog(this);
        gccOfflineViewModel = new GCCOfflineViewModel(getApplication());
        gccOfflineRepository = new GCCOfflineRepository(getApplication());
        viewModel = ViewModelProviders.of(this,
                new GCCPhotoCustomViewModel(this)).get(GCCPhotoViewModel.class);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            repairPath = sharedPreferences.getString(AppConstants.repairsPath, "");
            officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
            randomNum = sharedPreferences.getString(AppConstants.randomNum, "");

            if (!TextUtils.isEmpty(repairPath)) {
                file_repair = new File(repairPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String inspectionData = sharedPreferences.getString(AppConstants.InspectionDetails, "");
        Gson gson = new Gson();
        inspectionSubmitResponse = gson.fromJson(inspectionData, InspectionSubmitResponse.class);
        String stockDetails = sharedPreferences.getString(AppConstants.finalStockData, "");
        stockDetailsResponse = gson.fromJson(stockDetails, StockDetailsResponse.class);
        stockSubmitRequest = new StockSubmitRequest();


        if (inspectionSubmitResponse.getProcessingUnit() != null) {
            binding.machineryCV.setVisibility(View.VISIBLE);
            flag_pUnits = 1;
            String pUnitData = sharedPreferences.getString(AppConstants.P_UNIT_DATA, "");
            PUnits pUnits = gson.fromJson(pUnitData, PUnits.class);
            divId = pUnits.getDivisionId();
            divName = pUnits.getDivisionName();
            socId = pUnits.getSocietyId();
            socName = pUnits.getSocietyName();
            inchName = pUnits.getIncharge();
            suppType = getString(R.string.p_unit_req);
            suppId = pUnits.getGodownId();
            godName = pUnits.getGodownName();
        }
        if (inspectionSubmitResponse.getDrDepot() != null) {
            String depotData = sharedPreferences.getString(AppConstants.DR_DEPOT_DATA, "");
            DRDepots drDepot = gson.fromJson(depotData, DRDepots.class);
            divId = drDepot.getDivisionId();
            divName = drDepot.getDivisionName();
            socId = drDepot.getSocietyId();
            socName = drDepot.getSocietyName();
            inchName = drDepot.getIncharge();
            suppType = getString(R.string.dr_depot_req);
            suppId = drDepot.getGodownId();
            godName = drDepot.getGodownName();
        }
        if (inspectionSubmitResponse.getDrGodown() != null) {
            String godownData = sharedPreferences.getString(AppConstants.DR_GODOWN_DATA, "");
            DrGodowns drGodowns = gson.fromJson(godownData, DrGodowns.class);
            divId = drGodowns.getDivisionId();
            divName = drGodowns.getDivisionName();
            socId = drGodowns.getSocietyId();
            socName = drGodowns.getSocietyName();
            inchName = drGodowns.getIncharge();
            suppType = getString(R.string.dr_godown_req);
            suppId = drGodowns.getGodownId();
            godName = drGodowns.getGodownName();
        }
        if (inspectionSubmitResponse.getMfpGodowns() != null) {
            String mfpGodown = sharedPreferences.getString(AppConstants.MFP_DEPOT_DATA, "");
            MFPGoDowns mfpGoDowns = gson.fromJson(mfpGodown, MFPGoDowns.class);
            divId = mfpGoDowns.getDivisionId();
            divName = mfpGoDowns.getDivisionName();
            socId = mfpGoDowns.getSocietyId();
            socName = mfpGoDowns.getSocietyName();
            inchName = mfpGoDowns.getIncharge();
            suppType = getString(R.string.mfp_godown_req);
            suppId = mfpGoDowns.getGodownId();
            godName = mfpGoDowns.getGodownName();
        }

        LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(
                divId, socId, suppId);

        drGodownLiveData.observe(GCCPhotoActivity.this, new Observer<GccOfflineEntity>() {
            @Override
            public void onChanged(GccOfflineEntity GCCOfflineEntity) {
                if (GCCOfflineEntity != null) {
                    flag = true;
                    binding.btnLayout.btnNext.setText(getString(R.string.save));
                } else {
                    flag = false;
                    binding.btnLayout.btnNext.setText(getString(R.string.submit));
                }
            }
        });

        binding.ivEntrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.ENTRANCE;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.FLOOR;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivCeiling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.CEILING;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivStockArrng1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.STOCK_ARRANG1;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivStockArrng2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.STOCK_ARRANG2;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivMachinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.MACHINARY;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    customSaveAlert(GCCPhotoActivity.this, getString(R.string.app_name));
                }
            }
        });
    }

    public void customSaveAlert(Activity activity, String title) {
        try {
            String msg = "";
            if (flag) {
                msg = getString(R.string.do_you_want_save);
            } else {
                msg = getString(R.string.do_you_want);
            }
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_confirmation);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button btDialogNo = dialog.findViewById(R.id.btDialogNo);
                btDialogNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        callDataSubmit();
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    private void callDataSubmit() {
        request = new GCCSubmitRequest();
        request.setInspectionFindings(inspectionSubmitResponse);
        request.setOfficerId(officerID);
        request.setDivisionId(divId);
        request.setDivisionName(divName);
        request.setSocietyId(socId);
        request.setSocietyName(socName);
        request.setInchargeName(inchName);
        request.setSupplierType(suppType);
        request.setInspectionTime(Utils.getCurrentDateTime());
        request.setGodownId(suppId);
        request.setGodown_name(godName);
        request.setDeviceId(Utils.getDeviceID(this));
        request.setVersionNo(Utils.getVersionName(this));
        request.setPhoto_key_id(randomNum);
        setStockDetailsSubmitRequest();
        request.setStockDetails(stockSubmitRequest);


        if (flag) {
            callPhotoSubmit();
        } else {
            if (Utils.checkInternetConnection(GCCPhotoActivity.this)) {
                customProgressDialog.show();
                customProgressDialog.addText("Please wait...Uploading Data");
                viewModel.submitGCCDetails(request);
            } else {
                Utils.customWarningAlert(GCCPhotoActivity.this, getResources().getString(R.string.app_name), "Please check internet");
            }
        }
    }

    private void setStockDetailsSubmitRequest() {

        List<SubmitReqCommodities> essentialCommodityList = new ArrayList<>();
        List<SubmitReqCommodities> dailyReqList = new ArrayList<>();
        List<SubmitReqCommodities> mfp_commodities = new ArrayList<>();
        List<SubmitReqCommodities> emptiesList = new ArrayList<>();
        List<SubmitReqCommodities> processing_units = new ArrayList<>();

        if (stockDetailsResponse != null && stockDetailsResponse.getEssential_commodities() != null && stockDetailsResponse.getEssential_commodities().size() > 0) {
            for (int i = 0; i < stockDetailsResponse.getEssential_commodities().size(); i++) {
                SubmitReqCommodities essentialCommodity = new SubmitReqCommodities();
                essentialCommodity.setComType(stockDetailsResponse.getEssential_commodities().get(i).getCommName());
                essentialCommodity.setComCode(stockDetailsResponse.getEssential_commodities().get(i).getCommCode());
                essentialCommodity.setUnits(stockDetailsResponse.getEssential_commodities().get(i).getUnits());
                essentialCommodity.setSystemQty(stockDetailsResponse.getEssential_commodities().get(i).getQty());
                essentialCommodity.setSystemRate(stockDetailsResponse.getEssential_commodities().get(i).getRate());
                essentialCommodity.setSystemValue(stockDetailsResponse.getEssential_commodities().get(i).getQty() * stockDetailsResponse.getEssential_commodities().get(i).getRate());
                essentialCommodity.setPhysicalRate(stockDetailsResponse.getEssential_commodities().get(i).getRate());
                if (!TextUtils.isEmpty(stockDetailsResponse.getEssential_commodities().get(i).getPhyQuant())) {
                    essentialCommodity.setPhysiacalQty(Double.parseDouble(stockDetailsResponse.getEssential_commodities().get(i).getPhyQuant()));
                    essentialCommodity.setPhysicalValue(Double.parseDouble(stockDetailsResponse.getEssential_commodities().get(i).getPhyQuant()) * stockDetailsResponse.getEssential_commodities().get(i).getRate());
                } else {
                    //essentialCommodity.setPhysiacalQty(-1.0);
                    //essentialCommodity.setPhysicalValue(-1.0);
                }
                essentialCommodityList.add(essentialCommodity);
            }
        }
        if (stockDetailsResponse != null && stockDetailsResponse.getDialy_requirements() != null && stockDetailsResponse.getDialy_requirements().size() > 0) {
            for (int i = 0; i < stockDetailsResponse.getDialy_requirements().size(); i++) {
                SubmitReqCommodities essentialCommodity = new SubmitReqCommodities();
                essentialCommodity.setComType(stockDetailsResponse.getDialy_requirements().get(i).getCommName());
                essentialCommodity.setComCode(stockDetailsResponse.getDialy_requirements().get(i).getCommCode());
                essentialCommodity.setUnits(stockDetailsResponse.getDialy_requirements().get(i).getUnits());
                essentialCommodity.setSystemQty(stockDetailsResponse.getDialy_requirements().get(i).getQty());
                essentialCommodity.setSystemRate(stockDetailsResponse.getDialy_requirements().get(i).getRate());
                essentialCommodity.setSystemValue(stockDetailsResponse.getDialy_requirements().get(i).getQty() * stockDetailsResponse.getDialy_requirements().get(i).getRate());
                essentialCommodity.setPhysicalRate(stockDetailsResponse.getDialy_requirements().get(i).getRate());
                if (!TextUtils.isEmpty(stockDetailsResponse.getDialy_requirements().get(i).getPhyQuant())) {
                    essentialCommodity.setPhysiacalQty(Double.parseDouble(stockDetailsResponse.getDialy_requirements().get(i).getPhyQuant()));
                    essentialCommodity.setPhysicalValue(Double.parseDouble(stockDetailsResponse.getDialy_requirements().get(i).getPhyQuant()) * stockDetailsResponse.getDialy_requirements().get(i).getRate());
                } else {
                    // essentialCommodity.setPhysiacalQty(-1.0);
                    // essentialCommodity.setPhysicalValue(-1.0);
                }
                dailyReqList.add(essentialCommodity);
            }
        }
        if (stockDetailsResponse != null && stockDetailsResponse.getMfp_commodities() != null && stockDetailsResponse.getMfp_commodities().size() > 0) {
            for (int i = 0; i < stockDetailsResponse.getMfp_commodities().size(); i++) {
                SubmitReqCommodities essentialCommodity = new SubmitReqCommodities();
                essentialCommodity.setComType(stockDetailsResponse.getMfp_commodities().get(i).getCommName());
                essentialCommodity.setComCode(stockDetailsResponse.getMfp_commodities().get(i).getCommCode());
                essentialCommodity.setUnits(stockDetailsResponse.getMfp_commodities().get(i).getUnits());
                essentialCommodity.setSystemQty(stockDetailsResponse.getMfp_commodities().get(i).getQty());
                essentialCommodity.setSystemRate(stockDetailsResponse.getMfp_commodities().get(i).getRate());
                essentialCommodity.setSystemValue(stockDetailsResponse.getMfp_commodities().get(i).getQty() * stockDetailsResponse.getMfp_commodities().get(i).getRate());
                essentialCommodity.setPhysicalRate(stockDetailsResponse.getMfp_commodities().get(i).getRate());
                if (!TextUtils.isEmpty(stockDetailsResponse.getMfp_commodities().get(i).getPhyQuant())) {
                    essentialCommodity.setPhysiacalQty(Double.parseDouble(stockDetailsResponse.getMfp_commodities().get(i).getPhyQuant()));
                    essentialCommodity.setPhysicalValue(Double.parseDouble(stockDetailsResponse.getMfp_commodities().get(i).getPhyQuant()) * stockDetailsResponse.getMfp_commodities().get(i).getRate());
                } else {
                    //essentialCommodity.setPhysiacalQty(-1.0);
                    //essentialCommodity.setPhysicalValue(-1.0);
                }
                mfp_commodities.add(essentialCommodity);
            }
        }
        if (stockDetailsResponse != null && stockDetailsResponse.getEmpties() != null && stockDetailsResponse.getEmpties().size() > 0) {
            for (int i = 0; i < stockDetailsResponse.getEmpties().size(); i++) {
                SubmitReqCommodities essentialCommodity = new SubmitReqCommodities();
                essentialCommodity.setComType(stockDetailsResponse.getEmpties().get(i).getCommName());
                essentialCommodity.setComCode(stockDetailsResponse.getEmpties().get(i).getCommCode());
                essentialCommodity.setUnits(stockDetailsResponse.getEmpties().get(i).getUnits());
                essentialCommodity.setSystemQty(stockDetailsResponse.getEmpties().get(i).getQty());
                essentialCommodity.setSystemRate(stockDetailsResponse.getEmpties().get(i).getRate());
                essentialCommodity.setSystemValue(stockDetailsResponse.getEmpties().get(i).getQty() * stockDetailsResponse.getEmpties().get(i).getRate());
                essentialCommodity.setPhysicalRate(stockDetailsResponse.getEmpties().get(i).getRate());
                if (!TextUtils.isEmpty(stockDetailsResponse.getEmpties().get(i).getPhyQuant())) {
                    essentialCommodity.setPhysiacalQty(Double.parseDouble(stockDetailsResponse.getEmpties().get(i).getPhyQuant()));
                    essentialCommodity.setPhysicalValue(Double.parseDouble(stockDetailsResponse.getEmpties().get(i).getPhyQuant()) * stockDetailsResponse.getEmpties().get(i).getRate());
                } else {
                    //essentialCommodity.setPhysiacalQty(-1.0);
                    //essentialCommodity.setPhysicalValue(-1.0);
                }
                emptiesList.add(essentialCommodity);
            }
        }
        if (stockDetailsResponse != null && stockDetailsResponse.getProcessing_units() != null && stockDetailsResponse.getProcessing_units().size() > 0) {
            for (int i = 0; i < stockDetailsResponse.getProcessing_units().size(); i++) {
                SubmitReqCommodities essentialCommodity = new SubmitReqCommodities();
                essentialCommodity.setComType(stockDetailsResponse.getProcessing_units().get(i).getCommName());
                essentialCommodity.setComCode(stockDetailsResponse.getProcessing_units().get(i).getCommCode());
                essentialCommodity.setUnits(stockDetailsResponse.getProcessing_units().get(i).getUnits());
                essentialCommodity.setSystemQty(stockDetailsResponse.getProcessing_units().get(i).getQty());
                essentialCommodity.setSystemRate(stockDetailsResponse.getProcessing_units().get(i).getRate());
                essentialCommodity.setSystemValue(stockDetailsResponse.getProcessing_units().get(i).getQty() * stockDetailsResponse.getProcessing_units().get(i).getRate());
                essentialCommodity.setPhysicalRate(stockDetailsResponse.getProcessing_units().get(i).getRate());
                if (!TextUtils.isEmpty(stockDetailsResponse.getProcessing_units().get(i).getPhyQuant())) {
                    essentialCommodity.setPhysiacalQty(Double.parseDouble(stockDetailsResponse.getProcessing_units().get(i).getPhyQuant()));
                    essentialCommodity.setPhysicalValue(Double.parseDouble(stockDetailsResponse.getProcessing_units().get(i).getPhyQuant()) * stockDetailsResponse.getProcessing_units().get(i).getRate());
                } else {
                    //essentialCommodity.setPhysiacalQty(-1.0);
                    //essentialCommodity.setPhysicalValue(-1.0);
                }
                processing_units.add(essentialCommodity);
            }
        }
        stockSubmitRequest.setEssentialCommodities(essentialCommodityList);
        stockSubmitRequest.setDailyRequirements(dailyReqList);
        stockSubmitRequest.setMfpCommodities(mfp_commodities);
        stockSubmitRequest.setEmpties(emptiesList);
        stockSubmitRequest.setProcessingUnits(processing_units);
        String sysVal = sharedPreferences.getString(AppConstants.TOTAL_SYSVAL, "");
        String phyVal = sharedPreferences.getString(AppConstants.TOTAL_PHYVAL, "");
        stockSubmitRequest.setTotalSystemValue(Double.parseDouble(sysVal));
        stockSubmitRequest.setTotalPhysicalValue(Double.parseDouble(phyVal));
    }

    private void callPhotoSubmit() {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_entrance);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file_entrance.getName(), requestFile);
        RequestBody requestFile1 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_floor);
        MultipartBody.Part body1 =
                MultipartBody.Part.createFormData("image", file_floor.getName(), requestFile1);
        RequestBody requestFile2 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_ceiling);
        MultipartBody.Part body2 =
                MultipartBody.Part.createFormData("image", file_ceiling.getName(), requestFile2);
        RequestBody requestFile3 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_stock_arrang1);
        MultipartBody.Part body3 =
                MultipartBody.Part.createFormData("image", file_stock_arrang1.getName(), requestFile3);
        RequestBody requestFile4 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_stock_arrang2);
        MultipartBody.Part body4 =
                MultipartBody.Part.createFormData("image", file_stock_arrang2.getName(), requestFile4);
        MultipartBody.Part body5 = null;
        MultipartBody.Part body6 = null;
        if (flag_machinary == 1) {
            RequestBody requestFile5 =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file_machinary);
            body5 = MultipartBody.Part.createFormData("image", file_machinary.getName(), requestFile5);
        }
        if (file_repair != null) {
            RequestBody requestFile9 =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file_repair);

            body6 = MultipartBody.Part.createFormData("image", file_repair.getName(), requestFile9);
        }

        List<MultipartBody.Part> partList = new ArrayList<>();
        partList.add(body);
        partList.add(body1);
        partList.add(body2);
        partList.add(body3);
        partList.add(body4);
        if (body5 != null) {
            partList.add(body5);
        }
        if (body6 != null) {
            partList.add(body6);
        }

        if (flag) {
            String data = new Gson().toJson(request);
            String photos = new Gson().toJson(partList);
            GccOfflineEntity GCCOfflineEntity = new GccOfflineEntity();
            GCCOfflineEntity.setDivisionId(divId);
            GCCOfflineEntity.setDivisionName(divName);
            GCCOfflineEntity.setSocietyId(socId);
            GCCOfflineEntity.setSocietyName(socName);
            GCCOfflineEntity.setDrgownId(suppId);
            GCCOfflineEntity.setDrgownName(godName);
            GCCOfflineEntity.setTime(Utils.getCurrentDateTimeDisplay());

            GCCOfflineEntity.setData(data);
            GCCOfflineEntity.setPhotos(photos);
            GCCOfflineEntity.setType(type);

            gccOfflineRepository.insertGCCRecord(GCCPhotoActivity.this, GCCOfflineEntity);

        } else {
            customProgressDialog.show();
            customProgressDialog.addText("Please wait...Uploading Photos");

            viewModel.UploadImageServiceCall(partList);
        }
    }

    private boolean validate() {
        boolean returnFlag = true;
        if (flag_entrance == 0) {
            returnFlag = false;
            showSnackBar("Please capture entrance image");
        } else if (flag_floor == 0) {
            returnFlag = false;
            showSnackBar("Please capture floor image");
        } else if (flag_ceiling == 0) {
            returnFlag = false;
            showSnackBar("Please capture ceiling image");
        } else if (flag_stock_arrang1 == 0) {
            returnFlag = false;
            showSnackBar("Please capture stock arrangement image");
        } else if (flag_stock_arrang2 == 0) {
            returnFlag = false;
            showSnackBar("Please capture stock arrangement image");
        } else if (flag_pUnits == 1 && flag_machinary == 0) {
            returnFlag = false;
            showSnackBar("Please capture machinary image");
        }
        return returnFlag;
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.root, str, Snackbar.LENGTH_SHORT).show();
    }

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        FilePath = getExternalFilesDir(null)
                + "/" + IMAGE_DIRECTORY_NAME;

        String Image_name = PIC_NAME;
        FilePath = FilePath + "/" + Image_name;

//        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return FilePath;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                FilePath = getExternalFilesDir(null)
                        + "/" + IMAGE_DIRECTORY_NAME;

                String Image_name = PIC_TYPE + ".png";
                FilePath = FilePath + "/" + Image_name;

                FilePath = compressImage(FilePath);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bm = BitmapFactory.decodeFile(FilePath, options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);


                if (PIC_TYPE.equals(AppConstants.ENTRANCE)) {
                    flag_entrance = 1;
                    binding.ivEntrance.setPadding(0, 0, 0, 0);
                    binding.ivEntrance.setBackgroundColor(getResources().getColor(R.color.white));
                    file_entrance = new File(FilePath);
                    Glide.with(GCCPhotoActivity.this).load(file_entrance).into(binding.ivEntrance);

                } else if (PIC_TYPE.equals(AppConstants.FLOOR)) {
                    flag_floor = 1;
                    binding.ivFloor.setPadding(0, 0, 0, 0);
                    binding.ivFloor.setBackgroundColor(getResources().getColor(R.color.white));
                    file_floor = new File(FilePath);
                    Glide.with(GCCPhotoActivity.this).load(file_floor).into(binding.ivFloor);
                } else if (PIC_TYPE.equals(AppConstants.CEILING)) {
                    flag_ceiling = 1;
                    binding.ivCeiling.setPadding(0, 0, 0, 0);
                    binding.ivCeiling.setBackgroundColor(getResources().getColor(R.color.white));
                    file_ceiling = new File(FilePath);
                    Glide.with(GCCPhotoActivity.this).load(file_ceiling).into(binding.ivCeiling);
                } else if (PIC_TYPE.equals(AppConstants.STOCK_ARRANG1)) {
                    flag_stock_arrang1 = 1;
                    binding.ivStockArrng1.setPadding(0, 0, 0, 0);
                    binding.ivStockArrng1.setBackgroundColor(getResources().getColor(R.color.white));
                    file_stock_arrang1 = new File(FilePath);
                    Glide.with(GCCPhotoActivity.this).load(file_stock_arrang1).into(binding.ivStockArrng1);
                } else if (PIC_TYPE.equals(AppConstants.STOCK_ARRANG2)) {
                    flag_stock_arrang2 = 1;
                    binding.ivStockArrng2.setPadding(0, 0, 0, 0);
                    binding.ivStockArrng2.setBackgroundColor(getResources().getColor(R.color.white));
                    file_stock_arrang2 = new File(FilePath);
                    Glide.with(GCCPhotoActivity.this).load(file_stock_arrang2).into(binding.ivStockArrng2);
                } else if (PIC_TYPE.equals(AppConstants.MACHINARY)) {
                    flag_machinary = 1;
                    binding.ivMachinary.setPadding(0, 0, 0, 0);
                    binding.ivMachinary.setBackgroundColor(getResources().getColor(R.color.white));
                    file_machinary = new File(FilePath);
                    Glide.with(GCCPhotoActivity.this).load(file_machinary).into(binding.ivMachinary);
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


    public Uri getOutputMediaFileUri(int type) {
        File imageFile = getOutputMediaFile(type);
        Uri imageUri = null;
        if (imageFile != null) {
            imageUri = FileProvider.getUriForFile(
                    GCCPhotoActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider", //(use your app signature + ".provider" )
                    imageFile);
        }
        return imageUri;
    }

    private File getOutputMediaFile(int type) {
        mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("TAG", "Oops! Failed create " + "Android File Upload"
                        + " directory");
                return null;
            }
        }
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            PIC_NAME = PIC_TYPE + "~" + officerID + "~" + divId + "~" + suppId + "~" + Utils.getCurrentDateTimeFormat() + "~" +
                    Utils.getDeviceID(GCCPhotoActivity.this) + "~" + Utils.getVersionName(GCCPhotoActivity.this) + "~" + randomNum + ".png";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + PIC_TYPE + ".png");
        } else {
            return null;
        }

        return mediaFile;
    }


    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        Log.i("MSG", "handleError: " + errMsg);
        showSnackBar(errMsg);
    }

    @Override
    public void getData(GCCSubmitResponse gccSubmitResponse) {
        customProgressDialog.hide();
        if (gccSubmitResponse != null && gccSubmitResponse.getStatusCode() != null && gccSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
            callPhotoSubmit();
        } else if (gccSubmitResponse != null && gccSubmitResponse.getStatusCode() != null && gccSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {
            Snackbar.make(binding.root, gccSubmitResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(binding.root, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getPhotoData(GCCPhotoSubmitResponse gccPhotoSubmitResponse) {
        customProgressDialog.hide();
        if (gccPhotoSubmitResponse != null && gccPhotoSubmitResponse.getStatusCode() != null && gccPhotoSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_CODE)) {
            CallSuccessAlert(gccPhotoSubmitResponse.getStatusMessage());
        } else if (gccPhotoSubmitResponse != null && gccPhotoSubmitResponse.getStatusCode() != null && gccPhotoSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {
            Snackbar.make(binding.root, gccPhotoSubmitResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(binding.root, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void CallSuccessAlert(String msg) {

        if (mediaStorageDir.isDirectory()) {
            String[] children = mediaStorageDir.list();
            for (int i = 0; i < children.length; i++)
                new File(mediaStorageDir, children[i]).delete();
            mediaStorageDir.delete();
        }
        Utils.customSuccessAlert(this, getResources().getString(R.string.app_name), msg);
    }

    @Override
    public void gccRecCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {


                Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, DashboardMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletedrGoDownCount(int cnt) {
    }
}
