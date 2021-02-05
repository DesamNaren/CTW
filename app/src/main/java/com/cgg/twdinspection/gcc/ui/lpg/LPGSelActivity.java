package com.cgg.twdinspection.gcc.ui.lpg;

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
import com.cgg.twdinspection.databinding.ActivityLpgSelBinding;
import com.cgg.twdinspection.gcc.interfaces.GCCOfflineInterface;
import com.cgg.twdinspection.gcc.room.repository.GCCOfflineRepository;
import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.gcc.source.stock.CommonCommodity;
import com.cgg.twdinspection.gcc.source.stock.PetrolStockDetailsResponse;
import com.cgg.twdinspection.gcc.source.suppliers.lpg.LPGSupplierInfo;
import com.cgg.twdinspection.gcc.ui.petrolpump.PetrolPumpActivity;
import com.cgg.twdinspection.gcc.ui.petrolpump.PetrolPumpSelActivity;
import com.cgg.twdinspection.gcc.viewmodel.GCCOfflineViewModel;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
import com.cgg.twdinspection.gcc.viewmodel.DivisionSelectionViewModel;
import com.cgg.twdinspection.inspection.viewmodel.StockViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LPGSelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, GCCOfflineInterface {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    CustomProgressDialog customProgressDialog;
    ActivityLpgSelBinding binding;
    private Context context;
    private DivisionSelectionViewModel viewModel;
    private String selectedDivId, selectedSocietyId, selectLPGId;
    private List<DivisionsInfo> divisionsInfos;
    private List<String> societies;
    private List<String> LPGs;
    private LPGSupplierInfo selectedLPGs;
    ArrayAdapter selectAdapter;
    private GCCOfflineViewModel gccOfflineViewModel;
    private GCCOfflineRepository gccOfflineRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lpg_sel);
        context = LPGSelActivity.this;
        gccOfflineRepository = new GCCOfflineRepository(getApplication());
        gccOfflineViewModel = new GCCOfflineViewModel(getApplication());
        divisionsInfos = new ArrayList<>();
        societies = new ArrayList<>();
        LPGs = new ArrayList<>();
        customProgressDialog = new CustomProgressDialog(context);
        binding.header.headerTitle.setText(getResources().getString(R.string.gcc_lpg));
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LPGSelActivity.this, DashboardMenuActivity.class)
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
                divisionLiveData.removeObservers(LPGSelActivity.this);
                customProgressDialog.dismiss();
                if (divisions != null && divisions.size() > 0) {
                    ArrayList<String> divisionNames = new ArrayList<>();
                    divisionNames.add("-Select-");
                    divisionNames.addAll(divisions);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                            android.R.layout.simple_spinner_dropdown_item, divisionNames
                    );
                    binding.spDivision.setAdapter(adapter);
                    LiveData<List<LPGSupplierInfo>> lpgLiveData = viewModel.getAllLPGSuppliers();
                    lpgLiveData.observe(LPGSelActivity.this, new Observer<List<LPGSupplierInfo>>() {
                        @Override
                        public void onChanged(List<LPGSupplierInfo> lpgSupplierInfos) {
                            lpgLiveData.removeObservers(LPGSelActivity.this);
                            customProgressDialog.dismiss();
                            if (lpgSupplierInfos == null || lpgSupplierInfos.size() <= 0) {
                                Utils.customGCCSyncAlert(LPGSelActivity.this, getString(R.string.app_name), "No LPG godowns found...\n Do you want sync LPG Godown master data to proceed further");
                            }
                        }
                    });
                } else {
                    Utils.customGCCSyncAlert(LPGSelActivity.this, getString(R.string.app_name), "No divisions found...\n Do you want sync division master data to proceed further?");
                }
            }
        });


        binding.spDivision.setOnItemSelectedListener(this);
        binding.spSociety.setOnItemSelectedListener(this);
        binding.spLpg.setOnItemSelectedListener(this);
        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {


                    LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(selectedDivId, selectedSocietyId, selectLPGId);
                    drGodownLiveData.observe(LPGSelActivity.this, new Observer<GccOfflineEntity>() {
                        @Override
                        public void onChanged(GccOfflineEntity drGodowns) {
                            drGodownLiveData.removeObservers(LPGSelActivity.this);

                            if (drGodowns != null) {

                                Type listType = new TypeToken<ArrayList<CommonCommodity>>() {
                                }.getType();
                                List<CommonCommodity> petrolCommodities = new Gson().fromJson(drGodowns.getPetrolCommodities(), listType);


                                if (petrolCommodities != null && petrolCommodities.size() > 0) {
                                    Gson gson = new Gson();
                                    String lpgData = gson.toJson(selectedLPGs);
                                    editor.putString(AppConstants.LPG_DATA, lpgData);

                                    PetrolStockDetailsResponse stockDetailsResponse = new PetrolStockDetailsResponse();
                                    stockDetailsResponse.setCommonCommodities(petrolCommodities);

                                    editor.putString(AppConstants.StockDetailsResponse, gson.toJson(stockDetailsResponse));
                                    editor.commit();

                                    startActivity(new Intent(LPGSelActivity.this, LPGActivity.class));
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
                gccOfflineRepository.deleteGCCRecord(LPGSelActivity.this, selectedDivId, selectedSocietyId, selectLPGId);
            }
        });
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
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

    void callService(boolean flag) {
        if (validateFields()) {
            Gson gson = new Gson();
            StockViewModel viewModel = new StockViewModel(getApplication(), LPGSelActivity.this);
            if (Utils.checkInternetConnection(LPGSelActivity.this)) {
                customProgressDialog.show();
                LiveData<PetrolStockDetailsResponse> officesResponseLiveData = viewModel.getPLPGStockData(selectLPGId);
                officesResponseLiveData.observe(LPGSelActivity.this, new Observer<PetrolStockDetailsResponse>() {
                    @Override
                    public void onChanged(PetrolStockDetailsResponse stockDetailsResponse) {

                        customProgressDialog.hide();
                        officesResponseLiveData.removeObservers(LPGSelActivity.this);

                        if (stockDetailsResponse != null && stockDetailsResponse.getStatusCode() != null) {
                            if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {

                                if (stockDetailsResponse.getCommonCommodities() != null && stockDetailsResponse.getCommonCommodities().size() > 0) {
                                    if (flag) {
                                        Gson gson = new Gson();
                                        String lpgData = gson.toJson(selectedLPGs);
                                        editor.putString(AppConstants.LPG_DATA, lpgData);
                                        editor.putString(AppConstants.StockDetailsResponse, gson.toJson(stockDetailsResponse));
                                        editor.commit();

                                        startActivity(new Intent(context, LPGActivity.class));
                                    } else {
                                        GccOfflineEntity gccOfflineEntity = new GccOfflineEntity();
                                        gccOfflineEntity.setDivisionId(selectedDivId);
                                        gccOfflineEntity.setDivisionName(binding.spDivision.getSelectedItem().toString());
                                        gccOfflineEntity.setSocietyId(selectedSocietyId);
                                        gccOfflineEntity.setSocietyName(binding.spSociety.getSelectedItem().toString());
                                        gccOfflineEntity.setDrgownId(selectLPGId);
                                        gccOfflineEntity.setDrgownName(binding.spLpg.getSelectedItem().toString());
                                        gccOfflineEntity.setPetrolCommodities(gson.toJson(stockDetailsResponse.getCommonCommodities()));
                                        gccOfflineEntity.setType(AppConstants.OFFLINE_LPG);

                                        gccOfflineRepository.insertGCCRecord(LPGSelActivity.this, gccOfflineEntity);
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
                Utils.customWarningAlert(LPGSelActivity.this, getResources().getString(R.string.app_name), "Please check internet");
            }
        }
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
        } else if (TextUtils.isEmpty(selectLPGId)) {
            showSnackBar("Please select LPG godown");
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
            selectedLPGs = null;
            selectedSocietyId = "";
            selectedDivId = "";
            selectLPGId = "";
            binding.spSociety.setAdapter(selectAdapter);
            divisionsInfos = new ArrayList<>();
            societies = new ArrayList<>();
            societies.add("--Select--");
            if (position != 0) {
                LiveData<String> liveData = viewModel.getDivisionId(binding.spDivision.getSelectedItem().toString());
                liveData.observe(LPGSelActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        liveData.removeObservers(LPGSelActivity.this);
                        if (str != null) {
                            selectedDivId = str;
                            LiveData<List<DivisionsInfo>> listLiveData = viewModel.getSocieties(selectedDivId);
                            listLiveData.observe(LPGSelActivity.this, new Observer<List<DivisionsInfo>>() {
                                @Override
                                public void onChanged(List<DivisionsInfo> divisionsInfoList) {
                                    listLiveData.removeObservers(LPGSelActivity.this);
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
                                        binding.spLpg.setAdapter(selectAdapter);
                                        showSnackBar("No societies found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedLPGs = null;
                selectedDivId = "";
                selectedSocietyId = "";
                binding.spSociety.setAdapter(selectAdapter);
                selectLPGId = "";
                binding.spLpg.setAdapter(selectAdapter);
            }
        } else if (adapterView.getId() == R.id.sp_society) {
            binding.llDownload.setVisibility(View.GONE);
            if (position != 0) {
                selectedLPGs = null;
                selectedSocietyId = "";
                selectLPGId = "";
                binding.spLpg.setAdapter(selectAdapter);
                LPGs = new ArrayList<>();
                LiveData<String> liveData = viewModel.getSocietyId(selectedDivId, binding.spSociety.getSelectedItem().toString());
                liveData.observe(LPGSelActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        liveData.removeObservers(LPGSelActivity.this);
                        if (str != null) {
                            selectedSocietyId = str;
                            LiveData<List<LPGSupplierInfo>> listLiveData = viewModel.getLPGSuppliers(selectedDivId, selectedSocietyId);
                            listLiveData.observe(LPGSelActivity.this, new Observer<List<LPGSupplierInfo>>() {
                                @Override
                                public void onChanged(List<LPGSupplierInfo> lpgSupplierInfos) {
                                    listLiveData.removeObservers(LPGSelActivity.this);
                                    if (lpgSupplierInfos != null && lpgSupplierInfos.size() > 0) {
                                        LPGs.add("-Select-");
                                        for (int i = 0; i < lpgSupplierInfos.size(); i++) {
                                            LPGs.add(lpgSupplierInfos.get(i).getGodownName());
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_spinner_dropdown_item, LPGs);
                                        binding.spLpg.setAdapter(adapter);
                                    } else {
                                        binding.spLpg.setAdapter(selectAdapter);
                                        showSnackBar("No LPG godown found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedLPGs = null;
                selectedSocietyId = "";
                selectLPGId = "";
                binding.spLpg.setAdapter(selectAdapter);
            }
        } else if (adapterView.getId() == R.id.sp_lpg) {
            if (position != 0) {
                binding.llDownload.setVisibility(View.VISIBLE);
                selectedLPGs = null;
                selectLPGId = "";
                LiveData<LPGSupplierInfo> lpgLiveData = viewModel.getLPGID(selectedDivId, selectedSocietyId, binding.spLpg.getSelectedItem().toString());
                lpgLiveData.observe(LPGSelActivity.this, new Observer<LPGSupplierInfo>() {
                    @Override
                    public void onChanged(LPGSupplierInfo lpgSupplierInfo) {
                        if (lpgSupplierInfo != null) {
                            selectLPGId = lpgSupplierInfo.getGodownId();
                            selectedLPGs = lpgSupplierInfo;

                            LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(selectedDivId, selectedSocietyId, selectLPGId);
                            drGodownLiveData.observe(LPGSelActivity.this, new Observer<GccOfflineEntity>() {
                                @Override
                                public void onChanged(GccOfflineEntity drGodowns) {
                                    drGodownLiveData.removeObservers(LPGSelActivity.this);

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
                selectedLPGs = null;
                selectLPGId = "";
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
                Utils.customSyncSuccessAlert(LPGSelActivity.this, getResources().getString(R.string.app_name),
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
                Utils.customSyncSuccessAlert(LPGSelActivity.this, getResources().getString(R.string.app_name),
                        "Data deleted successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
