package com.example.twdinspection.schemes.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.inspection.source.DistManVillage.Districts;
import com.example.twdinspection.inspection.source.DistManVillage.Mandals;
import com.example.twdinspection.inspection.source.DistManVillage.Villages;
import com.example.twdinspection.schemes.room.repository.SchemesDMVRepository;
import com.example.twdinspection.schemes.source.FinancialYrsEntity;

import java.util.List;

public class SchemesDMVViewModel extends AndroidViewModel {
    private LiveData<List<Districts>> districts;
    private LiveData<List<Mandals>> mandals;
    private LiveData<List<Villages>> villages;
    private LiveData<List<FinancialYrsEntity>> financialYrs;
    private SchemesDMVRepository mRepository;

    public SchemesDMVViewModel(Application application) {
        super(application);
        districts = new MutableLiveData<>();
        mandals = new MutableLiveData<>();
        villages = new MutableLiveData<>();
        financialYrs = new MutableLiveData<>();
        mRepository = new SchemesDMVRepository(application);
    }

//    public LiveData<List<Districts>> getAllDistricts() {
//        if (districts != null) {
//            districts = mRepository.getDistricts();
//        }
//        return districts;
//    }
//
//
//
//    public LiveData<List<Mandals>> getAllMandals(int distId) {
//        if (mandals != null) {
//            mandals=mRepository.getMandals(distId);
//        }
//        return mandals;
//    }

    public LiveData<List<FinancialYrsEntity>> getFinancialYrs() {
        if (financialYrs != null) {
            financialYrs=mRepository.getFinancialYrs();
        }
        return financialYrs;
    }

//    public LiveData<List<Villages>> getAllVillages(int mandalId,int distId) {
//            villages=mRepository.getVillages(mandalId,distId);
//        return villages;
//    }
//
//    public LiveData<Integer> getDistId(String distName){
//        return mRepository.getDistId(distName);
//    }
//    public LiveData<Integer> getMandalId(String mandalName,int distId){
//        return mRepository.getMandalId(mandalName,distId);
//    }
//
//    public LiveData<Integer> getVillageId(String mandalName,int manId, int distId){
//        return mRepository.getVillageId(mandalName,manId,distId);
//    }
//
//    public LiveData<String> getInstId(String instName){
//        return mRepository.getInstId(instName);
//    }
}
