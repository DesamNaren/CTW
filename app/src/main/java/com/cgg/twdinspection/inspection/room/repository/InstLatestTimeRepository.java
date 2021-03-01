package com.cgg.twdinspection.inspection.room.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.inspection.interfaces.SchoolInstInterface;
import com.cgg.twdinspection.inspection.room.Dao.InstLastestTimeDao;
import com.cgg.twdinspection.inspection.room.database.SchoolDatabase;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstLatestTimeInfo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InstLatestTimeRepository {

    private final InstLastestTimeDao instLastestTimeDao;
    private LiveData<List<InstLatestTimeInfo>> listLiveData = new MutableLiveData<>();

    public InstLatestTimeRepository(Context application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        instLastestTimeDao = db.instLastestTimeDao();
    }

    public void insertLatestTime(InstLatestTimeInfo instLatestTimeInfo) {
        TWDApplication.getExecutorService().execute(() -> {
//            instSelectionDao.deleteInstSelection();
            instLastestTimeDao.insertInstLatestTime(instLatestTimeInfo);
        });
    }

    public void updateTimeInfo(String time, String instId) {
        try {
            TWDApplication.getExecutorService().execute(() -> {
                instLastestTimeDao.updateTimeInfo(time, instId);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTimeInfo( String instId) {
        try {
            TWDApplication.getExecutorService().execute(() -> {
                instLastestTimeDao.deleteTimeInfo(instId);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LiveData<List<InstLatestTimeInfo>> getLatestTimeInfo() {
        if (listLiveData != null) {
            listLiveData = instLastestTimeDao.getLatestTimeInfo();
        }
        return listLiveData;
    }

}
