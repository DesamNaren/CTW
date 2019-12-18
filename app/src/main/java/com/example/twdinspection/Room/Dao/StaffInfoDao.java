package com.example.twdinspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.twdinspection.source.staffAttendance.StaffAttendanceEntity;

import java.util.List;

@Dao
public interface StaffInfoDao {

    @Query("SELECT * from Staff_Info where institute_id LIKE :inst_id")
    LiveData<List<StaffAttendanceEntity>> getStaffInfoList(String inst_id);
}
