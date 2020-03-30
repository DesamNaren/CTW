package com.cgg.twdinspection.gcc.reports.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.ActivityMfpGodownInspRepBinding;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.google.gson.Gson;

public class MfpGodownInspRepActivity extends AppCompatActivity {

    ActivityMfpGodownInspRepBinding binding;
    SharedPreferences sharedPreferences;
    ReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mfp_godown_insp_rep);

        binding.bottomLl.btnNext.setText("Next");
        binding.header.headerTitle.setText("MFPGodown Inspection Report");
        binding.header.ivHome.setVisibility(View.GONE);
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        sharedPreferences= TWDApplication.get(MfpGodownInspRepActivity.this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.REP_DATA, "");
        reportData = gson.fromJson(data, ReportData.class);

        binding.setMfp(reportData.getInspectionFindings().getMfpGodowns());
        binding.setImageUrl(reportData.getPhotos().get(1).getFilePath());
        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MfpGodownInspRepActivity.this,ViewPhotosActivity.class));
            }
        });
    }
}
