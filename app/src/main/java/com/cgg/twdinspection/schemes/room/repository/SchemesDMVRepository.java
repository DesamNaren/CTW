package com.cgg.twdinspection.schemes.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.schemes.room.dao.SchemeDmvDao;
import com.cgg.twdinspection.schemes.room.database.SchemesDatabase;
import com.cgg.twdinspection.schemes.source.dmv.SchemeDistrict;
import com.cgg.twdinspection.schemes.source.dmv.SchemeMandal;
import com.cgg.twdinspection.schemes.source.dmv.SchemeVillage;
import com.cgg.twdinspection.schemes.source.finyear.FinancialYearsEntity;

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

    // Room executes all queries on file_provider_paths separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<SchemeDistrict>> getDistricts() {
        LiveData<List<SchemeDistrict>> districts = dmvDao.getDistricts();
        return districts;
    }

    public LiveData<List<SchemeMandal>> getMandals(String dist_id) {
        return dmvDao.getMandals(dist_id);
    }

    public LiveData<List<SchemeVillage>> getVillages(String mandalId, String distId) {
        return dmvDao.getVillages(mandalId, distId);
    }

    public LiveData<String> getDistId(String dist_name) {
        return dmvDao.getDistId(dist_name);
    }


    public LiveData<List<FinancialYearsEntity>> getFinancialYrs() {
        LiveData<List<FinancialYearsEntity>> financialYrs = dmvDao.getFinancialYrs();
        return financialYrs;
    }

    public LiveData<String> getMandalId(String mandalName, String distId) {
        return dmvDao.getMandalId(mandalName, distId);
    }

    public LiveData<String> getVillageId(String mandalName, String manId, String distId) {
        return dmvDao.getVillageId(mandalName, manId, distId);
    }

    public LiveData<String> getFinYearId(String finYear) {
        return dmvDao.getFinYearId(finYear);
    }

}
