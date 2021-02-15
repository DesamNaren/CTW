package com.cgg.twdinspection.engineering_works.reports.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.engineering_works.reports.source.WorkReportResponse;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EngReportsViewModel extends AndroidViewModel {
    private final MutableLiveData<WorkReportResponse> WorkReportResponseMutableLiveData;
    private final Context context;
    private final ErrorHandlerInterface errorHandlerInterface;

    public EngReportsViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        WorkReportResponseMutableLiveData = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }

    public LiveData<WorkReportResponse> getEngReports(String username) {
        if (WorkReportResponseMutableLiveData != null) {
            callEngReports(username);
        }
        return WorkReportResponseMutableLiveData;
    }

    private void callEngReports(String username) {
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getEngReports(username).enqueue(new Callback<WorkReportResponse>() {
            @Override
            public void onResponse(@NotNull Call<WorkReportResponse> call, @NotNull Response<WorkReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WorkReportResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<WorkReportResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}

