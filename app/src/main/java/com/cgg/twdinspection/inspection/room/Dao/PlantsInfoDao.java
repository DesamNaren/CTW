package com.cgg.twdinspection.inspection.room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import com.cgg.twdinspection.inspection.source.cocurriular_activities.PlantsEntity;

import java.util.List;

@Dao
public interface PlantsInfoDao {

    @Query("SELECT * from plants_info")
    LiveData<List<PlantsEntity>> getPlantsInfo();


    @Delete()
    void deletePlantInfo(PlantsEntity plantsEntity);

}
