package com.cgg.twdinspection.inspection.reports.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityAcademicGradeBinding;
import com.cgg.twdinspection.databinding.ActivityReportAcademicBinding;
import com.cgg.twdinspection.databinding.ActivityReportAcademicGradeBinding;
import com.cgg.twdinspection.inspection.adapter.AcademicGradeAdapter;
import com.cgg.twdinspection.inspection.interfaces.AcademicGradeInterface;
import com.cgg.twdinspection.inspection.reports.adapter.ReportAcademicGradeAdapter;
import com.cgg.twdinspection.inspection.reports.source.InspReportData;
import com.cgg.twdinspection.inspection.source.academic_overview.AcademicGradeEntity;
import com.cgg.twdinspection.inspection.source.inst_master.MasterClassInfo;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.ui.AcademicGradeActivity;
import com.cgg.twdinspection.inspection.viewmodel.AcademicViewModel;
import com.cgg.twdinspection.inspection.viewmodel.StudentsAttndViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ReportAcademicGradeActivity extends AppCompatActivity {
    ActivityReportAcademicGradeBinding binding;
    private String instId, officerId;
    private SharedPreferences sharedPreferences;
    private InspReportData reportData;
    ReportAcademicGradeAdapter academicGradeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_report_academic_grade);
        sharedPreferences = TWDApplication.get(this).getPreferences();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        binding.header.ivHome.setVisibility(View.GONE);
        binding.header.headerTitle.setText(getString(R.string.title_academic));

        sharedPreferences = TWDApplication.get(this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.INSP_REP_DATA, "");
        reportData = gson.fromJson(data, InspReportData.class);

        String jsonObject  = gson.toJson(reportData.getAcademicOverview());
        if(!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("{}")) {
            if(reportData.getAcademicOverview().getAcademicGradeEntities()!=null && reportData.getAcademicOverview().getAcademicGradeEntities().size()>0){
                academicGradeAdapter = new ReportAcademicGradeAdapter(ReportAcademicGradeActivity.this, reportData.getAcademicOverview().getAcademicGradeEntities());
                binding.gradeRV.setLayoutManager(new LinearLayoutManager(ReportAcademicGradeActivity.this));
                binding.gradeRV.setAdapter(academicGradeAdapter);
            }

        }
    }

    @Override
    public void onBackPressed() {
      finish();
    }
}
