package com.cgg.twdinspection.inspection.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.InstMainActivityBinding;
import com.cgg.twdinspection.inspection.adapter.MenuSectionsAdapter;
import com.cgg.twdinspection.inspection.interfaces.FinalSubmitListener;
import com.cgg.twdinspection.inspection.interfaces.InstSubmitInterface;
import com.cgg.twdinspection.inspection.interfaces.SchoolOfflineInterface;
import com.cgg.twdinspection.inspection.offline.SchoolsOfflineEntity;
import com.cgg.twdinspection.inspection.source.academic_overview.AcademicEntity;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.CoCurricularEntity;
import com.cgg.twdinspection.inspection.source.diet_issues.DietIssuesEntity;
import com.cgg.twdinspection.inspection.source.entitlements_distribution.EntitlementsEntity;
import com.cgg.twdinspection.inspection.source.general_comments.GeneralCommentsEntity;
import com.cgg.twdinspection.inspection.source.general_information.GeneralInfoEntity;
import com.cgg.twdinspection.inspection.source.infra_maintenance.InfraStructureEntity;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstMenuInfoEntity;
import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;
import com.cgg.twdinspection.inspection.source.registers_upto_date.RegistersEntity;
import com.cgg.twdinspection.inspection.source.staff_attendance.StaffAttendanceEntity;
import com.cgg.twdinspection.inspection.source.student_attendence_info.StudAttendInfoEntity;
import com.cgg.twdinspection.inspection.source.submit.InstSubmitRequest;
import com.cgg.twdinspection.inspection.source.submit.InstSubmitResponse;
import com.cgg.twdinspection.inspection.source.upload_photo.UploadPhoto;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstSelectionViewModel;
import com.cgg.twdinspection.inspection.viewmodel.SchoolsOfflineViewModel;
import com.cgg.twdinspection.inspection.viewmodel.UploadPhotoViewModel;
import com.cgg.twdinspection.offline.SchoolsOfflineDataActivity;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.cgg.twdinspection.schemes.interfaces.SchemeSubmitInterface;
import com.cgg.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;
import com.cgg.twdinspection.schemes.source.submit.SchemeSubmitResponse;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class InstMenuMainActivity extends LocBaseActivity implements SchemeSubmitInterface,
        ErrorHandlerInterface, InstSubmitInterface, FinalSubmitListener, SchoolOfflineInterface {
    InstMainActivityBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String instId, officer_id, dist_id, mand_id, vill_id;
    String instName, distName, mandalName, villageName;
    boolean submitFlag = false, generalInfoFlag = false, studAttendFlag = false,
            staffAttendFlag = false, medicalFlag = false, dietFlag = false, infraFlag = false,
            academicFlag = true, cocurricularFlag = true,
            entitlementsFlag = false, regFlag = false, generalCommentsFlag = false, photoFlag = false;
    InstMainViewModel instMainViewModel;
    private CustomProgressDialog customProgressDialog;
    LiveData<List<InstMenuInfoEntity>> sectionsData;
    private InstSubmitRequest instSubmitRequest;
    private String randomNo;
    public static final String IMAGE_DIRECTORY_NAME = "SCHOOL_INSP_IMAGES";
    private SchoolsOfflineViewModel schoolsOfflineViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customProgressDialog = new CustomProgressDialog(this);
        binding = DataBindingUtil.setContentView(this, R.layout.inst_main_activity);
        binding.appbar.header.syncIv.setVisibility(View.GONE);
        binding.appbar.header.headerTitle.setText(getString(R.string.dashboard));
        binding.appbar.header.backBtn.setVisibility(View.GONE);
        UploadPhotoViewModel viewModel = new UploadPhotoViewModel(InstMenuMainActivity.this);
        schoolsOfflineViewModel = new SchoolsOfflineViewModel(getApplication());

        binding.appbar.header.ivChange.setVisibility(View.VISIBLE);
        binding.appbar.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sectionsData != null && sectionsData.getValue() != null && sectionsData.getValue().size() > 0) {
                    boolean flag = false;
                    for (int i = 0; i < sectionsData.getValue().size(); i++) {
                        if (sectionsData.getValue().get(i).getFlag_completed() == 1) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        clearSharedPref();
                        instMainViewModel.deleteMenuData(instId);
                        startActivity(new Intent(InstMenuMainActivity.this, DashboardMenuActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        finish();
                    } else {

                        startActivity(new Intent(InstMenuMainActivity.this, DashboardMenuActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        finish();
                    }
                } else {
                    clearSharedPref();
                    instMainViewModel.deleteMenuData(instId);
                    startActivity(new Intent(InstMenuMainActivity.this, DashboardMenuActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }
            }
        });

        binding.appbar.header.ivChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.customChangeAppAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name),
                        getString(R.string.change_insp), instMainViewModel, editor, instId);
            }
        });

        instMainViewModel = new InstMainViewModel(getApplication(), InstMenuMainActivity.this);
        binding.setViewmodel(instMainViewModel);
        sharedPreferences = TWDApplication.get(this).getPreferences();
        editor = sharedPreferences.edit();
        instName = sharedPreferences.getString(AppConstants.INST_NAME, "");
        distName = sharedPreferences.getString(AppConstants.DIST_NAME, "");
        mandalName = sharedPreferences.getString(AppConstants.MAN_NAME, "");
        villageName = sharedPreferences.getString(AppConstants.VIL_NAME, "");
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        officer_id = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        dist_id = String.valueOf(sharedPreferences.getInt(AppConstants.DIST_ID, 0));
        mand_id = String.valueOf(sharedPreferences.getInt(AppConstants.MAN_ID, 0));
        vill_id = String.valueOf(sharedPreferences.getInt(AppConstants.VILL_ID, 0));
        String desLat = sharedPreferences.getString(AppConstants.LAT, "");
        String desLng = sharedPreferences.getString(AppConstants.LNG, "");

        InstSelectionViewModel selectionViewModel = new InstSelectionViewModel(getApplication());
        LiveData<String> liveData = selectionViewModel.getRandomId(instId);
        liveData.observe(InstMenuMainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String value) {
                randomNo = value;
            }
        });


        sectionsData = instMainViewModel.getAllSections(instId);
        sectionsData.observe(this, new Observer<List<InstMenuInfoEntity>>() {
            @Override
            public void onChanged(List<InstMenuInfoEntity> menuInfoEntities) {
                sectionsData.removeObservers(InstMenuMainActivity.this);

                if (sectionsData.getValue() != null && sectionsData.getValue().size() > 0) {
                    boolean flag = true;
                    for (int i = 0; i < sectionsData.getValue().size(); i++) {
                        if (i != 6 && i != 7 && sectionsData.getValue().get(i).getFlag_completed() == 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        binding.appbar.includeMenuLayout.btnSubmit.setVisibility(View.VISIBLE);
                    }
                }


                if (menuInfoEntities != null && menuInfoEntities.size() > 0) {
                    setAdapter(menuInfoEntities);
                } else {
                    String[] stringArray = getResources().getStringArray(R.array.inst_sections);
                    ArrayList<String> sections = new ArrayList<>(Arrays.asList(stringArray));

                    String[] stringArrayNames = getResources().getStringArray(R.array.inst_sections_names);
                    ArrayList<String> sectionsNames = new ArrayList<>(Arrays.asList(stringArrayNames));
                    if (sections.size() > 0) {
                        menuInfoEntities = new ArrayList<>();
                        for (int x = 0; x < sections.size(); x++) {
                            menuInfoEntities.add(new InstMenuInfoEntity(instId, x + 1, 0, sections.get(x), null, sectionsNames.get(x)));
                        }
                        if (menuInfoEntities.size() > 0) {
                            instMainViewModel.insertMenuSections(menuInfoEntities);
                            setAdapter(menuInfoEntities);
                        }
                    }

                }
            }
        });

        binding.appbar.includeMenuLayout.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sectionsData.getValue() != null && sectionsData.getValue().size() > 0) {
                    boolean flag = true;
                    for (int i = 0; i < sectionsData.getValue().size(); i++) {
                        if (i != 6 && i != 7 && sectionsData.getValue().get(i).getFlag_completed() == 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        submitCall();
                    } else {
                        Utils.customWarningAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_insp_all));
                    }
                }
            }
        });
    }

    private void setAdapter(List<InstMenuInfoEntity> menuInfoEntities) {
        MenuSectionsAdapter adapter = new MenuSectionsAdapter(InstMenuMainActivity.this, menuInfoEntities);
        binding.appbar.includeMenuLayout.rvMenu.setLayoutManager(new LinearLayoutManager(InstMenuMainActivity.this));
        binding.appbar.includeMenuLayout.rvMenu.setAdapter(adapter);
    }

    private void submitCall() {
        if (!submitFlag) {
            instSubmitRequest = new InstSubmitRequest();
            LiveData<GeneralInfoEntity> generalInfoEntityLiveData = instMainViewModel.getGeneralInfoData(instId);
            generalInfoEntityLiveData.observe(InstMenuMainActivity.this, new Observer<GeneralInfoEntity>() {
                @Override
                public void onChanged(GeneralInfoEntity generalInfoEntity) {
                    generalInfoEntityLiveData.removeObservers(InstMenuMainActivity.this);
                    if (generalInfoEntity != null) {
                        generalInfoFlag = true;
                        instSubmitRequest.setGeneral_info(generalInfoEntity);
                        submitFinalCall();
                    } else {
                        instSubmitRequest.setGeneral_info(null);
                    }
                }
            });
            LiveData<List<StudAttendInfoEntity>> studAttenLiveData = instMainViewModel.getStudAttendInfoData(instId);
            studAttenLiveData.observe(InstMenuMainActivity.this, new Observer<List<StudAttendInfoEntity>>() {
                @Override
                public void onChanged(List<StudAttendInfoEntity> studAttendInfoEntities) {
                    studAttenLiveData.removeObservers(InstMenuMainActivity.this);
                    if (studAttendInfoEntities != null) {
                        studAttendFlag = true;
                        instSubmitRequest.setStudent_attendence_info(studAttendInfoEntities);
                        submitFinalCall();
                    } else {
                        instSubmitRequest.setStudent_attendence_info(null);
                    }
                }
            });
            LiveData<List<StaffAttendanceEntity>> staffAttendLiveData = instMainViewModel.getStaffInfoData(instId);
            staffAttendLiveData.observe(InstMenuMainActivity.this, new Observer<List<StaffAttendanceEntity>>() {
                @Override
                public void onChanged(List<StaffAttendanceEntity> staffAttendanceEntities) {
                    staffAttendLiveData.removeObservers(InstMenuMainActivity.this);
                    if (staffAttendanceEntities != null) {
                        staffAttendFlag = true;
                        instSubmitRequest.setStaff_attendence_info(staffAttendanceEntities);
                        submitFinalCall();
                    } else {
                        instSubmitRequest.setStaff_attendence_info(null);
                    }
                }
            });
            LiveData<MedicalInfoEntity> medicalInfoEntityLiveData = instMainViewModel.getMedicalInfo(instId);
            medicalInfoEntityLiveData.observe(InstMenuMainActivity.this, new Observer<MedicalInfoEntity>() {
                @Override
                public void onChanged(MedicalInfoEntity medicalInfoEntity) {
                    medicalInfoEntityLiveData.removeObservers(InstMenuMainActivity.this);
                    if (medicalInfoEntity != null) {
                        medicalFlag = true;
                        instSubmitRequest.setMedical_issues(medicalInfoEntity);
                        submitFinalCall();
                    } else {
                        instSubmitRequest.setMedical_issues(null);
                    }
                }
            });
            LiveData<DietIssuesEntity> dietIssuesEntityLiveData = instMainViewModel.getDietInfoData(instId);
            dietIssuesEntityLiveData.observe(InstMenuMainActivity.this, new Observer<DietIssuesEntity>() {
                @Override
                public void onChanged(DietIssuesEntity dietIssuesEntity) {
                    dietIssuesEntityLiveData.removeObservers(InstMenuMainActivity.this);
                    if (dietIssuesEntity != null) {
                        dietFlag = true;
                        instSubmitRequest.setDiet_issues(dietIssuesEntity);
                        submitFinalCall();
                    } else {
                        instSubmitRequest.setDiet_issues(null);
                    }
                }
            });
            LiveData<InfraStructureEntity> infraStructureEntityLiveData = instMainViewModel.getInfrastructureInfoData(instId);
            infraStructureEntityLiveData.observe(InstMenuMainActivity.this, new Observer<InfraStructureEntity>() {
                @Override
                public void onChanged(InfraStructureEntity infraStructureEntity) {
                    infraStructureEntityLiveData.removeObservers(InstMenuMainActivity.this);
                    if (infraStructureEntity != null) {
                        infraFlag = true;
                        instSubmitRequest.setInfra_maintenance(infraStructureEntity);
                        submitFinalCall();
                    } else {
                        instSubmitRequest.setInfra_maintenance(null);
                    }
                }
            });
            LiveData<AcademicEntity> academicEntityLiveData = instMainViewModel.getAcademicInfoData(instId);
            academicEntityLiveData.observe(InstMenuMainActivity.this, new Observer<AcademicEntity>() {
                @Override
                public void onChanged(AcademicEntity academicEntity) {
                    academicEntityLiveData.removeObservers(InstMenuMainActivity.this);
                    if (academicEntity != null) {
                        academicFlag = true;
                        instSubmitRequest.setAcademic_overview(academicEntity);
                        submitFinalCall();
                    } else {
                        instSubmitRequest.setAcademic_overview(null);
                    }
                }
            });
            LiveData<CoCurricularEntity> coCurricularEntityLiveData = instMainViewModel.getCocurricularInfoData(instId);
            coCurricularEntityLiveData.observe(InstMenuMainActivity.this, new Observer<CoCurricularEntity>() {
                @Override
                public void onChanged(CoCurricularEntity coCurricularEntity) {
                    coCurricularEntityLiveData.removeObservers(InstMenuMainActivity.this);
                    if (coCurricularEntity != null) {
                        cocurricularFlag = true;
                        instSubmitRequest.setCoCurricular_info(coCurricularEntity);
                        submitFinalCall();
                    } else {
                        instSubmitRequest.setCoCurricular_info(null);
                    }
                }
            });
            LiveData<EntitlementsEntity> entitlementsEntityLiveData = instMainViewModel.getEntitlementInfoData(instId);
            entitlementsEntityLiveData.observe(InstMenuMainActivity.this, new Observer<EntitlementsEntity>() {
                @Override
                public void onChanged(EntitlementsEntity entitlementsEntity) {
                    entitlementsEntityLiveData.removeObservers(InstMenuMainActivity.this);
                    if (entitlementsEntity != null) {
                        entitlementsFlag = true;
                        instSubmitRequest.setEntitlements(entitlementsEntity);
                        submitFinalCall();
                    } else {
                        instSubmitRequest.setEntitlements(null);
                    }
                }
            });
            LiveData<RegistersEntity> registersEntityLiveData = instMainViewModel.getRegistersInfoData(instId);
            registersEntityLiveData.observe(InstMenuMainActivity.this, new Observer<RegistersEntity>() {
                @Override
                public void onChanged(RegistersEntity registersEntity) {
                    registersEntityLiveData.removeObservers(InstMenuMainActivity.this);
                    if (registersEntity != null) {
                        regFlag = true;
                        instSubmitRequest.setRegisters(registersEntity);
                        submitFinalCall();
                    } else {
                        instSubmitRequest.setRegisters(null);
                    }
                }
            });
            LiveData<GeneralCommentsEntity> generalCommentsEntityLiveData = instMainViewModel.getGeneralCommentsInfoData(instId);
            generalCommentsEntityLiveData.observe(InstMenuMainActivity.this, new Observer<GeneralCommentsEntity>() {
                @Override
                public void onChanged(GeneralCommentsEntity generalCommentsEntity) {
                    generalCommentsEntityLiveData.removeObservers(InstMenuMainActivity.this);
                    if (generalCommentsEntity != null) {
                        generalCommentsFlag = true;
                        instSubmitRequest.setGeneral_comments(generalCommentsEntity);
                        submitFinalCall();
                    } else {
                        instSubmitRequest.setGeneral_comments(null);
                    }
                }
            });

            if (sectionsData != null && sectionsData.getValue() != null && sectionsData.getValue().size() > 0) {
                if (sectionsData.getValue().get(11).getFlag_completed() == 1) {
                    photoFlag = true;
                }
                if (photoFlag) {
                    submitFinalCall();
                }
            }

        }

    }

    void submitFinalCall() {
        if (!submitFlag && generalInfoFlag && studAttendFlag && staffAttendFlag && medicalFlag && dietFlag && infraFlag && cocurricularFlag &&
                academicFlag && entitlementsFlag && regFlag && generalCommentsFlag && photoFlag) {
            customFinaSubmitAlert(InstMenuMainActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure_save));
        }
    }

    @Override
    public void getSubmitData(InstSubmitResponse schemeSubmitResponse) {
        customProgressDialog.hide();
        if (schemeSubmitResponse != null && schemeSubmitResponse.getStatusCode() != null && schemeSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
            clearSharedPref();
            CallSuccessAlert(schemeSubmitResponse.getStatusMessage());
        } else if (schemeSubmitResponse != null && schemeSubmitResponse.getStatusCode() != null && schemeSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {
            revertFlags();
            Utils.customErrorAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name), schemeSubmitResponse.getStatusMessage() + getString(R.string.failed_to_submit));
        } else {
            revertFlags();
            Utils.customErrorAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name), getResources().getString(R.string.something) + getString(R.string.failed_to_submit));
        }
    }

    private void revertFlags() {
        submitFlag = false;
        generalInfoFlag = false;
        studAttendFlag = false;
        staffAttendFlag = false;
        medicalFlag = false;
        dietFlag = false;
        infraFlag = false;
        cocurricularFlag = true;
        academicFlag = true;
        entitlementsFlag = false;
        regFlag = false;
        generalCommentsFlag = false;
    }

    private void CallSuccessAlert(String msg) {

        File mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME
                + "/" + instId);

        if (mediaStorageDir.isDirectory()) {
            String[] children = mediaStorageDir.list();
            for (String child : children) new File(mediaStorageDir, child).delete();
            mediaStorageDir.delete();
        }
        Utils.customSuccessAlert(this, getResources().getString(R.string.app_name), msg);
    }

    @Override
    public void handleError(Throwable e, Context context) {
        revertFlags();
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        Snackbar.make(binding.appbar.root, errMsg, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        if (sectionsData != null && sectionsData.getValue() != null && sectionsData.getValue().size() > 0) {
            boolean flag = false;
            for (int i = 0; i < sectionsData.getValue().size(); i++) {
                if (sectionsData.getValue().get(i).getFlag_completed() == 1) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                clearSharedPref();
                instMainViewModel.deleteMenuData(instId);
                startActivity(new Intent(InstMenuMainActivity.this, DMVSelectionActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            } else {
                startActivity(new Intent(this, DashboardMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        } else {
            clearSharedPref();
            instMainViewModel.deleteMenuData(instId);
            startActivity(new Intent(InstMenuMainActivity.this, DMVSelectionActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }
    }

    private void clearSharedPref() {
        editor.putString(AppConstants.INST_ID, "");
        editor.putString(AppConstants.INST_NAME, "");
        editor.putInt(AppConstants.DIST_ID, -1);
        editor.putInt(AppConstants.MAN_ID, -1);
        editor.putInt(AppConstants.VILL_ID, -1);
        editor.putString(AppConstants.DIST_NAME, "");
        editor.putString(AppConstants.MAN_NAME, "");
        editor.putString(AppConstants.VIL_NAME, "");
        editor.putString(AppConstants.LAT, "");
        editor.putString(AppConstants.LNG, "");
        editor.putString(AppConstants.ADDRESS, "");
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mGpsSwitchStateReceiver);
        if (customProgressDialog != null && customProgressDialog.isShowing()) {
            customProgressDialog.dismiss();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
    }

    @Override
    protected void onResume() {
        super.onResume();

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (mCurrentLocation == null) {
                    mCurrentLocation = locationResult.getLastLocation();
                }
            }
        };
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

    private final BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent != null && intent.getAction() != null &&
                        intent.getAction().matches(LocationManager.PROVIDERS_CHANGED_ACTION)) {
                    callPermissions();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void getData(SchemeSubmitResponse schemeSubmitResponse) {

    }

    @Override
    public void getPhotoData(SchemePhotoSubmitResponse schemePhotoSubmitResponse) {
        customProgressDialog.hide();

        if (schemePhotoSubmitResponse != null && schemePhotoSubmitResponse.getStatusCode() != null && schemePhotoSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_CODE)) {
            instSubmitRequest.setOfficer_id(officer_id);
            instSubmitRequest.setInstitute_id(instId);
            instSubmitRequest.setInspection_time(Utils.getCurrentDateTime());
            instSubmitRequest.setDist_id(dist_id);
            instSubmitRequest.setMandal_id(mand_id);
            instSubmitRequest.setVillage_id(vill_id);

            instSubmitRequest.setInstitute_name(instName);
            instSubmitRequest.setDist_name(distName);
            instSubmitRequest.setMandal_name(mandalName);
            instSubmitRequest.setVillage_name(villageName);
            instSubmitRequest.setPhoto_key_id(randomNo);
            instSubmitRequest.setDevice_Id(Utils.getDeviceID(this));
            instSubmitRequest.setVersion_No(Utils.getVersionName(this));
            instSubmitRequest.getAcademic_overview().setLast_yr_ssc_percent(instSubmitRequest.getGeneral_comments().getAnemic_stud_cnt());


            if (Utils.checkInternetConnection(InstMenuMainActivity.this)) {
                submitFlag = true;
                customProgressDialog.show();
                customProgressDialog.addText(getString(R.string.uploading_data));
                instMainViewModel.submitInstDetails(instSubmitRequest);
            } else {
                revertFlags();
                Utils.customErrorAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
            }
        } else if (schemePhotoSubmitResponse != null && schemePhotoSubmitResponse.getStatusCode() != null && schemePhotoSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_CODE)) {
            Utils.customErrorAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name), schemePhotoSubmitResponse.getStatusMessage());
            revertFlags();
        } else {
            Utils.customErrorAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name), getString(R.string.something));
            revertFlags();
        }
    }

    @Override
    public void finalSubmitCall() {
        SchoolsOfflineEntity schoolsOfflineEntity = new SchoolsOfflineEntity();
        schoolsOfflineEntity.setInst_id(instId);
        schoolsOfflineEntity.setInst_name(instName);
        schoolsOfflineEntity.setInst_time(Utils.getOfflineTime());
        schoolsOfflineEntity.setDist_id(dist_id);
        schoolsOfflineEntity.setDist_name(distName);
        schoolsOfflineEntity.setMan_id(mand_id);
        schoolsOfflineEntity.setMan_name(mandalName);
        schoolsOfflineEntity.setVil_id(vill_id);
        schoolsOfflineEntity.setVil_name(villageName);
        schoolsOfflineEntity.setOfficer_id(officer_id);

        schoolsOfflineViewModel.insertSchoolRecord(InstMenuMainActivity.this,
                schoolsOfflineEntity);
    }

    public void customFinaSubmitAlert(Activity activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_confirmation);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button btDialogNo = dialog.findViewById(R.id.btDialogNo);
                btDialogNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        revertFlags();
                    }
                });

                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        FinalSubmitListener saveListener = (FinalSubmitListener) activity;
                        saveListener.finalSubmitCall();
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void schoolRecCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, SchoolsOfflineDataActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletedSchoolCount(int cnt) {

    }

    @Override
    public void deletedSchoolCountSubmitted(int cnt) {

    }
}
