package com.example.twdinspection.inspection.room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.twdinspection.inspection.source.cocurriular_activities.CoCurricularEntity;
import com.example.twdinspection.inspection.source.cocurriular_activities.PlantsEntity;
import com.example.twdinspection.inspection.source.cocurriular_activities.StudAchievementEntity;

import java.util.List;

@Dao
public interface CocurricularDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCoCurricularInfo(CoCurricularEntity coCurricularEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAchievementInfo(StudAchievementEntity studAchievementEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlantInfo(PlantsEntity plantsEntity);

    @Query("Select count(*) from stud_achievements_info")
    LiveData<Integer> getAchievementsCnt();

    @Query("Select count(*) from plants_info")
    LiveData<Integer> getPlantsCnt();

    @Query("SELECT * from stud_achievements_info")
    LiveData<List<StudAchievementEntity>> getAchLiveData();

    @Query("SELECT * from plants_info")
    LiveData<List<PlantsEntity>> getPlantLiveData();


    @Query("delete from plants_info")
    void deletePlantsInfo();

    @Query("delete from stud_achievements_info")
    void deleteStuAchInfo();
}