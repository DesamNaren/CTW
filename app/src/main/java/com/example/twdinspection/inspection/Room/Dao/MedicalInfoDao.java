package com.example.twdinspection.inspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twdinspection.inspection.source.MedicalAndHealth.CallHealthInfoEntity;
import com.example.twdinspection.inspection.source.MedicalAndHealth.MedicalInfoEntity;

@Dao
public interface MedicalInfoDao {

    @Insert()
    void insertMedicalInfo(MedicalInfoEntity medicalInfoEntity);

    @Insert()
    void insertCallInfo(CallHealthInfoEntity callHealthInfoEntity);

    @Query("Select count(*) from CallHealthInfoEntity")
    LiveData<Integer> callCnt();
}
