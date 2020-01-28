package com.example.twdinspection.inspection.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.databinding.ActivityUploadedPhotoBinding;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.example.twdinspection.schemes.interfaces.SchemeSubmitInterface;
import com.example.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPhotoViewModel extends ViewModel {
    private MutableLiveData<SchemePhotoSubmitResponse> schemePhotoSubmitResponseMutableLiveData;
    private Context context;
    private ActivityUploadedPhotoBinding uploadedPhotoBinding;
    private ErrorHandlerInterface errorHandlerInterface;
    private SchemeSubmitInterface schemeSubmitInterface;


    UploadPhotoViewModel(ActivityUploadedPhotoBinding uploadedPhotoBinding,
                         Context context) {

        this.context = context;
        this.uploadedPhotoBinding = uploadedPhotoBinding;
        schemePhotoSubmitResponseMutableLiveData = new MutableLiveData<>();
        try {
            errorHandlerInterface = (ErrorHandlerInterface) context;
            schemeSubmitInterface = (SchemeSubmitInterface) context;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void UploadImageServiceCall(final List<MultipartBody.Part> partList) {
        try {
            TWDService twdService = TWDService.Factory.create("school");
            twdService.uploadSchoolImageCall(partList).enqueue(new Callback<SchemePhotoSubmitResponse>() {
                @Override
                public void onResponse(@NotNull Call<SchemePhotoSubmitResponse> call, @NotNull Response<SchemePhotoSubmitResponse> response) {
                    schemeSubmitInterface.getPhotoData(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<SchemePhotoSubmitResponse> call, @NotNull Throwable t) {
                    errorHandlerInterface.handleError(t, context);
                }
            });
        } catch (Exception e) {
            errorHandlerInterface.handleError(e, context);
            e.printStackTrace();
        }
    }

}
