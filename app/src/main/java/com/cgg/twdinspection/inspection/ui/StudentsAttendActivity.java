package com.cgg.twdinspection.inspection.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityStudentsAttendanceBinding;
import com.cgg.twdinspection.databinding.BottomSheetBinding;
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
    InstMainViewModel instMainViewModel;
    SharedPreferences sharedPreferences;
    String instId, officerId;
    private BottomSheetBinding bs_binding;

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
                            if (masterInstituteInfos != null) {
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

                            } else {
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
        Snackbar.make(bs_binding.bottomLl, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void openBottomSheet(StudAttendInfoEntity studAttendInfoEntity) {
        showContactDetails(studAttendInfoEntity);
    }

    public void showContactDetails(StudAttendInfoEntity studAttendInfoEntity) {
        bs_binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.bottom_sheet, null, false);
        dialog = new BottomSheetDialog(StudentsAttendActivity.this);
        dialog.setContentView(bs_binding.getRoot());
        dialog.setCancelable(true);

        TextView[] ids = new TextView[]{bs_binding.slno1, bs_binding.slno2, bs_binding.slno3, bs_binding.slno4,
                bs_binding.slno5};
        BaseActivity.setIds(ids, 8);

        if (studAttendInfoEntity.getAttendence_marked() != null) {
            if (studAttendInfoEntity.getAttendence_marked().equals(AppConstants.Yes)) {
                bs_binding.llStudPres.setVisibility(View.VISIBLE);
                bs_binding.llVariance.setVisibility(View.VISIBLE);
                bs_binding.rbYes.setChecked(true);
            }
            if (studAttendInfoEntity.getAttendence_marked().equals(AppConstants.No)) {
                bs_binding.llStudPres.setVisibility(View.GONE);
                bs_binding.llVariance.setVisibility(View.GONE);
                bs_binding.rbNo.setChecked(true);
            }
        }

        bs_binding.tvClassCount.setText(studAttendInfoEntity.getTotal_students());
        bs_binding.tvClassType.setText(studAttendInfoEntity.getClass_type());
        bs_binding.etStudMarkedPres.setText(studAttendInfoEntity.getStudent_count_in_register());
        bs_binding.etStudPresInsp.setText(studAttendInfoEntity.getStudent_count_during_inspection());
        bs_binding.tvVariance.setText(studAttendInfoEntity.getVariance());
        IsattenMarked = studAttendInfoEntity.getAttendence_marked();


        bs_binding.icClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        bs_binding.rgIsAttndMarked12.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_yes) {
                    IsattenMarked = AppConstants.Yes;
                    bs_binding.llVariance.setVisibility(View.VISIBLE);
                    bs_binding.llStudPres.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_no) {
                    IsattenMarked = AppConstants.No;
                    bs_binding.llStudPres.setVisibility(View.GONE);
                    bs_binding.llVariance.setVisibility(View.GONE);
                    bs_binding.etStudMarkedPres.setText("");
                    bs_binding.tvVariance.setText("");
                }
            }
        });

        bs_binding.etStudPresInsp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (bs_binding.etStudPresInsp.getVisibility() == View.VISIBLE) {
                    if (!TextUtils.isEmpty(bs_binding.etStudMarkedPres.getText().toString()) &&
                            !TextUtils.isEmpty(bs_binding.etStudPresInsp.getText().toString())) {
                        String var = null;
                        if (Integer.parseInt(bs_binding.etStudMarkedPres.getText().toString().trim()) > Integer.parseInt(bs_binding.etStudPresInsp.getText().toString().trim())) {
                            var = String.valueOf(Integer.parseInt(bs_binding.etStudMarkedPres.getText().toString().trim()) -
                                    Integer.parseInt(bs_binding.etStudPresInsp.getText().toString().trim()));
                        } else {
                            var = String.valueOf(Integer.parseInt(bs_binding.etStudPresInsp.getText().toString().trim()) -
                                    Integer.parseInt(bs_binding.etStudMarkedPres.getText().toString().trim()));
                        }
                        bs_binding.tvVariance.setText(var);
                    } else {
                        bs_binding.tvVariance.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        bs_binding.etStudMarkedPres.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (bs_binding.etStudMarkedPres.getVisibility() == View.VISIBLE) {
                    if (bs_binding.etStudMarkedPres.getVisibility() == View.VISIBLE) {
                        if (!TextUtils.isEmpty(bs_binding.etStudMarkedPres.getText().toString()) &&
                                !TextUtils.isEmpty(bs_binding.etStudPresInsp.getText().toString())) {
                            String var = null;
                            if (Integer.parseInt(bs_binding.etStudMarkedPres.getText().toString().trim()) > Integer.parseInt(bs_binding.etStudPresInsp.getText().toString().trim())) {
                                var = String.valueOf(Integer.parseInt(bs_binding.etStudMarkedPres.getText().toString().trim()) -
                                        Integer.parseInt(bs_binding.etStudPresInsp.getText().toString().trim()));
                            } else {
                                var = String.valueOf(Integer.parseInt(bs_binding.etStudPresInsp.getText().toString().trim()) -
                                        Integer.parseInt(bs_binding.etStudMarkedPres.getText().toString().trim()));
                            }
                            bs_binding.tvVariance.setText(var);
                        } else {
                            bs_binding.tvVariance.setText("");
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        dialog.show();

        bs_binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog.isShowing()) {

                    count_reg = bs_binding.etStudMarkedPres.getText().toString().trim();
                    count_during_insp = bs_binding.etStudPresInsp.getText().toString().trim();
                    variance = bs_binding.tvVariance.getText().toString().trim();

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
        } else if (TextUtils.isEmpty(count_reg) && bs_binding.llStudPres.getVisibility() == View.VISIBLE) {
            bs_binding.etStudMarkedPres.requestFocus();
            showBottomSheetSnackBar(getResources().getString(R.string.count_in_register));
            flag = false;
        } else if (bs_binding.llStudPres.getVisibility() == View.VISIBLE && count_reg.equals("0")) {
            showBottomSheetSnackBar(getResources().getString(R.string.count_in_register_zero));
            bs_binding.etStudMarkedPres.setText("");
            bs_binding.etStudMarkedPres.requestFocus();
            flag = false;
        } else if (TextUtils.isEmpty(count_during_insp)) {
            bs_binding.etStudPresInsp.requestFocus();
            showBottomSheetSnackBar(getResources().getString(R.string.count_dur_insp));
            flag = false;
        } else if (IsattenMarked.equals(AppConstants.Yes)) {
            if (Integer.parseInt(bs_binding.etStudMarkedPres.getText().toString()) > Integer.parseInt(studAttendInfoEntity.getTotal_students())) {
                showBottomSheetSnackBar(getResources().getString(R.string.stud_marked_greater_than_roll));
                flag = false;
            } else if (Integer.parseInt(bs_binding.etStudMarkedPres.getText().toString()) == 0) {
                showBottomSheetSnackBar(getString(R.string.greater_than_zero));
                flag = false;
            } else if (Integer.parseInt(bs_binding.etStudPresInsp.getText().toString()) > Integer.parseInt(studAttendInfoEntity.getTotal_students())) {
                showBottomSheetSnackBar(getResources().getString(R.string.stud_greater_than_roll));
                flag = false;
            }
        } else if (Integer.parseInt(bs_binding.etStudPresInsp.getText().toString()) > Integer.parseInt(studAttendInfoEntity.getTotal_students())) {
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
