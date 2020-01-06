package com.example.twdinspection.schemes.ui;

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
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivitySchemesDmvBinding;
import com.example.twdinspection.inspection.ui.BaseActivity;
import com.example.twdinspection.schemes.source.finyear.FinancialYearsEntity;
import com.example.twdinspection.schemes.viewmodel.SchemesDMVViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SchemesDMVActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    SchemesDMVViewModel viewModel;
    ActivitySchemesDmvBinding schemesDMVActivityBinding;
    private Context context;
    String selectedDistId, selectedManId, selectedVilId;
    String selectedManName, selFinValue;
    String selectedFinYearId;
    ArrayList<String> villageNames;
    ArrayList<String> finYearValues;
    ArrayList<String> mandalNames;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<FinancialYearsEntity> finYearList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SchemesDMVActivity.this;

        schemesDMVActivityBinding = putContentView(R.layout.activity_schemes_dmv, getResources().getString(R.string.general_info));

        viewModel = new SchemesDMVViewModel(getApplication());
        schemesDMVActivityBinding.setViewModel(viewModel);
        schemesDMVActivityBinding.executePendingBindings();

        schemesDMVActivityBinding.ivCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchemesDMVActivity.this, SchemeSyncActivity.class));
            }
        });

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            schemesDMVActivityBinding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            schemesDMVActivityBinding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            String curTime = Utils.getCurrentDateTime();
            editor.putString(AppConstants.INSP_TIME, curTime);
            editor.commit();
            schemesDMVActivityBinding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mandalNames = new ArrayList<>();
        villageNames = new ArrayList<>();
        finYearValues = new ArrayList<>();
        finYearList = new ArrayList<>();



        viewModel.getFinancialYrs().observe(SchemesDMVActivity.this, new Observer<List<FinancialYearsEntity>>() {
            @Override
            public void onChanged(List<FinancialYearsEntity> institutesEntities) {
                finYearList.addAll(institutesEntities);
                if (finYearList != null && finYearList.size() > 0) {
                    finYearValues.add("-Select-");
                    for (int i = 0; i < finYearList.size(); i++) {
                        finYearValues.add(finYearList.get(i).getFinYear());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                            android.R.layout.simple_spinner_dropdown_item, finYearValues);
                    schemesDMVActivityBinding.spFinYr.setAdapter(adapter);
                }
            }
        });

        viewModel.getAllDistricts().observe(this, districts -> {
            if (districts != null && districts.size() > 0) {
                ArrayList<String> distNames = new ArrayList<>();
                distNames.add("-Select-");
                for (int i = 0; i < districts.size(); i++) {
                    distNames.add(districts.get(i).getDistName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_dropdown_item, distNames
                );
                schemesDMVActivityBinding.spDist.setAdapter(adapter);
            }
        });

        schemesDMVActivityBinding.spDist.setOnItemSelectedListener(this);
        schemesDMVActivityBinding.spMandal.setOnItemSelectedListener(this);
        schemesDMVActivityBinding.spVillage.setOnItemSelectedListener(this);
        schemesDMVActivityBinding.spFinYr.setOnItemSelectedListener(this);

        schemesDMVActivityBinding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (validateFields()) {
                    editor.putString(AppConstants.DIST_ID, selectedDistId);
                    editor.putString(AppConstants.MAN_ID, selectedManId);
                    editor.putString(AppConstants.VILL_ID, selectedVilId);
                    editor.putString(AppConstants.INST_ID, selectedFinYearId);
                    editor.putString(AppConstants.INST_NAME, selFinValue);
                    editor.putString(AppConstants.MAN_NAME, selectedManName);
                    editor.commit();
                    startActivity(new Intent(SchemesDMVActivity.this, BeneficiaryReportActivity.class));
                }
            }
        });
    }

    private boolean validateFields() {
        if (TextUtils.isEmpty(selectedDistId)) {
            showSnackBar("Please select district");
            return false;
        } else  if (TextUtils.isEmpty(selectedManId)) {
            showSnackBar("Please select mandal");
            return false;
        } else if (TextUtils.isEmpty(selectedVilId)) {
            showSnackBar("Please select village");
            return false;
        } else  if (TextUtils.isEmpty(selectedFinYearId)) {
            showSnackBar("Please select financial year");
            return false;
        }
        return true;
    }

    private void showSnackBar(String str) {
        Snackbar snackbar = Snackbar.make(schemesDMVActivityBinding.cl, str, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.sp_dist) {
            mandalNames.clear();
            mandalNames.add("-Select-");
            if (i != 0) {
                viewModel.getDistId(schemesDMVActivityBinding.spDist.getSelectedItem().toString()).observe(SchemesDMVActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String value) {
                        if (value != null) {
                            selectedDistId = value;
                            viewModel.getAllMandals(value).observe(SchemesDMVActivity.this, mandals -> {
                                if (mandals != null && mandals.size() > 0) {
                                    for (int i = 0; i < mandals.size(); i++) {
                                        mandalNames.add(mandals.get(i).getMandalName());
                                    }
                                }
                            });
                        }
                    }
                });
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_dropdown_item, mandalNames
                );
                schemesDMVActivityBinding.spMandal.setAdapter(adapter);
            } else {
                selectedDistId = "";
                selectedManId = "";
                selectedVilId = "";
                selectedManName = "";
                schemesDMVActivityBinding.spMandal.setAdapter(null);
                schemesDMVActivityBinding.spVillage.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_Mandal) {
            villageNames.clear();
            villageNames.add("-Select-");
            if (i != 0) {

                viewModel.getMandalId(schemesDMVActivityBinding.spMandal.getSelectedItem().toString(), selectedDistId).observe(SchemesDMVActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String value) {
                        if (value != null) {
                            selectedManId = value;
                            selectedManName = schemesDMVActivityBinding.spMandal.getSelectedItem().toString();
                            viewModel.getAllVillages(value, selectedDistId).observe(SchemesDMVActivity.this, villages -> {
                                if (villages != null && villages.size() > 0) {

                                    for (int i = 0; i < villages.size(); i++) {
                                        villageNames.add(villages.get(i).getVillageName());
                                    }

                                }
                            });
                        }
                    }
                });
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_dropdown_item, villageNames);
                schemesDMVActivityBinding.spVillage.setAdapter(adapter);
            } else {
                selectedManId = "";
                selectedVilId = "";
                selectedManName = "";
                schemesDMVActivityBinding.spVillage.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_village) {
            if (i != 0) {
                viewModel.getVillageId(schemesDMVActivityBinding.spVillage.getSelectedItem().toString(), selectedManId, selectedDistId).observe(SchemesDMVActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String value) {
                        if (value != null) {
                            selectedVilId = value;
                        }
                    }
                });

            } else {
                selectedVilId = "";
                selectedFinYearId = "";
            }
        } else if (adapterView.getId() == R.id.sp_fin_yr) {
            if (i != 0) {
                viewModel.getFinYearId(schemesDMVActivityBinding.spFinYr.getSelectedItem().toString()).observe(SchemesDMVActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String finYearId) {
                        if (finYearId != null) {
                            selFinValue = schemesDMVActivityBinding.spFinYr.getSelectedItem().toString();
                            selectedFinYearId = finYearId;
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

//        viewModel.getInspectionRemarks().observe(SchemesDMVActivity.this, new Observer<List<InspectionRemarksEntity>>() {
//            @Override
//            public void onChanged(List<InspectionRemarksEntity> inspectionRemarksEntities) {
//
//            }
//        });
}

