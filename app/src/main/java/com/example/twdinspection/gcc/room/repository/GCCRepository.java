package com.example.twdinspection.gcc.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.gcc.room.dao.GCCDao;
import com.example.twdinspection.gcc.room.database.GCCDatabase;
import com.example.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.example.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.example.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.example.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.example.twdinspection.gcc.source.suppliers.punit.PUnits;

import java.util.List;

public class GCCRepository {

    private GCCDao gccDao;
    public LiveData<List<DivisionsInfo>> divisions = new MutableLiveData<>();
    public LiveData<List<DRDepots>> drGodowns = new MutableLiveData<>();
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


    public LiveData<List<DrGodowns>> getGoDowns(String divId, String socId) {
        return gccDao.getDrGoDowns(divId, socId);
    }

    public LiveData<List<DRDepots>> getDRDepots(String divId, String socId) {
        return gccDao.getDRDepots(divId, socId);
    }

    public LiveData<List<MFPGoDowns>> getMFPGoDowns(String divId, String socId) {
        return gccDao.getMFPDepots(divId, socId);
    }

    public LiveData<List<PUnits>> getPUnits(String divId, String socId) {
        return gccDao.getPUnits(divId, socId);
    }


    public LiveData<String> getDivisionID(String divisionName) {
        return gccDao.getDivisionID(divisionName);
    }

    public LiveData<String> getSocietyID(String divisionID, String societyName) {
        return gccDao.getSocietyID(divisionID, societyName);
    }

    public LiveData<DrGodowns> getGoDownID(String divisionID, String societyID, String goDownName) {
        return gccDao.getGoDownID(divisionID, societyID, goDownName);
    }

    public LiveData<DRDepots> getDRDepotID(String divisionID, String societyID, String depotName) {
        return gccDao.getDRDepotID(divisionID, societyID, depotName);
    }

    public LiveData<MFPGoDowns> getMFPGoDownID(String divisionID, String societyID, String mfpName) {
        return gccDao.getMFPGoDownID(divisionID, societyID, mfpName);
    }

    public LiveData<PUnits> getPUnitID(String divisionID, String societyID, String pUnitName) {
        return gccDao.getPUnitID(divisionID, societyID, pUnitName);
    }
}
