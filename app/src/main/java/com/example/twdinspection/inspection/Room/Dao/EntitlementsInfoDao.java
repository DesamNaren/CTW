package com.example.twdinspection.inspection.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.twdinspection.inspection.source.entitlements_distribution.EntitlementsEntity;

@Dao
public interface EntitlementsInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntitlementsInfo(EntitlementsEntity entitlementsEntity);

}
