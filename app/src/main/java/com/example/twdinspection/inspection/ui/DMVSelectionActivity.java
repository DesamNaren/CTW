package com.example.twdinspection.inspection.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.DmvSelectionActivityBinding;
import com.example.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.example.twdinspection.common.utils.CustomProgressDialog;
import com.example.twdinspection.inspection.viewmodel.DMVDetailsViewModel;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DMVSelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DMVDetailsViewModel viewModel;
    DmvSelectionActivityBinding dmvSelectionActivityBinding;
    private Context context;
    int selectedDistId, selectedManId, selectedVilId;
    String selectedInstId, selectedManName, selInstName, selectedVilName, selectedAddress;
    String lat, lng, address;
    ArrayList<String> instNames;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<MasterInstituteInfo> institutesEntityList;
    CustomProgressDialog customProgressDialog;
    private String cacheDate, currentDate;
    InstMainViewModel instMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = DMVSelectionActivity.this;
        customProgressDialog = new CustomProgressDialog(context);
        customProgressDialog.show();
        dmvSelectionActivityBinding = DataBindingUtil.setContentView(this, R.layout.dmv_selection_activity);
        dmvSelectionActivityBinding.header.syncIv.setVisibility(View.VISIBLE);
        dmvSelectionActivityBinding.header.headerTitle.setText(getResources().getString(R.string.general_info));
        instMainViewModel=new InstMainViewModel(getApplication());

        dmvSelectionActivityBinding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dmvSelectionActivityBinding.header.ivHome.setVisibility(View.GONE);

        viewModel = new DMVDetailsViewModel(getApplication());
        dmvSelectionActivityBinding.setViewModel(viewModel);
        dmvSelectionActivityBinding.executePendingBindings();

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            dmvSelectionActivityBinding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            dmvSelectionActivityBinding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            String curTime = Utils.getCurrentDateTimeDisplay();
            editor.putString(AppConstants.INSP_TIME, curTime);
            editor.commit();
            dmvSelectionActivityBinding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        instNames = new ArrayList<>();
        institutesEntityList = new ArrayList<>();
        viewModel.getAllDistricts().observe(this, districts -> {
            customProgressDialog.dismiss();
            if (districts != null && districts.size() > 0) {
                ArrayList<String> distNames = new ArrayList<>();
                distNames.add("-Select-");
                for (int i = 0; i < districts.size(); i++) {
                    distNames.add(districts.get(i).getDistName());
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
                    editor.putString(AppConstants.LAT, lat);
                    editor.putString(AppConstants.LNG, lng);
                    editor.putString(AppConstants.ADDRESS, address);
                    editor.commit();
                    startActivity(new Intent(DMVSelectionActivity.this, InstMenuMainActivity.class));
                    finish();
                }
            }
        });

        dmvSelectionActivityBinding.header.syncIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DMVSelectionActivity.this, SchoolSyncActivity.class));
            }
        });
    }

    private boolean validateFields() {
        if (selectedDistId == 0) {
            showSnackBar("Please select district");
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
            instNames = new ArrayList<>();
            institutesEntityList = new ArrayList<>();
            instNames.add("--Select--");
            if (i != 0) {
                viewModel.getDistId(dmvSelectionActivityBinding.spDist.getSelectedItem().toString()).observe(DMVSelectionActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        if (str != null) {
                            selectedDistId = Integer.valueOf(str);

                            viewModel.getInstitutes(selectedDistId).observe(DMVSelectionActivity.this, new Observer<List<MasterInstituteInfo>>() {
                                @Override
                                public void onChanged(List<MasterInstituteInfo> institutesEntities) {

                                    institutesEntityList.addAll(institutesEntities);
                                    if (institutesEntityList != null && institutesEntityList.size() > 0) {
                                        for (int i = 0; i < institutesEntityList.size(); i++) {
                                            instNames.add(institutesEntityList.get(i).getInstName());
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_spinner_dropdown_item, instNames);
                                        dmvSelectionActivityBinding.spInstitution.setAdapter(adapter);
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedDistId = 0;
                selInstName = "";
                dmvSelectionActivityBinding.mandal.setText("");
                dmvSelectionActivityBinding.village.setText("");
                dmvSelectionActivityBinding.address.setText("");
                dmvSelectionActivityBinding.spInstitution.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_institution) {
            customProgressDialog.show();
            if (i != 0) {
                viewModel.getInstId(dmvSelectionActivityBinding.spInstitution.getSelectedItem().toString()).observe(DMVSelectionActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer inst_id) {
                        if (inst_id != null) {
                            selInstName = dmvSelectionActivityBinding.spInstitution.getSelectedItem().toString();
                            selectedInstId = String.valueOf(inst_id);

                            viewModel.getInstituteInfo(selectedInstId).observe(DMVSelectionActivity.this, new Observer<MasterInstituteInfo>() {
                                @Override
                                public void onChanged(MasterInstituteInfo str) {
                                    customProgressDialog.dismiss();

                                    if (str != null) {
                                        selectedManId = str.getMandalId();
                                        selectedManName = str.getMandalName();
                                        if (selectedManName != null)
                                            dmvSelectionActivityBinding.mandal.setText("Mandal : " + selectedManName);

                                        selectedVilId = str.getVillageId();
                                        selectedVilName = str.getVillageName();
                                        if (selectedVilName != null)
                                            dmvSelectionActivityBinding.village.setText("Village : " + selectedVilName);

                                        selectedAddress = str.getAddress();
                                        if (selectedAddress != null)
                                            dmvSelectionActivityBinding.address.setText("Address : " + selectedAddress);
                                        lat = str.getLatitude();
                                        lng = str.getLongitude();
                                        address = str.getAddress();
                                    }
                                }
                            });

                        }
                    }
                });
            } else {
                customProgressDialog.dismiss();
                dmvSelectionActivityBinding.mandal.setText("");
                dmvSelectionActivityBinding.village.setText("");
                dmvSelectionActivityBinding.address.setText("");
                selectedInstId = "";
                selInstName = "";
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
                    instMainViewModel.deleteAllInspectionData();
                    Utils.ShowDeviceSessionAlert(this,
                            getResources().getString(R.string.app_name),
                            getString(R.string.ses_expire_re));
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
}
