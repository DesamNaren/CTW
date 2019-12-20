package com.example.twdinspection.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.Room.repository.ClassInfoRepository;
import com.example.twdinspection.Room.repository.GeneralInfoRepository;
import com.example.twdinspection.databinding.ActivityStudentsAttendanceBinding;
import com.example.twdinspection.source.GeneralInformation.GeneralInformationEntity;

public class GeneralInfoViewModel extends AndroidViewModel {
    private GeneralInfoRepository mRepository;

    public GeneralInfoViewModel(@NonNull Application application) {
        super(application);
        mRepository = new GeneralInfoRepository(application);
    }

    public long updateClassInfo(String attendence_marked, String count_reg, String count_during_insp,
                                String variance, int flag_completed, String inst_id, int class_id) {
        return mRepository.updateClassInfo(attendence_marked, count_reg, count_during_insp, variance, flag_completed, inst_id, class_id);
    }

    public long insertClassInfo(GeneralInformationEntity generalInformationEntity) {
        return mRepository.insertGeneralInfo(generalInformationEntity);
    }
}
