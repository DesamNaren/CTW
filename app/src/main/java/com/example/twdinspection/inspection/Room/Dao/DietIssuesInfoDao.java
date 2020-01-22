package com.example.twdinspection.inspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twdinspection.inspection.source.dietIssues.DietIssuesEntity;
import com.example.twdinspection.inspection.source.dietIssues.DietListEntity;
import com.example.twdinspection.inspection.source.inst_master.MasterInstituteInfo;

import java.util.List;

@Dao
public interface DietIssuesInfoDao {

    @Insert
    void updateDietIssuesInfo(DietIssuesEntity dietIssuesEntity);

    @Query("SELECT * from MasterInstituteInfo where instId LIKE :inst_id")
    LiveData<MasterInstituteInfo> getMasterDietList(String inst_id);

    @Query("SELECT * from DietListInfo where institute_id LIKE :inst_id ")
    LiveData<List<DietListEntity>> getDietList(String inst_id);

    @Query("DELETE FROM DietListInfo")
    void deleteDietInfo();

    @Insert
    void insertDietInfo(List<DietListEntity> dietListEntities);

}
