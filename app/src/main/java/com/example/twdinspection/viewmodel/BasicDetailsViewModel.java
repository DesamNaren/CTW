package com.example.twdinspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.Room.repository.DistrictRepository;
import com.example.twdinspection.source.DistManVillage.Districts;

import java.util.List;

public class BasicDetailsViewModel extends AndroidViewModel {
    private LiveData<List<Districts>> districts;
    private DistrictRepository mRepository;

    public BasicDetailsViewModel(Application application) {
        super(application);
        districts = new MutableLiveData<>();
        mRepository = new DistrictRepository(application);
    }

    public LiveData<List<Districts>> getAllDistricts() {
        if (districts != null) {
            districts = mRepository.getDistricts();
        }
        return districts;
    }
}
