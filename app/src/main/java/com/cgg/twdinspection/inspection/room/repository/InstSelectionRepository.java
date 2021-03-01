package com.cgg.twdinspection.inspection.room.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.inspection.interfaces.InstSelInterface;
import com.cgg.twdinspection.inspection.room.Dao.InstSelectionDao;
import com.cgg.twdinspection.inspection.room.database.SchoolDatabase;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstSelectionInfo;

public class InstSelectionRepository {

    private LiveData<InstSelectionInfo> infoLiveData = new MutableLiveData<>();
    private LiveData<String> randomNoLivedata = new MutableLiveData<>();
    private final InstSelectionDao instSelectionDao;

    public InstSelectionRepository(Context application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        instSelectionDao = db.instSelectionDao();
    }

    public LiveData<InstSelectionInfo> getSelectedInst(String instId) {
        if (infoLiveData != null) {
            infoLiveData = instSelectionDao.getInstSelection(instId);
        }
        return infoLiveData;
    }


    public LiveData<String> getRandomNo(String inst_id) {
        if (randomNoLivedata != null) {
            randomNoLivedata = instSelectionDao.getRandomNo(inst_id);
        }
        return randomNoLivedata;
    }

    public void insertSelInst(InstSelInterface instSelInterface, InstSelectionInfo instSelectionInfo) {
        TWDApplication.getExecutorService().execute(() -> {
            instSelectionDao.deleteInstSelection(instSelectionInfo.getInst_id());
            instSelectionDao.insertInstSelection(instSelectionInfo);
            int x = instSelectionDao.instSelCount();
            //Background work here
            TWDApplication.getHandler().post(() -> {
                try {
                    if (x > 0) {
                        instSelInterface.getCount(x);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI Thread work here
            });
        });
    }

}
