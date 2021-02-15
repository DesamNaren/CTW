package com.cgg.twdinspection.gcc.ui.gcc;

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
import com.cgg.twdinspection.databinding.ActivityGccSyncBinding;
import com.cgg.twdinspection.gcc.interfaces.GCCDivisionInterface;
import com.cgg.twdinspection.gcc.room.repository.GCCSyncRepository;
import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.divisions.GetOfficesResponse;
import com.cgg.twdinspection.gcc.source.suppliers.depot.DRDepotMasterResponse;
import com.cgg.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.cgg.twdinspection.gcc.source.suppliers.dr_godown.DRGoDownMasterResponse;
import com.cgg.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.cgg.twdinspection.gcc.source.suppliers.lpg.LPGMasterResponse;
import com.cgg.twdinspection.gcc.source.suppliers.lpg.LPGSupplierInfo;
import com.cgg.twdinspection.gcc.source.suppliers.mfp.MFPGoDownMasterResponse;
import com.cgg.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolPumpMasterResponse;
import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolSupplierInfo;
import com.cgg.twdinspection.gcc.source.suppliers.punit.PUnitMasterResponse;
import com.cgg.twdinspection.gcc.source.suppliers.punit.PUnits;
import com.cgg.twdinspection.gcc.viewmodel.DivisionSelectionViewModel;
import com.cgg.twdinspection.gcc.viewmodel.GCCSyncViewModel;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class GCCSyncActivity extends AppCompatActivity implements GCCDivisionInterface, ErrorHandlerInterface {
    private GCCSyncRepository gccSyncRepository;
    ActivityGccSyncBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CustomProgressDialog customProgressDialog;
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
        binding.header.headerTitle.setText(getResources().getString(R.string.sync_gcc_activity));
        instMainViewModel = new InstMainViewModel(getApplication());
        DivisionSelectionViewModel divisionSelectionViewModel = new DivisionSelectionViewModel(getApplication());

        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GCCSyncActivity.this, DashboardMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

            }
        });
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        LiveData<List<LPGSupplierInfo>> lpgLiveData = divisionSelectionViewModel.getAllLPGSuppliers();
        lpgLiveData.observe(this, new Observer<List<LPGSupplierInfo>>() {
            @Override
            public void onChanged(List<LPGSupplierInfo> drGodowns) {
                lpgLiveData.removeObservers(GCCSyncActivity.this);
                customProgressDialog.dismiss();
                if (drGodowns == null || drGodowns.size() <= 0)
                    binding.btnLpg.setText(getString(R.string.download));
                else
                    binding.btnLpg.setText(getString(R.string.re_download));

            }
        });


        LiveData<List<PetrolSupplierInfo>> petrolLiveData = divisionSelectionViewModel.getAllPetrolPumps();
        petrolLiveData.observe(this, new Observer<List<PetrolSupplierInfo>>() {
            @Override
            public void onChanged(List<PetrolSupplierInfo> drGodowns) {
                petrolLiveData.removeObservers(GCCSyncActivity.this);
                customProgressDialog.dismiss();
                if (drGodowns == null || drGodowns.size() <= 0)
                    binding.btnPetrolPump.setText(getString(R.string.download));
                else
                    binding.btnPetrolPump.setText(getString(R.string.re_download));
            }
        });


        LiveData<List<PUnits>> punitLiveData = divisionSelectionViewModel.getAllPUnits();
        punitLiveData.observe(this, new Observer<List<PUnits>>() {
            @Override
            public void onChanged(List<PUnits> drGodowns) {
                punitLiveData.removeObservers(GCCSyncActivity.this);
                customProgressDialog.dismiss();
                if (drGodowns == null || drGodowns.size() <= 0)
                    binding.btnPUnit.setText(getString(R.string.download));
                else
                    binding.btnPUnit.setText(getString(R.string.re_download));

            }
        });
        LiveData<List<MFPGoDowns>> mfpLiveData = divisionSelectionViewModel.getAllMFPGoDowns();
        mfpLiveData.observe(this, new Observer<List<MFPGoDowns>>() {
            @Override
            public void onChanged(List<MFPGoDowns> drGodowns) {
                mfpLiveData.removeObservers(GCCSyncActivity.this);
                customProgressDialog.dismiss();
                if (drGodowns == null || drGodowns.size() <= 0)
                    binding.btnMfpGodown.setText(getString(R.string.download));
                else
                    binding.btnMfpGodown.setText(getString(R.string.re_download));
            }
        });

        LiveData<List<DRDepots>> drDepotLiveData = divisionSelectionViewModel.getAllDRDepots();
        drDepotLiveData.observe(this, new Observer<List<DRDepots>>() {
            @Override
            public void onChanged(List<DRDepots> drGodowns) {
                drDepotLiveData.removeObservers(GCCSyncActivity.this);
                customProgressDialog.dismiss();
                if (drGodowns == null || drGodowns.size() <= 0)
                    binding.btnDrDepot.setText(getString(R.string.download));
                else
                    binding.btnDrDepot.setText(getString(R.string.re_download));

            }
        });

        LiveData<List<DrGodowns>> drGodownLiveData = divisionSelectionViewModel.getAllDRGoDowns();
        drGodownLiveData.observe(this, new Observer<List<DrGodowns>>() {
            @Override
            public void onChanged(List<DrGodowns> drGodowns) {
                drGodownLiveData.removeObservers(GCCSyncActivity.this);
                customProgressDialog.dismiss();
                if (drGodowns == null || drGodowns.size() <= 0)
                    binding.btnDrGodown.setText(getString(R.string.download));
                else
                    binding.btnDrGodown.setText(getString(R.string.re_download));
            }
        });
        LiveData<List<DivisionsInfo>> divisionListLiveData = divisionSelectionViewModel.getAllDivisionsMaster();
        divisionListLiveData.observe(this, new Observer<List<DivisionsInfo>>() {
            @Override
            public void onChanged(List<DivisionsInfo> divisionsInfos) {
                divisionListLiveData.removeObservers(GCCSyncActivity.this);
                customProgressDialog.dismiss();
                if (divisionsInfos == null || divisionsInfos.size() <= 0)
                    binding.btnDivision.setText(getString(R.string.download));
                else
                    binding.btnDivision.setText(getString(R.string.re_download));
            }
        });


        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnDivision.setOnClickListener(new View.OnClickListener() {
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
                                        binding.btnDivision.setText(getString(R.string.re_download));
                                    } else {
                                        binding.btnDivision.setText(getString(R.string.download));
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
                    Utils.customErrorAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                }
            }
        });

        binding.btnDrGodown.setOnClickListener(new View.OnClickListener() {
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
                    Utils.customErrorAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                }
            }

        });

        binding.btnDrDepot.setOnClickListener(new View.OnClickListener() {
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
                    Utils.customErrorAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                }
            }

        });

        binding.btnMfpGodown.setOnClickListener(new View.OnClickListener() {
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
                    Utils.customErrorAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                }
            }

        });


        binding.btnPUnit.setOnClickListener(new View.OnClickListener() {
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
                    Utils.customErrorAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                }
            }

        });

        binding.btnPetrolPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(GCCSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<PetrolPumpMasterResponse> petrolPumpMasterResponseLiveData = viewModel.getPetrolPumpMasterResponse();

                    petrolPumpMasterResponseLiveData.observe(GCCSyncActivity.this, new Observer<PetrolPumpMasterResponse>() {
                        @Override
                        public void onChanged(PetrolPumpMasterResponse petrolPumpMasterResponse) {
                            customProgressDialog.hide();
                            petrolPumpMasterResponseLiveData.removeObservers(GCCSyncActivity.this);
                            if (petrolPumpMasterResponse != null && petrolPumpMasterResponse.getStatusCode() != null) {
                                if (petrolPumpMasterResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                    if (petrolPumpMasterResponse.getPetrolSupplierInfos() != null && petrolPumpMasterResponse.getPetrolSupplierInfos().size() > 0) {
                                        gccSyncRepository.insertPetrolPumps(GCCSyncActivity.this, petrolPumpMasterResponse.getPetrolSupplierInfos());
                                    } else {
                                        Snackbar.make(binding.root, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
                                    }
                                } else if (petrolPumpMasterResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                    Snackbar.make(binding.root, petrolPumpMasterResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                                } else {
                                    callSnackBar(getString(R.string.something));
                                }
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        }
                    });
                } else {
                    Utils.customErrorAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                }
            }

        });

        binding.btnLpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(GCCSyncActivity.this)) {
                    customProgressDialog.show();
                    LiveData<LPGMasterResponse> lpgMasterResponseLiveData = viewModel.getLPGMasterResponse();

                    lpgMasterResponseLiveData.observe(GCCSyncActivity.this, new Observer<LPGMasterResponse>() {
                        @Override
                        public void onChanged(LPGMasterResponse lpgMasterResponse) {
                            customProgressDialog.hide();
                            lpgMasterResponseLiveData.removeObservers(GCCSyncActivity.this);
                            if (lpgMasterResponse != null && lpgMasterResponse.getStatusCode() != null) {
                                if (lpgMasterResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                    if (lpgMasterResponse.getLpgSupplierInfos() != null && lpgMasterResponse.getLpgSupplierInfos().size() > 0) {
                                        gccSyncRepository.insertLpg(GCCSyncActivity.this, lpgMasterResponse.getLpgSupplierInfos());
                                    } else {
                                        Snackbar.make(binding.root, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
                                    }
                                } else if (lpgMasterResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                    Snackbar.make(binding.root, lpgMasterResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                                } else {
                                    callSnackBar(getString(R.string.something));
                                }
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        }
                    });
                } else {
                    Utils.customErrorAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "Please check internet");
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

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void divisionCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                binding.btnDivision.setText(getString(R.string.re_download));
                Log.i("DIV_CNT", "divCount: " + cnt);
                Utils.customSyncSuccessAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name),
                        "Division master data downloaded successfully");
            } else {
                Utils.customErrorAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "No divisions found");
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
                binding.btnDrDepot.setText(getString(R.string.re_download));
                Log.i("SUP_CNT", "supCount: " + cnt);
                Utils.customSyncSuccessAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name),
                        "DR Depot master data downloaded successfully");
            } else {
                Utils.customErrorAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "No No DR Depots found");
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
                binding.btnDrGodown.setText(getString(R.string.re_download));
                Log.i("SUP_CNT", "drGodCount: " + cnt);
                Utils.customSyncSuccessAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name),
                        "DR GoDown master data downloaded successfully");
            } else {
                Utils.customErrorAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "No DR GoDowns found");
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
                binding.btnMfpGodown.setText(getString(R.string.re_download));
                Log.i("SUP_CNT", "mfpGodCount: " + cnt);
                Utils.customSyncSuccessAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name),
                        "MFP GoDown master data downloaded successfully");
            } else {
                Utils.customErrorAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "No MFP GoDowns found");
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
                binding.btnPUnit.setText(getString(R.string.re_download));
                Log.i("SUP_CNT", "pUnitCount: " + cnt);
                Utils.customSyncSuccessAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name),
                        "Processing Unit master data downloaded successfully");
            } else {
                Utils.customErrorAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "No Processing Units found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void petrolPumpCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                binding.btnPetrolPump.setText(getString(R.string.re_download));
                Log.i("PETROL_CNT", "petrolCount: " + cnt);
                Utils.customSyncSuccessAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name),
                        "Petrol pump master data downloaded successfully");
            } else {
                Utils.customErrorAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "No Petrol pumps found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lpgCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                binding.btnLpg.setText(getString(R.string.re_download));
                Log.i("LPG_CNT", "lpglCount: " + cnt);
                Utils.customSyncSuccessAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name),
                        "LPG master data downloaded successfully");
            } else {
                Utils.customErrorAlert(GCCSyncActivity.this, getResources().getString(R.string.app_name), "No LPG data found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
