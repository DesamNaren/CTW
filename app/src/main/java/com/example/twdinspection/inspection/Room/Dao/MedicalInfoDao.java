package com.example.twdinspection.inspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;

@Dao
public interface MedicalInfoDao {

    @Insert()
    void insertMedicalInfo(MedicalInfoEntity medicalInfoEntity);

    @Insert()
    void insertCallInfo(CallHealthInfoEntity callHealthInfoEntity);

    @Query("Select count(*) from CallHealthInfoEntity")
    LiveData<Integer> callCnt();
}
