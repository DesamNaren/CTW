package com.cgg.twdinspection.inspection.room.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.inspection.room.Dao.ClassInfoDao;
import com.cgg.twdinspection.inspection.room.database.DistrictDatabase;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.source.student_attendence_info.StudAttendInfoEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClassInfoRepository {

    private ClassInfoDao classInfoDao;
    public String tag = ClassInfoRepository.class.getSimpleName();

    public ClassInfoRepository(Context application) {
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
