package com.cgg.twdinspection.schemes.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import com.cgg.twdinspection.schemes.interfaces.SchemeDMVInterface;
import com.cgg.twdinspection.schemes.room.dao.SchemeSyncDao;
import com.cgg.twdinspection.schemes.room.database.SchemesDatabase;
import com.cgg.twdinspection.schemes.source.dmv.SchemeDistrict;
import com.cgg.twdinspection.schemes.source.dmv.SchemeMandal;
import com.cgg.twdinspection.schemes.source.dmv.SchemeVillage;
import com.cgg.twdinspection.schemes.source.finyear.FinancialYearsEntity;
import com.cgg.twdinspection.schemes.source.remarks.InspectionRemarksEntity;
import com.cgg.twdinspection.schemes.source.schemes.SchemeEntity;

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

    public void insertFinYears(final SchemeDMVInterface dmvInterface, final List<FinancialYearsEntity> financialYrsEntities) {
        new InsertFinYearAsyncTask(dmvInterface, financialYrsEntities).execute();
    }


    public void insertInsRemarks(final SchemeDMVInterface dmvInterface, final List<InspectionRemarksEntity> inspectionRemarksEntities) {
        new InsertInsRemarkAsyncTask(dmvInterface, inspectionRemarksEntities).execute();
    }

    public void insertSchemes(final SchemeDMVInterface dmvInterface, final List<SchemeEntity> schemeEntities) {
        new InsertSchemeAsyncTask(dmvInterface, schemeEntities).execute();
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
        List<FinancialYearsEntity> financialYrsEntities;
        SchemeDMVInterface dmvInterface;

        InsertFinYearAsyncTask(SchemeDMVInterface dmvInterface,
                                List<FinancialYearsEntity> financialYrsEntities) {
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

    @SuppressLint("StaticFieldLeak")
    private class InsertInsRemarkAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<InspectionRemarksEntity> inspectionRemarksEntities;
        SchemeDMVInterface dmvInterface;

        InsertInsRemarkAsyncTask(SchemeDMVInterface dmvInterface,
                               List<InspectionRemarksEntity> inspectionRemarksEntities) {
            this.inspectionRemarksEntities = inspectionRemarksEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteInsRemarks();
            syncDao.insertInsRemark(inspectionRemarksEntities);
            return syncDao.insRemarksCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.insRemCount(integer);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertSchemeAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<SchemeEntity> schemeEntities;
        SchemeDMVInterface dmvInterface;

        InsertSchemeAsyncTask(SchemeDMVInterface dmvInterface,
                                 List<SchemeEntity> schemeEntities) {
            this.schemeEntities = schemeEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteschemes();
            syncDao.insertSchemes(schemeEntities);
            return syncDao.schemeCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.schemeCount(integer);
        }
    }

}
