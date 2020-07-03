package com.cgg.twdinspection.inspection.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.screenshot.PDFUtil;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityPreviewPdfBinding;
import com.cgg.twdinspection.gcc.reports.adapter.ViewPhotoAdapter;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.cgg.twdinspection.gcc.reports.source.ReportPhoto;
import com.cgg.twdinspection.inspection.reports.adapter.DietIssuesReportAdapter;
import com.cgg.twdinspection.inspection.reports.adapter.StaffAttReportAdapter;
import com.cgg.twdinspection.inspection.reports.adapter.StuAttReportAdapter;
import com.cgg.twdinspection.inspection.reports.source.DietListEntity;
import com.cgg.twdinspection.inspection.reports.source.InspReportData;
import com.cgg.twdinspection.inspection.reports.source.StaffAttendenceInfo;
import com.cgg.twdinspection.inspection.reports.source.StudentAttendenceInfo;
import com.cgg.twdinspection.inspection.ui.DashboardActivity;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PreviewPdfActivity extends AppCompatActivity implements PDFUtil.PDFUtilListener {

    ActivityPreviewPdfBinding binding;
    SharedPreferences sharedPreferences;
    private Bitmap bitmap;
    private InspReportData inspReportData;
    File imageFile;
    String directory_path, filePath;
    private List<StudentAttendenceInfo> studentAttendInfoList;
    private List<StaffAttendenceInfo> staffAttendenceInfos;
    CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preview_pdf);
        customProgressDialog = new CustomProgressDialog(this);
        sharedPreferences = TWDApplication.get(this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.INSP_REP_DATA, "");
        inspReportData = gson.fromJson(data, InspReportData.class);

        binding.tvDate.setText(inspReportData.getInspectionTime());

        String jsonObject = gson.toJson(inspReportData.getGeneralInfo());
        if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("{}")) {

            binding.generalInfo.setInspData(inspReportData.getGeneralInfo());
            binding.generalInfo.manNameTv.setText(inspReportData.getMandalName() + " & " + inspReportData.getVillageName());
            binding.generalInfo.disNameTv.setText(inspReportData.getDistName());
            binding.generalInfo.instNameTv.setText(inspReportData.getInstituteName());
            binding.executePendingBindings();
        }

        binding.generalInfo.btnNext.setVisibility(View.GONE);
        binding.generalInfo.tvTitle.setVisibility(View.VISIBLE);
        binding.generalInfo.actionBar.appbar.setVisibility(View.GONE);

        binding.studAtt.btnLayout.btnLayout.setVisibility(View.GONE);
        binding.studAtt.tvTitle.setVisibility(View.VISIBLE);
        binding.studAtt.actionBar.appbar.setVisibility(View.GONE);

        binding.staff.btnLayout.btnLayout.setVisibility(View.GONE);
        binding.staff.tvTitle.setVisibility(View.VISIBLE);
        binding.staff.tvTitle.setText(getString(R.string.sta_att));
        binding.staff.actionBar.appbar.setVisibility(View.GONE);

        binding.medical.btnLayout.btnLayout.setVisibility(View.GONE);
        binding.medical.tvTitle.setVisibility(View.VISIBLE);
        binding.medical.actionBar.appbar.setVisibility(View.GONE);

        binding.diet.btnLayout.btnLayout.setVisibility(View.GONE);
        binding.diet.tvTitle.setVisibility(View.VISIBLE);
        binding.diet.actionBar.appbar.setVisibility(View.GONE);

        binding.infra.btnLayout.btnLayout.setVisibility(View.GONE);
        binding.infra.tvTitle.setVisibility(View.VISIBLE);
        binding.infra.actionBar.appbar.setVisibility(View.GONE);

        binding.academic.btnLayout.btnLayout.setVisibility(View.GONE);
        binding.academic.tvTitle.setVisibility(View.VISIBLE);
        binding.academic.actionBar.appbar.setVisibility(View.GONE);

        binding.coCurricular.btnNext.setVisibility(View.GONE);
        binding.coCurricular.tvTitle.setVisibility(View.VISIBLE);
        binding.coCurricular.actionBar.appbar.setVisibility(View.GONE);

        binding.entitlement.btnLayout.btnLayout.setVisibility(View.GONE);
        binding.entitlement.tvTitle.setVisibility(View.VISIBLE);
        binding.entitlement.actionBar.appbar.setVisibility(View.GONE);

        binding.registers.btnLayout.btnLayout.setVisibility(View.GONE);
        binding.registers.tvTitle.setVisibility(View.VISIBLE);
        binding.registers.actionBar.appbar.setVisibility(View.GONE);

        binding.genComments.btnLayout.btnLayout.setVisibility(View.GONE);
        binding.genComments.tvTitle.setVisibility(View.VISIBLE);
        binding.genComments.actionBar.appbar.setVisibility(View.GONE);

        binding.viewPhotos.header.backBtn.setVisibility(View.GONE);
        binding.viewPhotos.tvTitle.setVisibility(View.VISIBLE);
        binding.viewPhotos.header.root.setVisibility(View.GONE);

        binding.btnLayout.btnNext.setText("Get PDF");
        binding.actionBar.headerTitle.setText("PDF Preview");


        binding.actionBar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.actionBar.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PreviewPdfActivity.this, DashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

            }
        });

        if (inspReportData != null) {
            String generalInfo = gson.toJson(inspReportData.getGeneralInfo());
            if (!TextUtils.isEmpty(generalInfo) && !generalInfo.equalsIgnoreCase("{}")) {
                binding.generalInfo.setInspData(inspReportData.getGeneralInfo());
                binding.executePendingBindings();
            }
            String studAttendance = gson.toJson(inspReportData.getStudentAttendenceInfo());
            if (!TextUtils.isEmpty(studAttendance) && !studAttendance.equalsIgnoreCase("{}")) {
                studentAttendInfoList = inspReportData.getStudentAttendenceInfo();
                if (studentAttendInfoList != null && studentAttendInfoList.size() > 0) {
                    setStudAdapter(studentAttendInfoList);
                }
            }
            String staffAttendence = gson.toJson(inspReportData.getStaffAttendenceInfo());
            if (!TextUtils.isEmpty(staffAttendence) && !staffAttendence.equalsIgnoreCase("{}")) {
                staffAttendenceInfos = inspReportData.getStaffAttendenceInfo();
                if (staffAttendenceInfos != null && staffAttendenceInfos.size() > 0) {
                    setStaffAdapter(staffAttendenceInfos);
                }
            }
            String medical = gson.toJson(inspReportData.getMedicalIssues());
            if (!TextUtils.isEmpty(medical) && !medical.equalsIgnoreCase("{}")) {
                binding.medical.setMedical(inspReportData.getMedicalIssues());
                binding.executePendingBindings();
            }
            String diet = gson.toJson(inspReportData.getDietIssues());
            if (!TextUtils.isEmpty(diet) && !diet.equalsIgnoreCase("{}")) {
                binding.diet.setInspData(inspReportData.getDietIssues());
                binding.executePendingBindings();
            }
            if (inspReportData.getDietIssues().getDietListEntities() != null && inspReportData.getDietIssues().getDietListEntities().size() > 0) {
                setDietAdapter(inspReportData.getDietIssues().getDietListEntities());
            }
            String infra = gson.toJson(inspReportData.getInfraMaintenance());
            if (!TextUtils.isEmpty(infra) && !infra.equalsIgnoreCase("{}")) {
                binding.infra.setInspData(inspReportData.getInfraMaintenance());
                binding.executePendingBindings();
            }
            String academic = gson.toJson(inspReportData.getAcademicOverview());
            if (!TextUtils.isEmpty(academic) && !academic.equalsIgnoreCase("{}")) {
                binding.academic.setInspData(inspReportData.getAcademicOverview());
                binding.executePendingBindings();
            }
            String cocurricular = gson.toJson(inspReportData.getCoCurricularInfo());
            if (!TextUtils.isEmpty(cocurricular) && !cocurricular.equalsIgnoreCase("{}")) {
                binding.coCurricular.setInspData(inspReportData.getCoCurricularInfo());
                binding.executePendingBindings();
            }
            String entitlements = gson.toJson(inspReportData.getEntitlements());
            if (!TextUtils.isEmpty(entitlements) && !entitlements.equalsIgnoreCase("{}")) {
                binding.entitlement.setInspData(inspReportData.getEntitlements());
                binding.executePendingBindings();
            }
            String registers = gson.toJson(inspReportData.getRegisters());
            if (!TextUtils.isEmpty(registers) && !registers.equalsIgnoreCase("{}")) {
                binding.registers.setInspData(inspReportData.getRegisters());
                binding.executePendingBindings();
            }
            String genComments = gson.toJson(inspReportData.getGeneralComments());
            if (!TextUtils.isEmpty(genComments) && !genComments.equalsIgnoreCase("{}")) {
                binding.genComments.setComments(inspReportData.getGeneralComments());
                binding.executePendingBindings();
            }

            if (inspReportData.getPhotos() != null && inspReportData.getPhotos().size() > 0) {
                String reportData = sharedPreferences.getString(AppConstants.INSP_REP_DATA, "");
                ReportData reportData1 = gson.fromJson(reportData, ReportData.class);
                setPhotosAdapter(reportData1.getPhotos());
            }
        }
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                try {
                    customProgressDialog.show();
                    List<View> views = new ArrayList<>();

                    views.add(binding.logo);
                    views.add(binding.generalInfo.svGeneralInfo);
//                    views.add(binding.studAtt.actionBar.getRoot());
                    views.add(binding.studAtt.llTitle);
                    views.add(binding.studAtt.recyclerView);
//                    views.add(binding.staff.actionBar.getRoot());
                    views.add(binding.staff.llTitle);
                    views.add(binding.staff.recyclerView);
//                    views.add(binding.medical.actionBar.getRoot());
                    views.add(binding.medical.sv);
//                    views.add(binding.diet.actionBar.getRoot());
                    views.add(binding.diet.sv);
//                    views.add(binding.infra.actionBar.getRoot());
                    views.add(binding.infra.scrl);
//                    views.add(binding.academic.actionBar.getRoot());
                    views.add(binding.academic.scrl);
//                    views.add(binding.coCurricular.actionBar.getRoot());
                    views.add(binding.coCurricular.scrl);
//                    views.add(binding.entitlement.actionBar.getRoot());
                    views.add(binding.entitlement.scrl);
//                    views.add(binding.registers.actionBar.getRoot());
                    views.add(binding.registers.baseQueTv);
                    views.add(binding.registers.scrl);
//                    views.add(binding.genComments.actionBar.getRoot());
                    views.add(binding.genComments.scrl);
//                    views.add(binding.viewPhotos.header.getRoot());
                    views.add(binding.viewPhotos.llTitle);
                    views.add(binding.viewPhotos.recyclerView);

                    directory_path = getExternalFilesDir(null)
                            + "/" + "TWD/Schools/";

                    filePath = directory_path + "schools_" + inspReportData.getInstituteId() + "_" + inspReportData.getInspectionTime() + ".pdf";
                    File file = new File(filePath);
                    PDFUtil.getInstance().generatePDF(views, filePath, PreviewPdfActivity.this, "schools");

                } catch (Exception e) {
                    if (customProgressDialog.isShowing())
                        customProgressDialog.hide();
                    Toast.makeText(PreviewPdfActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void pdfGenerationSuccess(File savedPDFFile) {
        customProgressDialog.hide();
        Utils.customSyncSuccessAlert(PreviewPdfActivity.this, getString(R.string.app_name), "PDF saved successfully at " + savedPDFFile.getPath().toString());
    }

    @Override
    public void pdfGenerationFailure(Exception exception) {
        customProgressDialog.hide();
        Utils.customErrorAlert(PreviewPdfActivity.this, getString(R.string.app_name), getString(R.string.something) + " " + exception.getMessage());
    }

    private void setStudAdapter(List<StudentAttendenceInfo> studentAttendInfoList) {
        StuAttReportAdapter stockSubAdapter = new StuAttReportAdapter(this, studentAttendInfoList);
        binding.studAtt.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.studAtt.recyclerView.setAdapter(stockSubAdapter);
    }

    private void setStaffAdapter(List<StaffAttendenceInfo> staffAttendenceInfos) {
        StaffAttReportAdapter staffAttReportAdapter = new StaffAttReportAdapter(this, staffAttendenceInfos);
        binding.staff.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.staff.recyclerView.setAdapter(staffAttReportAdapter);
    }

    private void setPhotosAdapter(List<ReportPhoto> reportPhotos) {
        ViewPhotoAdapter adapter = new ViewPhotoAdapter(this, reportPhotos);
        binding.viewPhotos.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.viewPhotos.recyclerView.setAdapter(adapter);
    }

    private void setDietAdapter(List<DietListEntity> dietListEntities) {
        DietIssuesReportAdapter dietIssuesReportAdapter = new DietIssuesReportAdapter(this, dietListEntities);
        binding.diet.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.diet.recyclerView.setAdapter(dietIssuesReportAdapter);
    }

}


