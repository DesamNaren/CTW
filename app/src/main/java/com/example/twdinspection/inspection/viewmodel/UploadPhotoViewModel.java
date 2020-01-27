package com.example.twdinspection.inspection.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.databinding.ActivityUploadedPhotoBinding;
import com.example.twdinspection.inspection.utils.CustomProgressDialog;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.example.twdinspection.schemes.interfaces.SchemeSubmitInterface;
import com.example.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;

import org.jetbrains.annotations.NotNull;

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
    CustomProgressDialog customProgressDialog;


    UploadPhotoViewModel(ActivityUploadedPhotoBinding uploadedPhotoBinding,
                         Context context) {

        this.context = context;
        this.uploadedPhotoBinding = uploadedPhotoBinding;
        customProgressDialog = new CustomProgressDialog(context);
        schemePhotoSubmitResponseMutableLiveData = new MutableLiveData<>();
        try {
            errorHandlerInterface = (ErrorHandlerInterface) context;
            schemeSubmitInterface = (SchemeSubmitInterface) context;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void UploadImageServiceCall(final MultipartBody.Part body, final MultipartBody.Part body1, final MultipartBody.Part body2,
                                       final MultipartBody.Part body3, final MultipartBody.Part body4, final MultipartBody.Part body5,
                                       final MultipartBody.Part body6, final MultipartBody.Part body7, final MultipartBody.Part body8) {
        customProgressDialog.show();

        TWDService twdService = TWDService.Factory.create("school");
        twdService.uploadSchemeImageCall(body, body1, body2, body3, body4, body5, body6, body7, body8).enqueue(new Callback<SchemePhotoSubmitResponse>() {
            @Override
            public void onResponse(@NotNull Call<SchemePhotoSubmitResponse> call, @NotNull Response<SchemePhotoSubmitResponse> response) {
                customProgressDialog.dismiss();
                schemeSubmitInterface.getPhotoData(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<SchemePhotoSubmitResponse> call, @NotNull Throwable t) {
                customProgressDialog.dismiss();
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}
