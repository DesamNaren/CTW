package com.example.twdinspection.inspection.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.twdinspection.inspection.source.academic_overview.AcademicEntity;

@Dao
public interface AcademicInfoDao {

    @Insert()
    void insertAcademicInfo(AcademicEntity AcademicEntity);

}
