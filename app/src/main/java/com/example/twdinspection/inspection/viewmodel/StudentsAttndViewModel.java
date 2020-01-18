package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.inspection.Room.repository.ClassInfoRepository;
import com.example.twdinspection.inspection.source.inst_master.InstMasterResponse;
import com.example.twdinspection.inspection.source.inst_master.MasterClassInfo;
import com.example.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;
import com.example.twdinspection.databinding.ActivityStudentsAttendanceBinding;

import java.util.List;

public class StudentsAttndViewModel extends ViewModel {

    private MutableLiveData<List<StudAttendInfoEntity>> studentAttndLiveData;
    private Application application;
    private ActivityStudentsAttendanceBinding binding;
    private ClassInfoRepository mRepository;

    public StudentsAttndViewModel(ActivityStudentsAttendanceBinding binding, Application application) {
        this.binding = binding;
        this.application = application;
        mRepository = new ClassInfoRepository(application);

    }

    public LiveData<MasterInstituteInfo> getMasterClassInfo(String inst_id) {
        LiveData<MasterInstituteInfo> classIdsList = mRepository.getMasterClassIdsList(inst_id);
        return classIdsList;
    }

     public LiveData<List<StudAttendInfoEntity>> getClassInfo(String inst_id) {
         LiveData<List<StudAttendInfoEntity>> classIdsList = mRepository.getClassIdsList(inst_id);
        return classIdsList;
    }

    public long updateClassInfo(StudAttendInfoEntity studAttendInfoEntity) {
        long flag = mRepository.updateClassInfo(studAttendInfoEntity);
        return flag;
    }

    public void insertClassInfo(List<StudAttendInfoEntity> studAttendInfoEntityList) {
        mRepository.insertClassInfo(studAttendInfoEntityList);
    }
}
