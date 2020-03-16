package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.inspection.room.repository.InstSelectionRepository;
import com.example.twdinspection.inspection.source.inst_menu_info.InstSelectionInfo;

public class InstSelectionViewModel extends AndroidViewModel {
    private LiveData<InstSelectionInfo> infoLiveData;
    private InstSelectionRepository mRepository;

    public InstSelectionViewModel(Application application) {
        super(application);
        infoLiveData = new MutableLiveData<>();
        mRepository = new InstSelectionRepository(application);
    }

    public LiveData<InstSelectionInfo> getSelectedInst() {
        if (infoLiveData != null) {
            infoLiveData = mRepository.getSelectedInst();
        }
        return infoLiveData;
    }

    public void insertSelectedInst(InstSelectionInfo instSelectionInfo) {
        mRepository.insertInstSelection(instSelectionInfo);
    }
}
