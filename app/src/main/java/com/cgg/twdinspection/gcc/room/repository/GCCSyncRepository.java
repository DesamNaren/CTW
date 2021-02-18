package com.cgg.twdinspection.gcc.room.repository;

import android.app.Application;

import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.gcc.interfaces.GCCDivisionInterface;
import com.cgg.twdinspection.gcc.room.dao.GCCSyncDao;
import com.cgg.twdinspection.gcc.room.database.GCCDatabase;
import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.cgg.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.cgg.twdinspection.gcc.source.suppliers.lpg.LPGSupplierInfo;
import com.cgg.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolSupplierInfo;
import com.cgg.twdinspection.gcc.source.suppliers.punit.PUnits;

import java.util.List;

public class GCCSyncRepository {
    private final GCCSyncDao syncDao;

    public GCCSyncRepository(Application application) {
        GCCDatabase db = GCCDatabase.getDatabase(application);
        syncDao = db.gccSyncDao();
    }

    public void insertDivisions(final GCCDivisionInterface dmvInterface, final List<DivisionsInfo> divisionsInfos) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteDivisions();
            syncDao.insertDivisions(divisionsInfos);
            int x = syncDao.divisionCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        dmvInterface.divisionCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }

    public void insertDRDepots(final GCCDivisionInterface dmvInterface, final List<DRDepots> DRDepots) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteDRDepots();
            syncDao.insertDRDepots(DRDepots);
            int x = syncDao.drDepotCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        dmvInterface.drDepotCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }

    public void insertDRGoDowns(final GCCDivisionInterface dmvInterface, final List<DrGodowns> DRGodowns) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteDRGoDowns();
            syncDao.insertDRGoDowns(DRGodowns);
            int x = syncDao.drGoDownCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        dmvInterface.drGoDownCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }

    public void insertMFPGoDowns(final GCCDivisionInterface dmvInterface, final List<MFPGoDowns> mfpGoDowns) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteMFPDowns();
            syncDao.insertMFPGoDowns(mfpGoDowns);
            int x = syncDao.mfpGoDownCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        dmvInterface.mfpGoDownCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }

    public void insertPUnits(final GCCDivisionInterface dmvInterface, final List<PUnits> pUnits) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deletePUnits();
            syncDao.insertPUnits(pUnits);
            int x = syncDao.pUnitCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        dmvInterface.pUNitCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }

    public void insertPetrolPumps(final GCCDivisionInterface dmvInterface, final List<PetrolSupplierInfo> petrolSupplierInfos) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deletePetrolPumps();
            syncDao.insertPetrolPumps(petrolSupplierInfos);
            int x = syncDao.petrolPumpCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        dmvInterface.petrolPumpCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }

    public void insertLpg(final GCCDivisionInterface dmvInterface, final List<LPGSupplierInfo> lpgSupplierInfos) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteLPG();
            syncDao.insertLPG(lpgSupplierInfos);
            int x = syncDao.lpgCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        dmvInterface.lpgCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }
}
