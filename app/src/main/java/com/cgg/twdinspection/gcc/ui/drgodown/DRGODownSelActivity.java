package com.cgg.twdinspection.gcc.ui.drgodown;

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
import com.cgg.twdinspection.databinding.ActivityDrGodownSelBinding;
import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.cgg.twdinspection.inspection.ui.DashboardActivity;
import com.cgg.twdinspection.inspection.viewmodel.DivisionSelectionViewModel;
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
    ArrayAdapter<String> selectAdapter;

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
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DRGODownSelActivity.this, DashboardActivity.class)
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
                        } else {
                            showSnackBar(getString(R.string.something));
                        }
                    }
                });
            } else {
                selectedDrGodowns = null;
                selectedGoDownId = "";
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
