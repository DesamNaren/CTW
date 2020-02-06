package com.example.twdinspection.gcc.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.gcc.room.dao.GCCDao;
import com.example.twdinspection.gcc.room.database.GCCDatabase;
import com.example.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.example.twdinspection.gcc.source.suppliers.DRGodowns;

import java.util.List;

public class GCCRepository {

    private GCCDao gccDao;
    public LiveData<List<DivisionsInfo>> divisions = new MutableLiveData<>();
    public LiveData<List<DRGodowns>> drGodowns = new MutableLiveData<>();
    public int count;

    public GCCRepository(Application application) {
        GCCDatabase db = GCCDatabase.getDatabase(application);
        gccDao = db.gccDao();
    }

    public LiveData<List<String>> getDivisions() {
        return gccDao.getDivisionsInfo();
    }

    public LiveData<List<DivisionsInfo>> getSocieties(String divId) {
        return gccDao.getSocietyInfo(divId);
    }

    public LiveData<List<DRGodowns>> getDRGodowns() {
        return gccDao.getDRDepots();
    }


    public LiveData<String> getDivisionID(String divisionName) {
        return gccDao.getDivisionID(divisionName);
    }

    public LiveData<String> getSocietyID(String divisionID, String societyName) {
        return gccDao.getSocietyID(divisionID, societyName);
    }

}
