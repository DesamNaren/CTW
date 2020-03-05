package com.example.twdinspection.gcc.reports.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.twdinspection.R;
import com.example.twdinspection.common.ErrorHandler;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.CustomProgressDialog;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityReportDashboardBinding;
import com.example.twdinspection.gcc.reports.source.GCCReportResponse;
import com.example.twdinspection.gcc.reports.source.ReportData;
import com.example.twdinspection.gcc.viewmodel.GCCReportsViewModel;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GCCReportsDashboard extends AppCompatActivity implements ErrorHandlerInterface {

    ActivityReportDashboardBinding binding;
    GCCReportsViewModel viewModel;
    CustomProgressDialog customProgressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String officerId;
    List<ReportData> drDepot;
    List<ReportData> drGodown;
    List<ReportData> mfpGodown;
    List<ReportData> processingUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_dashboard);
        binding.header.headerTitle.setText(getString(R.string.gcc_reports));
        binding.header.ivHome.setVisibility(View.GONE);
        viewModel = new GCCReportsViewModel(GCCReportsDashboard.this, getApplication());
        customProgressDialog = new CustomProgressDialog(this);

        drDepot = new ArrayList<>();
        drGodown = new ArrayList<>();
        mfpGodown = new ArrayList<>();
        processingUnit = new ArrayList<>();
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();

            editor.putString(AppConstants.DR_Depot_Report, "");
            editor.putString(AppConstants.DR_Godown_Report, "");
            editor.putString(AppConstants.MFP_Godown_Report, "");
            editor.putString(AppConstants.PUnit_Report, "");
            editor.putString(AppConstants.Selected_Supp_Report, "");
            editor.commit();

            binding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            binding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            binding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
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

        binding.btnDrDepot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();
                String drDepotData = gson.toJson(drDepot);
                editor.putString(AppConstants.Selected_Supp_Report, drDepotData);
                editor.commit();
                startActivity(new Intent(GCCReportsDashboard.this, GCCReportActivity.class));
            }
        });

        binding.btnDrGodown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();
                String drGodownData = gson.toJson(drGodown);
                editor.putString(AppConstants.Selected_Supp_Report, drGodownData);
                editor.commit();
                startActivity(new Intent(GCCReportsDashboard.this, GCCReportActivity.class));
            }
        });

        binding.btnMfpGodown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();
                String data = gson.toJson(mfpGodown);
                editor.putString(AppConstants.Selected_Supp_Report, data);
                editor.commit();
                startActivity(new Intent(GCCReportsDashboard.this, GCCReportActivity.class));
            }
        });

        binding.btnPUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();
                String data = gson.toJson(processingUnit);
                editor.putString(AppConstants.Selected_Supp_Report, data);
                editor.commit();
                startActivity(new Intent(GCCReportsDashboard.this, GCCReportActivity.class));
            }
        });

        if (Utils.checkInternetConnection(GCCReportsDashboard.this)) {
            customProgressDialog.show();
            LiveData<GCCReportResponse> gccReports = viewModel.getGCCReports(officerId);
            gccReports.observe(GCCReportsDashboard.this, new Observer<GCCReportResponse>() {
                @Override
                public void onChanged(GCCReportResponse gccReportResponse) {
                    customProgressDialog.hide();
                    gccReports.removeObservers(GCCReportsDashboard.this);
                    if (gccReportResponse != null) {
                        if (gccReportResponse.getData() != null && gccReportResponse.getData().size() > 0) {
                            for (int i = 0; i < gccReportResponse.getData().size(); i++) {
                                switch (gccReportResponse.getData().get(i).getSupplierType()) {
                                    case AppConstants.REPORT_GODOWN_REP:
                                        drGodown.add(gccReportResponse.getData().get(i));
                                        break;
                                    case AppConstants.REPORT_DEPOT_REP:
                                        drDepot.add(gccReportResponse.getData().get(i));
                                        break;
                                    case AppConstants.REPORT_MFP_GODOWN_REP:
                                        mfpGodown.add(gccReportResponse.getData().get(i));
                                        break;
                                    case AppConstants.REPORT_PUNIT_REP:
                                        processingUnit.add(gccReportResponse.getData().get(i));
                                        break;
                                }
                            }
                            binding.tvDepotCnt.setText(String.valueOf(drDepot.size()));
                            binding.tvGodownCnt.setText(String.valueOf(drGodown.size()));
                            binding.tvMfpGodownCnt.setText(String.valueOf(mfpGodown.size()));
                            binding.tvPunitCnt.setText(String.valueOf(processingUnit.size()));

                            Gson gson = new Gson();
                            String drDepotData = gson.toJson(drDepot);
                            editor.putString(AppConstants.DR_Depot_Report, drDepotData);

                            String drGodownData = gson.toJson(drGodown);
                            editor.putString(AppConstants.DR_Godown_Report, drGodownData);

                            String mfpGodownData = gson.toJson(mfpGodown);
                            editor.putString(AppConstants.MFP_Godown_Report, mfpGodownData);

                            String pUnitData = gson.toJson(processingUnit);
                            editor.putString(AppConstants.PUnit_Report, pUnitData);
                            editor.commit();

                        } else if (gccReportResponse.getData() != null && gccReportResponse.getData().size() == 0) {
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
            Utils.customWarningAlert(GCCReportsDashboard.this, getResources().getString(R.string.app_name), "Please check internet");
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

}
