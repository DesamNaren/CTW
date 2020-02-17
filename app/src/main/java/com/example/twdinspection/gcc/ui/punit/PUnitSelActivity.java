package com.example.twdinspection.gcc.ui.punit;

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

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.CustomProgressDialog;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityPUnitSelBinding;
import com.example.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.example.twdinspection.gcc.source.suppliers.punit.PUnits;
import com.example.twdinspection.inspection.viewmodel.DivisionSelectionViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PUnitSelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    CustomProgressDialog customProgressDialog;
    ActivityPUnitSelBinding binding;
    private Context context;
    private DivisionSelectionViewModel viewModel;
    private String selectedDivId, selectedSocietyId, selectedPUnitID;
    private List<DivisionsInfo> divisionsInfos;
    private List<String> societies;
    private List<String> pUnits;
    private PUnits selectedPUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_p_unit_sel);
        context = PUnitSelActivity.this;
        divisionsInfos = new ArrayList<>();
        societies = new ArrayList<>();
        pUnits = new ArrayList<>();
        customProgressDialog = new CustomProgressDialog(context);
        binding.header.headerTitle.setText(getResources().getString(R.string.p_unit));
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
        divisionLiveData.observe(this, divisions -> {
            divisionLiveData.removeObservers(PUnitSelActivity.this);
            customProgressDialog.dismiss();
            if (divisions != null && divisions.size() > 0) {
                ArrayList<String> divisionNames = new ArrayList<>();
                divisionNames.add("-Select-");
                divisionNames.addAll(divisions);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_dropdown_item, divisionNames
                );
                binding.spDivision.setAdapter(adapter);
            }
        });

        binding.spDivision.setOnItemSelectedListener(this);
        binding.spSociety.setOnItemSelectedListener(this);
        binding.spPUnit.setOnItemSelectedListener(this);
        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    Gson gson = new Gson();
                    String pUnitData = gson.toJson(selectedPUnits);
                    editor.putString(AppConstants.P_UNIT_DATA, pUnitData);
                    editor.commit();

                    startActivity(new Intent(PUnitSelActivity.this, PUnitActivity.class));
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
        }
//        else if (TextUtils.isEmpty(selectedSocietyId)) {
//            showSnackBar("Please select society");
//            return false;
//        }

        else if (TextUtils.isEmpty(selectedPUnitID)) {
            showSnackBar("Please select processing unit");
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
            selectedPUnits = null;
            selectedSocietyId = "";
            selectedDivId = "";
            selectedPUnitID = "";
            divisionsInfos = new ArrayList<>();
            binding.spSociety.setAdapter(null);
            pUnits = new ArrayList<>();
            binding.spPUnit.setAdapter(null);
            societies = new ArrayList<>();
            societies.add("--Select--");
            if (position != 0) {
                LiveData<String> liveData = viewModel.getDivisionId(binding.spDivision.getSelectedItem().toString());
                liveData.observe(PUnitSelActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        liveData.removeObservers(PUnitSelActivity.this);
                        if (str != null) {
                            selectedDivId = str;
                            LiveData<List<DivisionsInfo>> listLiveData = viewModel.getSocieties(selectedDivId);
                            listLiveData.observe(PUnitSelActivity.this, new Observer<List<DivisionsInfo>>() {
                                @Override
                                public void onChanged(List<DivisionsInfo> divisionsInfoList) {
                                    listLiveData.removeObservers(PUnitSelActivity.this);
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
                                        showSnackBar("No societies found");
                                    }
                                }
                            });

                            LiveData<List<PUnits>> godownslistLiveData = viewModel.getPUnits(selectedDivId);
                            godownslistLiveData.observe(PUnitSelActivity.this, new Observer<List<PUnits>>() {
                                @Override
                                public void onChanged(List<PUnits> godownsList) {
                                    godownslistLiveData.removeObservers(PUnitSelActivity.this);
                                    if (godownsList != null && godownsList.size() > 0) {
                                        pUnits.add("-Select-");
                                        for (int i = 0; i < godownsList.size(); i++) {
                                            pUnits.add(godownsList.get(i).getGodownName());
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_spinner_dropdown_item, pUnits);
                                        binding.spPUnit.setAdapter(adapter);
                                    } else {
                                        showSnackBar("No processing units found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedPUnits = null;
                selectedDivId = "";
                selectedSocietyId = "";
                binding.spSociety.setAdapter(null);
                selectedPUnitID = "";
                binding.spPUnit.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_society) {

            if (position != 0) {
                selectedPUnits = null;
                selectedSocietyId = "";
                selectedPUnitID = "";

                pUnits = new ArrayList<>();
                LiveData<String> liveData = viewModel.getSocietyId(selectedDivId, binding.spSociety.getSelectedItem().toString());
                liveData.observe(PUnitSelActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        liveData.removeObservers(PUnitSelActivity.this);
                        if (str != null) {
                            selectedSocietyId = str;
                            LiveData<List<PUnits>> listLiveData = viewModel.getPUnits(selectedDivId, selectedSocietyId);
                            listLiveData.observe(PUnitSelActivity.this, new Observer<List<PUnits>>() {
                                @Override
                                public void onChanged(List<PUnits> godownsList) {
                                    binding.spPUnit.setAdapter(null);
                                    listLiveData.removeObservers(PUnitSelActivity.this);
                                    if (godownsList != null && godownsList.size() > 0) {
                                        pUnits.add("-Select-");
                                        for (int i = 0; i < godownsList.size(); i++) {
                                            pUnits.add(godownsList.get(i).getGodownName());
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_spinner_dropdown_item, pUnits);
                                        binding.spPUnit.setAdapter(adapter);
                                    } else {
                                        showSnackBar("No processing units found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedPUnits = null;
                selectedSocietyId = "";
                selectedPUnitID = "";
            }
        } else if (adapterView.getId() == R.id.sp_p_unit) {
            if (position != 0) {
                selectedPUnits = null;
                selectedPUnitID = "";
                LiveData<PUnits> liveData = viewModel.getPUnitID(selectedDivId, selectedSocietyId, binding.spPUnit.getSelectedItem().toString());
                liveData.observe(PUnitSelActivity.this, new Observer<PUnits>() {
                    @Override
                    public void onChanged(PUnits pUnits) {
                        liveData.removeObservers(PUnitSelActivity.this);
                        if (pUnits != null) {
                            selectedPUnitID = pUnits.getGodownId();
                            selectedPUnits = pUnits;
                        }
                    }
                });
                LiveData<PUnits> liveDataPUnit = viewModel.getPUnitID(selectedDivId, binding.spPUnit.getSelectedItem().toString());
                liveDataPUnit.observe(PUnitSelActivity.this, new Observer<PUnits>() {
                    @Override
                    public void onChanged(PUnits pUnits) {
                        liveDataPUnit.removeObservers(PUnitSelActivity.this);
                        if (pUnits != null) {
                            selectedPUnitID = pUnits.getGodownId();
                            selectedPUnits = pUnits;
                        }
                    }
                });
            } else {
                selectedPUnits = null;
                selectedPUnitID = "";
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
