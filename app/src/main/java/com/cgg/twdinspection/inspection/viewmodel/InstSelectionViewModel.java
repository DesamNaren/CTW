package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.inspection.interfaces.InstSelInterface;
import com.cgg.twdinspection.inspection.room.repository.InstSelectionRepository;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstSelectionInfo;

public class InstSelectionViewModel extends AndroidViewModel {
    private LiveData<InstSelectionInfo> infoLiveData;
    private LiveData<String> randomNo;
    private InstSelectionRepository mRepository;
    private Context context;

    public InstSelectionViewModel(Application context) {
        super(context);
        infoLiveData = new MutableLiveData<>();
        randomNo = new MutableLiveData<>();
        mRepository = new InstSelectionRepository(context);
        this.context = context;

    }

    public LiveData<InstSelectionInfo> getSelectedInst() {
        if (infoLiveData != null) {
            infoLiveData = mRepository.getSelectedInst();
        }
        return infoLiveData;
    }

   public LiveData<String> getRandomId(String inst_id) {
        if (randomNo != null) {
            randomNo = mRepository.getRandomNo(inst_id);
        }
        return randomNo;
    }

    public void insertInstitutes(InstSelInterface instSelInterface, InstSelectionInfo instSelectionInfo) {
        mRepository.insertSelInst(instSelInterface, instSelectionInfo);
    }


}
