package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.inspection.Room.repository.DMVRepository;
import com.example.twdinspection.inspection.source.GeneralInformation.InstitutesEntity;
import com.example.twdinspection.inspection.source.dmv.SchoolDistrict;
import com.example.twdinspection.inspection.source.dmv.SchoolMandal;
import com.example.twdinspection.inspection.source.dmv.SchoolVillage;

import java.util.List;

public class DMVDetailsViewModel extends AndroidViewModel {
    private LiveData<List<SchoolDistrict>> districts;
    private LiveData<List<SchoolMandal>> mandals;
    private LiveData<List<SchoolVillage>> villages;
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

    public LiveData<List<SchoolDistrict>> getAllDistricts() {
        if (districts != null) {
            districts = mRepository.getDistricts();
        }
        return districts;
    }



    public LiveData<List<SchoolMandal>> getAllMandals(int distId) {
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

    public LiveData<List<SchoolVillage>> getAllVillages(int mandalId,int distId) {
            villages=mRepository.getVillages(mandalId,distId);
        return villages;
    }

    public LiveData<String> getDistId(String distName){
        return mRepository.getDistId(distName);
    }

    public LiveData<String> getMandalId(String mandalName,int distId){
        return mRepository.getMandalId(mandalName,distId);
    }

    public LiveData<String> getVillageId(String mandalName,int manId, int distId){
        return mRepository.getVillageId(mandalName,manId,distId);
    }

    public LiveData<String> getInstId(String instName){
        return mRepository.getInstId(instName);
    }
}
