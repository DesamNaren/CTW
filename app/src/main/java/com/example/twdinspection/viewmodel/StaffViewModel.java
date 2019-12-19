package com.example.twdinspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.Room.repository.StaffInfoRepository;
import com.example.twdinspection.databinding.ActivityStaffAttBinding;
import com.example.twdinspection.source.staffAttendance.StaffAttendanceEntity;

import java.util.List;

public class StaffViewModel extends ViewModel {

    private ActivityStaffAttBinding binding;
    private Context context;
    private StaffInfoRepository staffInfoRepository;

    StaffViewModel(ActivityStaffAttBinding binding, Context context, Application application) {
        this.binding = binding;
        this.context = context;
        staffInfoRepository = new StaffInfoRepository(application);
    }

    public LiveData<List<StaffAttendanceEntity>> getStaffInfo(String inst_id) {
        LiveData<List<StaffAttendanceEntity>> staffInfoList= staffInfoRepository.getStaffInfoList(inst_id);
        return staffInfoList;
    }




}
