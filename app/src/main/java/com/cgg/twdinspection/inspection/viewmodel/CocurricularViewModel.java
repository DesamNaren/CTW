package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.databinding.ActivityCoCurricularBinding;
import com.cgg.twdinspection.inspection.room.repository.CocurricularRepository;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.CoCurricularEntity;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.PlantsEntity;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.StudAchievementEntity;

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


    public long deletePlantsInfo() {
        return mRepository.deletePlantsInfo();
    }
}
