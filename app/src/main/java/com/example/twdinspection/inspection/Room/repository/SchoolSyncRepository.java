package com.example.twdinspection.inspection.Room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import com.example.twdinspection.inspection.Room.Dao.SchoolSyncDao;
import com.example.twdinspection.inspection.Room.database.DistrictDatabase;
import com.example.twdinspection.inspection.interfaces.SchoolDMVInterface;
import com.example.twdinspection.inspection.source.dmv.SchoolDistrict;
import com.example.twdinspection.inspection.source.dmv.SchoolMandal;
import com.example.twdinspection.inspection.source.dmv.SchoolVillage;
import com.example.twdinspection.schemes.interfaces.SchemeDMVInterface;
import com.example.twdinspection.schemes.room.dao.SchemeSyncDao;
import com.example.twdinspection.schemes.room.database.SchemesDatabase;
import com.example.twdinspection.schemes.source.DMV.SchemeDistrict;
import com.example.twdinspection.schemes.source.DMV.SchemeMandal;
import com.example.twdinspection.schemes.source.DMV.SchemeVillage;
import com.example.twdinspection.schemes.source.finyear.FinancialYearsEntity;
import com.example.twdinspection.schemes.source.remarks.InspectionRemarksEntity;
import com.example.twdinspection.schemes.source.schemes.SchemeEntity;

import java.util.List;

public class SchoolSyncRepository {
    private SchoolSyncDao syncDao;

    public SchoolSyncRepository(Application application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        syncDao = db.schoolSyncDao();
    }

    public void insertSchoolDistricts(final SchoolDMVInterface dmvInterface, final List<SchoolDistrict> districtEntities) {
        new InsertDistrictAsyncTask(dmvInterface, districtEntities).execute();
    }

    public void insertSchoolMandals(final SchoolDMVInterface dmvInterface, final List<SchoolMandal> mandalEntities) {
        new InsertMandalAsyncTask(dmvInterface, mandalEntities).execute();
    }


    public void insertSchoolVillages(final SchoolDMVInterface dmvInterface, final List<SchoolVillage> villageEntities) {
        new InsertVillageAsyncTask(dmvInterface, villageEntities).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertDistrictAsyncTask extends AsyncTask<Void, Void, Integer> {
        SchoolDMVInterface dmvInterface;
        List<SchoolDistrict> districtEntities;

        InsertDistrictAsyncTask(SchoolDMVInterface dmvInterface, List<SchoolDistrict> districtEntities) {
            this.districtEntities = districtEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteSchoolDistricts();
            syncDao.insertSchoolDistricts(districtEntities);
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
        List<SchoolMandal> mandalEntites;
        SchoolDMVInterface dmvInterface;

        InsertMandalAsyncTask(SchoolDMVInterface dmvInterface,
                              List<SchoolMandal> mandalEntites) {
            this.mandalEntites = mandalEntites;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteSchoolMandals();
            syncDao.insertSchoolMandals(mandalEntites);
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
        List<SchoolVillage> villageEntites;
        SchoolDMVInterface dmvInterface;

        InsertVillageAsyncTask(SchoolDMVInterface dmvInterface,
                              List<SchoolVillage> villageEntites) {
            this.villageEntites = villageEntites;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteSchoolVillage();
            syncDao.insertSchoolVillages(villageEntites);
            return syncDao.villageCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dmvInterface.vilCount(integer);
        }
    }
}