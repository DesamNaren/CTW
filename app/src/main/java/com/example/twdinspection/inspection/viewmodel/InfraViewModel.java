package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityInfrastructureBinding;
import com.example.twdinspection.inspection.Room.repository.InfraStructureRepository;
import com.example.twdinspection.inspection.source.infra_maintenance.InfraStructureEntity;

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
