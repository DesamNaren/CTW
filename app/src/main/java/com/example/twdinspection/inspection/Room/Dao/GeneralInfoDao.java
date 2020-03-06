package com.example.twdinspection.inspection.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.example.twdinspection.inspection.source.general_information.GeneralInfoEntity;

@Dao
public interface GeneralInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGeneralInfo(GeneralInfoEntity generalInformationEntity);


}
