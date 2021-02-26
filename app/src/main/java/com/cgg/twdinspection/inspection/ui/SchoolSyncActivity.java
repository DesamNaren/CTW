package com.cgg.twdinspection.inspection.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
import com.cgg.twdinspection.databinding.ActivitySchoolSyncBinding;
import com.cgg.twdinspection.inspection.interfaces.SchoolDMVInterface;
import com.cgg.twdinspection.inspection.interfaces.SchoolDietInterface;
import com.cgg.twdinspection.inspection.interfaces.SchoolInstInterface;
import com.cgg.twdinspection.inspection.room.repository.SchoolSyncRepository;
import com.cgg.twdinspection.inspection.source.diet_issues.DietMasterResponse;
import com.cgg.twdinspection.inspection.source.dmv.SchoolDMVResponse;
import com.cgg.twdinspection.inspection.source.dmv.SchoolDistrict;
import com.cgg.twdinspection.inspection.source.inst_master.InstMasterResponse;
import com.cgg.twdinspection.inspection.source.diet_issues.MasterDietListInfo;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.viewmodel.DMVDetailsViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.SchoolSyncViewModel;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SchoolSyncActivity extends AppCompatActivity implements SchoolDMVInterface, SchoolInstInterface, SchoolDietInterface, ErrorHandlerInterface {
    private SchoolSyncRepository schoolSyncRepository;
    private SchoolDMVResponse schoolDMVResponse;
    private InstMasterResponse instMasterResponse;
    private DietMasterResponse dietMasterResponse;
    ActivitySchoolSyncBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String officerId;
    CustomProgressDialog customProgressDialog;
    InstMainViewModel instMainViewModel;
    DMVDetailsViewModel dmvViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customProgressDialog = new CustomProgressDialog(this);

        binding = DataBindingUtil.setContentView(SchoolSyncActivity.this, R.layout.activity_school_sync);
        dmvViewModel = new DMVDetailsViewModel(getApplication());
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

        dmvViewModel.getAllDistricts().observe(this, new Observer<List<SchoolDistrict>>() {
            @Override
            public void onChanged(List<SchoolDistrict> schoolDistricts) {

                if (schoolDistricts != null && schoolDistricts.size() > 0) {
                    binding.btnDmv.setText(getString(R.string.re_download));
                } else {
                    binding.btnDmv.setText(getString(R.string.download));
                }
            }
        });
        dmvViewModel.getAllInstitutes().observe(this, new Observer<List<MasterInstituteInfo>>() {
            @Override
            public void onChanged(List<MasterInstituteInfo> masterInstituteInfos) {

                if (masterInstituteInfos != null && masterInstituteInfos.size() > 0) {
                    binding.btnInst.setText(getString(R.string.re_download));
                } else {
                    binding.btnInst.setText(getString(R.string.download));
                }
            }
        });
        dmvViewModel.getAllDietList().observe(this, new Observer<List<MasterDietListInfo>>() {
            @Override
            public void onChanged(List<MasterDietListInfo> masterDietListInfos) {

                if (masterDietListInfos != null && masterDietListInfos.size() > 0) {
                    binding.btnDiet.setText(getString(R.string.re_download));
                } else {
                    binding.btnDiet.setText(getString(R.string.download));
                }
            }
        });


        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchoolSyncActivity.this, DashboardMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

            }
        });

        binding.btnDmv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(SchoolSyncActivity.this)) {
                    customProgressDialog.show();

                    LiveData<SchoolDMVResponse> schoolDMVResponseLiveData = viewModel.getSchoolDMVReposnse(officerId);
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

        binding.btnInst.setOnClickListener(new View.OnClickListener() {
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

        binding.btnDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(SchoolSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<DietMasterResponse> instMasterResponseLiveData = viewModel.getDietMasterResponse();
                    instMasterResponseLiveData.observe(SchoolSyncActivity.this, new Observer<DietMasterResponse>() {
                        @Override
                        public void onChanged(DietMasterResponse dietMasterResponse) {
                            instMasterResponseLiveData.removeObservers(SchoolSyncActivity.this);
                            SchoolSyncActivity.this.dietMasterResponse = dietMasterResponse;
                            if (dietMasterResponse != null) {
                                if (dietMasterResponse.getInstituteInfo() != null && dietMasterResponse.getInstituteInfo().size() > 0) {
                                    schoolSyncRepository.insertMasterDietList(SchoolSyncActivity.this, dietMasterResponse.getInstituteInfo());
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
        callSnackBar(errMsg);
    }

    @Override
    public void distCount(int cnt) {
        try {
            if (cnt > 0) {
                schoolSyncRepository.insertSchoolMandals(SchoolSyncActivity.this, schoolDMVResponse.getMandals());
            } else {
                customProgressDialog.hide();
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
                schoolSyncRepository.insertSchoolVillages(SchoolSyncActivity.this, schoolDMVResponse.getVillages());
            } else {
                customProgressDialog.hide();
                Utils.customErrorAlert(SchoolSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_mandals));
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
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clstCount(int cnt) {

    }

    @Override
    public void dietCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                Utils.customSyncSuccessAlert(SchoolSyncActivity.this, getResources().getString(R.string.app_name),
                        getString(R.string.diet_mas_syn));
            } else {
                Utils.customErrorAlert(SchoolSyncActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_insts));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
