package com.cgg.twdinspection.inspection.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
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
import com.cgg.twdinspection.inspection.reports.adapter.DietIssuesReportAdapter;
import com.cgg.twdinspection.inspection.reports.adapter.ReportsMenuSectionsAdapter;
import com.cgg.twdinspection.inspection.reports.adapter.StaffAttReportAdapter;
import com.cgg.twdinspection.inspection.reports.adapter.StuAttReportAdapter;
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
    private RecyclerView.LayoutManager layoutManager;

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
           /* String cocurricular = gson.toJson(inspReportData.getCoCurricularInfo());
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
            }*/
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
                try {
                    customProgressDialog.show();

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
                    views.add(binding.generalInfoPdf);
                    views.add(binding.medicalPdf);
                    views.add(binding.dietPdf);
                    views.add(binding.infraPdf1);
                    views.add(binding.infraPdf2);
                    views.add(binding.infraPdf3);
                    views.add(binding.academicPdf1);

                    PDFUtil.getInstance(InstReportsMenuActivity.this).generatePDF(views, filePath1, InstReportsMenuActivity.this, "schemes", "GCC");

                } catch (Exception e) {
                    if (customProgressDialog.isShowing())
                        customProgressDialog.hide();

                    Toast.makeText(InstReportsMenuActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                }
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

    private void addContent(Document document) throws DocumentException {
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

    private void createStaffTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(8);
        PdfPCell c1 = new PdfPCell(new Phrase("ID"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Designation"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Category"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Total leaves"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Leaves taken"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Leaves balance"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Attendance"));
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

    private void createDietTable(Section subCatPart)
            throws BadElementException {
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

    @Override
    public void pdfGenerationSuccess(File savedPDFFile) {
        customProgressDialog.hide();
        filePath2 = filePath + "_2" + ".pdf";

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(this.filePath2));
            document.open();
            addContent(document);
            addStaffContent(document);
            addDietContent(document);
            document.close();

//            File f =new File(filePath);
//            File f2 =new File(filePath);
//            mergeFiles(f.getAbsolutePath(), f2.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new ItextMerge(InstReportsMenuActivity.this, filePath, filePath1, filePath2, InstReportsMenuActivity.this);
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
        Utils.customPDFAlert(InstReportsMenuActivity.this, getString(R.string.app_name),
                "PDF File Generated Successfully. \n File saved at " + savedPDFFile + "\n Do you want open it?", savedPDFFile);
    }

    @Override
    public void pdfMergeFailure(Exception exception) {
        Utils.customErrorAlert(InstReportsMenuActivity.this, getString(R.string.app_name), getString(R.string.something) + " " + exception.getMessage());

    }
}

