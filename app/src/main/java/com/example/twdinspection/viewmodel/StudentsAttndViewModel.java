package com.example.twdinspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.Room.repository.ClassInfoRepository;
import com.example.twdinspection.source.studentAttendenceInfo.StudAttendInfoEntity;
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

    public LiveData<List<StudAttendInfoEntity>> getClassInfo(String inst_id) {
        LiveData<List<StudAttendInfoEntity>> classIdsList= mRepository.getClassIdsList(inst_id);
        return classIdsList;
    }

    public long updateClassInfo(String attendence_marked, String count_reg, String count_during_insp,
                                   String variance,int flag_completed,String inst_id,int class_id) {
       long flag=mRepository.updateClassInfo(attendence_marked,count_reg,count_during_insp,variance,flag_completed,inst_id,class_id);
        return flag;
    }

    public long updateClassInfo(StudAttendInfoEntity studAttendInfoEntity) {
       long flag=mRepository.updateClassInfo(studAttendInfoEntity);
        return flag;
    }
}
