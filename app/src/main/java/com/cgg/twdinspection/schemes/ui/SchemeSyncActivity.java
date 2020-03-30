package com.cgg.twdinspection.schemes.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivitySchemeSyncBinding;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.cgg.twdinspection.schemes.interfaces.SchemeDMVInterface;
import com.cgg.twdinspection.schemes.room.repository.SchemeSyncRepository;
import com.cgg.twdinspection.schemes.source.dmv.SchemeDMVResponse;
import com.cgg.twdinspection.schemes.source.finyear.FinancialYearResponse;
import com.cgg.twdinspection.schemes.source.remarks.InspectionRemarkResponse;
import com.cgg.twdinspection.schemes.source.schemes.SchemeEntity;
import com.cgg.twdinspection.schemes.source.schemes.SchemeResponse;
import com.cgg.twdinspection.schemes.viewmodel.SchemeSyncViewModel;
import com.google.android.material.snackbar.Snackbar;

public class SchemeSyncActivity extends AppCompatActivity implements SchemeDMVInterface, ErrorHandlerInterface {
    private SchemeSyncRepository schemeSyncRepository;
    private SchemeDMVResponse schemeDMVResponse;
    ActivitySchemeSyncBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CustomProgressDialog customProgressDialog;
    private String cacheDate, currentDate;
    InstMainViewModel instMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(SchemeSyncActivity.this, R.layout.activity_scheme_sync);

        customProgressDialog = new CustomProgressDialog(this);
        SchemeSyncViewModel viewModel = new SchemeSyncViewModel(SchemeSyncActivity.this, getApplication(), binding);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        schemeSyncRepository = new SchemeSyncRepository(getApplication());
        binding.header.headerTitle.setText(getResources().getString(R.string.sync_activity));
        instMainViewModel=new InstMainViewModel(getApplication());

        binding.header.ivHome.setVisibility(View.GONE);

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
        binding.btnSchemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(SchemeSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<SchemeDMVResponse> schemeDMVReposnse = viewModel.getSchemeDMVReposnse();
                    schemeDMVReposnse.observe(SchemeSyncActivity.this, new Observer<SchemeDMVResponse>() {
                        @Override
                        public void onChanged(SchemeDMVResponse schemeDMVResponse) {
                            schemeDMVReposnse.removeObservers(SchemeSyncActivity.this);
                            SchemeSyncActivity.this.schemeDMVResponse = schemeDMVResponse;
                            if (schemeDMVResponse.getDistricts() != null && schemeDMVResponse.getDistricts().size() > 0) {
                                schemeSyncRepository.insertSchemeDistricts(SchemeSyncActivity.this, schemeDMVResponse.getDistricts());
                            }
                        }
                    });
                } else {
                    Utils.customWarningAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                }
            }
        });

        binding.syncBtnYears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(SchemeSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<FinancialYearResponse> financialYearResponseLiveData = viewModel.getFinYearResponse();

                    financialYearResponseLiveData.observe(SchemeSyncActivity.this, new Observer<FinancialYearResponse>() {
                        @Override
                        public void onChanged(FinancialYearResponse financialYearResponse) {
                            financialYearResponseLiveData.removeObservers(SchemeSyncActivity.this);
                            if (financialYearResponse != null && financialYearResponse.getStatusCode() != null) {
                                if (Integer.valueOf(financialYearResponse.getStatusCode()) == AppConstants.SUCCESS_CODE) {
                                    if (financialYearResponse.getFinYears() != null && financialYearResponse.getFinYears().size() > 0) {
                                        schemeSyncRepository.insertFinYears(SchemeSyncActivity.this, financialYearResponse.getFinYears());
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
                    Utils.customWarningAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                }
            }

        });

        binding.btnInstInsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(SchemeSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<InspectionRemarkResponse> inspectionRemarkResponseLiveData = viewModel.getInspectionRemarks();
                    inspectionRemarkResponseLiveData.observe(SchemeSyncActivity.this, new Observer<InspectionRemarkResponse>() {
                        @Override
                        public void onChanged(InspectionRemarkResponse inspectionRemarkResponse) {
                            inspectionRemarkResponseLiveData.removeObservers(SchemeSyncActivity.this);
                            if (inspectionRemarkResponse != null && inspectionRemarkResponse.getStatusCode() != null) {
                                if (Integer.valueOf(inspectionRemarkResponse.getStatusCode()) == AppConstants.SUCCESS_CODE) {
                                    if (inspectionRemarkResponse.getSchemes() != null && inspectionRemarkResponse.getSchemes().size() > 0) {
                                        schemeSyncRepository.insertInsRemarks(SchemeSyncActivity.this, inspectionRemarkResponse.getSchemes());
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
                    Utils.customWarningAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                }
            }

        });

        binding.syncLlSchemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(SchemeSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<SchemeResponse> schemeResponseLiveData = viewModel.getSchemeResponse();
                    schemeResponseLiveData.observe(SchemeSyncActivity.this, new Observer<SchemeResponse>() {
                        @Override
                        public void onChanged(SchemeResponse schemeResponse) {
                            schemeResponseLiveData.removeObservers(SchemeSyncActivity.this);
                            if (schemeResponse != null && schemeResponse.getStatusCode() != null) {
                                if (Integer.valueOf(schemeResponse.getStatusCode()) == AppConstants.SUCCESS_CODE) {
                                    if (schemeResponse.getSchemes() != null && schemeResponse.getSchemes().size() > 0) {
                                        schemeResponse.getSchemes().add(0, new SchemeEntity(false, "ALL", "-1"));
                                        schemeSyncRepository.insertSchemes(SchemeSyncActivity.this, schemeResponse.getSchemes());
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
                    Utils.customWarningAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), "Please check internet");
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
                Utils.customWarningAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), "No districts found");
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
                Utils.customWarningAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), "No mandals found");
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
                Log.i("V_CNT", "vilCount: " + cnt);
                Utils.customSyncSuccessAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name),
                        "District master synced successfully");
                // Success Alert;
            } else {
                Utils.customWarningAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), "No villages found");
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
                Log.i("F_CNT", "finCount: " + cnt);
                Utils.customSyncSuccessAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name),
                        "Financial years synced successfully");
                // Success Alert;
            } else {
                Utils.customWarningAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), "No financial years found");
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
                Log.i("IN_CNT", "insCount: " + cnt);
                Utils.customSyncSuccessAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name),
                        "Inspection remarks synced successfully");
                // Success Alert;
            } else {
                Utils.customWarningAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), "No inspection remarks found");
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
                Log.i("SC_CNT", "schCount: " + cnt);
                Utils.customSyncSuccessAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name),
                        "Schemes synced successfully");
                // Success Alert;
            } else {
                Utils.customWarningAlert(SchemeSyncActivity.this, getResources().getString(R.string.app_name), "No schemes found");
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

            currentDate = Utils.getCurrentDate();
            cacheDate = sharedPreferences.getString(AppConstants.CACHE_DATE, "");

            if (!TextUtils.isEmpty(cacheDate)) {
                if (!cacheDate.equalsIgnoreCase(currentDate)) {

                    Utils.ShowDeviceSessionAlert(this,
                            getResources().getString(R.string.app_name),
                            getString(R.string.ses_expire_re), instMainViewModel);
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cacheDate = currentDate;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.CACHE_DATE, cacheDate);
        editor.commit();
    }
}
