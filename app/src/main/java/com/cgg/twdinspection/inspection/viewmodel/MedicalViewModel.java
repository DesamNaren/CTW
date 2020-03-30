package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.databinding.ActivityMedicalBinding;
import com.cgg.twdinspection.inspection.room.repository.MedicalInfoRepository;
import com.cgg.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;

import java.util.List;

public class MedicalViewModel extends ViewModel {

    private MedicalInfoRepository mRepository;

    MedicalViewModel(ActivityMedicalBinding binding, Application application) {
        LiveData<List<CallHealthInfoEntity>> callListLiveData = new MutableLiveData<>();
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
