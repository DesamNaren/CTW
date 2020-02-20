package com.example.twdinspection.gcc.reports.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityPunitInspRepBinding;
import com.example.twdinspection.gcc.reports.source.ReportData;
import com.google.gson.Gson;

public class PUnitInspRepActivity extends AppCompatActivity {

    ActivityPunitInspRepBinding binding;
    SharedPreferences sharedPreferences;
    ReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_punit_insp_rep);
        binding.header.headerTitle.setText("Processing Unit Inspection Report");
        binding.header.ivHome.setVisibility(View.GONE);
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        sharedPreferences= TWDApplication.get(PUnitInspRepActivity.this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.REP_DATA, "");
        reportData = gson.fromJson(data, ReportData.class);

        binding.setPUnit(reportData.getInspectionFindings().getProcessingUnit());
        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PUnitInspRepActivity.this,ViewPhotosActivity.class));
            }
        });
    }
}
