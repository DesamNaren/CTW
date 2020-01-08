package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityGeneralCommentsBinding;
import com.example.twdinspection.databinding.ActivityRegistersBinding;
import com.example.twdinspection.inspection.Room.repository.RegistersRepository;
import com.example.twdinspection.inspection.source.RegistersUptoDate.RegistersEntity;

public class GenCommentsViewModel extends ViewModel {

    private Application application;
    private ActivityGeneralCommentsBinding binding;
    private RegistersRepository mRepository;

    public GenCommentsViewModel(ActivityGeneralCommentsBinding binding, Application application) {
        this.binding = binding;
        this.application = application;
        mRepository = new RegistersRepository(application);

    }

    public long insertGeneralCommentsInfo(RegistersEntity registersEntity) {
        long flag = mRepository.insertRegistersInfo(registersEntity);
        return flag;
    }
}
