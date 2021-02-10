package com.cgg.twdinspection.inspection.room.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.inspection.interfaces.InstSelInterface;
import com.cgg.twdinspection.inspection.room.Dao.InstSelectionDao;
import com.cgg.twdinspection.inspection.room.database.SchoolDatabase;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstSelectionInfo;

public class InstSelectionRepository {

    private LiveData<InstSelectionInfo> infoLiveData = new MutableLiveData<>();
    private InstSelectionDao instSelectionDao;

    public InstSelectionRepository(Context application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        instSelectionDao = db.instSelectionDao();
    }

    public LiveData<InstSelectionInfo> getSelectedInst() {
        if (infoLiveData != null) {
            infoLiveData = instSelectionDao.getInstSelection();
        }
        return infoLiveData;
    }

    public void insertSelInst(InstSelInterface instSelInterface, InstSelectionInfo instSelectionInfo) {
        new InsertDivisionAsyncTask(instSelInterface, instSelectionInfo).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertDivisionAsyncTask extends AsyncTask<Void, Void, Integer> {
        InstSelectionInfo instSelectionInfo;
        InstSelInterface instSelInterface;

        InsertDivisionAsyncTask(InstSelInterface instSelInterface, InstSelectionInfo instSelectionInfo) {
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
