package com.example.twdinspection.schemes.ui;

import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivitySchemesDmvBinding;
import com.example.twdinspection.inspection.ui.BaseActivity;
import com.example.twdinspection.schemes.source.FinancialYrsEntity;
import com.example.twdinspection.schemes.source.InspectionRemarksEntity;
import com.example.twdinspection.schemes.viewmodel.SchemesDMVViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import afu.org.checkerframework.checker.oigj.qual.O;

public class SchemesDMVActivity extends BaseActivity {

    SchemesDMVViewModel viewModel;
    ActivitySchemesDmvBinding schemesDMVActivityBinding;
    private Context context;
    int selectedDistId, selectedManId, selectedVilId;
    String selectedInstId, selectedManName, selInstName;
    ArrayList<String> villageNames;
    ArrayList<String> finYearvalues;
    ArrayList<String> mandalNames;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<FinancialYrsEntity> institutesEntityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SchemesDMVActivity.this;

        schemesDMVActivityBinding = putContentView(R.layout.activity_schemes_dmv, getResources().getString(R.string.general_info));

        viewModel = new SchemesDMVViewModel(getApplication());
        schemesDMVActivityBinding.setViewModel(viewModel);
        schemesDMVActivityBinding.executePendingBindings();

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
        finYearvalues = new ArrayList<>();
        institutesEntityList = new ArrayList<>();

        viewModel.getFinancialYrs().observe(SchemesDMVActivity.this, new Observer<List<FinancialYrsEntity>>() {
            @Override
            public void onChanged(List<FinancialYrsEntity> institutesEntities) {
                institutesEntityList.addAll(institutesEntities);
                if (institutesEntityList != null && institutesEntityList.size() > 0) {
                    for (int i = 0; i < institutesEntityList.size(); i++) {
                        finYearvalues.add(institutesEntityList.get(i).getFin_year());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                            android.R.layout.simple_spinner_dropdown_item, finYearvalues);
                    schemesDMVActivityBinding.spFinYr.setAdapter(adapter);
                }
            }
        });

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
                schemesDMVActivityBinding.spDist.setAdapter(adapter);
            }
        });
        viewModel.getInspectionRemarks().observe(SchemesDMVActivity.this, new Observer<List<InspectionRemarksEntity>>() {
            @Override
            public void onChanged(List<InspectionRemarksEntity> inspectionRemarksEntities) {

            }
        });
//
//        schemesDMVActivityBinding.spDist.setOnItemSelectedListener(this);
//        schemesDMVActivityBinding.spMandal.setOnItemSelectedListener(this);
//        schemesDMVActivityBinding.spVillage.setOnItemSelectedListener(this);
//        schemesDMVActivityBinding.spFinYr.setOnItemSelectedListener(this);
        schemesDMVActivityBinding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (validateFields()) {
//                    editor.putInt(AppConstants.DIST_ID, selectedDistId);
//                    editor.putInt(AppConstants.MAN_ID, selectedManId);
//                    editor.putInt(AppConstants.VILL_ID, selectedVilId);
//                    editor.putString(AppConstants.INST_ID, selectedInstId);
//                    editor.putString(AppConstants.INST_NAME, selInstName);
//                    editor.putString(AppConstants.MAN_NAME, selectedManName);
//                    editor.commit();
                    startActivity(new Intent(SchemesDMVActivity.this, BeneficiaryReportActivity.class));
//                }
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
        Snackbar snackbar = Snackbar.make(schemesDMVActivityBinding.cl, str, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        if (adapterView.getId() == R.id.sp_dist) {
//            mandalNames.clear();
//            mandalNames.add("-Select-");
//            if (i != 0) {
//                viewModel.getDistId(schemesDMVActivityBinding.spDist.getSelectedItem().toString()).observe(SchemesDMVActivity.this, new Observer<Integer>() {
//                    @Override
//                    public void onChanged(Integer integer) {
//                        if (integer != null) {
//                            selectedDistId = integer;
//                            viewModel.getAllMandals(integer).observe(SchemesDMVActivity.this, mandals -> {
//                                if (mandals != null && mandals.size() > 0) {
//                                    for (int i = 0; i < mandals.size(); i++) {
//                                        mandalNames.add(mandals.get(i).getMandal_name());
//                                    }
//                                }
//                            });
//                        }
//                    }
//                });
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
//                        android.R.layout.simple_spinner_dropdown_item, mandalNames
//                );
//                schemesDMVActivityBinding.spMandal.setAdapter(adapter);
//            } else {
//                selectedDistId = 0;
//                selectedManId = 0;
//                selectedVilId = 0;
//                selectedManName = "";
//                selInstName = "";
//                schemesDMVActivityBinding.spMandal.setAdapter(null);
//                schemesDMVActivityBinding.spVillage.setAdapter(null);
//                schemesDMVActivityBinding.spFinYr.setAdapter(null);
//            }
//        } else if (adapterView.getId() == R.id.sp_Mandal) {
//            villageNames.clear();
//            villageNames.add("-Select-");
//            if (i != 0) {
//
//                viewModel.getMandalId(schemesDMVActivityBinding.spMandal.getSelectedItem().toString(), selectedDistId).observe(SchemesDMVActivity.this, new Observer<Integer>() {
//                    @Override
//                    public void onChanged(Integer integer) {
//                        if (integer != null) {
//                            selectedManId = integer;
//                            selectedManName = schemesDMVActivityBinding.spMandal.getSelectedItem().toString();
//                            viewModel.getAllVillages(integer, selectedDistId).observe(SchemesDMVActivity.this, villages -> {
//                                if (villages != null && villages.size() > 0) {
//
//                                    for (int i = 0; i < villages.size(); i++) {
//                                        villageNames.add(villages.get(i).getVillage_name());
//                                    }
//
//                                }
//                            });
//                        }
//                    }
//                });
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
//                        android.R.layout.simple_spinner_dropdown_item, villageNames);
//                schemesDMVActivityBinding.spVillage.setAdapter(adapter);
//            } else {
//                selectedManId = 0;
//                selectedVilId = 0;
//                selectedManName = "";
//                selInstName = "";
//                schemesDMVActivityBinding.spVillage.setAdapter(null);
//                schemesDMVActivityBinding.spFinYr.setAdapter(null);
//            }
//        } else if (adapterView.getId() == R.id.sp_village) {
//            finYearvalues.clear();
//            finYearvalues.add("-Select-");
//            if (i != 0) {
//
//                viewModel.getVillageId(schemesDMVActivityBinding.spVillage.getSelectedItem().toString(), selectedManId, selectedDistId).observe(SchemesDMVActivity.this, new Observer<Integer>() {
//                    @Override
//                    public void onChanged(Integer integer) {
//                        if (integer != null) {
//                            selectedVilId = integer;
//                        }
//                    }
//                });
//
//            } else {
//                selectedVilId = 0;
//                selectedInstId = "";
//                selInstName = "";
//                schemesDMVActivityBinding.spFinYr.setAdapter(null);
//            }
//        } else if (adapterView.getId() == R.id.sp_institution) {
//            if (i != 0) {
//                viewModel.getInstId(schemesDMVActivityBinding.spFinYr.getSelectedItem().toString()).observe(SchemesDMVActivity.this, new Observer<String>() {
//                    @Override
//                    public void onChanged(String inst_id) {
//                        if (inst_id != null) {
//                            selInstName = schemesDMVActivityBinding.spFinYr.getSelectedItem().toString();
//                            selectedInstId = inst_id;
//                        }
//                    }
//                });
//            }
//        }
//    }

//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
}
