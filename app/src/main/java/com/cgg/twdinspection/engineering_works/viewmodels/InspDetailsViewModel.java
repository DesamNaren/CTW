package com.cgg.twdinspection.engineering_works.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.engineering_works.room.repository.GrantSchemeRepository;
import com.cgg.twdinspection.engineering_works.room.repository.SectorsRepository;
import com.cgg.twdinspection.engineering_works.source.GrantScheme;
import com.cgg.twdinspection.engineering_works.source.GrantSchemesResponse;
import com.cgg.twdinspection.engineering_works.source.SectorsEntity;
import com.cgg.twdinspection.engineering_works.source.SectorsResponse;
import com.cgg.twdinspection.engineering_works.source.StagesResponse;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InspDetailsViewModel extends AndroidViewModel {

    private MutableLiveData<SectorsResponse> sectorsResponseLiveData;
    private MutableLiveData<GrantSchemesResponse> schemesResponseMutableLiveData;
    private MutableLiveData<StagesResponse> stagesResponseMutableLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    private SectorsRepository sectorsRepository;
    private GrantSchemeRepository schemeRepository;
    private LiveData<List<SectorsEntity>> sectorsListLiveData;
    private LiveData<List<GrantScheme>> schemesListLiveData;


    public InspDetailsViewModel(Context context, Application application) {
        super(application);
        sectorsRepository=new SectorsRepository(application);
        schemeRepository=new GrantSchemeRepository(application);
        sectorsResponseLiveData = new MutableLiveData<>();
        schemesResponseMutableLiveData = new MutableLiveData<>();
        stagesResponseMutableLiveData = new MutableLiveData<>();
        sectorsListLiveData = new MutableLiveData<>();
        schemesListLiveData = new MutableLiveData<>();
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

    public LiveData<List<GrantScheme>> getGrantSchemes() {
        if (schemesListLiveData != null) {
            schemesListLiveData = schemeRepository.getGrantSchemes();
        }
        return schemesListLiveData;
    }

    public LiveData<GrantSchemesResponse> getSchemesResponse() {
        if (schemesResponseMutableLiveData != null) {
            getGrantSchemesResponseCall();
        }
        return schemesResponseMutableLiveData;
    }

    private void getGrantSchemesResponseCall() {
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getGrantSandSchemes().enqueue(new Callback<GrantSchemesResponse>() {
            @Override
            public void onResponse(@NotNull Call<GrantSchemesResponse> call, @NotNull Response<GrantSchemesResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                    schemesResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<GrantSchemesResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<StagesResponse> getStagesResponse(int sectorId) {
        if (stagesResponseMutableLiveData != null) {
            getStagesResponseCall(sectorId);
        }
        return stagesResponseMutableLiveData;
    }

    private void getStagesResponseCall(int sectorId) {
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getStages(sectorId).enqueue(new Callback<StagesResponse>() {
            @Override
            public void onResponse(@NotNull Call<StagesResponse> call, @NotNull Response<StagesResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                    stagesResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<StagesResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public int insertGrantSchemesInfo(List<GrantScheme> grantSchemes) {
        return schemeRepository.insertSchemes(grantSchemes);
    }

    public LiveData<Integer> getSectorId(String sectorName){
        return sectorsRepository.getSectorId(sectorName);
    }

    public LiveData<Integer> getgrantSchemeId(String schemeName){
        return schemeRepository.getSchemeId(schemeName);
    }

}
