package com.example.twdinspection.inspection.Room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.twdinspection.inspection.Room.Dao.ClassInfoDao;
import com.example.twdinspection.inspection.Room.database.DistrictDatabase;
import com.example.twdinspection.inspection.interfaces.DMVInterface;
import com.example.twdinspection.inspection.source.inst_master.MasterClassInfo;
import com.example.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class ClassInfoRepository {

    private ClassInfoDao classInfoDao;
    public String tag = ClassInfoRepository.class.getSimpleName();

    public ClassInfoRepository(Application application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        classInfoDao = db.classInfoDao();
    }

    public LiveData<MasterInstituteInfo> getMasterClassIdsList(String inst_id) {
        return classInfoDao.getMasterClassIdList(inst_id);
    }

    public  LiveData<List<StudAttendInfoEntity>> getClassIdsList(String inst_id) {
        return classInfoDao.getClassIdList(inst_id);
    }

    long x = -1;

    public long updateClassInfo(StudAttendInfoEntity studAttendInfoEntity) {
        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                classInfoDao.updateClassInfo(studAttendInfoEntity);
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
                Log.i("Tag", tag + "onNext: " + aLong);
            }


            @Override
            public void onError(Throwable e) {
                Log.i("Tag", tag + "onError: " + e.getLocalizedMessage());
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


    public void insertClassInfo(List<StudAttendInfoEntity> studAttendInfoEntityList) {

        Observable.fromCallable(new Callable<List<StudAttendInfoEntity>>() {
            @Override
            public List<StudAttendInfoEntity> call() throws Exception {
                classInfoDao.deleteClassInfo();
                classInfoDao.insertStudAttendInfo(studAttendInfoEntityList);
                return null;
            }
        });

        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                classInfoDao.deleteClassInfo();
                classInfoDao.insertStudAttendInfo(studAttendInfoEntityList);
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



}
