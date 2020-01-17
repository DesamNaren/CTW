package com.example.twdinspection.inspection.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.twdinspection.inspection.source.GeneralInformation.GeneralInfoEntity;
import com.example.twdinspection.inspection.source.instMenuInfo.InstMenuInfoEntity;

@Dao
public interface GeneralInfoDao {

    @Insert()
    void insertGeneralInfo(GeneralInfoEntity generalInformationEntity);

    @Query("Update InstMenuInfoEntity set flag_completed=1, section_time=:time where section_id=:id")
    void updateSectionInfo(String time, int id);
}
