package com.example.twdinspection.inspection.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.twdinspection.inspection.source.registers_upto_date.RegistersEntity;

@Dao
public interface RegistersInfoDao {

    @Insert()
    void insertRegistersInfo(RegistersEntity registersEntity);

}
