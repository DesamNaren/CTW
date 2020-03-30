package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.databinding.ActivityRegistersBinding;
import com.cgg.twdinspection.inspection.room.repository.RegistersRepository;
import com.cgg.twdinspection.inspection.source.registers_upto_date.RegistersEntity;

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
