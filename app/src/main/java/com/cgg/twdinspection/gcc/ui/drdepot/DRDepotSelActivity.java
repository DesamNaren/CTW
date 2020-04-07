package com.cgg.twdinspection.gcc.ui.drdepot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

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
import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.cgg.twdinspection.inspection.viewmodel.DivisionSelectionViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DRDepotSelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_depot_sel);
        context = DRDepotSelActivity.this;
        divisionsInfos = new ArrayList<>();
        societies = new ArrayList<>();
        drDepots = new ArrayList<>();
        customProgressDialog = new CustomProgressDialog(context);
        binding.header.headerTitle.setText(getResources().getString(R.string.dr_depot));
        binding.header.ivHome.setVisibility(View.GONE);
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                        }else{
                            Utils.customGCCSyncAlert(DRDepotSelActivity.this,getString(R.string.app_name),"No divisions found...\n Do you want to sync divisions?");
                        }
                    }
                });

        LiveData<List<DRDepots>> drGodownLiveData = viewModel.getAllDRDepots();
        drGodownLiveData.observe(this, new Observer<List<DRDepots>>() {
            @Override
            public void onChanged(List<DRDepots> drGodowns) {
                drGodownLiveData.removeObservers(DRDepotSelActivity.this);
                customProgressDialog.dismiss();
                if (drGodowns== null || drGodowns.size() <= 0) {
                    Utils.customGCCSyncAlert(DRDepotSelActivity.this,getString(R.string.app_name),"No DR Depots found...\n Do you want to sync DR Depots?");
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
                    Gson gson = new Gson();
                    String depotData = gson.toJson(selectedDRDepots);
                    editor.putString(AppConstants.DR_DEPOT_DATA, depotData);
                    editor.commit();

                    startActivity(new Intent(DRDepotSelActivity.this, DRDepotActivity.class));
                }
            }
        });
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
        }else if (TextUtils.isEmpty(selectedDepotID)) {
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
            selectedDRDepots=null;
            selectedSocietyId = "";
            selectedDivId = "";
            selectedDepotID = "";
            binding.spSociety.setAdapter(null);
            divisionsInfos = new ArrayList<>();
            societies = new ArrayList<>();
            societies.add("--Select--");
            if (position != 0) {
                LiveData<String> liveData =  viewModel.getDivisionId(binding.spDivision.getSelectedItem().toString());
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
                                    }else{
                                        binding.spSociety.setAdapter(null);
                                        binding.spDepot.setAdapter(null);
                                        showSnackBar("No societies found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedDRDepots=null;
                selectedDivId = "";
                selectedSocietyId = "";
                binding.spSociety.setAdapter(null);
                selectedDepotID = "";
                binding.spDepot.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_society) {
            if (position != 0) {
                selectedDRDepots=null;
                selectedSocietyId = "";
                selectedDepotID = "";
                binding.spDepot.setAdapter(null);
                drDepots = new ArrayList<>();
                LiveData<String> liveData= viewModel.getSocietyId(selectedDivId, binding.spSociety.getSelectedItem().toString());
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
                                    }else {
                                        binding.spDepot.setAdapter(null);
                                        showSnackBar("No DR Depots found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedDRDepots=null;
                selectedSocietyId = "";
                selectedDepotID = "";
                binding.spDepot.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_depot) {
            if (position != 0) {
                selectedDRDepots=null;
                selectedDepotID = "";
               LiveData<DRDepots> liveData= viewModel.getDRDepotID(selectedDivId, selectedSocietyId, binding.spDepot.getSelectedItem().toString());
               liveData.observe(DRDepotSelActivity.this, new Observer<DRDepots>() {
                    @Override
                    public void onChanged(DRDepots drDepots) {
                        liveData.removeObservers(DRDepotSelActivity.this);
                        if (drDepots != null) {
                            selectedDepotID = drDepots.getGodownId();
                            selectedDRDepots = drDepots;
                        }else{
                            showSnackBar(getString(R.string.something));
                        }
                    }
                });
            } else {
                selectedDRDepots=null;
                selectedDepotID = "";
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
