package com.cgg.twdinspection.gcc.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.gcc.room.repository.GCCOfflineRepository;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;

import java.util.List;

public class GCCOfflineViewModel extends AndroidViewModel {

    private final GCCOfflineRepository mRepository;
    private LiveData<GccOfflineEntity> godownOfflineLiveData;
    private LiveData<List<GccOfflineEntity>> getGoDownsOfflineCount;

    public GCCOfflineViewModel(Application application) {
        super(application);
        mRepository = new GCCOfflineRepository(application);
        godownOfflineLiveData = new MutableLiveData<>();
        getGoDownsOfflineCount = new MutableLiveData<>();
    }

    public LiveData<GccOfflineEntity> getDRGoDownsOffline(String divId, String socId, String godownId) {
        if (godownOfflineLiveData != null) {
            godownOfflineLiveData = mRepository.getGCCRecords(divId, socId, godownId);
        }
        return godownOfflineLiveData;
    }

    public LiveData<GccOfflineEntity> getDRGoDownsOfflinePUnit(String divId, String socId, String godownId) {
        if (godownOfflineLiveData != null) {
            godownOfflineLiveData = mRepository.getGCCRecordsPUnit(divId, socId, godownId);
        }
        return godownOfflineLiveData;
    }


    public LiveData<List<GccOfflineEntity>> getGoDownsOfflineCount(String type) {
        if (getGoDownsOfflineCount != null) {
            getGoDownsOfflineCount = mRepository.getGCCOfflineCount(type);
        }
        return getGoDownsOfflineCount;
    }

}
