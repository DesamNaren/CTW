package com.example.twdinspection.inspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twdinspection.inspection.source.DiestIssues.DietIssuesEntity;
import com.example.twdinspection.inspection.source.DiestIssues.DietListEntity;
import com.example.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;

import java.util.List;

@Dao
public interface DietIssuesInfoDao {

    @Insert()
    void insertDietIssuesInfo(DietIssuesEntity dietIssuesEntity);

    @Query("SELECT * from MasterInstituteInfo where instId LIKE :inst_id")
    LiveData<MasterInstituteInfo> getMasterDietList(String inst_id);

    @Query("SELECT * from DietIssuesInfo where institute_id LIKE :inst_id")
    LiveData<List<DietListEntity>> getDietList(String inst_id);

    @Query("UPDATE DietIssuesInfo SET dietList = NULL")
    void deleteDietInfo();

    @Insert
    void insertDietInfo(List<DietListEntity> dietListEntities);

}
