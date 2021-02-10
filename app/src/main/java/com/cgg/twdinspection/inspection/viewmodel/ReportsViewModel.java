package com.cgg.twdinspection.inspection.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.inspection.reports.source.ReportCountsResponse;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportsViewModel extends ViewModel {

    private MutableLiveData<ReportCountsResponse> responseMutableLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    CustomProgressDialog customProgressDialog;

    ReportsViewModel(Context context) {
        this.context = context;
        customProgressDialog = new CustomProgressDialog(context);


        errorHandlerInterface = (ErrorHandlerInterface) context;
    }

    public LiveData<ReportCountsResponse> getReportCounts(String username) {
        responseMutableLiveData = new MutableLiveData<>();
        callReportCounts(username);
        return responseMutableLiveData;
    }

    private void callReportCounts(String username) {

        customProgressDialog.show();
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getReportCounts(username).enqueue(new Callback<ReportCountsResponse>() {
            @Override
            public void onResponse(@NotNull Call<ReportCountsResponse> call, @NotNull Response<ReportCountsResponse> response) {
                customProgressDialog.dismiss();
                responseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<ReportCountsResponse> call, @NotNull Throwable t) {
                customProgressDialog.dismiss();
                errorHandlerInterface.handleError(t, context);
            }
        });
    }
}
