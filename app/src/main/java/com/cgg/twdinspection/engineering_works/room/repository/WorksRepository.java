package com.cgg.twdinspection.engineering_works.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.engineering_works.room.dao.WorksDao;
import com.cgg.twdinspection.engineering_works.room.database.EngWorksDatabase;
import com.cgg.twdinspection.engineering_works.source.WorkDetail;

import java.util.List;

public class WorksRepository {

    WorksDao worksDao;
    private LiveData<List<WorkDetail>> workDetailLiveData = new MutableLiveData<>();
    private int x;
    public WorksRepository(Application application) {
        EngWorksDatabase database=EngWorksDatabase.getDatabase(application);
        worksDao =database.worksDao();
    }
    public LiveData<List<WorkDetail>> getSelWorkDetails(String distId,String mandId) {
        if(workDetailLiveData !=null){
            workDetailLiveData = worksDao.getWorkDetails(distId,mandId);
        }
        return workDetailLiveData;
    }

    public int insertWorks(List<WorkDetail> workDetails){
        new InsertWorksAsyncTask(workDetails).execute();
        return x;
    }

    public LiveData<List<String>> getDistricts() {
        return worksDao.getDistricts();
    }

    public LiveData<List<String>> getMandals(String distId) {
        return worksDao.getMandalList(distId);
    }

    public LiveData<String> getDistId(String distName) {
        return worksDao.getDistrictId(distName);
    }

    public LiveData<String> getMandalId(String mandalName) {
        return worksDao.getMandalId(mandalName);
    }

    public LiveData<Integer> getWorksCnt(){
       return worksDao.getWorksCount();
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertWorksAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<WorkDetail> workDetails;

        InsertWorksAsyncTask(List<WorkDetail> workDetails) {
            this.workDetails = workDetails;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            worksDao.insertWorks(workDetails);
            return worksDao.getInsertedWorksCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
           x=integer;
        }
    }

    public LiveData<Integer> getSectorId(String sectorName) {
        return worksDao.getSectorId(sectorName);
    }
}
