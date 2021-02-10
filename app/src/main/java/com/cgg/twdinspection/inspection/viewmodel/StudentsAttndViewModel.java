package com.cgg.twdinspection.inspection.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.databinding.ActivityStudentsAttendanceBinding;
import com.cgg.twdinspection.inspection.room.repository.ClassInfoRepository;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.source.student_attendence_info.StudAttendInfoEntity;

import java.util.List;

public class StudentsAttndViewModel extends ViewModel {

    private MutableLiveData<List<StudAttendInfoEntity>> studentAttndLiveData;
    private Context application;
    private ActivityStudentsAttendanceBinding binding;
    private ClassInfoRepository mRepository;

    StudentsAttndViewModel(ActivityStudentsAttendanceBinding binding, Context application) {
        this.binding = binding;
        this.application = application;
        mRepository = new ClassInfoRepository(application);
    }

    public StudentsAttndViewModel(Context application) {
        this.application = application;
        mRepository = new ClassInfoRepository(application);
    }

    public LiveData<MasterInstituteInfo> getMasterClassInfo(String inst_id) {
        return mRepository.getMasterClassIdsList(inst_id);
    }

    public LiveData<List<StudAttendInfoEntity>> getClassInfo(String inst_id) {
        return mRepository.getClassIdsList(inst_id);
    }


    public long updateClassInfo(StudAttendInfoEntity studAttendInfoEntity) {
        return mRepository.updateClassInfo(studAttendInfoEntity);
    }

    public void insertClassInfo(List<StudAttendInfoEntity> studAttendInfoEntityList) {
        mRepository.insertClassInfo(studAttendInfoEntityList);
    }

}
