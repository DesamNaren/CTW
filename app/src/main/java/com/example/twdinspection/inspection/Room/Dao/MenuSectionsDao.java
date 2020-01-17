package com.example.twdinspection.inspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.twdinspection.inspection.source.instMenuInfo.InstMenuInfoEntity;

import java.util.List;

@Dao
public interface MenuSectionsDao {
    @Insert()
    void insertMenuSections(List<InstMenuInfoEntity> menuInfoEntities);

    @Query("SELECT * from InstMenuInfoEntity")
    LiveData<List<InstMenuInfoEntity>> getSections();

    @Query("Update InstMenuInfoEntity set flag_completed=1, section_time=:time where section_id=:id")
    void updateSectionInfo(String time, int id);

}
