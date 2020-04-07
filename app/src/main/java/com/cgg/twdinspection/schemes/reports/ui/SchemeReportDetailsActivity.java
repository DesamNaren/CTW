package com.cgg.twdinspection.schemes.reports.ui;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.cgg.twdinspection.databinding.ActivityReportSchemeDetailsActivtyBinding;
import com.cgg.twdinspection.inspection.ui.DashboardActivity;
import com.cgg.twdinspection.schemes.reports.source.SchemeReportData;
import com.google.gson.Gson;

import uk.co.senab.photoview.PhotoViewAttacher;

public class SchemeReportDetailsActivity extends AppCompatActivity {

    ActivityReportSchemeDetailsActivtyBinding binding;
    private SharedPreferences sharedPreferences;
    private SchemeReportData schemeReportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_scheme_details_activty);
        binding.header.headerTitle.setText(getString(R.string.scheme_report_details));
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchemeReportDetailsActivity.this, DashboardActivity.class)
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

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        try {
            sharedPreferences = TWDApplication.get(SchemeReportDetailsActivity.this).getPreferences();
            Gson gson = new Gson();
            String data = sharedPreferences.getString(AppConstants.SCHEME_REP_DATA, "");
            schemeReportData = gson.fromJson(data, SchemeReportData.class);
            binding.setSchemeData(schemeReportData);
            binding.executePendingBindings();

        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        try {
            if (schemeReportData.getPhotos() != null && schemeReportData.getPhotos().size() > 0) {

                if (schemeReportData.getPhotos().size() >= 1 && schemeReportData.getPhotos().get(0) != null && schemeReportData.getPhotos().get(0) != null) {

                    binding.pbar.setVisibility(View.VISIBLE);


                    Glide.with(SchemeReportDetailsActivity.this)
                            .load(schemeReportData.getPhotos().get(0).getFilePath())
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
                            .into(binding.ivCam1);

                    binding.ivCam1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.displayPhotoDialogBox(schemeReportData.getPhotos().get(0).getFilePath(), SchemeReportDetailsActivity.this, "", false);
                        }
                    });
                }

                if (schemeReportData.getPhotos().size() >= 2 && schemeReportData.getPhotos().get(1) != null && schemeReportData.getPhotos().get(1) != null) {


                    binding.pbar2.setVisibility(View.VISIBLE);

                    Glide.with(SchemeReportDetailsActivity.this)
                            .load(schemeReportData.getPhotos().get(1).getFilePath())
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
                            .into(binding.ivCam2);

                    binding.ivCam2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.displayPhotoDialogBox(schemeReportData.getPhotos().get(1).getFilePath(), SchemeReportDetailsActivity.this, "", false);
                        }
                    });

                }
            } else {
                binding.llPhotos.setVisibility(View.GONE);
            }


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}
