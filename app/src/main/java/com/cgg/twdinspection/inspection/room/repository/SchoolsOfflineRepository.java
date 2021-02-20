package com.cgg.twdinspection.inspection.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.inspection.interfaces.SchoolOfflineInterface;
import com.cgg.twdinspection.inspection.offline.SchoolsOfflineEntity;
import com.cgg.twdinspection.inspection.room.Dao.SchoolsDaoOffline;
import com.cgg.twdinspection.inspection.room.database.SchoolDatabase;

import java.util.List;

public class SchoolsOfflineRepository {
    private final SchoolsDaoOffline offlineDao;

    public SchoolsOfflineRepository(Application application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        offlineDao = db.gccSchoolsOfflineDao();
    }

    public void insertSchoolRecord(final SchoolOfflineInterface schoolOfflineInterface,
                                   final SchoolsOfflineEntity schoolsOfflineEntity) {
        TWDApplication.getExecutorService().execute(() -> {
            offlineDao.deleteSchoolsRecord(schoolsOfflineEntity.getInst_id());
            offlineDao.insertSchoolRecord(schoolsOfflineEntity);
            int x = offlineDao.schoolRecCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        schoolOfflineInterface.schoolRecCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }


    public LiveData<List<SchoolsOfflineEntity>> getSchoolsRecordsOfflineCount() {
        return offlineDao.getSchoolsRecords();
    }

    public LiveData<SchoolsOfflineEntity> getSchoolsRecordOfflineCount(String inst_id) {
        return offlineDao.getSchoolsRecord(inst_id);
    }

    public LiveData<List<String>> getPreviousDayInsts(String time) {
        return offlineDao.getPreviousDayInsts(time);
    }

    public void deleteSchoolsRecord(final SchoolOfflineInterface schoolOfflineInterface, String instId, boolean flag) {
        TWDApplication.getExecutorService().execute(() -> {
            int x = offlineDao.deleteSchoolsRecord(instId);
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        if (flag)
                            schoolOfflineInterface.deletedSchoolCountSubmitted(x);
                        else
                            schoolOfflineInterface.deletedSchoolCount(x);
                        //remove all sections
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }

    public void deletePreviousdaySchoolsRecord(String instId) {
        TWDApplication.getExecutorService().execute(() -> {
           offlineDao.deleteSchoolsRecord(instId);
            //Background work here
        });
    }

    public void deleteAllSchoolsRecord(final SchoolOfflineInterface schoolOfflineInterface, String time) {
        TWDApplication.getExecutorService().execute(() -> {
            int x = offlineDao.deletePreviousDayRecords(time);
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        schoolOfflineInterface.deletedSchoolCount(x);

                        //remove all sections
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }


}
