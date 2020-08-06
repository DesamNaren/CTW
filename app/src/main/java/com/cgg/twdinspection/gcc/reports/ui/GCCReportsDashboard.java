package com.cgg.twdinspection.gcc.reports.ui;

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

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityReportDashboardBinding;
import com.cgg.twdinspection.gcc.reports.source.GCCReportResponse;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.cgg.twdinspection.gcc.viewmodel.GCCReportsViewModel;
import com.cgg.twdinspection.inspection.ui.DashboardActivity;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
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
    List<ReportData> petrolpump;
    List<ReportData> lpg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_dashboard);
        binding.header.headerTitle.setText(getString(R.string.gcc_reports));
        viewModel = new GCCReportsViewModel(GCCReportsDashboard.this, getApplication());
        customProgressDialog = new CustomProgressDialog(this);

        drDepot = new ArrayList<>();
        drGodown = new ArrayList<>();
        mfpGodown = new ArrayList<>();
        processingUnit = new ArrayList<>();
        petrolpump = new ArrayList<>();
        lpg = new ArrayList<>();
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GCCReportsDashboard.this, DashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();

            editor.putString(AppConstants.DR_Depot_Report, "");
            editor.putString(AppConstants.DR_Godown_Report, "");
            editor.putString(AppConstants.MFP_Godown_Report, "");
            editor.putString(AppConstants.PUnit_Report, "");
            editor.putString(AppConstants.Petrol_Report, "");
            editor.putString(AppConstants.lpg_Report, "");
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
                if (drDepot != null && drDepot.size() > 0) {
                    Gson gson = new Gson();
                    String drDepotData = gson.toJson(drDepot);
                    editor.putString(AppConstants.Selected_Supp_Report, drDepotData);
                    editor.commit();
                    startActivity(new Intent(GCCReportsDashboard.this, GCCReportActivity.class));
                } else {
                    callSnackBar("No data found");
                }
            }
        });

        binding.btnDrGodown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drGodown != null && drGodown.size() > 0) {
                    Gson gson = new Gson();
                    String drGodownData = gson.toJson(drGodown);
                    editor.putString(AppConstants.Selected_Supp_Report, drGodownData);
                    editor.commit();
                    startActivity(new Intent(GCCReportsDashboard.this, GCCReportActivity.class));
                } else {
                    callSnackBar("No data found");
                }
            }
        });

        binding.btnMfpGodown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mfpGodown != null && mfpGodown.size() > 0) {
                    Gson gson = new Gson();
                    String data = gson.toJson(mfpGodown);
                    editor.putString(AppConstants.Selected_Supp_Report, data);
                    editor.commit();
                    startActivity(new Intent(GCCReportsDashboard.this, GCCReportActivity.class));
                } else {
                    callSnackBar("No data found");
                }
            }
        });

        binding.btnPUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (processingUnit != null && processingUnit.size() > 0) {
                    Gson gson = new Gson();
                    String data = gson.toJson(processingUnit);
                    editor.putString(AppConstants.Selected_Supp_Report, data);
                    editor.commit();
                    startActivity(new Intent(GCCReportsDashboard.this, GCCReportActivity.class));
                } else {
                    callSnackBar("No data found");
                }
            }
        });

        binding.btnPetrolPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (petrolpump != null && petrolpump.size() > 0) {
                    Gson gson = new Gson();
                    String data = gson.toJson(petrolpump);
                    editor.putString(AppConstants.Selected_Supp_Report, data);
                    editor.commit();
                    startActivity(new Intent(GCCReportsDashboard.this, GCCReportActivity.class));
                } else {
                    callSnackBar("No data found");
                }
            }
        });

        binding.btnLpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lpg != null && lpg.size() > 0) {
                    Gson gson = new Gson();
                    String data = gson.toJson(lpg);
                    editor.putString(AppConstants.Selected_Supp_Report, data);
                    editor.commit();
                    startActivity(new Intent(GCCReportsDashboard.this, GCCReportActivity.class));
                } else {
                    callSnackBar("No data found");
                }
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
                                    case AppConstants.REPORT_PETROL_REP:
                                        petrolpump.add(gccReportResponse.getData().get(i));
                                        break;
                                    case AppConstants.REPORT_LPG_REP:
                                        lpg.add(gccReportResponse.getData().get(i));
                                        break;
                                }
                            }
                            binding.drDepotCnt.setText(String.valueOf(drDepot.size()));
                            binding.drGodownCnt.setText(String.valueOf(drGodown.size()));
                            binding.mfpGodownCnt.setText(String.valueOf(mfpGodown.size()));
                            binding.pUnitCnt.setText(String.valueOf(processingUnit.size()));
                            binding.petrolPumpCnt.setText(String.valueOf(petrolpump.size()));
                            binding.lpgCnt.setText(String.valueOf(lpg.size()));

                            Gson gson = new Gson();
                            String drDepotData = gson.toJson(drDepot);
                            editor.putString(AppConstants.DR_Depot_Report, drDepotData);

                            String drGodownData = gson.toJson(drGodown);
                            editor.putString(AppConstants.DR_Godown_Report, drGodownData);

                            String mfpGodownData = gson.toJson(mfpGodown);
                            editor.putString(AppConstants.MFP_Godown_Report, mfpGodownData);

                            String pUnitData = gson.toJson(processingUnit);
                            editor.putString(AppConstants.PUnit_Report, pUnitData);

                            String petrolData = gson.toJson(petrolpump);
                            editor.putString(AppConstants.Petrol_Report, petrolData);

                            String lpgData = gson.toJson(lpg);
                            editor.putString(AppConstants.lpg_Report, lpgData);

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
