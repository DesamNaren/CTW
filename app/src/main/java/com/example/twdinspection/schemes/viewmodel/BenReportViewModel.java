package com.example.twdinspection.schemes.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.schemes.room.repository.SchemesInfoRepository;
import com.example.twdinspection.schemes.source.SchemesInfoEntity;

import java.util.List;

public class BenReportViewModel extends AndroidViewModel {
    private LiveData<List<SchemesInfoEntity>> schemesList;
    private SchemesInfoRepository mRepository;

    public BenReportViewModel(Application application) {
        super(application);
        schemesList = new MutableLiveData<>();
        mRepository = new SchemesInfoRepository(application);
    }

    public LiveData<List<SchemesInfoEntity>> getSchemesInfo() {
        if (schemesList != null) {
            schemesList = mRepository.getSchemesInfo();
        }
        return schemesList;
    }
}
