package com.example.twdinspection.inspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.example.twdinspection.inspection.source.staffAttendance.StaffAttendanceEntity;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;

import java.util.List;

@Dao
public interface StaffInfoDao {

    @Query("SELECT * from Staff_Info where institute_id LIKE :inst_id")
    LiveData<List<StaffAttendanceEntity>> getStaffInfoList(String inst_id);

    @Update()
    void updateStaffInfo(List<StaffAttendanceEntity> staffAttendanceEntity);

    @Query("SELECT * from master_inst_info where instId LIKE :inst_id")
    LiveData<MasterInstituteInfo> getMasterStaffIdList(String inst_id);


    @Insert
    void insertStaffAttendInfo(List<StaffAttendanceEntity> staffAttendanceEntities);

    @Query("DELETE FROM Staff_Info")
    void deleteStaffInfo();
}
