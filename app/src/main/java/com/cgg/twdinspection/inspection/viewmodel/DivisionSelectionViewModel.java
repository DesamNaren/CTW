package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.gcc.room.repository.GCCRepository;
import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.cgg.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.cgg.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.cgg.twdinspection.gcc.source.suppliers.punit.PUnits;

import java.util.List;

public class DivisionSelectionViewModel extends AndroidViewModel {
    private LiveData<List<String>> divisions;
    private LiveData<List<DivisionsInfo>> societies;
    private LiveData<List<DrGodowns>> drGodowns;
    private LiveData<List<DrGodowns>> allDrGodowns;
    private LiveData<List<DRDepots>> drDepots;
    private LiveData<List<DRDepots>> allDrDepots;
    private LiveData<List<MFPGoDowns>> mfpGoDowns;
    private LiveData<List<MFPGoDowns>> allMfpGoDowns;
    private LiveData<List<PUnits>> pUnits;
    private LiveData<List<PUnits>> allpUnits;
    private GCCRepository mRepository;

    public DivisionSelectionViewModel(Application application) {
        super(application);
        divisions = new MutableLiveData<>();
        societies = new MutableLiveData<>();
        drGodowns = new MutableLiveData<>();
        allDrGodowns = new MutableLiveData<>();
        drDepots = new MutableLiveData<>();
        allDrDepots = new MutableLiveData<>();
        mfpGoDowns = new MutableLiveData<>();
        allMfpGoDowns = new MutableLiveData<>();
        pUnits = new MutableLiveData<>();
        allpUnits = new MutableLiveData<>();
        mRepository = new GCCRepository(application);
    }

    public LiveData<List<String>> getAllDivisions() {
        if (divisions != null) {
            divisions = mRepository.getDivisions();
        }
        return divisions;
    }

    public LiveData<List<DivisionsInfo>> getSocieties(String divId) {
        if (societies != null) {
            societies = mRepository.getSocieties(divId);
        }
        return societies;
    }


    public LiveData<List<DrGodowns>> getDRGoDowns(String divId, String socId) {
        if (drGodowns != null) {
            drGodowns = mRepository.getGoDowns(divId, socId);
        }
        return drGodowns;
    }
    public LiveData<List<DrGodowns>> getAllDRGoDowns() {
        if (allDrGodowns != null) {
            allDrGodowns = mRepository.getAllGoDowns();
        }
        return allDrGodowns;
    }

    public LiveData<List<DRDepots>> getDRDepots(String divId, String socId) {
        if (drDepots != null) {
            drDepots = mRepository.getDRDepots(divId, socId);
        }
        return drDepots;
    }
    public LiveData<List<DRDepots>> getAllDRDepots() {
        if (allDrDepots != null) {
            allDrDepots = mRepository.getAllDRDepots();
        }
        return allDrDepots;
    }

    public LiveData<List<MFPGoDowns>> getMFPGoDowns(String divId) {
        if (mfpGoDowns != null) {
            mfpGoDowns = mRepository.getMFPGoDowns(divId);
        }
        return mfpGoDowns;
    }

    public LiveData<List<MFPGoDowns>> getAllMFPGoDowns() {
        if (allMfpGoDowns != null) {
            allMfpGoDowns = mRepository.getAllMFPGoDowns();
        }
        return allMfpGoDowns;
    }


    public LiveData<List<PUnits>> getPUnits(String divId, String socId) {
        if (pUnits != null) {
            pUnits = mRepository.getPUnits(divId, socId);
        }
        return pUnits;
    }
    public LiveData<List<PUnits>> getAllPUnits() {
        if (allpUnits != null) {
            allpUnits = mRepository.getAllPUnits();
        }
        return allpUnits;
    }

    public LiveData<List<PUnits>> getPUnits(String divId) {
        if (pUnits != null) {
            pUnits = mRepository.getPUnits(divId);
        }
        return pUnits;
    }
    public LiveData<String> getDivisionId(String divName) {
        return mRepository.getDivisionID(divName);
    }

    public LiveData<String> getSocietyId(String divId, String socName) {
        return mRepository.getSocietyID(divId, socName);
    }


    public LiveData<DrGodowns> getGODownID(String divId, String socID, String goDownName) {
        return mRepository.getGoDownID(divId, socID, goDownName);
    }

    public LiveData<DRDepots> getDRDepotID(String divId, String socID, String depotName) {
        return mRepository.getDRDepotID(divId, socID, depotName);
    }

    public LiveData<MFPGoDowns> getMFPGoDownID(String divId, String mfpName) {
        return mRepository.getMFPGoDownID(divId, mfpName);
    }

    public LiveData<PUnits> getPUnitID(String divId, String socID, String pUnitName) {
        return mRepository.getPUnitID(divId, socID, pUnitName);
    }
    public LiveData<PUnits> getPUnitID(String divId, String pUnitName) {
        return mRepository.getPUnitID(divId, pUnitName);
    }
}
