package com.example.twdinspection.inspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;

import java.util.List;

@Dao
public interface StudAchDao {

    @Query("SELECT * from StudAchievementEntity")
    LiveData<List<StudAchievementEntity>> getStudAchievements();


    @Delete()
    void deleteAchievementsInfo(StudAchievementEntity studAchievementEntity);

}
