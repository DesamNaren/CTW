package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.inspection.Room.repository.DMVRepository;
import com.example.twdinspection.inspection.source.dmv.SchoolDistrict;
import com.example.twdinspection.inspection.source.dmv.SchoolMandal;
import com.example.twdinspection.inspection.source.dmv.SchoolVillage;
import com.example.twdinspection.inspection.source.inst_master.MasterInstituteInfo;

import java.util.List;

public class DMVDetailsViewModel extends AndroidViewModel {
    private LiveData<List<SchoolDistrict>> districts;
    private LiveData<List<SchoolMandal>> mandals;
    private LiveData<List<SchoolVillage>> villages;
    private LiveData<List<MasterInstituteInfo>> inst_names;
    private DMVRepository mRepository;

    public DMVDetailsViewModel(Application application) {
        super(application);
        districts = new MutableLiveData<>();
        mandals = new MutableLiveData<>();
        villages = new MutableLiveData<>();
        inst_names = new MutableLiveData<>();
        mRepository = new DMVRepository(application);
    }

    public LiveData<List<SchoolDistrict>> getAllDistricts() {
        if (districts != null) {
            districts = mRepository.getDistricts();
        }
        return districts;
    }


    public LiveData<List<MasterInstituteInfo>> getInstitutes(int districtId) {
        if (inst_names != null) {
            inst_names=mRepository.getInstitutes(districtId);
        }
        return inst_names;
    }


    public LiveData<String> getDistId(String distName){
        return mRepository.getDistId(distName);
    }

    public LiveData<MasterInstituteInfo> getInstituteInfo(String instId){
        return mRepository.getInstituteInfo(instId);
    }

    public LiveData<Integer> getInstId(String instName){
        return mRepository.getInstId(instName);
    }
}
