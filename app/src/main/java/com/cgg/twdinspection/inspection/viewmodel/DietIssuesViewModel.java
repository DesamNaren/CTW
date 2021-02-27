package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.databinding.ActivityDietIssuesBinding;
import com.cgg.twdinspection.inspection.room.repository.DietIssuesInfoRepository;
import com.cgg.twdinspection.inspection.source.diet_issues.DietIssuesEntity;
import com.cgg.twdinspection.inspection.source.diet_issues.DietListEntity;
import com.cgg.twdinspection.inspection.source.diet_issues.DietMasterResponse;
import com.cgg.twdinspection.inspection.source.diet_issues.MasterDietListInfo;
import com.cgg.twdinspection.inspection.source.inst_master.MasterDietInfo;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.source.staff_attendance.StaffAttendanceEntity;

import java.util.List;

public class DietIssuesViewModel extends ViewModel {

    private final DietIssuesInfoRepository mRepository;

    public DietIssuesViewModel(Application application) {
        mRepository = new DietIssuesInfoRepository(application);
    }

    public LiveData<MasterDietListInfo> getMasterDietInfo(String inst_id) {
        return mRepository.getMasterDietList(inst_id);
    }

    public LiveData<List<DietListEntity>> getDietInfo(String inst_id) {
        return mRepository.getDietList(inst_id);
    }

    public void insertDietInfo(List<DietListEntity> dietListEntities, String instId) {
        mRepository.insertDietInfo(dietListEntities, instId);
    }


    public long updateDietIssuesInfo(DietIssuesEntity dietIssuesEntity) {
        return mRepository.updateDietIssuesInfo(dietIssuesEntity);
    }

    public long deleteDietListInfo(String inst_id) {
        return mRepository.deleteDietListInfo(inst_id);
    }
}
