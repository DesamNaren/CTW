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

    public void deleteGCCRecord(final GCCOfflineInterface gccOfflineInterface, GccOfflineEntity entity) {
        new DeleteGCCRecordOfflineAsyncTask(gccOfflineInterface, entity).execute();
    }

    public void deleteGCCRecordSubmitted(final GCCOfflineInterface gccOfflineInterface, GccOfflineEntity entity, String msg) {
        new DeleteGCCRecordOfflineSubmittedAsyncTask(gccOfflineInterface, entity, msg).execute();
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
        GccOfflineEntity entity;
        GCCOfflineInterface gccOfflineInterface;

        DeleteGCCRecordOfflineAsyncTask(GCCOfflineInterface gccOfflineInterface, GccOfflineEntity entity) {
            this.gccOfflineInterface = gccOfflineInterface;
            this.entity = entity;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return offlineDao.deleteGCCRecord(entity.getDivisionId(), entity.getSocietyId(), entity.getDrgownId());
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            gccOfflineInterface.deletedrGoDownCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteGCCRecordOfflineSubmittedAsyncTask extends AsyncTask<Void, Void, Integer> {
        GccOfflineEntity entity;
        String msg;
        GCCOfflineInterface gccOfflineInterface;

        DeleteGCCRecordOfflineSubmittedAsyncTask(GCCOfflineInterface gccOfflineInterface, GccOfflineEntity entity, String msg) {
            this.gccOfflineInterface = gccOfflineInterface;
            this.entity = entity;
            this.msg = msg;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return offlineDao.deleteGCCRecord(entity.getDivisionId(), entity.getSocietyId(), entity.getDrgownId());
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            gccOfflineInterface.deletedrGoDownCountSubmitted(integer,msg);
        }
    }

}
