package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityCoCurricularBinding;
import com.example.twdinspection.databinding.ActivityMedicalBinding;
import com.example.twdinspection.inspection.Room.repository.CocurricularRepository;
import com.example.twdinspection.inspection.Room.repository.MedicalInfoRepository;
import com.example.twdinspection.inspection.source.cocurriularActivities.CoCurricularEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.PlantsEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;

import java.util.List;

public class CocurricularViewModel extends ViewModel {

    private Application application;
    private CocurricularRepository mRepository;
    ActivityCoCurricularBinding binding;
    private LiveData<List<StudAchievementEntity>> studAchievementsLiveData;
    private LiveData<List<PlantsEntity>> pListLiveData;

    public CocurricularViewModel(ActivityCoCurricularBinding binding, Application application) {
        this.application = application;
        this.binding = binding;
        studAchievementsLiveData = new MutableLiveData<>();
        pListLiveData = new MutableLiveData<>();
        mRepository = new CocurricularRepository(application);

    }

    public long insertAchievementInfo(StudAchievementEntity studAchievementEntity) {
        return mRepository.insertAchievementInfo(studAchievementEntity);
    }

    public long insertPlantInfo(PlantsEntity plantsEntity) {
        return mRepository.insertPlantInfo(plantsEntity);
    }



    public long insertCoCurricularInfo(CoCurricularEntity coCurricularEntity) {
        return mRepository.insertCoCurricularInfo(coCurricularEntity);
    }


    public LiveData<List<StudAchievementEntity>> getAchievementInfo() {
        if (studAchievementsLiveData != null) {
            studAchievementsLiveData = mRepository.getStudentAchLiveData();
        }
        return studAchievementsLiveData;
    }

    public LiveData<Integer> getAchievementsCnt() {
        return mRepository.getAchievementsCnt();
    }
    public LiveData<Integer> getPlantsCnt() {
        return mRepository.getPlantsCnt();
    }

    public LiveData<List<PlantsEntity>> getPlantsInfo() {
        if (pListLiveData != null) {
            pListLiveData = mRepository.getPlantsListLiveData();
        }
        return pListLiveData;
    }
}
