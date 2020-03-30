package com.cgg.twdinspection.inspection.room.repository;

import android.app.Application;
import android.util.Log;

import com.cgg.twdinspection.inspection.room.Dao.GeneralInfoDao;
import com.cgg.twdinspection.inspection.room.database.DistrictDatabase;
import com.cgg.twdinspection.inspection.source.general_information.GeneralInfoEntity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GeneralInfoRepository {

    private GeneralInfoDao generalInfoDao;
    private long x;
    private  String tag=GeneralInfoRepository.class.getSimpleName();


    public GeneralInfoRepository(Application application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        generalInfoDao = db.generalInfoDao();
    }



    public long insertGeneralInfo(GeneralInfoEntity generalInformationEntity) {
        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                generalInfoDao.insertGeneralInfo(generalInformationEntity);
            }
        });

        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i("Tag", tag+"onSubscribe: ");
            }

            @Override
            public void onNext(Long aLong) {
                Log.i("Tag", tag+"onNext: ");
                x = aLong;
            }


            @Override
            public void onError(Throwable e) {
                Log.i("Tag", tag+"onError: " + x);
            }

            @Override
            public void onComplete() {
                Log.i("Tag", tag+"onComplete: " + x);
            }
        };

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
        return x;
    }
}
