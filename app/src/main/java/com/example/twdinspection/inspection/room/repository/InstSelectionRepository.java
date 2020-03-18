package com.example.twdinspection.inspection.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.inspection.room.Dao.InstSelectionDao;
import com.example.twdinspection.inspection.room.database.DistrictDatabase;
import com.example.twdinspection.inspection.source.inst_menu_info.InstSelectionInfo;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InstSelectionRepository {

    private LiveData<InstSelectionInfo> infoLiveData = new MutableLiveData<>();
    private InstSelectionDao instSelectionDao;

    public InstSelectionRepository(Application application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        instSelectionDao = db.instSelectionDao();
    }

    public LiveData<InstSelectionInfo> getSelectedInst() {
        if(infoLiveData!=null){
            infoLiveData = instSelectionDao.getInstSelection();
        }
        return infoLiveData;
    }

    LiveData<Long> x;

    public LiveData<Long> insertInstSelection(InstSelectionInfo instSelectionInfo) {
        Observable observable = Observable.create(new ObservableOnSubscribe<LiveData<Long> >() {
            @Override
            public void subscribe(ObservableEmitter<LiveData<Long> > emitter) throws Exception {
                instSelectionDao.deleteInstSelection();
                instSelectionDao.insertInstSelection(instSelectionInfo);
            }
        });

        Observer<LiveData<Long> > observer = new Observer<LiveData<Long> >() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(LiveData<Long>  aLong) {
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

}
