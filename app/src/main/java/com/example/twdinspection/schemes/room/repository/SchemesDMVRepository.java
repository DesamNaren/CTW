package com.example.twdinspection.schemes.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.inspection.source.DistManVillage.Districts;
import com.example.twdinspection.inspection.source.DistManVillage.Mandals;
import com.example.twdinspection.schemes.room.dao.SchemeDmvDao;
import com.example.twdinspection.schemes.room.dao.SchemesInfoDao;
import com.example.twdinspection.schemes.room.database.SchemesDatabase;
import com.example.twdinspection.schemes.source.DMV.SchemesDistricts;
import com.example.twdinspection.schemes.source.FinancialYrsEntity;

import java.util.List;

public class SchemesDMVRepository {

    public SchemeDmvDao dmvDao;
//    public LiveData<List<Districts>> districts = new MutableLiveData<>();
//    public LiveData<List<Mandals>> mandals = new MutableLiveData<>();

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public SchemesDMVRepository(Application application) {
        SchemesDatabase db = SchemesDatabase.getDatabase(application);
        dmvDao = db.schemeDmvDao();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<SchemesDistricts>> getDistricts() {
        LiveData<List<SchemesDistricts>> districts=dmvDao.getDistricts();
        return districts;
    }
//
//    public LiveData<List<Mandals>> getMandals(int dist_id) {
//        return dmvDao.getMandals(dist_id);
//    }
//
//    public LiveData<List<Villages>> getVillages(int mandalId, int distId) {
//        return dmvDao.getVillages(mandalId, distId);
//    }
//
//    public LiveData<Integer> getDistId(String dist_name) {
//        return dmvDao.getDistId(dist_name);
//    }


    public LiveData<List<FinancialYrsEntity>> getFinancialYrs() {
        LiveData<List<FinancialYrsEntity>> financialYrs=dmvDao.getFinancialYrs();
        return financialYrs;
    }

//    public LiveData<Integer> getMandalId(String mandalName, int distId) {
//        return dmvDao.getMandalId(mandalName, distId);
//    }
//
//    public LiveData<Integer> getVillageId(String mandalName, int manId, int distId) {
//        return dmvDao.getVillageId(mandalName, manId, distId);
//    }
//

}
