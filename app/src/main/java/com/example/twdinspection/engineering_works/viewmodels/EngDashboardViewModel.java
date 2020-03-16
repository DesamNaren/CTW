package com.example.twdinspection.engineering_works.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.engineering_works.source.SectorsEntity;
import com.example.twdinspection.gcc.source.divisions.GetOfficesResponse;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EngDashboardViewModel extends AndroidViewModel {

    private MutableLiveData<List<SectorsEntity>> sectorsLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;

    public EngDashboardViewModel(Context context, Application application) {
        super(application);
        sectorsLiveData=new MutableLiveData<>();
        this.context = context;
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }

    public LiveData<List<SectorsEntity>> getSectorResponse() {
        if (sectorsLiveData != null) {
            getSectorsResponseCall();
        }
        return sectorsLiveData;
    }

    private void getSectorsResponseCall() {
        TWDService twdService = TWDService.Factory.create("gcc");
        twdService.getDivisionMasterResponse().enqueue(new Callback<GetOfficesResponse>() {
            @Override
            public void onResponse(@NotNull Call<GetOfficesResponse> call, @NotNull Response<GetOfficesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    sectorsLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<GetOfficesResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}

