package com.example.twdinspection.inspection.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.twdinspection.inspection.source.MedicalAndHealth.MedicalInfoEntity;

@Dao
public interface MedicalInfoDao {

    @Insert()
    void insertMedicalInfo(MedicalInfoEntity medicalInfoEntity);

}
