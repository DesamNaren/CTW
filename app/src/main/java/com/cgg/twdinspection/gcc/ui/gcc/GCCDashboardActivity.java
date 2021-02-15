package com.cgg.twdinspection.gcc.ui.gcc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityGccDashboardBinding;
import com.cgg.twdinspection.gcc.ui.drdepot.DRDepotSelActivity;
import com.cgg.twdinspection.gcc.ui.drgodown.DRGODownSelActivity;
import com.cgg.twdinspection.gcc.ui.lpg.LPGSelActivity;
import com.cgg.twdinspection.gcc.ui.mfpgodown.MFPGoDownSelActivity;
import com.cgg.twdinspection.gcc.ui.petrolpump.PetrolPumpSelActivity;
import com.cgg.twdinspection.gcc.ui.punit.PUnitSelActivity;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;

public class GCCDashboardActivity extends AppCompatActivity {
    ActivityGccDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gcc_dashboard);
        binding.header.headerTitle.setText(getResources().getString(R.string.gcc));
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GCCDashboardActivity.this, DashboardMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

            }
        });

        binding.header.syncIv.setVisibility(View.VISIBLE);
        binding.header.syncIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GCCDashboardActivity.this, GCCSyncActivity.class));
            }
        });

        try {
            SharedPreferences sharedPreferences = TWDApplication.get(this).getPreferences();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            binding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            binding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            String curTime = Utils.getCurrentDateTimeDisplay();
            editor.putString(AppConstants.INSP_TIME, curTime);
            editor.commit();
            binding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.btnDrGodown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GCCDashboardActivity.this, DRGODownSelActivity.class));
            }
        });

        binding.btnDrDepot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GCCDashboardActivity.this, DRDepotSelActivity.class));
            }
        });

        binding.btnMfpGodown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GCCDashboardActivity.this, MFPGoDownSelActivity.class));
            }
        });

        binding.btnPUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GCCDashboardActivity.this, PUnitSelActivity.class));
            }
        });

        binding.btnPetrolPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GCCDashboardActivity.this, PetrolPumpSelActivity.class));
            }
        });

        binding.btnLpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GCCDashboardActivity.this, LPGSelActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
