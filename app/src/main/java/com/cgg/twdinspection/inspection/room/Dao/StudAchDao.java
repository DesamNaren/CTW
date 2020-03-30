package com.cgg.twdinspection.inspection.room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import com.cgg.twdinspection.inspection.source.cocurriular_activities.StudAchievementEntity;

import java.util.List;

@Dao
public interface StudAchDao {

    @Query("SELECT * from stud_achievements_info")
    LiveData<List<StudAchievementEntity>> getStudAchievements();


    @Delete()
    void deleteAchievementsInfo(StudAchievementEntity studAchievementEntity);

}
