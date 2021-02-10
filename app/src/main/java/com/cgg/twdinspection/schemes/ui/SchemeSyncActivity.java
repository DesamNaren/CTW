package com.cgg.twdinspection.schemes.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
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
import com.cgg.twdinspection.databinding.ActivitySchemeSyncBinding;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.cgg.twdinspection.schemes.interfaces.SchemeDMVInterface;
import com.cgg.twdinspection.schemes.room.repository.SchemeSyncRepository;
import com.cgg.twdinspection.schemes.source.dmv.SchemeDMVResponse;
import com.cgg.twdinspection.schemes.source.dmv.SchemeDistrict;
import com.cgg.twdinspection.schemes.source.finyear.FinancialYearResponse;
import com.cgg.twdinspection.schemes.source.finyear.FinancialYearsEntity;
import com.cgg.twdinspection.schemes.source.remarks.InspectionRemarkResponse;
import com.cgg.twdinspection.schemes.source.remarks.InspectionRemarksEntity;
import com.cgg.twdinspection.schemes.source.schemes.SchemeEntity;
import com.cgg.twdinspection.schemes.source.schemes.SchemeResponse;
import com.cgg.twdinspection.schemes.viewmodel.BenDetailsViewModel;
import com.cgg.twdinspection.schemes.viewmodel.BenReportViewModel;
import com.cgg.twdinspection.schemes.viewmodel.SchemeSyncViewModel;
import com.cgg.twdinspection.schemes.viewmodel.SchemesDMVViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SchemeSyncActivity extends AppCompatActivity implements SchemeDMVInterface, ErrorHandlerInterface {
    private SchemeSyncRepository schemeSyncRepository;
    private SchemeDMVResponse schemeDMVResponse;
    ActivitySchemeSyncBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CustomProgressDialog customProgressDialog;
    InstMainViewModel instMainViewModel;
    SchemesDMVViewModel viewModel;
    BenReportViewModel benReportViewModel;
    private BenDetailsViewModel benDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(SchemeSyncActivity.this, R.layout.activity_scheme_sync);
        viewModel = new SchemesDMVViewModel(getApplication());
        benDetailsViewModel = new BenDetailsViewModel(getApplication());
        benReportViewModel = new BenReportViewModel(SchemeSyncActivity.this);
        customProgressDialog = new CustomProgressDialog(this);
        SchemeSyncViewModel sviewModel = new SchemeSyncViewModel(SchemeSyncActivity.this, getApplication(), binding);
        binding.setViewModel(sviewModel);
        binding.executePendingBindings();
        schemeSyncRepository = new SchemeSyncRepository(getApplication());
        binding.header.headerTitle.setText(getResources().getString(R.string.sync_scheme_activity));
        instMainViewModel = new InstMainViewModel(getApplication());

        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchemeSyncActivity.this, DashboardMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            binding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            binding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            binding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.btnDmv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(SchemeSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<SchemeDMVResponse> schemeDMVReposnse = sviewModel.getSchemeDMVReposnse();
                    schemeDMVReposnse.observe(SchemeSyncActivity.this, new Observer<SchemeDMVResponse>() {
                        @Override
                        public void onChanged(SchemeDMVResponse schemeDMVResponse) {
                            schemeDMVReposnse.removeObservers(SchemeSyncActivity.this);
                            SchemeSyncActivity.this.schemeDMVResponse = schemeDMVResponse;

                            if (schemeDMVResponse != null && schemeDMVResponse.getStatusCode() != null) {
                                if (Integer.valueOf(schemeDMVResponse.getStatusCode()) == AppConstants.SUCCESS_CODE) {
                                    if (schemeDMVResponse.getDistricts() != null && schemeDMVResponse.getDistricts().size() > 0) {

                                        schemeSyncRepository.insertSchemeDistricts(SchemeSyncActivity.this, schemeDMVResponse.getDistricts());
                                    } else {
                                        Utils.customErrorAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_districts));
                                    }
                                } else if (Integer.valueOf(schemeDMVResponse.getStatusCode()) == AppConstants.FAILURE_CODE) {
                                    Snackbar.make(binding.root, schemeDMVResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                                } else {
                                    callSnackBar(getString(R.string.something));
                                }
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        }
                    });
                } else {
                    Utils.customErrorAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
                }
            }
        });

        binding.syncBtnYears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(SchemeSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<FinancialYearResponse> financialYearResponseLiveData = sviewModel.getFinYearResponse();

                    financialYearResponseLiveData.observe(SchemeSyncActivity.this, new Observer<FinancialYearResponse>() {
                        @Override
                        public void onChanged(FinancialYearResponse financialYearResponse) {
                            financialYearResponseLiveData.removeObservers(SchemeSyncActivity.this);
                            if (financialYearResponse != null && financialYearResponse.getStatusCode() != null) {
                                if (Integer.valueOf(financialYearResponse.getStatusCode()) == AppConstants.SUCCESS_CODE) {
                                    if (financialYearResponse.getFinYears() != null && financialYearResponse.getFinYears().size() > 0) {
                                        schemeSyncRepository.insertFinYears(SchemeSyncActivity.this, financialYearResponse.getFinYears());
                                    } else {
                                        Utils.customErrorAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_fin_year));
                                    }
                                } else if (Integer.valueOf(financialYearResponse.getStatusCode()) == AppConstants.FAILURE_CODE) {
                                    Snackbar.make(binding.root, financialYearResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                                } else {
                                    callSnackBar(getString(R.string.something));
                                }
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        }
                    });

                } else {
                    Utils.customErrorAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
                }
            }

        });

        binding.btnInstInsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(SchemeSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<InspectionRemarkResponse> inspectionRemarkResponseLiveData = sviewModel.getInspectionRemarks();
                    inspectionRemarkResponseLiveData.observe(SchemeSyncActivity.this, new Observer<InspectionRemarkResponse>() {
                        @Override
                        public void onChanged(InspectionRemarkResponse inspectionRemarkResponse) {
                            inspectionRemarkResponseLiveData.removeObservers(SchemeSyncActivity.this);
                            if (inspectionRemarkResponse != null && inspectionRemarkResponse.getStatusCode() != null) {
                                if (Integer.valueOf(inspectionRemarkResponse.getStatusCode()) == AppConstants.SUCCESS_CODE) {
                                    if (inspectionRemarkResponse.getSchemes() != null && inspectionRemarkResponse.getSchemes().size() > 0) {
                                        schemeSyncRepository.insertInsRemarks(SchemeSyncActivity.this, inspectionRemarkResponse.getSchemes());
                                    } else {
                                        Utils.customErrorAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_ins_rem));
                                    }
                                } else if (Integer.valueOf(inspectionRemarkResponse.getStatusCode()) == AppConstants.FAILURE_CODE) {
                                    Snackbar.make(binding.root, inspectionRemarkResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                                } else {
                                    callSnackBar(getString(R.string.something));
                                }
                            } else {
                                callSnackBar(getString(R.string.something));
                            }


                        }
                    });
                } else {
                    Utils.customErrorAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
                }
            }

        });

        binding.syncLlSchemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(SchemeSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<SchemeResponse> schemeResponseLiveData = sviewModel.getSchemeResponse();
                    schemeResponseLiveData.observe(SchemeSyncActivity.this, new Observer<SchemeResponse>() {
                        @Override
                        public void onChanged(SchemeResponse schemeResponse) {
                            schemeResponseLiveData.removeObservers(SchemeSyncActivity.this);
                            if (schemeResponse != null && schemeResponse.getStatusCode() != null) {
                                if (Integer.valueOf(schemeResponse.getStatusCode()) == AppConstants.SUCCESS_CODE) {
                                    if (schemeResponse.getSchemes() != null && schemeResponse.getSchemes().size() > 0) {
                                        schemeResponse.getSchemes().add(0, new SchemeEntity(false, "ALL", "-1"));
                                        schemeSyncRepository.insertSchemes(SchemeSyncActivity.this, schemeResponse.getSchemes());
                                    } else {
                                        Utils.customErrorAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_scheme));
                                    }

                                } else if (Integer.valueOf(schemeResponse.getStatusCode()) == AppConstants.FAILURE_CODE) {
                                    Snackbar.make(binding.root, schemeResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                                } else {
                                    callSnackBar(getString(R.string.something));
                                }
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        }
                    });
                } else {
                    Utils.customErrorAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
                }
            }
        });

        viewModel.getFinancialYrs().observe(SchemeSyncActivity.this, new Observer<List<FinancialYearsEntity>>() {
            @Override
            public void onChanged(List<FinancialYearsEntity> institutesEntities) {

                if (institutesEntities != null && institutesEntities.size() > 0) {
                    binding.syncBtnYears.setText("Re-Download");
                } else {
                    binding.syncBtnYears.setText("Download");
                }
            }
        });
        viewModel.getAllDistricts().observe(this, new Observer<List<SchemeDistrict>>() {
            @Override
            public void onChanged(List<SchemeDistrict> schemeDistricts) {
                if (schemeDistricts != null && schemeDistricts.size() > 0) {
                    binding.btnDmv.setText("Re-Download");
                } else {
                    binding.btnDmv.setText("Download");
                }
            }
        });


        benReportViewModel.getSchemeInfo().observe(this, new Observer<List<SchemeEntity>>() {
            @Override
            public void onChanged(List<SchemeEntity> schemesInfoEntities) {
                if (schemesInfoEntities != null && schemesInfoEntities.size() > 0) {
                    binding.syncLlSchemes.setText("Re-Download");
                } else {
                    binding.syncLlSchemes.setText("Download");
                }
            }
        });

        benDetailsViewModel.getRemarks().observe(this, new Observer<List<InspectionRemarksEntity>>() {
            @Override
            public void onChanged(List<InspectionRemarksEntity> inspectionRemarksEntities) {
                if (inspectionRemarksEntities != null && inspectionRemarksEntities.size() > 0) {

                    binding.btnInstInsp.setText("Re-Download");
                } else {
                    binding.btnInstInsp.setText("Download");
                }
            }
        });

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
    public void onBackPressed() {
        startActivity(new Intent(SchemeSyncActivity.this, SchemesDMVActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

        finish();
    }

    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        callSnackBar(errMsg);
    }

    @Override
    public void distCount(int cnt) {
        try {
            if (cnt > 0) {
                Log.i("D_CNT", "distCount: " + cnt);
                schemeSyncRepository.insertSchemeMandals(SchemeSyncActivity.this, schemeDMVResponse.getMandals());
            } else {
                Utils.customErrorAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_districts));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void manCount(int cnt) {
        try {
            if (cnt > 0) {
                Log.i("M_CNT", "manCount: " + cnt);
                schemeSyncRepository.insertSchemeVillages(SchemeSyncActivity.this, schemeDMVResponse.getVillages());
            } else {
                Utils.customErrorAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_mandals));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void vilCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                binding.btnDmv.setText("Re-Download");
                Utils.customSyncSuccessAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name),
                        "District master data downloaded successfully");
                // Success Alert;
            } else {
                Utils.customErrorAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_villages));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finYear(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                binding.syncBtnYears.setText("Re-Download");
                Utils.customSyncSuccessAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name),
                        "Financial years master data downloaded successfully");
                // Success Alert;
            } else {
                Utils.customErrorAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_fin_year));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insRemCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                binding.btnInstInsp.setText("Re-Download");
                Utils.customSyncSuccessAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name),
                        "Inspection remarks master data downloaded successfully");
                // Success Alert;
            } else {
                Utils.customErrorAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_ins_rem));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void schemeCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                binding.syncLlSchemes.setText("Re-Download");
                Utils.customSyncSuccessAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name),
                        "Schemes master data downloaded successfully");
                // Success Alert;
            } else {
                Utils.customErrorAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_scheme));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            boolean isAutomatic = Utils.isTimeAutomatic(this);
            if (!isAutomatic) {
                Utils.customTimeAlert(this,
                        getResources().getString(R.string.app_name),
                        getString(R.string.date_time));
                return;
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

}
