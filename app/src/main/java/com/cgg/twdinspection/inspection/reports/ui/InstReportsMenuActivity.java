package com.cgg.twdinspection.inspection.reports.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.screenshot.ItextMerge;
import com.cgg.twdinspection.common.screenshot.PDFUtil;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ReportsInstMenuActivityBinding;
import com.cgg.twdinspection.gcc.reports.adapter.ViewPhotoAdapterPdf;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.cgg.twdinspection.inspection.reports.adapter.DietIssuesReportAdapter;
import com.cgg.twdinspection.inspection.reports.adapter.ReportAcademicGradeAdapter;
import com.cgg.twdinspection.inspection.reports.adapter.ReportsMenuSectionsAdapter;
import com.cgg.twdinspection.inspection.reports.adapter.StaffAttReportAdapter;
import com.cgg.twdinspection.inspection.reports.adapter.StuAttReportAdapter;
import com.cgg.twdinspection.inspection.reports.source.AcademicGradeEntity;
import com.cgg.twdinspection.inspection.reports.source.DietListEntity;
import com.cgg.twdinspection.inspection.reports.source.InspReportData;
import com.cgg.twdinspection.inspection.reports.source.StaffAttendenceInfo;
import com.cgg.twdinspection.inspection.reports.source.StudentAttendenceInfo;
import com.cgg.twdinspection.inspection.ui.DashboardActivity;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.google.gson.Gson;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class InstReportsMenuActivity extends LocBaseActivity implements PDFUtil.PDFUtilListener, ItextMerge.PDFMergeListener {
    ReportsInstMenuActivityBinding binding;
    CustomProgressDialog customProgressDialog;
    SharedPreferences sharedPreferences;
    private InspReportData inspReportData;
    String directory_path, filePath, filePath1, filePath2;
    File file;

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private java.util.List<StudentAttendenceInfo> studentAttendInfoList;
    private java.util.List<StaffAttendenceInfo> staffAttendenceInfoList;
    private java.util.List<DietListEntity> dietListEntityList;
    private java.util.List<AcademicGradeEntity> academicGradeEntityList;
    private RecyclerView.LayoutManager layoutManager;
    ViewPhotoAdapterPdf adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.reports_inst_menu_activity);
        binding.actionBar.headerTitle.setText(getString(R.string.insp_reports));
        binding.actionBar.ivPdf.setVisibility(View.VISIBLE);
        customProgressDialog = new CustomProgressDialog(this);

        sharedPreferences = TWDApplication.get(this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.INSP_REP_DATA, "");
        inspReportData = gson.fromJson(data, InspReportData.class);

        String jsonObject = gson.toJson(inspReportData.getGeneralInfo());
        if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("{}")) {

            binding.manNameTv.setText(inspReportData.getMandalName() + " & " + inspReportData.getVillageName());
            binding.disNameTv.setText(inspReportData.getDistName());
            binding.instNameTv.setText(inspReportData.getInstituteName());
            binding.tvDate.setText(inspReportData.getInspectionTime());
        }

        ReportData reportData = gson.fromJson(data, ReportData.class);
        if (reportData != null) {
            String jsonObj = gson.toJson(reportData.getPhotos());
            if (!TextUtils.isEmpty(jsonObj) && !jsonObj.equalsIgnoreCase("[]")) {
                adapter = new ViewPhotoAdapterPdf(this, reportData.getPhotos());
                binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                binding.recyclerView.setAdapter(adapter);
            }
        }
        if (inspReportData != null) {
            String generalInfo = gson.toJson(inspReportData.getGeneralInfo());
            if (!TextUtils.isEmpty(generalInfo) && !generalInfo.equalsIgnoreCase("{}")) {
                binding.setGeneralInfo(inspReportData.getGeneralInfo());
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
                staffAttendenceInfoList = inspReportData.getStaffAttendenceInfo();
                if (staffAttendenceInfoList != null && staffAttendenceInfoList.size() > 0) {
                    setStaffAdapter(staffAttendenceInfoList);
                }
            }

            String medical = gson.toJson(inspReportData.getMedicalIssues());
            if (!TextUtils.isEmpty(medical) && !medical.equalsIgnoreCase("{}")) {
                binding.setMedical(inspReportData.getMedicalIssues());
                binding.executePendingBindings();
            }
            String diet = gson.toJson(inspReportData.getDietIssues());
            if (!TextUtils.isEmpty(diet) && !diet.equalsIgnoreCase("{}")) {
                binding.setDiet(inspReportData.getDietIssues());
                binding.executePendingBindings();
            }
            if (inspReportData.getDietIssues().getDietListEntities() != null && inspReportData.getDietIssues().getDietListEntities().size() > 0) {
                dietListEntityList = inspReportData.getDietIssues().getDietListEntities();
                setDietAdapter(dietListEntityList);
            }
            String infra = gson.toJson(inspReportData.getInfraMaintenance());
            if (!TextUtils.isEmpty(infra) && !infra.equalsIgnoreCase("{}")) {
                binding.setInfra(inspReportData.getInfraMaintenance());
                binding.executePendingBindings();
            }

            String academic = gson.toJson(inspReportData.getAcademicOverview());
            if (!TextUtils.isEmpty(academic) && !academic.equalsIgnoreCase("{}")) {
                binding.setAcademic(inspReportData.getAcademicOverview());
                binding.executePendingBindings();
            }


            if (inspReportData.getAcademicOverview().getAcademicGradeEntities() != null && inspReportData.getAcademicOverview().getAcademicGradeEntities().size() > 0) {
                academicGradeEntityList = inspReportData.getAcademicOverview().getAcademicGradeEntities();
                setAcademicAdapter(academicGradeEntityList);
            }

            String cocurricular = gson.toJson(inspReportData.getCoCurricularInfo());
            if (!TextUtils.isEmpty(cocurricular) && !cocurricular.equalsIgnoreCase("{}")) {
                binding.setCocurricular(inspReportData.getCoCurricularInfo());
                binding.executePendingBindings();
            }

            String entitlements = gson.toJson(inspReportData.getEntitlements());
            if (!TextUtils.isEmpty(entitlements) && !entitlements.equalsIgnoreCase("{}")) {
                binding.setEntitlements(inspReportData.getEntitlements());
                binding.executePendingBindings();
            }

            String registers = gson.toJson(inspReportData.getRegisters());
            if (!TextUtils.isEmpty(registers) && !registers.equalsIgnoreCase("{}")) {
                binding.setRegisters(inspReportData.getRegisters());
                binding.executePendingBindings();
            }

            String genComments = gson.toJson(inspReportData.getGeneralComments());
            if (!TextUtils.isEmpty(genComments) && !genComments.equalsIgnoreCase("{}")) {
                binding.setComments(inspReportData.getGeneralComments());
                binding.executePendingBindings();
            }

        }

        binding.actionBar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InstReportsMenuActivity.this, InspectionReportsDashboard.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        binding.actionBar.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InstReportsMenuActivity.this, DashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
        binding.actionBar.ivPdf.setOnClickListener(new View.OnClickListener() {
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
                                        + "/" + "CTW/Schools/";
                            } else {
                                directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                        + "/" + "CTW/Schools/";
                            }

                            filePath = directory_path + inspReportData.getOfficerId() + "_" + inspReportData.getInspectionTime();

                            filePath1 = filePath + "_1" + ".pdf";
                            List<View> views = new ArrayList<>();
                            views.add(binding.generalInfoPdf1);
                            views.add(binding.generalInfoPdf2);
                            views.add(binding.medicalPdf);
                            views.add(binding.dietPdf);
                            views.add(binding.infraPdf1);
                            views.add(binding.infraPdf2);
                            views.add(binding.infraPdf3);
                            views.add(binding.academicPdf1);
                            views.add(binding.academicPdf2);
                            views.add(binding.academicPdf3);
                            views.add(binding.academicPdf4);
                            views.add(binding.cocurricularPdf1);
                            views.add(binding.cocurricularPdf2);
                            views.add(binding.entitlementPdf);
                            views.add(binding.registersPdf1);
                            views.add(binding.registersPdf2);
                            views.add(binding.generalCommentsPdf);
                            views.add(binding.photosPdf);

                            PDFUtil.getInstance(InstReportsMenuActivity.this).generatePDF(views, filePath1, InstReportsMenuActivity.this, "schemes", "GCC");

                        } catch (Exception e) {
                            if (customProgressDialog.isShowing())
                                customProgressDialog.hide();

                            Toast.makeText(InstReportsMenuActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 10000);

//                startActivity(new Intent(InstReportsMenuActivity.this, PreviewPdfActivity.class)
//                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                finish();
            }
        });


        String[] stringArray = getResources().getStringArray(R.array.inst_sections);
        ArrayList<String> sections = new ArrayList<>(Arrays.asList(stringArray));
        sections.set(sections.size() - 1, "View Photographs");
        if (sections.size() > 0) {
            ReportsMenuSectionsAdapter adapter = new ReportsMenuSectionsAdapter(InstReportsMenuActivity.this, sections);
            binding.rvMenu.setLayoutManager(new LinearLayoutManager(InstReportsMenuActivity.this));
            binding.rvMenu.setAdapter(adapter);
        }


    }

    private void setAcademicAdapter(List<AcademicGradeEntity> academicGradeEntityList) {
        ReportAcademicGradeAdapter reportAcademicGradeAdapter = new ReportAcademicGradeAdapter(this, academicGradeEntityList);
        layoutManager = new LinearLayoutManager(this);
        binding.academicGrade.gradeRV.setLayoutManager(layoutManager);
        binding.academicGrade.gradeRV.setAdapter(reportAcademicGradeAdapter);
    }

    private void addStudContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("Student Attendance", catFont);
        Chapter catPart = new Chapter(new Paragraph(anchor), 0);
        catPart.setNumberDepth(-1);
        Paragraph subPara = new Paragraph("", subFont);
        Section subCatPart = catPart.addSection(subPara);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 2);
        subCatPart.add(paragraph);
        createTable(subCatPart);
        document.add(catPart);
    }

    private void addStaffContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("Staff Attendance", catFont);
        Chapter catPart = new Chapter(new Paragraph(anchor), 0);
        catPart.setNumberDepth(-1);
        Paragraph subPara = new Paragraph("", subFont);
        Section subCatPart = catPart.addSection(subPara);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 2);
        subCatPart.add(paragraph);
        createStaffTable(subCatPart);
        document.add(catPart);
    }

    private void addDietContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("Diet Issues - Provision of Supplies", catFont);
        Chapter catPart = new Chapter(new Paragraph(anchor), 0);
        catPart.setNumberDepth(-1);
        Paragraph subPara = new Paragraph("", subFont);
        Section subCatPart = catPart.addSection(subPara);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 2);
        subCatPart.add(paragraph);
        createDietTable(subCatPart);
        document.add(catPart);
    }

    private void addAcademicContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("Academic OverView - Classes Performance", catFont);
        Chapter catPart = new Chapter(new Paragraph(anchor), 0);
        catPart.setNumberDepth(-1);
        Paragraph subPara = new Paragraph("", subFont);
        Section subCatPart = catPart.addSection(subPara);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 2);
        subCatPart.add(paragraph);
        createAcademicTable(subCatPart, document);
        document.add(catPart);
    }

    private void createFooter(Document document) {

        PdfPTable pdfPTable = new PdfPTable(1);
        pdfPTable.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

        PdfPTable pdfPTable1 = new PdfPTable(1);
        pdfPTable1.setWidthPercentage(80);
        pdfPTable1.addCell(getCell(inspReportData.getOfficerId(), PdfPCell.ALIGN_RIGHT));

        PdfPTable pdfPTable2 = new PdfPTable(1);
        pdfPTable2.setWidthPercentage(80);
        pdfPTable2.addCell(getCell(sharedPreferences.getString(AppConstants.OFFICER_DES, ""), PdfPCell.ALIGN_RIGHT));

        try {

            document.add(pdfPTable);
            document.add(pdfPTable);
            document.add(pdfPTable);
            document.add(pdfPTable1);
            document.add(pdfPTable);
            document.add(pdfPTable2);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void createStaffTable(Section subCatPart) throws BadElementException {
        PdfPTable table = new PdfPTable(8);
        PdfPCell c1 = new PdfPCell(new Phrase("ID"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Designation   "));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Category  "));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Total Leaves"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Leaves Taken"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Leave Balance"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Attendance  "));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
//        c1 = new PdfPCell(new Phrase("Yesterday super vision duty allotted"));
//        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(c1);
//        c1 = new PdfPCell(new Phrase("Last week turn duties attended"));
//        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(c1);
//        c1 = new PdfPCell(new Phrase("Last year academic panel grade"));
//        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(c1);
        table.setHeaderRows(1);
        try {

            for (int i = 0; i < staffAttendenceInfoList.size(); i++) {
                table.addCell(staffAttendenceInfoList.get(i).getEmpId());
                table.addCell(staffAttendenceInfoList.get(i).getEmpName());
                table.addCell(staffAttendenceInfoList.get(i).getDesignation());
                table.addCell(staffAttendenceInfoList.get(i).getCategory());
                table.addCell(staffAttendenceInfoList.get(i).getLeavesAvailed());
                table.addCell(staffAttendenceInfoList.get(i).getLeavesTaken());
                table.addCell(staffAttendenceInfoList.get(i).getLeavesBal());
                table.addCell(staffAttendenceInfoList.get(i).getEmpPresence());
//                table.addCell(staffAttendenceInfoList.get(i).getYdayDutyAllotted());
//                table.addCell(staffAttendenceInfoList.get(i).getLastWeekTeacherAttended());
//                table.addCell(staffAttendenceInfoList.get(i).getAcadPanelGrade());
            }
            subCatPart.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void createDietTable(Section subCatPart) throws BadElementException {
        PdfPTable table = new PdfPTable(3);
        PdfPCell c1 = new PdfPCell(new Phrase("Item Type"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Book Balance"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Ground Balance"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);
        try {

            for (int i = 0; i < dietListEntityList.size(); i++) {
                table.addCell(dietListEntityList.get(i).getItemName());
                table.addCell(dietListEntityList.get(i).getBookBal());
                table.addCell(dietListEntityList.get(i).getGroundBal());
            }
            subCatPart.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void createAcademicTable(Section subCatPart, Document document) throws BadElementException {
        PdfPTable table = new PdfPTable(9);
        PdfPCell c1 = new PdfPCell(new Phrase("Class Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Total Strength"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Grade A"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);


        c1 = new PdfPCell(new Phrase("Grade B"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Grade C"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Grade D"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Grade E"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Grade A+"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);


        c1 = new PdfPCell(new Phrase("Grade B+"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);
        try {

            for (int i = 0; i < academicGradeEntityList.size(); i++) {
                table.addCell(academicGradeEntityList.get(i).getClassType());
                table.addCell(academicGradeEntityList.get(i).getTotalStudents());
                table.addCell(academicGradeEntityList.get(i).getGradeAStuCount());
                table.addCell(academicGradeEntityList.get(i).getGradeBStuCount());
                table.addCell(academicGradeEntityList.get(i).getGradeCStuCount());
                table.addCell(academicGradeEntityList.get(i).getGradeDStuCount());
                table.addCell(academicGradeEntityList.get(i).getGradeEStuCount());
                table.addCell(academicGradeEntityList.get(i).getGradeAplusStuCount());
                table.addCell(academicGradeEntityList.get(i).getGradeBplusStuCount());
            }
            subCatPart.add(table);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    private void setStudAdapter(java.util.List<StudentAttendenceInfo> studentAttendInfoList) {
        StuAttReportAdapter stockSubAdapter = new StuAttReportAdapter(this, studentAttendInfoList);
        layoutManager = new LinearLayoutManager(this);
        binding.studAtt.recyclerView.setLayoutManager(layoutManager);
        binding.studAtt.recyclerView.setAdapter(stockSubAdapter);
    }

    private void setStaffAdapter(java.util.List<StaffAttendenceInfo> staffAttendenceInfoList) {
        StaffAttReportAdapter staffAttReportAdapter = new StaffAttReportAdapter(this, staffAttendenceInfoList);
        layoutManager = new LinearLayoutManager(this);
        binding.staffAtt.recyclerView.setLayoutManager(layoutManager);
        binding.staffAtt.recyclerView.setAdapter(staffAttReportAdapter);
    }

    private void setDietAdapter(java.util.List<DietListEntity> dietListEntities) {
        DietIssuesReportAdapter dietIssuesReportAdapter = new DietIssuesReportAdapter(this, dietListEntities);
        layoutManager = new LinearLayoutManager(this);
        binding.dietIssues.recyclerView.setLayoutManager(layoutManager);
        binding.dietIssues.recyclerView.setAdapter(dietIssuesReportAdapter);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(6);

        PdfPCell c1 = new PdfPCell(new Phrase("Class"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("On Roll Count"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Attendance Marked"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Presence Count"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Inspection Count"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Variance"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);
        try {

            for (int i = 0; i < studentAttendInfoList.size(); i++) {
                table.addCell(studentAttendInfoList.get(i).getClassId());
                table.addCell(studentAttendInfoList.get(i).getTotalStudents());
                table.addCell(studentAttendInfoList.get(i).getAttendenceMarked());
                table.addCell(studentAttendInfoList.get(i).getStudentCountInRegister());
                table.addCell(studentAttendInfoList.get(i).getStudentCountDuringInspection());
                table.addCell(studentAttendInfoList.get(i).getVariance());
            }
            subCatPart.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (customProgressDialog.isShowing())
            customProgressDialog.hide();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(InstReportsMenuActivity.this, InspectionReportsDashboard.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private void addPhotoContent(Document document) throws DocumentException {
        new InsertPhotoAsyncTask(document).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertPhotoAsyncTask extends AsyncTask<Void, Void, Boolean> {
        Document document;

        InsertPhotoAsyncTask(Document document) {
            this.document = document;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean flag = false;
            Anchor anchor = new Anchor("Photos", catFont);
            Chapter catPart = new Chapter(new Paragraph(anchor), 0);
            catPart.setNumberDepth(-1);
            Paragraph subPara = new Paragraph("", subFont);
            Section subCatPart = catPart.addSection(subPara);
            Paragraph paragraph = new Paragraph();
            addEmptyLine(paragraph, 2);
            subCatPart.add(paragraph);
            try {
                createPhotoTable(subCatPart, document);
                document.add(catPart);
            } catch (BadElementException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }

            return flag;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

        }
    }

    private void createPhotoTable(Section subCatPart, Document document)
            throws BadElementException {

        PdfPTable table = new PdfPTable(2);
        PdfPCell c1 = new PdfPCell(new Phrase("Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Image"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);


        table.setHeaderRows(1);
        try {

            for (int i = 0; i < inspReportData.getPhotos().size(); i++) {
                table.addCell(inspReportData.getPhotos().get(i).getFileName());
                Image image = Image.getInstance(inspReportData.getPhotos().get(i).getFilePath());
                table.addCell(image);
            }
            subCatPart.add(table);
            table.setTotalWidth(document.getPageSize().getWidth() / 3);
            table.setLockedWidth(true);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void pdfGenerationSuccess(File savedPDFFile) {

        filePath2 = filePath + "_2" + ".pdf";

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(this.filePath2));
            document.open();
            addStudContent(document);
            addStaffContent(document);
            addDietContent(document);
            addAcademicContent(document);
            createFooter(document);

//            addPhotoContent(document);

            document.close();
            new ItextMerge(InstReportsMenuActivity.this, filePath, filePath1, filePath2, InstReportsMenuActivity.this, inspReportData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private void mergeFiles(String file1, String file2) {
        PdfReader reader1 = null;
        try {
            reader1 = new PdfReader(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PdfReader reader2 = null;
        try {
            reader2 = new PdfReader(file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document document = new Document();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PdfCopy copy = null;
        try {
            copy = new PdfCopy(document, fos);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.open();
        PdfImportedPage page;
        PdfCopy.PageStamp stamp;
        Phrase phrase;
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font font = new Font(bf, 9);
        int n = reader1.getNumberOfPages();
        for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
            page = copy.getImportedPage(reader1, i);
            stamp = copy.createPageStamp(page);
            phrase = new Phrase("page " + i, font);
            ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, phrase, 520, 5, 0);
            try {
                stamp.alterContents();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                copy.addPage(page);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BadPdfFormatException e) {
                e.printStackTrace();
            }
        }
        for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
            page = copy.getImportedPage(reader2, i);
            stamp = copy.createPageStamp(page);
            phrase = new Phrase("page " + (n + i), font);
            ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, phrase, 520, 5, 0);
            try {
                stamp.alterContents();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                copy.addPage(page);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BadPdfFormatException e) {
                e.printStackTrace();
            }
        }
        document.close();
        reader1.close();
        reader2.close();
    }*/

    @Override
    public void pdfGenerationFailure(Exception exception) {
        customProgressDialog.hide();
        Utils.customErrorAlert(InstReportsMenuActivity.this, getString(R.string.app_name), getString(R.string.something) + " " + exception.getMessage());
    }

    @Override
    public void pdfMergeSuccess(File savedPDFFile) {
        customProgressDialog.hide();
        Utils.customPDFAlert(InstReportsMenuActivity.this, getString(R.string.app_name),
                "PDF File Generated Successfully. \n File saved at " + savedPDFFile + "\n Do you want open it?", savedPDFFile);
    }

    @Override
    public void pdfMergeFailure(Exception exception) {
        customProgressDialog.hide();
        Utils.customErrorAlert(InstReportsMenuActivity.this, getString(R.string.app_name), getString(R.string.something) + " " + exception.getMessage());

    }
}

