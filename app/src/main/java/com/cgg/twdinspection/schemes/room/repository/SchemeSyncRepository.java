package com.cgg.twdinspection.schemes.room.repository;

import android.app.Application;

import com.cgg.twdinspection.common.application.TWDApplication;
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
    private final SchemeSyncDao syncDao;

    public SchemeSyncRepository(Application application) {
        SchemesDatabase db = SchemesDatabase.getDatabase(application);
        syncDao = db.schemeSyncDao();
    }

    public void insertSchemeDistricts(final SchemeDMVInterface dmvInterface, final List<SchemeDistrict> districtEntities) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteSchemeDistricts();
            syncDao.insertSchemeDistricts(districtEntities);
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

    public void insertSchemeMandals(final SchemeDMVInterface dmvInterface, final List<SchemeMandal> mandalEntities) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteSchemeMandals();
            syncDao.insertSchemeMandals(mandalEntities);
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

    public void insertSchemeVillages(final SchemeDMVInterface dmvInterface, final List<SchemeVillage> villageEntities) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteSchemeVillage();
            syncDao.insertSchemeVillages(villageEntities);
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

    public void insertFinYears(final SchemeDMVInterface dmvInterface, final List<FinancialYearsEntity> financialYrsEntities) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteFinYears();
            syncDao.insertFinYears(financialYrsEntities);
            int x = syncDao.finYearCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        dmvInterface.finYear(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }

    public void insertInsRemarks(final SchemeDMVInterface dmvInterface, final List<InspectionRemarksEntity> inspectionRemarksEntities) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteInsRemarks();
            syncDao.insertInsRemark(inspectionRemarksEntities);
            int x = syncDao.insRemarksCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        dmvInterface.insRemCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }

    public void insertSchemes(final SchemeDMVInterface dmvInterface, final List<SchemeEntity> schemeEntities) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteschemes();
            syncDao.insertSchemes(schemeEntities);
            int x = syncDao.schemeCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        dmvInterface.schemeCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }
}
