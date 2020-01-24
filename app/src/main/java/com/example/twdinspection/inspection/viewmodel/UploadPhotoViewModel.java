package com.example.twdinspection.inspection.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.databinding.ActivityBenDetailsActivtyBinding;
import com.example.twdinspection.databinding.ActivityUploadedPhotoBinding;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.example.twdinspection.schemes.interfaces.SchemeSubmitInterface;
import com.example.twdinspection.schemes.room.repository.InspectionRemarksRepository;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;
import com.example.twdinspection.schemes.source.remarks.InspectionRemarksEntity;
import com.example.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitRequest;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitResponse;

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
        }catch (Exception e){

        }

    }

    public void UploadImageServiceCall(final MultipartBody.Part body,final MultipartBody.Part body1,final MultipartBody.Part body2,
                                       final MultipartBody.Part body3,final MultipartBody.Part body4,final MultipartBody.Part body5,
                                       final MultipartBody.Part body6,final MultipartBody.Part body7,final MultipartBody.Part body8) {
        uploadedPhotoBinding.progress.setVisibility(View.VISIBLE);
        TWDService twdService = TWDService.Factory.create("school");
        twdService.uploadSchemeImageCall(body,body1,body2,body3,body4,body5,body6,body7,body8).enqueue(new Callback<SchemePhotoSubmitResponse>() {
            @Override
            public void onResponse(@NotNull Call<SchemePhotoSubmitResponse> call, @NotNull Response<SchemePhotoSubmitResponse> response) {
                uploadedPhotoBinding.progress.setVisibility(View.GONE);
                schemeSubmitInterface.getPhotoData(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<SchemePhotoSubmitResponse> call, @NotNull Throwable t) {
                uploadedPhotoBinding.progress.setVisibility(View.GONE);
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}
