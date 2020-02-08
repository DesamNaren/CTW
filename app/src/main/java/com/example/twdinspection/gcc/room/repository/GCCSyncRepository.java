package com.example.twdinspection.gcc.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import com.example.twdinspection.gcc.interfaces.GCCDivisionInterface;
import com.example.twdinspection.gcc.room.dao.GCCSyncDao;
import com.example.twdinspection.gcc.room.database.GCCDatabase;
import com.example.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.example.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.example.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.example.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.example.twdinspection.gcc.source.suppliers.punit.PUnits;

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

    public void insertDRDepots(final GCCDivisionInterface dmvInterface, final List<DRDepots> DRDepots) {
        new InsertDRDepotAsyncTask(dmvInterface, DRDepots).execute();
    }

    public void insertDRGoDowns(final GCCDivisionInterface dmvInterface, final List<DrGodowns> DRGoDowns) {
        new InsertDRGoDownAsyncTask(dmvInterface, DRGoDowns).execute();
    }

    public void insertMFPGoDowns(final GCCDivisionInterface dmvInterface, final List<MFPGoDowns> mfpGoDowns) {
        new InsertMFPGoDownAsyncTask(dmvInterface, mfpGoDowns).execute();
    }

    public void insertPUnits(final GCCDivisionInterface dmvInterface, final List<PUnits> pUnits) {
        new InsertPUnitAsyncTask(dmvInterface, pUnits).execute();
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
        List<DRDepots> DRDepots;
        GCCDivisionInterface dmvInterface;

        InsertDRDepotAsyncTask(GCCDivisionInterface dmvInterface,
                                List<DRDepots> DRDepots) {
            this.DRDepots = DRDepots;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteDRDepots();
            syncDao.insertDRDepots(DRDepots);
            return syncDao.drDepotCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.drDepotCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertDRGoDownAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<DrGodowns> DRGodowns;
        GCCDivisionInterface dmvInterface;

        InsertDRGoDownAsyncTask(GCCDivisionInterface dmvInterface,
                               List<DrGodowns> DRGodowns) {
            this.DRGodowns = DRGodowns;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteDRGoDowns();
            syncDao.insertDRGoDowns(DRGodowns);
            return syncDao.drGoDownCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.drGoDownCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertMFPGoDownAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<MFPGoDowns> mfpGoDowns;
        GCCDivisionInterface dmvInterface;

        InsertMFPGoDownAsyncTask(GCCDivisionInterface dmvInterface,
                                List<MFPGoDowns> mfpGoDowns) {
            this.mfpGoDowns = mfpGoDowns;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteMFPDowns();
            syncDao.insertMFPGoDowns(mfpGoDowns);
            return syncDao.mfpGoDownCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.mfpGoDownCount(integer);
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class InsertPUnitAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<PUnits> pUnits;
        GCCDivisionInterface dmvInterface;

        InsertPUnitAsyncTask(GCCDivisionInterface dmvInterface,
                                 List<PUnits> pUnits) {
            this.pUnits = pUnits;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deletePUnits();
            syncDao.insertPUnits(pUnits);
            return syncDao.pUnitCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.pUNitCount(integer);
        }
    }

}
