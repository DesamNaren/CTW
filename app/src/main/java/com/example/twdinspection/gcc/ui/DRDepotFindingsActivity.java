package com.example.twdinspection.gcc.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityDrDepotFindingsBinding;
import com.example.twdinspection.databinding.ActivityGccFindingsBinding;
import com.example.twdinspection.gcc.source.inspections.DrDepotInspection;
import com.example.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.example.twdinspection.inspection.ui.LocBaseActivity;
import com.example.twdinspection.inspection.ui.UploadedPhotoActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class DRDepotFindingsActivity extends LocBaseActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ActivityDrDepotFindingsBinding binding;
    String PIC_NAME, PIC_TYPE;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public Uri fileUri;
    public static final String IMAGE_DIRECTORY_NAME = "GCC_IMAGES";
    String FilePath, checkUpDate;
    Bitmap bm;
    File file;
    double physVal = 0, sysVal = 0;
    private StockDetailsResponse stockDetailsResponse;
    private String ecsStock,drStock,emptyStock,abstractSales,depotCashBook,liabilityReg,visitorsBook,saleBook,weightsMeasurements,certIssueDate;
    private String depotAuthCert,mfpStock,mfpPurchase,billAbstract,abstractAccnt,advanceAccnt,mfpLiability,depotNameBoard,gccObjPrinc;
    private String depotTimimg,mfpComm,ecComm,drComm,stockBal,valuesAsPerSale,valuesAsPerPurchasePrice,qualVerified,depotMaintHygeine,repairsReq,repairsType;
    private int repairsFlag=0;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_depot_findings);
        binding.header.headerTitle.setText(getString(R.string.ins_off_fin));

        sharedPreferences = TWDApplication.get(DRDepotFindingsActivity.this).getPreferences();
        String stockData = sharedPreferences.getString(AppConstants.stockData, "");
        Gson gson = new Gson();
        stockDetailsResponse = gson.fromJson(stockData, StockDetailsResponse.class);
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (stockDetailsResponse != null) {
//            if (stockDetailsResponse.getEssential_commodities() != null && stockDetailsResponse.getEssential_commodities().size() > 0) {
//                for (int i = 0; i < stockDetailsResponse.getEssential_commodities().size(); i++) {
//                    physVal += Double.parseDouble(stockDetailsResponse.getEssential_commodities().get(i).getPhyQuant());
//                    sysVal += stockDetailsResponse.getEssential_commodities().get(i).getQty() * stockDetailsResponse.getEssential_commodities().get(i).getRate();
//                }
//            }
//            if (stockDetailsResponse.getDialy_requirements() != null && stockDetailsResponse.getDialy_requirements().size() > 0) {
//                for (int i = 0; i < stockDetailsResponse.getDialy_requirements().size(); i++) {
//                    physVal += Double.parseDouble(stockDetailsResponse.getDialy_requirements().get(i).getPhyQuant());
//                    sysVal += stockDetailsResponse.getDialy_requirements().get(i).getQty() * stockDetailsResponse.getDialy_requirements().get(i).getRate();
//                }
//            }
//            if (stockDetailsResponse.getEmpties() != null && stockDetailsResponse.getEmpties().size() > 0) {
//                for (int i = 0; i < stockDetailsResponse.getEmpties().size(); i++) {
//                    physVal += Double.parseDouble(stockDetailsResponse.getEmpties().get(i).getPhyQuant());
//                    sysVal += stockDetailsResponse.getEmpties().get(i).getQty() * stockDetailsResponse.getEmpties().get(i).getRate();
//                }
//            }
//            if (stockDetailsResponse.getMfp_commodities() != null && stockDetailsResponse.getMfp_commodities().size() > 0) {
//                for (int i = 0; i < stockDetailsResponse.getMfp_commodities().size(); i++) {
//                    physVal += Double.parseDouble(stockDetailsResponse.getMfp_commodities().get(i).getPhyQuant());
//                    sysVal += stockDetailsResponse.getMfp_commodities().get(i).getQty() * stockDetailsResponse.getMfp_commodities().get(i).getRate();
//                }
//            }
//            if (stockDetailsResponse.getProcessing_units() != null && stockDetailsResponse.getProcessing_units().size() > 0) {
//                for (int i = 0; i < stockDetailsResponse.getProcessing_units().size(); i++) {
//                    physVal += Double.parseDouble(stockDetailsResponse.getProcessing_units().get(i).getPhyQuant());
//                    sysVal += stockDetailsResponse.getProcessing_units().get(i).getQty() * stockDetailsResponse.getProcessing_units().get(i).getRate();
//                }
//            }
//        }

        binding.tvSysVal.setText(String.format("%.2f",sysVal));
        binding.tvPhysVal.setText(String.format("%.2f",physVal));
        binding.tvDiffVal.setText(String.format("%.2f", sysVal - physVal));

        binding.rgWeightMeasCert.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.weight_meas_cert_rb_yes) {
                    binding.weightCertIssueDate.setVisibility(View.VISIBLE);
                    weightsMeasurements=AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.weight_meas_cert_rb_no) {
                    weightsMeasurements=AppConstants.No;
                    binding.weightCertIssueDate.setVisibility(View.GONE);
                }
            }
        });
        binding.weightCertIssueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                certIssueDateSelection();
            }
        });
        binding.rgRepairsReq.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.repairs_req_yes_rb) {
                    repairsReq = AppConstants.Yes;
                    binding.insLl.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.repairs_req_no_rb) {
                    repairsReq = AppConstants.No;
                    binding.insLl.setVisibility(View.GONE);
                }
            }
        });
        binding.ivRepairsCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {

                    PIC_TYPE = AppConstants.STOREROOM;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });

        binding.rgEcsStock.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgEcsStock.getCheckedRadioButtonId();
                if (selctedItem == R.id.ecs_stock_yes_rb)
                    ecsStock = AppConstants.Yes;
                else
                    ecsStock = AppConstants.No;
            }
        });

        binding.rgDrStock.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDrStock.getCheckedRadioButtonId();
                if (selctedItem == R.id.dr_stock_rb_yes)
                    drStock = AppConstants.Yes;
                else
                    drStock = AppConstants.No;
            }
        });

        binding.rgEmptiesStock.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgEmptiesStock.getCheckedRadioButtonId();
                if (selctedItem == R.id.empties_stock_rb_yes)
                    emptyStock = AppConstants.Yes;
                else
                    emptyStock = AppConstants.No;
            }
        });

        binding.rgAbstractSales.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAbstractSales.getCheckedRadioButtonId();
                if (selctedItem == R.id.abstract_sales_rb_yes)
                    abstractSales = AppConstants.Yes;
                else
                    abstractSales = AppConstants.No;
            }
        });

        binding.rgDepotCashBook.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDepotCashBook.getCheckedRadioButtonId();
                if (selctedItem == R.id.depot_cash_book_yes_rb)
                    depotCashBook = AppConstants.Yes;
                else
                    depotCashBook = AppConstants.No;
            }
        });

        binding.rgLiabilityReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgLiabilityReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.liability_reg_rb_yes)
                    liabilityReg = AppConstants.Yes;
                else
                    liabilityReg = AppConstants.No;
            }
        });

        binding.rgVisitBookDepot.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgVisitBookDepot.getCheckedRadioButtonId();
                if (selctedItem == R.id.visit_book_depot_rb_yes)
                    visitorsBook = AppConstants.Yes;
                else
                    visitorsBook = AppConstants.No;
            }
        });

        binding.rgSaleBillBook.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSaleBillBook.getCheckedRadioButtonId();
                if (selctedItem == R.id.sale_bill_book_rb_yes)
                    saleBook = AppConstants.Yes;
                else
                    saleBook = AppConstants.No;
            }
        });

        binding.rgDepotAuthCert.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDepotAuthCert.getCheckedRadioButtonId();
                if (selctedItem == R.id.depot_auth_cert_rb_yes)
                    depotAuthCert = AppConstants.Yes;
                else
                    depotAuthCert = AppConstants.No;
            }
        });

        binding.rgMfpStock.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMfpStock.getCheckedRadioButtonId();
                if (selctedItem == R.id.mfp_stock_yes_rb)
                    mfpStock = AppConstants.Yes;
                else
                    mfpStock = AppConstants.No;
            }
        });

        binding.rgMfpPurchase.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMfpPurchase.getCheckedRadioButtonId();
                if (selctedItem == R.id.mfp_purchase_rb_yes)
                    mfpPurchase = AppConstants.Yes;
                else
                    mfpPurchase = AppConstants.No;
            }
        });

        binding.rgBillAbstract.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBillAbstract.getCheckedRadioButtonId();
                if (selctedItem == R.id.bill_abstract_rb_yes)
                    billAbstract = AppConstants.Yes;
                else
                    billAbstract = AppConstants.No;
            }
        });

        binding.rgAbstractAccntBook.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAbstractAccntBook.getCheckedRadioButtonId();
                if (selctedItem == R.id.abstract_accnt_book_rb_yes)
                    abstractAccnt = AppConstants.Yes;
                else
                    abstractAccnt = AppConstants.No;
            }
        });

        binding.rgAdvanceAccntBook.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAdvanceAccntBook.getCheckedRadioButtonId();
                if (selctedItem == R.id.advance_accnt_book_yes_rb)
                    advanceAccnt = AppConstants.Yes;
                else
                    advanceAccnt = AppConstants.No;
            }
        });

        binding.rgMfpLiabilityReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMfpLiabilityReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.mfp_liability_reg_rb_yes)
                    mfpLiability = AppConstants.Yes;
                else
                    mfpLiability = AppConstants.No;
            }
        });

        binding.rgDepotNameBoard.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDepotNameBoard.getCheckedRadioButtonId();
                if (selctedItem == R.id.depot_name_board_yes_rb)
                    depotNameBoard = AppConstants.Yes;
                else
                    depotNameBoard = AppConstants.No;
            }
        });

        binding.rgObjPrinc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgObjPrinc.getCheckedRadioButtonId();
                if (selctedItem == R.id.obj_princ_rb_yes)
                    gccObjPrinc = AppConstants.Yes;
                else
                    gccObjPrinc = AppConstants.No;
            }
        });

        binding.rgDepotTimimg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDepotTimimg.getCheckedRadioButtonId();
                if (selctedItem == R.id.depot_timimg_rb_yes)
                    depotTimimg = AppConstants.Yes;
                else
                    depotTimimg = AppConstants.No;
            }
        });

        binding.rgCommRate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCommRate.getCheckedRadioButtonId();
                if (selctedItem == R.id.comm_rate_rb_yes)
                    mfpComm = AppConstants.Yes;
                else
                    mfpComm = AppConstants.No;
            }
        });

        binding.rgEcCommRate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgEcCommRate.getCheckedRadioButtonId();
                if (selctedItem == R.id.ec_comm_rate_yes_rb)
                    ecComm = AppConstants.Yes;
                else
                    ecComm = AppConstants.No;
            }
        });

        binding.rgDrCommRate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDrCommRate.getCheckedRadioButtonId();
                if (selctedItem == R.id.dr_comm_rate_rb_yes)
                    drComm = AppConstants.Yes;
                else
                    drComm = AppConstants.No;
            }
        });

        binding.rgStockBal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStockBal.getCheckedRadioButtonId();
                if (selctedItem == R.id.stock_bal_rb_yes)
                    stockBal = AppConstants.Yes;
                else
                    stockBal = AppConstants.No;
            }
        });

        binding.rgValuesAsPerSalePrice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgValuesAsPerSalePrice.getCheckedRadioButtonId();
                if (selctedItem == R.id.values_as_per_sale_price_yes_rb)
                    valuesAsPerSale = AppConstants.Yes;
                else
                    valuesAsPerSale = AppConstants.No;
            }
        });

        binding.rgValuesAsPerPurchasePrice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgValuesAsPerPurchasePrice.getCheckedRadioButtonId();
                if (selctedItem == R.id.values_as_per_purchase_price_rb_yes)
                    valuesAsPerPurchasePrice = AppConstants.Yes;
                else
                    valuesAsPerPurchasePrice = AppConstants.No;
            }
        });

        binding.rgQualVerified.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgQualVerified.getCheckedRadioButtonId();
                if (selctedItem == R.id.qual_verified_rb_yes)
                    qualVerified = AppConstants.Yes;
                else
                    qualVerified = AppConstants.No;
            }
        });

        binding.rgDepotMaintHygeine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDepotMaintHygeine.getCheckedRadioButtonId();
                if (selctedItem == R.id.depot_maint_hygeine_rb_yes)
                    depotMaintHygeine = AppConstants.Yes;
                else
                    depotMaintHygeine = AppConstants.No;
            }
        });
       binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(validate()){
                   repairsType=binding.etRepairsType.getText().toString().trim();

                   DrDepotInspection inspection=new DrDepotInspection();
                   inspection.setEcsStock(ecsStock);
                   inspection.setDrStock(drStock);
                   inspection.setEmptyStock(emptyStock);
                   inspection.setAbstractSales(abstractSales);
                   inspection.setDepotCashBook(depotCashBook);
                   inspection.setLiabilityReg(liabilityReg);
                   inspection.setVisitorsBook(visitorsBook);
                   inspection.setSaleBook(saleBook);
                   inspection.setWeightsMeasurements(weightsMeasurements);
                   inspection.setDepotAuthCert(depotAuthCert);
                   inspection.setMfpStock(mfpStock);
                   inspection.setMfpPurchase(mfpPurchase);
                   inspection.setBillAbstract(billAbstract);
                   inspection.setAbstractAccnt(abstractAccnt);
                   inspection.setAdvanceAccnt(advanceAccnt);
                   inspection.setMfpLiability(mfpLiability);
                   inspection.setDepotNameBoard(depotNameBoard);
                   inspection.setGccObjPrinc(gccObjPrinc);
                   inspection.setDepotTimimg(depotTimimg);
                   inspection.setMfpComm(mfpComm);
                   inspection.setEcComm(ecComm);
                   inspection.setDrComm(drComm);
                   inspection.setStockBal(stockBal);
                   inspection.setValuesAsPerSale(valuesAsPerSale);
                   inspection.setValuesAsPerPurchasePrice(valuesAsPerPurchasePrice);
                   inspection.setQualVerified(qualVerified);
                   inspection.setDepotMaintHygeine(depotMaintHygeine);
                   inspection.setRepairsReq(repairsReq);
                   inspection.setRepairsType(repairsType);
                   inspection.setRemarks(binding.etRemarks.getText().toString());
                   inspection.setFeedback(binding.etFeedback.getText().toString());
                   inspection.setRepairsPath(FilePath);

                   String DepotInspection=gson.toJson(inspection);
                   editor.putString(AppConstants.DepotInspection,DepotInspection);
                   editor.commit();
               }
           }
       });
    }

    private boolean validate() {
        boolean returnFlag=true;
        if(TextUtils.isEmpty(ecsStock)){
            returnFlag=false;
            showSnackBar("Please check Ecs stock register");
            ScrollToView(binding.rgEcsStock);
        }else if(TextUtils.isEmpty(drStock)){
            returnFlag=false;
            showSnackBar("Please check DRs stock register");
            ScrollToView(binding.rgDrStock);
        }else if(TextUtils.isEmpty(emptyStock)){
            returnFlag=false;
            showSnackBar("Please check empties stock register");
            ScrollToView(binding.rgEmptiesStock);
        }else if(TextUtils.isEmpty(abstractSales)){
            returnFlag=false;
            showSnackBar("Please check abstract sales register");
            ScrollToView(binding.rgAbstractSales);
        }else if(TextUtils.isEmpty(depotCashBook)){
            returnFlag=false;
            showSnackBar("Please check Cash book");
            ScrollToView(binding.rgDepotCashBook);
        }else if(TextUtils.isEmpty(liabilityReg)){
            returnFlag=false;
            showSnackBar("Please check liability register");
            ScrollToView(binding.rgDepotCashBook);
        }else if(TextUtils.isEmpty(visitorsBook)){
            returnFlag=false;
            showSnackBar("Please check visitors note book");
            ScrollToView(binding.rgVisitBookDepot);
        }else if(TextUtils.isEmpty(saleBook)){
            returnFlag=false;
            showSnackBar("Please check sale bill book");
            ScrollToView(binding.rgSaleBillBook);
        }else if(TextUtils.isEmpty(weightsMeasurements)){
            returnFlag=false;
            showSnackBar("Please check weights and measurements certificate issued by legal metrology");
            ScrollToView(binding.rgWeightMeasCert);
        }else if(!TextUtils.isEmpty(weightsMeasurements) && weightsMeasurements.equals(AppConstants.Yes) && certIssueDate.equals(getResources().getString(R.string.select_date)) ){
            returnFlag=false;
            showSnackBar("Please select certificate issue date");
            ScrollToView(binding.etWeightCertIssue);
        }else if(TextUtils.isEmpty(depotAuthCert)){
            returnFlag=false;
            showSnackBar("Please check depot authorisation certificate issued by revenue authorities");
            ScrollToView(binding.rgWeightMeasCert);
        }else if(TextUtils.isEmpty(mfpStock)){
            returnFlag=false;
            showSnackBar("Please check MFP stock register");
            ScrollToView(binding.rgMfpStock);
        }else if(TextUtils.isEmpty(mfpPurchase)){
            returnFlag=false;
            showSnackBar("Please check MFP purchase bill book");
            ScrollToView(binding.rgMfpPurchase);
        }else if(TextUtils.isEmpty(billAbstract)){
            returnFlag=false;
            showSnackBar("Please check bill abstract book");
            ScrollToView(binding.rgBillAbstract);
        }else if(TextUtils.isEmpty(abstractAccnt)){
            returnFlag=false;
            showSnackBar("Please check abstract account book");
            ScrollToView(binding.rgAbstractAccntBook);
        }else if(TextUtils.isEmpty(advanceAccnt)){
            showSnackBar("Please check advance  account book");
            ScrollToView(binding.rgAdvanceAccntBook);
        }else if(TextUtils.isEmpty(mfpLiability)){
            returnFlag=false;
            showSnackBar("Please check MFP liability register");
            ScrollToView(binding.rgMfpLiabilityReg);
        }else if(TextUtils.isEmpty(depotNameBoard)){
            returnFlag=false;
            showSnackBar("Please check depot name board");
            ScrollToView(binding.rgDepotNameBoard);
        }else if(TextUtils.isEmpty(gccObjPrinc)){
            returnFlag=false;
            showSnackBar("Please check GCC objectives/principles board");
            ScrollToView(binding.rgObjPrinc);
        }else if(TextUtils.isEmpty(depotTimimg)){
            returnFlag=false;
            showSnackBar("Please check depot timing board");
            ScrollToView(binding.rgDepotTimimg);
        }else if(TextUtils.isEmpty(mfpComm)){
            returnFlag=false;
            showSnackBar("Please check MFP commodities rate board");
            ScrollToView(binding.rgCommRate);
        }else if(TextUtils.isEmpty(ecComm)){
            returnFlag=false;
            showSnackBar("Please check EC commodities rate board");
            ScrollToView(binding.rgEcCommRate);
        }else if(TextUtils.isEmpty(drComm)){
            returnFlag=false;
            showSnackBar("Please check DR commodities rate board");
            ScrollToView(binding.rgDrCommRate);
        }else if(TextUtils.isEmpty(stockBal)){
            returnFlag=false;
            showSnackBar("Please check stock balance board");
            ScrollToView(binding.rgStockBal);
        }else if(TextUtils.isEmpty(valuesAsPerSale)){
            returnFlag=false;
            showSnackBar("Please check whether the values are as per sale price");
            ScrollToView(binding.rgValuesAsPerSalePrice);
        }else if(TextUtils.isEmpty(valuesAsPerPurchasePrice)){
            returnFlag=false;
            showSnackBar("Please check whether the values are as per purchase price");
            ScrollToView(binding.rgValuesAsPerPurchasePrice);
        }else if(TextUtils.isEmpty(qualVerified)){
            returnFlag=false;
            showSnackBar("Please check the quality of the stocks was verified");
            ScrollToView(binding.rgQualVerified);
        }else if(TextUtils.isEmpty(depotMaintHygeine)){
            returnFlag=false;
            showSnackBar("Please check whether the depot is maintained in hygienic condition");
            ScrollToView(binding.rgDepotMaintHygeine);
        }else if(TextUtils.isEmpty(repairsReq)){
            returnFlag=false;
            showSnackBar("Please check any repairs required for Dr Godown");
            ScrollToView(binding.rgRepairsReq);
        }else if(!(TextUtils.isEmpty(repairsReq))&& repairsReq.equals(AppConstants.Yes)&& TextUtils.isEmpty(binding.etRepairsType.getText().toString().trim())){
            returnFlag=false;
            showSnackBar("Please enter repair type");
            ScrollToView(binding.etRepairsType);
        }else if(!TextUtils.isEmpty(repairsReq) &&repairsReq.equals(AppConstants.Yes)&&  !TextUtils.isEmpty(binding.etRepairsType.getText().toString()) && repairsFlag==0 ){
            returnFlag=false;
            showSnackBar("Please capture repair");
            ScrollToView(binding.ivRepairsCam);
        }else if(TextUtils.isEmpty(binding.etFeedback.getText().toString().trim())){
            returnFlag=false;
            showSnackBar("Please enter feedback of card holders");
            ScrollToView(binding.etFeedback);
        }else if(TextUtils.isEmpty(binding.etRemarks.getText().toString().trim())){
            returnFlag=false;
            showSnackBar("Please enter remarks");
            ScrollToView(binding.etFeedback);
        }
        return returnFlag;
    }
    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
    }

    private void ScrollToView(View view) {
//        binding.scroll.smoothScrollTo(0, view.getBottom());
    }
    private void certIssueDateSelection() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        checkUpDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        binding.etWeightCertIssue.setText(checkUpDate);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                FilePath = getExternalFilesDir(null)
                        + "/" + IMAGE_DIRECTORY_NAME;

                String Image_name = PIC_NAME;
                FilePath = FilePath + "/" + Image_name;

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bm = BitmapFactory.decodeFile(FilePath, options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                String OLDmyBase64Image = encodeToBase64(bm, Bitmap.CompressFormat.JPEG,
                        100);
                file = new File(FilePath);
                Glide.with(DRDepotFindingsActivity.this).load(file).into(binding.ivRepairsCam);
                repairsFlag=1;

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
                    DRDepotFindingsActivity.this,
                    "com.example.twdinspection.provider", //(use your app signature + ".provider" )
                    imageFile);
        }
        return imageUri;
    }

    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("TAG", "Oops! Failed create " + "Android File Upload"
                        + " directory");
                return null;
            }
        }
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
//            PIC_NAME = officerId + "~" + instId + "~" + Utils.getCurrentDateTime() + "~" + PIC_TYPE + ".png";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + PIC_NAME);
        } else {
            return null;
        }

        return mediaFile;
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }


}
