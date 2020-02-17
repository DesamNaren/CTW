package com.example.twdinspection.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityDashboardBinding;
import com.example.twdinspection.databinding.ActivityReportBinding;
import com.example.twdinspection.gcc.ui.reports.GCCReportsDashboard;
import com.example.twdinspection.inspection.source.reports.ReportCountsResponse;
import com.example.twdinspection.inspection.viewmodel.LoginCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.LoginViewModel;
import com.example.twdinspection.inspection.viewmodel.ReportsCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.ReportsViewModel;
import com.example.twdinspection.schemes.adapter.BenReportAdapter;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;

public class ReportActivity extends AppCompatActivity implements ErrorHandlerInterface {

    ActivityReportBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String officerId;
    ReportsViewModel viewModel;
    int gccCnt,instCnt,SchemesCnt;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report);
        viewModel =
                ViewModelProviders.of(ReportActivity.this,
                        new ReportsCustomViewModel(ReportActivity.this)).get(ReportsViewModel.class);

        binding.header.headerTitle.setVisibility(View.VISIBLE);
        binding.header.headerTitle.setText(getResources().getString(R.string.report));
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
            officerId=sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewModel.getReportCounts(officerId).observe(this, reportCountsResponse -> {

            if (reportCountsResponse != null && reportCountsResponse.getStatusCode() != null) {
                if (reportCountsResponse.getStatusCode() != null && reportCountsResponse.getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
                    binding.gccCnt.setText("GCC "+reportCountsResponse.getGcc());
                    binding.schemeCnt.setText("Schemes"+reportCountsResponse.getSchemes());
                    binding.instCnt.setText("Institute "+reportCountsResponse.getSchools());
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
                if(gccCnt>0){
//                    startActivity(new Intent(ReportActivity.this, GCCReportsDashboard.class));
                }else{
                    callSnackBar("No data found");
                }
                startActivity(new Intent(ReportActivity.this, GCCReportsDashboard.class));
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
