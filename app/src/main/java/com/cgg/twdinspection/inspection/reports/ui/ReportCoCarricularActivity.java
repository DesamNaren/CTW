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
import com.cgg.twdinspection.databinding.ActivityReportCoCurricularBinding;
import com.cgg.twdinspection.inspection.reports.source.InspReportData;
import com.cgg.twdinspection.inspection.ui.BaseActivity;
import com.cgg.twdinspection.inspection.ui.CocurricularStudAchActivity;
import com.google.gson.Gson;

public class ReportCoCarricularActivity extends BaseActivity {
    SharedPreferences sharedPreferences;
    ActivityReportCoCurricularBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_co_curricular);

        TextView[] ids = new TextView[]{binding.slno1, binding.slno2, binding.slno3, binding.slno4, binding.slno5,
                binding.slno6, binding.slno7, binding.slno8};
        BaseActivity.setIds(ids, 70);

        binding.actionBar.headerTitle.setText(getString(R.string.title_co_cir));
        binding.actionBar.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportCoCarricularActivity.this, InstReportsMenuActivity.class)
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
        InspReportData reportData = gson.fromJson(data, InspReportData.class);


        binding.btnNext.setText(getResources().getString(R.string.next));
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportCoCarricularActivity.this, ReportsEntitlementsActivity.class));
            }
        });

        String jsonObject = gson.toJson(reportData.getCoCurricularInfo());
        if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("{}")) {
            binding.setInspData(reportData.getCoCurricularInfo());

          /*  if (reportData.getCoCurricularInfo() != null && reportData.getCoCurricularInfo().getPlantsEntities() != null
                    && reportData.getCoCurricularInfo().getPlantsEntities().size() > 0) {
                binding.btnViewplant.setVisibility(View.VISIBLE);
                binding.btnViewplant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(ReportCoCarricularActivity.this, PlantsInfoActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
                                .putExtra(AppConstants.FROM_CLASS, AppConstants.REPORT_COCAR));
                    }
                });
            }
*/

            if (reportData.getCoCurricularInfo() != null && reportData.getCoCurricularInfo().getStudAchievementEntities() != null
                    && reportData.getCoCurricularInfo().getStudAchievementEntities().size() > 0) {
                binding.btnViewStud.setVisibility(View.VISIBLE);
                binding.btnViewStud.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(ReportCoCarricularActivity.this, CocurricularStudAchActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
                                .putExtra(AppConstants.FROM_CLASS, AppConstants.REPORT_COCAR));
                    }
                });
            }

            binding.executePendingBindings();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
