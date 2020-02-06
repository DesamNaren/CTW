package com.example.twdinspection.gcc.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import com.example.twdinspection.gcc.interfaces.GCCDivisionInterface;
import com.example.twdinspection.gcc.room.dao.GCCSyncDao;
import com.example.twdinspection.gcc.room.database.GCCDatabase;
import com.example.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.example.twdinspection.gcc.source.suppliers.DRGodowns;

import java.util.List;

public class GCCSyncRepository {
    private GCCSyncDao syncDao;

    public GCCSyncRepository(Application application) {
        GCCDatabase db = GCCDatabase.getDatabase(application);
        syncDao = db.gccSyncDao();
    }

    public void insertDivisions(final GCCDivisionInterface dmvInterface, final List<DivisionsInfo> divisionsInfos) {
        new InsertDivisionAsyncTask(dmvInterface, divisionsInfos).execute();
    }

    public void insertDRDepots(final GCCDivisionInterface dmvInterface, final List<DRGodowns> DRGodowns) {
        new InsertDRDepotAsyncTask(dmvInterface, DRGodowns).execute();
    }


    @SuppressLint("StaticFieldLeak")
    private class InsertDivisionAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<DivisionsInfo> divisionsInfos;
        GCCDivisionInterface dmvInterface;

        InsertDivisionAsyncTask(GCCDivisionInterface dmvInterface,
                                List<DivisionsInfo> divisionsInfos) {
            this.divisionsInfos = divisionsInfos;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteDivisions();
            syncDao.insertDivisions(divisionsInfos);
            return syncDao.divisionCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.divisionCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertDRDepotAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<DRGodowns> DRGodowns;
        GCCDivisionInterface dmvInterface;

        InsertDRDepotAsyncTask(GCCDivisionInterface dmvInterface,
                                List<DRGodowns> DRGodowns) {
            this.DRGodowns = DRGodowns;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteDRDepots();
            syncDao.insertDRDepots(DRGodowns);
            return syncDao.drDepotCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.supplierCount(integer);
        }
    }
}
