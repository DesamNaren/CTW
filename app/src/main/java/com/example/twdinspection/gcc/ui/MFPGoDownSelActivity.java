package com.example.twdinspection.gcc.ui;

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
import com.example.twdinspection.databinding.ActivityMfpGodownSelBinding;
import com.example.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.example.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.example.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.example.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.example.twdinspection.inspection.viewmodel.DivisionSelectionViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MFPGoDownSelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    CustomProgressDialog customProgressDialog;
    ActivityMfpGodownSelBinding binding;
    private Context context;
    private DivisionSelectionViewModel viewModel;
    private String selectedDivId, selectedSocietyId, selectedMfpID;
    private List<DivisionsInfo> divisionsInfos;
    private List<String> societies;
    private List<String> mfpGoDowns;
    private MFPGoDowns selectedMfpGoDowns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mfp_godown_sel);
        context = MFPGoDownSelActivity.this;
        divisionsInfos = new ArrayList<>();
        societies = new ArrayList<>();
        mfpGoDowns = new ArrayList<>();
        customProgressDialog = new CustomProgressDialog(context);
        binding.header.headerTitle.setText(getResources().getString(R.string.dr_godown));
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
            divisionLiveData.removeObservers(MFPGoDownSelActivity.this);
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
        binding.spMfp.setOnItemSelectedListener(this);
        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    Gson gson = new Gson();
                    String depotData = gson.toJson(selectedMfpGoDowns);
                    editor.putString(AppConstants.DR_DEPOT_DATA, depotData);
                    editor.commit();

                    startActivity(new Intent(MFPGoDownSelActivity.this, DRDepotActivity.class));
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
        }else if (TextUtils.isEmpty(selectedMfpID)) {
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
            selectedMfpGoDowns =null;
            selectedSocietyId = "";
            selectedDivId = "";
            selectedMfpID = "";
            divisionsInfos = new ArrayList<>();
            societies = new ArrayList<>();
            societies.add("--Select--");
            if (position != 0) {
             LiveData<String> liveData=  viewModel.getDivisionId(binding.spDivision.getSelectedItem().toString());
             liveData.observe(MFPGoDownSelActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        liveData.removeObservers(MFPGoDownSelActivity.this);
                        if (str != null) {
                            selectedDivId = str;
                          LiveData<List<DivisionsInfo>> listLiveData=  viewModel.getSocieties(selectedDivId);
                          listLiveData.observe(MFPGoDownSelActivity.this, new Observer<List<DivisionsInfo>>() {
                                @Override
                                public void onChanged(List<DivisionsInfo> divisionsInfoList) {
                                    listLiveData.removeObservers(MFPGoDownSelActivity.this);
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
                                        showSnackBar("No societies found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedMfpGoDowns =null;
                selectedDivId = "";
                selectedSocietyId = "";
                binding.spSociety.setAdapter(null);
                selectedMfpID = "";
                binding.spMfp.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_society) {
            if (position != 0) {
                selectedMfpGoDowns =null;
                selectedSocietyId = "";
                selectedMfpID = "";
                mfpGoDowns = new ArrayList<>();
              LiveData<String>  liveData =  viewModel.getSocietyId(selectedDivId, binding.spSociety.getSelectedItem().toString());
              liveData.observe(MFPGoDownSelActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        liveData.removeObservers(MFPGoDownSelActivity.this);
                        if (str != null) {
                            selectedSocietyId = str;
                         LiveData<List<MFPGoDowns>> listLiveData=  viewModel.getMFPGoDowns(selectedDivId, selectedSocietyId);
                         listLiveData.observe(MFPGoDownSelActivity.this, new Observer<List<MFPGoDowns>>() {
                                @Override
                                public void onChanged(List<MFPGoDowns> godownsList) {
                                    listLiveData.removeObservers(MFPGoDownSelActivity.this);
                                    if (godownsList != null && godownsList.size() > 0) {
                                        mfpGoDowns.add("-Select-");
                                        for (int i = 0; i < godownsList.size(); i++) {
                                            mfpGoDowns.add(godownsList.get(i).getGodownName());
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_spinner_dropdown_item, mfpGoDowns);
                                        binding.spMfp.setAdapter(adapter);
                                    }else {
                                        showSnackBar("No godowns found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedMfpGoDowns =null;
                selectedSocietyId = "";
                selectedMfpID = "";
                binding.spMfp.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_mfp) {
            if (position != 0) {
                selectedMfpGoDowns =null;
                selectedMfpID = "";
                LiveData<MFPGoDowns> liveData = viewModel.getMFPGoDownID(selectedDivId, selectedSocietyId, binding.spMfp.getSelectedItem().toString());
                liveData.observe(MFPGoDownSelActivity.this, new Observer<MFPGoDowns>() {
                    @Override
                    public void onChanged(MFPGoDowns mfps) {
                        liveData.removeObservers(MFPGoDownSelActivity.this);
                        if (mfps != null) {
                            selectedMfpID = mfps.getGodownId();
                            selectedMfpGoDowns = mfps;
                        }
                    }
                });
            } else {
                selectedMfpGoDowns =null;
                selectedMfpID = "";
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
