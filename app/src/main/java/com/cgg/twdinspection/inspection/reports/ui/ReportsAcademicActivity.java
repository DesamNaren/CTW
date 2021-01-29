package com.cgg.twdinspection.inspection.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.ActivityReportAcademicBinding;
import com.cgg.twdinspection.inspection.reports.source.InspReportData;
import com.cgg.twdinspection.inspection.ui.BaseActivity;
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

        TextView[] ids = new TextView[]{binding.slno1, binding.slno2, binding.slno3, binding.slno4, binding.slno5,
                binding.slno6, binding.slno7, binding.slno8, binding.slno9, binding.slno10, binding.slno11, binding.slno12,
                binding.slno13, binding.slno14, binding.slno15, binding.slno16, binding.slno17};
        BaseActivity.setIds(ids, 53);

        binding.actionBar.headerTitle.setText(getString(R.string.title_academic));
        binding.actionBar.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportsAcademicActivity.this, InstReportsMenuActivity.class));
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



        String jsonObject  = gson.toJson(reportData.getAcademicOverview());
        if(!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("{}")) {
            binding.setInspData(reportData.getAcademicOverview());
            binding.executePendingBindings();

        }

        binding.btnAddStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportsAcademicActivity.this,ReportAcademicGradeActivity.class));
            }
        });

        binding.btnLayout.btnNext.setText(getResources().getString(R.string.next));
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportsAcademicActivity.this, ReportCoCarricularActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
