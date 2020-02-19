package com.example.twdinspection.inspection.ui.reports;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.common.ErrorHandler;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.CustomProgressDialog;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityInspReportBinding;
import com.example.twdinspection.databinding.ActivityInspReportDashboardBinding;
import com.example.twdinspection.gcc.adapter.GCCReportAdapter;
import com.example.twdinspection.gcc.interfaces.ReportClickCallback;
import com.example.twdinspection.inspection.adapter.InspectionReportAdapter;
import com.example.twdinspection.inspection.interfaces.InspReportClickCallback;
import com.example.twdinspection.inspection.source.reports.AcademicOverview;
import com.example.twdinspection.inspection.source.reports.CoCurricularInfo;
import com.example.twdinspection.inspection.source.reports.DietIssues;
import com.example.twdinspection.inspection.source.reports.Entitlements;
import com.example.twdinspection.inspection.source.reports.InspReportData;
import com.example.twdinspection.inspection.source.reports.InspReportResponse;
import com.example.twdinspection.inspection.viewmodel.InspectionReportsViewModel;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class InspectionReportsDashboard extends AppCompatActivity implements ErrorHandlerInterface, InspReportClickCallback {

    ActivityInspReportBinding binding;
    InspectionReportsViewModel viewModel;
    CustomProgressDialog customProgressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String officerId;
//    AcademicOverview academicOverview;
//    CoCurricularInfo coCurricularInfo;
//    DietIssues dietIssues;
//    Entitlements entitlements;
//    List<Object> staffAttendenceInfo;
//    List<Object> studentAttendenceInfo;
    List<InspReportData> inspReportDataList;
    InspectionReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insp_report);
        binding.header.headerTitle.setText(getString(R.string.insp_reports));
        binding.header.ivHome.setVisibility(View.GONE);
        viewModel = new InspectionReportsViewModel(InspectionReportsDashboard.this, getApplication());
        customProgressDialog = new CustomProgressDialog(this);

//        staffAttendenceInfo = new ArrayList<>();
//        studentAttendenceInfo = new ArrayList<>();
        inspReportDataList = new ArrayList<>();
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        if (Utils.checkInternetConnection(InspectionReportsDashboard.this)) {
            customProgressDialog.show();
            LiveData<InspReportResponse> inspectionReports = viewModel.getInspectionReports(officerId);
            inspectionReports.observe(InspectionReportsDashboard.this, new Observer<InspReportResponse>() {
                @Override
                public void onChanged(InspReportResponse inspReportResponse) {
                    customProgressDialog.hide();
                    inspectionReports.removeObservers(InspectionReportsDashboard.this);
                    if (inspReportResponse != null) {
                        if (inspReportResponse.getData() != null && inspReportResponse.getData().size() > 0) {
                            for (int i = 0; i < inspReportResponse.getData().size(); i++) {
                                InspReportData inspReportData = inspReportResponse.getData().get(i);
                                inspReportDataList.add(inspReportData);
//                                Gson gson = new Gson();
//                                String data = gson.toJson(inspReportData);
//                                editor.putString(AppConstants.REP_DATA, data);
//                                editor.apply();
                            }

                            if(inspReportDataList!=null && inspReportDataList.size()>0){
                                binding.recyclerView.setVisibility(View.VISIBLE);
                                binding.tvEmpty.setVisibility(View.GONE);
                                adapter = new InspectionReportAdapter(InspectionReportsDashboard.this,inspReportDataList );
                                binding.recyclerView.setLayoutManager(new LinearLayoutManager(InspectionReportsDashboard.this));
                                binding.recyclerView.setAdapter(adapter);
                            }else{
                                binding.recyclerView.setVisibility(View.GONE);
                                binding.tvEmpty.setVisibility(View.VISIBLE);
                                callSnackBar("No data available");
                            }

                        } else if (inspReportResponse.getData() != null && inspReportResponse.getData().size() == 0) {
                            callSnackBar("No data available");
                        } else {
                            callSnackBar(getString(R.string.something));
                        }
                    } else {
                        callSnackBar(getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customWarningAlert(InspectionReportsDashboard.this, getResources().getString(R.string.app_name), "Please check internet");
        }
    }

    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        callSnackBar(errMsg);
    }


    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.root, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

        snackbar.show();
    }
    @Override
    public void onItemClick(InspReportData reportData) {
        Gson gson=new Gson();
        String data=gson.toJson(reportData);
        editor.putString(AppConstants.INSP_REP_DATA,data);
        editor.apply();
        startActivity(new Intent(this, InstReportsMenuActivity.class));
    }

}
