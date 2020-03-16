package com.example.twdinspection.inspection.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import com.example.twdinspection.inspection.room.Dao.SchoolSyncDao;
import com.example.twdinspection.inspection.room.database.DistrictDatabase;
import com.example.twdinspection.inspection.interfaces.SchoolDMVInterface;
import com.example.twdinspection.inspection.interfaces.SchoolInstInterface;
import com.example.twdinspection.inspection.source.dmv.SchoolDistrict;
import com.example.twdinspection.inspection.source.dmv.SchoolMandal;
import com.example.twdinspection.inspection.source.dmv.SchoolVillage;
import com.example.twdinspection.inspection.source.inst_master.MasterInstituteInfo;

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

    public void insertMasterInstitutes(final SchoolInstInterface schoolInstInterface, final List<MasterInstituteInfo> masterInstituteInfos) {
        new InsertInstAsyncTask(schoolInstInterface, masterInstituteInfos).execute();
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

    @SuppressLint("StaticFieldLeak")
    private class InsertInstAsyncTask extends AsyncTask<Void, Void, Integer> {
        SchoolInstInterface schoolInstInterface;
        List<MasterInstituteInfo> masterInstituteInfos;

        InsertInstAsyncTask(SchoolInstInterface schoolInstInterface, List<MasterInstituteInfo> masterInstituteInfos) {
            this.masterInstituteInfos = masterInstituteInfos;
            this.schoolInstInterface = schoolInstInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            syncDao.deleteMasterInst();
            syncDao.insertMasterInst(masterInstituteInfos);
            return syncDao.instCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            schoolInstInterface.instCount(integer);
        }
    }
}
