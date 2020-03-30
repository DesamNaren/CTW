package com.cgg.twdinspection.engineering_works.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.engineering_works.room.repository.SectorsRepository;
import com.cgg.twdinspection.engineering_works.source.SectorsEntity;
import com.cgg.twdinspection.engineering_works.source.SectorsResponse;
import com.cgg.twdinspection.engineering_works.source.WorksMasterResponse;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EngDashboardViewModel extends AndroidViewModel {

    private MutableLiveData<SectorsResponse> sectorsResponseLiveData;
    private MutableLiveData<WorksMasterResponse> worksMasterLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    private SectorsRepository sectorsRepository;
    private LiveData<List<SectorsEntity>> sectorsListLiveData;


    public EngDashboardViewModel(Context context, Application application) {
        super(application);
        sectorsRepository=new SectorsRepository(application);
        sectorsResponseLiveData = new MutableLiveData<>();
        worksMasterLiveData = new MutableLiveData<>();
        sectorsListLiveData = new MutableLiveData<>();
        this.context = context;
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }


    public LiveData<WorksMasterResponse> getWorksMaster() {
        if (worksMasterLiveData != null) {
            getWorksMasterResponse();
        }
        return worksMasterLiveData;
    }

    private void getWorksMasterResponse() {
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getWorksMaster().enqueue(new Callback<WorksMasterResponse>() {
            @Override
            public void onResponse(@NotNull Call<WorksMasterResponse> call, @NotNull Response<WorksMasterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //                    worksMasterLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<WorksMasterResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }


}

