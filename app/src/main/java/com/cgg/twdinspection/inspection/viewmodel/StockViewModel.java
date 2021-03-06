package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.gcc.source.stock.PetrolStockDetailsResponse;
import com.cgg.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockViewModel extends AndroidViewModel {
    private MutableLiveData<StockDetailsResponse> commodities;
    private MutableLiveData<PetrolStockDetailsResponse> pLPGCommodities;
    private Application application;
    private ErrorHandlerInterface errorHandlerInterface;

    public StockViewModel(@NonNull Application application, Context context) {
        super(application);
        this.application = application;
        commodities = new MutableLiveData<>();
        pLPGCommodities = new MutableLiveData<>();
        try {
            errorHandlerInterface = (ErrorHandlerInterface) context;
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

    public LiveData<PetrolStockDetailsResponse> getPLPGStockData(String supplierID) {
        if (pLPGCommodities != null) {
            getPLPGStockDetails(supplierID);
        }
        return pLPGCommodities;
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

    private void getPLPGStockDetails(String supplierID) {
        TWDService twdService = TWDService.Factory.create("gcc");
        twdService.getPLPGMasterResponse(supplierID).enqueue(new Callback<PetrolStockDetailsResponse>() {
            @Override
            public void onResponse(@NotNull Call<PetrolStockDetailsResponse> call, @NotNull Response<PetrolStockDetailsResponse> response) {
                pLPGCommodities.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<PetrolStockDetailsResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, application);
            }
        });
    }

}
