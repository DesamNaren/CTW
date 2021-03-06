package com.cgg.twdinspection.gcc.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.gcc.room.dao.GCCDao;
import com.cgg.twdinspection.gcc.room.database.GCCDatabase;
import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.cgg.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.cgg.twdinspection.gcc.source.suppliers.lpg.LPGSupplierInfo;
import com.cgg.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolSupplierInfo;
import com.cgg.twdinspection.gcc.source.suppliers.punit.PUnits;

import java.util.List;

public class GCCRepository {

    private final GCCDao gccDao;
    public LiveData<List<DivisionsInfo>> divisions = new MutableLiveData<>();
    public LiveData<List<DRDepots>> drGoDowns = new MutableLiveData<>();

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


    public LiveData<List<PetrolSupplierInfo>> getPetrolSuppliers(String divId, String socId) {
        return gccDao.getPetrolPumps(divId, socId);
    }

    public LiveData<List<LPGSupplierInfo>> getLPGSuppliers(String divId, String socId) {
        return gccDao.getLPGSuppliers(divId, socId);
    }

    public LiveData<List<DrGodowns>> getAllGoDowns() {
        return gccDao.getAllDrGoDowns();
    }

    public LiveData<List<DivisionsInfo>> getAllDivisions() {
        return gccDao.getAllDivisions();
    }

    public LiveData<List<PetrolSupplierInfo>> getAllPetrolSuppliers() {
        return gccDao.getAllPetrolSuppliers();
    }

    public LiveData<List<LPGSupplierInfo>> getAllLPGSuppliers() {
        return gccDao.getAllLPGSuppliers();
    }

    public LiveData<List<DRDepots>> getDRDepots(String divId, String socId) {
        return gccDao.getDRDepots(divId, socId);
    }

    public LiveData<List<MFPGoDowns>> getMFPGoDowns(String divId) {
        return gccDao.getMFPDepots(divId);
    }

    public LiveData<List<PUnits>> getPUnits(String divId, String socId) {
        return gccDao.getPUnits(divId, socId);
    }

    public LiveData<List<DRDepots>> getAllDRDepots() {
        return gccDao.getAllDRDepots();
    }

    public LiveData<List<MFPGoDowns>> getAllMFPGoDowns() {
        return gccDao.getAllMFPDepots();
    }

    public LiveData<List<PUnits>> getAllPUnits() {
        return gccDao.getAllPUnits();
    }


    public LiveData<List<PUnits>> getPUnits(String divId) {
        return gccDao.getPUnitsDiv(divId);
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

    public LiveData<PetrolSupplierInfo> getPetrolPumpID(String divisionID, String societyID, String goDownName) {
        return gccDao.getPetrolPumpID(divisionID, societyID, goDownName);
    }

    public LiveData<LPGSupplierInfo> getLPGID(String divisionID, String societyID, String goDownName) {
        return gccDao.getLPGID(divisionID, societyID, goDownName);
    }

    public LiveData<DRDepots> getDRDepotID(String divisionID, String societyID, String depotName) {
        return gccDao.getDRDepotID(divisionID, societyID, depotName);
    }

    public LiveData<MFPGoDowns> getMFPGoDownID(String divisionID, String mfpName) {
        return gccDao.getMFPGoDownID(divisionID, mfpName);
    }

    public LiveData<PUnits> getPUnitID(String divisionID, String societyID, String pUnitName) {
        return gccDao.getPUnitID(divisionID, societyID, pUnitName);
    }
}
