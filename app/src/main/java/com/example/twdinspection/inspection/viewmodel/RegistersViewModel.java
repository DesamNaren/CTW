package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityEntitlementsBinding;
import com.example.twdinspection.databinding.ActivityRegistersBinding;
import com.example.twdinspection.inspection.Room.repository.EntitlementsRepository;
import com.example.twdinspection.inspection.Room.repository.RegistersRepository;
import com.example.twdinspection.inspection.source.EntitlementsDistribution.EntitlementsEntity;
import com.example.twdinspection.inspection.source.RegistersUptoDate.RegistersEntity;

public class RegistersViewModel extends ViewModel {

    private Application application;
    private ActivityRegistersBinding binding;
    private RegistersRepository mRepository;

    public RegistersViewModel(ActivityRegistersBinding binding, Application application) {
        this.binding = binding;
        this.application = application;
        mRepository = new RegistersRepository(application);

    }

    public long insertRegistersInfo(RegistersEntity registersEntity) {
        long flag = mRepository.insertRegistersInfo(registersEntity);
        return flag;
    }
}
