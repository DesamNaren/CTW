package com.cgg.twdinspection.inspection.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.custom.CustomFontEditText;
import com.cgg.twdinspection.common.custom.CustomFontTextView;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityStudentsAttendanceBinding;
import com.cgg.twdinspection.inspection.adapter.StudentsAttAdapter;
import com.cgg.twdinspection.inspection.interfaces.SaveListener;
import com.cgg.twdinspection.inspection.interfaces.StudAttendInterface;
import com.cgg.twdinspection.inspection.source.inst_master.MasterClassInfo;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.source.student_attendence_info.StudAttendInfoEntity;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.StudAttndCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.StudentsAttndViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class StudentsAttendActivity extends BaseActivity implements StudAttendInterface, SaveListener {
    ActivityStudentsAttendanceBinding binding;
    StudentsAttAdapter adapter;
    BottomSheetDialog dialog;
    StudentsAttndViewModel studentsAttndViewModel;
    MasterInstituteInfo masterInstituteInfos;
    List<StudAttendInfoEntity> studAttendInfoEntityListMain;
    String IsattenMarked, count_reg, count_during_insp, variance;
    CustomFontEditText et_studMarkedPres, et_studPresInsp;
    LinearLayout ll_stud_pres,ll_variance;
    InstMainViewModel instMainViewModel;
    CoordinatorLayout bottom_ll;
    SharedPreferences sharedPreferences;
    String instId, officerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_students_attendance, getResources().getString(R.string.stu_att));

        studentsAttndViewModel =
                ViewModelProviders.of(StudentsAttendActivity.this,
                        new StudAttndCustomViewModel(binding, this, getApplication())).get(StudentsAttndViewModel.class);
        instMainViewModel = new InstMainViewModel(getApplication());
        studAttendInfoEntityListMain = new ArrayList<>();
        binding.setViewModel(studentsAttndViewModel);
        binding.btnLayout.btnPrevious.setVisibility(View.GONE);
        sharedPreferences = TWDApplication.get(this).getPreferences();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");


        studentsAttndViewModel.getClassInfo(instId).observe(StudentsAttendActivity.this, new Observer<List<StudAttendInfoEntity>>() {
            @Override
            public void onChanged(List<StudAttendInfoEntity> studAttendInfoEntityList) {
                if (studAttendInfoEntityList != null && studAttendInfoEntityList.size() > 0) {
                    studAttendInfoEntityListMain = studAttendInfoEntityList;
                    adapter = new StudentsAttAdapter(StudentsAttendActivity.this, studAttendInfoEntityListMain);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(StudentsAttendActivity.this));
                    binding.recyclerView.setAdapter(adapter);
                } else {
                    LiveData<MasterInstituteInfo> masterInstituteInfoLiveData = studentsAttndViewModel.getMasterClassInfo(
                            instId);
                    masterInstituteInfoLiveData.observe(StudentsAttendActivity.this, new Observer<MasterInstituteInfo>() {
                        @Override
                        public void onChanged(MasterInstituteInfo masterInstituteInfos) {
                            masterInstituteInfoLiveData.removeObservers(StudentsAttendActivity.this);
                            if(masterInstituteInfos!=null) {
                                StudentsAttendActivity.this.masterInstituteInfos = masterInstituteInfos;
                                List<MasterClassInfo> masterClassInfos = masterInstituteInfos.getClassInfo();
                                if (masterClassInfos != null && masterClassInfos.size() > 0) {
                                    for (int i = 0; i < masterClassInfos.size(); i++) {
                                        if (masterClassInfos.get(i).getStudentCount() > 0) {
                                            StudAttendInfoEntity studAttendInfoEntity = new StudAttendInfoEntity(officerId, 0,
                                                    instId, masterClassInfos.get(i).getType(),
                                                    String.valueOf(masterClassInfos.get(i).getClassId()),
                                                    String.valueOf(masterClassInfos.get(i).getStudentCount()));
                                            studAttendInfoEntityListMain.add(studAttendInfoEntity);
                                        }
                                    }
                                }

                                if (studAttendInfoEntityListMain != null && studAttendInfoEntityListMain.size() > 0) {
                                    studentsAttndViewModel.insertClassInfo(studAttendInfoEntityListMain);
                                } else {
                                    binding.emptyView.setVisibility(View.VISIBLE);
                                    binding.recyclerView.setVisibility(View.GONE);
                                }

                            }else{
                                binding.emptyView.setVisibility(View.VISIBLE);
                                binding.recyclerView.setVisibility(View.GONE);
                            }
                        }
                    });
                }

            }
        });


        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (studAttendInfoEntityListMain.size() == 0) {
                    startActivity(new Intent(StudentsAttendActivity.this, StaffAttendActivity.class));
                } else {
                    if (validateSubmit()) {
                        Utils.customSaveAlert(StudentsAttendActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));
                    } else {
                        showSnackBar("Please inspect all the classes");
                    }
                }
            }
        });
    }

    private boolean validateSubmit() {
        boolean returnFlag = true;
        for (int i = 0; i < studAttendInfoEntityListMain.size(); i++) {
            if (!(studAttendInfoEntityListMain.get(i).getFlag_completed() == 1))
                returnFlag = false;
        }
        return returnFlag;
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.root, str, Snackbar.LENGTH_SHORT).show();
    }

    private void showBottomSheetSnackBar(String str) {
        Snackbar.make(bottom_ll, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void openBottomSheet(StudAttendInfoEntity studAttendInfoEntity) {
        showContactDetails(studAttendInfoEntity);
    }

    public void showContactDetails(StudAttendInfoEntity studAttendInfoEntity) {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        bottom_ll = view.findViewById(R.id.bottom_ll);
        dialog = new BottomSheetDialog(StudentsAttendActivity.this);
        dialog.setContentView(view);
        dialog.setCancelable(true);

        ImageView ic_close = view.findViewById(R.id.ic_close);
        RadioGroup rg_IsAttndMarked_1_2 = view.findViewById(R.id.rg_IsAttndMarked_1_2);
        TextView tv_classType = view.findViewById(R.id.tv_classType);
        CustomFontTextView tv_classCount = view.findViewById(R.id.tv_classCount);
        RadioButton rb_attMark_yes = view.findViewById(R.id.rb_yes);
        RadioButton rb_attMark_yno = view.findViewById(R.id.rb_no);
        et_studPresInsp = view.findViewById(R.id.et_studPresInsp);
        et_studMarkedPres = view.findViewById(R.id.et_studMarkedPres);
        CustomFontTextView tv_variance = view.findViewById(R.id.tv_variance);
        ImageView btn_save = view.findViewById(R.id.btn_save);
        ll_stud_pres = view.findViewById(R.id.ll_stud_pres);
        ll_variance = view.findViewById(R.id.ll_variance);

        if (studAttendInfoEntity.getAttendence_marked() != null) {
            if (studAttendInfoEntity.getAttendence_marked().equals(AppConstants.Yes)) {
                ll_stud_pres.setVisibility(View.VISIBLE);
                rb_attMark_yes.setChecked(true);
            }
            if (studAttendInfoEntity.getAttendence_marked().equals(AppConstants.No)) {
                ll_stud_pres.setVisibility(View.GONE);
                rb_attMark_yno.setChecked(true);
            }
        }

        tv_classCount.setText(studAttendInfoEntity.getTotal_students());
        tv_classType.setText(studAttendInfoEntity.getClass_type());
        et_studMarkedPres.setText(studAttendInfoEntity.getStudent_count_in_register());
        et_studPresInsp.setText(studAttendInfoEntity.getStudent_count_during_inspection());
        tv_variance.setText(studAttendInfoEntity.getVariance());
        IsattenMarked = studAttendInfoEntity.getAttendence_marked();


        ic_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        rg_IsAttndMarked_1_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_yes) {
                    IsattenMarked = AppConstants.Yes;
                    ll_variance.setVisibility(View.VISIBLE);
                    ll_stud_pres.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_no) {
                    IsattenMarked = AppConstants.No;
                    ll_stud_pres.setVisibility(View.GONE);
                    ll_variance.setVisibility(View.GONE);
                    et_studMarkedPres.setText("");
                    tv_variance.setText("");
                }
            }
        });

        et_studPresInsp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et_studPresInsp.getVisibility() == View.VISIBLE) {
                    if (!TextUtils.isEmpty(et_studMarkedPres.getText().toString()) &&
                            !TextUtils.isEmpty(et_studPresInsp.getText().toString())) {
                        String var=null;
                        if(Integer.parseInt(et_studMarkedPres.getText().toString().trim())>Integer.parseInt(et_studPresInsp.getText().toString().trim())) {
                            var = String.valueOf(Integer.parseInt(et_studMarkedPres.getText().toString().trim()) -
                                    Integer.parseInt(et_studPresInsp.getText().toString().trim()));
                        }else{
                            var = String.valueOf(Integer.parseInt(et_studPresInsp.getText().toString().trim())-
                                    Integer.parseInt(et_studMarkedPres.getText().toString().trim()));
                        }
                        tv_variance.setText(var);
                    } else {
                        tv_variance.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_studMarkedPres.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et_studMarkedPres.getVisibility() == View.VISIBLE) {
                    if (et_studMarkedPres.getVisibility() == View.VISIBLE) {
                        if (!TextUtils.isEmpty(et_studMarkedPres.getText().toString()) &&
                                !TextUtils.isEmpty(et_studPresInsp.getText().toString())) {
                            String var=null;
                            if(Integer.parseInt(et_studMarkedPres.getText().toString().trim())>Integer.parseInt(et_studPresInsp.getText().toString().trim())) {
                                var = String.valueOf(Integer.parseInt(et_studMarkedPres.getText().toString().trim()) -
                                        Integer.parseInt(et_studPresInsp.getText().toString().trim()));
                            }else{
                                var = String.valueOf(Integer.parseInt(et_studPresInsp.getText().toString().trim())-
                                        Integer.parseInt(et_studMarkedPres.getText().toString().trim()));
                            }     tv_variance.setText(var);
                        } else {
                            tv_variance.setText("");
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        dialog.show();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog.isShowing()) {

                    count_reg = et_studMarkedPres.getText().toString().trim();
                    count_during_insp = et_studPresInsp.getText().toString().trim();
                    variance = tv_variance.getText().toString().trim();

                    if (validate(studAttendInfoEntity)) {

                        studAttendInfoEntity.setAttendence_marked(IsattenMarked);
                        studAttendInfoEntity.setStudent_count_in_register(count_reg);
                        studAttendInfoEntity.setStudent_count_during_inspection(count_during_insp);
                        studAttendInfoEntity.setVariance(variance);
                        studAttendInfoEntity.setInspection_time(Utils.getCurrentDateTime());
                        studAttendInfoEntity.setFlag_completed(1);

//                    long x=studentsAttndViewModel.updateClassInfo(IsattenMarked,count_reg,count_during_insp,variance,
//                            TWDApplication.get(StudentsAttendActivity.this).getPreferences().getString(AppConstants.InstId,"")
//                            ,studAttendInfoEntity.getClass_id());
//
                        long x = studentsAttndViewModel.updateClassInfo(studAttendInfoEntity);
                        //Toast.makeText(StudentsAttendActivity.this, "Updated " + x, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }


                }
            }
        });

    }

    private boolean validate(StudAttendInfoEntity studAttendInfoEntity) {
        boolean flag = true;
        if (TextUtils.isEmpty(IsattenMarked)) {
            showBottomSheetSnackBar(getResources().getString(R.string.attendance_marked));
            flag = false;
        } else if (TextUtils.isEmpty(count_reg) && ll_stud_pres.getVisibility() == View.VISIBLE) {
            et_studMarkedPres.requestFocus();
            showBottomSheetSnackBar(getResources().getString(R.string.count_in_register));
            flag = false;
        } else if (TextUtils.isEmpty(count_during_insp)) {
            et_studPresInsp.requestFocus();
            showBottomSheetSnackBar(getResources().getString(R.string.count_dur_insp));
            flag = false;
        } else if (IsattenMarked.equals(AppConstants.Yes)) {
            if (Integer.parseInt(et_studMarkedPres.getText().toString()) > Integer.parseInt(studAttendInfoEntity.getTotal_students())) {
                showBottomSheetSnackBar(getResources().getString(R.string.stud_marked_greater_than_roll));
                flag = false;
            }else if(Integer.parseInt(et_studMarkedPres.getText().toString())==0){
                showBottomSheetSnackBar(getString(R.string.greater_than_zero));
                flag = false;
            } else if (Integer.parseInt(et_studPresInsp.getText().toString()) > Integer.parseInt(studAttendInfoEntity.getTotal_students())) {
                showBottomSheetSnackBar(getResources().getString(R.string.stud_greater_than_roll));
                flag = false;
            }
        } else if (Integer.parseInt(et_studPresInsp.getText().toString()) > Integer.parseInt(studAttendInfoEntity.getTotal_students())) {
            showBottomSheetSnackBar(getResources().getString(R.string.stud_greater_than_roll));
            flag = false;
        }
        return flag;
    }

    public void onHomeClick(View view) {
        startActivity(new Intent(this, InstMenuMainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public void submitData() {
        final long[] z = {0};
        LiveData<Integer> liveData = instMainViewModel.getSectionId("StuAtt");
        liveData.observe(StudentsAttendActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer id) {
                liveData.removeObservers(StudentsAttendActivity.this);
                if (id != null) {
                    z[0] = instMainViewModel.updateSectionInfo(Utils.getCurrentDateTime(), id, instId);
                }
            }
        });
        if (z[0] >= 0) {
            Utils.customSectionSaveAlert(StudentsAttendActivity.this, getString(R.string.data_saved), getString(R.string.app_name));
        } else {
            showSnackBar(getString(R.string.failed));
        }
    }

    @Override
    public void onBackPressed() {
        super.callBack();
    }

}
