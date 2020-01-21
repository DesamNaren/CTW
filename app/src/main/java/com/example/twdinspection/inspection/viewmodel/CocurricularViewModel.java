package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityCoCurricularBinding;
import com.example.twdinspection.databinding.ActivityMedicalBinding;
import com.example.twdinspection.inspection.Room.repository.CocurricularRepository;
import com.example.twdinspection.inspection.Room.repository.MedicalInfoRepository;
import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;

import java.util.List;

public class CocurricularViewModel extends ViewModel {

    private Application application;
    private CocurricularRepository mRepository;
    ActivityCoCurricularBinding binding;
    private LiveData<List<StudAchievementEntity>> studAchievementsLiveData;

    public CocurricularViewModel(ActivityCoCurricularBinding binding, Application application) {
        this.application = application;
        this.binding = binding;
        mRepository = new CocurricularRepository(application);

    }

    public long insertAchievementInfo(StudAchievementEntity studAchievementEntity) {
        return mRepository.insertAchievementInfo(studAchievementEntity);
    }

}
