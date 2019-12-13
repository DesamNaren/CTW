package com.example.twdinspection.Room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.Room.Dao.DistrictDao;
import com.example.twdinspection.Room.database.DistrictDatabase;
import com.example.twdinspection.source.DistManVillage.Districts;

import java.util.List;

public class DistrictRepository {

    public DistrictDao districtDao;
    public LiveData<List<Districts>> districts = new MutableLiveData<>();
    public int count;

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
    public LiveData<List<Districts>> getDistricts() {
       return districtDao.getDistricts();
    }

    @SuppressLint("StaticFieldLeak")
    private class getAllDistrictsAsyncTask extends AsyncTask<Void, Void, LiveData<List<Districts>>> {

        private DistrictDao mAsyncTaskDao;

        getAllDistrictsAsyncTask(DistrictDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<Districts>> doInBackground(Void... voids) {
            districts = mAsyncTaskDao.getDistricts();
            return districts;
        }

        @Override
        protected void onPostExecute(LiveData<List<Districts>> districtEntities) {
            super.onPostExecute(districtEntities);
        }
    }
}
