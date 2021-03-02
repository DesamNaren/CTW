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
import com.cgg.twdinspection.inspection.interfaces.InstSelInterface;
import com.cgg.twdinspection.inspection.offline.SchoolsOfflineEntity;
import com.cgg.twdinspection.inspection.source.diet_issues.MasterDietListInfo;
import com.cgg.twdinspection.inspection.source.dmv.SchoolDistrict;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstLatestTimeInfo;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstSelectionInfo;
import com.cgg.twdinspection.inspection.viewmodel.DMVDetailsViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstSelectionViewModel;
import com.cgg.twdinspection.inspection.viewmodel.SchoolsOfflineViewModel;
import com.cgg.twdinspection.offline.SchoolsOfflineDataActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
    InstMainViewModel instMainViewModel;
    SchoolsOfflineViewModel schoolsOfflineViewModel;
    private InstSelectionViewModel selectionViewModel;
    private ArrayAdapter<String> selectAdapter;
    private long loginDistId, roleId;
    public static final String IMAGE_DIRECTORY_NAME = "SCHOOL_INSP_IMAGES";
    private String timer;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DMVSelectionActivity.this, DashboardMenuActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = DMVSelectionActivity.this;
        customProgressDialog = new CustomProgressDialog(context);
        customProgressDialog.show();
        dmvSelectionActivityBinding = DataBindingUtil.setContentView(this, R.layout.dmv_selection_activity);
        dmvSelectionActivityBinding.header.syncIv.setVisibility(View.VISIBLE);
        dmvSelectionActivityBinding.header.headerTitle.setText(getString(R.string.institute_inspection));
        instMainViewModel = new InstMainViewModel(getApplication());
        schoolsOfflineViewModel = new SchoolsOfflineViewModel(getApplication());
        selectionViewModel = new InstSelectionViewModel(getApplication());

        timer = AppConstants.TIMER;

        dmvSelectionActivityBinding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dmvSelectionActivityBinding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DMVSelectionActivity.this, DashboardMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

            }
        });


        LiveData<List<SchoolsOfflineEntity>> listLiveData = schoolsOfflineViewModel.getSchoolsOffline();
        listLiveData.observe(DMVSelectionActivity.this, new Observer<List<SchoolsOfflineEntity>>() {
            @Override
            public void onChanged(List<SchoolsOfflineEntity> offlineEntities) {
                listLiveData.removeObservers(DMVSelectionActivity.this);
                if (offlineEntities != null && offlineEntities.size() > 0) {
                    List<String> offlineInsts = new ArrayList<>();
                    for (int x = 0; x < offlineEntities.size(); x++) {
                        String offlineTIme = offlineEntities.get(x).getInst_time();
                        String curTime = Utils.getOfflineTime();
                        //Compare time diff
                        //if diff>48 then take each inst id and remove all tables

                        Date offlineDate = Utils.strToDate(offlineTIme);
                        Date curDate = Utils.strToDate(curTime);

                        long millis = curDate.getTime() - offlineDate.getTime();
                        int hours = (int) (millis / (1000 * 60 * 60));

                        if (!TextUtils.isEmpty(timer) && hours > Integer.parseInt(timer)) {
                            offlineInsts.add(offlineEntities.get(x).getInst_id());
                        }
                    }

                    if (offlineInsts != null && offlineInsts.size() > 0) {
                        for (int i = 0; i < offlineInsts.size(); i++) {

                            File mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME
                                    + "/" + offlineEntities.get(i).getInst_id());

                            if (mediaStorageDir.isDirectory()) {
                                String[] children = mediaStorageDir.list();
                                for (String child : children)
                                    new File(mediaStorageDir, child).delete();
                                mediaStorageDir.delete();
                            }

                            schoolsOfflineViewModel.deleteSchoolsRecord(offlineInsts.get(i));
                            instMainViewModel.deleteAllInspectionData(offlineInsts.get(i));
                        }
                    }
                }
            }
        });
        InstSelectionViewModel instSelectionViewModel = new InstSelectionViewModel(getApplication());
        LiveData<List<InstLatestTimeInfo>> timelistLiveData = instSelectionViewModel.getLatestTimeInfo();
        timelistLiveData.observe(DMVSelectionActivity.this, new Observer<List<InstLatestTimeInfo>>() {
            @Override
            public void onChanged(List<InstLatestTimeInfo> instLatestTimeInfos) {
                timelistLiveData.removeObservers(DMVSelectionActivity.this);
                if (instLatestTimeInfos != null && instLatestTimeInfos.size() > 0) {
                    List<String> offlineInsts = new ArrayList<>();
                    for (int x = 0; x < instLatestTimeInfos.size(); x++) {
                        String offlineTIme = instLatestTimeInfos.get(x).getInst_time();
                        String curTime = Utils.getOfflineTime();
                        //Compare time diff
                        //if diff>48 then take each inst id and remove all tables
                        if (offlineTIme != null) {
                            Date offlineDate = Utils.strToDate(offlineTIme);
                            Date curDate = Utils.strToDate(curTime);

                            long millis = curDate.getTime() - offlineDate.getTime();
                            int hours = (int) (millis / (1000 * 60 * 60));

                            if (!TextUtils.isEmpty(timer) && hours > Integer.parseInt(timer)) {
                                offlineInsts.add(instLatestTimeInfos.get(x).getInst_id());
                            }
                        }
                    }

                    if (offlineInsts != null && offlineInsts.size() > 0) {
                        for (int i = 0; i < offlineInsts.size(); i++) {


                            File mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME
                                    + "/" + instLatestTimeInfos.get(i).getInst_id());

                            if (mediaStorageDir.isDirectory()) {
                                String[] children = mediaStorageDir.list();
                                for (String child : children)
                                    new File(mediaStorageDir, child).delete();
                                mediaStorageDir.delete();
                            }

                            instMainViewModel.deleteAllInspectionData(offlineInsts.get(i));
                            instSelectionViewModel.deleteTimeInfo(offlineInsts.get(i));
                        }
                    }
                }
            }
        });

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
            loginDistId = sharedPreferences.getLong(AppConstants.LOGIN_DIST_ID, -1);
            roleId = sharedPreferences.getLong(AppConstants.ROLEID, -1);
            dmvSelectionActivityBinding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> selectList = new ArrayList<String>();
        selectList.add("-Select-");
        selectAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, selectList);

        instNames = new ArrayList<>();
        institutesEntityList = new ArrayList<>();

        if (!TextUtils.isEmpty(String.valueOf(roleId)) && roleId != 3) {
            viewModel.getSelectedDistricts(String.valueOf(loginDistId)).observe(this, new Observer<List<SchoolDistrict>>() {
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

                        viewModel.getAllInstitutes().observe(DMVSelectionActivity.this, new Observer<List<MasterInstituteInfo>>() {
                            @Override
                            public void onChanged(List<MasterInstituteInfo> masterInstituteInfos) {
                                customProgressDialog.dismiss();
                                if (masterInstituteInfos == null || masterInstituteInfos.size() == 0) {
                                    Utils.customSchoolSyncAlert(DMVSelectionActivity.this, getString(R.string.app_name), getString(R.string.no_ins_found));
                                } else {
                                    viewModel.getAllDietList().observe(DMVSelectionActivity.this, new Observer<List<MasterDietListInfo>>() {
                                        @Override
                                        public void onChanged(List<MasterDietListInfo> masterDietListInfos) {
                                            customProgressDialog.dismiss();
                                            if (masterDietListInfos == null || masterDietListInfos.size() == 0) {
                                                Utils.customSchoolSyncAlert(DMVSelectionActivity.this, getString(R.string.app_name), getString(R.string.no_diet_list_sound));
                                            }
                                        }
                                    });
                                }
                            }
                        });

                    } else {
                        Utils.customSchoolSyncAlert(DMVSelectionActivity.this, getString(R.string.app_name), getString(R.string.no_map_dis_found));
                    }
                }
            });
        } else {
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

                        viewModel.getAllInstitutes().observe(DMVSelectionActivity.this, new Observer<List<MasterInstituteInfo>>() {
                            @Override
                            public void onChanged(List<MasterInstituteInfo> masterInstituteInfos) {
                                customProgressDialog.dismiss();
                                if (masterInstituteInfos == null || masterInstituteInfos.size() == 0) {
                                    Utils.customSchoolSyncAlert(DMVSelectionActivity.this, getString(R.string.app_name), getString(R.string.no_ins_found));
                                } else {
                                    viewModel.getAllDietList().observe(DMVSelectionActivity.this, new Observer<List<MasterDietListInfo>>() {
                                        @Override
                                        public void onChanged(List<MasterDietListInfo> masterDietListInfos) {
                                            customProgressDialog.dismiss();
                                            if (masterDietListInfos == null || masterDietListInfos.size() == 0) {
                                                Utils.customSchoolSyncAlert(DMVSelectionActivity.this, getString(R.string.app_name), getString(R.string.no_diet_list_sound));
                                            }
                                        }
                                    });
                                }
                            }
                        });

                    } else {
                        Utils.customSchoolSyncAlert(DMVSelectionActivity.this, getString(R.string.app_name), getString(R.string.no_map_dis_found));
                    }
                }
            });
        }


        dmvSelectionActivityBinding.spDist.setOnItemSelectedListener(this);
        dmvSelectionActivityBinding.spMandal.setOnItemSelectedListener(this);
        dmvSelectionActivityBinding.spVillage.setOnItemSelectedListener(this);
        dmvSelectionActivityBinding.spInstitution.setOnItemSelectedListener(this);
        dmvSelectionActivityBinding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    String randomno = Utils.getRandomNumberString();
                    InstSelectionInfo instSelectionInfo = new InstSelectionInfo(selectedInstId,
                            selInstName,
                            String.valueOf(selectedDistId), String.valueOf(selectedManId), String.valueOf(selectedVilId),
                            selectedDistName, selectedManName, selectedVilName, lat, lng, address, randomno);

                    selectionViewModel.insertInstitutes(DMVSelectionActivity.this, instSelectionInfo);

                    InstLatestTimeInfo instLatestTimeInfo = new InstLatestTimeInfo(selectedInstId, selInstName, Utils.getOfflineTime());
                    selectionViewModel.insertLatestTime(instLatestTimeInfo);
                }
            }
        });

        dmvSelectionActivityBinding.header.syncIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DMVSelectionActivity.this, SchoolSyncActivity.class));
            }
        });
        dmvSelectionActivityBinding.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DMVSelectionActivity.this, SchoolsOfflineDataActivity.class));
                finish();
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
            dmvSelectionActivityBinding.llView.setVisibility(View.GONE);
            instNames = new ArrayList<>();
            institutesEntityList = new ArrayList<>();
            instNames.add("-Select-");
            if (i != 0) {
                viewModel.getDistId(dmvSelectionActivityBinding.spDist.getSelectedItem().toString()).observe(DMVSelectionActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        if (str != null) {
                            selectedDistName = dmvSelectionActivityBinding.spDist.getSelectedItem().toString();
                            selectedDistId = Integer.parseInt(str);
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
                                    } else {
                                        dmvSelectionActivityBinding.spInstitution.setAdapter(selectAdapter);
                                        showSnackBar(getString(R.string.no_inst_found));

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
            dmvSelectionActivityBinding.llView.setVisibility(View.GONE);
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
                                            dmvSelectionActivityBinding.mandal.setText(getString(R.string.mandal_) + selectedManName);

                                        selectedVilId = str.getVillageId();
                                        selectedVilName = str.getVillageName();
                                        if (selectedVilName != null)
                                            dmvSelectionActivityBinding.village.setText(getString(R.string.village_) + selectedVilName);

                                        selectedAddress = str.getAddress();
                                        if (selectedAddress != null)
                                            dmvSelectionActivityBinding.address.setText(getString(R.string.address_) + selectedAddress);
                                        lat = str.getLatitude();
                                        lng = str.getLongitude();
                                        address = str.getAddress();

                                        LiveData<SchoolsOfflineEntity> schoolsOfflineRecord = schoolsOfflineViewModel.getSchoolsOfflineRecord(String.valueOf(inst_id));
                                        schoolsOfflineRecord.observe(DMVSelectionActivity.this, new Observer<SchoolsOfflineEntity>() {
                                            @Override
                                            public void onChanged(SchoolsOfflineEntity offlineEntity) {
                                                schoolsOfflineRecord.removeObservers(DMVSelectionActivity.this);

                                                if (offlineEntity == null) {
                                                    dmvSelectionActivityBinding.llView.setVisibility(View.GONE);
                                                    dmvSelectionActivityBinding.btnProceed.setVisibility(View.VISIBLE);
                                                } else {
                                                    dmvSelectionActivityBinding.llView.setVisibility(View.VISIBLE);
                                                    dmvSelectionActivityBinding.btnProceed.setVisibility(View.GONE);
                                                }
                                            }
                                        });
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
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void getCount(int cnt) {
        if (cnt != -1) {
            LiveData<InstSelectionInfo> liveData = selectionViewModel.getSelectedInst(selectedInstId);
            liveData.observe(DMVSelectionActivity.this, new Observer<InstSelectionInfo>() {
                @Override
                public void onChanged(InstSelectionInfo instSelectionInfo) {
                    liveData.removeObservers(DMVSelectionActivity.this);
                    if (instSelectionInfo != null) {
                        editor.putInt(AppConstants.DIST_ID, Integer.parseInt(instSelectionInfo.getDist_id()));
                        editor.putInt(AppConstants.MAN_ID, Integer.parseInt(instSelectionInfo.getMan_id()));
                        editor.putInt(AppConstants.VILL_ID, Integer.parseInt(instSelectionInfo.getVil_id()));
                        editor.putString(AppConstants.INST_ID, instSelectionInfo.getInst_id());
                        editor.putString(AppConstants.INST_NAME, instSelectionInfo.getInst_name());
                        editor.putString(AppConstants.DIST_NAME, instSelectionInfo.getDist_name());
                        editor.putString(AppConstants.MAN_NAME, instSelectionInfo.getMan_name());
                        editor.putString(AppConstants.VIL_NAME, instSelectionInfo.getVil_name());
                        editor.putString(AppConstants.LAT, instSelectionInfo.getInst_lat());
                        editor.putString(AppConstants.LNG, instSelectionInfo.getInst_lng());
                        editor.putString(AppConstants.ADDRESS, instSelectionInfo.getInst_address());
                        editor.commit();

                        if (!TextUtils.isEmpty(instSelectionInfo.getInst_lat()) && Double.parseDouble(instSelectionInfo.getInst_lat()) > 0.0
                                && !TextUtils.isEmpty(instSelectionInfo.getInst_lng()) && Double.parseDouble(instSelectionInfo.getInst_lng()) > 0.0) {
                            startActivity(new Intent(DMVSelectionActivity.this, InstMenuMainActivity.class));
                            finish();
                        } else {
                            Utils.customHomeAlert(DMVSelectionActivity.this, getString(R.string.app_name), getString(R.string.inst_loc));
                        }

                    }
                }
            });
        } else {
            Toast.makeText(context, getString(R.string.something), Toast.LENGTH_SHORT).show();
        }
    }
}
