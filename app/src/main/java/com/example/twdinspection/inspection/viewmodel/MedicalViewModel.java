package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityMedicalBinding;
import com.example.twdinspection.inspection.Room.repository.MedicalInfoRepository;
import com.example.twdinspection.inspection.source.MedicalAndHealth.MedicalInfoEntity;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;

import java.util.List;

public class MedicalViewModel extends ViewModel {

    private Application application;
    private ActivityMedicalBinding binding;
    private MedicalInfoRepository mRepository;

    public MedicalViewModel(ActivityMedicalBinding binding, Application application) {
        this.binding = binding;
        this.application = application;
        mRepository = new MedicalInfoRepository(application);

    }


    public long insertMedicalInfo(MedicalInfoEntity medicalInfoEntity) {
       long flag=mRepository.insertMedicalInfo(medicalInfoEntity);
        return flag;
    }
}
