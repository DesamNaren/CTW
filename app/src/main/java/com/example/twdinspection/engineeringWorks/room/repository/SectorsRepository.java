package com.example.twdinspection.engineeringWorks.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import com.example.twdinspection.engineeringWorks.room.dao.SectorsDao;
import com.example.twdinspection.engineeringWorks.room.database.EngWorksDatabase;
import com.example.twdinspection.engineeringWorks.source.SectorsEntity;
import com.example.twdinspection.gcc.interfaces.GCCDivisionInterface;
import com.example.twdinspection.gcc.source.divisions.DivisionsInfo;

import java.util.List;

public class SectorsRepository {

    SectorsDao sectorsDao;
    public SectorsRepository(Application application) {
        EngWorksDatabase database=EngWorksDatabase.getDatabase(application);
        sectorsDao=database.sectorsDao();
    }

    public void insertSectors(List<SectorsEntity> sectorsEntities){
        new InsertSectorsAsyncTask(sectorsEntities).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertSectorsAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<SectorsEntity> sectorsEntities;
        GCCDivisionInterface dmvInterface;

        InsertSectorsAsyncTask(List<SectorsEntity> sectorsEntities) {
            this.sectorsEntities = sectorsEntities;
            this.dmvInterface = dmvInterface;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            sectorsDao.insertSectors(sectorsEntities);
            return sectorsDao.sectorCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
//            dmvInterface.divisionCount(integer);
        }
    }

}
