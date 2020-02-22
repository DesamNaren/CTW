package com.example.twdinspection.inspection.reports.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityReportDietIssuesBinding;
import com.example.twdinspection.databinding.ActivityReportInfrastructureBinding;
import com.example.twdinspection.inspection.reports.source.InspReportData;
import com.example.twdinspection.inspection.ui.BaseActivity;
import com.google.gson.Gson;

public class ReportsInfraActivity extends BaseActivity {
    private static final String TAG = ReportsInfraActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    ActivityReportInfrastructureBinding binding;
    private String officerID, instID, insTime;
    private InspReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_infrastructure);

        sharedPreferences = TWDApplication.get(this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.INSP_REP_DATA, "");
        reportData = gson.fromJson(data, InspReportData.class);

        binding.setInspData(reportData.getInfraMaintenance());
        binding.executePendingBindings();

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(ReportsDietIssuesActivity.this, StudentsAttendActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
