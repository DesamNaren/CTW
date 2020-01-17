package com.example.twdinspection.inspection.Room.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.twdinspection.inspection.Room.Dao.StaffInfoDao;
import com.example.twdinspection.inspection.Room.database.DistrictDatabase;
import com.example.twdinspection.inspection.source.GeneralInformation.GeneralInfoEntity;
import com.example.twdinspection.inspection.source.staffAttendance.StaffAttendanceEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StaffInfoRepository {

    public String tag=ClassInfoRepository.class.getSimpleName();
    public StaffInfoDao staffInfoDao;
    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public StaffInfoRepository(Application application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        staffInfoDao = db.staffInfoDao();

    }

    public LiveData<List<StaffAttendanceEntity>> getStaffInfoList(String inst_id) {
        LiveData<List<StaffAttendanceEntity>> classIdList= staffInfoDao.getStaffInfoList(inst_id);
        return classIdList;
    }

    long x;
    public long updateStaffInfo(List<StaffAttendanceEntity> staffAttendanceEntities) {
        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                staffInfoDao.updateStaffInfo(staffAttendanceEntities);
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
