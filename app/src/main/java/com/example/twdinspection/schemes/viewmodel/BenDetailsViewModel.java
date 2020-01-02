package com.example.twdinspection.schemes.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.inspection.source.DistManVillage.Districts;
import com.example.twdinspection.inspection.source.DistManVillage.Mandals;
import com.example.twdinspection.inspection.source.DistManVillage.Villages;
import com.example.twdinspection.inspection.source.GeneralInformation.InstitutesEntity;
import com.example.twdinspection.schemes.room.repository.SchemesDMVRepository;
import com.example.twdinspection.schemes.room.repository.SchemesInfoRepository;
import com.example.twdinspection.schemes.source.SchemesInfoEntity;

import java.util.List;

public class BenDetailsViewModel extends AndroidViewModel {

    public BenDetailsViewModel(Application application) {
        super(application);
    }
}
