package com.cgg.twdinspection.engineering_works.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityEngReportDetailsBinding;
import com.cgg.twdinspection.databinding.ActivityReportSchemeDetailsActivtyBinding;
import com.cgg.twdinspection.engineering_works.reports.source.ReportWorkDetails;
import com.cgg.twdinspection.inspection.reports.ui.ReportCoCarricularActivity;
import com.cgg.twdinspection.inspection.ui.DashboardActivity;
import com.cgg.twdinspection.inspection.ui.PlantsInfoActivity;
import com.cgg.twdinspection.schemes.reports.source.SchemeReportData;
import com.google.gson.Gson;

public class EngReportDetailsActivity extends AppCompatActivity {

    ActivityEngReportDetailsBinding binding;
    private SharedPreferences sharedPreferences;
    private ReportWorkDetails reportWorkDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_eng_report_details);
        binding.header.headerTitle.setText(getString(R.string.engineering_works));
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EngReportDetailsActivity.this, DashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });



        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EngReportDetailsActivity.this, ViewEngPhotosActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra(AppConstants.FROM_CLASS, AppConstants.REPORT_COCAR));

            }
        });



        try {
            sharedPreferences = TWDApplication.get(EngReportDetailsActivity.this).getPreferences();
            Gson gson = new Gson();
            String data = sharedPreferences.getString(AppConstants.ENG_REPORT_DATA, "");
            reportWorkDetails = gson.fromJson(data, ReportWorkDetails.class);
            binding.setWorkDetails(reportWorkDetails);
            binding.executePendingBindings();

        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        try {
//            if (reportWorkDetails.getPhotos() != null && reportWorkDetails.getPhotos().size() > 0) {

//                if (reportWorkDetails.getPhotos().size() >= 1 && reportWorkDetails.getPhotos().get(0) != null && reportWorkDetails.getPhotos().get(0) != null) {
//
//                    binding.pbar.setVisibility(View.VISIBLE);
//
//
//                    Glide.with(EngReportDetailsActivity.this)
//                            .load(reportWorkDetails.getPhotos().get(0).getFilePath())
//                            .error(R.drawable.no_image)
//                            .placeholder(R.drawable.camera)
//                            .listener(new RequestListener<String, GlideDrawable>() {
//                                @Override
//                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                                    binding.pbar.setVisibility(View.GONE);
//                                    return false;
//                                }
//
//                                @Override
//                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                    binding.pbar.setVisibility(View.GONE);
//                                    return false;
//                                }
//                            })
//                            .into(binding.ivCam1);
//
//                    binding.ivCam1.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Utils.displayPhotoDialogBox(reportWorkDetails.getPhotos().get(0).getFilePath(), EngReportDetailsActivity.this, "", false);
//                        }
//                    });
//                }

//                if (reportWorkDetails.getPhotos().size() >= 2 && reportWorkDetails.getPhotos().get(1) != null && reportWorkDetails.getPhotos().get(1) != null) {
//
//
//                    binding.pbar2.setVisibility(View.VISIBLE);
//
//                    Glide.with(EngReportDetailsActivity.this)
//                            .load(reportWorkDetails.getPhotos().get(1).getFilePath())
//                            .error(R.drawable.no_image)
//                            .placeholder(R.drawable.camera)
//                            .listener(new RequestListener<String, GlideDrawable>() {
//                                @Override
//                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                                    binding.pbar2.setVisibility(View.GONE);
//                                    return false;
//                                }
//
//                                @Override
//                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                    binding.pbar2.setVisibility(View.GONE);
//                                    return false;
//                                }
//                            })
//                            .into(binding.ivCam2);
//
//                    binding.ivCam2.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Utils.displayPhotoDialogBox(reportWorkDetails.getPhotos().get(1).getFilePath(), EngReportDetailsActivity.this, "", false);
//                        }
//                    });
//
//                }
//            } else {
//                binding.llPhotos.setVisibility(View.GONE);
//            }


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}