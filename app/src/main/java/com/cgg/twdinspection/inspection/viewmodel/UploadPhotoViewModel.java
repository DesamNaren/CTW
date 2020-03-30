package com.cgg.twdinspection.inspection.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.inspection.room.repository.PhotoRepository;
import com.cgg.twdinspection.inspection.source.submit.InstSubmitRequest;
import com.cgg.twdinspection.inspection.source.upload_photo.UploadPhoto;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.cgg.twdinspection.schemes.interfaces.SchemeSubmitInterface;
import com.cgg.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPhotoViewModel extends ViewModel {
    private LiveData<List<UploadPhoto>> uploadLiveData;
    private LiveData<UploadPhoto> uploadPhotoLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    private SchemeSubmitInterface schemeSubmitInterface;
    private PhotoRepository repository;


    public UploadPhotoViewModel(Context context) {

        this.context = context;
        repository = new PhotoRepository(context);
        uploadLiveData = new MutableLiveData<>();
        uploadPhotoLiveData = new MutableLiveData<>();
        try {
            errorHandlerInterface = (ErrorHandlerInterface) context;
            schemeSubmitInterface = (SchemeSubmitInterface) context;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public long insertPhotos(List<UploadPhoto> photos) {
        return repository.insertPhotos(photos);
    }

    public LiveData<List<UploadPhoto>> getPhotos() {
        uploadLiveData = repository.getPhotos();
        return uploadLiveData;
    }

    public LiveData<UploadPhoto> getPhotoData(String fileName) {
        uploadPhotoLiveData = repository.getPhotoData(fileName);
        return uploadPhotoLiveData;
    }

    public void UploadImageServiceCall(final List<MultipartBody.Part> partList, InstSubmitRequest instSubmitRequest) {
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
