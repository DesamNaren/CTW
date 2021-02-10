package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.inspection.room.repository.MedicalInfoRepository;
import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalDetailsBean;

import java.util.List;

public class MedicalDetailsViewModel extends AndroidViewModel {

    private MutableLiveData<List<MedicalDetailsBean>> medicalMutableLiveData;
    private MedicalInfoRepository mRepository;

    public MedicalDetailsViewModel(@NonNull Application application) {
        super(application);
        medicalMutableLiveData = new MutableLiveData<>();
        mRepository = new MedicalInfoRepository(application);
    }

    public LiveData<List<MedicalDetailsBean>> getMedicalDetails() {
        return mRepository.getMedicalListLiveData();
    }

    public void insertMedicalDetailsInfo(List<MedicalDetailsBean> medicalDetailsBeans) {
        mRepository.insertMedicalDetailsInfo(medicalDetailsBeans);
    }

}
