package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.inspection.interfaces.InstSelInterface;
import com.cgg.twdinspection.inspection.room.repository.InstLatestTimeRepository;
import com.cgg.twdinspection.inspection.room.repository.InstSelectionRepository;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstLatestTimeInfo;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstSelectionInfo;

import java.util.List;

public class InstSelectionViewModel extends AndroidViewModel {
    private LiveData<InstSelectionInfo> infoLiveData;
    private LiveData<String> randomNo;
    private LiveData<List<InstLatestTimeInfo>> timeInfoLiveData;
    private InstSelectionRepository mRepository;
    private InstLatestTimeRepository timeRepository;
    private Context context;

    public InstSelectionViewModel(Application context) {
        super(context);
        infoLiveData = new MutableLiveData<>();
        randomNo = new MutableLiveData<>();
        timeInfoLiveData = new MutableLiveData<>();
        mRepository = new InstSelectionRepository(context);
        timeRepository = new InstLatestTimeRepository(context);
        this.context = context;

    }

    public LiveData<InstSelectionInfo> getSelectedInst(String instId) {
        if (infoLiveData != null) {
            infoLiveData = mRepository.getSelectedInst(instId);
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

    public void insertLatestTime(InstLatestTimeInfo instLatestTimeInfo) {
        timeRepository.insertLatestTime(instLatestTimeInfo);
    }

    public void updateTimeInfo(String time, String instId) {
        timeRepository.updateTimeInfo(time, instId);
    }
    public LiveData<List<InstLatestTimeInfo>> getLatestTimeInfo() {
        if (timeInfoLiveData != null) {
            timeInfoLiveData = timeRepository.getLatestTimeInfo();
        }
        return timeInfoLiveData;
    }
    public void deleteTimeInfo( String instId) {
        timeRepository.deleteTimeInfo(instId);
    }
}
