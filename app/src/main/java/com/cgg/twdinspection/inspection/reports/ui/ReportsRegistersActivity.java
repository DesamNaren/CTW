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
import com.cgg.twdinspection.databinding.ActivityReportRegistersBinding;
import com.cgg.twdinspection.inspection.reports.source.InspReportData;
import com.cgg.twdinspection.inspection.ui.BaseActivity;
import com.google.gson.Gson;

public class ReportsRegistersActivity extends BaseActivity {
    private static final String TAG = ReportsRegistersActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    ActivityReportRegistersBinding binding;
    private InspReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_registers);

        TextView[] ids = new TextView[]{binding.slno1};
        BaseActivity.setIds(ids, 84);

        binding.actionBar.headerTitle.setText(getString(R.string.title_registers));
        binding.actionBar.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportsRegistersActivity.this, InstReportsMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
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

        String jsonObject = gson.toJson(reportData.getRegisters());
        if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("{}")) {
            binding.setInspData(reportData.getRegisters());
            binding.executePendingBindings();
        }

        binding.btnLayout.btnNext.setText(getResources().getString(R.string.next));
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportsRegistersActivity.this, ReportGeneralCommentsActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
