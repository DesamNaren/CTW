package com.example.twdinspection.schemes.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.databinding.ActivityBenDetailsActivtyBinding;
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
        benDetailsActivtyBinding.progress.setVisibility(View.VISIBLE);
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getSchemeSubmitResponse(schemeSubmitRequest).enqueue(new Callback<SchemeSubmitResponse>() {
            @Override
            public void onResponse(@NotNull Call<SchemeSubmitResponse> call, @NotNull Response<SchemeSubmitResponse> response) {
                benDetailsActivtyBinding.progress.setVisibility(View.GONE);
                schemeSubmitInterface.getData(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<SchemeSubmitResponse> call, @NotNull Throwable t) {
                benDetailsActivtyBinding.progress.setVisibility(View.GONE);
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public void UploadImageServiceCall(final MultipartBody.Part body) {
        benDetailsActivtyBinding.progress.setVisibility(View.VISIBLE);
        TWDService twdService = TWDService.Factory.create("school");
        twdService.uploadSchemeImageCall(body).enqueue(new Callback<SchemePhotoSubmitResponse>() {
            @Override
            public void onResponse(@NotNull Call<SchemePhotoSubmitResponse> call, @NotNull Response<SchemePhotoSubmitResponse> response) {
                benDetailsActivtyBinding.progress.setVisibility(View.GONE);
                schemeSubmitInterface.getPhotoData(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<SchemePhotoSubmitResponse> call, @NotNull Throwable t) {
                benDetailsActivtyBinding.progress.setVisibility(View.GONE);
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}
