package com.cgg.twdinspection.schemes.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.databinding.ActivityBenDetailsActivtyBinding;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.cgg.twdinspection.schemes.interfaces.SchemeSubmitInterface;
import com.cgg.twdinspection.schemes.room.repository.InspectionRemarksRepository;
import com.cgg.twdinspection.schemes.source.bendetails.BeneficiaryDetail;
import com.cgg.twdinspection.schemes.source.remarks.InspectionRemarksEntity;
import com.cgg.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;
import com.cgg.twdinspection.schemes.source.submit.SchemeSubmitRequest;
import com.cgg.twdinspection.schemes.source.submit.SchemeSubmitResponse;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BenDetailsViewModel extends ViewModel {
    public BeneficiaryDetail beneficiaryDetail;
    private LiveData<List<InspectionRemarksEntity>> inspectionRemarks = new MutableLiveData<>();
    private MutableLiveData<SchemeSubmitResponse> schemeSubmitResponseMutableLiveData;
    private MutableLiveData<SchemePhotoSubmitResponse> schemePhotoSubmitResponseMutableLiveData;
    private InspectionRemarksRepository remarksRepository;
    private Context context;
    private ActivityBenDetailsActivtyBinding benDetailsActivtyBinding;
    private ErrorHandlerInterface errorHandlerInterface;
    private SchemeSubmitInterface schemeSubmitInterface;

    public BenDetailsViewModel(
            Context context) {

        this.context = context;

        remarksRepository = new InspectionRemarksRepository(context);

    }

    BenDetailsViewModel(BeneficiaryDetail beneficiaryDetail,
                        ActivityBenDetailsActivtyBinding benDetailsActivtyBinding,
                        Context context) {

        this.context = context;
        this.beneficiaryDetail = beneficiaryDetail;
        this.benDetailsActivtyBinding = benDetailsActivtyBinding;

        schemeSubmitResponseMutableLiveData = new MutableLiveData<>();
        schemePhotoSubmitResponseMutableLiveData = new MutableLiveData<>();
        remarksRepository = new InspectionRemarksRepository(context);
        errorHandlerInterface = (ErrorHandlerInterface) context;
        schemeSubmitInterface = (SchemeSubmitInterface) context;
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

    public void submitSchemeDetails(SchemeSubmitRequest schemeSubmitRequest) {
        Gson gson = new Gson();
        String req = gson.toJson(schemeSubmitRequest);
        Log.i("schemesRequest", req);
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getSchemeSubmitResponse(schemeSubmitRequest).enqueue(new Callback<SchemeSubmitResponse>() {
            @Override
            public void onResponse(@NotNull Call<SchemeSubmitResponse> call, @NotNull Response<SchemeSubmitResponse> response) {
                schemeSubmitInterface.getData(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<SchemeSubmitResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public void UploadImageServiceCall(List<MultipartBody.Part> partList) {
        TWDService twdService = TWDService.Factory.create("school");
        twdService.uploadSchemeImageCall(partList).enqueue(new Callback<SchemePhotoSubmitResponse>() {
            @Override
            public void onResponse(@NotNull Call<SchemePhotoSubmitResponse> call, @NotNull Response<SchemePhotoSubmitResponse> response) {
                schemeSubmitInterface.getPhotoData(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<SchemePhotoSubmitResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}
