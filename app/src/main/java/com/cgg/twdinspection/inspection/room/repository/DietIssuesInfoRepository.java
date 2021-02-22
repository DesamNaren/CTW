package com.cgg.twdinspection.inspection.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.inspection.room.Dao.DietIssuesInfoDao;
import com.cgg.twdinspection.inspection.room.database.SchoolDatabase;
import com.cgg.twdinspection.inspection.source.diet_issues.DietIssuesEntity;
import com.cgg.twdinspection.inspection.source.diet_issues.DietListEntity;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DietIssuesInfoRepository {

    private final DietIssuesInfoDao dietIssuesInfoDao;
    private long x;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples

    public DietIssuesInfoRepository(Application application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        dietIssuesInfoDao = db.dietIssuesInfoDao();

    }

    public LiveData<MasterInstituteInfo> getMasterDietList(String inst_id) {
        return dietIssuesInfoDao.getMasterDietList(inst_id);
    }

    public LiveData<List<DietListEntity>> getDietList(String inst_id) {
        return dietIssuesInfoDao.getDietList(inst_id);
    }

    public long updateDietIssuesInfo(DietIssuesEntity dietIssuesEntity) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                dietIssuesInfoDao.updateDietIssuesInfo(dietIssuesEntity);
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

    public void insertDietInfo(List<DietListEntity> dietListEntities) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                dietIssuesInfoDao.deleteDietInfo(dietListEntities.get(0).getInstitute_id());
                dietIssuesInfoDao.insertDietInfo(dietListEntities);
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

    public long deleteDietListInfo(String inst_id) {

        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                dietIssuesInfoDao.deleteDietListInfo(inst_id);
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
