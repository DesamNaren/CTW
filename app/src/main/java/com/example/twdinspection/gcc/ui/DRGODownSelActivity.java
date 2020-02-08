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
import com.example.twdinspection.databinding.ActivityDrGodownSelBinding;
import com.example.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.example.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.example.twdinspection.inspection.viewmodel.DivisionSelectionViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DRGODownSelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    CustomProgressDialog customProgressDialog;
    ActivityDrGodownSelBinding binding;
    private Context context;
    private DivisionSelectionViewModel viewModel;
    private String selectedDivId, selectedSocietyId, selectedGoDownId;
    private List<DivisionsInfo> divisionsInfos;
    private List<String> societies;
    private List<String> drGodowns;
    private DrGodowns selectedDrGodowns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_godown_sel);
        context = DRGODownSelActivity.this;
        divisionsInfos = new ArrayList<>();
        societies = new ArrayList<>();
        drGodowns = new ArrayList<>();
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
            }
        });

        binding.spDivision.setOnItemSelectedListener(this);
        binding.spSociety.setOnItemSelectedListener(this);
        binding.spGodown.setOnItemSelectedListener(this);
        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    Gson gson = new Gson();
                    String goDownData = gson.toJson(selectedDrGodowns);
                    editor.putString(AppConstants.DR_GODOWN_DATA, goDownData);
                    editor.commit();

                    startActivity(new Intent(DRGODownSelActivity.this, DRGodownActivity.class));
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
        }else if (TextUtils.isEmpty(selectedGoDownId)) {
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
        if (adapterView.getId() == R.id.sp_division) {
            selectedDrGodowns=null;
            selectedSocietyId = "";
            selectedDivId = "";
            selectedGoDownId = "";
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
                                    }else{
                                        showSnackBar("No societies found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedDrGodowns=null;
                selectedDivId = "";
                selectedSocietyId = "";
                binding.spSociety.setAdapter(null);
                selectedGoDownId = "";
                binding.spGodown.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_society) {
            if (position != 0) {
                selectedDrGodowns=null;
                selectedSocietyId = "";
                selectedGoDownId = "";
                drGodowns = new ArrayList<>();
               LiveData<String> liveData = viewModel.getSocietyId(selectedDivId, binding.spSociety.getSelectedItem().toString());
               liveData.observe(DRGODownSelActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        liveData.removeObservers(DRGODownSelActivity.this);
                        if (str != null) {
                            selectedSocietyId = str;
                           LiveData<List<DrGodowns>>listLiveData = viewModel.getDRGoDowns(selectedDivId, selectedSocietyId);
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
                                    }else {
                                        showSnackBar("No godowns found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedDrGodowns=null;
                selectedSocietyId = "";
                selectedGoDownId = "";
                binding.spGodown.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_godown) {
            if (position != 0) {
                selectedDrGodowns=null;
                selectedGoDownId = "";
                LiveData<DrGodowns> drGodownsLiveData = viewModel.getGODownID(selectedDivId, selectedSocietyId, binding.spGodown.getSelectedItem().toString());
                drGodownsLiveData.observe(DRGODownSelActivity.this, new Observer<DrGodowns>() {
                    @Override
                    public void onChanged(DrGodowns drGodowns) {
                        if (drGodowns != null) {
                            selectedGoDownId = drGodowns.getGodownId();
                            selectedDrGodowns = drGodowns;
                        }
                    }
                });
            } else {
                selectedDrGodowns=null;
                selectedGoDownId = "";
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
