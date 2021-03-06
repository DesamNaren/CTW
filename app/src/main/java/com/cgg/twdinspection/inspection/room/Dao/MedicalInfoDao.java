package com.cgg.twdinspection.inspection.room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cgg.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalDetailsBean;
import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;

import java.util.List;

@Dao
public interface MedicalInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMedicalInfo(MedicalInfoEntity medicalInfoEntity);

    @Insert()
    void insertCallInfo(CallHealthInfoEntity callHealthInfoEntity);

    @Delete()
    void deleteCallInfo(CallHealthInfoEntity callHealthInfoEntity);

    @Query("Select count(*) from call_health_info")
    LiveData<Integer> callCnt();

    @Query("SELECT * from call_health_info")
    LiveData<List<CallHealthInfoEntity>> getCallListLiveData();


    @Query("SELECT * from medical_details_info")
    LiveData<List<MedicalDetailsBean>> getMedicalListLiveData();

    @Query("DELETE FROM medical_details_info")
    void deleteMedicalInfo();

    @Insert()
    void insertMedicalDetailsInfo(List<MedicalDetailsBean> medicalDetailsBeans);
}
