package com.example.twdinspection.inspection.Room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.inspection.Room.Dao.DistrictDao;
import com.example.twdinspection.inspection.Room.database.DistrictDatabase;
import com.example.twdinspection.inspection.source.DistManVillage.Districts;
import com.example.twdinspection.inspection.source.DistManVillage.Mandals;
import com.example.twdinspection.inspection.source.DistManVillage.Villages;
import com.example.twdinspection.inspection.source.GeneralInformation.InstitutesEntity;

import java.util.List;

public class DMVRepository {

    public DistrictDao districtDao;
    public LiveData<List<Districts>> districts = new MutableLiveData<>();
    public LiveData<List<Mandals>> mandals = new MutableLiveData<>();
    public LiveData<List<String>> institute_names = new MutableLiveData<>();
    public int count;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public DMVRepository(Application application) {
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



    public LiveData<List<InstitutesEntity>> getInstitutes() {
        LiveData<List<InstitutesEntity>> institutes=districtDao.getInstitutes();
        return institutes;
    }



    public LiveData<Integer> getMandalId(String mandalName, int distId) {
        return districtDao.getMandalId(mandalName, distId);
    }

    public LiveData<Integer> getVillageId(String mandalName, int manId, int distId) {
        return districtDao.getVillageId(mandalName, manId, distId);
    }

    public LiveData<String> getInstId(String inst_name) {
        return districtDao.getInstId(inst_name);
    }

}