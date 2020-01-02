package com.example.twdinspection.schemes.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.twdinspection.schemes.room.dao.SchemesInfoDao;
import com.example.twdinspection.schemes.room.database.SchemesDatabase;
import com.example.twdinspection.schemes.source.SchemesInfoEntity;

import java.util.List;

public class SchemesInfoRepository {

    private SchemesInfoDao schemesInfoDao;

    public SchemesInfoRepository(Application application) {
        SchemesDatabase db = SchemesDatabase.getDatabase(application);
        schemesInfoDao = db.schemesInfoDao();
    }

    public LiveData<List<SchemesInfoEntity>> getSchemesInfo() {
        return schemesInfoDao.getSchemesInfo();
    }

}
