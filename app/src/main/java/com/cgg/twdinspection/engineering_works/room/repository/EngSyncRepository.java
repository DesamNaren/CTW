package com.cgg.twdinspection.engineering_works.room.repository;

import android.app.Application;

import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.engineering_works.interfaces.EngSyncInterface;
import com.cgg.twdinspection.engineering_works.room.dao.EngWorksSyncDao;
import com.cgg.twdinspection.engineering_works.room.database.EngWorksDatabase;
import com.cgg.twdinspection.engineering_works.source.GrantScheme;
import com.cgg.twdinspection.engineering_works.source.SectorsEntity;
import com.cgg.twdinspection.engineering_works.source.WorkDetail;

import java.util.List;

public class EngSyncRepository {
    private final EngWorksSyncDao syncDao;

    public EngSyncRepository(Application application) {
        EngWorksDatabase db = EngWorksDatabase.getDatabase(application);
        syncDao = db.engWorksSyncDao();
    }

    public void insertEngSectors(final EngSyncInterface engSyncInterface, final List<SectorsEntity> sectorsEntities) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteSectors();
            syncDao.insertSectors(sectorsEntities);
            int x = syncDao.sectorsCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        engSyncInterface.setorsCnt(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }

    public void insertEngSchemes(final EngSyncInterface engSyncInterface, final List<GrantScheme> grantSchemes) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteSchemes();
            syncDao.insertEngSchemes(grantSchemes);
            int x = syncDao.grantSchemesCnt();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        engSyncInterface.schemesCnt(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }

    public void insertWorkDetails(final EngSyncInterface engSyncInterface, final List<WorkDetail> workDetails) {
        TWDApplication.getExecutorService().execute(() -> {
            syncDao.deleteWorkDetails();
            syncDao.insertWorkDetails(workDetails);
            int x = syncDao.worksCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        engSyncInterface.engWorksCnt(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }
}
