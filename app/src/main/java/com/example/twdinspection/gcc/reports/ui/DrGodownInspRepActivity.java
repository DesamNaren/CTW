package com.example.twdinspection.gcc.reports.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityDrGodownInspRepBinding;
import com.example.twdinspection.gcc.reports.source.ReportData;
import com.google.gson.Gson;

public class DrGodownInspRepActivity extends AppCompatActivity {

    ActivityDrGodownInspRepBinding binding;
    private SharedPreferences sharedPreferences;
    ReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_godown_insp_rep);

        binding.header.headerTitle.setText("Inspection Report");
        binding.header.ivHome.setVisibility(View.GONE);
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        sharedPreferences = TWDApplication.get(DrGodownInspRepActivity.this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.REP_DATA, "");
        reportData = gson.fromJson(data, ReportData.class);

        binding.setInspData(reportData.getInspectionFindings().getDrGodown());
        binding.setImageUrl("https://androidwave.com/wp-content/uploads/2019/01/profile_pic.jpg");

    }
}
