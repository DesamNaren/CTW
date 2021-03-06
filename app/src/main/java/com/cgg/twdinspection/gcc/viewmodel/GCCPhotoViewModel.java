package com.cgg.twdinspection.gcc.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.gcc.interfaces.GCCSubmitInterface;
import com.cgg.twdinspection.gcc.source.submit.GCCPhotoSubmitResponse;
import com.cgg.twdinspection.gcc.source.submit.GCCSubmitRequest;
import com.cgg.twdinspection.gcc.source.submit.GCCSubmitResponse;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GCCPhotoViewModel extends ViewModel {
    private final Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    private GCCSubmitInterface gccSubmitInterface;

    GCCPhotoViewModel(Context context) {
        this.context = context;
        try {
            errorHandlerInterface = (ErrorHandlerInterface) context;
            gccSubmitInterface = (GCCSubmitInterface) context;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void UploadImageServiceCall(final List<MultipartBody.Part> partList) {
        try {
            TWDService twdService = TWDService.Factory.create("school");
            twdService.uploadGCCImageCall(partList).enqueue(new Callback<GCCPhotoSubmitResponse>() {
                @Override
                public void onResponse(@NotNull Call<GCCPhotoSubmitResponse> call, @NotNull Response<GCCPhotoSubmitResponse> response) {
                    gccSubmitInterface.getPhotoData(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<GCCPhotoSubmitResponse> call, @NotNull Throwable t) {
                    errorHandlerInterface.handleError(t, context);
                }
            });
        } catch (Exception e) {
            errorHandlerInterface.handleError(e, context);
            e.printStackTrace();
        }
    }

    public void submitGCCDetails(GCCSubmitRequest gccSubmitRequest) {
        Gson gson = new Gson();
        String request = gson.toJson(gccSubmitRequest);
        Log.i("Request", request);
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getGCCSubmitResponse(gccSubmitRequest).enqueue(new Callback<GCCSubmitResponse>() {
            @Override
            public void onResponse(@NotNull Call<GCCSubmitResponse> call, @NotNull Response<GCCSubmitResponse> response) {
                gccSubmitInterface.getData(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<GCCSubmitResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}

