package com.cgg.twdinspection.gcc.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

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
import com.cgg.twdinspection.databinding.ActivityLpgInspRepBinding;
import com.cgg.twdinspection.gcc.reports.adapter.ViewPhotoAdapterPdf;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LPGInspRepActivity extends AppCompatActivity implements PDFUtil.PDFUtilListener {

    ActivityLpgInspRepBinding binding;
    private SharedPreferences sharedPreferences;
    ReportData reportData;
    CustomProgressDialog customProgressDialog;
    String directory_path, filePath;
    ViewPhotoAdapterPdf adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lpg_insp_rep);

        binding.bottomLl.btnNext.setText("Next");
        binding.header.headerTitle.setText("LPG FINDINGS REPPORT");
        binding.header.ivPdf.setVisibility(View.GONE);
        customProgressDialog = new CustomProgressDialog(this);

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LPGInspRepActivity.this, GCCReportsDashboard.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
        sharedPreferences = TWDApplication.get(LPGInspRepActivity.this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.REP_DATA, "");
        reportData = gson.fromJson(data, ReportData.class);
        try {
            if (reportData != null) {
                binding.divName.setText(reportData.getDivisionName());
                binding.socName.setText(reportData.getSocietyName());
                binding.drGodownName.setText(reportData.getGodownName());
                binding.inchargeName.setText(reportData.getInchargeName());
                binding.tvDate.setText(reportData.getInspectionTime());
                binding.tvOfficerName.setText(reportData.getOfficerId());
                binding.tvOfficerDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));

                String jsonObject = gson.toJson(reportData.getPhotos());
                if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("[]")) {
                    adapter = new ViewPhotoAdapterPdf(this, reportData.getPhotos());
                    binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                    binding.recyclerView.setAdapter(adapter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (reportData != null && reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getLpg() != null) {
            binding.setInspData(reportData.getInspectionFindings().getLpg());
        } else {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
        }
        if (reportData != null && reportData.getPhotos() != null && reportData.getPhotos().size() > 0) {
            for (int z = 0; z < reportData.getPhotos().size(); z++) {
                if (!TextUtils.isEmpty(reportData.getPhotos().get(z).getFileName())
                        && reportData.getPhotos().get(z).getFileName().contains(AppConstants.REPAIR)) {
                    Glide.with(LPGInspRepActivity.this)
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
                            .into(binding.ivRepairs);
                    Glide.with(LPGInspRepActivity.this)
                            .load(reportData.getPhotos().get(z).getFilePath())
                            .error(R.drawable.no_image)
                            .placeholder(R.drawable.camera)
                            .into(binding.ivRepairsPdf);
                    break;
                }

                int pos = reportData.getPhotos().size() - 1;
                binding.ivRepairs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.displayPhotoDialogBox(reportData.getPhotos().get(pos).getFilePath(), LPGInspRepActivity.this, reportData.getPhotos().get(pos).getFileName(), true);
                    }
                });
            }
        } else {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
        }

        binding.header.ivPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customProgressDialog.show();
                customProgressDialog.addText("Please wait...Downloading Pdf");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                                directory_path = getExternalFilesDir(null)
                                        + "/" + "CTW/GCC/";
                            } else {
                                directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                        + "/" + "CTW/GCC/";
                            }

                            filePath = directory_path + "LPG_" + reportData.getOfficerId() + "_" + reportData.getInspectionTime() + ".pdf";
                            File file = new File(filePath);
                            List<View> views = new ArrayList<>();
                            views.add(binding.titlePdf);
                            views.add(binding.generalPdf);
                            views.add(binding.photosPdf);

                            PDFUtil.getInstance(LPGInspRepActivity.this).generatePDF(views, filePath, LPGInspRepActivity.this, "schemes", "GCC");

                        } catch (Exception e) {
                            if (customProgressDialog.isShowing())
                                customProgressDialog.hide();

                            Toast.makeText(LPGInspRepActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 10000);

            }
        });
        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LPGInspRepActivity.this, ViewPhotosActivity.class)
                        .putExtra(AppConstants.PHOTO_TITLE, "LPG PHOTOS"));
            }
        });
    }

    @Override
    public void pdfGenerationSuccess(File savedPDFFile) {
        customProgressDialog.hide();

        Utils.customPDFAlert(LPGInspRepActivity.this, getString(R.string.app_name),
                "PDF File Generated Successfully. \n File saved at " + savedPDFFile + "\n Do you want open it?", savedPDFFile);
    }

    @Override
    public void pdfGenerationFailure(Exception exception) {
        customProgressDialog.hide();

        Utils.customErrorAlert(LPGInspRepActivity.this, getString(R.string.app_name), getString(R.string.something) + " " + exception.getMessage());
    }
}
