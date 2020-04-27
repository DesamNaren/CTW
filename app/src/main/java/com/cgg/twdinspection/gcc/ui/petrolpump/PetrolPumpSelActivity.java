package com.cgg.twdinspection.gcc.ui.petrolpump;

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
import com.cgg.twdinspection.databinding.ActivityPetrolPumpSelBinding;
import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolSupplierInfo;
import com.cgg.twdinspection.inspection.ui.DashboardActivity;
import com.cgg.twdinspection.inspection.viewmodel.DivisionSelectionViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PetrolPumpSelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_petrol_pump_sel);
        context = PetrolPumpSelActivity.this;
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
                startActivity(new Intent(PetrolPumpSelActivity.this, DashboardActivity.class)
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
                    Gson gson = new Gson();
                    String petrolPumpData = gson.toJson(selectedPetrolPumps);
                    editor.putString(AppConstants.PETROL_PUMP_DATA, petrolPumpData);
                    editor.commit();

                    startActivity(new Intent(PetrolPumpSelActivity.this, PetrolPumpActivity.class));
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
            selectedPetrolPumps = null;
            selectedSocietyId = "";
            selectedDivId = "";
            selectPetrolId = "";
            binding.spSociety.setAdapter(null);
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
                                        binding.spSociety.setAdapter(null);
                                        binding.spPetrol.setAdapter(null);
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
                binding.spSociety.setAdapter(null);
                selectPetrolId = "";
                binding.spPetrol.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_society) {
            if (position != 0) {
                selectedPetrolPumps = null;
                selectedSocietyId = "";
                selectPetrolId = "";
                binding.spPetrol.setAdapter(null);
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
                                        binding.spPetrol.setAdapter(null);
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
                binding.spPetrol.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_petrol) {
            if (position != 0) {
                selectedPetrolPumps = null;
                selectPetrolId = "";
                LiveData<PetrolSupplierInfo> drGodownsLiveData = viewModel.getPetrolPumpID(selectedDivId, selectedSocietyId, binding.spPetrol.getSelectedItem().toString());
                drGodownsLiveData.observe(PetrolPumpSelActivity.this, new Observer<PetrolSupplierInfo>() {
                    @Override
                    public void onChanged(PetrolSupplierInfo petrolSupplierInfo) {
                        if (petrolSupplierInfo != null) {
                            selectPetrolId = petrolSupplierInfo.getGodownId();
                            selectedPetrolPumps = petrolSupplierInfo;
                        } else {
                            showSnackBar(getString(R.string.something));
                        }
                    }
                });
            } else {
                selectedPetrolPumps = null;
                selectPetrolId = "";
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
