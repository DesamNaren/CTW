package com.cgg.twdinspection.engineering_works.reports.ui;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.screenshot.PDFUtil;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityEngPdfBinding;
import com.cgg.twdinspection.engineering_works.reports.source.ReportWorkDetails;
import com.cgg.twdinspection.gcc.reports.adapter.ViewPhotoAdapter;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EngPDFActivity extends AppCompatActivity implements PDFUtil.PDFUtilListener {
    ActivityEngPdfBinding binding;
    CustomProgressDialog customProgressDialog;
    String directory_path, filePath;
    private ReportWorkDetails reportWorkDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_eng_pdf);
        customProgressDialog = new CustomProgressDialog(this);
        binding.engDetails.header.getRoot().setVisibility(View.GONE);
        binding.engDetails.btnLayout.btnLayout.setVisibility(View.GONE);
        binding.photos.header.getRoot().setVisibility(View.GONE);

        binding.btnLayout.btnNext.setText(getString(R.string.get_pdf));
        binding.actionBar.headerTitle.setText(R.string.pdf_preview);

        try {
            SharedPreferences sharedPreferences = TWDApplication.get(EngPDFActivity.this).getPreferences();
            Gson gson = new Gson();
            String data = sharedPreferences.getString(AppConstants.ENG_REPORT_DATA, "");
            reportWorkDetails = gson.fromJson(data, ReportWorkDetails.class);
            binding.engDetails.setWorkDetails(reportWorkDetails);
            binding.executePendingBindings();
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if (reportWorkDetails.getPhotos().size() > 0) {
            binding.photos.recyclerView.setVisibility(View.VISIBLE);
            binding.photos.tvEmpty.setVisibility(View.GONE);
            ViewPhotoAdapter adapter = new ViewPhotoAdapter(EngPDFActivity.this, reportWorkDetails.getPhotos());
            binding.photos.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            binding.photos.recyclerView.setAdapter(adapter);
        } else {
            binding.photos.tvEmpty.setVisibility(View.VISIBLE);
            binding.photos.recyclerView.setVisibility(View.GONE);
        }

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                try {
                    customProgressDialog.show();
                    List<View> views = new ArrayList<>();
                    views.add(binding.engDetails.scrl);
                    views.add(binding.photos.recyclerView);

                    directory_path = getExternalFilesDir(null)
                            + "/" + "TWD/Engineering_Works/";

                    filePath = directory_path + "works_" + reportWorkDetails.getWorkId() + "_" + reportWorkDetails.getInspectionTime() + ".pdf";
                    File file = new File(filePath);
                    PDFUtil.getInstance(EngPDFActivity.this).generatePDF(views, filePath, EngPDFActivity.this);
                } catch (Exception e) {
                    if (customProgressDialog.isShowing())
                        customProgressDialog.hide();
                    Toast.makeText(EngPDFActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void pdfGenerationSuccess(File savedPDFFile) {
        customProgressDialog.hide();
        Utils.customSyncSuccessAlert(EngPDFActivity.this, getString(R.string.app_name), "PDF saved successfully at " + savedPDFFile.getPath().toString());
    }

    @Override
    public void pdfGenerationFailure(Exception exception) {
        customProgressDialog.hide();
        Utils.customErrorAlert(EngPDFActivity.this, getString(R.string.app_name), getString(R.string.something) + " " + exception.getMessage());
    }

}
