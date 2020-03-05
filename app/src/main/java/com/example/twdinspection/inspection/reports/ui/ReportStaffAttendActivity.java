package com.example.twdinspection.inspection.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ReportActivityStudentsAttendanceBinding;
import com.example.twdinspection.inspection.reports.adapter.StaffAttReportAdapter;
import com.example.twdinspection.inspection.reports.adapter.StuAttReportAdapter;
import com.example.twdinspection.inspection.reports.source.InspReportData;
import com.example.twdinspection.inspection.reports.source.StaffAttendenceInfo;
import com.example.twdinspection.inspection.reports.source.StudentAttendenceInfo;
import com.example.twdinspection.inspection.ui.BaseActivity;
import com.example.twdinspection.inspection.ui.InstMenuMainActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

public class ReportStaffAttendActivity extends BaseActivity {
    ReportActivityStudentsAttendanceBinding binding;
    SharedPreferences sharedPreferences;
    String instId, officerId;
    private InspReportData inspReportData;
    private List<StaffAttendenceInfo> staffAttendenceInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.report_activity_students_attendance);
        binding.actionBar.headerTitle.setText(getString(R.string.sta_att));
        binding.actionBar.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportStaffAttendActivity.this, InstReportsMenuActivity.class));
            }
        });

        binding.actionBar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sharedPreferences = TWDApplication.get(this).getPreferences();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");

        Gson gson=new Gson();
        String data = sharedPreferences.getString(AppConstants.INSP_REP_DATA, "");
        inspReportData=gson.fromJson(data, InspReportData.class);


        String jsonObject = gson.toJson(inspReportData.getStaffAttendenceInfo());
        if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("{}")) {
            staffAttendenceInfos = inspReportData.getStaffAttendenceInfo();
            if(staffAttendenceInfos!=null && staffAttendenceInfos.size()>0){
                setAdapter(staffAttendenceInfos);
            }
        }


        binding.btnLayout.btnNext.setText(getResources().getString(R.string.next));
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportStaffAttendActivity.this, ReportMedicalActivity.class));
            }
        });
    }

    private void setAdapter(List<StaffAttendenceInfo> staffAttendenceInfos) {
        StaffAttReportAdapter staffAttReportAdapter = new StaffAttReportAdapter(this, staffAttendenceInfos);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(staffAttReportAdapter);
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.root, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
