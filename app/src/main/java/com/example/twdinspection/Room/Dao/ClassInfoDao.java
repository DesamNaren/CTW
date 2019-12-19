package com.example.twdinspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.twdinspection.source.studentAttendenceInfo.StudAttendInfoEntity;

import java.util.List;

@Dao
public interface ClassInfoDao {

    @Query("SELECT * from ClassInfo where institute_id LIKE :inst_id")
    LiveData<List<StudAttendInfoEntity>> getClassIdList(String inst_id);

    @Update()
    void updateClassInfo(StudAttendInfoEntity studAttendInfoEntity);


}
