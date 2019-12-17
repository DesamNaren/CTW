package com.example.twdinspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.twdinspection.source.studentAttendenceInfo.StudAttendInfoEntity;

import java.util.List;

@Dao
public interface ClassInfoDao {

    @Query("SELECT * from ClassInfo where institute_id LIKE :inst_id")
    LiveData<List<StudAttendInfoEntity>> getClassIdList(String inst_id);
}
