package com.cgg.twdinspection.gcc.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.databinding.ActivityGccSyncBinding;
import com.cgg.twdinspection.gcc.source.divisions.GetOfficesResponse;
import com.cgg.twdinspection.gcc.source.suppliers.depot.DRDepotMasterResponse;
import com.cgg.twdinspection.gcc.source.suppliers.dr_godown.DRGoDownMasterResponse;
import com.cgg.twdinspection.gcc.source.suppliers.lpg.LPGMasterResponse;
import com.cgg.twdinspection.gcc.source.suppliers.mfp.MFPGoDownMasterResponse;
import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolPumpMasterResponse;
import com.cgg.twdinspection.gcc.source.suppliers.punit.PUnitMasterResponse;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GCCSyncViewModel extends AndroidViewModel {
    private MutableLiveData<GetOfficesResponse> divisionsInfoMutableLiveData;
    private MutableLiveData<DRDepotMasterResponse> drDepotMasterResponseMutableLiveData;
    private MutableLiveData<DRGoDownMasterResponse> drGoDownMasterResponseMutableLiveData;
    private MutableLiveData<MFPGoDownMasterResponse> mfpGoDownMasterResponseMutableLiveData;
    private MutableLiveData<PUnitMasterResponse> pUnitMasterResponseMutableLiveData;
    private MutableLiveData<PetrolPumpMasterResponse> petrolPumpMasterResponseMutableLiveData;
    private MutableLiveData<LPGMasterResponse> lpgMasterResponseMutableLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    private ActivityGccSyncBinding binding;

    public GCCSyncViewModel(Context context, Application application, ActivityGccSyncBinding binding) {
        super(application);
        this.context = context;
        this.binding = binding;
        divisionsInfoMutableLiveData = new MutableLiveData<>();
        drDepotMasterResponseMutableLiveData = new MutableLiveData<>();
        drGoDownMasterResponseMutableLiveData = new MutableLiveData<>();
        mfpGoDownMasterResponseMutableLiveData = new MutableLiveData<>();
        pUnitMasterResponseMutableLiveData = new MutableLiveData<>();
        petrolPumpMasterResponseMutableLiveData = new MutableLiveData<>();
        lpgMasterResponseMutableLiveData = new MutableLiveData<>();
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

    public LiveData<DRDepotMasterResponse> getDRDepotsResponse() {
        if (drDepotMasterResponseMutableLiveData != null) {
            getDRDepotMasterCall();
        }
        return drDepotMasterResponseMutableLiveData;
    }

    public LiveData<DRGoDownMasterResponse> getDRGoDownsResponse() {
        if (drGoDownMasterResponseMutableLiveData != null) {
            getDRGoDownMasterCall();
        }
        return drGoDownMasterResponseMutableLiveData;
    }

    public LiveData<MFPGoDownMasterResponse> getMFPGodownsResponse() {
        if (mfpGoDownMasterResponseMutableLiveData != null) {
            getMFPGoDownMasterCall();
        }
        return mfpGoDownMasterResponseMutableLiveData;
    }

    public LiveData<PUnitMasterResponse> getPUnitMasterResponse() {
        if (pUnitMasterResponseMutableLiveData != null) {
            getPUnitMasterCall();
        }
        return pUnitMasterResponseMutableLiveData;
    }

    public LiveData<PetrolPumpMasterResponse> getPetrolPumpMasterResponse() {
        if (petrolPumpMasterResponseMutableLiveData != null) {
            getPetrolPumpMasterCall();
        }
        return petrolPumpMasterResponseMutableLiveData;
    }


    public LiveData<LPGMasterResponse> getLPGMasterResponse() {
        if (lpgMasterResponseMutableLiveData != null) {
            getLPGMasterCall();
        }
        return lpgMasterResponseMutableLiveData;
    }
    private void getDRDepotMasterCall() {
        TWDService twdService = TWDService.Factory.create("gcc");
        twdService.getDRDepotMasterResponse().enqueue(new Callback<DRDepotMasterResponse>() {
            @Override
            public void onResponse(@NotNull Call<DRDepotMasterResponse> call, @NotNull Response<DRDepotMasterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    drDepotMasterResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<DRDepotMasterResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }


    private void getDRGoDownMasterCall() {
        TWDService twdService = TWDService.Factory.create("gcc");
        twdService.getDRGoDownMasterResponse().enqueue(new Callback<DRGoDownMasterResponse>() {
            @Override
            public void onResponse(@NotNull Call<DRGoDownMasterResponse> call, @NotNull Response<DRGoDownMasterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    drGoDownMasterResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<DRGoDownMasterResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    private void getMFPGoDownMasterCall() {
        TWDService twdService = TWDService.Factory.create("gcc");
        twdService.getMFPDownMasterResponse().enqueue(new Callback<MFPGoDownMasterResponse>() {
            @Override
            public void onResponse(@NotNull Call<MFPGoDownMasterResponse> call, @NotNull Response<MFPGoDownMasterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mfpGoDownMasterResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<MFPGoDownMasterResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    private void getPUnitMasterCall() {
        TWDService twdService = TWDService.Factory.create("gcc");
        twdService.getPUnitMasterResponse().enqueue(new Callback<PUnitMasterResponse>() {
            @Override
            public void onResponse(@NotNull Call<PUnitMasterResponse> call, @NotNull Response<PUnitMasterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pUnitMasterResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<PUnitMasterResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    private void getPetrolPumpMasterCall() {
        TWDService twdService = TWDService.Factory.create("gcc");
        twdService.getPetrolPumpMasterResponse().enqueue(new Callback<PetrolPumpMasterResponse>() {
            @Override
            public void onResponse(@NotNull Call<PetrolPumpMasterResponse> call, @NotNull Response<PetrolPumpMasterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    petrolPumpMasterResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<PetrolPumpMasterResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });


    }

    private void getLPGMasterCall() {
        TWDService twdService = TWDService.Factory.create("gcc");
        twdService.getLPGMasterResponse().enqueue(new Callback<LPGMasterResponse>() {
            @Override
            public void onResponse(@NotNull Call<LPGMasterResponse> call, @NotNull Response<LPGMasterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    lpgMasterResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<LPGMasterResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }
}



