package com.cgg.twdinspection.inspection.room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.cgg.twdinspection.inspection.source.entitlements_distribution.EntitlementsEntity;

@Dao
public interface EntitlementsInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntitlementsInfo(EntitlementsEntity entitlementsEntity);

}
