package com.example.twdinspection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.adapter.StaffAdapter;
import com.example.twdinspection.databinding.ActivityStaffAttBinding;
import com.example.twdinspection.source.EmployeeResponse;
import com.example.twdinspection.viewmodel.StaffAttendCustomViewModel;
import com.example.twdinspection.viewmodel.StaffViewModel;

import java.util.ArrayList;
import java.util.List;

public class StaffAttendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStaffAttBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_staff_att);
        TextView tv_title=findViewById(R.id.header_title);
        tv_title.setText("Staff Attendance");
        StaffViewModel staffViewModel = ViewModelProviders.of(
                this, new StaffAttendCustomViewModel(binding, this)).get(StaffViewModel.class);
        binding.setViewmodel(staffViewModel);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setReverseLayout(false);
        binding.staffRv.setLayoutManager(layoutManager);

        List<EmployeeResponse> list = new ArrayList<>();
        for(int x=0;x<10;x++){
            EmployeeResponse e = new EmployeeResponse();
            e.setId(""+x);
            e.setEmployeeName("XYZ "+x);
            e.setEmployeeSalary("SAL "+x);
            e.setEmployeeAge("AGE "+x);
            list.add(e);
        }

        StaffAdapter staffAdapter = new StaffAdapter(StaffAttendActivity.this,list);
        binding.staffRv.setAdapter(staffAdapter);
        binding.staffRv.setHasFixedSize(true);

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StaffAttendActivity.this, MedicalActivity.class));
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
