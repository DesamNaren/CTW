package com.cgg.twdinspection.gcc.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.screenshot.PDFUtil;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityPunitInspRepBinding;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PUnitInspRepActivity extends AppCompatActivity implements PDFUtil.PDFUtilListener {

    ActivityPunitInspRepBinding binding;
    SharedPreferences sharedPreferences;
    ReportData reportData;
    CustomProgressDialog customProgressDialog;
    String directory_path, filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_punit_insp_rep);

        binding.bottomLl.btnNext.setText("Next");
        binding.header.headerTitle.setText("PROCESSING UNIT FINDINGS REPORT");
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
                startActivity(new Intent(PUnitInspRepActivity.this, GCCReportsDashboard.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
        sharedPreferences = TWDApplication.get(PUnitInspRepActivity.this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.REP_DATA, "");
        reportData = gson.fromJson(data, ReportData.class);

        if (reportData != null && reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getProcessingUnit() != null
                && reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates() != null) {
            try {
                binding.remarksStock.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getRawMatStockRegisterRemarks());
                binding.remarksProcessing.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getProcessingRegisterRemarks());
                binding.remarksInward.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getInwardRegisterRemarks());
                binding.remarksOutward.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getOutwardRegisterRemarks());
                binding.remarksSaleInv.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getSaleInvoiceBookRemarks());
                binding.remarksLabAtt.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getLabourAttendRegisterRemarks());
                binding.remarksFireNoc.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getFireDeptRemarks());
                binding.remarksAmc.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getAmcMachinaryRemarks());
                binding.remarksAgmark.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getAgmarkCertRemarks());
                binding.remarksFsaai.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getFsaaiCertRemarks());
                binding.remarksEmpties.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getEmptiesRegisterRemarks());
                binding.remarksBarrels.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getBarrelsAlumnCansRemarks());
                binding.remarksCashBook.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getCashBookRemarks());
                binding.remarksCashBank.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getCashBankBalRemarks());
                binding.remarksVehLog.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getVehLogBookRemarks());
                binding.remarks.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getGeneralFindings().getRemarks());

                binding.remarksStockPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getRawMatStockRegisterRemarks());
                binding.remarksProcessingPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getProcessingRegisterRemarks());
                binding.remarksInwardPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getInwardRegisterRemarks());
                binding.remarksOutwardPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getOutwardRegisterRemarks());
                binding.remarksSaleInvPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getSaleInvoiceBookRemarks());
                binding.remarksLabAttPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getLabourAttendRegisterRemarks());
                binding.remarksFireNocPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getFireDeptRemarks());
                binding.remarksAmcPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getAmcMachinaryRemarks());
                binding.remarksAgmarkPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getAgmarkCertRemarks());
                binding.remarksFsaaiPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getFsaaiCertRemarks());
                binding.remarksEmptiesPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getEmptiesRegisterRemarks());
                binding.remarksBarrelsPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getBarrelsAlumnCansRemarks());
                binding.remarksCashBookPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getCashBookRemarks());
                binding.remarksCashBankPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getCashBankBalRemarks());
                binding.remarksVehLogPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getVehLogBookRemarks());
                binding.remarksPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getGeneralFindings().getRemarks());

            } catch (Exception e) {
                Toast.makeText(this, getString(R.string.something) + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
        }
        if (reportData != null && reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getProcessingUnit() != null) {
            binding.setPUnit(reportData.getInspectionFindings().getProcessingUnit());
        } else {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
        }

        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PUnitInspRepActivity.this, ViewPhotosActivity.class)
                        .putExtra(AppConstants.PHOTO_TITLE, "PROCESSING UNIT PHOTOS"));
            }
        });
        binding.tvDate.setText(reportData.getInspectionTime());
        binding.header.ivPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    customProgressDialog.show();

                    directory_path = getExternalFilesDir(null)
                            + "/" + "TWD/GCC/";

                    filePath = directory_path + "Processing_Unit_" + reportData.getOfficerId() + "_" + reportData.getInspectionTime() + ".pdf";
                    File file = new File(filePath);
                    List<View> views = new ArrayList<>();
                    views.add(binding.registersPdf);
                    views.add(binding.generalPdf);

                    PDFUtil.getInstance().generatePDF(views, filePath, PUnitInspRepActivity.this, "schemes");

                } catch (Exception e) {
                    if (customProgressDialog.isShowing())
                        customProgressDialog.hide();

                    Toast.makeText(PUnitInspRepActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void pdfGenerationSuccess(File savedPDFFile) {
        customProgressDialog.hide();

        Utils.customSyncSuccessAlert(PUnitInspRepActivity.this, getString(R.string.app_name), "PDF saved successfully at " + savedPDFFile.getPath().toString());
    }

    @Override
    public void pdfGenerationFailure(Exception exception) {
        customProgressDialog.hide();

        Utils.customErrorAlert(PUnitInspRepActivity.this, getString(R.string.app_name), getString(R.string.something) + " " + exception.getMessage());
    }
}
