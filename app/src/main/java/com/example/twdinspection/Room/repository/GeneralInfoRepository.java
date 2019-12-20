package com.example.twdinspection.Room.repository;

import android.app.Application;
import android.util.Log;

import com.example.twdinspection.Room.Dao.GeneralInfoDao;
import com.example.twdinspection.Room.database.DistrictDatabase;
import com.example.twdinspection.source.GeneralInformation.GeneralInformationEntity;

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


    public GeneralInfoRepository(Application application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        generalInfoDao = db.generalInfoDao();
    }


    public long updateClassInfo(String attendence_marked, String count_reg, String count_during_insp,
                                String variance, int flag_completed, String inst_id, int class_id) {
//
//
//        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
//            @Override
//            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
//                classInfoDao.updateClassInfo(attendence_marked,count_reg,count_during_insp,variance,inst_id,inst_id,class_id);
//            }
//        });
//
//        Observer<Long> observer = new Observer<Long>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.i("Tag", "onSubscribe: ");
//            }
//
//            @Override
//            public void onNext(Long aLong) {
//                x = aLong;
////                flag = true;
//                Log.i("Tag", "onNext: " + x);
//            }
//
//
//            @Override
//            public void onError(Throwable e) {
////                flag = false;
//                Log.i("Tag", "onError: " + x);
//            }
//
//            @Override
//            public void onComplete() {
////                flag = true;
//                Log.i("Tag", "onComplete: " + x);
//            }
//        };
//
//        observable.observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(observer);
        return x;
    }

    public long insertGeneralInfo(GeneralInformationEntity generalInformationEntity) {
        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                generalInfoDao.insertGeneralInfo(generalInformationEntity);
            }
        });

        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i("Tag", "onSubscribe: ");
            }

            @Override
            public void onNext(Long aLong) {
                x = aLong;
            }


            @Override
            public void onError(Throwable e) {
                Log.i("Tag", "onError: " + x);
            }

            @Override
            public void onComplete() {
                Log.i("Tag", "onComplete: " + x);
            }
        };

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
        return x;
    }
}
