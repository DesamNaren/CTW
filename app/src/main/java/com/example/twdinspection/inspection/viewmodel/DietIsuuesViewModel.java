package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityDietIssuesBinding;
import com.example.twdinspection.inspection.Room.repository.DietIssuesInfoRepository;
import com.example.twdinspection.inspection.source.DiestIssues.DietIssuesEntity;

public class DietIsuuesViewModel extends ViewModel {

    private Application application;
    private ActivityDietIssuesBinding binding;
    private DietIssuesInfoRepository mRepository;

    public DietIsuuesViewModel(ActivityDietIssuesBinding binding, Application application) {
        this.binding = binding;
        this.application = application;
        mRepository = new DietIssuesInfoRepository(application);
    }


    public long insertDietIssuesInfo(DietIssuesEntity dietIssuesEntity) {
       long flag=mRepository.insertDietIssuesInfo(dietIssuesEntity);
        return flag;
    }
}
