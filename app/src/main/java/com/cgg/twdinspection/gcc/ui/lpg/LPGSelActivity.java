package com.cgg.twdinspection.gcc.ui.lpg;

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
import com.cgg.twdinspection.databinding.ActivityLpgSelBinding;
import com.cgg.twdinspection.databinding.ActivityPetrolPumpSelBinding;
import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.suppliers.lpg.LPGSupplierInfo;
import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolSupplierInfo;
import com.cgg.twdinspection.gcc.ui.petrolpump.PetrolPumpActivity;
import com.cgg.twdinspection.gcc.ui.petrolpump.PetrolPumpFindingsActivity;
import com.cgg.twdinspection.inspection.viewmodel.DivisionSelectionViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LPGSelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lpg_sel);
        context = LPGSelActivity.this;
        divisionsInfos = new ArrayList<>();
        societies = new ArrayList<>();
        LPGs = new ArrayList<>();
        customProgressDialog = new CustomProgressDialog(context);
        binding.header.headerTitle.setText(getResources().getString(R.string.lpg));
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
                }else{
                    Utils.customGCCSyncAlert(LPGSelActivity.this,getString(R.string.app_name),"No divisions found...\n Do you want to sync divisions?");
                }
            }
        });
         LiveData<List<LPGSupplierInfo>> lpgLiveData = viewModel.getAllLPGSuppliers();
        lpgLiveData.observe(this, new Observer<List<LPGSupplierInfo>>() {
                    @Override
                    public void onChanged(List<LPGSupplierInfo> lpgSupplierInfos) {
                        lpgLiveData.removeObservers(LPGSelActivity.this);
                        customProgressDialog.dismiss();
                        if (lpgSupplierInfos== null || lpgSupplierInfos.size() <= 0) {
                            Utils.customGCCSyncAlert(LPGSelActivity.this,getString(R.string.app_name),"No LPG godowns found...\n Do you want to sync LPG godowns?");
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
                    Gson gson = new Gson();
                    String lpgData = gson.toJson(selectedLPGs);
                    editor.putString(AppConstants.LPG_DATA, lpgData);
                    editor.commit();

                    startActivity(new Intent(LPGSelActivity.this, LPGActivity.class));

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
        if (adapterView.getId() == R.id.sp_division) {
            selectedLPGs = null;
            selectedSocietyId = "";
            selectedDivId = "";
            selectLPGId = "";
            binding.spSociety.setAdapter(null);
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
                                        binding.spSociety.setAdapter(null);
                                        binding.spLpg.setAdapter(null);
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
                binding.spSociety.setAdapter(null);
                selectLPGId = "";
                binding.spLpg.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_society) {
            if (position != 0) {
                selectedLPGs = null;
                selectedSocietyId = "";
                selectLPGId = "";
                binding.spLpg.setAdapter(null);
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
                                        binding.spLpg.setAdapter(null);
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
                binding.spLpg.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_lpg) {
            if (position != 0) {
                selectedLPGs = null;
                selectLPGId = "";
                LiveData<LPGSupplierInfo> lpgLiveData = viewModel.getLPGID(selectedDivId, selectedSocietyId, binding.spLpg.getSelectedItem().toString());
                lpgLiveData.observe(LPGSelActivity.this, new Observer<LPGSupplierInfo>() {
                    @Override
                    public void onChanged(LPGSupplierInfo lpgSupplierInfo) {
                        if (lpgSupplierInfo != null) {
                            selectLPGId = lpgSupplierInfo.getGodownId();
                            selectedLPGs = lpgSupplierInfo;
                        }else{
                            showSnackBar(getString(R.string.something));
                        }
                    }
                });
            } else {
                selectedLPGs = null;
                selectLPGId = "";
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}