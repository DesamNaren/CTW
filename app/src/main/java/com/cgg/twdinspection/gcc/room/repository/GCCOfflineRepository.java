package com.cgg.twdinspection.gcc.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.gcc.interfaces.GCCOfflineInterface;
import com.cgg.twdinspection.gcc.room.dao.GCCDaoOffline;
import com.cgg.twdinspection.gcc.room.database.GCCDatabase;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;

import java.util.List;

public class GCCOfflineRepository {
    private GCCDaoOffline offlineDao;

    public GCCOfflineRepository(Application application) {
        GCCDatabase db = GCCDatabase.getDatabase(application);
        offlineDao = db.gccOfflineDao();
    }

    public void insertGCCRecord(final GCCOfflineInterface gccOfflineInterface, final GccOfflineEntity GCCOfflineEntity) {
        new InsertGCCOfflineAsyncTask(gccOfflineInterface, GCCOfflineEntity).execute();
    }

    public LiveData<GccOfflineEntity> getGCCRecords(String divId, String socId, String godownId) {
        return offlineDao.getGCCRecords(divId, socId, godownId);
    }

    public LiveData<List<GccOfflineEntity>> getGCCOfflineCount(String type) {
        return offlineDao.getGccRecCount(type);
    }

    public void deleteGCCRecord(final GCCOfflineInterface gccOfflineInterface, String divId, String socId, String godownId) {
        new DeleteGCCRecordOfflineAsyncTask(gccOfflineInterface, divId, socId, godownId).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertGCCOfflineAsyncTask extends AsyncTask<Void, Void, Integer> {
        GccOfflineEntity gccOfflineEntity;
        GCCOfflineInterface gccOfflineInterface;

        InsertGCCOfflineAsyncTask(GCCOfflineInterface dmvInterface, GccOfflineEntity divisionsInfos) {
            this.gccOfflineEntity = divisionsInfos;
            this.gccOfflineInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            offlineDao.deleteGCCRecord(gccOfflineEntity.getDivisionId(), gccOfflineEntity.getSocietyId(), gccOfflineEntity.getDrgownId());
            offlineDao.insertGCCRecord(gccOfflineEntity);
            return offlineDao.gccRecCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            gccOfflineInterface.gccRecCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteGCCRecordOfflineAsyncTask extends AsyncTask<Void, Void, Integer> {
        String divId, socId, godownId;
        GCCOfflineInterface gccOfflineInterface;

        DeleteGCCRecordOfflineAsyncTask(GCCOfflineInterface gccOfflineInterface, String divId, String socId, String godownId) {
            this.gccOfflineInterface = gccOfflineInterface;
            this.divId = divId;
            this.socId = socId;
            this.godownId = godownId;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return offlineDao.deleteGCCRecord(divId, socId, godownId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            gccOfflineInterface.deletedrGoDownCount(integer);
        }
    }

}
