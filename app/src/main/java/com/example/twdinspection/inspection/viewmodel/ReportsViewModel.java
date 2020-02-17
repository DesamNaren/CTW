package com.example.twdinspection.inspection.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.common.utils.CustomProgressDialog;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityLoginCreBinding;
import com.example.twdinspection.inspection.source.EmployeeResponse;
import com.example.twdinspection.inspection.source.reports.ReportCountsResponse;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;

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
//        username.setValue("maadhavisriram");
//        password.setValue("guest");

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
