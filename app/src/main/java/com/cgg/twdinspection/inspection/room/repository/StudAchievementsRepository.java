package com.cgg.twdinspection.inspection.room.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.inspection.room.Dao.StudAchDao;
import com.cgg.twdinspection.inspection.room.database.SchoolDatabase;
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

public class StudAchievementsRepository {

    private final StudAchDao studAchDao;
    private long x;

    public StudAchievementsRepository(Context application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        studAchDao = db.studAchDao();
    }

    public LiveData<List<StudAchievementEntity>> getStudAchvListLiveData() {
        return studAchDao.getStudAchievements();
    }

    public long deleteAchievementsInfo(StudAchievementEntity studAchievementEntity) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                studAchDao.deleteAchievementsInfo(studAchievementEntity);
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
