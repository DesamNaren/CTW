package com.cgg.twdinspection.inspection.room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.cgg.twdinspection.inspection.source.general_information.GeneralInfoEntity;

@Dao
public interface GeneralInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGeneralInfo(GeneralInfoEntity generalInformationEntity);


}
