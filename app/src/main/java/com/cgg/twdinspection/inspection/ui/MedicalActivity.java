package com.cgg.twdinspection.inspection.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.custom.CustomFontEditText;
import com.cgg.twdinspection.common.custom.CustomFontTextView;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityMedicalBinding;
import com.cgg.twdinspection.inspection.interfaces.SaveListener;
import com.cgg.twdinspection.inspection.source.inst_master.MasterClassInfo;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalDetailsBean;
import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;
import com.cgg.twdinspection.inspection.viewmodel.CallHealthCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.CallHealthViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.MedicalCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.MedicalDetailsViewModel;
import com.cgg.twdinspection.inspection.viewmodel.MedicalViewModel;
import com.cgg.twdinspection.inspection.viewmodel.StudentsAttndViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;
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
    private int localFlag;
    String screened_by_call_health, left_for_screening, sickboarders, sickboardersArea;
    StudentsAttndViewModel studentsAttndViewModel;
    private int totalStrength = 0;

    private void ScrollToView(View view) {
        view.getParent().requestChildFocus(view, view);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_medical, getResources().getString(R.string.medical_health));
        medicalDetailsViewModel = new MedicalDetailsViewModel(getApplication());
        instMainViewModel = new InstMainViewModel(getApplication());
        studentsAttndViewModel = new StudentsAttndViewModel(MedicalActivity.this);


        callHealthViewModel = ViewModelProviders.of(MedicalActivity.this,
                new CallHealthCustomViewModel(this)).get(CallHealthViewModel.class);

        medicalViewModel = ViewModelProviders.of(MedicalActivity.this,
                new MedicalCustomViewModel(binding, this, getApplication())).get(MedicalViewModel.class);
        binding.setViewModel(medicalViewModel);

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
            instID = sharedPreferences.getString(AppConstants.INST_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        getTotalInstStrength();

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
                        binding.btnMedicalView.setVisibility(View.GONE);
                    }

                    binding.btnAddStud.setVisibility(View.GONE);
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
                if (selctedItem == R.id.yes_call_health_100) {
                    callHealth100 = "YES";
                    binding.llScreenedByCallHealth.setVisibility(View.VISIBLE);
                    binding.llLeftForScreening.setVisibility(View.GONE);
                    left_for_screening = null;
                } else if (selctedItem == R.id.no_call_health_100) {
                    callHealth100 = "NO";
                    binding.llScreenedByCallHealth.setVisibility(View.GONE);
                    binding.llLeftForScreening.setVisibility(View.VISIBLE);
                    screened_by_call_health = null;
                } else callHealth100 = null;
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sickboarders = binding.etSickboarders.getText().toString().trim();
                sickboardersArea = binding.etSickboardersArea.getText().toString().trim();
                screened_by_call_health = binding.etScreenedByCallHealth.getText().toString().trim();
                left_for_screening = binding.etLeftForScreening.getText().toString().trim();

                int f_cnt = 0;
                int c_cnt = 0;
                int h_cnt = 0;
                int d_cnt = 0;
                int m_cnt = 0;
                int o_cnt = 0;
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
                    medicalInfoEntity.setInspection_time(Utils.getCurrentDateTime());
                    medicalInfoEntity.setOfficer_id(officerID);
                    medicalInfoEntity.setInstitute_id(instID);
                    medicalInfoEntity.setFeverCount(String.valueOf(f_cnt));
                    medicalInfoEntity.setColdCount(String.valueOf(c_cnt));
                    medicalInfoEntity.setHeadacheCount(String.valueOf(h_cnt));
                    medicalInfoEntity.setDiarrheaCount(String.valueOf(d_cnt));
                    medicalInfoEntity.setMalariaCount(String.valueOf(m_cnt));
                    medicalInfoEntity.setOthersCount(String.valueOf(o_cnt));
                    medicalInfoEntity.setLast_medical_checkup_date(checkUpDate);
                    medicalInfoEntity.setMedicalCheckUpDoneByWhom(medicalCheckUpDoneByWhom);
                    medicalInfoEntity.setAnmWeeklyUpdated(anmWeeklyUpdated);
                    medicalInfoEntity.setCallHealth100(callHealth100);
                    medicalInfoEntity.setScreenedByCallHealth(screened_by_call_health);
                    medicalInfoEntity.setLeftForscreening(left_for_screening);
                    medicalInfoEntity.setRecorded_in_register(recorderedInRegister);
                    medicalInfoEntity.setSickBoarders(sickboarders);
                    medicalInfoEntity.setSickBoarders_area(sickboardersArea);

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
                    binding.btnView.setVisibility(View.GONE);
                    slNoCnt = cnt;
                } else {
                    slNoCnt = 0;
                    binding.btnView.setVisibility(View.GONE);
                }
            }
        });


        binding.etFever.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int fevCnt = Integer.valueOf(s.toString());
                    if (fevCnt > totalStrength) {
                        binding.etFever.setText("");
                        binding.etFever.setError(getString(R.string.count_not_exceed_strength));
                        binding.etFever.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etCold.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int fevCnt = Integer.valueOf(s.toString());
                    if (fevCnt > totalStrength) {
                        binding.etCold.setText("");
                        binding.etCold.setError(getString(R.string.count_not_exceed_strength));
                        binding.etCold.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etDiarrhea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int fevCnt = Integer.valueOf(s.toString());
                    if (fevCnt > totalStrength) {
                        binding.etDiarrhea.setText("");
                        binding.etDiarrhea.setError(getString(R.string.count_not_exceed_strength));
                        binding.etDiarrhea.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etHeadache.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int fevCnt = Integer.valueOf(s.toString());
                    if (fevCnt > totalStrength) {
                        binding.etHeadache.setText("");
                        binding.etHeadache.setError(getString(R.string.count_not_exceed_strength));
                        binding.etHeadache.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etMalaria.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int fevCnt = Integer.valueOf(s.toString());
                    if (fevCnt > totalStrength) {
                        binding.etMalaria.setText("");
                        binding.etMalaria.setError(getString(R.string.count_not_exceed_strength));
                        binding.etMalaria.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etOthers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int cnt = Integer.valueOf(s.toString());
                    if (cnt > totalStrength) {
                        binding.etOthers.setText("");
                        binding.etOthers.setError(getString(R.string.count_not_exceed_strength));
                        binding.etOthers.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etLeftForScreening.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int cnt = Integer.valueOf(s.toString());
                    if (cnt > totalStrength) {
                        binding.etLeftForScreening.setText("");
                        binding.etLeftForScreening.setError(getString(R.string.count_not_exceed_strength));
                        binding.etLeftForScreening.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etScreenedByCallHealth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int cnt = Integer.valueOf(s.toString());
                    if (cnt > totalStrength) {
                        binding.etScreenedByCallHealth.setText("");
                        binding.etScreenedByCallHealth.setError(getString(R.string.count_not_exceed_strength));
                        binding.etScreenedByCallHealth.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void getTotalInstStrength() {
        LiveData<MasterInstituteInfo> masterInstituteInfoLiveData = studentsAttndViewModel.getMasterClassInfo(
                instID);
        masterInstituteInfoLiveData.observe(MedicalActivity.this, new Observer<MasterInstituteInfo>() {
            @Override
            public void onChanged(MasterInstituteInfo masterInstituteInfos) {
                masterInstituteInfoLiveData.removeObservers(MedicalActivity.this);
                List<MasterClassInfo> masterClassInfos = masterInstituteInfos.getClassInfo();
                if (masterClassInfos != null && masterClassInfos.size() > 0) {
                    for (int i = 0; i < masterClassInfos.size(); i++) {
                        if (masterClassInfos.get(i).getStudentCount() > 0) {
                            totalStrength += masterClassInfos.get(i).getStudentCount();
                        }
                    }
                }

                binding.totalStrength.setText("Institute overall strength: " + totalStrength);

                try {
                    localFlag = getIntent().getIntExtra(AppConstants.LOCAL_FLAG, -1);
                    if (localFlag == 1) {
                        //get local record & set to data binding
                        LiveData<MedicalInfoEntity> medicalInfo = instMainViewModel.getMedicalInfo();
                        medicalInfo.observe(MedicalActivity.this, new Observer<MedicalInfoEntity>() {
                            @Override
                            public void onChanged(MedicalInfoEntity medicalInfoEntity) {
                                medicalInfo.removeObservers(MedicalActivity.this);
                                if (medicalInfoEntity != null) {
                                    binding.setMedical(medicalInfoEntity);
                                    binding.executePendingBindings();
                                    checkUpDate = binding.etMedicalCheckupDate.getText().toString();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private boolean validateData(int tot_cnt) {

        if (TextUtils.isEmpty(sickboarders) || sickboarders.equals("0")) {
            showBottomSheetSnackBar(getResources().getString(R.string.sel_no_of_sick_boarders));
            binding.etSickboarders.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(sickboardersArea) || sickboardersArea.equals("0")) {
            showBottomSheetSnackBar(getResources().getString(R.string.sel_no_of_sick_boarders_area));
            binding.etSickboardersArea.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(checkUpDate)) {
            ScrollToView(binding.etMedicalCheckupDate);
            showBottomSheetSnackBar(getResources().getString(R.string.last_medical_date));
            return false;
        } else if (TextUtils.isEmpty(medicalCheckUpDoneByWhom)) {
            ScrollToView(binding.rgMedicalCheckUpDoneByWhom);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_medical_check_up_done_by_whom));
            return false;
        } else if (TextUtils.isEmpty(anmWeeklyUpdated)) {
            ScrollToView(binding.rgAnmWeeklyUpdated);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_tabs_of_anm_weekly_updated));
            return false;
        } else if (TextUtils.isEmpty(callHealth100)) {
            ScrollToView(binding.rgCallHealth100);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_call_health_100));
            return false;
        } else if (callHealth100.equalsIgnoreCase("Yes") && TextUtils.isEmpty(screened_by_call_health) || screened_by_call_health.equals("0")) {
            showBottomSheetSnackBar(getResources().getString(R.string.sel_screened_by_call_health));
            binding.etScreenedByCallHealth.requestFocus();
            return false;
        } else if (callHealth100.equalsIgnoreCase("No") && TextUtils.isEmpty(left_for_screening) || left_for_screening.equals("0")) {
            showBottomSheetSnackBar(getResources().getString(R.string.sel_left_for_screening));
            binding.etLeftForScreening.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(recorderedInRegister)) {
            ScrollToView(binding.rgMedicalCheckupDetails);
            showBottomSheetSnackBar(getResources().getString(R.string.sel_visitor_register));
            return false;
        } else if (recorderedInRegister.equals(AppConstants.Yes) && tot_cnt == 0) {
            ScrollToView(binding.etFever);
            showBottomSheetSnackBar(getResources().getString(R.string.enter_suffering_count));
            return false;
        }

//        else if (recorderedInRegister.equals(AppConstants.Yes) &&
//                tot_cnt > 0 && medicalDetailsBeans != null &&
//                tot_cnt != medicalDetailsBeans.size()) {
//            showBottomSheetSnackBar(getResources().getString(R.string.not_match));
//            return false;
//        }
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

    private CoordinatorLayout callCoordinatorLayout;

    public void showCallHeathDetails() {
        View view = getLayoutInflater().inflate(R.layout.call_health_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(MedicalActivity.this);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        callCoordinatorLayout = view.findViewById(R.id.bottom_ll);
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

    private void showCallHealthBottomSheetSnackBar(String str) {
        Snackbar.make(callCoordinatorLayout, str, Snackbar.LENGTH_SHORT).show();
    }

    private void showMediaclBottomSheetSnackBar(String str) {
        Snackbar.make(callCoordinatorLayout, str, Snackbar.LENGTH_SHORT).show();
    }

    private boolean validate(String stuNameStr, String dStr, String dateStr) {
        boolean flag = true;
        if (TextUtils.isEmpty(stuNameStr)) {
            showCallHealthBottomSheetSnackBar(getResources().getString(R.string.enter_stu_name));
            flag = false;
        } else if (TextUtils.isEmpty(dStr)) {
            showCallHealthBottomSheetSnackBar(getResources().getString(R.string.enter_disease));
            flag = false;
        } else if (!dateStr.contains("/")) {
            showCallHealthBottomSheetSnackBar(getResources().getString(R.string.sel_plan_date));
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
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
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
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
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
                        binding.btnMedicalView.setVisibility(View.GONE);
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
            final long[] z = {0};
            try {
                LiveData<Integer> liveData = instMainViewModel.getSectionId("Medical");
                liveData.observe(MedicalActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer id) {
                        liveData.removeObservers(MedicalActivity.this);
                        if (id != null) {
                            z[0] = instMainViewModel.updateSectionInfo(Utils.getCurrentDateTime(), id, instID);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z[0] >= 0) {
                Utils.customSectionSaveAlert(MedicalActivity.this, getString(R.string.data_saved), getString(R.string.app_name));
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
