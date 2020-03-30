package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.databinding.ActivityPlantsInfoBinding;
import com.cgg.twdinspection.inspection.room.repository.PlantsInfoRepository;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.PlantsEntity;

import java.util.List;

public class PlantsInfoViewModel extends ViewModel {

    Application application;
    PlantsInfoRepository mRepository;
    ActivityPlantsInfoBinding binding;
    LiveData<List<PlantsEntity>> plantsLiveData;

    public PlantsInfoViewModel(ActivityPlantsInfoBinding binding, Application application) {
        this.application = application;
        this.binding = binding;
        mRepository = new PlantsInfoRepository(application);
        plantsLiveData =new MutableLiveData<>();
    }

    public LiveData<List<PlantsEntity>> getPlantsData() {
        if (plantsLiveData != null) {
            plantsLiveData = mRepository.getPlantsListLiveData();
        }
        return plantsLiveData;
    }

    public long deleteAchievementsInfo(PlantsEntity plantsEntity) {
        return mRepository.deletePlantsInfo(plantsEntity);
    }
}
