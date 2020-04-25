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
import com.cgg.twdinspection.inspection.viewmodel.UploadPhotoViewModel;
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
        ErrorHandlerInterface, InstSubmitInterface, FinalSubmitListener {
    InstMainActivityBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String instId, officer_id, dist_id, mand_id, vill_id;
    String instName, distName, mandalName, villageName;
    boolean submitFlag = false, generalInfoFlag = false, studAttendFlag = false,
            staffAttendFlag = false, medicalFlag = false, dietFlag = false, infraFlag = false,
            academicFlag = false, cocurricularFlag = false,
            entitlementsFlag = false, regFlag = false, generalCommentsFlag = false, photoFlag = false;
    InstMainViewModel instMainViewModel;
    private String desLat, desLng;
    private CustomProgressDialog customProgressDialog;
    private String cacheDate, currentDate;
    LiveData<List<InstMenuInfoEntity>> arrayListLiveData;
    private UploadPhotoViewModel viewModel;
    private InstSubmitRequest instSubmitRequest;
    private File file_storeroom, file_varandah,
            file_playGround, file_diningHall, file_dormitory,
            file_mainBulding, file_toilet, file_kitchen, file_classroom,
            file_tds, file_menu, file_officer;
    private String randomNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customProgressDialog = new CustomProgressDialog(InstMenuMainActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.inst_main_activity);
        binding.appbar.header.syncIv.setVisibility(View.GONE);
        binding.appbar.header.headerTitle.setText(getString(R.string.dashboard));
        binding.appbar.header.backBtn.setVisibility(View.GONE);
        viewModel = new UploadPhotoViewModel(InstMenuMainActivity.this);

        binding.appbar.header.ivChange.setVisibility(View.VISIBLE);
        binding.appbar.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InstMenuMainActivity.this, DashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        binding.appbar.header.ivChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.customChangeAppAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name),
                        getString(R.string.change_insp), instMainViewModel, editor);
            }
        });

        instMainViewModel = new InstMainViewModel(binding, getApplication(), InstMenuMainActivity.this);
        binding.setViewmodel(instMainViewModel);

        arrayListLiveData = instMainViewModel.getAllSections();
        sharedPreferences = TWDApplication.get(this).getPreferences();
        editor = sharedPreferences.edit();
        instName = sharedPreferences.getString(AppConstants.INST_NAME, "");
        distName = sharedPreferences.getString(AppConstants.DIST_NAME, "");
        mandalName = sharedPreferences.getString(AppConstants.MAN_NAME, "");
        villageName = sharedPreferences.getString(AppConstants.VIL_NAME, "");
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        Log.i(":INST_ID", "onCreate: " + instId);
        officer_id = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        dist_id = String.valueOf(sharedPreferences.getInt(AppConstants.DIST_ID, 0));
        mand_id = String.valueOf(sharedPreferences.getInt(AppConstants.MAN_ID, 0));
        vill_id = String.valueOf(sharedPreferences.getInt(AppConstants.VILL_ID, 0));
        desLat = sharedPreferences.getString(AppConstants.LAT, "");
        desLng = sharedPreferences.getString(AppConstants.LNG, "");
        randomNo = sharedPreferences.getString(AppConstants.RANDOM_NO, "");

        if (randomNo.equalsIgnoreCase("")) {
            String randomno = Utils.getRandomNumberString();
            editor.putString(AppConstants.RANDOM_NO, randomno);
            editor.commit();
        }

        if (TextUtils.isEmpty(instId)) {
            Utils.ShowDeviceSessionAlert(this,
                    getResources().getString(R.string.app_name),
                    getString(R.string.ses_expire_re), instMainViewModel);
        }

        LiveData<List<UploadPhoto>> listLiveData = viewModel.getPhotos();
        listLiveData.observe(InstMenuMainActivity.this, new Observer<List<UploadPhoto>>() {
            @Override
            public void onChanged(List<UploadPhoto> uploadPhotos) {
                listLiveData.removeObservers(InstMenuMainActivity.this);
                if (uploadPhotos != null && uploadPhotos.size() > 0) {
                    for (int z = 0; z < uploadPhotos.size(); z++) {
                        if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.STOREROOM)) {
                            file_storeroom = new File(uploadPhotos.get(z).getPhoto_path());
                        }
                        if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.VARANDAH)) {
                            file_varandah = new File(uploadPhotos.get(z).getPhoto_path());
                        }
                        if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.DORMITORY)) {
                            file_dormitory = new File(uploadPhotos.get(z).getPhoto_path());
                        }
                        if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.DININGHALL)) {
                            file_diningHall = new File(uploadPhotos.get(z).getPhoto_path());
                        }
                        if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.MAINBUILDING)) {
                            file_mainBulding = new File(uploadPhotos.get(z).getPhoto_path());
                        }
                        if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.TOILET)) {
                            file_toilet = new File(uploadPhotos.get(z).getPhoto_path());
                        }
                        if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.KITCHEN)) {
                            file_kitchen = new File(uploadPhotos.get(z).getPhoto_path());
                        }
                        if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.CLASSROOM)) {
                            file_classroom = new File(uploadPhotos.get(z).getPhoto_path());
                        }
                        if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.PLAYGROUND)) {
                            file_playGround = new File(uploadPhotos.get(z).getPhoto_path());
                        }
                        if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.TDS)) {
                            file_tds = new File(uploadPhotos.get(z).getPhoto_path());
                        }
                        if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.MENU)) {
                            file_menu = new File(uploadPhotos.get(z).getPhoto_path());
                        }
                        if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.OFFICER)) {
                            file_officer = new File(uploadPhotos.get(z).getPhoto_path());
                        }

                    }
                }
            }
        });
        arrayListLiveData.observe(this, new Observer<List<InstMenuInfoEntity>>() {
            @Override
            public void onChanged(List<InstMenuInfoEntity> menuInfoEntities) {
                arrayListLiveData.removeObservers(InstMenuMainActivity.this);

                if (arrayListLiveData.getValue() != null && arrayListLiveData.getValue().size() > 0) {
                    boolean flag = true;
                    for (int i = 0; i < arrayListLiveData.getValue().size(); i++) {
                        if (arrayListLiveData.getValue().get(i).getFlag_completed() == 0) {
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
                if (arrayListLiveData.getValue() != null && arrayListLiveData.getValue().size() > 0) {
                    boolean flag = true;
                    for (int i = 0; i < arrayListLiveData.getValue().size(); i++) {
                        if (arrayListLiveData.getValue().get(i).getFlag_completed() == 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        if (Utils.checkInternetConnection(InstMenuMainActivity.this)) {
                            getLocationData();
                        } else {
                            Utils.customWarningAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                        }
                    } else {
                        Utils.customWarningAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name), "Please inspect all the sections");
                    }
                }
            }
        });
    }

    private void getLocationData() {

        Location cLocation = null, dLocation = null;

        if (!TextUtils.isEmpty(desLat) && !TextUtils.isEmpty(desLng)) {
            dLocation = new Location("dLoc");
            dLocation.setLatitude(Double.valueOf(desLat));
            dLocation.setLongitude(Double.valueOf(desLng));
        }

        if (mCurrentLocation != null && mCurrentLocation.getLatitude() != 0 && mCurrentLocation.getLongitude() != 0) {
            cLocation = new Location("cLoc");
            cLocation.setLatitude(mCurrentLocation.getLatitude());
            cLocation.setLongitude(mCurrentLocation.getLongitude());
        }

        if (cLocation == null) {
            Snackbar.make(binding.appbar.root, getString(R.string.loc_not_ava), Snackbar.LENGTH_SHORT).show();

            return;
        }

        if (dLocation != null && dLocation.getLatitude() > 0 && dLocation.getLongitude() > 0) {
            float distance = Utils.calcDistance(cLocation, dLocation);
            if (distance <= AppConstants.DISTANCE) {
                submitCall();
            } else {
                Utils.customDistanceAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name), "Sorry, inspection submit not allowed, You are not within the "
                        + AppConstants.DISTANCE+" meter radius of selected institute");
            }
        } else {
            Utils.customDistanceAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name), "Sorry, inspection submit not allowed, institute location details are not found");
        }
    }

    private void callPhotoSubmit(InstSubmitRequest instSubmitRequest) {

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_classroom);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file_classroom.getName(), requestFile);
        RequestBody requestFile1 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_diningHall);
        MultipartBody.Part body1 =
                MultipartBody.Part.createFormData("image", file_diningHall.getName(), requestFile1);
        RequestBody requestFile2 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_kitchen);
        MultipartBody.Part body2 =
                MultipartBody.Part.createFormData("image", file_kitchen.getName(), requestFile2);
        RequestBody requestFile3 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_mainBulding);
        MultipartBody.Part body3 =
                MultipartBody.Part.createFormData("image", file_mainBulding.getName(), requestFile3);
        RequestBody requestFile4 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_playGround);
        MultipartBody.Part body4 =
                MultipartBody.Part.createFormData("image", file_playGround.getName(), requestFile4);
        RequestBody requestFile5 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_toilet);
        MultipartBody.Part body5 =
                MultipartBody.Part.createFormData("image", file_toilet.getName(), requestFile5);
        RequestBody requestFile6 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_varandah);
        MultipartBody.Part body6 =
                MultipartBody.Part.createFormData("image", file_varandah.getName(), requestFile6);
        RequestBody requestFile7 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_dormitory);
        MultipartBody.Part body7 =
                MultipartBody.Part.createFormData("image", file_dormitory.getName(), requestFile7);
        RequestBody requestFile8 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_storeroom);
        MultipartBody.Part body8 =
                MultipartBody.Part.createFormData("image", file_storeroom.getName(), requestFile8);
        MultipartBody.Part body9 = null;
        if (file_tds != null && !file_tds.getPath().equalsIgnoreCase("null")) {
            RequestBody requestFile9 =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file_tds);

            body9 = MultipartBody.Part.createFormData("image", file_tds.getName(), requestFile9);
        }
        MultipartBody.Part body10 = null;
        if (file_menu != null && !file_menu.getPath().equalsIgnoreCase("null")) {
            RequestBody requestFile10 =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file_menu);

            body10 = MultipartBody.Part.createFormData("image", file_menu.getName(), requestFile10);
        }

        RequestBody requestFile11 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_officer);
        MultipartBody.Part body11 =
                MultipartBody.Part.createFormData("image", file_officer.getName(), requestFile11);

        customProgressDialog.show();


        List<MultipartBody.Part> partList = new ArrayList<>();
        partList.add(body);
        partList.add(body1);
        partList.add(body2);
        partList.add(body3);
        partList.add(body4);
        partList.add(body5);
        partList.add(body6);
        partList.add(body7);
        partList.add(body8);
        if (body9 != null) {
            partList.add(body9);
        }
        if (body10 != null) {
            partList.add(body10);
        }
        partList.add(body11);


        viewModel.UploadImageServiceCall(partList, instSubmitRequest);
    }

    private void setAdapter(List<InstMenuInfoEntity> menuInfoEntities) {
        MenuSectionsAdapter adapter = new MenuSectionsAdapter(InstMenuMainActivity.this, menuInfoEntities);
        binding.appbar.includeMenuLayout.rvMenu.setLayoutManager(new LinearLayoutManager(InstMenuMainActivity.this));
        binding.appbar.includeMenuLayout.rvMenu.setAdapter(adapter);
    }

    private void submitCall() {
        if (!submitFlag) {
            instSubmitRequest = new InstSubmitRequest();
            LiveData<GeneralInfoEntity> generalInfoEntityLiveData = instMainViewModel.getGeneralInfoData();
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
            LiveData<List<StudAttendInfoEntity>> studAttenLiveData = instMainViewModel.getStudAttendInfoData();
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
            LiveData<List<StaffAttendanceEntity>> staffAttendLiveData = instMainViewModel.getStaffInfoData();
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
            LiveData<MedicalInfoEntity> medicalInfoEntityLiveData = instMainViewModel.getMedicalInfo();
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
            LiveData<DietIssuesEntity> dietIssuesEntityLiveData = instMainViewModel.getDietInfoData();
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
            LiveData<InfraStructureEntity> infraStructureEntityLiveData = instMainViewModel.getInfrastructureInfoData();
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
            LiveData<AcademicEntity> academicEntityLiveData = instMainViewModel.getAcademicInfoData();
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
            LiveData<CoCurricularEntity> coCurricularEntityLiveData = instMainViewModel.getCocurricularInfoData();
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
            LiveData<EntitlementsEntity> entitlementsEntityLiveData = instMainViewModel.getEntitlementInfoData();
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
            LiveData<RegistersEntity> registersEntityLiveData = instMainViewModel.getRegistersInfoData();
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
            LiveData<GeneralCommentsEntity> generalCommentsEntityLiveData = instMainViewModel.getGeneralCommentsInfoData();
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

            if (arrayListLiveData != null && arrayListLiveData.getValue() != null && arrayListLiveData.getValue().size() > 0) {
                if (arrayListLiveData.getValue().get(11).getFlag_completed() == 1) {
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
            customFinaSubmitAlert(InstMenuMainActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));
        }
    }

    @Override
    public void getSubmitData(InstSubmitResponse schemeSubmitResponse) {
        customProgressDialog.hide();
        if (schemeSubmitResponse != null && schemeSubmitResponse.getStatusCode() != null && schemeSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
            instMainViewModel.deleteAllInspectionData();
            clearSharedPref();
            CallSuccessAlert(schemeSubmitResponse.getStatusMessage());
        } else if (schemeSubmitResponse != null && schemeSubmitResponse.getStatusCode() != null && schemeSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {
            revertFlags();
            Utils.customErrorAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name), schemeSubmitResponse.getStatusMessage() + " Failed data submit");
        } else {
            revertFlags();
            Utils.customErrorAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name), getResources().getString(R.string.something) + " Failed data submit");
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
        cocurricularFlag = false;
        academicFlag = false;
        entitlementsFlag = false;
        regFlag = false;
        generalCommentsFlag = false;
    }

    private void CallSuccessAlert(String msg) {
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


        if (arrayListLiveData != null && arrayListLiveData.getValue() != null && arrayListLiveData.getValue().size() > 0) {
            boolean flag = false;
            for (int i = 0; i < arrayListLiveData.getValue().size(); i++) {
                if (arrayListLiveData.getValue().get(i).getFlag_completed() == 1) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                clearSharedPref();
                instMainViewModel.deleteMenuData();
                startActivity(new Intent(InstMenuMainActivity.this, DMVSelectionActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            } else {

                startActivity(new Intent(this, DashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        } else {
            clearSharedPref();
            instMainViewModel.deleteMenuData();
            startActivity(new Intent(InstMenuMainActivity.this, DMVSelectionActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
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
        editor.putString(AppConstants.RANDOM_NO, "");

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

    private BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
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
            Snackbar.make(binding.appbar.root, "Uploaded photos successfully.Submitting data...", Snackbar.LENGTH_LONG).show();
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


            if (Utils.checkInternetConnection(InstMenuMainActivity.this)) {
                submitFlag = true;
                customProgressDialog.show();
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
        if (Utils.checkInternetConnection(InstMenuMainActivity.this)) {
            callPhotoSubmit(instSubmitRequest);
        } else {
            Utils.customErrorAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
    }

    public void customFinaSubmitAlert(Activity activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_information);
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


}
