package com.cgg.twdinspection.offline;

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
import com.cgg.twdinspection.databinding.ActivityGccOfflineDashboardBinding;
import com.cgg.twdinspection.gcc.reports.ui.GCCReportActivity;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.gcc.viewmodel.GCCOfflineViewModel;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class GCCOfflineDashboard extends AppCompatActivity {

    ActivityGccOfflineDashboardBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private List<GccOfflineEntity> drGodown;
    private List<GccOfflineEntity> drDepot;
    private List<GccOfflineEntity> mfpGodown;
    private List<GccOfflineEntity> processingUnit;
    private List<GccOfflineEntity> petrolpump;
    private List<GccOfflineEntity> lpg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gcc_offline_dashboard);
        binding.header.headerTitle.setText(getString(R.string.gcc_reports_offline));



        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GCCOfflineDashboard.this, DashboardMenuActivity.class)
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
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.btnDrDepot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drDepot != null && drDepot.size() > 0) {
                    startActivity(new Intent(GCCOfflineDashboard.this, GCCOfflineDataActivity.class)
                            .putExtra(AppConstants.FROM_CLASS, AppConstants.OFFLINE_DR_DEPOT));
                } else {
                    callSnackBar("No data found");
                }
            }
        });

        binding.btnDrGodown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drGodown != null && drGodown.size() > 0) {
                    startActivity(new Intent(GCCOfflineDashboard.this, GCCOfflineDataActivity.class)
                    .putExtra(AppConstants.FROM_CLASS, AppConstants.OFFLINE_DR_GODOWN));
                } else {
                    callSnackBar("No data found");
                }
            }
        });

        binding.btnMfpGodown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mfpGodown != null && mfpGodown.size() > 0) {
                    startActivity(new Intent(GCCOfflineDashboard.this, GCCOfflineDataActivity.class)
                            .putExtra(AppConstants.FROM_CLASS, AppConstants.OFFLINE_MFP));
                } else {
                    callSnackBar("No data found");
                }
            }
        });

        binding.btnPUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (processingUnit != null && processingUnit.size() > 0) {
                    startActivity(new Intent(GCCOfflineDashboard.this, GCCOfflineDataActivity.class)
                            .putExtra(AppConstants.FROM_CLASS, AppConstants.OFFLINE_P_UNIT));
                } else {
                    callSnackBar("No data found");
                }
            }
        });

        binding.btnPetrolPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (petrolpump != null && petrolpump.size() > 0) {
                    startActivity(new Intent(GCCOfflineDashboard.this, GCCOfflineDataActivity.class)
                            .putExtra(AppConstants.FROM_CLASS, AppConstants.OFFLINE_PETROL));
                } else {
                    callSnackBar("No data found");
                }
            }
        });

        binding.btnLpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lpg != null && lpg.size() > 0) {
                    startActivity(new Intent(GCCOfflineDashboard.this, GCCOfflineDataActivity.class)
                            .putExtra(AppConstants.FROM_CLASS, AppConstants.OFFLINE_LPG));
                } else {
                    callSnackBar("No data found");
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        GCCOfflineViewModel gccOfflineViewModel = new GCCOfflineViewModel(getApplication());

        LiveData<List<GccOfflineEntity>> drGodownLiveData = gccOfflineViewModel.getGoDownsOfflineCount(AppConstants.OFFLINE_DR_GODOWN);
        drGodownLiveData.observe(GCCOfflineDashboard.this, new Observer<List<GccOfflineEntity>>() {
            @Override
            public void onChanged(List<GccOfflineEntity> drGodown) {
                GCCOfflineDashboard.this.drGodown = drGodown;
                drGodownLiveData.removeObservers(GCCOfflineDashboard.this);
                if (drGodown != null && drGodown.size() > 0) {
                    binding.drGodownCnt.setText(String.valueOf(drGodown.size()));
                }else {
                    binding.drGodownCnt.setText(R.string.na);
                }
            }
        });


        LiveData<List<GccOfflineEntity>> drDepotLiveData = gccOfflineViewModel.getGoDownsOfflineCount(AppConstants.OFFLINE_DR_DEPOT);
        drDepotLiveData.observe(GCCOfflineDashboard.this, new Observer<List<GccOfflineEntity>>() {
            @Override
            public void onChanged(List<GccOfflineEntity> drDepot) {
                GCCOfflineDashboard.this.drDepot = drDepot;
                drDepotLiveData.removeObservers(GCCOfflineDashboard.this);
                if (drDepot != null && drDepot.size() > 0) {
                    binding.drDepotCnt.setText(String.valueOf(drDepot.size()));
                }else {
                    binding.drDepotCnt.setText(R.string.na);
                }
            }
        });


        LiveData<List<GccOfflineEntity>> mfpGodownLiveData = gccOfflineViewModel.getGoDownsOfflineCount(AppConstants.OFFLINE_MFP);
        mfpGodownLiveData.observe(GCCOfflineDashboard.this, new Observer<List<GccOfflineEntity>>() {
            @Override
            public void onChanged(List<GccOfflineEntity> mfpGodown) {
                GCCOfflineDashboard.this.mfpGodown = mfpGodown;
                mfpGodownLiveData.removeObservers(GCCOfflineDashboard.this);
                if (mfpGodown != null && mfpGodown.size() > 0) {
                    binding.mfpGodownCnt.setText(String.valueOf(mfpGodown.size()));
                }else {
                    binding.mfpGodownCnt.setText(R.string.na);
                }
            }
        });


        LiveData<List<GccOfflineEntity>> pUnitLiveData = gccOfflineViewModel.getGoDownsOfflineCount(AppConstants.OFFLINE_P_UNIT);
        pUnitLiveData.observe(GCCOfflineDashboard.this, new Observer<List<GccOfflineEntity>>() {
            @Override
            public void onChanged(List<GccOfflineEntity> processingUnit) {
                GCCOfflineDashboard.this.processingUnit = processingUnit;
                pUnitLiveData.removeObservers(GCCOfflineDashboard.this);
                if (processingUnit != null && processingUnit.size() > 0) {
                    binding.pUnitCnt.setText(String.valueOf(processingUnit.size()));
                }else {
                    binding.pUnitCnt.setText(R.string.na);
                }
            }
        });

        LiveData<List<GccOfflineEntity>> petrolPumpLiveData = gccOfflineViewModel.getGoDownsOfflineCount(AppConstants.OFFLINE_PETROL);
        petrolPumpLiveData.observe(GCCOfflineDashboard.this, new Observer<List<GccOfflineEntity>>() {
            @Override
            public void onChanged(List<GccOfflineEntity> petrolpump) {
                GCCOfflineDashboard.this.petrolpump = petrolpump;
                petrolPumpLiveData.removeObservers(GCCOfflineDashboard.this);
                if (petrolpump != null && petrolpump.size() > 0) {
                    binding.petrolPumpCnt.setText(String.valueOf(petrolpump.size()));
                }else {
                    binding.petrolPumpCnt.setText(R.string.na);
                }
            }
        });

        LiveData<List<GccOfflineEntity>> lpgLiveData = gccOfflineViewModel.getGoDownsOfflineCount(AppConstants.OFFLINE_LPG);
        lpgLiveData.observe(GCCOfflineDashboard.this, new Observer<List<GccOfflineEntity>>() {
            @Override
            public void onChanged(List<GccOfflineEntity> lpg) {
                GCCOfflineDashboard.this.lpg = lpg;
                lpgLiveData.removeObservers(GCCOfflineDashboard.this);
                if (lpg != null && lpg.size() > 0) {
                    binding.lpgCnt.setText(String.valueOf(lpg.size()));
                }else {
                    binding.lpgCnt.setText(R.string.na);
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

}
