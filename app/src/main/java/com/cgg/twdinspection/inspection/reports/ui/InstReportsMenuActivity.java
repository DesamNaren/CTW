package com.cgg.twdinspection.inspection.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
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
import com.cgg.twdinspection.inspection.ui.BaseActivity;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.google.gson.Gson;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class InstReportsMenuActivity extends LocBaseActivity implements PDFUtil.PDFUtilListener, ItextMerge.PDFMergeListener {
    ReportsInstMenuActivityBinding binding;
    CustomProgressDialog customProgressDialog;
    SharedPreferences sharedPreferences;
    private InspReportData inspReportData;
    String directory_path, filePath, filePath1, filePath2, filePath_temp;
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
    String folder = "Schools";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.reports_inst_menu_activity);

        TextView[] ids1 = new TextView[]{binding.slno11, binding.slno12, binding.slno13, binding.slno14, binding.slno15,
                binding.slno16, binding.slno17, binding.slno18};
        BaseActivity.setIds(ids1, 0);

        TextView[] ids4 = new TextView[]{binding.slno41, binding.slno42, binding.slno43, binding.slno44, binding.slno45,
                binding.slno46, binding.slno47};
        BaseActivity.setIds(ids4, 13);

        TextView[] ids5 = new TextView[]{binding.slno51, binding.slno52, binding.slno53, binding.slno54, binding.slno55};
        BaseActivity.setIds(ids5, 21);

        TextView[] ids6 = new TextView[]{binding.slno61, binding.slno62, binding.slno63, binding.slno64, binding.slno65,
                binding.slno66, binding.slno67, binding.slno68, binding.slno69, binding.slno610, binding.slno611,
                binding.slno612, binding.slno613, binding.slno614, binding.slno615, binding.slno616, binding.slno617,
                binding.slno618, binding.slno619, binding.slno620, binding.slno621, binding.slno622, binding.slno623,
                binding.slno624, binding.slno625, binding.slno626};
        BaseActivity.setIds(ids6, 27);
        binding.slno6261.setText(binding.slno626.getText().toString() + "1.");

        TextView[] ids7 = new TextView[]{binding.slno71, binding.slno72, binding.slno73, binding.slno74, binding.slno75,
                binding.slno76, binding.slno77, binding.slno78, binding.slno79, binding.slno710, binding.slno711,
                binding.slno712, binding.slno713, binding.slno714, binding.slno715, binding.slno716};
        BaseActivity.setIds(ids7, 54);

        TextView[] ids8 = new TextView[]{binding.slno81, binding.slno82, binding.slno83, binding.slno84, binding.slno85,
                binding.slno86, binding.slno87, binding.slno88};
        BaseActivity.setIds(ids8, 70);

        TextView[] ids9 = new TextView[]{binding.slno91, binding.slno92, binding.slno93, binding.slno94, binding.slno95,
                binding.slno96};
        BaseActivity.setIds(ids9, 78);

        TextView[] ids10 = new TextView[]{binding.slno101};
        BaseActivity.setIds(ids10, 84);

        TextView[] ids11 = new TextView[]{binding.slno111, binding.slno112, binding.slno113, binding.slno114,
                binding.slno115, binding.slno116};
        BaseActivity.setIds(ids11, 85);

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
            if (!TextUtils.isEmpty(cocurricular) && !cocurricular.equalsIgnoreCase("{}")
                    && inspReportData.getCoCurricularInfo().getInstituteId() != null) {
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
                finish();
            }
        });
        binding.actionBar.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InstReportsMenuActivity.this, DashboardMenuActivity.class)
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

                            String cocurricular = gson.toJson(inspReportData.getCoCurricularInfo());
                            if (!TextUtils.isEmpty(cocurricular) && !cocurricular.equalsIgnoreCase("{}")
                                    && inspReportData.getCoCurricularInfo().getInstituteId() != null) {
                                views.add(binding.cocurricularPdf1);
                                views.add(binding.cocurricularPdf2);
                            }

                            views.add(binding.entitlementPdf);
                            views.add(binding.registersPdf1);
                            views.add(binding.registersPdf2);
                            views.add(binding.generalCommentsPdf1);
                            views.add(binding.generalCommentsPdf2);
                            views.add(binding.photosPdf);

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                                filePath1 = PDFUtil.createPdfFile(InstReportsMenuActivity.this,
                                        "Schools_" + inspReportData.getOfficerId()
                                                + "_" + inspReportData.getInspectionTime()
                                                + "_1" + ".pdf", folder);
                            } else {
                                directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                        + "/" + "CTW/Schools/";
                                filePath = directory_path + "Schools_" + inspReportData.getOfficerId() + "_" + inspReportData.getInspectionTime();
                                filePath1 = filePath + "_1" + ".pdf";
                            }

                            PDFUtil.getInstance(InstReportsMenuActivity.this).generatePDF(views,
                                    filePath1, InstReportsMenuActivity.this);

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

    private void setAcademicAdapter(List<AcademicGradeEntity> academicGradeEntityList) {
        ReportAcademicGradeAdapter reportAcademicGradeAdapter = new ReportAcademicGradeAdapter(this, academicGradeEntityList);
        layoutManager = new LinearLayoutManager(this);
        binding.academicGrade.gradeRV.setLayoutManager(layoutManager);
        binding.academicGrade.gradeRV.setAdapter(reportAcademicGradeAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (customProgressDialog.isShowing())
            customProgressDialog.hide();
    }

    @Override
    public void onBackPressed() {
        if (customProgressDialog.isShowing())
            customProgressDialog.hide();
        finish();
    }

    @Override
    public void pdfGenerationSuccess(File savedPDFFile) {
        customProgressDialog.hide();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            filePath2 = PDFUtil.createPdfFile(InstReportsMenuActivity.this, "Schools_" + inspReportData.getOfficerId()
                    + "_" + inspReportData.getInspectionTime()
                    + "_2" + ".pdf", folder);
        } else {
            filePath2 = filePath + "_2" + ".pdf";
        }

        try {
            customProgressDialog.show();
            customProgressDialog.addText("Please wait...Downloading Pdf");

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(this.filePath2));
            document.open();

            addStudContent(document);
            addStaffContent(document);
            if (dietListEntityList != null)
                addDietContent(document);
            if (academicGradeEntityList != null)
                addAcademicContent(document);

            document.close();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                filePath_temp = PDFUtil.createPdfFile(InstReportsMenuActivity.this, "Schools_" + inspReportData.getOfficerId()
                        + "_" + inspReportData.getInspectionTime()
                        + "_temp" + ".pdf", folder);

                new ItextMerge(filePath_temp, filePath1, filePath2, InstReportsMenuActivity.this);

            } else {
                new ItextMerge(filePath + "_temp", filePath1, filePath2, InstReportsMenuActivity.this);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addStudContent(Document document) throws DocumentException {
        Paragraph paragraph1 = new Paragraph("Student Attendance", catFont);
        document.add(paragraph1);
        addLineSeperator(document);
        createStudTable(document);
        addLineSeperator(document);
    }

    private void addStaffContent(Document document) throws DocumentException {
        Paragraph paragraph1 = new Paragraph("Staff Attendance", catFont);
        document.add(paragraph1);
        addLineSeperator(document);
        createStaffTable(document);
        addLineSeperator(document);
    }

    private void addDietContent(Document document) throws DocumentException {
        Paragraph paragraph1 = new Paragraph("Diet Issues - Provision of Supplies", catFont);
        document.add(paragraph1);
        addLineSeperator(document);
        createDietTable(document);
        addLineSeperator(document);
    }

    private void addAcademicContent(Document document) throws DocumentException {
        Paragraph paragraph1 = new Paragraph("Academic OverView - Classes Performance", catFont);
        document.add(paragraph1);
        addLineSeperator(document);
        createAcademicTable(document);
        addLineSeperator(document);
    }

    private void createStudTable(Document document) throws BadElementException {
        PdfPTable table = new PdfPTable(6);
        table.setTotalWidth(550);
        table.setLockedWidth(true);
        createCell("Class", table);
        createCell("On Roll Count", table);
        createCell("Attendance Marked", table);
        createCell("Presence Count", table);
        createCell("Inspection Count", table);
        createCell("Variance", table);

        table.setHeaderRows(1);
        try {

            for (int i = 0; i < studentAttendInfoList.size(); i++) {

                createCell(studentAttendInfoList.get(i).getClassId(), table);
                createCell(studentAttendInfoList.get(i).getTotalStudents(), table);
                createCell(studentAttendInfoList.get(i).getAttendenceMarked(), table);
                createCell(studentAttendInfoList.get(i).getStudentCountInRegister(), table);
                createCell(studentAttendInfoList.get(i).getStudentCountDuringInspection(), table);
                createCell(studentAttendInfoList.get(i).getVariance(), table);

            }
            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createStaffTable(Document document) throws BadElementException {

        PdfPTable table = new PdfPTable(7);
        table.setTotalWidth(550);
        table.setLockedWidth(true);
        createCell("ID", table);
        createCell("Name", table);
        createCell("Designation", table);
//        createCell("Category", table);
//        createCell("Total Leaves", table);
//        createCell("Leaves Taken", table);
//        createCell("Leave Balance", table);
        createCell("Attendance", table);
        createCell("Yesterday super vision duty allotted", table);
        createCell("Last week turn duties attended", table);
        createCell("Teacher's last year academic panel grade", table);

        table.setHeaderRows(1);

        try {

            for (int i = 0; i < staffAttendenceInfoList.size(); i++) {

                createCell(staffAttendenceInfoList.get(i).getEmpId(), table);
                createCell(staffAttendenceInfoList.get(i).getEmpName(), table);
                createCell(staffAttendenceInfoList.get(i).getDesignation(), table);
//                createCell(staffAttendenceInfoList.get(i).getCategory(), table);
//                createCell(staffAttendenceInfoList.get(i).getLeavesAvailed(), table);
//                createCell(staffAttendenceInfoList.get(i).getLeavesTaken(), table);
//                createCell(staffAttendenceInfoList.get(i).getLeavesBal(), table);
                createCell(staffAttendenceInfoList.get(i).getEmpPresence(), table);
                createCell(staffAttendenceInfoList.get(i).getYdayDutyAllotted(), table);
                createCell(staffAttendenceInfoList.get(i).getLastWeekTeacherAttended(), table);
                createCell(staffAttendenceInfoList.get(i).getAcadPanelGrade(), table);

            }
            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void createDietTable(Document document) throws BadElementException {

        PdfPTable table = new PdfPTable(3);
        table.setTotalWidth(550);
        table.setLockedWidth(true);
        createCell("Item Type", table);
        createCell("Book Balance", table);
        createCell("Ground Balance", table);
        table.setHeaderRows(1);

        try {

            for (int i = 0; i < dietListEntityList.size(); i++) {

                createCell(dietListEntityList.get(i).getItemName(), table);
                createCell(dietListEntityList.get(i).getBookBal(), table);
                createCell(dietListEntityList.get(i).getGroundBal(), table);

            }
            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void createAcademicTable(Document document) throws BadElementException {

        PdfPTable table = new PdfPTable(9);
        table.setTotalWidth(550);
        table.setLockedWidth(true);
        createCell("Class Name", table);
        createCell("Total Strength", table);
        createCell("Grade A", table);
        createCell("Grade B", table);
        createCell("Grade C", table);
        createCell("Grade D", table);
        createCell("Grade E", table);
        createCell("Grade A+", table);
        createCell("Grade B+", table);

        table.setHeaderRows(1);
        try {

            for (int i = 0; i < academicGradeEntityList.size(); i++) {

                createCell(academicGradeEntityList.get(i).getClassType(), table);
                createCell(academicGradeEntityList.get(i).getTotalStudents(), table);
                createCell(academicGradeEntityList.get(i).getGradeAStuCount(), table);
                createCell(academicGradeEntityList.get(i).getGradeBStuCount(), table);
                createCell(academicGradeEntityList.get(i).getGradeCStuCount(), table);
                createCell(academicGradeEntityList.get(i).getGradeDStuCount(), table);
                createCell(academicGradeEntityList.get(i).getGradeEStuCount(), table);
                createCell(academicGradeEntityList.get(i).getGradeAplusStuCount(), table);
                createCell(academicGradeEntityList.get(i).getGradeBplusStuCount(), table);
            }
            document.add(table);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void createCell(String label, PdfPTable table) {
        PdfPCell c1 = new PdfPCell(new Phrase(label));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPaddingTop(5);
        c1.setPaddingBottom(5);
        table.addCell(c1);
    }

    private void addLineSeperator(Document document) {
        try {
            LineSeparator separator = new LineSeparator();
            separator.setPercentage(100);
            separator.setLineColor(BaseColor.WHITE);
            Chunk linebreak = new Chunk(separator);
            document.add(linebreak);

        } catch (DocumentException e) {
            e.printStackTrace();
        }

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

            document.add(pdfPTable1);
            document.add(pdfPTable);
            document.add(pdfPTable2);

        } catch (DocumentException e) {
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

    @Override
    public void pdfGenerationFailure(Exception exception) {
        customProgressDialog.hide();
        Utils.customErrorAlert(InstReportsMenuActivity.this, getString(R.string.app_name), getString(R.string.something) + " " + exception.getMessage());
    }

    @Override
    public void pdfMergeSuccess() {
        File savedPDFFile = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            filePath = PDFUtil.createPdfFile(InstReportsMenuActivity.this, "Schools_" + inspReportData.getOfficerId()
                    + "_" + inspReportData.getInspectionTime()
                    + ".pdf", folder);

            savedPDFFile = new File(filePath);

        } else {
            savedPDFFile = new File(filePath + ".pdf");
        }

        customProgressDialog.hide();
        try {
            addWatermark(savedPDFFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void addWatermark(File savedPDFFile) throws IOException, DocumentException {
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
            Phrase p1 = new Phrase(inspReportData.getOfficerId() + ", " + sharedPreferences.getString(AppConstants.OFFICER_DES, ""), f);
            ColumnText.showTextAligned(over, Element.ALIGN_RIGHT, p1, 550, 30, 0);
            Phrase p2 = new Phrase("Inspection Report-Schools" + ", " + inspReportData.getInspectionTime(), f);
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


        Utils.customPDFAlert(InstReportsMenuActivity.this, getString(R.string.app_name),
                "PDF File Generated Successfully. \n File saved at " + savedPDFFile + "\n Do you want open it?", savedPDFFile);

    }

    @Override
    public void pdfMergeFailure(Exception exception) {
        customProgressDialog.hide();
        Utils.customErrorAlert(InstReportsMenuActivity.this, getString(R.string.app_name), getString(R.string.something) + " " + exception.getMessage());

    }
}

