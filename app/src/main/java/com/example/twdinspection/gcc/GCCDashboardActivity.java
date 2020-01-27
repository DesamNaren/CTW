package com.example.twdinspection.gcc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityDashboardBinding;
import com.example.twdinspection.databinding.ActivityGccDashboardBinding;
import com.example.twdinspection.inspection.ui.DMVSelectionActivity;
import com.example.twdinspection.inspection.ui.DashboardActivity;
import com.example.twdinspection.schemes.ui.SchemesDMVActivity;

public class GCCDashboardActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    ActivityGccDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gcc_dashboard);
        binding.header.headerTitle.setText(getResources().getString(R.string.dashboard));
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            binding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            binding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            String curTime = Utils.getCurrentDateTime();
            editor.putString(AppConstants.INSP_TIME, curTime);
            editor.commit();
            binding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.btnDrGodown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GCCDashboardActivity.this, DMVSelectionActivity.class));
            }
        });

        binding.btnDrDepot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GCCDashboardActivity.this, SchemesDMVActivity.class));
            }
        });

        binding.btnMfpGodown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GCCDashboardActivity.this, SchemesDMVActivity.class));
            }
        });

        binding.btnPUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GCCDashboardActivity.this, SchemesDMVActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Utils.customExitAlert(this,
                getResources().getString(R.string.app_name),
                getString(R.string.exit_msg));
    }
}
