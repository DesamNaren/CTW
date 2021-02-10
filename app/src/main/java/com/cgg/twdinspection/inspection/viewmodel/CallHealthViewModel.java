package com.cgg.twdinspection.inspection.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.inspection.room.repository.MedicalInfoRepository;
import com.cgg.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;

import java.util.List;

public class CallHealthViewModel extends ViewModel {

    private MedicalInfoRepository mRepository;
    private LiveData<List<CallHealthInfoEntity>> callListLiveData;

    Context context;

    CallHealthViewModel(Context application) {
        context = application;
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
