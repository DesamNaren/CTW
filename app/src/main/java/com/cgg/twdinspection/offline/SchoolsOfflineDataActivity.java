package com.cgg.twdinspection.offline;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.cgg.twdinspection.databinding.ActivitySchoolsOfflineReportBinding;
import com.cgg.twdinspection.inspection.interfaces.InstSubmitInterface;
import com.cgg.twdinspection.inspection.interfaces.SchoolOfflineInterface;
import com.cgg.twdinspection.inspection.interfaces.SchoolsOfflineSubmitInterface;
import com.cgg.twdinspection.inspection.offline.SchoolsOfflineEntity;
import com.cgg.twdinspection.inspection.source.academic_overview.AcademicEntity;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.CoCurricularEntity;
import com.cgg.twdinspection.inspection.source.diet_issues.DietIssuesEntity;
import com.cgg.twdinspection.inspection.source.entitlements_distribution.EntitlementsEntity;
import com.cgg.twdinspection.inspection.source.general_comments.GeneralCommentsEntity;
import com.cgg.twdinspection.inspection.source.general_information.GeneralInfoEntity;
import com.cgg.twdinspection.inspection.source.infra_maintenance.InfraStructureEntity;
import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;
import com.cgg.twdinspection.inspection.source.registers_upto_date.RegistersEntity;
import com.cgg.twdinspection.inspection.source.staff_attendance.StaffAttendanceEntity;
import com.cgg.twdinspection.inspection.source.student_attendence_info.StudAttendInfoEntity;
import com.cgg.twdinspection.inspection.source.submit.InstSubmitRequest;
import com.cgg.twdinspection.inspection.source.submit.InstSubmitResponse;
import com.cgg.twdinspection.inspection.source.upload_photo.UploadPhoto;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstSelectionViewModel;
import com.cgg.twdinspection.inspection.viewmodel.SchoolsOfflineViewModel;
import com.cgg.twdinspection.inspection.viewmodel.UploadPhotoViewModel;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.cgg.twdinspection.schemes.interfaces.SchemeSubmitInterface;
import com.cgg.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;
import com.cgg.twdinspection.schemes.source.submit.SchemeSubmitResponse;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SchoolsOfflineDataActivity extends LocBaseActivity implements SchoolsOfflineSubmitInterface,
        SchemeSubmitInterface, InstSubmitInterface, ErrorHandlerInterface, SchoolOfflineInterface {

    ActivitySchoolsOfflineReportBinding offlineReportBinding;
    private CustomProgressDialog customProgressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private SchoolsOfflineViewModel schoolsOfflineViewModel;
    private UploadPhotoViewModel uploadPhotoViewModel;
    private InstMainViewModel instMainViewModel;
    private SchoolsOfflineEntity schoolsOfflineEntity;
    public static final String IMAGE_DIRECTORY_NAME = "SCHOOL_INSP_IMAGES";
    private InstSubmitRequest instSubmitRequest;
    private File file_storeroom, file_varandah,
            file_playGround, file_diningHall, file_dormitory,
            file_mainBulding, file_toilet, file_kitchen, file_classroom,
            file_tds, file_menu, file_officer;
    private String randomNo;
    public Location mCurrentLocation;
    private String curTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customProgressDialog = new CustomProgressDialog(this);
        sharedPreferences = TWDApplication.get(this).getPreferences();
        editor = sharedPreferences.edit();
        curTime = Utils.getOfflineTime();

        offlineReportBinding = DataBindingUtil.setContentView(this, R.layout.activity_schools_offline_report);
        offlineReportBinding.executePendingBindings();
        offlineReportBinding.header.headerTitle.setText(getString(R.string.schools_reports_offline_data));

        offlineReportBinding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchoolsOfflineDataActivity.this, DashboardMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        schoolsOfflineViewModel = new SchoolsOfflineViewModel(getApplication());
        uploadPhotoViewModel = new UploadPhotoViewModel(SchoolsOfflineDataActivity.this);
        instMainViewModel = new InstMainViewModel(getApplication(), SchoolsOfflineDataActivity.this);
        offlineReportBinding.setViewmodel(instMainViewModel);

        offlineReportBinding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        try {
            SchoolsOfflineViewModel schoolsOfflineViewModel = new SchoolsOfflineViewModel(getApplication());
            LiveData<List<SchoolsOfflineEntity>> listLiveData = schoolsOfflineViewModel.getSchoolsOffline();
            listLiveData.observe(SchoolsOfflineDataActivity.this, new Observer<List<SchoolsOfflineEntity>>() {
                @Override
                public void onChanged(List<SchoolsOfflineEntity> offlineEntities) {
                    listLiveData.removeObservers(SchoolsOfflineDataActivity.this);
                    if (offlineEntities != null && offlineEntities.size() > 0) {

                        offlineReportBinding.tvEmpty.setVisibility(View.GONE);
                        offlineReportBinding.recyclerView.setVisibility(View.VISIBLE);

                        SchoolsOfflineDataAdapter adapter = new SchoolsOfflineDataAdapter(SchoolsOfflineDataActivity.this, offlineEntities);
                        offlineReportBinding.recyclerView.setLayoutManager(new LinearLayoutManager(SchoolsOfflineDataActivity.this));
                        offlineReportBinding.recyclerView.setAdapter(adapter);
                    } else {
                        offlineReportBinding.tvEmpty.setVisibility(View.VISIBLE);
                        offlineReportBinding.recyclerView.setVisibility(View.GONE);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void submitRecord(SchoolsOfflineEntity entity) {
        customSubmitAlert(getResources().getString(R.string.are_you_sure), true, entity);
    }

    @Override
    public void deleteRecord(SchoolsOfflineEntity entity) {
        customSubmitAlert(getResources().getString(R.string.are_you_sure_remove), false, entity);
    }

    private void customSubmitAlert(String msg, boolean flag, SchoolsOfflineEntity entity) {
        try {
            final Dialog dialog = new Dialog(SchoolsOfflineDataActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_confirmation);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(getString(R.string.app_name));
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button btDialogNo = dialog.findViewById(R.id.btDialogNo);
                btDialogNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        schoolsOfflineEntity = entity;
                        if (flag) {
                            InstSelectionViewModel selectionViewModel = new InstSelectionViewModel(getApplication());
                            LiveData<String> liveData = selectionViewModel.getRandomId(schoolsOfflineEntity.getInst_id());
                            liveData.observe(SchoolsOfflineDataActivity.this, new Observer<String>() {
                                @Override
                                public void onChanged(String value) {
                                    randomNo = value;
                                    if (Utils.checkInternetConnection(SchoolsOfflineDataActivity.this)) {
                                        setData();
                                    } else {
                                        Utils.customWarningAlert(SchoolsOfflineDataActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                                    }
                                }
                            });
                        } else {
                            removeRecord(false);
                        }
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void removeRecord(boolean flag) {
        File mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME +
                "/" + schoolsOfflineEntity.getInst_id());

        if (mediaStorageDir.isDirectory()) {
            String[] children = mediaStorageDir.list();
            for (int i = 0; i < children.length; i++)
                new File(mediaStorageDir, children[i]).delete();
            mediaStorageDir.delete();
        }
        schoolsOfflineViewModel.deleteSchoolsRecord(SchoolsOfflineDataActivity.this,
                schoolsOfflineEntity.getInst_id(), flag);

    }

    private void setPhotosData() {
        LiveData<List<UploadPhoto>> listLiveData = uploadPhotoViewModel.getPhotos(schoolsOfflineEntity.getInst_id());
        listLiveData.observe(SchoolsOfflineDataActivity.this, new Observer<List<UploadPhoto>>() {
            @Override
            public void onChanged(List<UploadPhoto> uploadPhotos) {
                if (uploadPhotos != null && uploadPhotos.size() > 0) {
                    for (int z = 0; z < uploadPhotos.size(); z++) {
                        if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.STOREROOM)) {
                            file_storeroom = new File(uploadPhotos.get(z).getPhoto_path());
                        }
                        if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.SICKROOM)) {
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
                    callPhotoSubmit();
                }
            }
        });
    }

    private void setData() {
        instSubmitRequest = new InstSubmitRequest();
        LiveData<GeneralInfoEntity> generalInfoEntityLiveData = instMainViewModel.getGeneralInfoData(schoolsOfflineEntity.getInst_id());
        generalInfoEntityLiveData.observe(SchoolsOfflineDataActivity.this, new Observer<GeneralInfoEntity>() {
            @Override
            public void onChanged(GeneralInfoEntity generalInfoEntity) {
                instSubmitRequest.setGeneral_info(generalInfoEntity);
            }
        });
        LiveData<List<StudAttendInfoEntity>> studAttenLiveData = instMainViewModel.getStudAttendInfoData(schoolsOfflineEntity.getInst_id());
        studAttenLiveData.observe(SchoolsOfflineDataActivity.this, new Observer<List<StudAttendInfoEntity>>() {
            @Override
            public void onChanged(List<StudAttendInfoEntity> studAttendInfoEntities) {
                instSubmitRequest.setStudent_attendence_info(studAttendInfoEntities);
            }
        });
        LiveData<List<StaffAttendanceEntity>> staffAttendLiveData = instMainViewModel.getStaffInfoData(schoolsOfflineEntity.getInst_id());
        staffAttendLiveData.observe(SchoolsOfflineDataActivity.this, new Observer<List<StaffAttendanceEntity>>() {
            @Override
            public void onChanged(List<StaffAttendanceEntity> staffAttendanceEntities) {
                instSubmitRequest.setStaff_attendence_info(staffAttendanceEntities);
            }
        });
        LiveData<MedicalInfoEntity> medicalInfoEntityLiveData = instMainViewModel.getMedicalInfo(schoolsOfflineEntity.getInst_id());
        medicalInfoEntityLiveData.observe(SchoolsOfflineDataActivity.this, new Observer<MedicalInfoEntity>() {
            @Override
            public void onChanged(MedicalInfoEntity medicalInfoEntity) {
                instSubmitRequest.setMedical_issues(medicalInfoEntity);
            }
        });
        LiveData<DietIssuesEntity> dietIssuesEntityLiveData = instMainViewModel.getDietInfoData(schoolsOfflineEntity.getInst_id());
        dietIssuesEntityLiveData.observe(SchoolsOfflineDataActivity.this, new Observer<DietIssuesEntity>() {
            @Override
            public void onChanged(DietIssuesEntity dietIssuesEntity) {
                instSubmitRequest.setDiet_issues(dietIssuesEntity);
            }
        });
        LiveData<InfraStructureEntity> infraStructureEntityLiveData = instMainViewModel.getInfrastructureInfoData(schoolsOfflineEntity.getInst_id());
        infraStructureEntityLiveData.observe(SchoolsOfflineDataActivity.this, new Observer<InfraStructureEntity>() {
            @Override
            public void onChanged(InfraStructureEntity infraStructureEntity) {
                instSubmitRequest.setInfra_maintenance(infraStructureEntity);
            }
        });
        LiveData<AcademicEntity> academicEntityLiveData = instMainViewModel.getAcademicInfoData(schoolsOfflineEntity.getInst_id());
        academicEntityLiveData.observe(SchoolsOfflineDataActivity.this, new Observer<AcademicEntity>() {
            @Override
            public void onChanged(AcademicEntity academicEntity) {
                instSubmitRequest.setAcademic_overview(academicEntity);
            }
        });
        LiveData<CoCurricularEntity> coCurricularEntityLiveData = instMainViewModel.getCocurricularInfoData(schoolsOfflineEntity.getInst_id());
        coCurricularEntityLiveData.observe(SchoolsOfflineDataActivity.this, new Observer<CoCurricularEntity>() {
            @Override
            public void onChanged(CoCurricularEntity coCurricularEntity) {
                instSubmitRequest.setCoCurricular_info(coCurricularEntity);
            }
        });
        LiveData<EntitlementsEntity> entitlementsEntityLiveData = instMainViewModel.getEntitlementInfoData(schoolsOfflineEntity.getInst_id());
        entitlementsEntityLiveData.observe(SchoolsOfflineDataActivity.this, new Observer<EntitlementsEntity>() {
            @Override
            public void onChanged(EntitlementsEntity entitlementsEntity) {
                instSubmitRequest.setEntitlements(entitlementsEntity);
            }
        });
        LiveData<RegistersEntity> registersEntityLiveData = instMainViewModel.getRegistersInfoData(schoolsOfflineEntity.getInst_id());
        registersEntityLiveData.observe(SchoolsOfflineDataActivity.this, new Observer<RegistersEntity>() {
            @Override
            public void onChanged(RegistersEntity registersEntity) {
                instSubmitRequest.setRegisters(registersEntity);
            }
        });
        LiveData<GeneralCommentsEntity> generalCommentsEntityLiveData = instMainViewModel.getGeneralCommentsInfoData(schoolsOfflineEntity.getInst_id());
        generalCommentsEntityLiveData.observe(SchoolsOfflineDataActivity.this, new Observer<GeneralCommentsEntity>() {
            @Override
            public void onChanged(GeneralCommentsEntity generalCommentsEntity) {
                instSubmitRequest.setGeneral_comments(generalCommentsEntity);
            }
        });

        if(mCurrentLocation!=null){
            instSubmitRequest.setLatitude(String.valueOf(mCurrentLocation.getLatitude()));
            instSubmitRequest.setLongitude(String.valueOf(mCurrentLocation.getLongitude()));
        }else{
            instSubmitRequest.setLatitude(null);
            instSubmitRequest.setLongitude(null);
        }
        setPhotosData();
    }


    private void callPhotoSubmit() {
        if (Utils.checkInternetConnection(SchoolsOfflineDataActivity.this)) {
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
            customProgressDialog.addText("Please wait...Uploading Photos");


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


            uploadPhotoViewModel.UploadImageServiceCall(partList, instSubmitRequest);
        } else {
            Utils.customWarningAlert(SchoolsOfflineDataActivity.this, getResources().getString(R.string.app_name), "Please check internet");
        }
    }

    @Override
    public void getData(SchemeSubmitResponse schemeSubmitResponse) {

    }

    public void getPhotoData(SchemePhotoSubmitResponse schemePhotoSubmitResponse) {
        customProgressDialog.hide();

        if (schemePhotoSubmitResponse != null && schemePhotoSubmitResponse.getStatusCode() != null && schemePhotoSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_CODE)) {
            instSubmitRequest.setOfficer_id(schoolsOfflineEntity.getOfficer_id());
            instSubmitRequest.setInstitute_id(schoolsOfflineEntity.getInst_id());
            instSubmitRequest.setInspection_time(Utils.getCurrentDateTime());
            instSubmitRequest.setDist_id(schoolsOfflineEntity.getDist_id());
            instSubmitRequest.setMandal_id(schoolsOfflineEntity.getMan_id());
            instSubmitRequest.setVillage_id(schoolsOfflineEntity.getVil_id());
            instSubmitRequest.setInstitute_name(schoolsOfflineEntity.getInst_name());
            instSubmitRequest.setDist_name(schoolsOfflineEntity.getDist_name());
            instSubmitRequest.setMandal_name(schoolsOfflineEntity.getMan_name());
            instSubmitRequest.setVillage_name(schoolsOfflineEntity.getVil_name());
            instSubmitRequest.setPhoto_key_id(randomNo);
            instSubmitRequest.setDevice_Id(Utils.getDeviceID(this));
            instSubmitRequest.setVersion_No(Utils.getVersionName(this));
            instSubmitRequest.getAcademic_overview().setLast_yr_ssc_percent(instSubmitRequest.getGeneral_comments().getAnemic_stud_cnt());

            if (Utils.checkInternetConnection(SchoolsOfflineDataActivity.this)) {
                customProgressDialog.show();
                customProgressDialog.addText(getString(R.string.uploading_data));
                instMainViewModel.submitInstDetails(instSubmitRequest);
            } else {
                Utils.customErrorAlert(SchoolsOfflineDataActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
            }
        } else if (schemePhotoSubmitResponse != null && schemePhotoSubmitResponse.getStatusCode() != null && schemePhotoSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_CODE)) {
            Utils.customErrorAlert(SchoolsOfflineDataActivity.this, getResources().getString(R.string.app_name), schemePhotoSubmitResponse.getStatusMessage());
        } else {
            Utils.customErrorAlert(SchoolsOfflineDataActivity.this, getResources().getString(R.string.app_name), getString(R.string.something));
        }
    }

    @Override
    public void getSubmitData(InstSubmitResponse schemeSubmitResponse) {
        customProgressDialog.hide();
        if (schemeSubmitResponse != null && schemeSubmitResponse.getStatusCode() != null && schemeSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
            instMainViewModel.deleteAllInspectionData(schoolsOfflineEntity.getInst_id());
            removeRecord(true);
        } else if (schemeSubmitResponse != null && schemeSubmitResponse.getStatusCode() != null && schemeSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {
            Utils.customErrorAlert(SchoolsOfflineDataActivity.this, getResources().getString(R.string.app_name), schemeSubmitResponse.getStatusMessage() + getString(R.string.failed_to_submit));
        } else {
            Utils.customErrorAlert(SchoolsOfflineDataActivity.this, getResources().getString(R.string.app_name), getResources().getString(R.string.something) + getString(R.string.failed_to_submit));
        }
    }

    private void showSnackBar(String str) {
        Snackbar.make(offlineReportBinding.cl, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg;
        if (e instanceof FileNotFoundException) {
            errMsg = "Failed, No Images found in storage";
        } else {
            errMsg = ErrorHandler.handleError(e, context);
        }
        showSnackBar(errMsg);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void schoolRecCount(int cnt) {

    }

    @Override
    public void deletedSchoolCount(int cnt) {
        try {
            if (cnt > 0) {
                Utils.customSyncOfflineSuccessAlert(SchoolsOfflineDataActivity.this, getResources().getString(R.string.app_name),
                        "Data deleted successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletedSchoolCountSubmitted(int cnt) {
        try {
            if (cnt > 0) {
                Utils.customSyncOfflineSuccessAlert(this, getResources().getString(R.string.app_name),
                        "Data submitted successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                    callPermissions();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
