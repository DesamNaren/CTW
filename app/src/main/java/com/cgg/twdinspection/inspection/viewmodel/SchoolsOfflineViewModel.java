package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.inspection.interfaces.SchoolOfflineInterface;
import com.cgg.twdinspection.inspection.offline.SchoolsOfflineEntity;
import com.cgg.twdinspection.inspection.room.repository.SchoolsOfflineRepository;

import java.util.List;

public class SchoolsOfflineViewModel extends AndroidViewModel {

    private final SchoolsOfflineRepository mRepository;
    private LiveData<List<SchoolsOfflineEntity>> listLiveData;
    private LiveData<SchoolsOfflineEntity> schoolsOfflineEntityLiveData;
    private LiveData<List<String>> instLiveData;

    public SchoolsOfflineViewModel(Application application) {
        super(application);
        mRepository = new SchoolsOfflineRepository(application);
        schoolsOfflineEntityLiveData = new MutableLiveData<>();
        listLiveData = new MutableLiveData<>();
        instLiveData = new MutableLiveData<>();
    }

    public LiveData<List<SchoolsOfflineEntity>> getSchoolsOffline() {
        if (listLiveData != null) {
            listLiveData = mRepository.getSchoolsRecordsOfflineCount();
        }
        return listLiveData;
    }

    public LiveData<SchoolsOfflineEntity> getSchoolsOfflineRecord(String inst_id) {
        if (schoolsOfflineEntityLiveData != null) {
            schoolsOfflineEntityLiveData = mRepository.getSchoolsRecordOfflineCount(inst_id);
        }
        return schoolsOfflineEntityLiveData;
    }

    public LiveData<List<String>> getPreviousDayInsts(String time) {
        if (instLiveData != null) {
            instLiveData = mRepository.getPreviousDayInsts(time);
        }
        return instLiveData;
    }

    public void insertSchoolRecord(final SchoolOfflineInterface schoolOfflineInterface,
                                   final SchoolsOfflineEntity schoolsOfflineEntity) {
        mRepository.insertSchoolRecord(schoolOfflineInterface, schoolsOfflineEntity);
    }

    public void deleteSchoolsRecord(final SchoolOfflineInterface schoolOfflineInterface, String instId, boolean flag) {
        mRepository.deleteSchoolsRecord(schoolOfflineInterface,
                instId, flag);
    }

    public void deleteSchoolsRecord(String instId) {
        mRepository.deletePreviousdaySchoolsRecord(instId);
    }
}
