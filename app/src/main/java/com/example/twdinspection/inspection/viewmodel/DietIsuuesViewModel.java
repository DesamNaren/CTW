package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityDietIssuesBinding;
import com.example.twdinspection.inspection.Room.repository.DietIssuesInfoRepository;
import com.example.twdinspection.inspection.source.dietIssues.DietIssuesEntity;
import com.example.twdinspection.inspection.source.dietIssues.DietListEntity;
import com.example.twdinspection.inspection.source.inst_master.MasterInstituteInfo;

import java.util.List;

public class DietIsuuesViewModel extends ViewModel {

    private Application application;
    private ActivityDietIssuesBinding binding;
    private DietIssuesInfoRepository mRepository;

    public DietIsuuesViewModel(ActivityDietIssuesBinding binding, Application application) {
        this.binding = binding;
        this.application = application;
        mRepository = new DietIssuesInfoRepository(application);
    }

    public LiveData<MasterInstituteInfo> getMasterDietInfo(String inst_id) {
        LiveData<MasterInstituteInfo> classIdsList = mRepository.getMasterDietList(inst_id);
        return classIdsList;
    }

/*    public LiveData<List<DietListEntity>> getDietInfo(String inst_id) {
        LiveData<List<DietListEntity>> classIdsList = mRepository.getDietList(inst_id);
        return classIdsList;
    }*/

    public void insertDietInfo(List<DietListEntity> dietListEntities) {
        mRepository.insertDietInfo(dietListEntities);
    }


    public long insertDietIssuesInfo(DietIssuesEntity dietIssuesEntity) {
        long flag = mRepository.insertDietIssuesInfo(dietIssuesEntity);
        return flag;
    }
}
