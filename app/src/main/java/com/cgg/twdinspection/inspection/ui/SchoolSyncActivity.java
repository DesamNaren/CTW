package com.cgg.twdinspection.inspection.ui;

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
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivitySchoolSyncBinding;
import com.cgg.twdinspection.inspection.interfaces.SchoolDMVInterface;
import com.cgg.twdinspection.inspection.interfaces.SchoolInstInterface;
import com.cgg.twdinspection.inspection.room.repository.SchoolSyncRepository;
import com.cgg.twdinspection.inspection.source.dmv.SchoolDMVResponse;
import com.cgg.twdinspection.inspection.source.inst_master.InstMasterResponse;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.SchoolSyncViewModel;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;

public class SchoolSyncActivity extends AppCompatActivity implements SchoolDMVInterface, SchoolInstInterface, ErrorHandlerInterface {
    private SchoolSyncRepository schoolSyncRepository;
    private SchoolDMVResponse schoolDMVResponse;
    private InstMasterResponse instMasterResponse;
    ActivitySchoolSyncBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String officerId;
    CustomProgressDialog customProgressDialog;
    private String cacheDate, currentDate;
    InstMainViewModel instMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customProgressDialog = new CustomProgressDialog(SchoolSyncActivity.this);

        binding = DataBindingUtil.setContentView(SchoolSyncActivity.this, R.layout.activity_school_sync);

        SchoolSyncViewModel viewModel = new SchoolSyncViewModel(SchoolSyncActivity.this, getApplication(), binding);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        schoolSyncRepository = new SchoolSyncRepository(getApplication());
        binding.header.headerTitle.setText(getResources().getString(R.string.sync_school_activity));
        instMainViewModel = new InstMainViewModel(getApplication());

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
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
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchoolSyncActivity.this, DashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

            }
        });

        binding.btnDmv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(SchoolSyncActivity.this)) {
                    customProgressDialog.show();

                    LiveData<SchoolDMVResponse> schoolDMVResponseLiveData = viewModel.getSchoolDMVReposnse("maadhavisriram");
                    schoolDMVResponseLiveData.observe(SchoolSyncActivity.this, new Observer<SchoolDMVResponse>() {
                        @Override
                        public void onChanged(SchoolDMVResponse schoolDMVResponse) {
                            schoolDMVResponseLiveData.removeObservers(SchoolSyncActivity.this);
                            SchoolSyncActivity.this.schoolDMVResponse = schoolDMVResponse;
                            if (schoolDMVResponse != null) {
                                if (schoolDMVResponse.getDistricts() != null && schoolDMVResponse.getDistricts().size() > 0) {
                                    schoolSyncRepository.insertSchoolDistricts(SchoolSyncActivity.this, schoolDMVResponse.getDistricts());
                                } else {
                                    Utils.customErrorAlert(SchoolSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_districts));
                                }
                            } else {
                                Utils.customErrorAlert(SchoolSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.server_not));
                            }
                        }
                    });
                } else {
                    Utils.customErrorAlert(SchoolSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
                }
            }
        });

        binding.syncInstitutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(SchoolSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<InstMasterResponse> instMasterResponseLiveData = viewModel.getInstMasterResponse();
                    instMasterResponseLiveData.observe(SchoolSyncActivity.this, new Observer<InstMasterResponse>() {
                        @Override
                        public void onChanged(InstMasterResponse instMasterResponse) {
                            instMasterResponseLiveData.removeObservers(SchoolSyncActivity.this);
                            SchoolSyncActivity.this.instMasterResponse = instMasterResponse;
                            if (instMasterResponse != null) {
                                if (instMasterResponse.getInstituteInfo() != null && instMasterResponse.getInstituteInfo().size() > 0) {
                                    schoolSyncRepository.insertMasterInstitutes(SchoolSyncActivity.this, instMasterResponse.getInstituteInfo());
                                } else {
                                    Utils.customErrorAlert(SchoolSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_insts));
                                }
                            } else {
                                Utils.customErrorAlert(SchoolSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.server_not));
                            }
                        }
                    });
                } else {
                    Utils.customErrorAlert(SchoolSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
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
        startActivity(new Intent(SchoolSyncActivity.this, DMVSelectionActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        Log.i("MSG", "handleError: " + errMsg);
        callSnackBar(errMsg);
    }

    @Override
    public void distCount(int cnt) {
        try {
            if (cnt > 0) {
                Log.i("D_CNT", "distCount: " + cnt);
                schoolSyncRepository.insertSchoolMandals(SchoolSyncActivity.this, schoolDMVResponse.getMandals());
            } else {
                Utils.customErrorAlert(SchoolSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_districts));
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
                schoolSyncRepository.insertSchoolVillages(SchoolSyncActivity.this, schoolDMVResponse.getVillages());
            } else {
                Utils.customErrorAlert(SchoolSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_mandals));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void vilCount(int cnt) {
        try {
            if (cnt > 0) {
                Log.i("V_CNT", "vilCount: " + cnt);
                customProgressDialog.hide();
                Utils.customSyncSuccessAlert(SchoolSyncActivity.this, getResources().getString(R.string.app_name),
                        getString(R.string.dist_mas_sync));
            } else {
                Utils.customErrorAlert(SchoolSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_villages));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void instCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                Log.i("I_CNT", "instCount: " + cnt);
                Utils.customSyncSuccessAlert(SchoolSyncActivity.this, getResources().getString(R.string.app_name),
                        getString(R.string.ins_mas_syn));
            } else {
                Utils.customErrorAlert(SchoolSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_insts));
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

    @Override
    public void clstCount(int cnt) {

    }
}
