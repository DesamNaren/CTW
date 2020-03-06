package com.example.twdinspection.inspection.Room.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.twdinspection.inspection.Room.Dao.CocurricularDao;
import com.example.twdinspection.inspection.Room.database.DistrictDatabase;
import com.example.twdinspection.inspection.source.cocurriular_activities.CoCurricularEntity;
import com.example.twdinspection.inspection.source.cocurriular_activities.PlantsEntity;
import com.example.twdinspection.inspection.source.cocurriular_activities.StudAchievementEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CocurricularRepository {

    public CocurricularDao cocurricularDao;
    private String tag= CocurricularRepository.class.getSimpleName();
    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public CocurricularRepository(Context application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        cocurricularDao = db.cocurricularDao();

    }

    long x;

    public long insertAchievementInfo(StudAchievementEntity studAchievementEntity) {


        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                cocurricularDao.insertAchievementInfo(studAchievementEntity);
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

    public long insertPlantInfo(PlantsEntity plantsEntity) {


        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                cocurricularDao.insertPlantInfo(plantsEntity);
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

    public long insertCoCurricularInfo(CoCurricularEntity coCurricularEntity) {
        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                cocurricularDao.insertCoCurricularInfo(coCurricularEntity);
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


    public LiveData<Integer> getAchievementsCnt() {
        return cocurricularDao.getAchievementsCnt();
    }

    public LiveData<List<StudAchievementEntity>> getStudentAchLiveData() {
        return cocurricularDao.getAchLiveData();
    }


    public LiveData<List<PlantsEntity>> getPlantsListLiveData() {
        return cocurricularDao.getPlantLiveData();
    }

    public LiveData<Integer> getPlantsCnt() {
        return cocurricularDao.getPlantsCnt();
    }
}
