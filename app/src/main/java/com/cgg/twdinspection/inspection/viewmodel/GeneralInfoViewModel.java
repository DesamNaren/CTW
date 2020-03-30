package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cgg.twdinspection.inspection.room.repository.GeneralInfoRepository;
import com.cgg.twdinspection.inspection.source.general_information.GeneralInfoEntity;

public class GeneralInfoViewModel extends AndroidViewModel {
    private GeneralInfoRepository mRepository;

    public GeneralInfoViewModel(@NonNull Application application) {
        super(application);
        mRepository = new GeneralInfoRepository(application);
    }

    public long insertGeneralInfo(GeneralInfoEntity generalInfoEntity) {
        return mRepository.insertGeneralInfo(generalInfoEntity);
    }




}
