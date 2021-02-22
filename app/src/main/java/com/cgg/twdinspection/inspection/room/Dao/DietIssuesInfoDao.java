package com.cgg.twdinspection.inspection.room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cgg.twdinspection.inspection.source.diet_issues.DietIssuesEntity;
import com.cgg.twdinspection.inspection.source.diet_issues.DietListEntity;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;

import java.util.List;

@Dao
public interface DietIssuesInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateDietIssuesInfo(DietIssuesEntity dietIssuesEntity);

    @Query("SELECT * from master_inst_info where instId LIKE :inst_id")
    LiveData<MasterInstituteInfo> getMasterDietList(String inst_id);

    @Query("SELECT * from diet_list_info where institute_id LIKE :inst_id ")
    LiveData<List<DietListEntity>> getDietList(String inst_id);

    @Query("DELETE FROM diet_list_info where institute_id LIKE :inst_id")
    void deleteDietInfo(String inst_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertDietInfo(List<DietListEntity> dietListEntities);

    @Query("delete from diet_list_info where institute_id LIKE :inst_id")
    void deleteDietListInfo(String inst_id);
}
