package com.example.twdinspection.inspection.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.gcc.interfaces.GCCDivisionInterface;
import com.example.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.example.twdinspection.inspection.interfaces.InstSelInterface;
import com.example.twdinspection.inspection.room.repository.InstSelectionRepository;
import com.example.twdinspection.inspection.source.inst_menu_info.InstSelectionInfo;

import java.util.List;

public class InstSelectionViewModel extends AndroidViewModel {
    private LiveData<InstSelectionInfo> infoLiveData;
    private InstSelectionRepository mRepository;
    private Context context;

    public InstSelectionViewModel(Application context) {
        super(context);
        infoLiveData = new MutableLiveData<>();
        mRepository = new InstSelectionRepository(context);
        this.context=context;

    }

    public LiveData<InstSelectionInfo> getSelectedInst() {
        if (infoLiveData != null) {
            infoLiveData = mRepository.getSelectedInst();
        }
        return infoLiveData;
    }
    public void insertInstitutes(InstSelInterface instSelInterface,InstSelectionInfo instSelectionInfo) {
        mRepository.insertSelInst(instSelInterface,instSelectionInfo);
    }


}
