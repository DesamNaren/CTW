package com.cgg.twdinspection.inspection.room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.cgg.twdinspection.inspection.source.registers_upto_date.RegistersEntity;

@Dao
public interface RegistersInfoDao {

    @Insert()
    void insertRegistersInfo(RegistersEntity registersEntity);

}
