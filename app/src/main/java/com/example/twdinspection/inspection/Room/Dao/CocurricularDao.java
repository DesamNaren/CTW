package com.example.twdinspection.inspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;

import java.util.List;

@Dao
public interface CocurricularDao {

    @Insert()
    void insertAchievementInfo(StudAchievementEntity studAchievementEntity);

    @Query("Select count(*) from StudAchievementEntity")
    LiveData<Integer> getAchievementsCnt();
}
