package com.cgg.twdinspection.inspection.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.ActivityReportInfrastructureBinding;
import com.cgg.twdinspection.inspection.reports.source.InspReportData;
import com.cgg.twdinspection.inspection.ui.BaseActivity;
import com.cgg.twdinspection.schemes.reports.ui.SchemeReportDetailsActivity;
import com.google.gson.Gson;

public class ReportsInfraActivity extends BaseActivity {
    private static final String TAG = ReportsInfraActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    ActivityReportInfrastructureBinding binding;
    private String officerID, instID, insTime;
    private InspReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_infrastructure);
        binding.actionBar.headerTitle.setText(getString(R.string.title_infra));
        binding.actionBar.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportsInfraActivity.this, InstReportsMenuActivity.class));
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

        String jsonObject  = gson.toJson(reportData.getInfraMaintenance());
        if(!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("{}")) {
            binding.setInspData(reportData.getInfraMaintenance());
//
//            if (reportData.getPhotos() != null && reportData.getPhotos().size() > 0) {
//                for (int z = 0; z < reportData.getPhotos().size(); z++) {
//                    if (reportData.getPhotos().get(z).getFileName().equalsIgnoreCase("TDS.png")) {
//
//                        binding.pbar.setVisibility(View.VISIBLE);
//
//                        Glide.with(ReportsInfraActivity.this)
//                                .load(reportData.getPhotos().get(z).getFilePath())
//                                .error(R.drawable.no_image)
//                                .placeholder(R.drawable.camera)
//                                .listener(new RequestListener<String, GlideDrawable>() {
//                                    @Override
//                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                                        binding.pbar.setVisibility(View.GONE);
//                                        return false;
//                                    }
//
//                                    @Override
//                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                        binding.pbar.setVisibility(View.GONE);
//                                        return false;
//                                    }
//                                })
//                                .into(binding.ivTds);
//
//                        return;
//                    }
//                }
//            }
            binding.executePendingBindings();
        }



        binding.btnLayout.btnNext.setText(getResources().getString(R.string.next));
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportsInfraActivity.this, ReportsAcademicActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
