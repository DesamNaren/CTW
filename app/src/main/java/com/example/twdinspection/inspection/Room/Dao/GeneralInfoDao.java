package com.example.twdinspection.inspection.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.twdinspection.inspection.source.GeneralInformation.GeneralInfoEntity;

@Dao
public interface GeneralInfoDao {

    @Insert()
    void insertGeneralInfo(GeneralInfoEntity generalInformationEntity);

}
