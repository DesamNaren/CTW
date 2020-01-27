package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.inspection.Room.repository.GeneralInfoRepository;
import com.example.twdinspection.inspection.Room.repository.MenuSectionsRepository;
import com.example.twdinspection.inspection.source.GeneralInformation.GeneralInfoEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.example.twdinspection.inspection.source.instMenuInfo.InstMenuInfoEntity;

import java.util.List;

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
