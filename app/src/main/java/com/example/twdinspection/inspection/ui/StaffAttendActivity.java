package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.inspection.adapter.StaffAdapter;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.databinding.ActivityStaffAttBinding;
import com.example.twdinspection.inspection.source.staffAttendance.StaffAttendanceEntity;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.inspection.viewmodel.StaffAttendCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.StaffViewModel;

import java.util.List;

public class StaffAttendActivity extends BaseActivity {

    ActivityStaffAttBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_staff_att, getResources().getString(R.string.sta_att));

        StaffViewModel staffViewModel = ViewModelProviders.of(
                this, new StaffAttendCustomViewModel(binding, this)).get(StaffViewModel.class);
        binding.setViewmodel(staffViewModel);


        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StaffAttendActivity.this, MedicalActivity.class));
            }
        });

        staffViewModel.getStaffInfo(TWDApplication.get(this).getPreferences().getString(AppConstants.INST_ID, "")).observe(this, new Observer<List<StaffAttendanceEntity>>() {
            @Override
            public void onChanged(List<StaffAttendanceEntity> staffAttendanceEntities) {
                StaffAdapter staffAdapter = new StaffAdapter(StaffAttendActivity.this, staffAttendanceEntities);
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
