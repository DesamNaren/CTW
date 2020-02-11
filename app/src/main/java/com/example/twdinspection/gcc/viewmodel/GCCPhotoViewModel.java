package com.example.twdinspection.gcc.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.gcc.interfaces.GCCSubmitInterface;
import com.example.twdinspection.gcc.source.submit.GCCPhotoSubmitResponse;
import com.example.twdinspection.gcc.source.submit.GCCSubmitRequest;
import com.example.twdinspection.gcc.source.submit.GCCSubmitResponse;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.example.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitRequest;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GCCPhotoViewModel extends ViewModel {
    private Context context;
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
            TWDService twdService = TWDService.Factory.create("gcc");
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
        TWDService twdService = TWDService.Factory.create("gcc");
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

