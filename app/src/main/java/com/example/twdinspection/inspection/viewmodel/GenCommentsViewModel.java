package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityGeneralCommentsBinding;
import com.example.twdinspection.databinding.ActivityRegistersBinding;
import com.example.twdinspection.inspection.Room.repository.GenCommentsRepository;
import com.example.twdinspection.inspection.Room.repository.RegistersRepository;
import com.example.twdinspection.inspection.source.GeneralComments.GeneralCommentsEntity;
import com.example.twdinspection.inspection.source.RegistersUptoDate.RegistersEntity;

public class GenCommentsViewModel extends ViewModel {

    private Application application;
    private ActivityGeneralCommentsBinding binding;
    private GenCommentsRepository mRepository;

    public GenCommentsViewModel(ActivityGeneralCommentsBinding binding, Application application) {
        this.binding = binding;
        this.application = application;
        mRepository = new GenCommentsRepository(application);

    }

    public long insertGeneralCommentsInfo(GeneralCommentsEntity generalCommentsEntity) {
        long flag = mRepository.insertGeneralCommentsInfo(generalCommentsEntity);
        return flag;
    }
}
