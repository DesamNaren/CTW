package com.cgg.twdinspection.inspection.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.inspection.room.Dao.DistrictDao;
import com.cgg.twdinspection.inspection.room.database.SchoolDatabase;
import com.cgg.twdinspection.inspection.source.dmv.SchoolDistrict;
import com.cgg.twdinspection.inspection.source.dmv.SchoolMandal;
import com.cgg.twdinspection.inspection.source.dmv.SchoolVillage;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;

import java.util.List;

public class DMVRepository {

    private DistrictDao districtDao;
    public LiveData<List<SchoolDistrict>> districts = new MutableLiveData<>();
    public LiveData<List<SchoolMandal>> mandals = new MutableLiveData<>();
    public LiveData<List<String>> institute_names = new MutableLiveData<>();
    public int count;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public DMVRepository(Application application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        districtDao = db.distDao();

    }

    // Room executes all queries on file_provider_paths separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<SchoolDistrict>> getDistricts() {
        return districtDao.getDistricts();
    }

    public LiveData<List<SchoolMandal>> getMandals(int dist_id) {
        return districtDao.getMandals(dist_id);
    }

    public LiveData<List<SchoolVillage>> getVillages(int mandalId, int distId) {
        return districtDao.getVillages(mandalId, distId);
    }

    public LiveData<String> getDistId(String dist_name) {
        return districtDao.getDistId(dist_name);
    }



    public LiveData<List<MasterInstituteInfo>> getInstitutes(int districtId) {
        LiveData<List<MasterInstituteInfo>> institutes=districtDao.getInstitutes(districtId);
        return institutes;
    }
 public LiveData<List<MasterInstituteInfo>> getAllInstitutes() {
        LiveData<List<MasterInstituteInfo>> institutes=districtDao.getAllInstitutes();
        return institutes;
    }



    public LiveData<MasterInstituteInfo> getInstituteInfo(String instId) {
        return districtDao.getInstituteInfo(instId);
    }

    public LiveData<Integer> getInstId(String inst_name, int districtId) {
        return districtDao.getInstId(inst_name, districtId);
    }

}
