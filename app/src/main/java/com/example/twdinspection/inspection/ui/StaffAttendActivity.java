package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.inspection.adapter.StaffAdapter;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.databinding.ActivityStaffAttBinding;
import com.example.twdinspection.inspection.interfaces.SaveListener;
import com.example.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.example.twdinspection.inspection.source.inst_master.MasterStaffInfo;
import com.example.twdinspection.inspection.source.staffAttendance.StaffAttendanceEntity;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.example.twdinspection.inspection.viewmodel.StaffAttendCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.StaffViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class StaffAttendActivity extends BaseActivity implements SaveListener {

    ActivityStaffAttBinding binding;
    List<StaffAttendanceEntity> staffAttendanceEntitiesmain;
    StaffAdapter staffAdapter;
    StaffViewModel staffViewModel;
    InstMainViewModel instMainViewModel;
    SharedPreferences sharedPreferences;
    String instName, instId, officerId;
    MasterInstituteInfo masterInstituteInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_staff_att, getResources().getString(R.string.sta_att));

        binding.btnLayout.btnPrevious.setVisibility(View.GONE);
        staffViewModel = ViewModelProviders.of(
                this, new StaffAttendCustomViewModel(binding, this)).get(StaffViewModel.class);
        binding.setViewmodel(staffViewModel);
        staffAttendanceEntitiesmain = new ArrayList<>();
        instMainViewModel = new InstMainViewModel(getApplication());
        sharedPreferences = TWDApplication.get(this).getPreferences();
        officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        instName = sharedPreferences.getString(AppConstants.INST_NAME, "");
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                    Utils.customSaveAlert(StaffAttendActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));

            }
        });

        staffViewModel.getStaffInfo(instId).observe(this, new Observer<List<StaffAttendanceEntity>>() {
            @Override
            public void onChanged(List<StaffAttendanceEntity> staffAttendanceEntities) {
                if (staffAttendanceEntities != null && staffAttendanceEntities.size() > 0) {
                    staffAttendanceEntitiesmain = staffAttendanceEntities;
                    staffAdapter = new StaffAdapter(StaffAttendActivity.this, staffAttendanceEntitiesmain);
                    binding.staffRv.setAdapter(staffAdapter);
                    binding.staffRv.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
                    binding.staffRv.setLayoutManager(layoutManager);
                } else {

                    LiveData<MasterInstituteInfo> masterInstituteInfoLiveData = staffViewModel.getMasterStaffInfo(TWDApplication.get(StaffAttendActivity.this).getPreferences().getString(AppConstants.INST_ID, ""));
                    masterInstituteInfoLiveData.observe(StaffAttendActivity.this, new Observer<MasterInstituteInfo>() {
                        @Override
                        public void onChanged(MasterInstituteInfo masterInstituteInfos) {
                            masterInstituteInfoLiveData.removeObservers(StaffAttendActivity.this);
                            StaffAttendActivity.this.masterInstituteInfos = masterInstituteInfos;
                            List<MasterStaffInfo> masterStaffInfos = masterInstituteInfos.getStaffInfo();
                            if (masterStaffInfos != null && masterStaffInfos.size() > 0) {
                                for (int i = 0; i < masterStaffInfos.size(); i++) {
                                    StaffAttendanceEntity staffAttendanceEntity = new StaffAttendanceEntity(officerId, String.valueOf(masterStaffInfos.get(i).getHostelId()), instName, String.valueOf(masterStaffInfos.get(i).getEmpId()), masterStaffInfos.get(i).getEmpName(), masterStaffInfos.get(i).getDesignation());
                                    staffAttendanceEntitiesmain.add(staffAttendanceEntity);
                                }
                            }

                            if (staffAttendanceEntitiesmain != null && staffAttendanceEntitiesmain.size() > 0) {
                                staffViewModel.insertStaffInfo(staffAttendanceEntitiesmain);
                            } else {
                                binding.emptyTv.setVisibility(View.VISIBLE);
                                binding.staffRv.setVisibility(View.GONE);
                            }

                        }
                    });
                }
            }
        });

//        staffViewModel.getUserResponseLiveData().observe(this,
//                new Observer<CreateUserResponse>() {
//                    @Override
//                    public void onChanged(CreateUserResponse c) {
//                        if (c != null) {
//                            setAdapter();
//                            Toast.makeText(getBaseContext(), "" + c.getId(), Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getBaseContext(), "No val", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//        setAdapter();

    }

    private boolean validate() {
        boolean returnFlag = true;
        for (int i = 0; i < staffAttendanceEntitiesmain.size(); i++) {
            if (!(staffAttendanceEntitiesmain.get(i).isAbsentFlag() || staffAttendanceEntitiesmain.get(i).isPresentFlag()
                    || staffAttendanceEntitiesmain.get(i).isOndepFlag())) {
                returnFlag = false;
                showSnackBar("Please mark attendance of all employees");
                break;
            } else if (TextUtils.isEmpty(staffAttendanceEntitiesmain.get(i).getYday_duty_allotted())) {
                returnFlag = false;
                showSnackBar("Please mark yesterday duty alloted to all employees");
                break;
            }

        }
        return returnFlag;
    }

    private void showSnackBar(String str) {
//        Snackbar.make(findViewById(staffAdapter.getItemViewType(pos)), str, Snackbar.LENGTH_SHORT).show();
        Snackbar.make(binding.rootLayout, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void submitData() {
        for (int i = 0; i < staffAttendanceEntitiesmain.size(); i++) {
            staffAttendanceEntitiesmain.get(i).setInspection_time(Utils.getCurrentDateTime());
        }
        long x = staffViewModel.updateStaffInfo(staffAttendanceEntitiesmain);
        if (x >= 0) {
            final long[] z = {0};
            try {
                LiveData<Integer> liveData = instMainViewModel.getSectionId("StfAtt");
                liveData.observe(StaffAttendActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer id) {
                        if (id != null) {
                            z[0] = instMainViewModel.updateSectionInfo(Utils.getCurrentDateTime(), id, instId);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z[0] >= 0) {
                Utils.customSectionSaveAlert(StaffAttendActivity.this,getString(R.string.data_saved),getString(R.string.app_name));
            } else {
                showSnackBar(getString(R.string.failed));
            }
        } else {
            showSnackBar(getString(R.string.failed));
        }
    }

//    private void setAdapter() {
//        authViewModel.getAllEmployees().observe(this, new Observer<List<EmployeeResponse>>() {
//            @Override
//            public void onChanged(List<EmployeeResponse> employeeResponses) {
//                if (employeeResponses != null && employeeResponses.size() > 0) {
//                    adapter.setEmployeeList(employeeResponses);
////                            binding.rv.scrollToPosition(employeeResponses.size()-1);
//
//                }
//            }
//
//        });
//    }

    @Override
    public void onBackPressed() {
        super.callBack();
    }
}
