package com.example.twdinspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.Room.repository.DMVRepository;
import com.example.twdinspection.source.DistManVillage.Districts;
import com.example.twdinspection.source.DistManVillage.Mandals;
import com.example.twdinspection.source.DistManVillage.Villages;
import com.example.twdinspection.source.GeneralInformation.InstitutesEntity;

import java.util.List;

public class DMVDetailsViewModel extends AndroidViewModel {
    private LiveData<List<Districts>> districts;
    private LiveData<List<Mandals>> mandals;
    private LiveData<List<Villages>> villages;
    private LiveData<List<InstitutesEntity>> inst_names;
    private DMVRepository mRepository;

    public DMVDetailsViewModel(Application application) {
        super(application);
        districts = new MutableLiveData<>();
        mandals = new MutableLiveData<>();
        villages = new MutableLiveData<>();
        inst_names = new MutableLiveData<>();
        mRepository = new DMVRepository(application);
    }

    public LiveData<List<Districts>> getAllDistricts() {
        if (districts != null) {
            districts = mRepository.getDistricts();
        }
        return districts;
    }



    public LiveData<List<Mandals>> getAllMandals(int distId) {
        if (mandals != null) {
            mandals=mRepository.getMandals(distId);
        }
        return mandals;
    }

    public LiveData<List<InstitutesEntity>> getInstitutes() {
        if (inst_names != null) {
            inst_names=mRepository.getInstitutes();
        }
        return inst_names;
    }

    public LiveData<List<Villages>> getAllVillages(int mandalId,int distId) {
            villages=mRepository.getVillages(mandalId,distId);
        return villages;
    }

    public LiveData<Integer> getDistId(String distName){
        return mRepository.getDistId(distName);
    }
    public LiveData<Integer> getMandalId(String mandalName,int distId){
        return mRepository.getMandalId(mandalName,distId);
    }

    public LiveData<Integer> getVillageId(String mandalName,int manId, int distId){
        return mRepository.getVillageId(mandalName,manId,distId);
    }

    public LiveData<String> getInstId(String instName){
        return mRepository.getInstId(instName);
    }
}
