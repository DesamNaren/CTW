package com.example.twdinspection.inspection.Room.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.twdinspection.inspection.Room.Dao.ClassInfoDao;
import com.example.twdinspection.inspection.Room.database.DistrictDatabase;
import com.example.twdinspection.inspection.source.inst_master.MasterClassInfo;
import com.example.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClassInfoRepository {

    public ClassInfoDao classInfoDao;
    public String tag = ClassInfoRepository.class.getSimpleName();

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ClassInfoRepository(Application application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        classInfoDao = db.classInfoDao();

    }

    public LiveData<MasterInstituteInfo> getMasterClassIdsList(String inst_id) {
        LiveData<MasterInstituteInfo> classIdList = classInfoDao.getMasterClassIdList(inst_id);
        return classIdList;
    }

    public LiveData<List<StudAttendInfoEntity>> getClassIdsList(String inst_id) {
        LiveData<List<StudAttendInfoEntity>> classIdList = classInfoDao.getClassIdList(inst_id);
        return classIdList;
    }
    long x;

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

    public long insertClassInfo(List<StudAttendInfoEntity> studAttendInfoEntityList) {


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
}
