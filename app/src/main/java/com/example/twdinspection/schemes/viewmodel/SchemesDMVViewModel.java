package com.example.twdinspection.schemes.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.schemes.room.repository.SchemesDMVRepository;
import com.example.twdinspection.schemes.source.DMV.SchemeDistrict;
import com.example.twdinspection.schemes.source.DMV.SchemeMandal;
import com.example.twdinspection.schemes.source.DMV.SchemeVillage;
import com.example.twdinspection.schemes.source.finyear.FinancialYrsEntity;
import com.example.twdinspection.schemes.source.remarks.InspectionRemarksEntity;

import java.util.List;

public class SchemesDMVViewModel extends AndroidViewModel {
    private LiveData<List<SchemeDistrict>> districts;
    private LiveData<List<SchemeMandal>> mandals;
    private LiveData<List<SchemeVillage>> villages;
    private LiveData<List<FinancialYrsEntity>> financialYrs;
    private SchemesDMVRepository mRepository;
    private MutableLiveData<List<InspectionRemarksEntity>> iListMutableLiveData;

    public SchemesDMVViewModel(Application application) {
        super(application);
        districts = new MutableLiveData<>();
        mandals = new MutableLiveData<>();
        villages = new MutableLiveData<>();
        financialYrs = new MutableLiveData<>();
        iListMutableLiveData = new MutableLiveData<>();
        mRepository = new SchemesDMVRepository(application);
    }

    public LiveData<List<SchemeDistrict>> getAllDistricts() {
        if (districts != null) {
            districts = mRepository.getDistricts();
        }
        return districts;
    }

    public LiveData<List<SchemeMandal>> getAllMandals(String distId) {
        if (mandals != null) {
            mandals=mRepository.getMandals(distId);
        }
        return mandals;
    }

    public LiveData<List<SchemeVillage>> getAllVillages(String mandalId,String distId) {
        villages=mRepository.getVillages(mandalId,distId);
        return villages;
    }

    public LiveData<List<FinancialYrsEntity>> getFinancialYrs() {
        if (financialYrs != null) {
            financialYrs=mRepository.getFinancialYrs();
        }
        return financialYrs;
    }

    public LiveData<String> getDistId(String distName){
        return mRepository.getDistId(distName);
    }
    public LiveData<String> getMandalId(String mandalName,String distId){
        return mRepository.getMandalId(mandalName,distId);
    }

    public LiveData<String> getVillageId(String mandalName,String manId, String distId){
        return mRepository.getVillageId(mandalName,manId,distId);
    }

    public LiveData<String> getFinYearId(String finYear){
        return mRepository.getFinYearId(finYear);
    }


//    public MutableLiveData<List<InspectionRemarksEntity>> getInspectionRemarks() {
//        TWDService twdService = TWDService.Factory.create("schemes");
//        twdService.getInspectionRemarks().enqueue(new Callback<SchemeRemarksResponse>() {
//            @Override
//            public void onResponse(@NotNull Call<SchemeRemarksResponse> call, @NotNull Response<SchemeRemarksResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    if (response.body().getStatusCode() != null && response.body().getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
//                        if (response.body().getSchemes() != null && response.body().getSchemes().size() > 0) {
//                            List<InspectionRemarksEntity> beneficiaryDetails = response.body().getSchemes();
//                            iListMutableLiveData.setValue(beneficiaryDetails);
//                        }
//                    } else if (response.body().getStatusCode() != null && response.body().getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {
//
//                    } else {
//
//                    }
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<SchemeRemarksResponse> call, @NotNull Throwable t) {
//                Log.i("UU", "onFailure: " + t.getMessage());
//            }
//        });
//        return iListMutableLiveData;
//    }
}
