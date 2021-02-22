package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.databinding.ActivityDietIssuesBinding;
import com.cgg.twdinspection.inspection.room.repository.DietIssuesInfoRepository;
import com.cgg.twdinspection.inspection.source.diet_issues.DietIssuesEntity;
import com.cgg.twdinspection.inspection.source.diet_issues.DietListEntity;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;

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

    public LiveData<List<DietListEntity>> getDietInfo(String inst_id) {
        LiveData<List<DietListEntity>> classIdsList = mRepository.getDietList(inst_id);
        return classIdsList;
    }

    public void insertDietInfo(List<DietListEntity> dietListEntities) {
        mRepository.insertDietInfo(dietListEntities);
    }


    public long updateDietIssuesInfo(DietIssuesEntity dietIssuesEntity) {
        long flag = mRepository.updateDietIssuesInfo(dietIssuesEntity);
        return flag;
    }

    public long deleteDietListInfo(String inst_id) {
        return mRepository.deleteDietListInfo(inst_id);
    }
}
