package com.cgg.twdinspection.schemes.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivitySchemesDmvBinding;
import com.cgg.twdinspection.inspection.ui.DashboardActivity;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.schemes.source.dmv.SchemeDistrict;
import com.cgg.twdinspection.schemes.source.dmv.SchemeMandal;
import com.cgg.twdinspection.schemes.source.dmv.SchemeVillage;
import com.cgg.twdinspection.schemes.source.finyear.FinancialYearsEntity;
import com.cgg.twdinspection.schemes.viewmodel.SchemesDMVViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SchemesDMVActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    SchemesDMVViewModel viewModel;
    ActivitySchemesDmvBinding schemesDMVActivityBinding;
    private Context context;
    String selectedDistId, selectedManId, selectedVilId;
    String selectedManName, selectedDistName, selectedVilName, selFinValue;
    String selectedFinYearId;
    ArrayList<String> villageNames;
    ArrayList<String> finYearValues;
    ArrayList<String> mandalNames;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<FinancialYearsEntity> finYearList;
    private String cacheDate, currentDate;
    InstMainViewModel instMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SchemesDMVActivity.this;

        schemesDMVActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_schemes_dmv);
        schemesDMVActivityBinding.header.headerTitle.setText(getResources().getString(R.string.general_info));
        schemesDMVActivityBinding.header.ivHome.setVisibility(View.GONE);
        instMainViewModel=new InstMainViewModel(getApplication());

        schemesDMVActivityBinding.header.syncIv.setVisibility(View.VISIBLE);
        schemesDMVActivityBinding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        schemesDMVActivityBinding.header.syncIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(SchemesDMVActivity.this,SchemeSyncActivity.class));
            }
        });

        viewModel = new SchemesDMVViewModel(getApplication());
        schemesDMVActivityBinding.setViewModel(viewModel);
        schemesDMVActivityBinding.executePendingBindings();


        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            schemesDMVActivityBinding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            schemesDMVActivityBinding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            String curTime = Utils.getCurrentDateTimeDisplay();
            editor.putString(AppConstants.INSP_TIME, curTime);
            editor.commit();
            schemesDMVActivityBinding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
        } catch (Exception e) {
            Toast.makeText(context, getString(R.string.something), Toast.LENGTH_SHORT).show();
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
                }else {
                    callSnackBar(getResources().getString(R.string.no_fin_year));
                }
            }
        });
        viewModel.getAllDistricts().observe(this, new Observer<List<SchemeDistrict>>() {
                    @Override
                    public void onChanged(List<SchemeDistrict> schemeDistricts) {
                        if (schemeDistricts != null && schemeDistricts.size() > 0) {
                            ArrayList<String> distNames = new ArrayList<>();
                            distNames.add("-Select-");
                            for (int i = 0; i < schemeDistricts.size(); i++) {
                                distNames.add(schemeDistricts.get(i).getDistName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                    android.R.layout.simple_spinner_dropdown_item, distNames
                            );
                            schemesDMVActivityBinding.spDist.setAdapter(adapter);
                        } else {
                            callSnackBar(getResources().getString(R.string.no_districts));
                        }
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
                    editor.putString(AppConstants.SCHEME_DIST_ID, selectedDistId);
                    editor.putString(AppConstants.SCHEME_MAN_ID, selectedManId);
                    editor.putString(AppConstants.SCHEME_VIL_ID, selectedVilId);
                    editor.putString(AppConstants.SCHEME_FIN_ID, selectedFinYearId);

                    editor.putString(AppConstants.SCHEME_DIST_NAME, selectedDistName);
                    editor.putString(AppConstants.SCHEME_MAN_NAME, selectedManName);
                    editor.putString(AppConstants.SCHEME_VIL_NAME, selectedVilName);
                    editor.putString(AppConstants.SCHEME_FIN_YEAR, selFinValue);

                    editor.commit();
                    startActivity(new Intent(SchemesDMVActivity.this, BeneficiaryReportActivity.class));
                }
            }
        });
    }

    private boolean validateFields() {
        if (TextUtils.isEmpty(selectedDistId)) {
            showSnackBar(getResources().getString(R.string.plz_sel_dis));
            return false;
        } else if (TextUtils.isEmpty(selectedManId)) {
            showSnackBar(getResources().getString(R.string.plz_sel_man));
            return false;
        } else if (TextUtils.isEmpty(selectedVilId)) {
            showSnackBar(getResources().getString(R.string.plz_sel_vil));
            return false;
        } else if (TextUtils.isEmpty(selectedFinYearId)) {
            showSnackBar(getResources().getString(R.string.plz_sel_fin));
            return false;
        }
        return true;
    }

    void callSnackBar(String msg){
        Snackbar snackbar = Snackbar.make(schemesDMVActivityBinding.cl, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

        snackbar.show();
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
                            selectedDistName = schemesDMVActivityBinding.spDist.getSelectedItem().toString();
                            viewModel.getAllMandals(value).observe(SchemesDMVActivity.this, new Observer<List<SchemeMandal>>() {
                                        @Override
                                        public void onChanged(List<SchemeMandal> schemeMandals) {
                                            if (schemeMandals != null && schemeMandals.size() > 0) {
                                                for (int i = 0; i < schemeMandals.size(); i++) {
                                                    mandalNames.add(schemeMandals.get(i).getMandalName());
                                                }
                                            } else {
                                                callSnackBar(getResources().getString(R.string.no_mandals));
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
                selectedDistName = "";
                selectedVilName = "";
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
                            viewModel.getAllVillages(value, selectedDistId).observe(SchemesDMVActivity.this, new Observer<List<SchemeVillage>>() {
                                        @Override
                                        public void onChanged(List<SchemeVillage> schemeVillages) {
                                            if (schemeVillages != null && schemeVillages.size() > 0) {
                                                for (int i = 0; i < schemeVillages.size(); i++) {
                                                    villageNames.add(schemeVillages.get(i).getVillageName());
                                                }
                                            } else {
                                                callSnackBar(getResources().getString(R.string.no_villages));
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
                selectedVilName = "";
                schemesDMVActivityBinding.spVillage.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_village) {
            if (i != 0) {
                viewModel.getVillageId(schemesDMVActivityBinding.spVillage.getSelectedItem().toString(), selectedManId, selectedDistId).observe(SchemesDMVActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String value) {
                        if (value != null) {
                            selectedVilName = schemesDMVActivityBinding.spVillage.getSelectedItem().toString();
                            selectedVilId = value;
                        }
                    }
                });

            } else {
                selectedVilId = "";
                selectedVilName = "";
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
            } else {
                selectedFinYearId = "";
                selFinValue = "";
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            boolean isAutomatic = Utils.isTimeAutomatic(this);
            if (!isAutomatic) {
                Utils.customTimeAlert(this,
                        getResources().getString(R.string.app_name),
                        getString(R.string.date_time));
                return;
            }

            currentDate = Utils.getCurrentDate();
            cacheDate = sharedPreferences.getString(AppConstants.CACHE_DATE, "");

            if (!TextUtils.isEmpty(cacheDate)) {
                if (!cacheDate.equalsIgnoreCase(currentDate)) {

                    Utils.ShowDeviceSessionAlert(this,
                            getResources().getString(R.string.app_name),
                            getString(R.string.ses_expire_re), instMainViewModel);
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cacheDate = currentDate;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.CACHE_DATE, cacheDate);
        editor.commit();
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, DashboardActivity.class)
        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}

