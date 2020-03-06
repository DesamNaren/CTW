package com.example.twdinspection.inspection.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.twdinspection.inspection.source.entitlements_distribution.EntitlementsEntity;

@Dao
public interface EntitlementsInfoDao {

    @Insert()
    void insertEntitlementsInfo(EntitlementsEntity entitlementsEntity);

}
