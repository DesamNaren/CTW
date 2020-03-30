package com.cgg.twdinspection.inspection.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.ReportActivityStudentsAttendanceBinding;
import com.cgg.twdinspection.inspection.reports.adapter.StuAttReportAdapter;
import com.cgg.twdinspection.inspection.reports.source.InspReportData;
import com.cgg.twdinspection.inspection.reports.source.StudentAttendenceInfo;
import com.cgg.twdinspection.inspection.ui.InstMenuMainActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

public class ReportStudentsAttendActivity extends AppCompatActivity {
    ReportActivityStudentsAttendanceBinding binding;
    SharedPreferences sharedPreferences;
    String instId, officerId;
    private InspReportData inspReportData;
    private List<StudentAttendenceInfo> studentAttendInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.report_activity_students_attendance);
        binding.actionBar.headerTitle.setText(getString(R.string.stu_att));

        binding.actionBar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.actionBar.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportStudentsAttendActivity.this, InstReportsMenuActivity.class));
            }
        });


        sharedPreferences = TWDApplication.get(this).getPreferences();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");

        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.INSP_REP_DATA, "");
        inspReportData = gson.fromJson(data, InspReportData.class);

        String jsonObject = gson.toJson(inspReportData.getStudentAttendenceInfo());
        if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("{}")) {
            studentAttendInfoList = inspReportData.getStudentAttendenceInfo();
            if (studentAttendInfoList != null && studentAttendInfoList.size() > 0) {
                setAdapter(studentAttendInfoList);
            }
        }


        binding.btnLayout.btnNext.setText(getResources().getString(R.string.next));
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportStudentsAttendActivity.this, ReportStaffAttendActivity.class));
            }
        });
    }

    private void setAdapter(List<StudentAttendenceInfo> studentAttendInfoList) {
        StuAttReportAdapter stockSubAdapter = new StuAttReportAdapter(this, studentAttendInfoList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(stockSubAdapter);
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.root, str, Snackbar.LENGTH_SHORT).show();
    }

    public void onHomeClick(View view) {
        startActivity(new Intent(this, InstMenuMainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
