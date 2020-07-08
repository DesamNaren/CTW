package com.cgg.twdinspection.schemes.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.cgg.twdinspection.common.screenshot.PDFUtil;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityReportSchemeDetailsActivtyBinding;
import com.cgg.twdinspection.inspection.ui.DashboardActivity;
import com.cgg.twdinspection.schemes.reports.source.SchemeReportData;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SchemeReportDetailsActivity extends AppCompatActivity implements PDFUtil.PDFUtilListener {

    ActivityReportSchemeDetailsActivtyBinding binding;
    private SharedPreferences sharedPreferences;
    private SchemeReportData schemeReportData;
    CustomProgressDialog customProgressDialog;
    String directory_path, filePath;
    private String TAG=SchemeReportDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_scheme_details_activty);

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();

            Gson gson = new Gson();
            String data = sharedPreferences.getString(AppConstants.SCHEME_REP_DATA, "");
            schemeReportData = gson.fromJson(data, SchemeReportData.class);
            binding.setSchemeData(schemeReportData);
            binding.executePendingBindings();

        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        binding.header.headerTitle.setText(getString(R.string.scheme_report_details));
        binding.header.ivPdf.setVisibility(View.VISIBLE);

        binding.tvDate.setText(schemeReportData.getInspectionTime());
        binding.tvOfficerName.setText(schemeReportData.getOfficerId());
        binding.tvOfficerDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));

        customProgressDialog = new CustomProgressDialog(this);
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchemeReportDetailsActivity.this, DashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
        binding.header.ivPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    customProgressDialog.show();

                    directory_path = getExternalFilesDir(null)
                            + "/" + "TWD/Schemes/";

                    filePath = directory_path + "schemes_" + schemeReportData.getBenId() + "_" + schemeReportData.getInspectionTime() + ".pdf";
                    File file = new File(filePath);
                    List<View> views = new ArrayList<>();
                    views.add(binding.scrlPdf);

                    PDFUtil.getInstance().generatePDF(views, filePath, SchemeReportDetailsActivity.this,"schemes");
                    Log.i(TAG, "onClick: try");

                } catch (Exception e) {
                    if (customProgressDialog.isShowing())
                        customProgressDialog.hide();

                    Toast.makeText(SchemeReportDetailsActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                }

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
                            .into(binding.ivCam1Pdf);

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
                            .into(binding.ivCam2Pdf);

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

    @Override
    public void pdfGenerationSuccess(File savedPDFFile) {
        customProgressDialog.hide();

        Utils.customSyncSuccessAlert(SchemeReportDetailsActivity.this, getString(R.string.app_name), "PDF saved successfully at " + savedPDFFile.getPath().toString());
    }

    @Override
    public void pdfGenerationFailure(Exception exception) {
        customProgressDialog.hide();

        Utils.customErrorAlert(SchemeReportDetailsActivity.this, getString(R.string.app_name), getString(R.string.something) + " " + exception.getMessage());
    }

}
