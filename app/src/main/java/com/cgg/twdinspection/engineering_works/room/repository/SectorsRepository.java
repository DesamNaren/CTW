package com.cgg.twdinspection.engineering_works.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.engineering_works.room.dao.SectorsDao;
import com.cgg.twdinspection.engineering_works.room.database.EngWorksDatabase;
import com.cgg.twdinspection.engineering_works.source.SectorsEntity;

import java.util.List;

public class SectorsRepository {

    private final SectorsDao sectorsDao;
    private LiveData<List<SectorsEntity>> sectorsLiveData = new MutableLiveData<>();
    private int x;

    public SectorsRepository(Application application) {
        EngWorksDatabase database = EngWorksDatabase.getDatabase(application);
        sectorsDao = database.sectorsDao();
    }

    public LiveData<List<SectorsEntity>> getSectors() {
        if (sectorsLiveData != null) {
            sectorsLiveData = sectorsDao.getSectors();
        }
        return sectorsLiveData;
    }

    public int insertSectors(List<SectorsEntity> sectorsEntities) {
        TWDApplication.getExecutorService().execute(() -> {
            sectorsDao.insertSectors(sectorsEntities);
            x = sectorsDao.sectorCount();
            //Background work here
        });
        return x;
    }

    public LiveData<Integer> getSectorId(String sectorName) {
        return sectorsDao.getSectorId(sectorName);
    }
}
