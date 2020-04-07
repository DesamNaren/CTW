package com.cgg.twdinspection.gcc.ui.drgodown;

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
import com.cgg.twdinspection.BuildConfig;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.custom.CustomFontEditText;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.gcc.source.inspections.InspectionSubmitResponse;
import com.cgg.twdinspection.gcc.source.inspections.godown.DrGodownGeneralFindings;
import com.cgg.twdinspection.gcc.source.inspections.godown.DrGodownInsp;
import com.cgg.twdinspection.gcc.source.inspections.godown.DrGodownRegisterBookCertificates;
import com.cgg.twdinspection.gcc.source.inspections.godown.DrGodownStockDetails;
import com.cgg.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.cgg.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.cgg.twdinspection.gcc.ui.gcc.GCCPhotoActivity;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.cgg.twdinspection.databinding.ActivityGccFindingsBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class DRGodownFindingsActivity extends LocBaseActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ActivityGccFindingsBinding binding;
    String PIC_NAME, PIC_TYPE;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public Uri fileUri;
    public static final String IMAGE_DIRECTORY_NAME = "GCC_IMAGES";
    String FilePath, checkUpDate;
    Bitmap bm;
    File file;
    private String officerID, divId, suppId;
    double physVal = 0, sysVal = 0, difference = 0;
    private StockDetailsResponse stockDetailsResponse;
    private String stockReg, purchaseReg, salePriceFix, godownLiaReg, insCer, fireNOC, weightMea, stockRegEntry, valuesAsperSale;
    private String qualityStock, stockCards, godownCom, godownHyg, availEqp, lastInsSoc, lastInsDiv, repairsReq;
    private String insComName, insComDate, weightDate, lastSocDate, lastDivDate, deficitReason, remarks, repairType;
    private int repairsFlag = 0;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gcc_findings);
        binding.header.headerTitle.setText(getString(R.string.ins_off_fin));
        binding.header.ivHome.setVisibility(View.GONE);
        binding.bottomLl.btnNext.setText(getString(R.string.saveandnext));

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
        stockDetailsResponse = gson.fromJson(stockData, StockDetailsResponse.class);
        String godownData = sharedPreferences.getString(AppConstants.DR_GODOWN_DATA, "");
        DrGodowns drGodown = gson.fromJson(godownData, DrGodowns.class);
        divId = drGodown.getDivisionId();
        suppId = drGodown.getGodownId();

        if (stockDetailsResponse != null) {
            if (stockDetailsResponse.getEssential_commodities() != null && stockDetailsResponse.getEssential_commodities().size() > 0) {
                for (int i = 0; i < stockDetailsResponse.getEssential_commodities().size(); i++) {
                    physVal += Double.parseDouble(stockDetailsResponse.getEssential_commodities().get(i).getPhyQuant());
                    sysVal += stockDetailsResponse.getEssential_commodities().get(i).getQty() * stockDetailsResponse.getEssential_commodities().get(i).getRate();
                }
            }
            if (stockDetailsResponse.getDialy_requirements() != null && stockDetailsResponse.getDialy_requirements().size() > 0) {
                for (int i = 0; i < stockDetailsResponse.getDialy_requirements().size(); i++) {
                    physVal += Double.parseDouble(stockDetailsResponse.getDialy_requirements().get(i).getPhyQuant());
                    sysVal += stockDetailsResponse.getDialy_requirements().get(i).getQty() * stockDetailsResponse.getDialy_requirements().get(i).getRate();
                }
            }
            if (stockDetailsResponse.getEmpties() != null && stockDetailsResponse.getEmpties().size() > 0) {
                for (int i = 0; i < stockDetailsResponse.getEmpties().size(); i++) {
                    physVal += Double.parseDouble(stockDetailsResponse.getEmpties().get(i).getPhyQuant());
                    sysVal += stockDetailsResponse.getEmpties().get(i).getQty() * stockDetailsResponse.getEmpties().get(i).getRate();
                }
            }
            if (stockDetailsResponse.getMfp_commodities() != null && stockDetailsResponse.getMfp_commodities().size() > 0) {
                for (int i = 0; i < stockDetailsResponse.getMfp_commodities().size(); i++) {
                    physVal += Double.parseDouble(stockDetailsResponse.getMfp_commodities().get(i).getPhyQuant());
                    sysVal += stockDetailsResponse.getMfp_commodities().get(i).getQty() * stockDetailsResponse.getMfp_commodities().get(i).getRate();
                }
            }
            if (stockDetailsResponse.getProcessing_units() != null && stockDetailsResponse.getProcessing_units().size() > 0) {
                for (int i = 0; i < stockDetailsResponse.getProcessing_units().size(); i++) {
                    physVal += Double.parseDouble(stockDetailsResponse.getProcessing_units().get(i).getPhyQuant());
                    sysVal += stockDetailsResponse.getProcessing_units().get(i).getQty() * stockDetailsResponse.getProcessing_units().get(i).getRate();
                }
            }
        }

        binding.tvSysVal.setText(String.format("%.2f", sysVal));
        binding.tvPhysVal.setText(String.format("%.2f", physVal));
        binding.tvDiffVal.setText(String.format("%.2f", sysVal - physVal));
        difference = sysVal - physVal;

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
                    salePriceFix = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.sale_rb_no) {
                    salePriceFix = AppConstants.No;
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


        binding.rgStockRegEntriesMade.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.stock_reg_entries_made_yes_rb) {
                    stockRegEntry = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.stock_reg_entries_made_no_rb) {
                    stockRegEntry = AppConstants.No;
                }
            }
        });


        binding.rgValuesAsPerSalePrice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.values_as_per_sale_price_rb_yes) {
                    valuesAsperSale = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.values_as_per_sale_price_rb_no) {
                    valuesAsperSale = AppConstants.No;
                }
            }
        });


        binding.rgQualityVerified.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.quality_verified_rb_yes) {
                    qualityStock = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.quality_verified_rb_no) {
                    qualityStock = AppConstants.No;
                }
            }
        });


        binding.rgStackCardsDisplayed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.stack_cards_displayed_rb_yes) {
                    stockCards = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.stack_cards_displayed_rb_no) {
                    stockCards = AppConstants.No;
                }
            }
        });


        binding.rgGodownComputerized.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.godown_computerized_yes_rb) {
                    godownCom = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.godown_computerized_no_rb) {
                    godownCom = AppConstants.No;
                }
            }
        });


        binding.rgGodownMaintained.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.godown_maintained_rb_yes) {
                    godownHyg = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.godown_maintained_rb_no) {
                    godownHyg = AppConstants.No;
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

                    DrGodownInsp drGodownInsp = new DrGodownInsp();

                    DrGodownStockDetails stockDetails = new DrGodownStockDetails();
                    stockDetails.setStockValueAsPerSystem(sysVal);
                    stockDetails.setStockValueAsPerPhysical(physVal);
                    stockDetails.setDeficit(difference);
                    stockDetails.setDeficitReason(deficitReason);

                    drGodownInsp.setStockDetails(stockDetails);

                    DrGodownRegisterBookCertificates registerBookCertificates = new DrGodownRegisterBookCertificates();
                    registerBookCertificates.setStockRegister(stockReg);
                    registerBookCertificates.setPurchaseRegister(purchaseReg);
                    registerBookCertificates.setSalePriceFixationRegister(salePriceFix);
                    registerBookCertificates.setGodownLiabilityRegister(godownLiaReg);
                    registerBookCertificates.setInsuranceCertificate(insCer);
                    registerBookCertificates.setInsuranceCompany(insComName);
                    if(!insComDate.contains("/")){
                        registerBookCertificates.setInsuranceValidity("");
                    }else {
                        registerBookCertificates.setInsuranceValidity(insComDate);
                    }
                    registerBookCertificates.setFireDeptNoc(fireNOC);
                    registerBookCertificates.setWeightMeasureCertificate(weightMea);
                    if(!weightDate.contains("/")){
                        registerBookCertificates.setWeightMeasureValidity("");
                    }else {
                        registerBookCertificates.setWeightMeasureValidity(weightDate);
                    }



                    drGodownInsp.setRegisterBookCertificates(registerBookCertificates);


                    DrGodownGeneralFindings generalFindings = new DrGodownGeneralFindings();
                    generalFindings.setStockRegisterEntries(stockRegEntry);
                    generalFindings.setValuesAsPerSalePrice(valuesAsperSale);
                    generalFindings.setFireDeptNoc(fireNOC);
                    generalFindings.setWeightMeasureCertificate(weightMea);
                    generalFindings.setWeightMeasureValidity(weightDate);
                    generalFindings.setStockQualityVerified(qualityStock);
                    generalFindings.setStockCardDisplay(stockCards);
                    generalFindings.setComputerizedSystem(godownCom);
                    generalFindings.setHygienicCondition(godownHyg);
                    generalFindings.setFireSafteyAvail(availEqp);
                    if(!lastSocDate.contains("/")){
                        generalFindings.setSocietyManagerInspectionDate("");
                    }else {
                        generalFindings.setSocietyManagerInspectionDate(lastSocDate);
                    }


                    if(!lastDivDate.contains("/")){
                        generalFindings.setDivisionManagerInspectionDate("");
                    }else {
                        generalFindings.setDivisionManagerInspectionDate(lastDivDate);
                    }


                    generalFindings.setDivisionManagerInspection(lastInsDiv);
                    generalFindings.setSocietyManagerInspection(lastInsSoc);
                    generalFindings.setRepairsRequired(repairsReq);
                    generalFindings.setRepairType(remarks);
                    generalFindings.setRemarks(remarks);
                    drGodownInsp.setGeneralFindings(generalFindings);

                    InspectionSubmitResponse inspectionSubmitResponse = new InspectionSubmitResponse();
                    inspectionSubmitResponse.setDrGodown(drGodownInsp);
                    try {
                        editor = sharedPreferences.edit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    editor.putString(AppConstants.repairsPath, FilePath);
                    editor.putString(AppConstants.TOTAL_PHYVAL,String.valueOf(physVal));
                    editor.putString(AppConstants.TOTAL_SYSVAL,String.valueOf(sysVal));String inspectionDetails = gson.toJson(inspectionSubmitResponse);
                    editor.putString(AppConstants.InspectionDetails, inspectionDetails);
                    editor.commit();

                    startActivity(new Intent(DRGodownFindingsActivity.this, GCCPhotoActivity.class));
                }
            }
        });
    }

    private boolean validate() {
        boolean returnFlag = true;
        if (TextUtils.isEmpty(deficitReason)) {
            returnFlag = false;
            ScrollToViewEditText(binding.etReason, "Enter Reason");
        } else if (TextUtils.isEmpty(stockReg)) {
            returnFlag = false;
            showSnackBar("Please check stock register");
        } else if (TextUtils.isEmpty(purchaseReg)) {
            returnFlag = false;
            showSnackBar("Please check purchase register");
        } else if (TextUtils.isEmpty(salePriceFix)) {
            returnFlag = false;
            showSnackBar("Please check sale price fixation register");
        } else if (TextUtils.isEmpty(godownLiaReg)) {
            returnFlag = false;
            showSnackBar("Please check godown liability register");
        } else if (TextUtils.isEmpty(insCer)) {
            returnFlag = false;
            showSnackBar("Please check insurance certificate");
        } else if (insCer.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(insComName)) {
            returnFlag = false;
            ScrollToViewEditText(binding.etComName, "Enter insurance company name");
        } else if (insCer.equalsIgnoreCase(AppConstants.Yes) && !insComDate.contains("/")) {
            returnFlag = false;
            ScrollToViewEditText(binding.etInsDate, "Enter insurance validity");
            ScrollToView(binding.etInsDate);
        } else if (TextUtils.isEmpty(fireNOC)) {
            returnFlag = false;
            showSnackBar("Please check fire department NOC");
            ScrollToView(binding.rgFireNoc);
        } else if (TextUtils.isEmpty(weightMea)) {
            returnFlag = false;
            showSnackBar("Please check weight measure by legal metrology");
            ScrollToView(binding.rgWeight);
        } else if (weightMea.equalsIgnoreCase(AppConstants.Yes) && !weightDate.contains("/")) {
            returnFlag = false;
            ScrollToViewEditText(binding.etLegalMetDate, "Enter weight measure validity date");
            ScrollToView(binding.etLegalMetDate);
        } else if (TextUtils.isEmpty(stockRegEntry)) {
            returnFlag = false;
            showSnackBar("Please check stock register entries");
            ScrollToView(binding.rgStockRegEntriesMade);
        } else if (TextUtils.isEmpty(valuesAsperSale)) {
            returnFlag = false;
            showSnackBar("Please check values mentioned in sale price register");
            ScrollToView(binding.rgValuesAsPerSalePrice);
        } else if (TextUtils.isEmpty(qualityStock)) {
            returnFlag = false;
            showSnackBar("Please check quality of the stock");
            ScrollToView(binding.rgQualityVerified);
        } else if (TextUtils.isEmpty(stockCards)) {
            returnFlag = false;
            showSnackBar("Please check stack cards displayed");
            ScrollToView(binding.rgStackCardsDisplayed);
        } else if (TextUtils.isEmpty(godownCom)) {
            returnFlag = false;
            showSnackBar("Please check godown computerized");
            ScrollToView(binding.rgGodownComputerized);
        } else if (TextUtils.isEmpty(godownHyg)) {
            returnFlag = false;
            showSnackBar("Please check godown hygienic condition");
            ScrollToView(binding.rgGodownMaintained);
        } else if (TextUtils.isEmpty(availEqp)) {
            returnFlag = false;
            showSnackBar("Please check availability of fire saftey");
            ScrollToView(binding.rgAvailEquipment);
        } else if (TextUtils.isEmpty(lastInsSoc)) {
            returnFlag = false;
            showSnackBar("Please check society manager last inspection date");
            ScrollToView(binding.rgInspDateSocManager);
        } else if (lastInsSoc.equalsIgnoreCase(AppConstants.Yes) && !lastSocDate.contains("/")) {
            returnFlag = false;
            ScrollToViewEditText(binding.lastInsSocDate, "Select last inspection date");
            ScrollToView(binding.rgInspDateSocManager);
        } else if (TextUtils.isEmpty(lastInsDiv)) {
            returnFlag = false;
            showSnackBar("Please check division manager last inspection date");
        } else if (lastInsDiv.equalsIgnoreCase(AppConstants.Yes) &&  !lastDivDate.contains("/")) {
            returnFlag = false;
            ScrollToViewEditText(binding.lastInsDivDate, "Select last inspection date");
            ScrollToView(binding.lastInsDivDate);
        } else if (TextUtils.isEmpty(repairsReq)) {
            returnFlag = false;
            showSnackBar("Please check repairs required");
        } else if (repairsReq.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(repairType)) {
            returnFlag = false;
            ScrollToViewEditText(binding.etRepairType, "Enter repair type");
            ScrollToView(binding.ivRepairs);
        }  else if (repairsReq.equalsIgnoreCase(AppConstants.Yes) && repairsFlag == 0) {
            returnFlag = false;
            showSnackBar("Please capture repairs required photo");
            ScrollToView(binding.ivRepairs);
        } else if (TextUtils.isEmpty(remarks)) {
            returnFlag = false;
            showSnackBar("Please enter remarks");
            ScrollToView(binding.etRemarks);
        }
        return returnFlag;
    }

    private void ScrollToView(View view) {
        view.getParent().requestChildFocus(view,view);
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
                binding.ivRepairs.setPadding(0, 0, 0, 0);
                binding.ivRepairs.setBackgroundColor(getResources().getColor(R.color.white));
                file = new File(FilePath);
                Glide.with(DRGodownFindingsActivity.this).load(file).into(binding.ivRepairs);
                repairsFlag = 1;

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
                    DRGodownFindingsActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
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
            PIC_NAME = officerID + "~" + divId + "~" + suppId + "~" + Utils.getCurrentDateTime() + "~" + PIC_TYPE + ".png";
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

    @Override
    public void onBackPressed() {
        finish();
    }
}
