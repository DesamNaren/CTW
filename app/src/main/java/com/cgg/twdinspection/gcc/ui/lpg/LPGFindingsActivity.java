package com.cgg.twdinspection.gcc.ui.lpg;

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
import com.cgg.twdinspection.databinding.ActivityGccPetrolPumpFindingsBinding;
import com.cgg.twdinspection.gcc.source.inspections.InspectionSubmitResponse;
import com.cgg.twdinspection.gcc.source.inspections.lpg.LPGGeneralFindings;
import com.cgg.twdinspection.gcc.source.inspections.lpg.LPGIns;
import com.cgg.twdinspection.gcc.source.inspections.lpg.LPGRegisterBookCertificates;
import com.cgg.twdinspection.gcc.source.inspections.lpg.LPGStockDetails;
import com.cgg.twdinspection.gcc.source.inspections.petrol_pump.PetrolPumpGeneralFindings;
import com.cgg.twdinspection.gcc.source.inspections.petrol_pump.PetrolPumpIns;
import com.cgg.twdinspection.gcc.source.inspections.petrol_pump.PetrolPumpRegisterBookCertificates;
import com.cgg.twdinspection.gcc.source.inspections.petrol_pump.PetrolPumpStockDetails;
import com.cgg.twdinspection.gcc.source.stock.PetrolStockDetailsResponse;
import com.cgg.twdinspection.gcc.source.suppliers.lpg.LPGSupplierInfo;
import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolSupplierInfo;
import com.cgg.twdinspection.gcc.ui.petrolpump.PetrolPumpPhotoActivity;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class LPGFindingsActivity extends LocBaseActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ActivityGccPetrolPumpFindingsBinding binding;
    String PIC_NAME, PIC_TYPE;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public Uri fileUri;
    public static final String IMAGE_DIRECTORY_NAME = "GCC_IMAGES";
    String FilePath, checkUpDate;
    Bitmap bm;
    File file;
    private String officerID, divId, suppId;
    double physVal = 0, sysVal = 0, difference = 0;
    private PetrolStockDetailsResponse stockDetailsResponse;
    private String stockReg, purchaseReg, dailysales, godownLiaReg, cashbook, remittance, remittanceCash, insCer, fireNOC, weightMea;
    private String petrolPumpCom, petrolPumpHyg, availEqp, availCcCameras, lastInsSoc, lastInsDiv, repairsReq;
    private String insComName, insComDate, weightDate, lastSocDate, lastDivDate, deficitReason, remarks, repairType;
    private int repairsFlag = 0;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gcc_petrol_pump_findings);
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
        stockDetailsResponse = gson.fromJson(stockData, PetrolStockDetailsResponse.class);

        String lpgData = sharedPreferences.getString(AppConstants.LPG_DATA, "");
        LPGSupplierInfo lpgSupplierInfo = gson.fromJson(lpgData, LPGSupplierInfo.class);
        divId = lpgSupplierInfo.getDivisionId();
        suppId = lpgSupplierInfo.getGodownId();



        if (stockDetailsResponse != null) {
            if (stockDetailsResponse.getCommonCommodities() != null && stockDetailsResponse.getCommonCommodities().size() > 0) {
                for (int i = 0; i < stockDetailsResponse.getCommonCommodities().size(); i++) {
                    physVal += Double.parseDouble(stockDetailsResponse.getCommonCommodities().get(i).getPhyQuant());
                    sysVal += stockDetailsResponse.getCommonCommodities().get(i).getQty() * stockDetailsResponse.getCommonCommodities().get(i).getRate();
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
                    remittanceCash = "Alternative Days";
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

                    LPGIns lpgIns = new LPGIns();

                    LPGStockDetails stockDetails = new LPGStockDetails();
                    stockDetails.setStockValueAsPerSystem(sysVal);
                    stockDetails.setStockValueAsPerPhysical(physVal);
                    stockDetails.setDeficit(difference);
                    stockDetails.setDeficitReason(deficitReason);

                    lpgIns.setStockDetails(stockDetails);

                    LPGRegisterBookCertificates registerBookCertificates = new LPGRegisterBookCertificates();
                    registerBookCertificates.setStockRegister(stockReg);
                    registerBookCertificates.setPurchaseRegister(purchaseReg);
                    registerBookCertificates.setSalePriceFixationRegister(dailysales);
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

                    lpgIns.setRegisterBookCertificates(registerBookCertificates);

                    LPGGeneralFindings generalFindings = new LPGGeneralFindings();

                    generalFindings.setFireDeptNoc(fireNOC);
                    generalFindings.setWeightMeasureCertificate(weightMea);
                    generalFindings.setWeightMeasureValidity(weightDate);
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
                    generalFindings.setRepairType(remarks);
                    generalFindings.setRemarks(remarks);
                    lpgIns.setGeneralFindings(generalFindings);

                    InspectionSubmitResponse inspectionSubmitResponse = new InspectionSubmitResponse();
                    inspectionSubmitResponse.setLpg(lpgIns);
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
                    editor.commit();
                    startActivity(new Intent(LPGFindingsActivity.this, LpgPhotoActivity.class));
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
            ScrollToView(binding.rgPurchase);
            showSnackBar("Please check stock register");
        } else if (TextUtils.isEmpty(purchaseReg)) {
            returnFlag = false;
            showSnackBar("Please check purchase register");
        } else if (TextUtils.isEmpty(dailysales)) {
            returnFlag = false;
            ScrollToView(binding.rgSale);
            showSnackBar("Please check Daily Sales Register");
        } else if (TextUtils.isEmpty(godownLiaReg)) {
            returnFlag = false;
            ScrollToView(binding.rgRemittanceCash);
            showSnackBar("Please check godown liability register");
        } else if (TextUtils.isEmpty(cashbook)) {
            returnFlag = false;
            ScrollToView(binding.rgCash);
            showSnackBar("Please check Cash book");
        } else if (TextUtils.isEmpty(remittance)) {
            returnFlag = false;
            ScrollToView(binding.rgRemittanceCash);
            showSnackBar("Please check daily Remittance of Cash to society office");
        } else if (remittance.equalsIgnoreCase(AppConstants.No) && TextUtils.isEmpty(remittanceCash)) {
            returnFlag = false;
            ScrollToView(binding.rgRemittanceCash);
            showSnackBar("Please check daily Remittance of Cash to society office");
        } else if (TextUtils.isEmpty(insCer)) {
            returnFlag = false;
            ScrollToView(binding.insLl);
            showSnackBar("Please check insurance certificate");
        } else if (insCer.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(insComName)) {
            returnFlag = false;
            ScrollToViewEditText(binding.etComName, "Enter insurance company name");
        } else if (insCer.equalsIgnoreCase(AppConstants.Yes) && !insComDate.contains("/")) {
            returnFlag = false;

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

            ScrollToView(binding.etLegalMetDate);
        } else if (TextUtils.isEmpty(petrolPumpCom)) {
            returnFlag = false;
            showSnackBar("Please check godown computerized");
            ScrollToView(binding.rgPetrolPumpComputerized);
        } else if (TextUtils.isEmpty(petrolPumpHyg)) {
            returnFlag = false;
            showSnackBar("Please check godown hygienic condition");
            ScrollToView(binding.rgPetrolPumpMaintained);
        } else if (TextUtils.isEmpty(availEqp)) {
            returnFlag = false;
            showSnackBar("Please check availability of fire safety equipment");
            ScrollToView(binding.rgAvailEquipment);
        } else if (TextUtils.isEmpty(availCcCameras)) {
            returnFlag = false;
            showSnackBar("Please check availability of CC Cameras");
            ScrollToView(binding.rgAvailCcCameras);
        } else if (TextUtils.isEmpty(lastInsSoc)) {
            returnFlag = false;
            showSnackBar("Please check society manager last inspection date");
            ScrollToView(binding.rgInspDateSocManager);
        } else if (lastInsSoc.equalsIgnoreCase(AppConstants.Yes) && !lastSocDate.contains("/")) {
            returnFlag = false;

            ScrollToView(binding.rgInspDateSocManager);
        } else if (TextUtils.isEmpty(lastInsDiv)) {
            returnFlag = false;
            showSnackBar("Please check division manager last inspection date");
        } else if (lastInsDiv.equalsIgnoreCase(AppConstants.Yes) && !lastDivDate.contains("/")) {
            returnFlag = false;

            ScrollToView(binding.lastInsDivDate);
        } else if (TextUtils.isEmpty(repairsReq)) {
            returnFlag = false;
            showSnackBar("Please check repairs required");
        } else if (repairsReq.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(repairType)) {
            returnFlag = false;
            ScrollToViewEditText(binding.etRepairType, "Enter repair type");
            ScrollToView(binding.ivRepairs);
        } else if (repairsReq.equalsIgnoreCase(AppConstants.Yes) && repairsFlag == 0) {
            returnFlag = false;
            showSnackBar("Please capture repairs required photo");
            ScrollToView(binding.ivRepairs);
        } else if (TextUtils.isEmpty(remarks)) {
            returnFlag = false;
            showSnackBar("Please enter remarks of inspection officer");
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

                binding.ivRepairs.setPadding(0, 0, 0, 0);
                binding.ivRepairs.setBackgroundColor(getResources().getColor(R.color.white));
                file = new File(FilePath);
                Glide.with(LPGFindingsActivity.this).load(file).into(binding.ivRepairs);
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
                    LPGFindingsActivity.this,
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

    @Override
    public void onBackPressed() {
        finish();
    }
}