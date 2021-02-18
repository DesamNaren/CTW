package com.cgg.twdinspection.engineering_works.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.engineering_works.room.dao.WorksDao;
import com.cgg.twdinspection.engineering_works.room.database.EngWorksDatabase;
import com.cgg.twdinspection.engineering_works.source.WorkDetail;

import java.util.List;

public class WorksRepository {

    private final WorksDao worksDao;
    private LiveData<List<WorkDetail>> workDetailLiveData = new MutableLiveData<>();
    private int x;

    public WorksRepository(Application application) {
        EngWorksDatabase database = EngWorksDatabase.getDatabase(application);
        worksDao = database.worksDao();
    }

    public LiveData<List<WorkDetail>> getSelWorkDetails(String distId, String mandId) {
        if (workDetailLiveData != null) {
            workDetailLiveData = worksDao.getWorkDetails(distId, mandId);
        }
        return workDetailLiveData;
    }

    public int insertWorks(List<WorkDetail> workDetails) {
        TWDApplication.getExecutorService().execute(() -> {
            worksDao.insertWorks(workDetails);
            x = worksDao.getInsertedWorksCount();
            //Background work here
        });
        return x;
    }

    public LiveData<List<String>> getDistricts() {
        return worksDao.getDistricts();
    }

    public LiveData<List<String>> getMandals(String distId) {
        return worksDao.getMandalList(distId);
    }

    public LiveData<String> getDistId(String distName) {
        return worksDao.getDistrictId(distName);
    }

    public LiveData<String> getMandalId(String mandalName) {
        return worksDao.getMandalId(mandalName);
    }

    public LiveData<Integer> getWorksCnt() {
        return worksDao.getWorksCount();
    }

    public LiveData<Integer> getSectorId(String sectorName) {
        return worksDao.getSectorId(sectorName);
    }

    public LiveData<List<WorkDetail>> getWorks() {
        return worksDao.getWorks();
    }

}
