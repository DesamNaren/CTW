package com.example.twdinspection.inspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twdinspection.inspection.source.cocurriularActivities.CoCurricularEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.PlantsEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;

import java.util.List;

@Dao
public interface CocurricularDao {
    @Insert()
    void insertCoCurricularInfo(CoCurricularEntity coCurricularEntity);

    @Insert()
    void insertAchievementInfo(StudAchievementEntity studAchievementEntity);

    @Insert()
    void insertPlantInfo(PlantsEntity plantsEntity);

    @Query("Select count(*) from StudAchievementEntity")
    LiveData<Integer> getAchievementsCnt();

    @Query("Select count(*) from PlantsEntity")
    LiveData<Integer> getPlantsCnt();

    @Query("SELECT * from StudAchievementEntity")
    LiveData<List<StudAchievementEntity>> getAchLiveData();

    @Query("SELECT * from PlantsEntity")
    LiveData<List<PlantsEntity>> getPlantLiveData();
}
