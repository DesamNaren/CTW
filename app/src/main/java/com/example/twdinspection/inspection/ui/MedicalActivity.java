package com.example.twdinspection.inspection.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.custom.CustomFontEditText;
import com.example.twdinspection.common.custom.CustomFontTextView;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityMedicalBinding;
import com.example.twdinspection.inspection.interfaces.SaveListener;
import com.example.twdinspection.inspection.source.MedicalDetailsBean;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;
import com.example.twdinspection.inspection.viewmodel.CallHealthCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.CallHealthViewModel;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.example.twdinspection.inspection.viewmodel.MedicalCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.MedicalDetailsViewModel;
import com.example.twdinspection.inspection.viewmodel.MedicalViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;

public class MedicalActivity extends BaseActivity implements SaveListener {
    ActivityMedicalBinding binding;
    MedicalViewModel medicalViewModel;
    MedicalInfoEntity medicalInfoEntity;
    String recorderedInRegister, medicalCheckUpDoneByWhom, anmWeeklyUpdated, callHealth100;
    private int slNoCnt = 0;
    private int tot_cnt;
    private MedicalDetailsViewModel medicalDetailsViewModel;
    private int feverCount, coldCount, headacheCount, diarrheaCount, malariaCount, othersCount;
    private String checkUpDate;
    private List<MedicalDetailsBean> medicalDetailsBeans;
    private List<CallHealthInfoEntity> callHealthInfoEntities;
    private CallHealthViewModel callHealthViewModel;
    InstMainViewModel instMainViewModel;
    private String instID, officerID, insTime;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_medical, getResources().getString(R.string.medical_health));
        medicalDetailsViewModel = new MedicalDetailsViewModel(getApplication());
        instMainViewModel = new InstMainViewModel(getApplication());

        callHealthViewModel = ViewModelProviders.of(MedicalActivity.this,
                new CallHealthCustomViewModel(this)).get(CallHealthViewModel.class);

        medicalViewModel = ViewModelProviders.of(MedicalActivity.this,
                new MedicalCustomViewModel(binding, this, getApplication())).get(MedicalViewModel.class);
        binding.setViewModel(medicalViewModel);

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
            insTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");
            instID = sharedPreferences.getString(AppConstants.INST_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                if (selctedItem == R.id.medical_checkup_details_yes) {
                    if (tot_cnt > 0 && tot_cnt == medicalDetailsBeans.size()) {
                        binding.btnMedicalView.setVisibility(View.VISIBLE);
                    }

                    binding.btnAddStud.setVisibility(View.VISIBLE);
                    recorderedInRegister = "YES";
                } else if (selctedItem == R.id.medical_checkup_details_no) {
                    binding.btnAddStud.setVisibility(View.GONE);
                    binding.btnMedicalView.setVisibility(View.GONE);
                    recorderedInRegister = "NO";
                } else {
                    binding.btnAddStud.setVisibility(View.GONE);
                    recorderedInRegister = null;
                }
            }
        });

        binding.rgMedicalCheckUpDoneByWhom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMedicalCheckUpDoneByWhom.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_medical_officer)
                    medicalCheckUpDoneByWhom = "MEDICAL OFFICER";
                else if (selctedItem == R.id.rb_anm)
                    medicalCheckUpDoneByWhom = "ANM";
                else medicalCheckUpDoneByWhom = null;
            }
        });

        binding.rgAnmWeeklyUpdated.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAnmWeeklyUpdated.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_anm_weekly_updated)
                    anmWeeklyUpdated = "YES";
                else if (selctedItem == R.id.rb_no_anm_weekly_updated)
                    anmWeeklyUpdated = "NO";
                else anmWeeklyUpdated = null;
            }
        });

        binding.rgCallHealth100.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCallHealth100.getCheckedRadioButtonId();
                if (selctedItem == R.id.yes_call_health_100)
                    callHealth100 = "YES";
                else if (selctedItem == R.id.no_call_health_100)
                    callHealth100 = "NO";
                else callHealth100 = null;
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int f_cnt = 0, c_cnt = 0, h_cnt = 0, d_cnt = 0, m_cnt = 0, o_cnt = 0;
                int tot_cnt = 0;


                if (!TextUtils.isEmpty(binding.etFever.getText())) {
                    f_cnt = Integer.valueOf(binding.etFever.getText().toString());
                }

                if (!TextUtils.isEmpty(binding.etCold.getText())) {
                    c_cnt = Integer.valueOf(binding.etCold.getText().toString());
                }

                if (!TextUtils.isEmpty(binding.etHeadache.getText())) {
                    h_cnt = Integer.valueOf(binding.etHeadache.getText().toString());
                }

                if (!TextUtils.isEmpty(binding.etDiarrhea.getText())) {
                    d_cnt = Integer.valueOf(binding.etDiarrhea.getText().toString());
                }

                if (!TextUtils.isEmpty(binding.etMalaria.getText())) {
                    m_cnt = Integer.valueOf(binding.etMalaria.getText().toString());
                }
                if (!TextUtils.isEmpty(binding.etOthers.getText())) {
                    o_cnt = Integer.valueOf(binding.etOthers.getText().toString());
                }

                tot_cnt = f_cnt + c_cnt + h_cnt + d_cnt + m_cnt + o_cnt;


                if (validateData(tot_cnt)) {
                    medicalInfoEntity = new MedicalInfoEntity();
                    medicalInfoEntity.setFeverCount(f_cnt);
                    medicalInfoEntity.setColdCount(c_cnt);
                    medicalInfoEntity.setHeadacheCount(h_cnt);
                    medicalInfoEntity.setDiarrheaCount(d_cnt);
                    medicalInfoEntity.setMalariaCount(m_cnt);
                    medicalInfoEntity.setOthersCount(o_cnt);
                    medicalInfoEntity.setLast_medical_checkup_date(checkUpDate);
                    medicalInfoEntity.setMedicalCheckUpDoneByWhom(medicalCheckUpDoneByWhom);
                    medicalInfoEntity.setAnmWeeklyUpdated(anmWeeklyUpdated);
                    medicalInfoEntity.setCallHealth100(callHealth100);
                    medicalInfoEntity.setRecorded_in_register(recorderedInRegister);
                    if (callHealthInfoEntities != null && callHealthInfoEntities.size() > 0) {
                        medicalInfoEntity.setCallHealthInfoEntities(callHealthInfoEntities);
                    }
                    if (recorderedInRegister.equals(AppConstants.Yes)) {
                        medicalInfoEntity.setMedicalDetails(medicalDetailsBeans);
                    }


                    Utils.customSaveAlert(MedicalActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));
                }


            }
        });

        binding.btnAddCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCallHeathDetails();
            }
        });
        binding.btnAddStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feverCount = 0;
                coldCount = 0;
                diarrheaCount = 0;
                headacheCount = 0;
                malariaCount = 0;
                othersCount = 0;

                if (!TextUtils.isEmpty(binding.etFever.getText().toString())) {
                    feverCount = Integer.valueOf(binding.etFever.getText().toString());
                }
                if (!TextUtils.isEmpty(binding.etCold.getText().toString())) {
                    coldCount = Integer.valueOf(binding.etCold.getText().toString());
                }
                if (!TextUtils.isEmpty(binding.etDiarrhea.getText().toString())) {
                    diarrheaCount = Integer.valueOf(binding.etDiarrhea.getText().toString());
                }
                if (!TextUtils.isEmpty(binding.etHeadache.getText().toString())) {
                    headacheCount = Integer.valueOf(binding.etHeadache.getText().toString());
                }
                if (!TextUtils.isEmpty(binding.etMalaria.getText().toString())) {
                    malariaCount = Integer.valueOf(binding.etMalaria.getText().toString());
                }
                if (!TextUtils.isEmpty(binding.etOthers.getText().toString())) {
                    othersCount = Integer.valueOf(binding.etOthers.getText().toString());
                }

                tot_cnt = feverCount + coldCount + diarrheaCount + headacheCount + malariaCount + othersCount;
                if (tot_cnt > 0) {


                    startActivity(new Intent(MedicalActivity.this, MedicalDetailsActivity.class)
                            .putExtra("f_cnt", feverCount)
                            .putExtra("c_cnt", coldCount)
                            .putExtra("d_cnt", diarrheaCount)
                            .putExtra("h_cnt", headacheCount)
                            .putExtra("m_cnt", malariaCount)
                            .putExtra("o_cnt", othersCount)
                            .putExtra("tot_cnt", tot_cnt)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {
                    showBottomSheetSnackBar(getResources().getString(R.string.enter_suffering_count));
                }
            }
        });

        binding.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCallData();
            }
        });
        binding.btnMedicalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMedicalData();
            }
        });

        LiveData<Integer> liveData = medicalViewModel.getCallCnt();
        liveData.observe(MedicalActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer cnt) {
                if (cnt != null && cnt > 0) {
                    binding.btnView.setVisibility(View.VISIBLE);
                    slNoCnt = cnt;
                } else {
                    slNoCnt = 0;
                    binding.btnView.setVisibility(View.GONE);
                }
            }
        });
    }

    private boolean validateData(int tot_cnt) {
        if (TextUtils.isEmpty(checkUpDate)) {
            showBottomSheetSnackBar(getResources().getString(R.string.last_medical_date));
            return false;
        } else if (TextUtils.isEmpty(medicalCheckUpDoneByWhom)) {
            showBottomSheetSnackBar(getResources().getString(R.string.sel_medical_check_up_done_by_whom));
            return false;
        } else if (TextUtils.isEmpty(anmWeeklyUpdated)) {
            showBottomSheetSnackBar(getResources().getString(R.string.sel_tabs_of_anm_weekly_updated));
            return false;
        } else if (TextUtils.isEmpty(callHealth100)) {
            showBottomSheetSnackBar(getResources().getString(R.string.sel_call_health_100));
            return false;
        } else if (TextUtils.isEmpty(recorderedInRegister)) {
            showBottomSheetSnackBar(getResources().getString(R.string.sel_visitor_register));
            return false;
        } else if (recorderedInRegister.equals(AppConstants.Yes) && tot_cnt == 0) {
            showBottomSheetSnackBar(getResources().getString(R.string.enter_suffering_count));
            return false;
        } else if (recorderedInRegister.equals(AppConstants.Yes) &&
                tot_cnt > 0 && medicalDetailsBeans != null &&
                tot_cnt != medicalDetailsBeans.size()) {
            showBottomSheetSnackBar(getResources().getString(R.string.not_match));
            return false;
        }
        return true;
    }

    private void viewCallData() {
        startActivity(new Intent(this, CallHealthActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private void viewMedicalData() {
        startActivity(new Intent(this, ViewMedicalActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
    }


    public void showCallHeathDetails() {
        View view = getLayoutInflater().inflate(R.layout.call_health_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(MedicalActivity.this);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        LinearLayout ll_entries = view.findViewById(R.id.ll_entries);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(ll_entries);
        bottomSheetBehavior.setPeekHeight(1500);
        dialog.show();

        CustomFontTextView slNo = view.findViewById(R.id.tv_slCount);
        slNo.setText(String.valueOf(slNoCnt + 1));
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
                        callHealthInfoEntity.setSlNo(slNoCnt + 1);
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
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
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
                        checkUpDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        binding.etMedicalCheckupDate.setText(checkUpDate);

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

    @Override
    protected void onResume() {
        super.onResume();
        medicalDetailsViewModel.getMedicalDetails().observe(this, new Observer<List<MedicalDetailsBean>>() {
            @Override
            public void onChanged(List<MedicalDetailsBean> medicalDetailsBeans) {
                MedicalActivity.this.medicalDetailsBeans = medicalDetailsBeans;
                if (medicalDetailsBeans != null && medicalDetailsBeans.size() > 0 && tot_cnt > 0) {
                    int fCnt = 0, cCnt = 0, hCnt = 0, dCnt = 0, mCnt = 0, oCnt = 0;

                    for (int z = 0; z < medicalDetailsBeans.size(); z++) {
                        if (medicalDetailsBeans.get(z).getType().equals("1")) {
                            fCnt++;
                        } else if (medicalDetailsBeans.get(z).getType().equals("2")) {
                            cCnt++;
                        } else if (medicalDetailsBeans.get(z).getType().equals("3")) {
                            hCnt++;
                        } else if (medicalDetailsBeans.get(z).getType().equals("4")) {
                            dCnt++;
                        } else if (medicalDetailsBeans.get(z).getType().equals("5")) {
                            mCnt++;
                        } else if (medicalDetailsBeans.get(z).getType().equals("6")) {
                            oCnt++;
                        }
                    }

                    if (feverCount > 0 && feverCount == fCnt) {
                        binding.ivFever.setBackground(getResources().getDrawable(R.drawable.medical_disable));
                        binding.etFever.setEnabled(false);
                    }
                    if (coldCount > 0 && coldCount == cCnt) {
                        binding.ivCold.setBackground(getResources().getDrawable(R.drawable.medical_disable));
                        binding.etCold.setEnabled(false);
                    }


                    if (headacheCount > 0 && headacheCount == hCnt) {
                        binding.ivHeadache.setBackground(getResources().getDrawable(R.drawable.medical_disable));
                        binding.etHeadache.setEnabled(false);
                    }
                    if (diarrheaCount > 0 && diarrheaCount == dCnt) {
                        binding.ivDiarrhea.setBackground(getResources().getDrawable(R.drawable.medical_disable));
                        binding.etDiarrhea.setEnabled(false);
                    }


                    if (malariaCount > 0 && malariaCount == mCnt) {
                        binding.ivMalaria.setBackground(getResources().getDrawable(R.drawable.medical_disable));
                        binding.etMalaria.setEnabled(false);
                    }
                    if (othersCount > 0 && othersCount == oCnt) {
                        binding.ivOthers.setBackground(getResources().getDrawable(R.drawable.medical_disable));
                        binding.etOthers.setEnabled(false);
                    }

                    if (tot_cnt > 0 && tot_cnt == medicalDetailsBeans.size()) {
                        binding.btnMedicalView.setVisibility(View.VISIBLE);
                    }


                }

            }
        });

        callHealthViewModel = ViewModelProviders.of(MedicalActivity.this,
                new CallHealthCustomViewModel(this)).get(CallHealthViewModel.class);

        callHealthViewModel.getCallHealthData().observe(MedicalActivity.this, new Observer<List<CallHealthInfoEntity>>() {
            @Override
            public void onChanged(List<CallHealthInfoEntity> callHealthInfoEntities) {
                MedicalActivity.this.callHealthInfoEntities = callHealthInfoEntities;
            }
        });
    }

    @Override
    public void submitData() {
        long x = medicalViewModel.insertMedicalInfo(medicalInfoEntity);
        if (x >= 0) {
            long z = 0;
            try {
                z = instMainViewModel.updateSectionInfo(Utils.getCurrentDateTime(), 4, instID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z >= 0) {
                showBottomSheetSnackBar(getString(R.string.data_saved));
                startActivity(new Intent(MedicalActivity.this, DietIssuesActivity.class));
            } else {
                showBottomSheetSnackBar(getString(R.string.failed));
            }
        } else {
            showBottomSheetSnackBar(getString(R.string.failed));
        }
    }

    @Override
    public void onBackPressed() {
        super.callBack();
    }
}
