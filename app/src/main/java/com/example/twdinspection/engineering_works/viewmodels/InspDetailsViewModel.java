package com.example.twdinspection.engineering_works.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.engineering_works.room.repository.SectorsRepository;
import com.example.twdinspection.engineering_works.source.SectorsEntity;
import com.example.twdinspection.engineering_works.source.SectorsResponse;
import com.example.twdinspection.engineering_works.source.WorksMasterResponse;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InspDetailsViewModel extends AndroidViewModel {

    private MutableLiveData<SectorsResponse> sectorsResponseLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    private SectorsRepository sectorsRepository;
    private LiveData<List<SectorsEntity>> sectorsListLiveData;


    public InspDetailsViewModel(Context context, Application application) {
        super(application);
        sectorsRepository=new SectorsRepository(application);
        sectorsResponseLiveData = new MutableLiveData<>();
        sectorsListLiveData = new MutableLiveData<>();
        this.context = context;
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }

    public LiveData<List<SectorsEntity>> getSectors() {
        if (sectorsListLiveData != null) {
            sectorsListLiveData = sectorsRepository.getSectors();
        }
        return sectorsListLiveData;
    }

    public LiveData<SectorsResponse> getSectorResponse() {
        if (sectorsResponseLiveData != null) {
            getSectorsResponseCall();
        }
        return sectorsResponseLiveData;
    }

    private void getSectorsResponseCall() {
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getSectorsMaster().enqueue(new Callback<SectorsResponse>() {
            @Override
            public void onResponse(@NotNull Call<SectorsResponse> call, @NotNull Response<SectorsResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                    sectorsResponseLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SectorsResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public int insertSectorsInfo(List<SectorsEntity> sectorsEntities) {
        return sectorsRepository.insertSectors(sectorsEntities);
    }

}

