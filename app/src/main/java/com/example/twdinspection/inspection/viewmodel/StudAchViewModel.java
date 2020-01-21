package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityCocurricularAchDetailsBinding;
import com.example.twdinspection.inspection.Room.repository.StudAchievementsRepository;
import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;

import java.util.List;

public class StudAchViewModel extends ViewModel {

    Application application;
    StudAchievementsRepository mRepository;
    ActivityCocurricularAchDetailsBinding binding;
    LiveData<List<StudAchievementEntity>> studAchievementsLiveData;

    public StudAchViewModel(ActivityCocurricularAchDetailsBinding binding, Application application) {
        this.application = application;
        this.binding = binding;
        mRepository = new StudAchievementsRepository(application);
        studAchievementsLiveData=new MutableLiveData<>();
    }

    public LiveData<List<StudAchievementEntity>> getStudAchievementsData() {
        if (studAchievementsLiveData != null) {
            studAchievementsLiveData = mRepository.getCallListLiveData();
        }
        return studAchievementsLiveData;
    }

    public long deleteAchievementsInfo(StudAchievementEntity studAchievementEntity) {
        return mRepository.deleteAchievementsInfo(studAchievementEntity);
    }
}
