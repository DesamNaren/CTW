package com.example.twdinspection.schemes.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.twdinspection.R;
import com.example.twdinspection.common.ErrorHandler;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivitySchemeSyncBinding;
import com.example.twdinspection.inspection.ui.BaseActivity;
import com.example.twdinspection.inspection.ui.DashboardActivity;
import com.example.twdinspection.inspection.ui.LoginActivity;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.example.twdinspection.schemes.interfaces.SchemeDMVInterface;
import com.example.twdinspection.schemes.room.repository.SchemeSyncRepository;
import com.example.twdinspection.schemes.source.DMV.SchemeDMVResponse;
import com.example.twdinspection.schemes.source.finyear.FinancialYearResponse;
import com.example.twdinspection.schemes.source.remarks.InspectionRemarkResponse;
import com.example.twdinspection.schemes.source.schemes.SchemeEntity;
import com.example.twdinspection.schemes.source.schemes.SchemeResponse;
import com.example.twdinspection.schemes.viewmodel.SchemeSyncViewModel;
import com.google.android.material.snackbar.Snackbar;

public class SchemeSyncActivity extends AppCompatActivity implements SchemeDMVInterface, ErrorHandlerInterface {
    private SchemeSyncRepository schemeSyncRepository;
    private SchemeDMVResponse schemeDMVResponse;
    private FinancialYearResponse financialYearResponse;
    private InspectionRemarkResponse inspectionRemarkResponse;
    private SchemeResponse schemeResponse;
    ActivitySchemeSyncBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(SchemeSyncActivity.this,R.layout.activity_scheme_sync);

        SchemeSyncViewModel viewModel = new SchemeSyncViewModel(SchemeSyncActivity.this,getApplication(),binding);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        schemeSyncRepository = new SchemeSyncRepository(getApplication());
        binding.header.headerTitle.setText(getResources().getString(R.string.sync_activity));
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.syncDMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               viewModel.getSchemeDMVReposnse().observe(SchemeSyncActivity.this, new Observer<SchemeDMVResponse>() {
                   @Override
                   public void onChanged(SchemeDMVResponse schemeDMVResponse) {
                       SchemeSyncActivity.this.schemeDMVResponse=schemeDMVResponse;
                       if (schemeDMVResponse.getDistricts() != null && schemeDMVResponse.getDistricts().size() > 0) {
                           schemeSyncRepository.insertSchemeDistricts(SchemeSyncActivity.this, schemeDMVResponse.getDistricts());
                       }
                   }
               });
            }
        });

        binding.syncYears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               viewModel.getFinYearResponse().observe(SchemeSyncActivity.this, new Observer<FinancialYearResponse>() {
                   @Override
                   public void onChanged(FinancialYearResponse financialYearResponse) {
                       if (financialYearResponse != null && financialYearResponse.getStatusCode() != null) {
                           if (Integer.valueOf(financialYearResponse.getStatusCode()) == AppConstants.SUCCESS_CODE) {
                               SchemeSyncActivity.this.financialYearResponse=financialYearResponse;
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
            }
        });

        binding.syncInsRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getInspectionRemarks().observe(SchemeSyncActivity.this, new Observer<InspectionRemarkResponse>() {
                    @Override
                    public void onChanged(InspectionRemarkResponse inspectionRemarkResponse) {

                        if (inspectionRemarkResponse != null && inspectionRemarkResponse.getStatusCode() != null) {
                            if (Integer.valueOf(inspectionRemarkResponse.getStatusCode()) == AppConstants.SUCCESS_CODE) {
                                SchemeSyncActivity.this.inspectionRemarkResponse=inspectionRemarkResponse;
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
            }
        });

        binding.syncSchemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getSchemeResponse().observe(SchemeSyncActivity.this, new Observer<SchemeResponse>() {
                    @Override
                    public void onChanged(SchemeResponse schemeResponse) {
                        if (schemeResponse != null && schemeResponse.getStatusCode() != null) {
                            if (Integer.valueOf(schemeResponse.getStatusCode()) == AppConstants.SUCCESS_CODE) {

                                SchemeSyncActivity.this.schemeResponse=schemeResponse;
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
            }
        });
    }

    void callSnackBar(String msg){
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
        startActivity(new Intent(SchemeSyncActivity.this,SchemesDMVActivity.class));
    }

    @Override
    public void handleError(Throwable e, Context context) {
        String errMsg = ErrorHandler.handleError(e, context);
        callSnackBar(errMsg);
    }

    @Override
    public void distCount(int cnt) {
        try {
            if (cnt > 0) {
                Log.i("D_CNT", "distCount: "+cnt);
                schemeSyncRepository.insertSchemeMandals(SchemeSyncActivity.this, schemeDMVResponse.getMandals());
            } else {
               // onDataNotAvailable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void manCount(int cnt) {
        try {
            if (cnt > 0) {
                Log.i("M_CNT", "manCount: "+cnt);
                schemeSyncRepository.insertSchemeVillages(SchemeSyncActivity.this, schemeDMVResponse.getVillages());
            } else {
                // onDataNotAvailable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void vilCount(int cnt) {
        try {
            if (cnt > 0) {
                Log.i("V_CNT", "vilCount: "+cnt);
                binding.progress.setVisibility(View.GONE);
                Utils.customSyncSuccessAlert(SchemeSyncActivity.this,getResources().getString(R.string.app_name),
                        "Districts synced successfully");
                // Success Alert;
            } else {
                // onDataNotAvailable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finYear(int cnt) {
        try {
            if (cnt > 0) {
                Log.i("F_CNT", "finCount: "+cnt);
                binding.progress.setVisibility(View.GONE);
                Utils.customSyncSuccessAlert(SchemeSyncActivity.this,getResources().getString(R.string.app_name),
                        "Financial years synced successfully");
                // Success Alert;
            } else {
                // onDataNotAvailable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insRemCount(int cnt) {
        try {
            if (cnt > 0) {
                Log.i("IN_CNT", "insCount: "+cnt);
                binding.progress.setVisibility(View.GONE);
                Utils.customSyncSuccessAlert(SchemeSyncActivity.this,getResources().getString(R.string.app_name),
                        "Inspection remarks synced successfully");
                // Success Alert;
            } else {
                // onDataNotAvailable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void schemeCount(int cnt) {
        try {
            if (cnt > 0) {
                Log.i("SC_CNT", "schCount: "+cnt);
                binding.progress.setVisibility(View.GONE);
                Utils.customSyncSuccessAlert(SchemeSyncActivity.this,getResources().getString(R.string.app_name),
                        "Schemes synced successfully");
                // Success Alert;
            } else {
                // onDataNotAvailable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
