package com.cgg.twdinspection.engineering_works.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import com.cgg.twdinspection.engineering_works.interfaces.EngSyncInterface;
import com.cgg.twdinspection.engineering_works.room.dao.EngWorksSyncDao;
import com.cgg.twdinspection.engineering_works.room.database.EngWorksDatabase;
import com.cgg.twdinspection.engineering_works.source.GrantScheme;
import com.cgg.twdinspection.engineering_works.source.SectorsEntity;
import com.cgg.twdinspection.engineering_works.source.WorkDetail;

import java.util.List;

public class EngSyncRepository {
    private EngWorksSyncDao syncDao;

    public EngSyncRepository(Application application) {
        EngWorksDatabase db = EngWorksDatabase.getDatabase(application);
        syncDao = db.engWorksSyncDao();
    }

    public void insertEngSectors(final EngSyncInterface engSyncInterface, final List<SectorsEntity> sectorsEntities) {
        new InsertSectorsAsyncTask(engSyncInterface, sectorsEntities).execute();
    }

    public void insertEngSchemes(final EngSyncInterface engSyncInterface, final List<GrantScheme> grantSchemes) {
        new InsertSchemesAsyncTask(engSyncInterface, grantSchemes).execute();
    }


    public void insertWorkDetails(final EngSyncInterface engSyncInterface, final List<WorkDetail> workDetails) {
        new InsertWorkDetailAsyncTask(engSyncInterface, workDetails).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertSectorsAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<SectorsEntity> sectorsEntities;
        EngSyncInterface engSyncInterface;

        InsertSectorsAsyncTask(EngSyncInterface engSyncInterface,
                               List<SectorsEntity> sectorsEntities) {
            this.sectorsEntities = sectorsEntities;
            this.engSyncInterface = engSyncInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteSectors();
            syncDao.insertSectors(sectorsEntities);
            return syncDao.sectorsCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            engSyncInterface.setorsCnt(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertSchemesAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<GrantScheme> grantSchemes;
        EngSyncInterface engSyncInterface;

        InsertSchemesAsyncTask(EngSyncInterface engSyncInterface,
                               List<GrantScheme> grantSchemes) {
            this.grantSchemes = grantSchemes;
            this.engSyncInterface = engSyncInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteSchemes();
            syncDao.insertEngSchemes(grantSchemes);
            return syncDao.grantSchemesCnt();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            engSyncInterface.schemesCnt(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertWorkDetailAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<WorkDetail> workDetails;
        EngSyncInterface engSyncInterface;

        InsertWorkDetailAsyncTask(EngSyncInterface engSyncInterface,
                                  List<WorkDetail> workDetails) {
            this.workDetails = workDetails;
            this.engSyncInterface = engSyncInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteWorkDetails();
            syncDao.insertWorkDetails(workDetails);
            return syncDao.worksCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            engSyncInterface.engWorksCnt(integer);
        }
    }
}
