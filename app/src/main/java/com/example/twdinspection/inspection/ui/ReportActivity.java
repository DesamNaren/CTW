package com.example.twdinspection.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.twdinspection.R;
import com.example.twdinspection.common.ErrorHandler;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityReportBinding;
import com.example.twdinspection.inspection.reports.ui.InspectionReportsDashboard;
import com.example.twdinspection.gcc.reports.ui.GCCReportsDashboard;
import com.example.twdinspection.schemes.reports.ui.SchemesReportActivity;
import com.example.twdinspection.inspection.reports.source.ReportCountsResponse;
import com.example.twdinspection.inspection.viewmodel.ReportsCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.ReportsViewModel;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;

public class ReportActivity extends AppCompatActivity implements ErrorHandlerInterface {

    ActivityReportBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String officerId;
    ReportsViewModel viewModel;
    int gccCnt, instCnt, schemesCnt;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report);
        gccCnt = -1;
        instCnt = -1;
        schemesCnt = -1;
        viewModel =
                ViewModelProviders.of(ReportActivity.this,
                        new ReportsCustomViewModel(ReportActivity.this)).get(ReportsViewModel.class);

        binding.header.headerTitle.setVisibility(View.VISIBLE);
        binding.header.headerTitle.setText(getResources().getString(R.string.report));

        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportActivity.this, DashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

//        binding.btnGcc.setBackgroundResource(R.drawable.disabled);
//        binding.btnSchemes.setBackgroundResource(R.drawable.disabled);
//        binding.btnInstInsp.setBackgroundResource(R.drawable.disabled);
//        binding.btnGcc.setBackgroundResource(R.drawable.disabled);

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            binding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            binding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            binding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
            officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        LiveData<ReportCountsResponse> liveData = viewModel.getReportCounts(officerId);
        liveData.observe(this, reportCountsResponse -> {
            liveData.removeObservers(this);
            if (reportCountsResponse != null && reportCountsResponse.getStatusCode() != null) {
                if (reportCountsResponse.getStatusCode() != null && reportCountsResponse.getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
                    gccCnt = reportCountsResponse.getGcc();
                    schemesCnt = reportCountsResponse.getSchemes();
                    instCnt = reportCountsResponse.getSchools();

                    binding.gccCnt.setText(String.valueOf(gccCnt));
                    binding.schemeCnt.setText(String.valueOf(schemesCnt));
                    binding.instCnt.setText(String.valueOf(instCnt));

                } else if (reportCountsResponse.getStatusCode() != null && reportCountsResponse.getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {
                    callSnackBar(getString(R.string.something));
                } else {
                    callSnackBar(getString(R.string.something));
                }
            } else {
                callSnackBar(getString(R.string.something));
            }
        });

        binding.btnGcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gccCnt > 0) {
                    startActivity(new Intent(ReportActivity.this, GCCReportsDashboard.class));
                } else {
                    callSnackBar("No data found");
                }
            }
        });
        binding.btnInstInsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (instCnt > 0) {
//                    startActivity(new Intent(ReportActivity.this, InspectionReportsDashboard.class));
                } else {
                    callSnackBar("No data found");
                }
            }
        });

        binding.btnSchemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (schemesCnt > 0) {
                    startActivity(new Intent(ReportActivity.this, SchemesReportActivity.class));
                } else {
                    callSnackBar("No data found");
                }
            }
        });
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.rlRoot, msg, Snackbar.LENGTH_INDEFINITE);
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
    public void handleError(Throwable e, Context context) {
        String errMsg = ErrorHandler.handleError(e, context);
        callSnackBar(errMsg);
    }
}
