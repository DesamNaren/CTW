package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.inspection.adapter.StaffAdapter;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.databinding.ActivityStaffAttBinding;
import com.example.twdinspection.inspection.interfaces.SaveListener;
import com.example.twdinspection.inspection.source.staffAttendance.StaffAttendanceEntity;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.example.twdinspection.inspection.viewmodel.StaffAttendCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.StaffViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class StaffAttendActivity extends BaseActivity implements SaveListener {

    ActivityStaffAttBinding binding;
    List<StaffAttendanceEntity> staffAttendanceEntities;
    StaffAdapter staffAdapter;
    StaffViewModel staffViewModel;
    InstMainViewModel instMainViewModel;
    SharedPreferences sharedPreferences;
    String instId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_staff_att, getResources().getString(R.string.sta_att));

        binding.btnLayout.btnPrevious.setVisibility(View.GONE);
        staffViewModel = ViewModelProviders.of(
                this, new StaffAttendCustomViewModel(binding, this)).get(StaffViewModel.class);
        binding.setViewmodel(staffViewModel);
        instMainViewModel = new InstMainViewModel(getApplication());
        sharedPreferences = TWDApplication.get(this).getPreferences();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                    Utils.customSaveAlert(StaffAttendActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));

            }
        });

        staffViewModel.getStaffInfo(instId).observe(this, new Observer<List<StaffAttendanceEntity>>() {
            @Override
            public void onChanged(List<StaffAttendanceEntity> staffAttendanceEntities) {
                StaffAttendActivity.this.staffAttendanceEntities=staffAttendanceEntities;
                staffAdapter = new StaffAdapter(StaffAttendActivity.this, staffAttendanceEntities);
                binding.staffRv.setAdapter(staffAdapter);
                binding.staffRv.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
                binding.staffRv.setLayoutManager(layoutManager);
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
        boolean returnFlag=true;
        for (int i=0;i<staffAttendanceEntities.size();i++){
            if(!(staffAttendanceEntities.get(i).isAbsentFlag() || staffAttendanceEntities.get(i).isPresentFlag()
                    || staffAttendanceEntities.get(i).isOndepFlag())){
                returnFlag=false;
                showSnackBar("Please mark attendance of all employees");
                break;
            }else if(TextUtils.isEmpty(staffAttendanceEntities.get(i).getYday_duty_allotted())){
                returnFlag=false;
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
        long x = staffViewModel.updateStaffInfo(staffAttendanceEntities);
        if (x >= 0) {
            long z = 0;
            try {
//                InstMenuInfoEntity instMenuInfoEntity = new InstMenuInfoEntity(1, 1, "General Information", Utils.getCurrentDateTime());
                z = instMainViewModel.updateSectionInfo(Utils.getCurrentDateTime(), 3,instId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z >= 0) {
                showSnackBar(getString(R.string.data_saved));
                startActivity(new Intent(StaffAttendActivity.this, MedicalActivity.class));
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
}
