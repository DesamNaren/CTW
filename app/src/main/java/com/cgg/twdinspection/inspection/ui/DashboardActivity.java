package com.cgg.twdinspection.inspection.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.engineering_works.ui.EngineeringDashboardActivity;
import com.cgg.twdinspection.gcc.ui.gcc.GCCDashboardActivity;
import com.cgg.twdinspection.inspection.reports.ui.ReportActivity;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstMenuInfoEntity;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstSelectionInfo;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstSelectionViewModel;
import com.cgg.twdinspection.schemes.ui.SchemesDMVActivity;
import com.cgg.twdinspection.databinding.ActivityDashboardBinding;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String cacheDate, currentDate;
    ActivityDashboardBinding binding;
    InstMainViewModel instMainViewModel;
    InstSelectionViewModel instSelectionViewModel;
    private String instId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        binding.header.headerTitle.setText(getResources().getString(R.string.dashboard));
        binding.header.ivHome.setVisibility(View.GONE);
        binding.header.ivLogout.setVisibility(View.VISIBLE);
        instMainViewModel = new InstMainViewModel(getApplication());
        instSelectionViewModel = new InstSelectionViewModel(getApplication());

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.header.ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.customLogoutAlert(DashboardActivity.this, getResources().getString(R.string.app_name),
                        getString(R.string.logout), instMainViewModel, editor);

            }
        });

        try {
            if (getSupportActionBar() != null) {
//                getSupportActionBar().setTitle(getResources().getString(R.string.ben_report));
                TextView tv = new TextView(getApplicationContext());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                        RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
                tv.setLayoutParams(lp);
                tv.setText(getResources().getString(R.string.dashboard));
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(tv);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            instId = sharedPreferences.getString(AppConstants.INST_ID, "");
            editor = sharedPreferences.edit();
            binding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            binding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            String curTime = Utils.getCurrentDateTimeDisplay();
            editor.putString(AppConstants.INSP_TIME, curTime);
            editor.commit();
            binding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.btnInstInsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instSelectionViewModel.getSelectedInst().observe(DashboardActivity.this, new Observer<InstSelectionInfo>() {
                    @Override
                    public void onChanged(InstSelectionInfo instSelectionInfo) {
                        if (instSelectionInfo != null) {
                            instId = instSelectionInfo.getInst_id();
                            if (!TextUtils.isEmpty(instId)) {
                                instMainViewModel.getAllSections().observe(DashboardActivity.this, new Observer<List<InstMenuInfoEntity>>() {
                                    @Override
                                    public void onChanged(List<InstMenuInfoEntity> instMenuInfoEntities) {
                                        if (instMenuInfoEntities != null && instMenuInfoEntities.size() > 0) {

                                            boolean flag = false;
                                            for (int i = 0; i < instMenuInfoEntities.size(); i++) {
                                                if (instMenuInfoEntities.get(i).getFlag_completed() == 1) {
                                                    flag = true;
                                                    break;
                                                }
                                            }
                                            if (flag) {
                                                startActivity(new Intent(DashboardActivity.this, InstMenuMainActivity.class));
                                                finish();
                                            } else {
                                                startActivity(new Intent(DashboardActivity.this, DMVSelectionActivity.class));
                                            }
                                        } else {
                                            startActivity(new Intent(DashboardActivity.this, DMVSelectionActivity.class));
                                        }
                                    }

                                });
                            } else {
                                startActivity(new Intent(DashboardActivity.this, DMVSelectionActivity.class));
                            }
                        } else {
                            startActivity(new Intent(DashboardActivity.this, DMVSelectionActivity.class));
                        }
                    }
                });

            }
        });

        binding.btnSchemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, SchemesDMVActivity.class));
            }
        });

        binding.btnEngWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, EngineeringDashboardActivity.class));
            }
        });

        binding.btnGcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, GCCDashboardActivity.class));
            }
        });

        binding.btnReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, ReportActivity.class));
            }
        });
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inst_main_menu_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_report:
                startActivity(new Intent(DashboardActivity.this, ReportActivity.class));
                break;
        }
        return true;
    }*/

    @Override
    protected void onResume() {

        try {
            boolean isAutomatic = Utils.isTimeAutomatic(this);
            if (!isAutomatic) {
                Utils.customTimeAlert(this,
                        getResources().getString(R.string.app_name),
                        getString(R.string.date_time));
                return;
            }

            currentDate = Utils.getCurrentDate();
            cacheDate = sharedPreferences.getString(AppConstants.CACHE_DATE, "");

            if (!TextUtils.isEmpty(cacheDate)) {
                if (!cacheDate.equalsIgnoreCase(currentDate)) {

                    Utils.ShowDeviceSessionAlert(this,
                            getResources().getString(R.string.app_name),
                            getString(R.string.ses_expire_re), instMainViewModel);
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {

        cacheDate = currentDate;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.CACHE_DATE, cacheDate);
        editor.commit();
        super.onPause();
    }

    @Override
    public void onBackPressed() {

        Utils.customExitAlert(this,
                getResources().getString(R.string.app_name),
                getString(R.string.exit_msg));
    }
}
