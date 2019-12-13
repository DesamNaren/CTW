package com.example.twdinspection.Room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.Room.Dao.DistrictDao;
import com.example.twdinspection.Room.database.DistrictDatabase;
import com.example.twdinspection.source.DistManVillage.Districts;
import com.example.twdinspection.source.DistManVillage.Mandals;
import com.example.twdinspection.source.DistManVillage.Villages;

import java.util.List;

public class DistrictRepository {

    public DistrictDao districtDao;
    public LiveData<List<Districts>> districts = new MutableLiveData<>();
    public LiveData<List<Mandals>> mandals = new MutableLiveData<>();
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

    public LiveData<List<Mandals>> getMandals(int dist_id) {
        return districtDao.getMandals(dist_id);
    }

    public LiveData<List<Villages>> getVillages(int mandalId, int distId) {
        return districtDao.getVillages(mandalId, distId);
    }

    public LiveData<Integer> getDistId(String dist_name) {
        return districtDao.getDistId(dist_name);
    }

    public LiveData<Integer> getMandalId(String mandalName, int distId) {
        return districtDao.getMandalId(mandalName, distId);
    }
}
