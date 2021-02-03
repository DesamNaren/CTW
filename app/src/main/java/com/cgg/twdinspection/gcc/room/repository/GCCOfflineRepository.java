package com.cgg.twdinspection.gcc.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.gcc.interfaces.GCCOfflineInterface;
import com.cgg.twdinspection.gcc.room.dao.GCCDaoOffline;
import com.cgg.twdinspection.gcc.room.database.GCCDatabase;
import com.cgg.twdinspection.gcc.source.offline.drgodown.DrGodownOffline;

public class GCCOfflineRepository {
    private GCCDaoOffline offlineDao;

    public GCCOfflineRepository(Application application) {
        GCCDatabase db = GCCDatabase.getDatabase(application);
        offlineDao = db.gccOfflineDao();
    }

    public void insertDRGodowns(final GCCOfflineInterface gccOfflineInterface, final DrGodownOffline drGodownOffline) {
        new InsertDrGodownOfflineAsyncTask(gccOfflineInterface, drGodownOffline).execute();
    }

    public LiveData<DrGodownOffline> getGoDownsOffline(String divId, String socId, String godownId) {
        return offlineDao.getDrGoDowns(divId, socId, godownId);
    }

    public void deleteGoDown(final GCCOfflineInterface gccOfflineInterface, String divId, String socId, String godownId) {
        new DeleteDrGodownOfflineAsyncTask(gccOfflineInterface, divId, socId, godownId).execute();
//        return offlineDao.deleteDRGodown(divId, socId, godownId);
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertDrGodownOfflineAsyncTask extends AsyncTask<Void, Void, Integer> {
        DrGodownOffline drGodownOffline;
        GCCOfflineInterface gccOfflineInterface;

        InsertDrGodownOfflineAsyncTask(GCCOfflineInterface dmvInterface, DrGodownOffline divisionsInfos) {
            this.drGodownOffline = divisionsInfos;
            this.gccOfflineInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            offlineDao.deleteDRGodown(drGodownOffline.getDivisionId(), drGodownOffline.getSocietyId(), drGodownOffline.getDrgownId());
            offlineDao.insertDRGodown(drGodownOffline);
            return offlineDao.DRGodownCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            gccOfflineInterface.drGoDownCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteDrGodownOfflineAsyncTask extends AsyncTask<Void, Void, Integer> {
        String divId, socId, godownId;
        GCCOfflineInterface gccOfflineInterface;

        DeleteDrGodownOfflineAsyncTask(GCCOfflineInterface gccOfflineInterface, String divId, String socId, String godownId) {
            this.gccOfflineInterface = gccOfflineInterface;
            this.divId = divId;
            this.socId = socId;
            this.godownId = godownId;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return offlineDao.deleteDRGodown(divId, socId, godownId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            gccOfflineInterface.deletedrGoDownCount(integer);
        }
    }

}
