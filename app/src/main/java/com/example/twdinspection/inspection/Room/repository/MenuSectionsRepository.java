package com.example.twdinspection.inspection.Room.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.twdinspection.inspection.Room.Dao.MenuSectionsDao;
import com.example.twdinspection.inspection.Room.database.DistrictDatabase;
import com.example.twdinspection.inspection.source.instMenuInfo.InstMenuInfoEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MenuSectionsRepository {

    private MenuSectionsDao menuSectionsDao;
    private String tag = MenuSectionsRepository.class.getSimpleName();

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public MenuSectionsRepository(Application application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        menuSectionsDao = db.menuSectionsDao();
    }

    public LiveData<List<InstMenuInfoEntity>> getSections() {
        return menuSectionsDao.getSections();
    }


    long x;

    public long insertMenuSections(List<InstMenuInfoEntity> menuInfoEntities) {

        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                menuSectionsDao.insertMenuSections(menuInfoEntities);
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
            }


            @Override
            public void onError(Throwable e) {
                Log.i("Tag", tag + "onError: " + x);
            }

            @Override
            public void onComplete() {
                Log.i("Tag", tag + "onComplete: " + x);
            }
        };

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
        return x;
    }

    public long updateSectionInfo(String time,int id) {
        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                menuSectionsDao.updateSectionInfo(time,id);
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
            }


            @Override
            public void onError(Throwable e) {
                Log.i("Tag", tag + "onError: " + x);
            }

            @Override
            public void onComplete() {
                Log.i("Tag", tag + "onComplete: " + x);
            }
        };

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
        return x;
    }



}
