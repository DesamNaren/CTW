package com.example.twdinspection.inspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twdinspection.inspection.source.MedicalDetailsBean;
import com.example.twdinspection.inspection.source.instMenuInfo.InstMenuInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public interface MedicalInfoDao {

    @Insert()
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
