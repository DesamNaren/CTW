package com.example.twdinspection.inspection.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.twdinspection.inspection.source.general_comments.GeneralCommentsEntity;

@Dao
public interface GenCommentsInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGenCommentsInfo(GeneralCommentsEntity generalCommentsEntity);

}
