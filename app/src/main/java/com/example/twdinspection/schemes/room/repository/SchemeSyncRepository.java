package com.example.twdinspection.schemes.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import com.example.twdinspection.schemes.interfaces.SchemeDMVInterface;
import com.example.twdinspection.schemes.room.dao.SchemeSyncDao;
import com.example.twdinspection.schemes.room.database.SchemesDatabase;
import com.example.twdinspection.schemes.source.DMV.SchemeDistrict;
import com.example.twdinspection.schemes.source.DMV.SchemeMandal;
import com.example.twdinspection.schemes.source.DMV.SchemeVillage;
import com.example.twdinspection.schemes.source.finyear.FinancialYrsEntity;

import java.util.List;

public class SchemeSyncRepository {
    private SchemeSyncDao syncDao;

    public SchemeSyncRepository(Application application) {
        SchemesDatabase db = SchemesDatabase.getDatabase(application);
        syncDao = db.schemeSyncDao();
    }

    public void insertSchemeDistricts(final SchemeDMVInterface dmvInterface, final List<SchemeDistrict> districtEntities) {
        new InsertDistrictAsyncTask(dmvInterface, districtEntities).execute();
    }

    public void insertSchemeMandals(final SchemeDMVInterface dmvInterface, final List<SchemeMandal> mandalEntities) {
        new InsertMandalAsyncTask(dmvInterface, mandalEntities).execute();
    }


    public void insertSchemeVillages(final SchemeDMVInterface dmvInterface, final List<SchemeVillage> villageEntities) {
        new InsertVillageAsyncTask(dmvInterface, villageEntities).execute();
    }

    public void insertFinYears(final SchemeDMVInterface dmvInterface, final List<FinancialYrsEntity> financialYrsEntities) {
        new InsertFinYearAsyncTask(dmvInterface, financialYrsEntities).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertDistrictAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<SchemeDistrict> districtEntities;
        SchemeDMVInterface dmvInterface;

        InsertDistrictAsyncTask(SchemeDMVInterface dmvInterface,
                                List<SchemeDistrict> districtEntities) {
            this.districtEntities = districtEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteSchemeDistricts();
            syncDao.insertSchemeDistricts(districtEntities);
            return syncDao.districtCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.distCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertMandalAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<SchemeMandal> mandalEntites;
        SchemeDMVInterface dmvInterface;

        InsertMandalAsyncTask(SchemeDMVInterface dmvInterface,
                              List<SchemeMandal> mandalEntites) {
            this.mandalEntites = mandalEntites;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteSchemeMandals();
            syncDao.insertSchemeMandals(mandalEntites);
            return syncDao.mandalCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.manCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertVillageAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<SchemeVillage> villageEntites;
        SchemeDMVInterface dmvInterface;

        InsertVillageAsyncTask(SchemeDMVInterface dmvInterface,
                              List<SchemeVillage> villageEntites) {
            this.villageEntites = villageEntites;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteSchemeVillage();
            syncDao.insertSchemeVillages(villageEntites);
            return syncDao.villageCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.vilCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertFinYearAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<FinancialYrsEntity> financialYrsEntities;
        SchemeDMVInterface dmvInterface;

        InsertFinYearAsyncTask(SchemeDMVInterface dmvInterface,
                                List<FinancialYrsEntity> financialYrsEntities) {
            this.financialYrsEntities = financialYrsEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteFinYears();
            syncDao.insertFinYears(financialYrsEntities);
            return syncDao.finYearCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.finYear(integer);
        }
    }
}
