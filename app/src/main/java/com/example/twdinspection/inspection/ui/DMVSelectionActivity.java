package com.example.twdinspection.inspection.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.lifecycle.Observer;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.databinding.DmvSelectionActivityBinding;
import com.example.twdinspection.inspection.source.GeneralInformation.InstitutesEntity;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.inspection.viewmodel.DMVDetailsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DMVSelectionActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    DMVDetailsViewModel viewModel;
    DmvSelectionActivityBinding dmvSelectionActivityBinding;
    private Context context;
    int selectedDistId, selectedManId, selectedVilId;
    String selectedInstId, selectedManName, selInstName;
    ArrayList<String> villageNames;
    ArrayList<String> instNames;
    ArrayList<String> mandalNames;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<InstitutesEntity> institutesEntityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = DMVSelectionActivity.this;

        dmvSelectionActivityBinding = putContentView(R.layout.dmv_selection_activity, getResources().getString(R.string.general_info));

        viewModel = new DMVDetailsViewModel(getApplication());
        dmvSelectionActivityBinding.setViewModel(viewModel);
        dmvSelectionActivityBinding.executePendingBindings();

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            dmvSelectionActivityBinding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            dmvSelectionActivityBinding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            String curTime = Utils.getCurrentDateTime();
            editor.putString(AppConstants.INSP_TIME, curTime);
            editor.commit();
            dmvSelectionActivityBinding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mandalNames = new ArrayList<>();
        villageNames = new ArrayList<>();
        instNames = new ArrayList<>();
        institutesEntityList = new ArrayList<>();
        viewModel.getAllDistricts().observe(this, districts -> {
            if (districts != null && districts.size() > 0) {
                ArrayList<String> distNames = new ArrayList<>();
                distNames.add("-Select-");
                for (int i = 0; i < districts.size(); i++) {
                    distNames.add(districts.get(i).getDist_name());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_dropdown_item, distNames
                );
                dmvSelectionActivityBinding.spDist.setAdapter(adapter);
            }
        });

        dmvSelectionActivityBinding.spDist.setOnItemSelectedListener(this);
        dmvSelectionActivityBinding.spMandal.setOnItemSelectedListener(this);
        dmvSelectionActivityBinding.spVillage.setOnItemSelectedListener(this);
        dmvSelectionActivityBinding.spInstitution.setOnItemSelectedListener(this);
        dmvSelectionActivityBinding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    editor.putInt(AppConstants.DIST_ID, selectedDistId);
                    editor.putInt(AppConstants.MAN_ID, selectedManId);
                    editor.putInt(AppConstants.VILL_ID, selectedVilId);
                    editor.putString(AppConstants.INST_ID, selectedInstId);
                    editor.putString(AppConstants.INST_NAME, selInstName);
                    editor.putString(AppConstants.MAN_NAME, selectedManName);
                    editor.commit();
                    startActivity(new Intent(DMVSelectionActivity.this, GeneralInfoActivity.class));
                    finish();
                }
            }
        });
    }

    private boolean validateFields() {


        if (selectedDistId == 0) {
            showSnackBar("Please select district");
            return false;
        } else if (selectedManId == 0) {
            showSnackBar("Please select mandal");
            return false;
        } else if (selectedVilId == 0) {
            showSnackBar("Please select village");
            return false;
        } else if (TextUtils.isEmpty(selectedInstId)) {
            showSnackBar("Please select institute");
            return false;
        }
        return true;
    }

    private void showSnackBar(String str) {
        Snackbar snackbar = Snackbar.make(dmvSelectionActivityBinding.cl, str, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.sp_dist) {
            mandalNames.clear();
            mandalNames.add("-Select-");
            if (i != 0) {
                viewModel.getDistId(dmvSelectionActivityBinding.spDist.getSelectedItem().toString()).observe(DMVSelectionActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer != null) {
                            selectedDistId = integer;
                            viewModel.getAllMandals(integer).observe(DMVSelectionActivity.this, mandals -> {
                                if (mandals != null && mandals.size() > 0) {
                                    for (int i = 0; i < mandals.size(); i++) {
                                        mandalNames.add(mandals.get(i).getMandal_name());
                                    }
                                }
                            });
                        }
                    }
                });
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_dropdown_item, mandalNames
                );
                dmvSelectionActivityBinding.spMandal.setAdapter(adapter);
            } else {
                selectedDistId = 0;
                selectedManId = 0;
                selectedVilId = 0;
                selectedManName = "";
                selInstName = "";
                dmvSelectionActivityBinding.spMandal.setAdapter(null);
                dmvSelectionActivityBinding.spVillage.setAdapter(null);
                dmvSelectionActivityBinding.spInstitution.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_Mandal) {
            villageNames.clear();
            villageNames.add("-Select-");
            if (i != 0) {

                viewModel.getMandalId(dmvSelectionActivityBinding.spMandal.getSelectedItem().toString(), selectedDistId).observe(DMVSelectionActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer != null) {
                            selectedManId = integer;
                            selectedManName = dmvSelectionActivityBinding.spMandal.getSelectedItem().toString();
                            viewModel.getAllVillages(integer, selectedDistId).observe(DMVSelectionActivity.this, villages -> {
                                if (villages != null && villages.size() > 0) {

                                    for (int i = 0; i < villages.size(); i++) {
                                        villageNames.add(villages.get(i).getVillage_name());
                                    }

                                }
                            });
                        }
                    }
                });
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_dropdown_item, villageNames);
                dmvSelectionActivityBinding.spVillage.setAdapter(adapter);
            } else {
                selectedManId = 0;
                selectedVilId = 0;
                selectedManName = "";
                selInstName = "";
                dmvSelectionActivityBinding.spVillage.setAdapter(null);
                dmvSelectionActivityBinding.spInstitution.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_village) {
            instNames.clear();
            instNames.add("-Select-");
            if (i != 0) {

                viewModel.getVillageId(dmvSelectionActivityBinding.spVillage.getSelectedItem().toString(), selectedManId, selectedDistId).observe(DMVSelectionActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer != null) {
                            selectedVilId = integer;
                        }
                    }
                });
                viewModel.getInstitutes().observe(DMVSelectionActivity.this, new Observer<List<InstitutesEntity>>() {
                    @Override
                    public void onChanged(List<InstitutesEntity> institutesEntities) {
                        institutesEntityList.addAll(institutesEntities);
                        if (institutesEntityList != null && institutesEntityList.size() > 0) {
                            for (int i = 0; i < institutesEntityList.size(); i++) {
                                instNames.add(institutesEntityList.get(i).getInst_Name());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                    android.R.layout.simple_spinner_dropdown_item, instNames);
                            dmvSelectionActivityBinding.spInstitution.setAdapter(adapter);
                        }
                    }
                });
            } else {
                selectedVilId = 0;
                selectedInstId = "";
                selInstName = "";
                dmvSelectionActivityBinding.spInstitution.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_institution) {
            if (i != 0) {
                viewModel.getInstId(dmvSelectionActivityBinding.spInstitution.getSelectedItem().toString()).observe(DMVSelectionActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String inst_id) {
                        if (inst_id != null) {
                            selInstName = dmvSelectionActivityBinding.spInstitution.getSelectedItem().toString();
                            selectedInstId = inst_id;
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
