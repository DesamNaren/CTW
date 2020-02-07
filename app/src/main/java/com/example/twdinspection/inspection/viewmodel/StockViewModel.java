package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.gcc.room.repository.GCCRepository;
import com.example.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.example.twdinspection.gcc.source.divisions.GetOfficesResponse;
import com.example.twdinspection.gcc.source.stock.CommonCommodity;
import com.example.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.example.twdinspection.inspection.Room.repository.MedicalInfoRepository;
import com.example.twdinspection.inspection.source.EmployeeResponse;
import com.example.twdinspection.inspection.source.LoginUser;
import com.example.twdinspection.inspection.source.MedicalDetailsBean;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.example.twdinspection.schemes.interfaces.SchemeSubmitInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockViewModel extends AndroidViewModel {
    private MutableLiveData<StockDetailsResponse> commodities;
    private Application application;
    private ErrorHandlerInterface errorHandlerInterface;

    public StockViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        commodities = new MutableLiveData<>();
        try {
            errorHandlerInterface = (ErrorHandlerInterface) application;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LiveData<StockDetailsResponse> getStockData(String supplierID) {
        if (commodities != null) {
            getStockDetails(supplierID);
        }
        return commodities;
    }

    private void getStockDetails(String supplierID) {
        TWDService twdService = TWDService.Factory.create("gcc");
        twdService.getDRDepotMasterResponse(supplierID).enqueue(new Callback<StockDetailsResponse>() {
            @Override
            public void onResponse(@NotNull Call<StockDetailsResponse> call, @NotNull Response<StockDetailsResponse> response) {
                commodities.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<StockDetailsResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, application);
            }
        });
    }

}
