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

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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

    public LiveData<List<UploadPhoto>> getPhotos(String inst_id) {
        uploadLiveData = repository.getPhotos(inst_id);
        return uploadLiveData;
    }

    public LiveData<UploadPhoto> getPhotoData(String fileName, String inst_id) {
        uploadPhotoLiveData = repository.getPhotoData(fileName, inst_id);
        return uploadPhotoLiveData;
    }

    public void deletePhotoData(String inst_id) {
        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                repository.deletePhotoData(inst_id);
            }
        });

        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
            }


            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
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
