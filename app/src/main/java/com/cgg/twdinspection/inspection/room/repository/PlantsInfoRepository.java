package com.cgg.twdinspection.inspection.room.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.inspection.room.Dao.PlantsInfoDao;
import com.cgg.twdinspection.inspection.room.database.DistrictDatabase;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.PlantsEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlantsInfoRepository {

    public PlantsInfoDao plantsInfoDao;
    private String tag= PlantsInfoRepository.class.getSimpleName();

    public PlantsInfoRepository(Context application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        plantsInfoDao = db.plantsInfoDao();

    }

    public LiveData<List<PlantsEntity>> getPlantsListLiveData() {
            return plantsInfoDao.getPlantsInfo();
    }
    long x;

    public long deletePlantsInfo(PlantsEntity plantsEntity) {


        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                plantsInfoDao.deletePlantInfo(plantsEntity);
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
