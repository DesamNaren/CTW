package com.example.twdinspection.engineering_works.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.engineering_works.room.dao.SectorsDao;
import com.example.twdinspection.engineering_works.room.database.EngWorksDatabase;
import com.example.twdinspection.engineering_works.source.GrantScheme;
import com.example.twdinspection.engineering_works.source.SectorsEntity;

import java.util.List;

public class SectorsRepository {

    SectorsDao sectorsDao;
    private LiveData<List<SectorsEntity>> sectorsLiveData = new MutableLiveData<>();
    private int x;
    public SectorsRepository(Application application) {
        EngWorksDatabase database=EngWorksDatabase.getDatabase(application);
        sectorsDao=database.sectorsDao();
    }
    public LiveData<List<SectorsEntity>> getSectors() {
        if(sectorsLiveData !=null){
            sectorsLiveData = sectorsDao.getSectors();
        }
        return sectorsLiveData;
    }

    public int insertSectors(List<SectorsEntity> sectorsEntities){
        new InsertSectorsAsyncTask(sectorsEntities).execute();
        return x;
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertSectorsAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<SectorsEntity> sectorsEntities;

        InsertSectorsAsyncTask(List<SectorsEntity> sectorsEntities) {
            this.sectorsEntities = sectorsEntities;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            sectorsDao.insertSectors(sectorsEntities);
            return sectorsDao.sectorCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            x=integer;
        }
    }

    public LiveData<Integer> getSectorId(String sectorName) {
        return sectorsDao.getSectorId(sectorName);
    }
}
