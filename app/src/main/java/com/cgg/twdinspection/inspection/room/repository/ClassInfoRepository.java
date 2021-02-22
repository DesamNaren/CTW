package com.cgg.twdinspection.inspection.room.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.inspection.room.Dao.ClassInfoDao;
import com.cgg.twdinspection.inspection.room.database.SchoolDatabase;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.source.student_attendence_info.StudAttendInfoEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClassInfoRepository {

    private final ClassInfoDao classInfoDao;
    private long x = -1;

    public ClassInfoRepository(Context application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        classInfoDao = db.classInfoDao();
    }

    public LiveData<MasterInstituteInfo> getMasterClassIdsList(String inst_id) {
        return classInfoDao.getMasterClassIdList(inst_id);
    }

    public LiveData<List<StudAttendInfoEntity>> getClassIdsList(String inst_id) {
        return classInfoDao.getClassIdList(inst_id);
    }

    public long updateClassInfo(StudAttendInfoEntity studAttendInfoEntity) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                classInfoDao.updateClassInfo(studAttendInfoEntity);
            }
        });

        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
            }

            @Override
            public void onNext(@NotNull Long aLong) {
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

    public void insertClassInfo(List<StudAttendInfoEntity> studAttendInfoEntityList) {

        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                classInfoDao.deleteClassInfo(studAttendInfoEntityList.get(0).getInstitute_id());
                classInfoDao.insertStudAttendInfo(studAttendInfoEntityList);
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
    }
}
