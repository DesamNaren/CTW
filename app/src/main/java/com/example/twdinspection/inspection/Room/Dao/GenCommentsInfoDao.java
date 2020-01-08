package com.example.twdinspection.inspection.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.twdinspection.inspection.source.GeneralComments.GeneralCommentsEntity;
import com.example.twdinspection.inspection.source.RegistersUptoDate.RegistersEntity;

@Dao
public interface GenCommentsInfoDao {

    @Insert()
    void insertGenCommentsInfo(GeneralCommentsEntity generalCommentsEntity);

}
