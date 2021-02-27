package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.inspection.room.repository.DMVRepository;
import com.cgg.twdinspection.inspection.source.dmv.SchoolDistrict;
import com.cgg.twdinspection.inspection.source.dmv.SchoolMandal;
import com.cgg.twdinspection.inspection.source.dmv.SchoolVillage;
import com.cgg.twdinspection.inspection.source.diet_issues.MasterDietListInfo;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;

import java.util.List;

public class DMVDetailsViewModel extends AndroidViewModel {
    private LiveData<List<SchoolDistrict>> districts;
    private LiveData<List<SchoolMandal>> mandals;
    private LiveData<List<SchoolVillage>> villages;
    private LiveData<List<MasterInstituteInfo>> inst_names;
    private LiveData<List<MasterInstituteInfo>> all_inst;
    private LiveData<List<MasterDietListInfo>> all_diet;
    private DMVRepository mRepository;

    public DMVDetailsViewModel(Application application) {
        super(application);
        districts = new MutableLiveData<>();
        mandals = new MutableLiveData<>();
        villages = new MutableLiveData<>();
        inst_names = new MutableLiveData<>();
        all_inst = new MutableLiveData<>();
        all_diet = new MutableLiveData<>();
        mRepository = new DMVRepository(application);
    }

    public LiveData<List<SchoolDistrict>> getSelectedDistricts(String distId) {
        if (districts != null) {
            districts = mRepository.getSelectedDistricts(distId);
        }
        return districts;
    }


    public LiveData<List<SchoolDistrict>> getAllDistricts() {
        if (districts != null) {
            districts = mRepository.getDistricts();
        }
        return districts;
    }

    public LiveData<List<MasterInstituteInfo>> getAllInstitutes() {
        if (all_inst != null) {
            all_inst = mRepository.getAllInstitutes();
        }
        return all_inst;
    }

     public LiveData<List<MasterDietListInfo>> getAllDietList() {
        if (all_diet != null) {
            all_diet = mRepository.getAllDietList();
        }
        return all_diet;
    }

    public LiveData<List<MasterInstituteInfo>> getInstitutes(int districtId) {
        if (inst_names != null) {
            inst_names = mRepository.getInstitutes(districtId);
        }
        return inst_names;
    }


    public LiveData<String> getDistId(String distName) {
        return mRepository.getDistId(distName);
    }

    public LiveData<MasterInstituteInfo> getInstituteInfo(String instId) {
        return mRepository.getInstituteInfo(instId);
    }

    public LiveData<Integer> getInstId(String instName, int districtId) {
        return mRepository.getInstId(instName, districtId);
    }
}
