package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.gcc.room.repository.GCCRepository;
import com.example.twdinspection.gcc.source.divisions.DivisionsInfo;

import java.util.List;

public class DivisionSelectionViewModel extends AndroidViewModel {
    private LiveData<List<String>> divisions;
    private LiveData<List<DivisionsInfo>> societies;
    private GCCRepository mRepository;

    public DivisionSelectionViewModel(Application application) {
        super(application);
        divisions = new MutableLiveData<>();
        societies = new MutableLiveData<>();
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


    public LiveData<String> getDivisionId(String divName) {
        return mRepository.getDivisionID(divName);
    }

    public LiveData<String> getSocietyId(String divId, String socName) {
        return mRepository.getSocietyID(divId, socName);
    }

}
