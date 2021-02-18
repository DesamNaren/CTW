package com.cgg.twdinspection.inspection.room.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.inspection.room.Dao.CocurricularDao;
import com.cgg.twdinspection.inspection.room.database.SchoolDatabase;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.CoCurricularEntity;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.PlantsEntity;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.StudAchievementEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CocurricularRepository {

    private final CocurricularDao cocurricularDao;
    private long x;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public CocurricularRepository(Context application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        cocurricularDao = db.cocurricularDao();

    }

    public long insertAchievementInfo(StudAchievementEntity studAchievementEntity) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                cocurricularDao.insertAchievementInfo(studAchievementEntity);
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
            public void onError(Throwable e) {
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

    public long insertPlantInfo(PlantsEntity plantsEntity) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                cocurricularDao.insertPlantInfo(plantsEntity);
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
            public void onError(Throwable e) {
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

    public long insertCoCurricularInfo(CoCurricularEntity coCurricularEntity) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                cocurricularDao.insertCoCurricularInfo(coCurricularEntity);
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

    public long deletePlantsInfo() {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                cocurricularDao.deletePlantsInfo();
                cocurricularDao.deleteStuAchInfo();
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
