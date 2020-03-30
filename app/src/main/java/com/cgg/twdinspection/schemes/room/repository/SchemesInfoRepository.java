package com.cgg.twdinspection.schemes.room.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.schemes.room.dao.SchemesInfoDao;
import com.cgg.twdinspection.schemes.room.database.SchemesDatabase;
import com.cgg.twdinspection.schemes.source.schemes.SchemeEntity;

import java.util.List;

public class SchemesInfoRepository {

    private SchemesInfoDao schemesInfoDao;

    public SchemesInfoRepository(Context application) {
        SchemesDatabase db = SchemesDatabase.getDatabase(application);
        schemesInfoDao = db.schemesInfoDao();
    }

    public LiveData<List<SchemeEntity>> getSchemesInfo() {
        return schemesInfoDao.getSchemesInfo();
    }

}
