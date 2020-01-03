package com.example.twdinspection.schemes.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.inspection.source.DistManVillage.Districts;
import com.example.twdinspection.inspection.source.DistManVillage.Mandals;
import com.example.twdinspection.inspection.source.DistManVillage.Villages;
import com.example.twdinspection.schemes.room.repository.SchemesDMVRepository;
import com.example.twdinspection.schemes.source.DMV.SchemesDistricts;
import com.example.twdinspection.schemes.source.FinancialYrsEntity;
import com.example.twdinspection.schemes.source.InspectionRemarksEntity;
import com.example.twdinspection.schemes.source.SchemeRemarksResponse;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryReport;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryRequest;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchemesDMVViewModel extends AndroidViewModel {
    private LiveData<List<SchemesDistricts>> districts;
    private LiveData<List<Mandals>> mandals;
    private LiveData<List<Villages>> villages;
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

    public LiveData<List<SchemesDistricts>> getAllDistricts() {
        if (districts != null) {
            districts = mRepository.getDistricts();
        }
        return districts;
    }

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
