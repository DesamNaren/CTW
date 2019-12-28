package com.example.twdinspection.schemes.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.inspection.Room.database.DistrictDatabase;
import com.example.twdinspection.inspection.source.DistManVillage.Districts;
import com.example.twdinspection.inspection.source.DistManVillage.Mandals;
import com.example.twdinspection.inspection.source.DistManVillage.Villages;
import com.example.twdinspection.inspection.source.GeneralInformation.InstitutesEntity;
import com.example.twdinspection.schemes.room.dao.SchemeDmvDao;

import java.util.List;

public class SchemesDMVRepository {

    public SchemeDmvDao dmvDao;
    public LiveData<List<Districts>> districts = new MutableLiveData<>();
    public LiveData<List<Mandals>> mandals = new MutableLiveData<>();
    public LiveData<List<String>> institute_names = new MutableLiveData<>();
    public int count;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public SchemesDMVRepository(Application application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
//        DistrictDatabase db = DistrictDatabase.getSchemeDatabase(application);
        dmvDao = db.dmvDao();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Districts>> getDistricts() {
        return dmvDao.getDistricts();
    }

    public LiveData<List<Mandals>> getMandals(int dist_id) {
        return dmvDao.getMandals(dist_id);
    }

    public LiveData<List<Villages>> getVillages(int mandalId, int distId) {
        return dmvDao.getVillages(mandalId, distId);
    }

    public LiveData<Integer> getDistId(String dist_name) {
        return dmvDao.getDistId(dist_name);
    }



    public LiveData<List<InstitutesEntity>> getInstitutes() {
        LiveData<List<InstitutesEntity>> institutes=dmvDao.getInstitutes();
        return institutes;
    }



    public LiveData<Integer> getMandalId(String mandalName, int distId) {
        return dmvDao.getMandalId(mandalName, distId);
    }

    public LiveData<Integer> getVillageId(String mandalName, int manId, int distId) {
        return dmvDao.getVillageId(mandalName, manId, distId);
    }

    public LiveData<String> getInstId(String inst_name) {
        return dmvDao.getInstId(inst_name);
    }

}
