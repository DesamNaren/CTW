package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.inspection.source.reports.InspReportResponse;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InspectionReportsViewModel extends AndroidViewModel {
    private MutableLiveData<InspReportResponse> gccReportResponseMutableLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;

    public InspectionReportsViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        gccReportResponseMutableLiveData = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }

    public LiveData<InspReportResponse> getInspectionReports(String username) {
        if (gccReportResponseMutableLiveData != null) {
            callGCCReports(username);
        }
        return gccReportResponseMutableLiveData;
    }

    private void callGCCReports(String username) {
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getInspectionReports(username).enqueue(new Callback<InspReportResponse>() {
            @Override
            public void onResponse(@NotNull Call<InspReportResponse> call, @NotNull Response<InspReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    gccReportResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<InspReportResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}

