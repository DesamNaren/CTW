package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.databinding.InstMainActivityBinding;
import com.example.twdinspection.inspection.Room.repository.MenuSectionsRepository;
import com.example.twdinspection.inspection.interfaces.InstSubmitInterface;
import com.example.twdinspection.inspection.source.AcademicOverview.AcademicEntity;
import com.example.twdinspection.inspection.source.EntitlementsDistribution.EntitlementsEntity;
import com.example.twdinspection.inspection.source.GeneralComments.GeneralCommentsEntity;
import com.example.twdinspection.inspection.source.GeneralInformation.GeneralInfoEntity;
import com.example.twdinspection.inspection.source.InfrastructureAndMaintenance.InfraStructureEntity;
import com.example.twdinspection.inspection.source.RegistersUptoDate.RegistersEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.CoCurricularEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.example.twdinspection.inspection.source.dietIssues.DietIssuesEntity;
import com.example.twdinspection.inspection.source.instMenuInfo.InstMenuInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;
import com.example.twdinspection.inspection.source.staffAttendance.StaffAttendanceEntity;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;
import com.example.twdinspection.inspection.source.submit.InstSubmitRequest;
import com.example.twdinspection.inspection.source.submit.InstSubmitResponse;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.example.twdinspection.schemes.interfaces.SchemeSubmitInterface;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitRequest;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitResponse;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstMainViewModel extends AndroidViewModel {
    private MenuSectionsRepository mRepository;
    private LiveData<List<InstMenuInfoEntity>> instMenuInfoEntities;
    private InstSubmitInterface instSubmitInterface;
    InstMainActivityBinding binding;
    LiveData<GeneralInfoEntity> generalInfoEntityLiveData;
    LiveData<List<StudAttendInfoEntity>> studAttendInfo;
    LiveData<List<StaffAttendanceEntity>> staffAttendInfo;
    LiveData<MedicalInfoEntity> medicalInfoEntityLiveData;
    LiveData<DietIssuesEntity> dietIssuesEntityLiveData;
    LiveData<InfraStructureEntity> infraStructureEntityLiveData;
    LiveData<AcademicEntity> academicEntityLiveData;
    LiveData<CoCurricularEntity> coCurricularEntityLiveData;
    LiveData<EntitlementsEntity> entitlementsEntityLiveData;
    LiveData<RegistersEntity> registersEntityLiveData;
    LiveData<GeneralCommentsEntity> generalCommentsEntityLiveData;
    private ErrorHandlerInterface errorHandlerInterface;
    Context context;

    public InstMainViewModel(Application application) {
        super(application);
        mRepository = new MenuSectionsRepository(application);
    }

    public InstMainViewModel(InstMainActivityBinding binding, Application application, Context context) {
        super(application);
        this.context = context;
        this.binding = binding;
        errorHandlerInterface = (ErrorHandlerInterface) context;
        instSubmitInterface = (InstSubmitInterface) context;
        generalInfoEntityLiveData=new MutableLiveData<>();
        studAttendInfo=new MutableLiveData<>();
        staffAttendInfo=new MutableLiveData<>();
        medicalInfoEntityLiveData=new MutableLiveData<>();
        dietIssuesEntityLiveData=new MutableLiveData<>();
        infraStructureEntityLiveData=new MutableLiveData<>();
        academicEntityLiveData=new MutableLiveData<>();
        coCurricularEntityLiveData=new MutableLiveData<>();
        entitlementsEntityLiveData=new MutableLiveData<>();
        registersEntityLiveData=new MutableLiveData<>();
        generalCommentsEntityLiveData=new MutableLiveData<>();
        mRepository = new MenuSectionsRepository(application);
        instMenuInfoEntities = new MutableLiveData<>(); instMenuInfoEntities = new MutableLiveData<>();
    }

    public void insertMenuSections(List<InstMenuInfoEntity> menuInfoEntities) {
        mRepository.insertMenuSections(menuInfoEntities);
    }

    public LiveData<List<InstMenuInfoEntity>> getAllSections() {
        if (instMenuInfoEntities != null) {
            instMenuInfoEntities = mRepository.getSections();
        }
        return instMenuInfoEntities;
    }

    public void submitInstDetails(InstSubmitRequest instSubmitRequest) {
        try {
            binding.appbar.progress.setVisibility(View.VISIBLE);
            TWDService twdService = TWDService.Factory.create("school");
            Gson gson=new Gson();
           String request= gson.toJson(instSubmitRequest);
            Log.i("request",""+request);
            twdService.getInstSubmitResponse(instSubmitRequest).enqueue(new Callback<InstSubmitResponse>() {
                @Override
                public void onResponse(@NotNull Call<InstSubmitResponse> call, @NotNull Response<InstSubmitResponse> response) {
                    binding.appbar.progress.setVisibility(View.GONE);
                    instSubmitInterface.getData(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<InstSubmitResponse> call, @NotNull Throwable t) {
                    binding.appbar.progress.setVisibility(View.GONE);
                    errorHandlerInterface.handleError(t, context);
                }
            });
        }catch (Exception e){
            binding.appbar.progress.setVisibility(View.GONE);
            e.printStackTrace();
        }

    }

    public LiveData<GeneralInfoEntity> getGeneralInfoData() {
        if (generalInfoEntityLiveData != null) {
            generalInfoEntityLiveData = mRepository.getGeneralInfo();
        }
        return generalInfoEntityLiveData;
    }

    public LiveData<List<StudAttendInfoEntity>> getStudAttendInfoData() {
        if (studAttendInfo != null) {
            studAttendInfo = mRepository.getStudAttendInfo();
        }
        return studAttendInfo;
    }

    public LiveData<List<StaffAttendanceEntity>> getStaffInfoData() {
        if (staffAttendInfo != null) {
            staffAttendInfo = mRepository.getStaffInfo();
        }
        return staffAttendInfo;
    }

    public LiveData<MedicalInfoEntity> getMedicalInfo() {
        if (medicalInfoEntityLiveData != null) {
            medicalInfoEntityLiveData = mRepository.getMedicalInfo();
        }
        return medicalInfoEntityLiveData;
    }

    public LiveData<DietIssuesEntity> getDietInfoData() {
        if (dietIssuesEntityLiveData != null) {
            dietIssuesEntityLiveData = mRepository.getDietInfo();
        }
        return dietIssuesEntityLiveData;
    }

    public LiveData<InfraStructureEntity> getInfrastructureInfoData() {
        if (infraStructureEntityLiveData != null) {
            infraStructureEntityLiveData = mRepository.getInfrastructureInfo();
        }
        return infraStructureEntityLiveData;
    }

    public LiveData<AcademicEntity> getAcademicInfoData() {
        if (academicEntityLiveData != null) {
            academicEntityLiveData = mRepository.getAcademicInfo();
        }
        return academicEntityLiveData;
    }

    public LiveData<CoCurricularEntity> getCocurricularInfoData() {
        if (coCurricularEntityLiveData != null) {
            coCurricularEntityLiveData = mRepository.getCocurricularInfo();
        }
        return coCurricularEntityLiveData;
    }

    public LiveData<EntitlementsEntity> getEntitlementInfoData() {
        if (entitlementsEntityLiveData != null) {
            entitlementsEntityLiveData = mRepository.getEntitlementInfo();
        }
        return entitlementsEntityLiveData;
    }

    public LiveData<RegistersEntity> getRegistersInfoData() {
        if (registersEntityLiveData != null) {
            registersEntityLiveData = mRepository.getRegistersInfo();
        }
        return registersEntityLiveData;
    }

    public LiveData<GeneralCommentsEntity> getGeneralCommentsInfoData() {
        if (generalCommentsEntityLiveData != null) {
            generalCommentsEntityLiveData = mRepository.getGeneralCommentsInfo();
        }
        return generalCommentsEntityLiveData;
    }
    public long updateSectionInfo(String time, int id, String instId) {
        return mRepository.updateSectionInfo(time, id, instId);
    }

    public LiveData<Integer> getMenuRecordsCount() {
        return mRepository.getMenuRecordsCount();
    }

    /* public int getSectionId(String name) {
         return mRepository.getSectionId(name);
     }*/
    public LiveData<Integer> getSectionId(String name) {
        return mRepository.getSectionId(name);
    }
}
