package com.example.twdinspection.inspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.twdinspection.inspection.source.AcademicOverview.AcademicOveriewEntity;
import com.example.twdinspection.inspection.source.inst_master.MasterClassInfo;
import com.example.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;

import java.util.List;

@Dao
public interface ClassInfoDao {

    @Query("SELECT * from MasterInstituteInfo where instId LIKE :inst_id")
    LiveData<MasterInstituteInfo> getMasterClassIdList(String inst_id);

    @Query("SELECT * from ClassInfo where institute_id LIKE :inst_id")
    LiveData<List<StudAttendInfoEntity>> getClassIdList(String inst_id);

    @Insert
    void insertStudAttendInfo(List<StudAttendInfoEntity> studAttendInfoEntityList);

    @Update()
    void updateClassInfo(StudAttendInfoEntity studAttendInfoEntity);

    @Query("DELETE FROM ClassInfo")
    void deleteClassInfo();


}
