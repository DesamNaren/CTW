package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.databinding.ActivityInfrastructureBinding;
import com.cgg.twdinspection.inspection.room.repository.InfraStructureRepository;
import com.cgg.twdinspection.inspection.source.infra_maintenance.InfraStructureEntity;

public class InfraViewModel extends ViewModel {

    private Application application;
    private ActivityInfrastructureBinding binding;
    private InfraStructureRepository mRepository;

    public InfraViewModel(ActivityInfrastructureBinding binding, Application application) {
        this.binding = binding;
        this.application = application;
        mRepository = new InfraStructureRepository(application);

    }

    public long insertInfraStructureInfo(InfraStructureEntity infrastuctureEntity) {
       long flag=mRepository.insertInfraStructureInfo(infrastuctureEntity);
        return flag;
    }
}
