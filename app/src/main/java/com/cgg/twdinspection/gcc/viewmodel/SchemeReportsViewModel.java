package com.cgg.twdinspection.gcc.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.cgg.twdinspection.schemes.reports.source.SchemeReportResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchemeReportsViewModel extends AndroidViewModel {
    private final MutableLiveData<SchemeReportResponse> schemeReportResponseMutableLiveData;
    private final Context context;
    private final ErrorHandlerInterface errorHandlerInterface;

    public SchemeReportsViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        schemeReportResponseMutableLiveData = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;
    }

    public LiveData<SchemeReportResponse> getSchemeReports(String username) {
        if (schemeReportResponseMutableLiveData != null) {
            callSchemeReports(username);
        }
        return schemeReportResponseMutableLiveData;
    }

    private void callSchemeReports(String username) {
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getSchemeReports(username).enqueue(new Callback<SchemeReportResponse>() {
            @Override
            public void onResponse(@NotNull Call<SchemeReportResponse> call, @NotNull Response<SchemeReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    schemeReportResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SchemeReportResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}

