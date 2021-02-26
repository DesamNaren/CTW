package com.cgg.twdinspection.gcc.ui.petrolpump;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;

import com.bumptech.glide.Glide;
import com.cgg.twdinspection.BuildConfig;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.custom.CustomFontEditText;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityGccPetrolPumpFindingsBinding;
import com.cgg.twdinspection.gcc.source.inspections.InspectionSubmitResponse;
import com.cgg.twdinspection.gcc.source.inspections.petrol_pump.PetrolPumpGeneralFindings;
import com.cgg.twdinspection.gcc.source.inspections.petrol_pump.PetrolPumpIns;
import com.cgg.twdinspection.gcc.source.inspections.petrol_pump.PetrolPumpRegisterBookCertificates;
import com.cgg.twdinspection.gcc.source.inspections.petrol_pump.PetrolPumpStockDetails;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.gcc.source.stock.CommonCommodity;
import com.cgg.twdinspection.gcc.source.stock.PetrolStockDetailsResponse;
import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolSupplierInfo;
import com.cgg.twdinspection.gcc.viewmodel.GCCOfflineViewModel;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class PetrolPumpFindingsActivity extends LocBaseActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ActivityGccPetrolPumpFindingsBinding binding;
    String PIC_NAME, PIC_TYPE;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public Uri fileUri;
    public static final String IMAGE_DIRECTORY_NAME = AppConstants.GCC_IMAGES;
    String FilePath, checkUpDate;
    Bitmap bm;
    File file;
    private String officerID, divId, suppId;
    double physVal = 0, sysVal = 0, difference = 0, insSysVal = 0, notInsSysVal = 0;
    private PetrolStockDetailsResponse finalStockDetailsResponse;
    private String stockReg, purchaseReg, dailysales, godownLiaReg, cashbook, remittance, remittanceCash, insCer, fireNOC, weightMea;
    private String petrolPumpCom, petrolPumpHyg, availEqp, availCcCameras, lastInsSoc, lastInsDiv, repairsReq;
    private String insComName, insComDate, weightDate, lastSocDate, lastDivDate, deficitReason, remarks, repairType;
    private int repairsFlag = 0;
    private String randomNum;
    private List<CommonCommodity> finalPetCom;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gcc_petrol_pump_findings);
        binding.header.headerTitle.setText(getString(R.string.petrol_ins_off_fin));
        binding.header.ivHome.setVisibility(View.GONE);
        binding.bottomLl.btnNext.setText(getString(R.string.saveandnext));
        randomNum = Utils.getRandomNumberString();

        finalPetCom = new ArrayList<>();

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String stockData = sharedPreferences.getString(AppConstants.stockData, "");
        officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        Gson gson = new Gson();
        PetrolStockDetailsResponse stockDetailsResponse = gson.fromJson(stockData, PetrolStockDetailsResponse.class);

        String petrolData = sharedPreferences.getString(AppConstants.PETROL_PUMP_DATA, "");
        PetrolSupplierInfo petrolSupplierInfo = gson.fromJson(petrolData, PetrolSupplierInfo.class);
        divId = petrolSupplierInfo.getDivisionId();
        suppId = petrolSupplierInfo.getGodownId();

        GCCOfflineViewModel gccOfflineViewModel = new GCCOfflineViewModel(getApplication());
        LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(
                petrolSupplierInfo.getDivisionId(), petrolSupplierInfo.getSocietyId(), petrolSupplierInfo.getGodownId());

        drGodownLiveData.observe(PetrolPumpFindingsActivity.this, new androidx.lifecycle.Observer<GccOfflineEntity>() {
            @Override
            public void onChanged(GccOfflineEntity gccOfflineEntity) {
                binding.header.ivMode.setVisibility(View.VISIBLE);
                if (gccOfflineEntity != null) {
                    binding.header.ivMode.setBackground(getResources().getDrawable(R.drawable.offline_mode));
                } else {
                    binding.header.ivMode.setBackground(getResources().getDrawable(R.drawable.online_mode));
                }
            }
        });

        if (stockDetailsResponse != null) {
            if (stockDetailsResponse.getCommonCommodities() != null && stockDetailsResponse.getCommonCommodities().size() > 0) {
                for (int i = 0; i < stockDetailsResponse.getCommonCommodities().size(); i++) {
                    if (!TextUtils.isEmpty(stockDetailsResponse.getCommonCommodities().get(i).getPhyQuant())) {
                        physVal += Double.parseDouble(stockDetailsResponse.getCommonCommodities().get(i).getPhyQuant()) * stockDetailsResponse.getCommonCommodities().get(i).getRate();
                        insSysVal += stockDetailsResponse.getCommonCommodities().get(i).getQty() * stockDetailsResponse.getCommonCommodities().get(i).getRate();

                        finalPetCom.add(stockDetailsResponse.getCommonCommodities().get(i));
                    }
                    sysVal += stockDetailsResponse.getCommonCommodities().get(i).getQty() * stockDetailsResponse.getCommonCommodities().get(i).getRate();
                }
            }

            sysVal = Double.parseDouble(String.format("%.2f", sysVal));
            physVal = Double.parseDouble(String.format("%.2f", physVal));
            binding.tvSysVal.setText(String.format("%.2f", sysVal));
            binding.tvPhysVal.setText(String.format("%.2f", physVal));
            notInsSysVal = sysVal - insSysVal;
            notInsSysVal = Double.parseDouble(String.format("%.2f", notInsSysVal));
            binding.tvInsSysVal.setText(String.format("%.2f", insSysVal));
            binding.tvNotInsSysVal.setText(String.format("%.2f", notInsSysVal));
            difference = insSysVal - physVal;
            difference = Double.parseDouble(String.format("%.2f", difference));
            binding.tvDiffVal.setText(String.format("%.2f", difference));
        }


        binding.rgStock.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.stock_yes_rb) {
                    stockReg = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.stock_no_rb) {
                    stockReg = AppConstants.No;
                }
            }
        });


        binding.rgPurchase.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.purchase_rb_yes) {
                    purchaseReg = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.purchase_rb_no) {
                    purchaseReg = AppConstants.No;
                }
            }
        });


        binding.rgSale.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.sale_rb_yes) {
                    dailysales = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.sale_rb_no) {
                    dailysales = AppConstants.No;
                }
            }
        });


        binding.rgGodown.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.godown_rb_yes) {
                    godownLiaReg = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.godown_rb_no) {
                    godownLiaReg = AppConstants.No;
                }
            }
        });

        binding.rgCash.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.cash_rb_yes) {
                    cashbook = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.cash_rb_no) {
                    cashbook = AppConstants.No;
                }
            }
        });

        binding.rgRemittance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.remittance_rb_yes) {
                    remittance = AppConstants.Yes;
                    binding.llRemittanceCash.setVisibility(View.GONE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.remittance_rb_no) {
                    remittance = AppConstants.No;
                    binding.llRemittanceCash.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.rgRemittanceCash.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.remittance_cash_rb_daily) {
                    remittanceCash = "Daily";
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.remittance_cash_rb_alt) {
                    remittanceCash = "Alternate days";
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.remittance_cash_rb_weekly) {
                    remittanceCash = "Weekly";
                }
            }
        });


        binding.rgInsurance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.insurance_yes_rb) {
                    insCer = AppConstants.Yes;
                    binding.insLl.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.insurance_no_rb) {
                    insCer = AppConstants.No;
                    binding.insLl.setVisibility(View.GONE);
                }
            }
        });


        binding.rgFireNoc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.fire_noc_rb_yes) {
                    fireNOC = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.fire_noc_rb_no) {
                    fireNOC = AppConstants.No;
                }
            }
        });


        binding.rgWeight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.weight_rb_yes) {
                    weightMea = AppConstants.Yes;
                    binding.legalLl.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.weight_rb_no) {
                    weightMea = AppConstants.No;
                    binding.legalLl.setVisibility(View.GONE);
                }
            }
        });

        binding.rgPetrolPumpComputerized.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.petrol_pump_computerized_yes_rb) {
                    petrolPumpCom = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.petrol_pump_computerized_no_rb) {
                    petrolPumpCom = AppConstants.No;
                }
            }
        });


        binding.rgPetrolPumpMaintained.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.petrol_pump_maintained_rb_yes) {
                    petrolPumpHyg = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.petrol_pump_maintained_rb_no) {
                    petrolPumpHyg = AppConstants.No;
                }
            }
        });


        binding.rgAvailEquipment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.avail_equipment_rb_yes) {
                    availEqp = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.avail_equipment_rb_no) {
                    availEqp = AppConstants.No;
                }
            }
        });

        binding.rgAvailCcCameras.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.avail_cc_cameras_rb_yes) {
                    availCcCameras = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.avail_cc_cameras_rb_no) {
                    availCcCameras = AppConstants.No;
                }
            }
        });


        binding.rgInspDateSocManager.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.insp_date_soc_manager_rb_yes) {
                    lastInsSoc = AppConstants.Yes;
                    binding.lastInsSocLl.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.insp_date_soc_manager_rb_no) {
                    lastInsSoc = AppConstants.No;
                    binding.lastInsSocLl.setVisibility(View.GONE);
                }
            }
        });


        binding.rgInspDateDivManager.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.insp_date_div_manager_rb_yes) {
                    lastInsDiv = AppConstants.Yes;
                    binding.lastInsDivLl.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.insp_date_div_manager_rb_no) {
                    lastInsDiv = AppConstants.No;
                    binding.lastInsDivLl.setVisibility(View.GONE);
                }
            }
        });


        binding.rgRepairsReq.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.repairs_req_rb_yes) {
                    repairsReq = AppConstants.Yes;
                    binding.repairsLl.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.repairs_req_rb_no) {
                    repairsReq = AppConstants.No;
                    binding.repairsLl.setVisibility(View.GONE);
                }
            }
        });


        binding.etInsDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                certIssueDateSelection();
            }
        });

        binding.etLegalMetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                legalMetDateSelection();
            }
        });


        binding.lastInsSocDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastInsSocDateSelection();
            }
        });

        binding.lastInsDivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastInsDivDateSelection();
            }
        });
        binding.ivRepairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {

                    PIC_TYPE = AppConstants.REPAIR;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });


        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                remarks = binding.etRemarks.getText().toString().trim();
                deficitReason = binding.etReason.getText().toString().trim();
                insComName = binding.etComName.getText().toString().trim();
                insComDate = binding.etInsDate.getText().toString().trim();
                weightDate = binding.etLegalMetDate.getText().toString().trim();
                lastSocDate = binding.lastInsSocDate.getText().toString().trim();
                lastDivDate = binding.lastInsDivDate.getText().toString().trim();
                repairType = binding.etRepairType.getText().toString().trim();

                if (validate()) {

                    PetrolPumpIns petrolPumpIns = new PetrolPumpIns();

                    PetrolPumpStockDetails stockDetails = new PetrolPumpStockDetails();
                    stockDetails.setStockValueAsPerSystem(sysVal);
                    stockDetails.setStockValueAsPerPhysical(physVal);
                    stockDetails.setDeficit(difference);
                    stockDetails.setDeficitReason(deficitReason);
                    stockDetails.setStockValueAsPerSystemInsp(String.valueOf(insSysVal));
                    stockDetails.setStockValueAsPerSystemNotInsp(String.valueOf(notInsSysVal));

                    petrolPumpIns.setStockDetails(stockDetails);

                    PetrolPumpRegisterBookCertificates registerBookCertificates = new PetrolPumpRegisterBookCertificates();
                    registerBookCertificates.setStockRegister(stockReg);
                    registerBookCertificates.setCashBook(cashbook);
                    registerBookCertificates.setDailyRemittance(remittanceCash);
                    registerBookCertificates.setRemittance(remittance);
                    registerBookCertificates.setPurchaseRegister(purchaseReg);
                    registerBookCertificates.setDailySalesRegister(dailysales);
                    registerBookCertificates.setGodownLiabilityRegister(godownLiaReg);
                    registerBookCertificates.setInsuranceCertificate(insCer);
                    registerBookCertificates.setInsuranceCompany(insComName);
                    if (!insComDate.contains("/")) {
                        registerBookCertificates.setInsuranceValidity("");
                    } else {
                        registerBookCertificates.setInsuranceValidity(insComDate);
                    }
                    registerBookCertificates.setFireDeptNoc(fireNOC);
                    registerBookCertificates.setWeightMeasureCertificate(weightMea);
                    if (!weightDate.contains("/")) {
                        registerBookCertificates.setWeightMeasureValidity("");
                    } else {
                        registerBookCertificates.setWeightMeasureValidity(weightDate);
                    }

                    petrolPumpIns.setRegisterBookCertificates(registerBookCertificates);

                    PetrolPumpGeneralFindings generalFindings = new PetrolPumpGeneralFindings();


                    generalFindings.setCcCamerasAvail(availCcCameras);
                    generalFindings.setComputerizedSystem(petrolPumpCom);
                    generalFindings.setHygienicCondition(petrolPumpHyg);
                    generalFindings.setFireSafteyAvail(availEqp);
                    if (!lastSocDate.contains("/")) {
                        generalFindings.setSocietyManagerInspectionDate("");
                    } else {
                        generalFindings.setSocietyManagerInspectionDate(lastSocDate);
                    }

                    if (!lastDivDate.contains("/")) {
                        generalFindings.setDivisionManagerInspectionDate("");
                    } else {
                        generalFindings.setDivisionManagerInspectionDate(lastDivDate);
                    }

                    generalFindings.setDivisionManagerInspection(lastInsDiv);
                    generalFindings.setSocietyManagerInspection(lastInsSoc);
                    generalFindings.setRepairsRequired(repairsReq);
                    generalFindings.setRepairsType(repairType);
                    generalFindings.setRemarks(remarks);
                    petrolPumpIns.setGeneralFindings(generalFindings);

                    InspectionSubmitResponse inspectionSubmitResponse = new InspectionSubmitResponse();
                    inspectionSubmitResponse.setPetrolPump(petrolPumpIns);
                    try {
                        editor = sharedPreferences.edit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    editor.putString(AppConstants.repairsPath, FilePath);
                    editor.putString(AppConstants.TOTAL_PHYVAL, String.valueOf(physVal));
                    editor.putString(AppConstants.TOTAL_SYSVAL, String.valueOf(sysVal));
                    String inspectionDetails = gson.toJson(inspectionSubmitResponse);
                    editor.putString(AppConstants.InspectionDetails, inspectionDetails);
                    editor.putString(AppConstants.randomNum, randomNum);

                    finalStockDetailsResponse = new PetrolStockDetailsResponse();
                    finalStockDetailsResponse.setCommonCommodities(finalPetCom);

                    String stockData = gson.toJson(finalStockDetailsResponse);
                    editor.putString(AppConstants.finalStockData, stockData);

                    editor.commit();

                    startActivity(new Intent(PetrolPumpFindingsActivity.this, PetrolPumpPhotoActivity.class)
                            .putExtra(AppConstants.TITLE, getString(R.string.petrol_upload_photos)));
                }
            }
        });
    }

    private boolean validate() {
        boolean returnFlag = true;
        if (TextUtils.isEmpty(deficitReason)) {
            returnFlag = false;
            ScrollToViewEditText(binding.etReason,  getString(R.string.ent_reason));
        } else if (TextUtils.isEmpty(stockReg)) {
            returnFlag = false;
            ScrollToView(binding.rgStock);
            showSnackBar( getString(R.string.chk_stock_reg_entries));
        } else if (TextUtils.isEmpty(purchaseReg)) {
            returnFlag = false;
            ScrollToView(binding.rgPurchase);
            showSnackBar( getString(R.string.chk_purchase_reg));
        } else if (TextUtils.isEmpty(dailysales)) {
            returnFlag = false;
            ScrollToView(binding.rgSale);
            showSnackBar( getString(R.string.chk_dai_sales_reg));
        } else if (TextUtils.isEmpty(godownLiaReg)) {
            returnFlag = false;
            ScrollToView(binding.rgGodown);
            showSnackBar( getString(R.string.chk_godown_lia_reg));
        } else if (TextUtils.isEmpty(cashbook)) {
            returnFlag = false;
            ScrollToView(binding.rgCash);
            showSnackBar( getString(R.string.chk_cash_book));
        } else if (TextUtils.isEmpty(remittance)) {
            returnFlag = false;
            ScrollToView(binding.rgRemittanceCash);
            showSnackBar(getString(R.string.dai_rem_soc));
        } else if (remittance.equalsIgnoreCase(AppConstants.No) && TextUtils.isEmpty(remittanceCash)) {
            returnFlag = false;
            ScrollToView(binding.rgRemittanceCash);
            showSnackBar(getString(R.string.dai_rem_soc));
        } else if (TextUtils.isEmpty(insCer)) {
            returnFlag = false;
            ScrollToView(binding.insLl);
            showSnackBar( getString(R.string.chk_ins_cer));
        } else if (insCer.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(insComName)) {
            returnFlag = false;
            ScrollToViewEditText(binding.etComName,  getString(R.string.ent_ins_com));
        } else if (insCer.equalsIgnoreCase(AppConstants.Yes) && !insComDate.contains("/")) {
            returnFlag = false;
            showSnackBar( getString(R.string.sel_ins_val));
            ScrollToView(binding.etInsDate);
        } else if (TextUtils.isEmpty(fireNOC)) {
            returnFlag = false;
            showSnackBar( getString(R.string.chk_fire_noc));
            ScrollToView(binding.rgFireNoc);
        } else if (TextUtils.isEmpty(weightMea)) {
            returnFlag = false;
            showSnackBar( getString(R.string.wei_val));
            ScrollToView(binding.rgWeight);
        } else if (weightMea.equalsIgnoreCase(AppConstants.Yes) && !weightDate.contains("/")) {
            returnFlag = false;
            showSnackBar(getString(R.string.wei_val));
            ScrollToView(binding.etLegalMetDate);
        } else if (TextUtils.isEmpty(petrolPumpCom)) {
            returnFlag = false;
            showSnackBar( getString(R.string.chk_godown_computerized));
            ScrollToView(binding.rgPetrolPumpComputerized);
        } else if (TextUtils.isEmpty(petrolPumpHyg)) {
            returnFlag = false;
            showSnackBar( getString(R.string.chk_godown_hyg));
            ScrollToView(binding.rgPetrolPumpMaintained);
        } else if (TextUtils.isEmpty(availEqp)) {
            returnFlag = false;
            showSnackBar( getString(R.string.ava_fire_safety));
            ScrollToView(binding.rgAvailEquipment);
        } else if (TextUtils.isEmpty(availCcCameras)) {
            returnFlag = false;
            showSnackBar( getString(R.string.chk_cc_cam));
            ScrollToView(binding.rgAvailCcCameras);
        } else if (TextUtils.isEmpty(lastInsSoc)) {
            returnFlag = false;
            showSnackBar(getString(R.string.chk_last_insp_date));
            ScrollToView(binding.rgInspDateSocManager);
        } else if (lastInsSoc.equalsIgnoreCase(AppConstants.Yes) && !lastSocDate.contains("/")) {
            returnFlag = false;
            showSnackBar(getString(R.string.sel_soc_last_insp_date));
            ScrollToView(binding.rgInspDateSocManager);
        } else if (TextUtils.isEmpty(lastInsDiv)) {
            returnFlag = false;
            showSnackBar(getString(R.string.div_last_ins_date));
        } else if (lastInsDiv.equalsIgnoreCase(AppConstants.Yes) && !lastDivDate.contains("/")) {
            returnFlag = false;
            showSnackBar(getString(R.string.se_div_man_ins_date));
            ScrollToView(binding.lastInsDivDate);
        } else if (TextUtils.isEmpty(repairsReq)) {
            returnFlag = false;
            ScrollToView(binding.rgRepairsReq);
            showSnackBar(getString(R.string.chk_rep_req));
        } else if (repairsReq.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(repairType)) {
            returnFlag = false;
            ScrollToViewEditText(binding.etRepairType, getString(R.string.ent_rep_type));
            ScrollToView(binding.etRepairType);
        } else if (repairsReq.equalsIgnoreCase(AppConstants.Yes) && repairsFlag == 0) {
            returnFlag = false;
            showSnackBar(getString(R.string.cap_rep_photo));
            ScrollToView(binding.ivRepairs);
        } else if (TextUtils.isEmpty(remarks)) {
            returnFlag = false;
            showSnackBar(getString(R.string.rem_insp_officer));
            ScrollToView(binding.etRemarks);
        }
        return returnFlag;
    }

    private void ScrollToView(View view) {
        view.getParent().requestChildFocus(view, view);
    }

    private void ScrollToViewEditText(View view, String reason) {
        CustomFontEditText editText = (CustomFontEditText) view;
        editText.setError(reason);
        editText.requestFocus();
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
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
                        binding.etInsDate.setText(checkUpDate);
                        insComDate = checkUpDate;

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void legalMetDateSelection() {
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
                        binding.etLegalMetDate.setText(checkUpDate);
                        weightDate = checkUpDate;

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    private void lastInsSocDateSelection() {
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
                        binding.lastInsSocDate.setText(checkUpDate);
                        lastSocDate = checkUpDate;

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }


    private void lastInsDivDateSelection() {
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
                        binding.lastInsDivDate.setText(checkUpDate);
                        lastDivDate = checkUpDate;

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
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

                binding.ivRepairs.setPadding(0, 0, 0, 0);
                binding.ivRepairs.setBackgroundColor(getResources().getColor(R.color.white));
                file = new File(FilePath);
                Glide.with(PetrolPumpFindingsActivity.this).load(file).into(binding.ivRepairs);
                repairsFlag = 1;

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.user_cancelled_cap), Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.sorry_failed_to_cap), Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        File imageFile = getOutputMediaFile(type);
        Uri imageUri = null;
        if (imageFile != null) {
            imageUri = FileProvider.getUriForFile(
                    PetrolPumpFindingsActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    imageFile);
        }
        return imageUri;
    }

    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            PIC_NAME = PIC_TYPE + "~" + officerID + "~" + divId + "~" + suppId + "~" +
                    Utils.getCurrentDateTimeFormat() + "~" +
                    Utils.getDeviceID(PetrolPumpFindingsActivity.this) + "~" +
                    Utils.getVersionName(PetrolPumpFindingsActivity.this) + "~" + randomNum
                    + ".png";

            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + PIC_TYPE + ".png");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
