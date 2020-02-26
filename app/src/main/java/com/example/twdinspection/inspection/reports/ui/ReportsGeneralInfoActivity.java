package com.example.twdinspection.inspection.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityReportGeneralInfoBinding;
import com.example.twdinspection.inspection.reports.source.InspReportData;
import com.example.twdinspection.inspection.ui.StudentsAttendActivity;
import com.google.gson.Gson;

public class ReportsGeneralInfoActivity extends AppCompatActivity {
    private static final String TAG = ReportsGeneralInfoActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    ActivityReportGeneralInfoBinding binding;
    private InspReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_general_info);
        binding.actionBar.headerTitle.setText(getString(R.string.general_info));
        binding.actionBar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.actionBar.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportsGeneralInfoActivity.this, InstReportsMenuActivity.class));
            }
        });


        sharedPreferences = TWDApplication.get(this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.INSP_REP_DATA, "");
        reportData = gson.fromJson(data, InspReportData.class);

        binding.setInspData(reportData.getGeneralInfo());

        binding.includeBasicLayout.inspectionTime.setText(reportData.getGeneralInfo().getInspectionTime());
        binding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_ID, ""));
        binding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
        binding.executePendingBindings();

        binding.btnNext.setText(getResources().getString(R.string.next));
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportsGeneralInfoActivity.this, ReportStudentsAttendActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
