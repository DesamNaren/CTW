package com.example.twdinspection.Room.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.twdinspection.Room.Dao.DistrictDao;
import com.example.twdinspection.Room.database.DistrictDatabase;
import com.example.twdinspection.source.DistManVillage.DistrictEntity;

import java.util.ArrayList;
import java.util.List;

public class DistrictRepository {

    public DistrictDao districtDao;
    public List<DistrictEntity> districts=new ArrayList<>();

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public DistrictRepository(Application application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        districtDao = db.distDao();

    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public List<DistrictEntity> getDistricts() {
        new insertAsyncTask(districtDao).execute();
        return districts;
    }
    private class insertAsyncTask extends AsyncTask<Void, Void, List<DistrictEntity>> {

        private DistrictDao mAsyncTaskDao;

        insertAsyncTask(DistrictDao dao) {
            mAsyncTaskDao = dao;
        }



        @Override
        protected List<DistrictEntity> doInBackground(Void... voids) {
            districts = mAsyncTaskDao.getDistricts();
            return districts;
        }

        @Override
        protected void onPostExecute(List<DistrictEntity> districtEntities) {
            super.onPostExecute(districtEntities);
        }
    }

}
