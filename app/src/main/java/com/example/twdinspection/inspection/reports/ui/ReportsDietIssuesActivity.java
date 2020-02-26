package com.example.twdinspection.inspection.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityReportDietIssuesBinding;
import com.example.twdinspection.inspection.reports.source.InspReportData;
import com.example.twdinspection.inspection.ui.BaseActivity;
import com.example.twdinspection.inspection.ui.StudentsAttendActivity;
import com.google.gson.Gson;

public class ReportsDietIssuesActivity extends BaseActivity {
    private static final String TAG = ReportsDietIssuesActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    ActivityReportDietIssuesBinding binding;
    private InspReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_diet_issues);
        binding.actionBar.headerTitle.setText(getString(R.string.diet_issues));
        binding.actionBar.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportsDietIssuesActivity.this, InstReportsMenuActivity.class));
            }
        });

        binding.actionBar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        sharedPreferences = TWDApplication.get(this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.INSP_REP_DATA, "");
        reportData = gson.fromJson(data, InspReportData.class);

        binding.setInspData(reportData.getDietIssues());
        binding.setMenuImgUrl("https://androidwave.com/wp-content/uploads/2019/01/profile_pic.jpg");
        binding.setOfficerImgUrl("https://androidwave.com/wp-content/uploads/2019/01/profile_pic.jpg");
        binding.executePendingBindings();

        binding.btnLayout.btnNext.setText(getResources().getString(R.string.next));
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportsDietIssuesActivity.this, ReportsInfraActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
