package com.cgg.twdinspection.gcc.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.ActivityLpgInspRepBinding;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.google.gson.Gson;

public class LPGInspRepActivity extends AppCompatActivity {

    ActivityLpgInspRepBinding binding;
    private SharedPreferences sharedPreferences;
    ReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lpg_insp_rep);

        binding.bottomLl.btnNext.setText("Next");
        binding.header.headerTitle.setText("LPG Inspection Report");
        binding.header.ivHome.setVisibility(View.GONE);
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        sharedPreferences = TWDApplication.get(LPGInspRepActivity.this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.REP_DATA, "");
        reportData = gson.fromJson(data, ReportData.class);

        if (reportData != null && reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getLpg() != null) {
            binding.setInspData(reportData.getInspectionFindings().getLpg());
        } else {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
        }
        if (reportData != null && reportData.getPhotos() != null && reportData.getPhotos().size()>0) {
            for(int z=0;z<reportData.getPhotos().size();z++) {
                if (!TextUtils.isEmpty(reportData.getPhotos().get(z).getFileName())
                        && reportData.getPhotos().get(z).getFileName().equalsIgnoreCase(AppConstants.REPAIR))
                    binding.setImageUrl(reportData.getPhotos().get(z).getFilePath());
                break;
            }
        } else {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
        }

        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LPGInspRepActivity.this, ViewPhotosActivity.class));
            }
        });
    }
}
