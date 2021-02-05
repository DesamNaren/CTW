package com.cgg.twdinspection.gcc.ui.drgodown;

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
import com.cgg.twdinspection.databinding.ActivityDrGodownSelBinding;
import com.cgg.twdinspection.gcc.interfaces.GCCOfflineInterface;
import com.cgg.twdinspection.gcc.room.repository.GCCOfflineRepository;
import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.gcc.source.stock.CommonCommodity;
import com.cgg.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.cgg.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.cgg.twdinspection.gcc.viewmodel.DivisionSelectionViewModel;
import com.cgg.twdinspection.gcc.viewmodel.GCCOfflineViewModel;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
import com.cgg.twdinspection.inspection.viewmodel.StockViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DRGODownSelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, GCCOfflineInterface {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    CustomProgressDialog customProgressDialog;
    ActivityDrGodownSelBinding binding;
    private Context context;
    private DivisionSelectionViewModel viewModel;
    private GCCOfflineViewModel gccOfflineViewModel;
    private String selectedDivId, selectedSocietyId, selectedGoDownId;
    private List<DivisionsInfo> divisionsInfos;
    private List<String> societies;
    private List<String> drGodowns;
    private DrGodowns selectedDrGodowns;
    private GCCOfflineRepository gccOfflineRepository;
    ArrayAdapter<String> selectAdapter;
    private boolean dailyreq_flag, emp_flag, ess_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_godown_sel);
        context = DRGODownSelActivity.this;
        divisionsInfos = new ArrayList<>();
        societies = new ArrayList<>();
        drGodowns = new ArrayList<>();
        customProgressDialog = new CustomProgressDialog(context);
        binding.header.headerTitle.setText(getResources().getString(R.string.gcc_dr_godown));
        gccOfflineRepository = new GCCOfflineRepository(getApplication());
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DRGODownSelActivity.this, DashboardMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

            }
        });
        viewModel = new DivisionSelectionViewModel(getApplication());
        gccOfflineViewModel = new GCCOfflineViewModel(getApplication());
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

        ArrayList selectList = new ArrayList();
        selectList.add("Select");
        selectAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, selectList);

        LiveData<List<String>> divisionLiveData = viewModel.getAllDivisions();
        divisionLiveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> divisions) {
                divisionLiveData.removeObservers(DRGODownSelActivity.this);
                customProgressDialog.dismiss();
                if (divisions != null && divisions.size() > 0) {
                    ArrayList<String> divisionNames = new ArrayList<>();
                    divisionNames.add("-Select-");
                    divisionNames.addAll(divisions);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                            android.R.layout.simple_spinner_dropdown_item, divisionNames
                    );
                    binding.spDivision.setAdapter(adapter);
                    LiveData<List<DrGodowns>> drGodownLiveData = viewModel.getAllDRGoDowns();
                    drGodownLiveData.observe(DRGODownSelActivity.this, new Observer<List<DrGodowns>>() {
                        @Override
                        public void onChanged(List<DrGodowns> drGodowns) {
                            drGodownLiveData.removeObservers(DRGODownSelActivity.this);
                            customProgressDialog.dismiss();
                            if (drGodowns == null || drGodowns.size() <= 0) {
                                Utils.customGCCSyncAlert(DRGODownSelActivity.this, getString(R.string.app_name), "No DR Godowns found...\n Do you want to sync DR Godown master data to proceed further?");
                            }
                        }
                    });
                } else {
                    Utils.customGCCSyncAlert(DRGODownSelActivity.this, getString(R.string.app_name), "No divisions found...\n Do you want to sync division master data to proceed further?");
                }
            }
        });


        binding.spDivision.setAdapter(selectAdapter);
        binding.spDivision.setAdapter(selectAdapter);
        binding.spDivision.setAdapter(selectAdapter);
        binding.spDivision.setOnItemSelectedListener(this);
        binding.spSociety.setOnItemSelectedListener(this);
        binding.spGodown.setOnItemSelectedListener(this);
        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {

                    editor.putString(AppConstants.StockDetailsResponse, "");
                    editor.commit();

                    LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(selectedDivId, selectedSocietyId, selectedGoDownId);
                    drGodownLiveData.observe(DRGODownSelActivity.this, new Observer<GccOfflineEntity>() {
                        @Override
                        public void onChanged(GccOfflineEntity drGodowns) {
                            drGodownLiveData.removeObservers(DRGODownSelActivity.this);

                            if (drGodowns != null) {

                                Type listType = new TypeToken<ArrayList<CommonCommodity>>() {
                                }.getType();
                                List<CommonCommodity> essestinalCommodities = new Gson().fromJson(drGodowns.getEssentials(), listType);
                                List<CommonCommodity> dailyReqCommodities = new Gson().fromJson(drGodowns.getDailyReq(), listType);
                                List<CommonCommodity> emptiesCommodities = new Gson().fromJson(drGodowns.getEmpties(), listType);


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
                                    String goDownData = gson.toJson(selectedDrGodowns);
                                    editor.putString(AppConstants.DR_GODOWN_DATA, goDownData);

                                    StockDetailsResponse stockDetailsResponse = new StockDetailsResponse();
                                    stockDetailsResponse.setEssential_commodities(essestinalCommodities);
                                    stockDetailsResponse.setDialy_requirements(dailyReqCommodities);
                                    stockDetailsResponse.setEmpties(emptiesCommodities);

                                    editor.putString(AppConstants.StockDetailsResponse, gson.toJson(stockDetailsResponse));
                                    editor.commit();

                                    startActivity(new Intent(DRGODownSelActivity.this, DRGodownActivity.class));
                                } else {
                                    callSnackBar(getString(R.string.no_comm));
                                }


                            } else {
                                customOnlineAlert("Do you want to proceed in Online mode?");
                            }
                        }
                    });

                }
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
                gccOfflineRepository.deleteGCCRecord(DRGODownSelActivity.this, selectedDivId, selectedSocietyId, selectedGoDownId);
            }
        });

    }


    void callService(boolean flag) {
        if (validateFields()) {
            Gson gson = new Gson();
            StockViewModel viewModel = new StockViewModel(getApplication(), DRGODownSelActivity.this);
            if (Utils.checkInternetConnection(DRGODownSelActivity.this)) {
                customProgressDialog.show();
                LiveData<StockDetailsResponse> officesResponseLiveData = viewModel.getStockData(selectedGoDownId);
                officesResponseLiveData.observe(DRGODownSelActivity.this, new Observer<StockDetailsResponse>() {
                    @Override
                    public void onChanged(StockDetailsResponse stockDetailsResponse) {

                        customProgressDialog.hide();
                        officesResponseLiveData.removeObservers(DRGODownSelActivity.this);

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
                                        Gson gson = new Gson();
                                        String goDownData = gson.toJson(selectedDrGodowns);
                                        editor.putString(AppConstants.DR_GODOWN_DATA, goDownData);
                                        editor.putString(AppConstants.StockDetailsResponse, gson.toJson(stockDetailsResponse));
                                        editor.commit();

                                        startActivity(new Intent(context, DRGodownActivity.class));
                                    } else {
                                        GccOfflineEntity gccOfflineEntity = new GccOfflineEntity();
                                        gccOfflineEntity.setDivisionId(selectedDivId);
                                        gccOfflineEntity.setDivisionName(binding.spDivision.getSelectedItem().toString());
                                        gccOfflineEntity.setSocietyId(selectedSocietyId);
                                        gccOfflineEntity.setSocietyName(binding.spSociety.getSelectedItem().toString());
                                        gccOfflineEntity.setDrgownId(selectedGoDownId);
                                        gccOfflineEntity.setDrgownName(binding.spGodown.getSelectedItem().toString());
                                        gccOfflineEntity.setEssentials(gson.toJson(stockDetailsResponse.getEssential_commodities()));
                                        gccOfflineEntity.setDailyReq(gson.toJson(stockDetailsResponse.getDialy_requirements()));
                                        gccOfflineEntity.setEmpties(gson.toJson(stockDetailsResponse.getEmpties()));
                                        gccOfflineEntity.setType(AppConstants.OFFLINE_DR_GODOWN);

                                        gccOfflineRepository.insertGCCRecord(DRGODownSelActivity.this, gccOfflineEntity);
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
                Utils.customWarningAlert(DRGODownSelActivity.this, getResources().getString(R.string.app_name), "Please check internet");
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
        } else if (TextUtils.isEmpty(selectedGoDownId)) {
            showSnackBar("Please select godown");
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
        binding.llDownload.setVisibility(View.GONE);
        if (adapterView.getId() == R.id.sp_division) {
            selectedDrGodowns = null;
            selectedSocietyId = "";
            selectedDivId = "";
            selectedGoDownId = "";
            binding.spSociety.setAdapter(selectAdapter);
            divisionsInfos = new ArrayList<>();
            societies = new ArrayList<>();
            societies.add("--Select--");
            if (position != 0) {
                LiveData<String> liveData = viewModel.getDivisionId(binding.spDivision.getSelectedItem().toString());
                liveData.observe(DRGODownSelActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        liveData.removeObservers(DRGODownSelActivity.this);
                        if (str != null) {
                            selectedDivId = str;
                            LiveData<List<DivisionsInfo>> listLiveData = viewModel.getSocieties(selectedDivId);
                            listLiveData.observe(DRGODownSelActivity.this, new Observer<List<DivisionsInfo>>() {
                                @Override
                                public void onChanged(List<DivisionsInfo> divisionsInfoList) {
                                    listLiveData.removeObservers(DRGODownSelActivity.this);
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
                                        binding.spGodown.setAdapter(selectAdapter);
                                        showSnackBar("No societies found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedDrGodowns = null;
                selectedDivId = "";
                selectedSocietyId = "";
                binding.spSociety.setAdapter(selectAdapter);
                selectedGoDownId = "";
                binding.spGodown.setAdapter(selectAdapter);
            }
        } else if (adapterView.getId() == R.id.sp_society) {
            binding.llDownload.setVisibility(View.GONE);
            if (position != 0) {
                selectedDrGodowns = null;
                selectedSocietyId = "";
                selectedGoDownId = "";
                binding.spGodown.setAdapter(selectAdapter);
                drGodowns = new ArrayList<>();
                LiveData<String> liveData = viewModel.getSocietyId(selectedDivId, binding.spSociety.getSelectedItem().toString());
                liveData.observe(DRGODownSelActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        liveData.removeObservers(DRGODownSelActivity.this);
                        if (str != null) {
                            selectedSocietyId = str;
                            LiveData<List<DrGodowns>> listLiveData = viewModel.getDRGoDowns(selectedDivId, selectedSocietyId);
                            listLiveData.observe(DRGODownSelActivity.this, new Observer<List<DrGodowns>>() {
                                @Override
                                public void onChanged(List<DrGodowns> godownsList) {
                                    listLiveData.removeObservers(DRGODownSelActivity.this);
                                    if (godownsList != null && godownsList.size() > 0) {
                                        drGodowns.add("-Select-");
                                        for (int i = 0; i < godownsList.size(); i++) {
                                            drGodowns.add(godownsList.get(i).getGodownName());
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_spinner_dropdown_item, drGodowns);
                                        binding.spGodown.setAdapter(adapter);
                                    } else {
                                        binding.spGodown.setAdapter(selectAdapter);
                                        showSnackBar("No DR Godowns found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedDrGodowns = null;
                selectedSocietyId = "";
                selectedGoDownId = "";
                binding.spGodown.setAdapter(selectAdapter);
            }
        } else if (adapterView.getId() == R.id.sp_godown) {
            binding.llDownload.setVisibility(View.VISIBLE);
            if (position != 0) {
                selectedDrGodowns = null;
                selectedGoDownId = "";
                LiveData<DrGodowns> drGodownsLiveData = viewModel.getGODownID(selectedDivId, selectedSocietyId, binding.spGodown.getSelectedItem().toString());
                drGodownsLiveData.observe(DRGODownSelActivity.this, new Observer<DrGodowns>() {
                    @Override
                    public void onChanged(DrGodowns drGodowns) {
                        if (drGodowns != null) {
                            selectedGoDownId = drGodowns.getGodownId();
                            selectedDrGodowns = drGodowns;

                            LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(selectedDivId, selectedSocietyId, selectedGoDownId);
                            drGodownLiveData.observe(DRGODownSelActivity.this, new Observer<GccOfflineEntity>() {
                                @Override
                                public void onChanged(GccOfflineEntity drGodowns) {
                                    drGodownLiveData.removeObservers(DRGODownSelActivity.this);

                                    if (drGodowns == null) {
                                        binding.btnDownload.setText("Download");
                                        binding.btnRemove.setVisibility(View.GONE);
                                    } else {
                                        binding.btnDownload.setText("Re-Download");
                                        binding.btnRemove.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        } else {
                            showSnackBar(getString(R.string.something));
                        }
                    }
                });
            } else {
                selectedDrGodowns = null;
                selectedGoDownId = "";
                binding.llDownload.setVisibility(View.GONE);
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
                binding.btnDownload.setText("Re-Download");
                binding.btnRemove.setVisibility(View.VISIBLE);
                Utils.customSyncSuccessAlert(DRGODownSelActivity.this, getResources().getString(R.string.app_name),
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
                binding.btnDownload.setText("Download");
                binding.btnRemove.setVisibility(View.GONE);
                Utils.customSyncSuccessAlert(DRGODownSelActivity.this, getResources().getString(R.string.app_name),
                        "Data deleted successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void customOnlineAlert(String msg) {
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
                dialogMessage.setText(msg);
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
