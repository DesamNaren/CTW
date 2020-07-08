package com.cgg.twdinspection.gcc.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.cgg.twdinspection.databinding.ActivityDrGodownInspRepBinding;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DrGodownInspRepActivity extends AppCompatActivity implements PDFUtil.PDFUtilListener{

    ActivityDrGodownInspRepBinding binding;
    private SharedPreferences sharedPreferences;
    ReportData reportData;
    CustomProgressDialog customProgressDialog;
    String directory_path, filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_godown_insp_rep);

        binding.bottomLl.btnNext.setText("Next");
        binding.header.headerTitle.setText("DR GODOWN FINDINGS REPORT");
        binding.header.ivPdf.setVisibility(View.VISIBLE);
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
                startActivity(new Intent(DrGodownInspRepActivity.this, GCCReportsDashboard.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
        sharedPreferences = TWDApplication.get(DrGodownInspRepActivity.this).getPreferences();
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

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (reportData != null && reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getDrGodown() != null) {
            binding.setInspData(reportData.getInspectionFindings().getDrGodown());
        } else {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
        }
        if (reportData != null && reportData.getPhotos() != null && reportData.getPhotos().size() > 0) {
            for (int z = 0; z < reportData.getPhotos().size(); z++) {
                if (!TextUtils.isEmpty(reportData.getPhotos().get(z).getFileName())
                        && reportData.getPhotos().get(z).getFileName().contains(AppConstants.REPAIR)) {
                    Glide.with(DrGodownInspRepActivity.this)
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
                    Glide.with(DrGodownInspRepActivity.this)
                            .load(reportData.getPhotos().get(z).getFilePath())
                            .error(R.drawable.no_image)
                            .placeholder(R.drawable.camera)
                            .into(binding.ivRepairsPdf);
                    break;
                }

                int pos = z;
                binding.ivRepairs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.displayPhotoDialogBox(reportData.getPhotos().get(pos).getFilePath(),
                                DrGodownInspRepActivity.this, reportData.getPhotos().get(pos).getFileName(), true);
                    }
                });
            }
        } else {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
        }
        binding.header.ivPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    customProgressDialog.show();

                    directory_path = getExternalFilesDir(null)
                            + "/" + "TWD/GCC/";

                    filePath = directory_path + "Dr_Godown_" + reportData.getOfficerId() + "_" + reportData.getInspectionTime() + ".pdf";
                    File file = new File(filePath);
                    List<View> views = new ArrayList<>();
                    views.add(binding.titlePdf);
                    views.add(binding.generalPdf);

                    PDFUtil.getInstance().generatePDF(views, filePath, DrGodownInspRepActivity.this, "schemes");

                } catch (Exception e) {
                    if (customProgressDialog.isShowing())
                        customProgressDialog.hide();

                    Toast.makeText(DrGodownInspRepActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrGodownInspRepActivity.this, ViewPhotosActivity.class)
                        .putExtra(AppConstants.PHOTO_TITLE, "DR GODOWN PHOTOS"));
            }
        });

    }
    @Override
    public void pdfGenerationSuccess(File savedPDFFile) {
        customProgressDialog.hide();

        Utils.customSyncSuccessAlert(DrGodownInspRepActivity.this, getString(R.string.app_name), "PDF saved successfully at " + savedPDFFile.getPath().toString());
    }

    @Override
    public void pdfGenerationFailure(Exception exception) {
        customProgressDialog.hide();

        Utils.customErrorAlert(DrGodownInspRepActivity.this, getString(R.string.app_name), getString(R.string.something) + " " + exception.getMessage());
    }
}
