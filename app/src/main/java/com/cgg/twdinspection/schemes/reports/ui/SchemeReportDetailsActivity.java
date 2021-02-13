package com.cgg.twdinspection.schemes.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.screenshot.PDFUtil;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityReportSchemeDetailsActivtyBinding;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
import com.cgg.twdinspection.schemes.reports.source.SchemeReportData;
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

public class SchemeReportDetailsActivity extends AppCompatActivity implements PDFUtil.PDFUtilListener {

    ActivityReportSchemeDetailsActivtyBinding binding;
    private SharedPreferences sharedPreferences;
    private SchemeReportData schemeReportData;
    CustomProgressDialog customProgressDialog;
    String directory_path, filePath;
    private String TAG = SchemeReportDetailsActivity.class.getSimpleName();
    String folder = "Schemes";
    private String filePath_temp;

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
//        binding.tvOfficerName.setText(schemeReportData.getOfficerId() + ", " + sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
//        binding.tvOfficerDes.setText("Inspection Report-Schemes" + ", " + schemeReportData.getInspectionTime());

        customProgressDialog = new CustomProgressDialog(this);
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchemeReportDetailsActivity.this, DashboardMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
        binding.header.ivPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    customProgressDialog.show();
                    List<View> views = new ArrayList<>();
                    views.add(binding.scrlPdf);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                        filePath_temp = PDFUtil.createPdfFile(SchemeReportDetailsActivity.this,
                                "Schemes_" + schemeReportData.getOfficerId()
                                        + "_" + schemeReportData.getInspectionTime() + "_temp", folder);
                    } else {
                        directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                + "/" + "CTW/Schemes/";

                        filePath_temp = directory_path + "Schemes_" + schemeReportData.getOfficerId() + "_" +
                                schemeReportData.getInspectionTime();
                        filePath_temp = filePath + "_temp" + ".pdf";
                    }

                    PDFUtil.getInstance(SchemeReportDetailsActivity.this).generatePDF(views,filePath_temp , SchemeReportDetailsActivity.this, "schemes", "Schemes");
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
                            .into(binding.ivCam1);
                    Glide.with(SchemeReportDetailsActivity.this)
                            .load(schemeReportData.getPhotos().get(0).getFilePath())
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
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    binding.pbar2.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    binding.pbar2.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(binding.ivCam2);

                    Glide.with(SchemeReportDetailsActivity.this)
                            .load(schemeReportData.getPhotos().get(1).getFilePath())
                            .error(R.drawable.no_image)
                            .placeholder(R.drawable.camera)
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    binding.pbar2.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
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

    public void addWatermark(File savedFile) throws IOException, DocumentException {
        PdfStamper stamper;
        PdfReader reader;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            reader = new PdfReader(filePath_temp);
            stamper = new PdfStamper(reader, new FileOutputStream(filePath));
        } else {
            reader = new PdfReader(filePath + "_temp" + ".pdf");
            stamper = new PdfStamper(reader, new FileOutputStream(filePath + ".pdf"));
        }

        Font f = new Font(Font.FontFamily.HELVETICA, 11);
        f.setColor(BaseColor.GRAY);
        int n = reader.getNumberOfPages();

        for (int i = 1; i <= n; i++) {
            PdfContentByte over = stamper.getOverContent(i);
            Phrase p1 = new Phrase(schemeReportData.getOfficerId() + ", " + sharedPreferences.getString(AppConstants.OFFICER_DES, ""), f);
            ColumnText.showTextAligned(over, Element.ALIGN_RIGHT, p1, 550, 30, 0);
            Phrase p2 = new Phrase("Inspection Report-Schemes" + ", " + schemeReportData.getInspectionTime(), f);
            ColumnText.showTextAligned(over, Element.ALIGN_RIGHT, p2, 550, 15, 0);
            over.saveState();
            PdfGState gs1 = new PdfGState();
            gs1.setFillOpacity(0.5f);
            over.setGState(gs1);
            over.restoreState();
        }
        stamper.close();
        reader.close();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            File file = new File(filePath_temp);
            file.delete();
        } else {
            File file = new File(filePath + "_temp" + ".pdf");
            file.delete();
        }
        Utils.customPDFAlert(SchemeReportDetailsActivity.this, getString(R.string.app_name),
                "PDF File Generated Successfully. \n File saved at " + savedFile + "\n Do you want open it?", savedFile);
    }

    @Override
    public void pdfGenerationSuccess(File savedPDFFile) {
        customProgressDialog.hide();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            filePath = PDFUtil.createPdfFile(SchemeReportDetailsActivity.this, "Schemes_" +
                    schemeReportData.getOfficerId()
                    + "_" + schemeReportData.getInspectionTime() + ".pdf", folder);

            savedPDFFile = new File(filePath);

        } else {
            savedPDFFile = new File(filePath + ".pdf");
        }

        try {
            addWatermark(savedPDFFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void pdfGenerationFailure(Exception exception) {
        customProgressDialog.hide();

        Utils.customErrorAlert(SchemeReportDetailsActivity.this, getString(R.string.app_name), getString(R.string.something) + " " + exception.getMessage());
    }

}
