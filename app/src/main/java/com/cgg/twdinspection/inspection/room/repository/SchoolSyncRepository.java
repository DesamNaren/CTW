package com.cgg.twdinspection.inspection.room.repository;

import android.app.Application;

import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.inspection.interfaces.SchoolDMVInterface;
import com.cgg.twdinspection.inspection.interfaces.SchoolInstInterface;
import com.cgg.twdinspection.inspection.room.Dao.SchoolSyncDao;
import com.cgg.twdinspection.inspection.room.database.SchoolDatabase;
import com.cgg.twdinspection.inspection.source.dmv.SchoolDistrict;
import com.cgg.twdinspection.inspection.source.dmv.SchoolMandal;
import com.cgg.twdinspection.inspection.source.dmv.SchoolVillage;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;

import java.util.List;

public class SchoolSyncRepository {
    private final SchoolSyncDao syncDao;

    public SchoolSyncRepository(Application application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        syncDao = db.schoolSyncDao();
    }

    public void insertSchoolDistricts(final SchoolDMVInterface dmvInterface, final List<SchoolDistrict> districtEntities) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteSchoolDistricts();
            syncDao.insertSchoolDistricts(districtEntities);
            int x = syncDao.districtCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        dmvInterface.distCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }

    public void insertSchoolMandals(final SchoolDMVInterface dmvInterface, final List<SchoolMandal> mandalEntities) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteSchoolMandals();
            syncDao.insertSchoolMandals(mandalEntities);
            int x = syncDao.mandalCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        dmvInterface.manCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }


    public void insertSchoolVillages(final SchoolDMVInterface dmvInterface, final List<SchoolVillage> villageEntities) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteSchoolVillage();
            syncDao.insertSchoolVillages(villageEntities);
            int x = syncDao.villageCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        dmvInterface.vilCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }

    public void insertMasterInstitutes(final SchoolInstInterface schoolInstInterface, final List<MasterInstituteInfo> masterInstituteInfos) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteMasterInst();
            syncDao.insertMasterInst(masterInstituteInfos);
            int x = syncDao.instCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        schoolInstInterface.instCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }
}
