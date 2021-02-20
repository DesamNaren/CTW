package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.gcc.room.repository.GCCOfflineRepository;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.inspection.offline.SchoolsOfflineEntity;
import com.cgg.twdinspection.inspection.room.repository.SchoolsOfflineRepository;

import java.util.List;

public class SchoolsOfflineViewModel extends AndroidViewModel {

    private final SchoolsOfflineRepository mRepository;
    private LiveData<List<SchoolsOfflineEntity>> listLiveData;

    public SchoolsOfflineViewModel(Application application) {
        super(application);
        mRepository = new SchoolsOfflineRepository(application);
        listLiveData = new MutableLiveData<>();
    }

    public LiveData<List<SchoolsOfflineEntity>> getSchoolsOffline() {
        if (listLiveData != null) {
            listLiveData = mRepository.getSchoolsRecordsOfflineCount();
        }
        return listLiveData;
    }

}
