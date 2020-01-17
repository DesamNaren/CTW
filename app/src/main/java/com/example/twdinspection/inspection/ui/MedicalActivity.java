package com.example.twdinspection.inspection.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.twdinspection.R;
import com.example.twdinspection.common.custom.CustomFontEditText;
import com.example.twdinspection.common.custom.CustomFontTextView;
import com.example.twdinspection.databinding.ActivityMedicalBinding;
import com.example.twdinspection.inspection.source.MedicalAndHealth.CallHealthInfoEntity;
import com.example.twdinspection.inspection.source.MedicalAndHealth.MedicalInfoEntity;
import com.example.twdinspection.inspection.viewmodel.MedicalCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.MedicalViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MedicalActivity extends BaseActivity {
    ActivityMedicalBinding binding;
    MedicalViewModel medicalViewModel;
    MedicalInfoEntity medicalInfoEntity;
    String recorderedInRegister = "", medicalCheckUpDoneByWhom = "", anmWeeklyUpdated = "", callHealth100 = "";
    private CoordinatorLayout rootLayout;
    private int slNoCnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_medical, getResources().getString(R.string.medical_health));

        medicalViewModel = ViewModelProviders.of(MedicalActivity.this,
                new MedicalCustomViewModel(binding, this, getApplication())).get(MedicalViewModel.class);
        binding.setViewModel(medicalViewModel);

        binding.etMedicalCheckupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicalCheckupDateSelection();
            }
        });

        binding.rgMedicalCheckupDetails.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMedicalCheckupDetails.getCheckedRadioButtonId();
                if (selctedItem == R.id.medical_checkup_details_yes)
                    recorderedInRegister = "YES";
                else
                    recorderedInRegister = "NO";
            }
        });

        binding.rgMedicalCheckUpDoneByWhom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMedicalCheckUpDoneByWhom.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_medical_officer)
                    medicalCheckUpDoneByWhom = "MEDICAL OFFICER";
                else
                    medicalCheckUpDoneByWhom = "ANM";
            }
        });

        binding.rgAnmWeeklyUpdated.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAnmWeeklyUpdated.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_anm_weekly_updated)
                    anmWeeklyUpdated = "YES";
                else
                    anmWeeklyUpdated = "NO";
            }
        });

        binding.rgCallHealth100.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCallHealth100.getCheckedRadioButtonId();
                if (selctedItem == R.id.yes_call_health_100)
                    callHealth100 = "YES";
                else
                    callHealth100 = "NO";
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feverCount = binding.etFever.getText().toString().trim();
                String coldCount = binding.etCold.getText().toString().trim();
                String diarrheaCount = binding.etDiarrhea.getText().toString().trim();
                String headacheCount = binding.etHeadache.getText().toString().trim();
                String malariaCount = binding.etMalaria.getText().toString().trim();
                String othersCount = binding.etOthers.getText().toString().trim();
                String CheckupDate = binding.etMedicalCheckupDate.getText().toString().trim();

                if (feverCount.isEmpty())
                    feverCount = "0";
                if (coldCount.isEmpty())
                    coldCount = "0";
                if (diarrheaCount.isEmpty())
                    diarrheaCount = "0";
                if (headacheCount.isEmpty())
                    headacheCount = "0";
                if (malariaCount.isEmpty())
                    malariaCount = "0";
                if (othersCount.isEmpty())
                    othersCount = "0";


                medicalInfoEntity = new MedicalInfoEntity();

                medicalInfoEntity.setFeverCount(feverCount);
                medicalInfoEntity.setColdCount(coldCount);
                medicalInfoEntity.setHeadacheCount(headacheCount);
                medicalInfoEntity.setDiarrheaCount(diarrheaCount);
                medicalInfoEntity.setMalariaCount(malariaCount);
                medicalInfoEntity.setOthersCount(othersCount);
                medicalInfoEntity.setLast_medical_checkup_date(CheckupDate);
                medicalInfoEntity.setRecorded_in_register(recorderedInRegister);
                medicalInfoEntity.setMedicalCheckUpDoneByWhom(medicalCheckUpDoneByWhom);
                medicalInfoEntity.setAnmWeeklyUpdated(anmWeeklyUpdated);
                medicalInfoEntity.setCallHealth100(callHealth100);

                long x = medicalViewModel.insertMedicalInfo(medicalInfoEntity);
//                Toast.makeText(MedicalActivity.this, "Inserted " + x, Toast.LENGTH_SHORT).show();

//                Utils.customAlert(MedicalActivity.this, "Data submitted successfully", AppConstants.SUCCESS, false);

//                if (binding.medicalCheckupDetailsYes.isChecked()) {
//                    startActivity(new Intent(MedicalActivity.this, MedicalDetailsActivity.class));
//                } else {
//                    startActivity(new Intent(MedicalActivity.this, DietIssuesActivity.class));
//                }
                startActivity(new Intent(MedicalActivity.this, DietIssuesActivity.class));

            }
        });

        binding.btnAddCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCallHeathDetails();
            }
        });

        binding.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewCallData();
            }
        });

        LiveData<Integer> liveData = medicalViewModel.getCallCnt();
        liveData.observe(MedicalActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer cnt) {
                if (cnt != null) {
                    slNoCnt = cnt;
                }
            }
        });
    }

    public void showCallHeathDetails() {
        View view = getLayoutInflater().inflate(R.layout.call_health_bottom_sheet, null);
        rootLayout = view.findViewById(R.id.root_layout);
        BottomSheetDialog dialog = new BottomSheetDialog(MedicalActivity.this);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        LinearLayout ll_entries = view.findViewById(R.id.ll_entries);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(ll_entries);
        bottomSheetBehavior.setPeekHeight(1500);
        dialog.show();

        CustomFontTextView slNo = view.findViewById(R.id.tv_slCount);
        slNo.setText(String.valueOf(slNoCnt));
        CustomFontEditText stuName = view.findViewById(R.id.et_studName);
        CustomFontEditText disease = view.findViewById(R.id.et_disease);
        CustomFontEditText date = view.findViewById(R.id.et_call_checkup_date);

        ImageView btn_save = view.findViewById(R.id.btn_save);
        ImageView btn_close = view.findViewById(R.id.ic_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog.isShowing()) {
                    String stuNameStr = stuName.getText().toString().trim();
                    String diseaseStr = disease.getText().toString().trim();
                    String dateStr = date.getText().toString().trim();

                    if (validate(stuNameStr, diseaseStr, dateStr)) {


                        CallHealthInfoEntity callHealthInfoEntity = new CallHealthInfoEntity();
                        callHealthInfoEntity.setSlNo(slNoCnt+1);
                        callHealthInfoEntity.setStu_name(stuNameStr);
                        callHealthInfoEntity.setDisease(diseaseStr);
                        callHealthInfoEntity.setPlanDate(dateStr);

                        long x = medicalViewModel.insertCallInfo(callHealthInfoEntity);
                        dialog.dismiss();
                        if (x >= 0) {
                            showBottomSheetSnackBar(getResources().getString(R.string.data_saved));
                        }
                    }


                }
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDateSelection(date);
            }
        });

    }


    private void showBottomSheetSnackBar(String str) {
        Snackbar.make(rootLayout, str, Snackbar.LENGTH_SHORT).show();
    }

    private boolean validate(String stuNameStr, String dStr, String dateStr) {
        boolean flag = true;
        if (TextUtils.isEmpty(stuNameStr)) {
            showBottomSheetSnackBar(getResources().getString(R.string.enter_stu_name));
            flag = false;
        } else if (TextUtils.isEmpty(dStr)) {
            showBottomSheetSnackBar(getResources().getString(R.string.enter_disease));
            flag = false;
        } else if (!dateStr.contains("/")) {
            showBottomSheetSnackBar(getResources().getString(R.string.sel_plan_date));
            flag = false;
        }
        return flag;
    }

    private void medicalCheckupDateSelection() {
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

                        binding.etMedicalCheckupDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void callDateSelection(CustomFontEditText dateSel) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateSel.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}
