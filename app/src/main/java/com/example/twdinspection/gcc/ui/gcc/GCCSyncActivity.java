package com.example.twdinspection.gcc.ui.gcc;

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

import com.example.twdinspection.R;
import com.example.twdinspection.common.ErrorHandler;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.CustomProgressDialog;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityGccSyncBinding;
import com.example.twdinspection.gcc.interfaces.GCCDivisionInterface;
import com.example.twdinspection.gcc.room.repository.GCCSyncRepository;
import com.example.twdinspection.gcc.source.divisions.GetOfficesResponse;
import com.example.twdinspection.gcc.source.suppliers.depot.DRDepotMasterResponse;
import com.example.twdinspection.gcc.source.suppliers.dr_godown.DRGoDownMasterResponse;
import com.example.twdinspection.gcc.source.suppliers.mfp.MFPGoDownMasterResponse;
import com.example.twdinspection.gcc.source.suppliers.punit.PUnitMasterResponse;
import com.example.twdinspection.gcc.viewmodel.GCCSyncViewModel;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;

public class GCCSyncActivity extends AppCompatActivity implements GCCDivisionInterface, ErrorHandlerInterface {
    private GCCSyncRepository gccSyncRepository;
    ActivityGccSyncBinding binding;
    SharedPreferences sharedPreferences;
    CustomProgressDialog customProgressDialog;
    private String cacheDate, currentDate;
    InstMainViewModel instMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(GCCSyncActivity.this, R.layout.activity_gcc_sync);

        customProgressDialog = new CustomProgressDialog(this);
        GCCSyncViewModel viewModel = new GCCSyncViewModel(GCCSyncActivity.this, getApplication(), binding);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        gccSyncRepository = new GCCSyncRepository(getApplication());
        binding.header.headerTitle.setText(getResources().getString(R.string.sync_activity));
        instMainViewModel = new InstMainViewModel(getApplication());

        binding.header.ivHome.setVisibility(View.GONE);

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
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
        binding.llDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(GCCSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<GetOfficesResponse> officesResponseLiveData = viewModel.getDivisionsResponse();
                    officesResponseLiveData.observe(GCCSyncActivity.this, new Observer<GetOfficesResponse>() {
                        @Override
                        public void onChanged(GetOfficesResponse officesResponse) {
                            customProgressDialog.hide();
                            officesResponseLiveData.removeObservers(GCCSyncActivity.this);
                            if (officesResponse != null && officesResponse.getStatusCode() != null) {
                                if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                    if (officesResponse.getDivisions() != null && officesResponse.getDivisions().size() > 0) {
                                        gccSyncRepository.insertDivisions(GCCSyncActivity.this, officesResponse.getDivisions());
                                    } else {
                                        Snackbar.make(binding.root, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
                                    }
                                } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                    Snackbar.make(binding.root, officesResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                                } else {
                                    callSnackBar(getString(R.string.something));
                                }
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        }
                    });
                } else {
                    Utils.customWarningAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                }
            }
        });

        binding.llDrGodown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(GCCSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<DRGoDownMasterResponse> drDepotMasterResponseLiveData = viewModel.getDRGoDownsResponse();

                    drDepotMasterResponseLiveData.observe(GCCSyncActivity.this, new Observer<DRGoDownMasterResponse>() {
                        @Override
                        public void onChanged(DRGoDownMasterResponse suppliersResponse) {
                            customProgressDialog.hide();
                            drDepotMasterResponseLiveData.removeObservers(GCCSyncActivity.this);
                            if (suppliersResponse != null && suppliersResponse.getStatusCode() != null) {
                                if (suppliersResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                    if (suppliersResponse.getGodowns() != null && suppliersResponse.getGodowns().size() > 0) {
                                        gccSyncRepository.insertDRGoDowns(GCCSyncActivity.this, suppliersResponse.getGodowns());
                                    } else {
                                        Snackbar.make(binding.root, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
                                    }
                                } else if (suppliersResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                    Snackbar.make(binding.root, suppliersResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                                } else {
                                    callSnackBar(getString(R.string.something));
                                }
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        }
                    });
                } else {
                    Utils.customWarningAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                }
            }

        });

        binding.llDrDepot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(GCCSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<DRDepotMasterResponse> drDepotMasterResponseLiveData = viewModel.getDRDepotsResponse();

                    drDepotMasterResponseLiveData.observe(GCCSyncActivity.this, new Observer<DRDepotMasterResponse>() {
                        @Override
                        public void onChanged(DRDepotMasterResponse suppliersResponse) {
                            customProgressDialog.hide();
                            drDepotMasterResponseLiveData.removeObservers(GCCSyncActivity.this);
                            if (suppliersResponse != null && suppliersResponse.getStatusCode() != null) {
                                if (suppliersResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                    if (suppliersResponse.getDRDepots() != null && suppliersResponse.getDRDepots().size() > 0) {
                                        gccSyncRepository.insertDRDepots(GCCSyncActivity.this, suppliersResponse.getDRDepots());
                                    } else {
                                        Snackbar.make(binding.root, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
                                    }
                                } else if (suppliersResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                    Snackbar.make(binding.root, suppliersResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                                } else {
                                    callSnackBar(getString(R.string.something));
                                }
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        }
                    });
                } else {
                    Utils.customWarningAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                }
            }

        });

        binding.llMfpGodown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(GCCSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<MFPGoDownMasterResponse> mfpGoDownMasterResponseLiveData = viewModel.getMFPGodownsResponse();

                    mfpGoDownMasterResponseLiveData.observe(GCCSyncActivity.this, new Observer<MFPGoDownMasterResponse>() {
                        @Override
                        public void onChanged(MFPGoDownMasterResponse suppliersResponse) {
                            customProgressDialog.hide();
                            mfpGoDownMasterResponseLiveData.removeObservers(GCCSyncActivity.this);
                            if (suppliersResponse != null && suppliersResponse.getStatusCode() != null) {
                                if (suppliersResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                    if (suppliersResponse.getMfpGoDowns() != null && suppliersResponse.getMfpGoDowns().size() > 0) {
                                        gccSyncRepository.insertMFPGoDowns(GCCSyncActivity.this, suppliersResponse.getMfpGoDowns());
                                    } else {
                                        Snackbar.make(binding.root, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
                                    }
                                } else if (suppliersResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                    Snackbar.make(binding.root, suppliersResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                                } else {
                                    callSnackBar(getString(R.string.something));
                                }
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        }
                    });
                } else {
                    Utils.customWarningAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                }
            }

        });


        binding.llPUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(GCCSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<PUnitMasterResponse> pUnitMasterResponseLiveData = viewModel.getPUnitMasterResponse();

                    pUnitMasterResponseLiveData.observe(GCCSyncActivity.this, new Observer<PUnitMasterResponse>() {
                        @Override
                        public void onChanged(PUnitMasterResponse suppliersResponse) {
                            customProgressDialog.hide();
                            pUnitMasterResponseLiveData.removeObservers(GCCSyncActivity.this);
                            if (suppliersResponse != null && suppliersResponse.getStatusCode() != null) {
                                if (suppliersResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                    if (suppliersResponse.getpUnits() != null && suppliersResponse.getpUnits().size() > 0) {
                                        gccSyncRepository.insertPUnits(GCCSyncActivity.this, suppliersResponse.getpUnits());
                                    } else {
                                        Snackbar.make(binding.root, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
                                    }
                                } else if (suppliersResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                    Snackbar.make(binding.root, suppliersResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                                } else {
                                    callSnackBar(getString(R.string.something));
                                }
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        }
                    });
                } else {
                    Utils.customWarningAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "Please check internet");
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
        startActivity(new Intent(GCCSyncActivity.this, GCCDashboardActivity.class)
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
                    instMainViewModel.deleteAllInspectionData();
                    Utils.ShowDeviceSessionAlert(this,
                            getResources().getString(R.string.app_name),
                            getString(R.string.ses_expire_re));
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

    @Override
    public void divisionCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                Log.i("DIV_CNT", "divCount: " + cnt);
                Utils.customSyncSuccessAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name),
                        "Division master synced successfully");
            } else {
                Utils.customWarningAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "No divisions found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drDepotCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                Log.i("SUP_CNT", "supCount: " + cnt);
                Utils.customSyncSuccessAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name),
                        "DR Depot master synced successfully");
            } else {
                Utils.customWarningAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "No No DR Depots found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drGoDownCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                Log.i("SUP_CNT", "drGodCount: " + cnt);
                Utils.customSyncSuccessAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name),
                        "DR GoDown master synced successfully");
            } else {
                Utils.customWarningAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "No DR GoDowns found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mfpGoDownCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                Log.i("SUP_CNT", "mfpGodCount: " + cnt);
                Utils.customSyncSuccessAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name),
                        "MFP GoDown master synced successfully");
            } else {
                Utils.customWarningAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "No MFP GoDowns found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pUNitCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                Log.i("SUP_CNT", "pUnitCount: " + cnt);
                Utils.customSyncSuccessAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name),
                        "Processing Unit master synced successfully");
            } else {
                Utils.customWarningAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "No Processing Units found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
