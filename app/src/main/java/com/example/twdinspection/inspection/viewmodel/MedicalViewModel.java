package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityMedicalBinding;
import com.example.twdinspection.inspection.Room.repository.MedicalInfoRepository;
import com.example.twdinspection.inspection.source.MedicalAndHealth.CallHealthInfoEntity;
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
        return mRepository.insertMedicalInfo(medicalInfoEntity);
    }

    public long insertCallInfo(CallHealthInfoEntity callHealthInfoEntity) {
        return mRepository.insertCallInfo(callHealthInfoEntity);
    }

    public LiveData<Integer> getCallCnt() {
        return mRepository.getCallCnt();
    }
}
