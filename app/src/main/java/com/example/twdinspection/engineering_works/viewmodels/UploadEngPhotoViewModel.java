package com.example.twdinspection.engineering_works.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.engineering_works.interfaces.UploadEngPhotosSubmitInterface;
import com.example.twdinspection.engineering_works.source.SubmitEngWorksRequest;
import com.example.twdinspection.gcc.source.submit.GCCPhotoSubmitResponse;
import com.example.twdinspection.gcc.source.submit.GCCSubmitRequest;
import com.example.twdinspection.gcc.source.submit.GCCSubmitResponse;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadEngPhotoViewModel extends ViewModel {
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    private UploadEngPhotosSubmitInterface engPhotosSubmitInterface;

    UploadEngPhotoViewModel(Context context) {
        this.context = context;
        try {
            errorHandlerInterface = (ErrorHandlerInterface) context;
            engPhotosSubmitInterface = (UploadEngPhotosSubmitInterface) context;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void submitEngWorksDetails(SubmitEngWorksRequest gccSubmitRequest) {
        Gson gson=new Gson();
        String request=gson.toJson(gccSubmitRequest);
        Log.i("Request",request);
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getEngWorksSubmitResponse(gccSubmitRequest).enqueue(new Callback<GCCSubmitResponse>() {
            @Override
            public void onResponse(@NotNull Call<GCCSubmitResponse> call, @NotNull Response<GCCSubmitResponse> response) {
                engPhotosSubmitInterface.getData(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<GCCSubmitResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public void UploadImageServiceCall(final List<MultipartBody.Part> partList) {
        try {
            TWDService twdService = TWDService.Factory.create("school");
            twdService.uploadEngPhotoCall(partList).enqueue(new Callback<GCCPhotoSubmitResponse>() {
                @Override
                public void onResponse(@NotNull Call<GCCPhotoSubmitResponse> call, @NotNull Response<GCCPhotoSubmitResponse> response) {
                    engPhotosSubmitInterface.getPhotoData(response.body());
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
        Gson gson=new Gson();
        String request=gson.toJson(gccSubmitRequest);
        Log.i("Request",request);
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getGCCSubmitResponse(gccSubmitRequest).enqueue(new Callback<GCCSubmitResponse>() {
            @Override
            public void onResponse(@NotNull Call<GCCSubmitResponse> call, @NotNull Response<GCCSubmitResponse> response) {
                engPhotosSubmitInterface.getData(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<GCCSubmitResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}

