package com.example.twdinspection.inspection.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.example.twdinspection.inspection.interfaces.InstSelInterface;
import com.example.twdinspection.inspection.room.Dao.InstSelectionDao;
import com.example.twdinspection.inspection.room.database.DistrictDatabase;
import com.example.twdinspection.inspection.source.inst_menu_info.InstSelectionInfo;

import java.util.List;

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

    public InstSelectionRepository(Context application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        instSelectionDao = db.instSelectionDao();
    }

    public LiveData<InstSelectionInfo> getSelectedInst() {
        if(infoLiveData!=null){
            infoLiveData = instSelectionDao.getInstSelection();
        }
        return infoLiveData;
    }
    public void insertSelInst(InstSelInterface instSelInterface,InstSelectionInfo instSelectionInfo) {
        new InsertDivisionAsyncTask(instSelInterface,instSelectionInfo).execute();
    }
    @SuppressLint("StaticFieldLeak")
    private class InsertDivisionAsyncTask extends AsyncTask<Void, Void, Integer> {
        InstSelectionInfo instSelectionInfo;
        InstSelInterface instSelInterface;
        InsertDivisionAsyncTask(InstSelInterface instSelInterface,InstSelectionInfo instSelectionInfo) {
            this.instSelectionInfo = instSelectionInfo;
            this.instSelInterface = instSelInterface;

        }

        @Override
        protected Integer doInBackground(Void... voids) {
            instSelectionDao.deleteInstSelection();
            instSelectionDao.insertInstSelection(instSelectionInfo);
            return instSelectionDao.instSelCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            instSelInterface.getCount(integer);
        }
    }

}
