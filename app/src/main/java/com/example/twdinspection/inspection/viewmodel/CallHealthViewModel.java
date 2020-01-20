package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityCallHealthBinding;
import com.example.twdinspection.databinding.ActivityMedicalBinding;
import com.example.twdinspection.inspection.Room.repository.MedicalInfoRepository;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;

import java.util.List;

public class CallHealthViewModel extends ViewModel {

    private MedicalInfoRepository mRepository;
    private LiveData<List<CallHealthInfoEntity>> callListLiveData;

    Context context;

    CallHealthViewModel(Context application) {
        context=application;
        mRepository = new MedicalInfoRepository(context);
        callListLiveData = new MutableLiveData<>();
    }


    public LiveData<List<CallHealthInfoEntity>> getCallHealthData() {
        if (callListLiveData != null) {
            callListLiveData = mRepository.getCallListLiveData();
        }
        return callListLiveData;
    }

    public long deleteCallInfo(CallHealthInfoEntity callHealthInfoEntity) {
        return mRepository.deleteCallInfo(callHealthInfoEntity);
    }
}
