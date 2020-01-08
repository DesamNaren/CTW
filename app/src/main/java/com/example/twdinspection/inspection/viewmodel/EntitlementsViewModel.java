package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityAcademicBinding;
import com.example.twdinspection.databinding.ActivityEntitlementsBinding;
import com.example.twdinspection.inspection.Room.repository.AcademicRepository;
import com.example.twdinspection.inspection.Room.repository.EntitlementsRepository;
import com.example.twdinspection.inspection.source.AcademicOverview.AcademicOveriewEntity;
import com.example.twdinspection.inspection.source.EntitlementsDistribution.EntitlementsEntity;

public class EntitlementsViewModel extends ViewModel {

    private Application application;
    private ActivityEntitlementsBinding binding;
    private EntitlementsRepository mRepository;

    public EntitlementsViewModel(ActivityEntitlementsBinding binding, Application application) {
        this.binding = binding;
        this.application = application;
        mRepository = new EntitlementsRepository(application);

    }

    public long insertEntitlementsInfo(EntitlementsEntity entitlementsEntity) {
        long flag = mRepository.insertEntitlementsInfo(entitlementsEntity);
        return flag;
    }
}
