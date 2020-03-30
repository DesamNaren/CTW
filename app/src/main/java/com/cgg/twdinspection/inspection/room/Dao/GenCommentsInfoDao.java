package com.cgg.twdinspection.inspection.room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.cgg.twdinspection.inspection.source.general_comments.GeneralCommentsEntity;

@Dao
public interface GenCommentsInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGenCommentsInfo(GeneralCommentsEntity generalCommentsEntity);

}
