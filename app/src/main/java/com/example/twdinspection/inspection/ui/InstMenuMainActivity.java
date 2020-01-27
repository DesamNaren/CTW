package com.example.twdinspection.inspection.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.common.ErrorHandler;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.InstMainActivityBinding;
import com.example.twdinspection.inspection.adapter.MenuSectionsAdapter;
import com.example.twdinspection.inspection.interfaces.InstSubmitInterface;
import com.example.twdinspection.inspection.source.AcademicOverview.AcademicEntity;
import com.example.twdinspection.inspection.source.EntitlementsDistribution.EntitlementsEntity;
import com.example.twdinspection.inspection.source.GeneralComments.GeneralCommentsEntity;
import com.example.twdinspection.inspection.source.GeneralInformation.GeneralInfoEntity;
import com.example.twdinspection.inspection.source.InfrastructureAndMaintenance.InfraStructureEntity;
import com.example.twdinspection.inspection.source.RegistersUptoDate.RegistersEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.CoCurricularEntity;
import com.example.twdinspection.inspection.source.dietIssues.DietIssuesEntity;
import com.example.twdinspection.inspection.source.instMenuInfo.InstMenuInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;
import com.example.twdinspection.inspection.source.staffAttendance.StaffAttendanceEntity;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;
import com.example.twdinspection.inspection.source.submit.InstSubmitRequest;
import com.example.twdinspection.inspection.source.submit.InstSubmitResponse;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class InstMenuMainActivity extends AppCompatActivity
        implements ErrorHandlerInterface, InstSubmitInterface {
    InstMainActivityBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String instId, officer_id, dist_id, mand_id, vill_id;
    boolean submitFlag = false, generalInfoFlag = false, studAttendFlag = false, staffAttendFlag = false, medicalFlag = false, dietFlag = false, infraFlag = false, academicFlag = false, cocurricularFlag = false, entitlementsFlag = false, regFlag = false, generalCommentsFlag = false;
    InstMainViewModel instMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.inst_main_activity);
        binding.appbar.header.syncIv.setVisibility(View.VISIBLE);
        binding.appbar.header.ivHome.setVisibility(View.GONE);
        binding.appbar.header.headerTitle.setText("Dashboard");
        binding.appbar.header.backBtn.setVisibility(View.GONE);

        instMainViewModel = new InstMainViewModel(binding, getApplication(), InstMenuMainActivity.this);
        binding.setViewmodel(instMainViewModel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        LiveData<List<InstMenuInfoEntity>> arrayListLiveData = instMainViewModel.getAllSections();
        sharedPreferences = TWDApplication.get(this).getPreferences();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        officer_id = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        dist_id = String.valueOf(sharedPreferences.getInt(AppConstants.DIST_ID, 0));
        mand_id = String.valueOf(sharedPreferences.getInt(AppConstants.MAN_ID, 0));
        vill_id = String.valueOf(sharedPreferences.getInt(AppConstants.VILL_ID, 0));

        arrayListLiveData.observe(this, new Observer<List<InstMenuInfoEntity>>() {
            @Override
            public void onChanged(List<InstMenuInfoEntity> menuInfoEntities) {
                arrayListLiveData.removeObservers(InstMenuMainActivity.this);

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

        binding.appbar.header.syncIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = true;
                for (int i = 0; i < arrayListLiveData.getValue().size(); i++) {
                    if (arrayListLiveData.getValue().get(i).getFlag_completed() == 0) {
                        flag = false;
                    }
                }
                if (flag) {
                    if (Utils.checkInternetConnection(InstMenuMainActivity.this)) {
                        submitCall();
                    } else {
                        Utils.customWarningAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                    }
                } else {
//                    submitCall();
                    Utils.customWarningAlert(InstMenuMainActivity.this, getResources().getString(R.string.app_name), "Please inspect all the sections");
                }
            }
        });

        binding.appbar.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InstMenuMainActivity.this, GeneralInfoActivity.class));
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
            InstSubmitRequest instSubmitRequest = new InstSubmitRequest();
            LiveData<GeneralInfoEntity> generalInfoEntityLiveData = instMainViewModel.getGeneralInfoData();
            generalInfoEntityLiveData.observe(InstMenuMainActivity.this, new Observer<GeneralInfoEntity>() {
                @Override
                public void onChanged(GeneralInfoEntity generalInfoEntity) {
                    generalInfoEntityLiveData.removeObservers(InstMenuMainActivity.this);
                    if (generalInfoEntity != null) {
                        generalInfoFlag = true;
                        instSubmitRequest.setGeneral_info(generalInfoEntity);
                        submitFinalCall(instSubmitRequest);
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
                        submitFinalCall(instSubmitRequest);
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
                        submitFinalCall(instSubmitRequest);
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
                        submitFinalCall(instSubmitRequest);
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
                        submitFinalCall(instSubmitRequest);
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
                        submitFinalCall(instSubmitRequest);
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
                        submitFinalCall(instSubmitRequest);
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
                        instSubmitRequest.setCoCurricularEntity(coCurricularEntity);
                        submitFinalCall(instSubmitRequest);
                    } else {
                        instSubmitRequest.setCoCurricularEntity(null);
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
                        submitFinalCall(instSubmitRequest);
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
                        submitFinalCall(instSubmitRequest);
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
                        submitFinalCall(instSubmitRequest);
                    } else {
                        instSubmitRequest.setGeneral_comments(null);
                    }
                }
            });

        }

    }

    void submitFinalCall(InstSubmitRequest instSubmitRequest) {
        if (!submitFlag && generalInfoFlag && studAttendFlag && staffAttendFlag && medicalFlag && dietFlag && infraFlag && cocurricularFlag &&
                academicFlag && entitlementsFlag && regFlag && generalCommentsFlag) {
            instSubmitRequest.setOfficer_id(officer_id);
            instSubmitRequest.setInstitute_id(instId);
            instSubmitRequest.setInspection_time(Utils.getCurrentDateTime());
            instSubmitRequest.setDist_id(dist_id);
            instSubmitRequest.setMandal_id(mand_id);
            instSubmitRequest.setVillage_id(vill_id);
            instMainViewModel.submitInstDetails(instSubmitRequest);
            submitFlag = true;
        }
    }

    @Override
    public void getData(InstSubmitResponse schemeSubmitResponse) {
        if (schemeSubmitResponse != null && schemeSubmitResponse.getStatusCode() != null && schemeSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
            CallSuccessAlert(schemeSubmitResponse.getStatusMessage());
        } else if (schemeSubmitResponse != null && schemeSubmitResponse.getStatusCode() != null && schemeSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {
            submitFlag = false;
            Snackbar.make(binding.appbar.root, schemeSubmitResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
        } else {
            submitFlag = false;
            Snackbar.make(binding.appbar.root, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void CallSuccessAlert(String msg) {
        Utils.customSuccessAlert(this, getResources().getString(R.string.app_name), msg);
    }

    @Override
    public void handleError(Throwable e, Context context) {
        String errMsg = ErrorHandler.handleError(e, context);
        Snackbar.make(binding.appbar.root, errMsg, Snackbar.LENGTH_SHORT).show();
    }



    @Override
    public void onBackPressed() {
        try {

        } catch (Exception e) {
            InstMenuMainActivity.this.finish();
        }
    }

}

