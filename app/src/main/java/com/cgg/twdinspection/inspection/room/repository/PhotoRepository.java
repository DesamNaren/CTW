package com.cgg.twdinspection.inspection.room.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.inspection.room.Dao.PhotoDao;
import com.cgg.twdinspection.inspection.room.database.SchoolDatabase;
import com.cgg.twdinspection.inspection.source.upload_photo.UploadPhoto;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PhotoRepository {

    private final PhotoDao photoDao;
    public LiveData<List<UploadPhoto>> photos = new MutableLiveData<>();


    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public PhotoRepository(Context application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        photoDao = db.photoDao();

    }

    // Room executes all queries on file_provider_paths separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<UploadPhoto>> getPhotos() {
        return photoDao.getPhotos();
    }

    public LiveData<UploadPhoto> getPhotoData(String name) {
        return photoDao.getPhotoData(name);
    }

    long x;

    public long insertPhotos(List<UploadPhoto> uploadPhotos) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                photoDao.insertPhotos(uploadPhotos);
            }
        });

        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
            }

            @Override
            public void onNext(@NotNull Long aLong) {
                x = aLong;
            }

            @Override
            public void onError(@NotNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
        return x;
    }
}
