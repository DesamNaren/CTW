package com.cgg.twdinspection.gcc.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.gcc.interfaces.GCCOfflineInterface;
import com.cgg.twdinspection.gcc.room.dao.GCCDaoOffline;
import com.cgg.twdinspection.gcc.room.database.GCCDatabase;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;

import java.util.List;

public class GCCOfflineRepository {
    private final GCCDaoOffline offlineDao;

    public GCCOfflineRepository(Application application) {
        GCCDatabase db = GCCDatabase.getDatabase(application);
        offlineDao = db.gccOfflineDao();
    }

    public void insertGCCRecord(final GCCOfflineInterface gccOfflineInterface, final GccOfflineEntity gccOfflineEntity) {
        TWDApplication.getExecutorService().execute(() -> {
            offlineDao.deleteGCCRecord(gccOfflineEntity.getDivisionId(), gccOfflineEntity.getSocietyId(),
                    gccOfflineEntity.getDrgownId());
            offlineDao.insertGCCRecord(gccOfflineEntity);
            int x = offlineDao.gccRecCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        gccOfflineInterface.gccRecCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }

    public LiveData<GccOfflineEntity> getGCCRecords(String divId, String socId, String godownId) {
        return offlineDao.getGCCRecords(divId, socId, godownId);
    }

    public LiveData<GccOfflineEntity> getGCCRecordsPUnit(String divId, String socId, String godownId) {
        return offlineDao.getGCCRecordsPUnit(divId, socId, godownId);
    }

    public LiveData<List<GccOfflineEntity>> getGCCOfflineCount(String type) {
        return offlineDao.getGccRecCount(type);
    }

    public void deleteGCCRecord(final GCCOfflineInterface gccOfflineInterface, GccOfflineEntity entity) {
        TWDApplication.getExecutorService().execute(() -> {
            int x = offlineDao.deleteGCCRecord(entity.getDivisionId(), entity.getSocietyId(), entity.getDrgownId());
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        gccOfflineInterface.deletedrGoDownCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }

    public void deleteGCCRecordSubmitted(final GCCOfflineInterface gccOfflineInterface, GccOfflineEntity entity, String msg) {
        TWDApplication.getExecutorService().execute(() -> {
            int x = offlineDao.deleteGCCRecord(entity.getDivisionId(), entity.getSocietyId(), entity.getDrgownId());
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        gccOfflineInterface.deletedrGoDownCountSubmitted(x, msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }
}
