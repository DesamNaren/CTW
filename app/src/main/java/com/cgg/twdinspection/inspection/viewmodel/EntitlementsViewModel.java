package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.databinding.ActivityEntitlementsBinding;
import com.cgg.twdinspection.inspection.room.repository.EntitlementsRepository;
import com.cgg.twdinspection.inspection.source.entitlements_distribution.EntitlementsEntity;

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
