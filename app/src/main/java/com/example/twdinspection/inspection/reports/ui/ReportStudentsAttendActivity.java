package com.example.twdinspection.inspection.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ReportActivityStudentsAttendanceBinding;
import com.example.twdinspection.gcc.adapter.DailyReqSubAdapter;
import com.example.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.example.twdinspection.inspection.reports.adapter.StuAttReportAdapter;
import com.example.twdinspection.inspection.reports.source.InspReportData;
import com.example.twdinspection.inspection.reports.source.StudentAttendenceInfo;
import com.example.twdinspection.inspection.ui.BaseActivity;
import com.example.twdinspection.inspection.ui.InstMenuMainActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

public class ReportStudentsAttendActivity extends BaseActivity {
    ReportActivityStudentsAttendanceBinding binding;
    SharedPreferences sharedPreferences;
    String instId, officerId;
    private InspReportData inspReportData;
    private List<StudentAttendenceInfo> studentAttendInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.report_activity_students_attendance, getResources().getString(R.string.stu_att));

        sharedPreferences = TWDApplication.get(this).getPreferences();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");

        Gson gson=new Gson();
        String data = sharedPreferences.getString(AppConstants.INSP_REP_DATA, "");
        inspReportData=gson.fromJson(data, InspReportData.class);

        if(inspReportData!=null) {
            studentAttendInfoList = inspReportData.getStudentAttendenceInfo();
            if(studentAttendInfoList!=null && studentAttendInfoList.size()>0){
                setAdapter(studentAttendInfoList);
            }
        }

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
