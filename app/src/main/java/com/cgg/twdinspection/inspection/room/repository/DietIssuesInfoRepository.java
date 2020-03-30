package com.cgg.twdinspection.inspection.room.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.inspection.room.Dao.DietIssuesInfoDao;
import com.cgg.twdinspection.inspection.room.database.DistrictDatabase;
import com.cgg.twdinspection.inspection.source.diet_issues.DietIssuesEntity;
import com.cgg.twdinspection.inspection.source.diet_issues.DietListEntity;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DietIssuesInfoRepository {

    public DietIssuesInfoDao dietIssuesInfoDao;
    private String tag = DietIssuesInfoRepository.class.getSimpleName();

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public DietIssuesInfoRepository(Application application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        dietIssuesInfoDao = db.dietIssuesInfoDao();

    }

    public LiveData<MasterInstituteInfo> getMasterDietList(String inst_id) {
        return dietIssuesInfoDao.getMasterDietList(inst_id);
    }

    public  LiveData<List<DietListEntity>> getDietList(String inst_id) {
        return dietIssuesInfoDao.getDietList(inst_id);
    }


    long x;

    public long updateDietIssuesInfo(DietIssuesEntity dietIssuesEntity) {


        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                dietIssuesInfoDao.updateDietIssuesInfo(dietIssuesEntity);
            }
        });

        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i("Tag", tag + "onSubscribe: ");
            }

            @Override
            public void onNext(Long aLong) {
                x = aLong;
//                flag = true;
                Log.i("Tag", tag + "onNext: " + x);
            }


            @Override
            public void onError(Throwable e) {
//                flag = false;
                Log.i("Tag", tag + "onError: " + x);
            }

            @Override
            public void onComplete() {
//                flag = true;
                Log.i("Tag", tag + "onComplete: " + x);
            }
        };

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
        return x;
    }

    public void insertDietInfo(List<DietListEntity> dietListEntities) {

        Observable.fromCallable(new Callable<List<DietListEntity>>() {
            @Override
            public List<DietListEntity> call() throws Exception {
                dietIssuesInfoDao.deleteDietInfo();
                dietIssuesInfoDao.insertDietInfo(dietListEntities);
                return null;
            }
        });

        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                dietIssuesInfoDao.deleteDietInfo();
                dietIssuesInfoDao.insertDietInfo(dietListEntities);
            }
        });

        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Long aLong) {
                x = aLong;
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

    public long deleteDietListInfo() {

        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                dietIssuesInfoDao.deleteDietListInfo();
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