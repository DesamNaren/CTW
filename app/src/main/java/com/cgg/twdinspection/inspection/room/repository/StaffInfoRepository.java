package com.cgg.twdinspection.inspection.room.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.inspection.room.Dao.StaffInfoDao;
import com.cgg.twdinspection.inspection.room.database.SchoolDatabase;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.source.staff_attendance.StaffAttendanceEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StaffInfoRepository {

    private final StaffInfoDao staffInfoDao;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public StaffInfoRepository(Application application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        staffInfoDao = db.staffInfoDao();
    }


    public LiveData<MasterInstituteInfo> getMasterStaffIdsList(String inst_id) {
        return staffInfoDao.getMasterStaffIdList(inst_id);
    }

    public LiveData<List<StaffAttendanceEntity>> getStaffInfoList(String inst_id) {
        return staffInfoDao.getStaffInfoList(inst_id);
    }

    public void insertStaffInfo(List<StaffAttendanceEntity> staffAttendanceEntities) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                staffInfoDao.deleteStaffInfo();
                staffInfoDao.insertStaffAttendInfo(staffAttendanceEntities);
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

    long x;

    public long updateStaffInfo(List<StaffAttendanceEntity> staffAttendanceEntities) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                staffInfoDao.updateStaffInfo(staffAttendanceEntities);
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
