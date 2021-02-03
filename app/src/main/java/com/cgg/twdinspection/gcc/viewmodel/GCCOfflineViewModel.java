package com.cgg.twdinspection.gcc.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.gcc.room.repository.GCCOfflineRepository;
import com.cgg.twdinspection.gcc.room.repository.GCCRepository;
import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.offline.drgodown.DrGodownOffline;
import com.cgg.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.cgg.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.cgg.twdinspection.gcc.source.suppliers.lpg.LPGSupplierInfo;
import com.cgg.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolSupplierInfo;
import com.cgg.twdinspection.gcc.source.suppliers.punit.PUnits;

import java.util.List;

public class GCCOfflineViewModel extends AndroidViewModel {

    private GCCOfflineRepository mRepository;
    private LiveData<DrGodownOffline> godownOfflineLiveData;

    public GCCOfflineViewModel(Application application) {
        super(application);
        mRepository = new GCCOfflineRepository(application);
        godownOfflineLiveData = new MutableLiveData<>();
    }

    public LiveData<DrGodownOffline> getDRGoDownsOffline(String divId, String socId, String godownId) {
        if (godownOfflineLiveData != null) {
            godownOfflineLiveData = mRepository.getGoDownsOffline(divId, socId, godownId);
        }
        return godownOfflineLiveData;
    }
}
