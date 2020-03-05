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
import com.example.twdinspection.databinding.ActivityMedicalReportBinding;
import com.example.twdinspection.databinding.ReportActivityStudentsAttendanceBinding;
import com.example.twdinspection.inspection.reports.adapter.StaffAttReportAdapter;
import com.example.twdinspection.inspection.reports.source.InspReportData;
import com.example.twdinspection.inspection.reports.source.MedicalIssues;
import com.example.twdinspection.inspection.reports.source.StaffAttendenceInfo;
import com.example.twdinspection.inspection.ui.BaseActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

public class ReportMedicalActivity extends BaseActivity {
    ActivityMedicalReportBinding binding;
    SharedPreferences sharedPreferences;
    String instId, officerId;
    private InspReportData inspReportData;
    private List<MedicalIssues> medicalIssues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_medical_report);
        binding.actionBar.headerTitle.setText(getString(R.string.medical_health));
        binding.actionBar.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportMedicalActivity.this, InstReportsMenuActivity.class));
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

        String jsonObject  = gson.toJson(inspReportData.getMedicalIssues());
        if(!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("{}")) {
            binding.setMedical(inspReportData.getMedicalIssues());
            binding.executePendingBindings();
        }

        binding.btnLayout.btnNext.setText(getResources().getString(R.string.next));
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportMedicalActivity.this, ReportsDietIssuesActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
