package com.cgg.twdinspection.inspection.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.ActivityReportDietIssuesBinding;
import com.cgg.twdinspection.inspection.reports.adapter.DietIssuesReportAdapter;
import com.cgg.twdinspection.inspection.reports.source.DietListEntity;
import com.cgg.twdinspection.inspection.reports.source.InspReportData;
import com.cgg.twdinspection.inspection.ui.BaseActivity;
import com.google.gson.Gson;

import java.util.List;

public class ReportsDietIssuesActivity extends BaseActivity {
    private static final String TAG = ReportsDietIssuesActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    ActivityReportDietIssuesBinding binding;
    private InspReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_diet_issues);
        binding.actionBar.headerTitle.setText(getString(R.string.diet_issues));
        binding.actionBar.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportsDietIssuesActivity.this, InstReportsMenuActivity.class));
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


        String jsonObject = gson.toJson(reportData.getDietIssues());
        if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("{}")) {

            binding.setInspData(reportData.getDietIssues());
            if(reportData.getDietIssues().getDietListEntities()!=null && reportData.getDietIssues().getDietListEntities().size()>0){
                setAdapter(reportData.getDietIssues().getDietListEntities());
            }
            if (reportData.getPhotos() != null && reportData.getPhotos().size() > 0) {
                boolean menuFlag = false, officerFlag = false;
                for (int z = 0; z < reportData.getPhotos().size(); z++) {
                    if (reportData.getPhotos().get(z).getFileName().equalsIgnoreCase("MENU.png")) {

                        binding.pbar.setVisibility(View.VISIBLE);

                        Glide.with(ReportsDietIssuesActivity.this)
                                .load(reportData.getPhotos().get(z).getFilePath())
                                .error(R.drawable.no_image)
                                .placeholder(R.drawable.camera)
                                .listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        binding.pbar.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        binding.pbar.setVisibility(View.GONE);
                                        return false;
                                    }
                                })
                                .into(binding.ivMenu);

                        menuFlag = true;
                    }
                    if (reportData.getPhotos().get(z).getFileName().equalsIgnoreCase("OFFICER.png")) {
                        binding.pbar2.setVisibility(View.VISIBLE);

                        Glide.with(ReportsDietIssuesActivity.this)
                                .load(reportData.getPhotos().get(z).getFilePath())
                                .error(R.drawable.no_image)
                                .placeholder(R.drawable.camera)
                                .listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        binding.pbar2.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        binding.pbar2.setVisibility(View.GONE);
                                        return false;
                                    }
                                })
                                .into(binding.ivInspOfficer);

                        officerFlag = true;
                    }

                    if (menuFlag && officerFlag) {
                        return;
                    }
                }
            }
            binding.executePendingBindings();
        }


        binding.btnLayout.btnNext.setText(getResources().getString(R.string.next));
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportsDietIssuesActivity.this, ReportsInfraActivity.class));
            }
        });
    }
    private void setAdapter(List<DietListEntity > dietListEntities) {
        DietIssuesReportAdapter dietIssuesReportAdapter = new DietIssuesReportAdapter(this, dietListEntities);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setAdapter(dietIssuesReportAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
