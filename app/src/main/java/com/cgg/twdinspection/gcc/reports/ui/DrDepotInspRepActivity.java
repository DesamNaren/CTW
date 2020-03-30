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
import com.cgg.twdinspection.databinding.ActivityDrDepotInspRepBinding;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.google.gson.Gson;

public class DrDepotInspRepActivity extends AppCompatActivity {

    ActivityDrDepotInspRepBinding binding;
    SharedPreferences sharedPreferences;
    ReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_depot_insp_rep);
        binding.header.headerTitle.setText("Depot Inspection Report");
        binding.header.ivHome.setVisibility(View.GONE);
        binding.bottomLl.btnNext.setText("Next");
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        sharedPreferences= TWDApplication.get(DrDepotInspRepActivity.this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.REP_DATA, "");
        reportData = gson.fromJson(data, ReportData.class);
        binding.setDrDepot(reportData.getInspectionFindings().getDrDepot());
        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrDepotInspRepActivity.this,ViewPhotosActivity.class));
            }
        });
    }
}
