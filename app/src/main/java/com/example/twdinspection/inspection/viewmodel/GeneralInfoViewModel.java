package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.twdinspection.inspection.Room.repository.GeneralInfoRepository;
import com.example.twdinspection.inspection.source.AcademicOverview.AcademicOveriewEntity;
import com.example.twdinspection.inspection.source.GeneralInformation.GeneralInfoEntity;

public class GeneralInfoViewModel extends AndroidViewModel {
    private GeneralInfoRepository mRepository;

    public GeneralInfoViewModel(@NonNull Application application) {
        super(application);
        mRepository = new GeneralInfoRepository(application);
    }


    public long insertGeneralInfo(GeneralInfoEntity generalInfoEntity) {
        long flag = mRepository.insertGeneralInfo(generalInfoEntity);
        return flag;
    }

}
