package com.example.twdinspection.gcc.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.databinding.ActivityGccSyncBinding;
import com.example.twdinspection.gcc.source.divisions.GetOfficesResponse;
import com.example.twdinspection.gcc.source.suppliers.DRDepotMasterResponse;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GCCSyncViewModel extends AndroidViewModel {
    private MutableLiveData<GetOfficesResponse> divisionsInfoMutableLiveData;
    private MutableLiveData<DRDepotMasterResponse> supplierInfoMutableLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    private ActivityGccSyncBinding binding;

    public GCCSyncViewModel(Context context, Application application, ActivityGccSyncBinding binding) {
        super(application);
        this.context=context;
        this.binding=binding;
        divisionsInfoMutableLiveData = new MutableLiveData<>();
        supplierInfoMutableLiveData = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }

    public LiveData<GetOfficesResponse> getDivisionsResponse() {
        if (divisionsInfoMutableLiveData != null) {
            getDivisionsResponseCall();
        }
        return divisionsInfoMutableLiveData;
    }

    private void getDivisionsResponseCall() {
        TWDService twdService = TWDService.Factory.create("gcc");
        twdService.getDivisionMasterResponse().enqueue(new Callback<GetOfficesResponse>() {
            @Override
            public void onResponse(@NotNull Call<GetOfficesResponse> call, @NotNull Response<GetOfficesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    divisionsInfoMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<GetOfficesResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<DRDepotMasterResponse> getSupplierResponse() {
        if (supplierInfoMutableLiveData != null) {
            getSupplierResponseCall();
        }
        return supplierInfoMutableLiveData;
    }

    private void getSupplierResponseCall() {
        TWDService twdService = TWDService.Factory.create("gcc");
        twdService.getDRDepotMasterResponse().enqueue(new Callback<DRDepotMasterResponse>() {
            @Override
            public void onResponse(@NotNull Call<DRDepotMasterResponse> call, @NotNull Response<DRDepotMasterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                        supplierInfoMutableLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(@NotNull Call<DRDepotMasterResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }
}

