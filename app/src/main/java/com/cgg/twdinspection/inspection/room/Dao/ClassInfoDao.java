package com.cgg.twdinspection.inspection.room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.source.student_attendence_info.StudAttendInfoEntity;

import java.util.List;

@Dao
public interface ClassInfoDao {

    @Query("SELECT * from master_inst_info where instId LIKE :inst_id")
    LiveData<MasterInstituteInfo> getMasterClassIdList(String inst_id);

    @Query("SELECT * from class_info where institute_id LIKE :inst_id")
    LiveData<List<StudAttendInfoEntity>> getClassIdList(String inst_id);


    @Insert
    void insertStudAttendInfo(List<StudAttendInfoEntity> studAttendInfoEntityList);

    @Update()
    void updateClassInfo(StudAttendInfoEntity studAttendInfoEntity);

    @Query("DELETE FROM class_info where institute_id LIKE :inst_id")
    void deleteClassInfo(String inst_id);


}
