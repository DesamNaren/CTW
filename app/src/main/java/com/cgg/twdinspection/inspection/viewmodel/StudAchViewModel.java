package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.databinding.ActivityCocurricularAchDetailsBinding;
import com.cgg.twdinspection.inspection.room.repository.StudAchievementsRepository;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.StudAchievementEntity;

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
            studAchievementsLiveData = mRepository.getStudAchvListLiveData();
        }
        return studAchievementsLiveData;
    }

    public long deleteAchievementsInfo(StudAchievementEntity studAchievementEntity) {
        return mRepository.deleteAchievementsInfo(studAchievementEntity);
    }
}
