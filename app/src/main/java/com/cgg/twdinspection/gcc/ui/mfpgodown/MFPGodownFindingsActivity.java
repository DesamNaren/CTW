package com.cgg.twdinspection.gcc.ui.mfpgodown;

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
import com.cgg.twdinspection.databinding.ActivityGccMfpFindingsBinding;
import com.cgg.twdinspection.gcc.source.inspections.InspectionSubmitResponse;
import com.cgg.twdinspection.gcc.source.inspections.MFPGodowns.MFPGeneralFindings;
import com.cgg.twdinspection.gcc.source.inspections.MFPGodowns.MFPRegisterBookCertificates;
import com.cgg.twdinspection.gcc.source.inspections.MFPGodowns.MfpGodownsInsp;
import com.cgg.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.cgg.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.cgg.twdinspection.gcc.ui.gcc.GCCPhotoActivity;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class MFPGodownFindingsActivity extends LocBaseActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ActivityGccMfpFindingsBinding binding;
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
    private String stockReg, insCer, fireNOC, weightMea;
    private String qualityStock, stockCards, godownHyg, driage, trayAvail, repairsReq;
    private String insComName, insComDate, weightDate, genAvail, remarks, repairType, lastInsDiv;
    private int repairsFlag = 0;
    private String randomNum;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gcc_mfp_findings);
        binding.header.headerTitle.setText(getString(R.string.mfp_godown_ins_off_fin));
        binding.header.ivHome.setVisibility(View.GONE);
        binding.bottomLl.btnNext.setText(getString(R.string.saveandnext));
        randomNum = Utils.getRandomNumberString();
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
        String godownData = sharedPreferences.getString(AppConstants.MFP_DEPOT_DATA, "");
        MFPGoDowns mfpGoDowns = gson.fromJson(godownData, MFPGoDowns.class);
        divId = mfpGoDowns.getDivisionId();
        suppId = mfpGoDowns.getGodownId();

        if (stockDetailsResponse != null) {
            if (stockDetailsResponse.getMfp_commodities() != null && stockDetailsResponse.getMfp_commodities().size() > 0) {
                for (int i = 0; i < stockDetailsResponse.getMfp_commodities().size(); i++) {
                    if(!TextUtils.isEmpty(stockDetailsResponse.getMfp_commodities().get(i).getPhyQuant())) {
                        physVal += Double.parseDouble(stockDetailsResponse.getMfp_commodities().get(i).getPhyQuant());
                    }
                    sysVal += stockDetailsResponse.getMfp_commodities().get(i).getQty() * stockDetailsResponse.getMfp_commodities().get(i).getRate();
                }
            }
        }
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


        binding.rgQualityStock.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.qual_stock_rb_yes) {
                    qualityStock = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.qual_stock_rb_no) {
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


        binding.rgGodownHyg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.godown_hyg_rb_yes) {
                    godownHyg = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.godown_hyg_rb_no) {
                    godownHyg = AppConstants.No;
                }
            }
        });


        binding.rgDraige.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.draige_rb_yes) {
                    driage = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.draige_rb_no) {
                    driage = AppConstants.No;
                }
            }
        });


        binding.rgGodownGenAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.gen_avail_yes_rb) {
                    genAvail = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.gen_avail_no_rb) {
                    genAvail = AppConstants.No;
                }
            }
        });


        binding.rgTray.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.tray_rb_yes) {
                    trayAvail = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.tray_rb_no) {
                    trayAvail = AppConstants.No;
                }
            }
        });


        binding.rgInspDateDivManager.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.insp_date_div_manager_rb_yes) {
                    lastInsDiv = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.insp_date_div_manager_rb_no) {
                    lastInsDiv = AppConstants.No;
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
                insComName = binding.etComName.getText().toString().trim();
                insComDate = binding.etInsDate.getText().toString().trim();
                weightDate = binding.etLegalMetDate.getText().toString().trim();
                repairType = binding.etRepairType.getText().toString().trim();

                if (validate()) {

                    MfpGodownsInsp mfpGodownsInsp = new MfpGodownsInsp();


                    MFPRegisterBookCertificates registerBookCertificates = new MFPRegisterBookCertificates();
                    registerBookCertificates.setMfpStockRegister(stockReg);
                    registerBookCertificates.setInsuranceCertificate(insCer);
                    if (!insComDate.contains("/"))
                        registerBookCertificates.setInsuranceValidity("");
                    else
                        registerBookCertificates.setInsuranceValidity(insComDate);

                    registerBookCertificates.setInsuranceCompany(insComName);
                    registerBookCertificates.setFireNoc(fireNOC);
                    registerBookCertificates.setWeightMeasureCertificate(weightMea);
                    if (!weightDate.contains("/")) {
                        registerBookCertificates.setWeightMeasureValidity("");
                    } else {
                        registerBookCertificates.setWeightMeasureValidity(weightDate);
                    }


                    mfpGodownsInsp.setRegisterBookCertificates(registerBookCertificates);


                    MFPGeneralFindings generalFindings = new MFPGeneralFindings();
                    generalFindings.setStockQualityVerified(qualityStock);
                    generalFindings.setStackCardsDisp(stockCards);
                    generalFindings.setHygienicCondition(godownHyg);
                    generalFindings.setDriagePropSubmitted(driage);
                    generalFindings.setGeneratorAvail(genAvail);
                    generalFindings.setTrayDryerAvail(trayAvail);
                    generalFindings.setDivManagerInspDate(lastInsDiv);
                    generalFindings.setRepairReq(repairsReq);
                    generalFindings.setRepairType(repairType);
                    generalFindings.setRemarks(remarks);

                    mfpGodownsInsp.setGeneralFindings(generalFindings);

                    InspectionSubmitResponse inspectionSubmitResponse = new InspectionSubmitResponse();
                    inspectionSubmitResponse.setMfpGodowns(mfpGodownsInsp);
                    try {
                        editor = sharedPreferences.edit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    editor.putString(AppConstants.repairsPath, FilePath);

                    sysVal = Double.valueOf(String.format("%.2f", sysVal));
                    physVal = Double.valueOf(String.format("%.2f", physVal));

                    editor.putString(AppConstants.TOTAL_PHYVAL, String.valueOf(physVal));
                    editor.putString(AppConstants.TOTAL_SYSVAL, String.valueOf(sysVal));
                    String inspectionDetails = gson.toJson(inspectionSubmitResponse);
                    editor.putString(AppConstants.InspectionDetails, inspectionDetails);
                    editor.putString(AppConstants.randomNum, randomNum);
                    editor.commit();

                    startActivity(new Intent(MFPGodownFindingsActivity.this, GCCPhotoActivity.class)
                            .putExtra(AppConstants.TITLE, getString(R.string.mfp_godown_upload_photos)));
                }
            }
        });
    }

    private boolean validate() {
        boolean returnFlag = true;
        if (TextUtils.isEmpty(stockReg)) {
            returnFlag = false;
            showSnackBar("Please check stock register");
            ScrollToView(binding.rgStock);
        } else if (TextUtils.isEmpty(insCer)) {
            returnFlag = false;
            showSnackBar("Please check insurance certificate");
            ScrollToView(binding.rgInsurance);
        } else if (insCer.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(insComName)) {
            returnFlag = false;
            ScrollToView(binding.rgInsurance);
            ScrollToViewEditText(binding.etComName, "Enter insurance company name");
        } else if (insCer.equalsIgnoreCase(AppConstants.Yes) && !insComDate.contains("/")) {
            returnFlag = false;
            showSnackBar("Enter insurance validity");
            ScrollToView(binding.rgInsurance);
//            ScrollToViewEditText(binding.etInsDate, "Enter insurance validity");
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
            showSnackBar("Enter weight measure validity date");
            ScrollToView(binding.rgWeight);
//            ScrollToViewEditText(binding.etLegalMetDate, "Enter weight measure validity date");
        } else if (TextUtils.isEmpty(qualityStock)) {
            returnFlag = false;
            ScrollToView(binding.rgQualityStock);
            showSnackBar("Please check quality of the stock");
        } else if (TextUtils.isEmpty(stockCards)) {
            returnFlag = false;
            ScrollToView(binding.rgStackCardsDisplayed);
            showSnackBar("Please check stack cards displayed");
        } else if (TextUtils.isEmpty(godownHyg)) {
            returnFlag = false;
            ScrollToView(binding.rgGodownHyg);
            showSnackBar("Please check godown hygienic condition");
        } else if (TextUtils.isEmpty(driage)) {
            returnFlag = false;
            ScrollToView(binding.rgDraige);
            showSnackBar("Please check driage praposal");
        } else if (TextUtils.isEmpty(genAvail)) {
            returnFlag = false;
            ScrollToView(binding.rgGodownGenAvail);
            showSnackBar("Please check generator available");
        } else if (TextUtils.isEmpty(trayAvail)) {
            returnFlag = false;
            ScrollToView(binding.rgTray);
            showSnackBar("Please check tray dryer available");
        } else if (TextUtils.isEmpty(lastInsDiv)) {
            returnFlag = false;
            ScrollToView(binding.rgInspDateDivManager);
            showSnackBar("Please check last inspection date");
        } else if (TextUtils.isEmpty(repairsReq)) {
            returnFlag = false;
            ScrollToView(binding.ivRepairs);
            showSnackBar("Please check repairs required");
        } else if (repairsReq.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(repairType)) {
            returnFlag = false;
            ScrollToViewEditText(binding.etRepairType, "Enter repair type");
        } else if (repairsReq.equalsIgnoreCase(AppConstants.Yes) && repairsFlag == 0) {
            returnFlag = false;
            showSnackBar("Please capture repairs required photo");
            ScrollToView(binding.ivRepairs);
        } else if (TextUtils.isEmpty(remarks)) {
            returnFlag = false;
            showSnackBar("Please enter remarks");
            ScrollToViewEditText(binding.etRemarks, "Please enter remarks");
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
                Glide.with(MFPGodownFindingsActivity.this).load(file).into(binding.ivRepairs);
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
                    MFPGodownFindingsActivity.this,
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
            PIC_NAME = PIC_TYPE + "~" + officerID + "~" + divId + "~" + suppId + "~" + Utils.getCurrentDateTimeFormat() + "~" + Utils.getDeviceID(MFPGodownFindingsActivity.this)
                    + "~" + Utils.getVersionName(MFPGodownFindingsActivity.this) + "~" +randomNum+ ".png";
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
