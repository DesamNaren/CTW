package com.cgg.twdinspection.inspection.room.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.inspection.room.Dao.MedicalInfoDao;
import com.cgg.twdinspection.inspection.room.database.SchoolDatabase;
import com.cgg.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalDetailsBean;
import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MedicalInfoRepository {

    private final MedicalInfoDao medicalInfoDao;
    private long x;

    public MedicalInfoRepository(Context application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        medicalInfoDao = db.medicalInfoDao();
    }

    public long insertMedicalInfo(MedicalInfoEntity medicalIssuesEntity) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                medicalInfoDao.insertMedicalInfo(medicalIssuesEntity);
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

    public long insertCallInfo(CallHealthInfoEntity callHealthInfoEntity) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                medicalInfoDao.insertCallInfo(callHealthInfoEntity);
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

    public LiveData<Integer> getCallCnt() {
        return medicalInfoDao.callCnt();
    }

    public long deleteCallInfo(CallHealthInfoEntity callHealthInfoEntity) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                medicalInfoDao.deleteCallInfo(callHealthInfoEntity);
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

    public LiveData<List<CallHealthInfoEntity>> getCallListLiveData() {
        return medicalInfoDao.getCallListLiveData();
    }

    public LiveData<List<MedicalDetailsBean>> getMedicalListLiveData() {
        return medicalInfoDao.getMedicalListLiveData();
    }

    public void insertMedicalDetailsInfo(List<MedicalDetailsBean> medicalDetailsBeans) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                medicalInfoDao.deleteMedicalInfo();
                medicalInfoDao.insertMedicalDetailsInfo(medicalDetailsBeans);
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
