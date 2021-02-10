package com.cgg.twdinspection.inspection.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.ActivityReportInfrastructureBinding;
import com.cgg.twdinspection.inspection.reports.source.InspReportData;
import com.cgg.twdinspection.inspection.ui.BaseActivity;
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

        TextView[] ids = new TextView[]{binding.slno1, binding.slno2, binding.slno3, binding.slno4, binding.slno5,
                binding.slno6, binding.slno7, binding.slno8, binding.slno9, binding.slno10, binding.slno11, binding.slno12,
                binding.slno13, binding.slno14, binding.slno15, binding.slno16, binding.slno17, binding.slno18,
                binding.slno19, binding.slno20, binding.slno21, binding.slno22, binding.slno23, binding.slno24,
                binding.slno25, binding.slno26};
        BaseActivity.setIds(ids, 27);
        binding.slno261.setText(binding.slno26.getText().toString() + "1.");

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

        String jsonObject = gson.toJson(reportData.getInfraMaintenance());
        if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("{}")) {
            binding.setInspData(reportData.getInfraMaintenance());

            if (reportData.getPhotos() != null && reportData.getPhotos().size() > 0) {
                for (int z = 0; z < reportData.getPhotos().size(); z++) {
                    if (reportData.getPhotos().get(z).getFileName().equalsIgnoreCase("TDS.png")) {

                        binding.pbar.setVisibility(View.VISIBLE);

                        Glide.with(ReportsInfraActivity.this)
                                .load(reportData.getPhotos().get(z).getFilePath())
                                .error(R.drawable.no_image)
                                .placeholder(R.drawable.camera)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        binding.pbar.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        binding.pbar.setVisibility(View.GONE);
                                        return false;
                                    }
                                })
                                .into(binding.ivTds);

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
                startActivity(new Intent(ReportsInfraActivity.this, ReportsAcademicActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
