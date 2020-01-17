package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.inspection.Room.repository.MenuSectionsRepository;
import com.example.twdinspection.inspection.source.instMenuInfo.InstMenuInfoEntity;

import java.util.ArrayList;
import java.util.List;

public class InstMainViewModel extends AndroidViewModel {
    private MenuSectionsRepository mRepository;
    private LiveData<List<InstMenuInfoEntity>> instMenuInfoEntities;

    public InstMainViewModel(Application application) {
        super(application);
        mRepository = new MenuSectionsRepository(application);
        instMenuInfoEntities = new MutableLiveData<>();
    }

    public void insertMenuSections(List<InstMenuInfoEntity> menuInfoEntities) {
        mRepository.insertMenuSections(menuInfoEntities);
    }

    public LiveData<List<InstMenuInfoEntity>> getAllSections() {
        if (instMenuInfoEntities != null) {
            instMenuInfoEntities = mRepository.getSections();
        }
        return instMenuInfoEntities;
    }
    public long updateSectionInfo(String time,int id,String instId) {
        return mRepository.updateSectionInfo(time,id,instId);
    }

}
