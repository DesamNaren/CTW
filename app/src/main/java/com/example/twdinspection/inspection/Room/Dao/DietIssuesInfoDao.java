package com.example.twdinspection.inspection.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.twdinspection.inspection.source.DiestIssues.DietIssuesEntity;

@Dao
public interface DietIssuesInfoDao {

    @Insert()
    void insertDietIssuesInfo(DietIssuesEntity dietIssuesEntity);

}
