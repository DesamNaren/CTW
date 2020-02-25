package com.example.twdinspection.inspection.reports.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityReportAcademicBinding;
import com.example.twdinspection.databinding.ActivityReportGeneralInfoBinding;
import com.example.twdinspection.databinding.ActivityReportInfrastructureBinding;
import com.example.twdinspection.inspection.reports.source.InspReportData;
import com.example.twdinspection.inspection.ui.BaseActivity;
import com.google.gson.Gson;

public class ReportsAcademicActivity extends BaseActivity {
    private static final String TAG = ReportsAcademicActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    ActivityReportAcademicBinding binding;
    private InspReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_academic);

        sharedPreferences = TWDApplication.get(this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.INSP_REP_DATA, "");
        reportData = gson.fromJson(data, InspReportData.class);

        binding.setInspData(reportData.getAcademicOverview());
        binding.executePendingBindings();

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(ReportsGeneralInfoActivity.this, StudentsAttendActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}