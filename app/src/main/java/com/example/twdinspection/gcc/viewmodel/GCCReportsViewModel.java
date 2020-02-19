package com.example.twdinspection.gcc.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.gcc.reports.source.GCCReportResponse;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GCCReportsViewModel extends AndroidViewModel {
    private MutableLiveData<GCCReportResponse> gccReportResponseMutableLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;

    public GCCReportsViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        gccReportResponseMutableLiveData = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }

    public LiveData<GCCReportResponse> getGCCReports(String username) {
        if (gccReportResponseMutableLiveData != null) {
            callGCCReports(username);
        }
        return gccReportResponseMutableLiveData;
    }

    private void callGCCReports(String username) {
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getGCCReports(username).enqueue(new Callback<GCCReportResponse>() {
            @Override
            public void onResponse(@NotNull Call<GCCReportResponse> call, @NotNull Response<GCCReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    gccReportResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<GCCReportResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}

