package com.cgg.twdinspection.inspection.reports.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.graphics.Bitmap;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.screenshot.ScreenshotUtil;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.ActivityPreviewPdfBinding;
import com.cgg.twdinspection.inspection.reports.source.InspReportData;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;

public class PreviewPdfActivity extends AppCompatActivity {

    ActivityPreviewPdfBinding binding;
    SharedPreferences sharedPreferences;
    private Bitmap bitmap;
    private InspReportData inspReportData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preview_pdf);
        binding.diet.header.setVisibility(View.GONE);
        binding.generalInfo.header.setVisibility(View.GONE);
        binding.medical.header.setVisibility(View.GONE);
        binding.medical.btnLayout.btnLayout.setVisibility(View.GONE);
        binding.generalInfo.btnNext.setVisibility(View.GONE);
        binding.diet.btnLayout.btnLayout.setVisibility(View.GONE);

        binding.actionBar.ivPdf.setVisibility(View.VISIBLE);
        binding.btnLayout.btnNext.setText("Get PDF");
        sharedPreferences = TWDApplication.get(this).getPreferences();

        Gson gson=new Gson();
        String data = sharedPreferences.getString(AppConstants.INSP_REP_DATA, "");
        inspReportData=gson.fromJson(data, InspReportData.class);

        String medical  = gson.toJson(inspReportData.getMedicalIssues());
        if(!TextUtils.isEmpty(medical) && !medical.equalsIgnoreCase("{}")) {
            binding.medical.setMedical(inspReportData.getMedicalIssues());
            binding.executePendingBindings();
        }
        String generalInfo  = gson.toJson(inspReportData.getGeneralInfo());
        if(!TextUtils.isEmpty(generalInfo) && !generalInfo.equalsIgnoreCase("{}")) {
            binding.generalInfo.setInspData(inspReportData.getGeneralInfo());
            binding.executePendingBindings();
        }
        String diet  = gson.toJson(inspReportData.getDietIssues());
        if(!TextUtils.isEmpty(diet) && !diet.equalsIgnoreCase("{}")) {
            binding.diet.setInspData(inspReportData.getDietIssues());
            binding.executePendingBindings();
        }

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                bitmap = ScreenshotUtil.getInstance().takeScreenshotForView(binding.scrollView); // Take ScreenshotUtil for any view
//                Log.i("bitmap:",bitmap.toString());
            }
        });
    }
}
