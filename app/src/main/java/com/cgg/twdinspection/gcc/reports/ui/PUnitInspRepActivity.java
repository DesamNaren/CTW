package com.cgg.twdinspection.gcc.reports.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.ActivityPunitInspRepBinding;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.google.gson.Gson;

public class PUnitInspRepActivity extends AppCompatActivity {

    ActivityPunitInspRepBinding binding;
    SharedPreferences sharedPreferences;
    ReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_punit_insp_rep);

        binding.bottomLl.btnNext.setText("Next");
        binding.header.headerTitle.setText("Processing Unit Inspection Report");
        binding.header.ivHome.setVisibility(View.GONE);
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        sharedPreferences= TWDApplication.get(PUnitInspRepActivity.this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.REP_DATA, "");
        reportData = gson.fromJson(data, ReportData.class);

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


        binding.setPUnit(reportData.getInspectionFindings().getProcessingUnit());
        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PUnitInspRepActivity.this,ViewPhotosActivity.class));
            }
        });
    }
}
