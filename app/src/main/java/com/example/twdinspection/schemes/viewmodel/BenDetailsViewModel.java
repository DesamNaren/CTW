package com.example.twdinspection.schemes.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.schemes.room.repository.InspectionRemarksRepository;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;
import com.example.twdinspection.schemes.source.remarks.InspectionRemarksEntity;
import com.example.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitRequest;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BenDetailsViewModel extends AndroidViewModel {
    public BeneficiaryDetail beneficiaryDetail;
    private LiveData<List<InspectionRemarksEntity>> inspectionRemarks = new MutableLiveData<>();
    private MutableLiveData<SchemeSubmitResponse> schemeSubmitResponseMutableLiveData;
    private MutableLiveData<SchemePhotoSubmitResponse> schemePhotoSubmitResponseMutableLiveData;
    private InspectionRemarksRepository remarksRepository;

    public BenDetailsViewModel(Application application, BeneficiaryDetail beneficiaryDetail) {
        super(application);
        this.beneficiaryDetail = beneficiaryDetail;
        schemeSubmitResponseMutableLiveData = new MutableLiveData<>();
        schemePhotoSubmitResponseMutableLiveData = new MutableLiveData<>();
        remarksRepository = new InspectionRemarksRepository(application);
    }

    public LiveData<List<InspectionRemarksEntity>> getRemarks() {
        if (inspectionRemarks != null) {
            inspectionRemarks = remarksRepository.getInspRemarks();
        }
        return inspectionRemarks;
    }

    public LiveData<String> getRemarkId(String remType) {
        return remarksRepository.getRemarkId(remType);
    }

    public LiveData<SchemeSubmitResponse> getSchemeSubmitResponse(SchemeSubmitRequest schemeSubmitRequest) {
        if(schemeSubmitResponseMutableLiveData!=null){
            submitSchemeDetails(schemeSubmitRequest);
        }
        return schemeSubmitResponseMutableLiveData;
    }

    public LiveData<SchemePhotoSubmitResponse> getSchemePhotoSubmitResponse(final  MultipartBody.Part body) {
        if(schemePhotoSubmitResponseMutableLiveData!=null){
            UploadImageServiceCall(body);
        }
        return schemePhotoSubmitResponseMutableLiveData;
    }

    private void submitSchemeDetails(SchemeSubmitRequest schemeSubmitRequest) {
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getSchemeSubmitResponse(schemeSubmitRequest).enqueue(new Callback<SchemeSubmitResponse>() {
            @Override
            public void onResponse(@NotNull Call<SchemeSubmitResponse> call, @NotNull Response<SchemeSubmitResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatusCode() != null && response.body().getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
                        schemeSubmitResponseMutableLiveData.setValue(response.body());

                    } else if (response.body().getStatusCode() != null && response.body().getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {

                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(@NotNull Call<SchemeSubmitResponse> call, @NotNull Throwable t) {
                Log.i("UU", "onFailure: " + t.getMessage());
            }
        });
    }

    public void UploadImageServiceCall(final  MultipartBody.Part body) {
        TWDService twdService = TWDService.Factory.create("school");
        twdService.uploadSchemeImageCall(body).enqueue(new Callback<SchemePhotoSubmitResponse>() {
            @Override
            public void onResponse(@NotNull Call<SchemePhotoSubmitResponse> call, @NotNull Response<SchemePhotoSubmitResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatusCode() != null && response.body().getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
                        schemePhotoSubmitResponseMutableLiveData.setValue(response.body());

                    } else if (response.body().getStatusCode() != null && response.body().getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {

                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(@NotNull Call<SchemePhotoSubmitResponse> call, @NotNull Throwable t) {
                Log.i("UU", "onFailure: " + t.getMessage());
            }
        });
    }

}
