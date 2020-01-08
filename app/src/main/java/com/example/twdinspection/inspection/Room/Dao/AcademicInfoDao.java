package com.example.twdinspection.inspection.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.twdinspection.inspection.source.AcademicOverview.AcademicOveriewEntity;
import com.example.twdinspection.inspection.source.MedicalAndHealth.MedicalInfoEntity;

@Dao
public interface AcademicInfoDao {

    @Insert()
    void insertAcademicInfo(AcademicOveriewEntity academicOveriewEntity);

}
