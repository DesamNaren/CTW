package com.cgg.twdinspection.inspection.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.ActivityReportGeneralInfoBinding;
import com.cgg.twdinspection.inspection.reports.source.InspReportData;
import com.cgg.twdinspection.inspection.ui.BaseActivity;
import com.google.gson.Gson;

public class ReportsGeneralInfoActivity extends AppCompatActivity {
    private static final String TAG = ReportsGeneralInfoActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    ActivityReportGeneralInfoBinding binding;
    private InspReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_general_info);

        TextView[] ids = new TextView[]{binding.slno1, binding.slno2, binding.slno3, binding.slno4, binding.slno5,
                binding.slno6, binding.slno7, binding.slno8};
        BaseActivity.setIds(ids, 0);

        binding.actionBar.headerTitle.setText(getString(R.string.general_info));
        binding.actionBar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.actionBar.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportsGeneralInfoActivity.this, InstReportsMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        sharedPreferences = TWDApplication.get(this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.INSP_REP_DATA, "");
        reportData = gson.fromJson(data, InspReportData.class);

        String jsonObject = gson.toJson(reportData.getGeneralInfo());
        if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("{}")) {
            binding.setInspData(reportData.getGeneralInfo());
            binding.manNameTv.setText(reportData.getMandalName() + " & " + reportData.getVillageName());
            binding.disNameTv.setText(reportData.getDistName());
            binding.instNameTv.setText(reportData.getInstituteName());

            binding.includeBasicLayout.inspectionTime.setText(reportData.getGeneralInfo().getInspectionTime());
            binding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_ID, ""));
            binding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            binding.executePendingBindings();
        }

        binding.btnNext.setText(getResources().getString(R.string.next));
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportsGeneralInfoActivity.this, ReportStudentsAttendActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
