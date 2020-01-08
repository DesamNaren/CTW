package com.example.twdinspection.inspection.Room.repository;

import android.app.Application;
import android.util.Log;

import com.example.twdinspection.inspection.Room.Dao.GenCommentsInfoDao;
import com.example.twdinspection.inspection.Room.Dao.RegistersInfoDao;
import com.example.twdinspection.inspection.Room.database.DistrictDatabase;
import com.example.twdinspection.inspection.source.GeneralComments.GeneralCommentsEntity;
import com.example.twdinspection.inspection.source.RegistersUptoDate.RegistersEntity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GenCommentsRepository {

    public GenCommentsInfoDao genCommentsInfoDao;
    private String tag= GenCommentsRepository.class.getSimpleName();
    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public GenCommentsRepository(Application application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        genCommentsInfoDao = db.genCommentsInfoDao();

    }


    long x;

    public long insertGeneralCommentsInfo(GeneralCommentsEntity generalCommentsEntity) {


        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                genCommentsInfoDao.insertGenCommentsInfo(generalCommentsEntity);
            }
        });

        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i("Tag", tag+"onSubscribe: ");
            }

            @Override
            public void onNext(Long aLong) {
                x = aLong;
//                flag = true;
                Log.i("Tag", tag+"onNext: " + x);
            }


            @Override
            public void onError(Throwable e) {
//                flag = false;
                Log.i("Tag", tag+"onError: " + x);
            }

            @Override
            public void onComplete() {
//                flag = true;
                Log.i("Tag", tag+"onComplete: " + x);
            }
        };

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
        return x;
    }
}
