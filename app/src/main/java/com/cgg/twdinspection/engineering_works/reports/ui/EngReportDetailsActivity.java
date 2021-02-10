package com.cgg.twdinspection.engineering_works.reports.ui;

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

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.screenshot.PDFUtil;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityEngReportDetailsBinding;
import com.cgg.twdinspection.engineering_works.reports.source.ReportWorkDetails;
import com.cgg.twdinspection.gcc.reports.adapter.ViewPhotoAdapterPdf;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EngReportDetailsActivity extends AppCompatActivity implements PDFUtil.PDFUtilListener {

    ActivityEngReportDetailsBinding binding;
    private SharedPreferences sharedPreferences;
    private ReportWorkDetails reportWorkDetails;
    CustomProgressDialog customProgressDialog;
    String directory_path, filePath;
    Gson gson;
    ViewPhotoAdapterPdf adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_eng_report_details);
        binding.header.headerTitle.setText(getString(R.string.engineering_works_rep));
        binding.btnLayout.btnNext.setText(getString(R.string.next));

        binding.header.ivPdf.setVisibility(View.VISIBLE);
        customProgressDialog = new CustomProgressDialog(this);

        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EngReportDetailsActivity.this, DashboardMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        binding.header.ivPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customProgressDialog.show();
                customProgressDialog.addText("Please wait...Downloading Pdf");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                                directory_path = getExternalFilesDir(null)
                                        + "/" + "CTW/Engineering Works/";
                            } else {
                                directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                        + "/" + "CTW/Engineering Works/";
                            }

                            filePath = directory_path + "Engineering_Works_" + reportWorkDetails.getOfficerId() + "_" + reportWorkDetails.getInspectionTime();

                            List<View> views = new ArrayList<>();
                            views.add(binding.engWorksPdf1);
                            views.add(binding.engWorksPdf2);
                            views.add(binding.photosPdf);

                            PDFUtil.getInstance(EngReportDetailsActivity.this).generatePDF(views, filePath + "_temp" + ".pdf", EngReportDetailsActivity.this, "schemes", "GCC");

                        } catch (Exception e) {
                            if (customProgressDialog.isShowing())
                                customProgressDialog.hide();

                            Toast.makeText(EngReportDetailsActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 10000);
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
            gson = new Gson();
            String data = sharedPreferences.getString(AppConstants.ENG_REPORT_DATA, "");
            reportWorkDetails = gson.fromJson(data, ReportWorkDetails.class);
            binding.setWorkDetails(reportWorkDetails);
            binding.executePendingBindings();

        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        try {
            if (reportWorkDetails != null) {

                binding.tvDate.setText(reportWorkDetails.getInspectionTime());

                String jsonObject = gson.toJson(reportWorkDetails.getPhotos());
                if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("[]")) {
                    adapter = new ViewPhotoAdapterPdf(this, reportWorkDetails.getPhotos());
                    binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                    binding.recyclerView.setAdapter(adapter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       /* try {
            if (reportWorkDetails.getPhotos() != null && reportWorkDetails.getPhotos().size() > 0) {

                if (reportWorkDetails.getPhotos().size() >= 1 && reportWorkDetails.getPhotos().get(0) != null && reportWorkDetails.getPhotos().get(0) != null) {

                    binding.pbar.setVisibility(View.VISIBLE);


                    Glide.with(EngReportDetailsActivity.this)
                            .load(reportWorkDetails.getPhotos().get(0).getFilePath())
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
                            Utils.displayPhotoDialogBox(reportWorkDetails.getPhotos().get(0).getFilePath(), EngReportDetailsActivity.this, "", false);
                        }
                    });
                }

                if (reportWorkDetails.getPhotos().size() >= 2 && reportWorkDetails.getPhotos().get(1) != null && reportWorkDetails.getPhotos().get(1) != null) {


                    binding.pbar2.setVisibility(View.VISIBLE);

                    Glide.with(EngReportDetailsActivity.this)
                            .load(reportWorkDetails.getPhotos().get(1).getFilePath())
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
                            Utils.displayPhotoDialogBox(reportWorkDetails.getPhotos().get(1).getFilePath(), EngReportDetailsActivity.this, "", false);
                        }
                    });

                }
            } else {
                binding.llPhotos.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }*/

    }

    public void addWatermark() throws IOException, DocumentException {

        PdfReader reader = new PdfReader(filePath + "_temp" + ".pdf");
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(filePath + ".pdf"));
        Font f = new Font(Font.FontFamily.HELVETICA, 11);
        f.setColor(BaseColor.GRAY);
        int n = reader.getNumberOfPages();

        for (int i = 1; i <= n; i++) {
            PdfContentByte over = stamper.getOverContent(i);
            Phrase p1 = new Phrase(reportWorkDetails.getOfficerId() + ", " + sharedPreferences.getString(AppConstants.OFFICER_DES, ""), f);
            ColumnText.showTextAligned(over, Element.ALIGN_RIGHT, p1, 550, 30, 0);
            Phrase p2 = new Phrase("Inspection Report-Engineering Works" + ", " + reportWorkDetails.getInspectionTime(), f);
            ColumnText.showTextAligned(over, Element.ALIGN_RIGHT, p2, 550, 15, 0);
            over.saveState();
            PdfGState gs1 = new PdfGState();
            gs1.setFillOpacity(0.5f);
            over.setGState(gs1);
            over.restoreState();
        }
        stamper.close();
        reader.close();
    }

    @Override
    public void pdfGenerationSuccess(File savedPDFFile) {
        customProgressDialog.hide();
        File savedFile = new File(filePath + ".pdf");
        customProgressDialog.hide();
        try {
            addWatermark();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        File file = new File(filePath + "_temp" + ".pdf");
        file.delete();
        Utils.customPDFAlert(EngReportDetailsActivity.this, getString(R.string.app_name),
                "PDF File Generated Successfully. \n File saved at " + savedFile + "\n Do you want open it?", savedFile);
    }

    @Override
    public void pdfGenerationFailure(Exception exception) {
        customProgressDialog.hide();

        Utils.customErrorAlert(EngReportDetailsActivity.this, getString(R.string.app_name), getString(R.string.something) + " " + exception.getMessage());
    }

}
