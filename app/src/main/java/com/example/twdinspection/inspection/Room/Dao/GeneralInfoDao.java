package com.example.twdinspection.inspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.twdinspection.inspection.source.GeneralInformation.GeneralInfoEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.example.twdinspection.inspection.source.instMenuInfo.InstMenuInfoEntity;

import java.util.List;

@Dao
public interface GeneralInfoDao {

    @Insert()
    void insertGeneralInfo(GeneralInfoEntity generalInformationEntity);


}
