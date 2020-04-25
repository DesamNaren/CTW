package com.cgg.twdinspection.engineering_works.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.cgg.twdinspection.databinding.ActivityEngSyncBinding;
import com.cgg.twdinspection.engineering_works.interfaces.EngSyncInterface;
import com.cgg.twdinspection.engineering_works.room.repository.EngSyncRepository;
import com.cgg.twdinspection.engineering_works.source.GrantScheme;
import com.cgg.twdinspection.engineering_works.source.GrantSchemesResponse;
import com.cgg.twdinspection.engineering_works.source.SectorsEntity;
import com.cgg.twdinspection.engineering_works.source.SectorsResponse;
import com.cgg.twdinspection.engineering_works.source.WorkDetail;
import com.cgg.twdinspection.engineering_works.source.WorksMasterResponse;
import com.cgg.twdinspection.engineering_works.viewmodels.EngSyncViewModel;
import com.cgg.twdinspection.inspection.ui.DashboardActivity;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class EngSyncActivity extends AppCompatActivity implements EngSyncInterface, ErrorHandlerInterface {
    private EngSyncRepository engSyncRepository;
    ActivityEngSyncBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CustomProgressDialog customProgressDialog;
    InstMainViewModel instMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(EngSyncActivity.this, R.layout.activity_eng_sync);

        customProgressDialog = new CustomProgressDialog(this);
        EngSyncViewModel viewModel = new EngSyncViewModel(EngSyncActivity.this, getApplication(), binding);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        engSyncRepository = new EngSyncRepository(getApplication());
        binding.header.headerTitle.setText(getResources().getString(R.string.sync_activity));
        instMainViewModel = new InstMainViewModel(getApplication());

        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EngSyncActivity.this, DashboardActivity.class)
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
        binding.btnSector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(EngSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<SectorsResponse> sectorResponse = viewModel.getSectorResponse();
                    sectorResponse.observe(EngSyncActivity.this, new Observer<SectorsResponse>() {
                        @Override
                        public void onChanged(SectorsResponse sectorsResponse) {
                            sectorResponse.removeObservers(EngSyncActivity.this);

                            if (sectorsResponse != null && sectorsResponse.getStatusCode() != null) {
                                if (sectorsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                    if (sectorsResponse.getSectorsEntitys() != null && sectorsResponse.getSectorsEntitys().size() > 0) {
                                        engSyncRepository.insertEngSectors(EngSyncActivity.this, sectorsResponse.getSectorsEntitys());
                                    } else {
                                        Utils.customErrorAlert(EngSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_districts));
                                    }
                                } else if (sectorsResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                    Snackbar.make(binding.root, sectorsResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                                } else {
                                    callSnackBar(getString(R.string.something));
                                }
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        }
                    });
                } else {
                    Utils.customErrorAlert(EngSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
                }
            }
        });

        binding.syncBtnSchemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(EngSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<GrantSchemesResponse> schemesResponse = viewModel.getSchemesResponse();

                    schemesResponse.observe(EngSyncActivity.this, new Observer<GrantSchemesResponse>() {
                        @Override
                        public void onChanged(GrantSchemesResponse grantSchemesResponse) {
                            schemesResponse.removeObservers(EngSyncActivity.this);
                            if (grantSchemesResponse != null && grantSchemesResponse.getStatusCode() != null) {
                                if (Integer.valueOf(grantSchemesResponse.getStatusCode()) == AppConstants.SUCCESS_CODE) {
                                    if (grantSchemesResponse.getSchemes() != null && grantSchemesResponse.getSchemes().size() > 0) {
                                        engSyncRepository.insertEngSchemes(EngSyncActivity.this, grantSchemesResponse.getSchemes());
                                    } else {
                                        Utils.customErrorAlert(EngSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_fin_year));
                                    }
                                } else if (grantSchemesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                    Snackbar.make(binding.root, grantSchemesResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                                } else {
                                    callSnackBar(getString(R.string.something));
                                }
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        }
                    });

                } else {
                    Utils.customErrorAlert(EngSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
                }
            }

        });

        binding.btnEngWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(EngSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<WorksMasterResponse> inspectionRemarkResponseLiveData = viewModel.getWorksMasterResponse();
                    inspectionRemarkResponseLiveData.observe(EngSyncActivity.this, new Observer<WorksMasterResponse>() {
                        @Override
                        public void onChanged(WorksMasterResponse worksMasterResponse) {
                            inspectionRemarkResponseLiveData.removeObservers(EngSyncActivity.this);
                            if (worksMasterResponse != null && worksMasterResponse.getStatusCode() != null) {
                                if (worksMasterResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                    if (worksMasterResponse.getWorkDetails() != null && worksMasterResponse.getWorkDetails().size() > 0) {
                                        engSyncRepository.insertWorkDetails(EngSyncActivity.this, worksMasterResponse.getWorkDetails());
                                    } else {
                                        Utils.customErrorAlert(EngSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_ins_rem));
                                    }
                                } else if (worksMasterResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                    Snackbar.make(binding.root, worksMasterResponse.getStatusMsg(), Snackbar.LENGTH_SHORT).show();
                                } else {
                                    callSnackBar(getString(R.string.something));
                                }
                            } else {
                                callSnackBar(getString(R.string.something));
                            }


                        }
                    });
                } else {
                    Utils.customErrorAlert(EngSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
                }
            }

        });
        viewModel.getEngWorks().observe(EngSyncActivity.this, new Observer<List<WorkDetail>>() {
            @Override
            public void onChanged(List<WorkDetail> workDetails) {
                if (workDetails != null && workDetails.size() > 0) {
                    binding.btnEngWorks.setText("Re-Download");
                } else {
                    binding.btnEngWorks.setText("Download");
                }
            }
        });
        viewModel.getGrantSchemes().observe(EngSyncActivity.this, new Observer<List<GrantScheme>>() {
            @Override
            public void onChanged(List<GrantScheme> grantSchemes) {
                if (grantSchemes != null && grantSchemes.size() > 0) {
                    binding.syncBtnSchemes.setText("Re-Download");
                } else {
                    binding.syncBtnSchemes.setText("Download");
                }
            }
        });
        viewModel.getSectors().observe(EngSyncActivity.this, new Observer<List<SectorsEntity>>() {
            @Override
            public void onChanged(List<SectorsEntity> sectorsEntities) {
                if (sectorsEntities != null && sectorsEntities.size() > 0) {
                    binding.btnSector.setText("Re-Download");
                } else {
                    binding.btnSector.setText("Download");
                }
            }
        });
    }


        void callSnackBar (String msg){
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
        public void onBackPressed () {
            startActivity(new Intent(EngSyncActivity.this, EngineeringDashboardActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

            finish();
        }

        @Override
        public void handleError (Throwable e, Context context){
            customProgressDialog.hide();
            String errMsg = ErrorHandler.handleError(e, context);
            callSnackBar(errMsg);
        }

        @Override
        public void setorsCnt ( int cnt){
            customProgressDialog.hide();
            try {
                if (cnt > 0) {
                    Log.i("SC_CNT", "schCount: " + cnt);
                    binding.btnSector.setText("Re-Download");
                    Utils.customSyncSuccessAlert(EngSyncActivity.this, getResources().getString(R.string.app_name),
                            "Sectors synced successfully");
                    // Success Alert;
                } else {
                    Utils.customErrorAlert(EngSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_scheme));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void schemesCnt ( int cnt){
            customProgressDialog.hide();
            try {
                if (cnt > 0) {
                    Log.i("SC_CNT", "schCount: " + cnt);
                    binding.syncBtnSchemes.setText("Re-Download");
                    Utils.customSyncSuccessAlert(EngSyncActivity.this, getResources().getString(R.string.app_name),
                            "Schemes synced successfully");
                    // Success Alert;
                } else {
                    Utils.customErrorAlert(EngSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_scheme));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void engWorksCnt ( int cnt){
            customProgressDialog.hide();
            try {
                if (cnt > 0) {
                    Log.i("SC_CNT", "schCount: " + cnt);
                    binding.btnEngWorks.setText("Re-Download");
                    Utils.customSyncSuccessAlert(EngSyncActivity.this, getResources().getString(R.string.app_name),
                            "ENgineering works synced successfully");
                    // Success Alert;
                } else {
                    Utils.customErrorAlert(EngSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_scheme));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
