package com.cgg.twdinspection.gcc.ui.drdepot;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityDrDepotSelBinding;
import com.cgg.twdinspection.gcc.interfaces.GCCOfflineInterface;
import com.cgg.twdinspection.gcc.room.repository.GCCOfflineRepository;
import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.gcc.source.stock.CommonCommodity;
import com.cgg.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.cgg.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.cgg.twdinspection.gcc.viewmodel.DivisionSelectionViewModel;
import com.cgg.twdinspection.gcc.viewmodel.GCCOfflineViewModel;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
import com.cgg.twdinspection.inspection.viewmodel.StockViewModel;
import com.cgg.twdinspection.offline.GCCOfflineDataActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DRDepotSelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, GCCOfflineInterface {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    CustomProgressDialog customProgressDialog;
    ActivityDrDepotSelBinding binding;
    private Context context;
    private DivisionSelectionViewModel viewModel;
    private String selectedDivId, selectedSocietyId, selectedDepotID;
    private List<DivisionsInfo> divisionsInfos;
    private List<String> societies;
    private List<String> drDepots;
    private DRDepots selectedDRDepots;
    private ArrayAdapter<String> selectAdapter;
    private boolean dailyreq_flag, emp_flag, ess_flag;
    private GCCOfflineRepository gccOfflineRepository;
    private GCCOfflineViewModel gccOfflineViewModel;
    private ArrayList<String> selectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_depot_sel);
        gccOfflineRepository = new GCCOfflineRepository(getApplication());
        gccOfflineViewModel = new GCCOfflineViewModel(getApplication());
        context = DRDepotSelActivity.this;
        divisionsInfos = new ArrayList<>();
        societies = new ArrayList<>();
        drDepots = new ArrayList<>();
        customProgressDialog = new CustomProgressDialog(context);
        binding.header.headerTitle.setText(getResources().getString(R.string.gcc_dr_depot));
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DRDepotSelActivity.this, DashboardMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

            }
        });
        viewModel = new DivisionSelectionViewModel(getApplication());
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            binding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            binding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            String curTime = Utils.getCurrentDateTimeDisplay();
            editor.putString(AppConstants.INSP_TIME, curTime);
            editor.commit();
            binding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        selectList = new ArrayList<String>();
        selectList.add("Select");
        selectAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, selectList);

        LiveData<List<String>> divisionLiveData = viewModel.getAllDivisions();
        divisionLiveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> divisions) {
                divisionLiveData.removeObservers(DRDepotSelActivity.this);
                customProgressDialog.dismiss();
                if (divisions != null && divisions.size() > 0) {
                    ArrayList<String> divisionNames = new ArrayList<>();
                    divisionNames.add("-Select-");
                    divisionNames.addAll(divisions);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                            android.R.layout.simple_spinner_dropdown_item, divisionNames
                    );
                    binding.spDivision.setAdapter(adapter);

                    LiveData<List<DRDepots>> drGodownLiveData = viewModel.getAllDRDepots();
                    drGodownLiveData.observe(DRDepotSelActivity.this, new Observer<List<DRDepots>>() {
                        @Override
                        public void onChanged(List<DRDepots> drGodowns) {
                            drGodownLiveData.removeObservers(DRDepotSelActivity.this);
                            customProgressDialog.dismiss();
                            if (drGodowns == null || drGodowns.size() <= 0) {
                                Utils.customGCCSyncAlert(DRDepotSelActivity.this, getString(R.string.app_name), "No DR Depots found...\n Do you want to sync DR Depot master data to proceed further");
                            }
                        }
                    });
                } else {
                    Utils.customGCCSyncAlert(DRDepotSelActivity.this, getString(R.string.app_name), "No divisions found...\n Do you want to sync division master data to proceed further?");
                }
            }
        });


        binding.spDivision.setOnItemSelectedListener(this);
        binding.spSociety.setOnItemSelectedListener(this);
        binding.spDepot.setOnItemSelectedListener(this);
        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {

                    LiveData<GccOfflineEntity> drDepotLiveData = gccOfflineViewModel.getDRGoDownsOffline(selectedDivId, selectedSocietyId, selectedDepotID);
                    drDepotLiveData.observe(DRDepotSelActivity.this, new Observer<GccOfflineEntity>() {
                        @Override
                        public void onChanged(GccOfflineEntity drDepots) {
                            drDepotLiveData.removeObservers(DRDepotSelActivity.this);

                            if (drDepots != null) {

                                Type listType = new TypeToken<ArrayList<CommonCommodity>>() {
                                }.getType();
                                List<CommonCommodity> essestinalCommodities = new Gson().fromJson(drDepots.getEssentials(), listType);
                                List<CommonCommodity> dailyReqCommodities = new Gson().fromJson(drDepots.getDailyReq(), listType);
                                List<CommonCommodity> emptiesCommodities = new Gson().fromJson(drDepots.getEmpties(), listType);


                                if (essestinalCommodities != null && essestinalCommodities.size() > 0) {
                                    ess_flag = true;
                                }
                                if (dailyReqCommodities != null && dailyReqCommodities.size() > 0) {
                                    dailyreq_flag = true;
                                }
                                if (emptiesCommodities != null && emptiesCommodities.size() > 0) {
                                    emp_flag = true;
                                }


                                if (ess_flag || dailyreq_flag || emp_flag) {
                                    Gson gson = new Gson();
                                    String depotData = gson.toJson(selectedDRDepots);
                                    editor.putString(AppConstants.DR_DEPOT_DATA, depotData);

                                    StockDetailsResponse stockDetailsResponse = new StockDetailsResponse();
                                    stockDetailsResponse.setEssential_commodities(essestinalCommodities);
                                    stockDetailsResponse.setDialy_requirements(dailyReqCommodities);
                                    stockDetailsResponse.setEmpties(emptiesCommodities);

                                    editor.putString(AppConstants.StockDetailsResponse, new Gson().toJson(stockDetailsResponse));
                                    editor.commit();

                                    startActivity(new Intent(DRDepotSelActivity.this, DRDepotActivity.class));
                                } else {
                                    callSnackBar(getString(R.string.no_comm));
                                }


                            } else {
                                customOnlineAlert();
                            }
                        }
                    });

                }
            }
        });

        binding.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DRDepotSelActivity.this, GCCOfflineDataActivity.class)
                        .putExtra(AppConstants.FROM_CLASS, AppConstants.OFFLINE_DR_DEPOT));
                finish();
            }
        });

        binding.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callService(false);
            }
        });

        binding.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GccOfflineEntity entity = new GccOfflineEntity();
                entity.setDivisionId(selectedDivId);
                entity.setSocietyId(selectedSocietyId);
                entity.setDrgownId(selectedDepotID);

                gccOfflineRepository.deleteGCCRecord(DRDepotSelActivity.this, entity);
            }
        });
    }

    void callService(boolean flag) {
        if (validateFields()) {

            StockViewModel viewModel = new StockViewModel(getApplication(), DRDepotSelActivity.this);
            if (Utils.checkInternetConnection(DRDepotSelActivity.this)) {
                customProgressDialog.show();
                LiveData<StockDetailsResponse> officesResponseLiveData = viewModel.getStockData(selectedDepotID);
                officesResponseLiveData.observe(DRDepotSelActivity.this, new Observer<StockDetailsResponse>() {
                    @Override
                    public void onChanged(StockDetailsResponse stockDetailsResponse) {
                        customProgressDialog.hide();
                        officesResponseLiveData.removeObservers(DRDepotSelActivity.this);
                        Gson gson = new Gson();
                        if (stockDetailsResponse != null && stockDetailsResponse.getStatusCode() != null) {
                            if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {

                                if (stockDetailsResponse.getEssential_commodities() != null && stockDetailsResponse.getEssential_commodities().size() > 0) {
                                    ess_flag = true;
                                }

                                if (stockDetailsResponse.getDialy_requirements() != null && stockDetailsResponse.getDialy_requirements().size() > 0) {
                                    dailyreq_flag = true;
                                }

                                if (stockDetailsResponse.getEmpties() != null && stockDetailsResponse.getEmpties().size() > 0) {
                                    emp_flag = true;
                                }

                                if (ess_flag || dailyreq_flag || emp_flag) {
                                    if (flag) {
                                        String depotData = gson.toJson(selectedDRDepots);
                                        editor.putString(AppConstants.DR_DEPOT_DATA, depotData);
                                        editor.putString(AppConstants.StockDetailsResponse, gson.toJson(stockDetailsResponse));
                                        editor.commit();

                                        startActivity(new Intent(context, DRDepotActivity.class));
                                    } else {
                                        GccOfflineEntity gccOfflineEntity = new GccOfflineEntity();
                                        gccOfflineEntity.setDivisionId(selectedDivId);
                                        gccOfflineEntity.setDivisionName(binding.spDivision.getSelectedItem().toString());
                                        gccOfflineEntity.setSocietyId(selectedSocietyId);
                                        gccOfflineEntity.setSocietyName(binding.spSociety.getSelectedItem().toString());
                                        gccOfflineEntity.setDrgownId(selectedDepotID);
                                        gccOfflineEntity.setDrgownName(binding.spDepot.getSelectedItem().toString());
                                        gccOfflineEntity.setEssentials(gson.toJson(stockDetailsResponse.getEssential_commodities()));
                                        gccOfflineEntity.setDailyReq(gson.toJson(stockDetailsResponse.getDialy_requirements()));
                                        gccOfflineEntity.setEmpties(gson.toJson(stockDetailsResponse.getEmpties()));
                                        gccOfflineEntity.setType(AppConstants.OFFLINE_DR_DEPOT);

                                        gccOfflineRepository.insertGCCRecord(DRDepotSelActivity.this, gccOfflineEntity);
                                    }
                                } else {
                                    callSnackBar(getString(R.string.no_comm));
                                }


                            } else if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                callSnackBar(getString(R.string.no_comm));
                            } else {
                                callSnackBar(getString(R.string.something));
                            }

                        } else {
                            callSnackBar(getString(R.string.something));
                        }

                    }
                });
            } else {
                Utils.customWarningAlert(DRDepotSelActivity.this, getResources().getString(R.string.app_name), "Please check internet");
            }
        }
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    private boolean validateFields() {
        if (TextUtils.isEmpty(selectedDivId)) {
            showSnackBar("Please select division");
            return false;
        } else if (TextUtils.isEmpty(selectedSocietyId)) {
            showSnackBar("Please select society");
            return false;
        } else if (TextUtils.isEmpty(selectedDepotID)) {
            showSnackBar("Please select depot");
            return false;
        }
        return true;
    }


    private void showSnackBar(String str) {
        Snackbar snackbar = Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView.getId() == R.id.sp_division) {
            binding.llDownload.setVisibility(View.GONE);
            binding.llView.setVisibility(View.GONE);
            selectedDRDepots = null;
            selectedSocietyId = "";
            selectedDivId = "";
            selectedDepotID = "";
            binding.spSociety.setAdapter(selectAdapter);
            divisionsInfos = new ArrayList<>();
            societies = new ArrayList<>();
            societies.add("--Select--");
            if (position != 0) {
                LiveData<String> liveData = viewModel.getDivisionId(binding.spDivision.getSelectedItem().toString());
                liveData.observe(DRDepotSelActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        liveData.removeObservers(DRDepotSelActivity.this);
                        if (str != null) {
                            selectedDivId = str;
                            LiveData<List<DivisionsInfo>> listLiveData = viewModel.getSocieties(selectedDivId);
                            listLiveData.observe(DRDepotSelActivity.this, new Observer<List<DivisionsInfo>>() {
                                @Override
                                public void onChanged(List<DivisionsInfo> divisionsInfoList) {
                                    listLiveData.removeObservers(DRDepotSelActivity.this);
                                    divisionsInfos.addAll(divisionsInfoList);
                                    if (divisionsInfos != null && divisionsInfos.size() > 0) {
                                        for (int i = 0; i < divisionsInfos.size(); i++) {
                                            if (!TextUtils.isEmpty(divisionsInfos.get(i).getSocietyName()))
                                                societies.add(divisionsInfos.get(i).getSocietyName());
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_spinner_dropdown_item, societies);
                                        binding.spSociety.setAdapter(adapter);
                                    } else {
                                        binding.spSociety.setAdapter(selectAdapter);
                                        binding.spDepot.setAdapter(selectAdapter);
                                        showSnackBar("No societies found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedDRDepots = null;
                selectedDivId = "";
                selectedSocietyId = "";
                binding.spSociety.setAdapter(selectAdapter);
                selectedDepotID = "";
                binding.spDepot.setAdapter(selectAdapter);
            }
        } else if (adapterView.getId() == R.id.sp_society) {
            if (position != 0) {
                binding.llDownload.setVisibility(View.GONE);
                binding.llView.setVisibility(View.GONE);
                selectedDRDepots = null;
                selectedSocietyId = "";
                selectedDepotID = "";
                binding.spDepot.setAdapter(selectAdapter);
                drDepots = new ArrayList<>();
                LiveData<String> liveData = viewModel.getSocietyId(selectedDivId, binding.spSociety.getSelectedItem().toString());
                liveData.observe(DRDepotSelActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        liveData.removeObservers(DRDepotSelActivity.this);
                        if (str != null) {
                            selectedSocietyId = str;
                            LiveData<List<DRDepots>> listLiveData = viewModel.getDRDepots(selectedDivId, selectedSocietyId);
                            listLiveData.observe(DRDepotSelActivity.this, new Observer<List<DRDepots>>() {
                                @Override
                                public void onChanged(List<DRDepots> depotsList) {
                                    listLiveData.removeObservers(DRDepotSelActivity.this);
                                    if (depotsList != null && depotsList.size() > 0) {
                                        drDepots.add("-Select-");
                                        for (int i = 0; i < depotsList.size(); i++) {
                                            drDepots.add(depotsList.get(i).getGodownName());
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_spinner_dropdown_item, drDepots);
                                        binding.spDepot.setAdapter(adapter);
                                    } else {
                                        binding.spDepot.setAdapter(selectAdapter);
                                        showSnackBar("No DR Depots found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedDRDepots = null;
                selectedSocietyId = "";
                selectedDepotID = "";
                binding.spDepot.setAdapter(selectAdapter);
            }
        } else if (adapterView.getId() == R.id.sp_depot) {
            if (position != 0) {
                binding.llDownload.setVisibility(View.VISIBLE);
                binding.llView.setVisibility(View.GONE);
                selectedDRDepots = null;
                selectedDepotID = "";
                LiveData<DRDepots> liveData = viewModel.getDRDepotID(selectedDivId, selectedSocietyId, binding.spDepot.getSelectedItem().toString());
                liveData.observe(DRDepotSelActivity.this, new Observer<DRDepots>() {
                    @Override
                    public void onChanged(DRDepots drDepots) {
                        liveData.removeObservers(DRDepotSelActivity.this);

                        if (drDepots != null) {
                            selectedDepotID = drDepots.getGodownId();
                            selectedDRDepots = drDepots;

                            LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(selectedDivId, selectedSocietyId, selectedDepotID);
                            drGodownLiveData.observe(DRDepotSelActivity.this, new Observer<GccOfflineEntity>() {
                                @Override
                                public void onChanged(GccOfflineEntity drGodowns) {
                                    drGodownLiveData.removeObservers(DRDepotSelActivity.this);

                                    if (drGodowns == null) {
                                        binding.btnDownload.setText(getString(R.string.download));
                                        binding.btnRemove.setVisibility(View.GONE);
                                        binding.btnProceed.setVisibility(View.VISIBLE);
                                    } else {
                                        if (drGodowns.isFlag()) {
                                            binding.llView.setVisibility(View.VISIBLE);
                                            binding.llDownload.setVisibility(View.GONE);
                                            binding.btnProceed.setVisibility(View.GONE);
                                        } else {
                                            binding.llDownload.setVisibility(View.VISIBLE);
                                            binding.llView.setVisibility(View.GONE);
                                            binding.btnDownload.setText(R.string.re_download);
                                            binding.btnRemove.setVisibility(View.VISIBLE);
                                            binding.btnProceed.setVisibility(View.VISIBLE);
                                        }

                                    }
                                }
                            });

                        } else {
                            showSnackBar(getString(R.string.something));
                        }
                    }
                });
            } else {
                selectedDRDepots = null;
                selectedDepotID = "";
                binding.llDownload.setVisibility(View.GONE);
                binding.llView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void gccRecCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                binding.btnDownload.setText(getString(R.string.re_download));
                binding.btnRemove.setVisibility(View.VISIBLE);
                Utils.customSyncSuccessAlert(DRDepotSelActivity.this, getResources().getString(R.string.app_name),
                        "Data downloaded successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletedrGoDownCount(int cnt) {

        try {
            if (cnt > 0) {
                binding.btnDownload.setText(getString(R.string.download));
                binding.btnRemove.setVisibility(View.GONE);
                Utils.customSyncSuccessAlert(DRDepotSelActivity.this, getResources().getString(R.string.app_name),
                        getString(R.string.data_saved_successfully));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletedrGoDownCountSubmitted(int cnt, String msg) {

    }


    private void customOnlineAlert() {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_confirmation);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(getString(R.string.app_name));
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(R.string.do_you_want_online_mode);
                Button btDialogNo = dialog.findViewById(R.id.btDialogNo);
                btDialogNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        callService(true);
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


}
