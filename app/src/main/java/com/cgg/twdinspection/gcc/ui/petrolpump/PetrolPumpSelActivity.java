package com.cgg.twdinspection.gcc.ui.petrolpump;

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
import com.cgg.twdinspection.databinding.ActivityPetrolPumpSelBinding;
import com.cgg.twdinspection.gcc.interfaces.GCCOfflineInterface;
import com.cgg.twdinspection.gcc.room.repository.GCCOfflineRepository;
import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.gcc.source.stock.CommonCommodity;
import com.cgg.twdinspection.gcc.source.stock.PetrolStockDetailsResponse;
import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolSupplierInfo;
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

public class PetrolPumpSelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, GCCOfflineInterface {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    CustomProgressDialog customProgressDialog;
    ActivityPetrolPumpSelBinding binding;
    private Context context;
    private DivisionSelectionViewModel viewModel;
    private String selectedDivId, selectedSocietyId, selectPetrolId;
    private List<DivisionsInfo> divisionsInfos;
    private List<String> societies;
    private List<String> petrolPumps;
    private PetrolSupplierInfo selectedPetrolPumps;
    ArrayAdapter selectAdapter;
    private GCCOfflineRepository gccOfflineRepository;
    private GCCOfflineViewModel gccOfflineViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_petrol_pump_sel);
        context = PetrolPumpSelActivity.this;
        gccOfflineRepository = new GCCOfflineRepository(getApplication());
        gccOfflineViewModel = new GCCOfflineViewModel(getApplication());
        divisionsInfos = new ArrayList<>();
        societies = new ArrayList<>();
        petrolPumps = new ArrayList<>();
        customProgressDialog = new CustomProgressDialog(context);
        binding.header.headerTitle.setText(getResources().getString(R.string.gcc_petrol_pump));
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PetrolPumpSelActivity.this, DashboardMenuActivity.class)
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

        ArrayList selectList = new ArrayList();
        selectList.add("Select");
        selectAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, selectList);


        LiveData<List<String>> divisionLiveData = viewModel.getAllDivisions();
        divisionLiveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> divisions) {
                divisionLiveData.removeObservers(PetrolPumpSelActivity.this);
                customProgressDialog.dismiss();
                if (divisions != null && divisions.size() > 0) {
                    ArrayList<String> divisionNames = new ArrayList<>();
                    divisionNames.add("-Select-");
                    divisionNames.addAll(divisions);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                            android.R.layout.simple_spinner_dropdown_item, divisionNames
                    );
                    binding.spDivision.setAdapter(adapter);
                    LiveData<List<PetrolSupplierInfo>> petrolLiveData = viewModel.getAllPetrolPumps();
                    petrolLiveData.observe(PetrolPumpSelActivity.this, new Observer<List<PetrolSupplierInfo>>() {
                        @Override
                        public void onChanged(List<PetrolSupplierInfo> petrolSupplierInfos) {
                            petrolLiveData.removeObservers(PetrolPumpSelActivity.this);
                            customProgressDialog.dismiss();
                            if (petrolSupplierInfos == null || petrolSupplierInfos.size() <= 0) {
                                Utils.customGCCSyncAlert(PetrolPumpSelActivity.this, getString(R.string.app_name), "No Petrol pumps found...\n Do you want to sync petrol pump master data to proceed further?");
                            }
                        }
                    });
                } else {
                    Utils.customGCCSyncAlert(PetrolPumpSelActivity.this, getString(R.string.app_name), "No divisions found...\n Do you want to sync division master data to proceed further?");
                }
            }
        });


        binding.spDivision.setOnItemSelectedListener(this);
        binding.spSociety.setOnItemSelectedListener(this);
        binding.spPetrol.setOnItemSelectedListener(this);
        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {

                    editor.putString(AppConstants.StockDetailsResponse, "");
                    editor.commit();


                    LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(selectedDivId, selectedSocietyId, selectPetrolId);
                    drGodownLiveData.observe(PetrolPumpSelActivity.this, new Observer<GccOfflineEntity>() {
                        @Override
                        public void onChanged(GccOfflineEntity drGodowns) {
                            drGodownLiveData.removeObservers(PetrolPumpSelActivity.this);

                            if (drGodowns != null) {

                                Type listType = new TypeToken<ArrayList<CommonCommodity>>() {
                                }.getType();
                                List<CommonCommodity> petrolCommodities = new Gson().fromJson(drGodowns.getPetrolCommodities(), listType);


                                if (petrolCommodities != null && petrolCommodities.size() > 0) {
                                    Gson gson = new Gson();
                                    String petrolData = gson.toJson(selectedPetrolPumps);
                                    editor.putString(AppConstants.PETROL_PUMP_DATA, petrolData);

                                    PetrolStockDetailsResponse stockDetailsResponse = new PetrolStockDetailsResponse();
                                    stockDetailsResponse.setCommonCommodities(petrolCommodities);

                                    editor.putString(AppConstants.StockDetailsResponse, gson.toJson(stockDetailsResponse));
                                    editor.commit();

                                    startActivity(new Intent(PetrolPumpSelActivity.this, PetrolPumpActivity.class));
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
                GccOfflineEntity entity = new GccOfflineEntity();
                entity.setDivisionId(selectedDivId);
                entity.setSocietyId(selectedSocietyId);
                entity.setDrgownId(selectPetrolId);

                gccOfflineRepository.deleteGCCRecord(PetrolPumpSelActivity.this, entity);
            }
        });

    }


    void callService(boolean flag) {
        if (validateFields()) {
            Gson gson = new Gson();
            StockViewModel viewModel = new StockViewModel(getApplication(), PetrolPumpSelActivity.this);
            if (Utils.checkInternetConnection(PetrolPumpSelActivity.this)) {
                customProgressDialog.show();
                LiveData<PetrolStockDetailsResponse> officesResponseLiveData = viewModel.getPLPGStockData(selectPetrolId);
                officesResponseLiveData.observe(PetrolPumpSelActivity.this, new Observer<PetrolStockDetailsResponse>() {
                    @Override
                    public void onChanged(PetrolStockDetailsResponse stockDetailsResponse) {

                        customProgressDialog.hide();
                        officesResponseLiveData.removeObservers(PetrolPumpSelActivity.this);

                        if (stockDetailsResponse != null && stockDetailsResponse.getStatusCode() != null) {
                            if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {

                                if (stockDetailsResponse.getCommonCommodities() != null && stockDetailsResponse.getCommonCommodities().size() > 0) {
                                    if (flag) {
                                        Gson gson = new Gson();
                                        String petrolData = gson.toJson(selectedPetrolPumps);
                                        editor.putString(AppConstants.PETROL_PUMP_DATA, petrolData);
                                        editor.putString(AppConstants.StockDetailsResponse, gson.toJson(stockDetailsResponse));
                                        editor.commit();

                                        startActivity(new Intent(context, PetrolPumpActivity.class));
                                    } else {
                                        GccOfflineEntity gccOfflineEntity = new GccOfflineEntity();
                                        gccOfflineEntity.setDivisionId(selectedDivId);
                                        gccOfflineEntity.setDivisionName(binding.spDivision.getSelectedItem().toString());
                                        gccOfflineEntity.setSocietyId(selectedSocietyId);
                                        gccOfflineEntity.setSocietyName(binding.spSociety.getSelectedItem().toString());
                                        gccOfflineEntity.setDrgownId(selectPetrolId);
                                        gccOfflineEntity.setDrgownName(binding.spPetrol.getSelectedItem().toString());
                                        gccOfflineEntity.setPetrolCommodities(gson.toJson(stockDetailsResponse.getCommonCommodities()));
                                        gccOfflineEntity.setType(AppConstants.OFFLINE_PETROL);

                                        gccOfflineRepository.insertGCCRecord(PetrolPumpSelActivity.this, gccOfflineEntity);
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
                Utils.customWarningAlert(PetrolPumpSelActivity.this, getResources().getString(R.string.app_name), "Please check internet");
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
        } else if (TextUtils.isEmpty(selectPetrolId)) {
            showSnackBar("Please select petrol pump");
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
            selectedPetrolPumps = null;
            selectedSocietyId = "";
            selectedDivId = "";
            selectPetrolId = "";
            binding.spSociety.setAdapter(selectAdapter);
            divisionsInfos = new ArrayList<>();
            societies = new ArrayList<>();
            societies.add("--Select--");
            if (position != 0) {
                LiveData<String> liveData = viewModel.getDivisionId(binding.spDivision.getSelectedItem().toString());
                liveData.observe(PetrolPumpSelActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        liveData.removeObservers(PetrolPumpSelActivity.this);
                        if (str != null) {
                            selectedDivId = str;
                            LiveData<List<DivisionsInfo>> listLiveData = viewModel.getSocieties(selectedDivId);
                            listLiveData.observe(PetrolPumpSelActivity.this, new Observer<List<DivisionsInfo>>() {
                                @Override
                                public void onChanged(List<DivisionsInfo> divisionsInfoList) {
                                    listLiveData.removeObservers(PetrolPumpSelActivity.this);
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
                                        binding.spPetrol.setAdapter(selectAdapter);
                                        showSnackBar("No societies found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedPetrolPumps = null;
                selectedDivId = "";
                selectedSocietyId = "";
                binding.spSociety.setAdapter(selectAdapter);
                selectPetrolId = "";
                binding.spPetrol.setAdapter(selectAdapter);
            }
        } else if (adapterView.getId() == R.id.sp_society) {
            binding.llDownload.setVisibility(View.GONE);
            if (position != 0) {
                selectedPetrolPumps = null;
                selectedSocietyId = "";
                selectPetrolId = "";
                binding.spPetrol.setAdapter(selectAdapter);
                petrolPumps = new ArrayList<>();
                LiveData<String> liveData = viewModel.getSocietyId(selectedDivId, binding.spSociety.getSelectedItem().toString());
                liveData.observe(PetrolPumpSelActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        liveData.removeObservers(PetrolPumpSelActivity.this);
                        if (str != null) {
                            selectedSocietyId = str;
                            LiveData<List<PetrolSupplierInfo>> listLiveData = viewModel.getPetroPumps(selectedDivId, selectedSocietyId);
                            listLiveData.observe(PetrolPumpSelActivity.this, new Observer<List<PetrolSupplierInfo>>() {
                                @Override
                                public void onChanged(List<PetrolSupplierInfo> petrolSupplierInfos) {
                                    listLiveData.removeObservers(PetrolPumpSelActivity.this);
                                    if (petrolSupplierInfos != null && petrolSupplierInfos.size() > 0) {
                                        petrolPumps.add("-Select-");
                                        for (int i = 0; i < petrolSupplierInfos.size(); i++) {
                                            petrolPumps.add(petrolSupplierInfos.get(i).getGodownName());
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_spinner_dropdown_item, petrolPumps);
                                        binding.spPetrol.setAdapter(adapter);
                                    } else {
                                        binding.spPetrol.setAdapter(selectAdapter);
                                        showSnackBar("No Petrol pumps found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedPetrolPumps = null;
                selectedSocietyId = "";
                selectPetrolId = "";
                binding.spPetrol.setAdapter(selectAdapter);
            }
        } else if (adapterView.getId() == R.id.sp_petrol) {
            if (position != 0) {
                binding.llDownload.setVisibility(View.VISIBLE);
                selectedPetrolPumps = null;
                selectPetrolId = "";
                LiveData<PetrolSupplierInfo> drGodownsLiveData = viewModel.getPetrolPumpID(selectedDivId, selectedSocietyId, binding.spPetrol.getSelectedItem().toString());
                drGodownsLiveData.observe(PetrolPumpSelActivity.this, new Observer<PetrolSupplierInfo>() {
                    @Override
                    public void onChanged(PetrolSupplierInfo petrolSupplierInfo) {
                        if (petrolSupplierInfo != null) {
                            selectPetrolId = petrolSupplierInfo.getGodownId();
                            selectedPetrolPumps = petrolSupplierInfo;

                            LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(selectedDivId, selectedSocietyId, selectPetrolId);
                            drGodownLiveData.observe(PetrolPumpSelActivity.this, new Observer<GccOfflineEntity>() {
                                @Override
                                public void onChanged(GccOfflineEntity drGodowns) {
                                    drGodownLiveData.removeObservers(PetrolPumpSelActivity.this);

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
                binding.llDownload.setVisibility(View.GONE);
                selectedPetrolPumps = null;
                selectPetrolId = "";
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
                Utils.customSyncSuccessAlert(PetrolPumpSelActivity.this, getResources().getString(R.string.app_name),
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
                Utils.customSyncSuccessAlert(PetrolPumpSelActivity.this, getResources().getString(R.string.app_name),
                        "Data deleted successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletedrGoDownCountSubmitted(int cnt, String msg) {

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
