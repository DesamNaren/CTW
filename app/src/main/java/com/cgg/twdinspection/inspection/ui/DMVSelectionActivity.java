package com.cgg.twdinspection.inspection.ui;

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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.DmvSelectionActivityBinding;
import com.cgg.twdinspection.gcc.ui.drdepot.DRDepotSelActivity;
import com.cgg.twdinspection.inspection.interfaces.InstSelInterface;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstSelectionInfo;
import com.cgg.twdinspection.inspection.source.dmv.SchoolDistrict;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.viewmodel.DMVDetailsViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstSelectionViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DMVSelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, InstSelInterface {
    DMVDetailsViewModel viewModel;
    DmvSelectionActivityBinding dmvSelectionActivityBinding;
    private Context context;
    int selectedDistId, selectedManId, selectedVilId;
    String selectedInstId, selectedManName, selInstName, selectedVilName, selectedDistName, selectedAddress;
    String lat, lng, address;
    ArrayList<String> instNames;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<MasterInstituteInfo> institutesEntityList;
    CustomProgressDialog customProgressDialog;
    private String cacheDate, currentDate;
    InstMainViewModel instMainViewModel;
    private InstSelectionViewModel selectionViewModel;
    ArrayAdapter selectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = DMVSelectionActivity.this;
        customProgressDialog = new CustomProgressDialog(context);
        customProgressDialog.show();
        dmvSelectionActivityBinding = DataBindingUtil.setContentView(this, R.layout.dmv_selection_activity);
        dmvSelectionActivityBinding.header.syncIv.setVisibility(View.VISIBLE);
        dmvSelectionActivityBinding.header.headerTitle.setText("Institute Inspection");
        instMainViewModel = new InstMainViewModel(getApplication());
        selectionViewModel = new InstSelectionViewModel(getApplication());

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

        ArrayList selectList=new ArrayList();
        selectList.add("Select");
        selectAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, selectList);

        instNames = new ArrayList<>();
        institutesEntityList = new ArrayList<>();
        viewModel.getAllDistricts().observe(this, new Observer<List<SchoolDistrict>>() {
            @Override
            public void onChanged(List<SchoolDistrict> schoolDistricts) {
                customProgressDialog.dismiss();
                if (schoolDistricts != null && schoolDistricts.size() > 0) {
                    ArrayList<String> distNames = new ArrayList<>();
                    distNames.add("-Select-");
                    for (int i = 0; i < schoolDistricts.size(); i++) {
                        distNames.add(schoolDistricts.get(i).getDistName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                            android.R.layout.simple_spinner_dropdown_item, distNames
                    );
                    dmvSelectionActivityBinding.spDist.setAdapter(adapter);
                }else{
                    Utils.customSchoolSyncAlert(DMVSelectionActivity.this,getString(R.string.app_name),"No districts found...\n Do you want to sync district master?");
                }
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

                    InstSelectionInfo instSelectionInfo = new InstSelectionInfo(selectedInstId,
                            selInstName,
                            String.valueOf(selectedDistId),  String.valueOf(selectedManId),  String.valueOf(selectedVilId),
                            selectedDistName, selectedManName, selectedVilName, lat, lng, address);

                    selectionViewModel.insertInstitutes(DMVSelectionActivity.this,instSelectionInfo);
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
                            selectedDistName = dmvSelectionActivityBinding.spDist.getSelectedItem().toString();
                            selectedDistId = Integer.valueOf(str);
                                dmvSelectionActivityBinding.mandal.setText("");
                                dmvSelectionActivityBinding.village.setText("");
                                dmvSelectionActivityBinding.address.setText("");
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
                                    }else{
                                        dmvSelectionActivityBinding.spInstitution.setAdapter(selectAdapter);
                                        showSnackBar("No institutes found");

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
                dmvSelectionActivityBinding.spInstitution.setAdapter(selectAdapter);
            }
        } else if (adapterView.getId() == R.id.sp_institution) {

            dmvSelectionActivityBinding.mandal.setText("");
            dmvSelectionActivityBinding.village.setText("");
            dmvSelectionActivityBinding.address.setText("");
            customProgressDialog.show();
            if (i != 0) {
                viewModel.getInstId(dmvSelectionActivityBinding.spInstitution.getSelectedItem().toString()
                        , selectedDistId).observe(DMVSelectionActivity.this, new Observer<Integer>() {
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

    @Override
    public void getCount(int cnt) {
        if(cnt!=-1){
            LiveData<InstSelectionInfo> liveData =  selectionViewModel.getSelectedInst();
            liveData.observe(DMVSelectionActivity.this, new Observer<InstSelectionInfo>() {
                @Override
                public void onChanged(InstSelectionInfo instSelectionInfo) {
                    liveData.removeObservers(DMVSelectionActivity.this);
                    if(instSelectionInfo!=null){
                        editor.putInt(AppConstants.DIST_ID, Integer.valueOf(instSelectionInfo.getDist_id()));
                        editor.putInt(AppConstants.MAN_ID, Integer.valueOf(instSelectionInfo.getMan_id()));
                        editor.putInt(AppConstants.VILL_ID, Integer.valueOf(instSelectionInfo.getVil_id()));
                        editor.putString(AppConstants.INST_ID, instSelectionInfo.getInst_id());
                        editor.putString(AppConstants.INST_NAME, instSelectionInfo.getInst_name());
                        editor.putString(AppConstants.DIST_NAME, instSelectionInfo.getDist_name());
                        editor.putString(AppConstants.MAN_NAME, instSelectionInfo.getMan_name());
                        editor.putString(AppConstants.VIL_NAME, instSelectionInfo.getVil_name());
                        editor.putString(AppConstants.LAT, instSelectionInfo.getInst_lat());
                        editor.putString(AppConstants.LNG, instSelectionInfo.getInst_lng());
                        editor.putString(AppConstants.ADDRESS, instSelectionInfo.getInst_address());
                        editor.commit();

                        if(!TextUtils.isEmpty(instSelectionInfo.getInst_lat()) && Double.valueOf(instSelectionInfo.getInst_lat())>0.0
                                && !TextUtils.isEmpty(instSelectionInfo.getInst_lng()) && Double.valueOf(instSelectionInfo.getInst_lng())>0.0){
                            startActivity(new Intent(DMVSelectionActivity.this, InstMenuMainActivity.class));
                            finish();
                        }else {
                            Utils.customHomeAlert(DMVSelectionActivity.this, getString(R.string.app_name), getString(R.string.inst_loc));
                        }

                    }
                }
            });
        }else{
            Toast.makeText(context, "Something went wrong..Please try again", Toast.LENGTH_SHORT).show();
        }
    }
}
