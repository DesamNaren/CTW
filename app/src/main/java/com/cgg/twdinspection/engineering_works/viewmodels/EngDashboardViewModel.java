package com.cgg.twdinspection.engineering_works.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.engineering_works.room.repository.WorksRepository;
import com.cgg.twdinspection.engineering_works.source.SectorsEntity;
import com.cgg.twdinspection.engineering_works.source.SectorsResponse;
import com.cgg.twdinspection.engineering_works.source.WorkDetail;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import java.util.List;

public class EngDashboardViewModel extends AndroidViewModel {

    private final WorksRepository worksRepository;


    public EngDashboardViewModel(Context context, Application application) {
        super(application);
        worksRepository = new WorksRepository(application);
    }

    public int insertWorksInfo(List<WorkDetail> workDetails) {
        return worksRepository.insertWorks(workDetails);
    }

    public LiveData<List<String>> getDistricts() {
        return worksRepository.getDistricts();
    }

    public LiveData<List<String>> getMandals(String distId) {
        return worksRepository.getMandals(distId);
    }

    public LiveData<String> getDistId(String distName) {
        return worksRepository.getDistId(distName);
    }

    public LiveData<String> getMandalId(String mandalName) {
        return worksRepository.getMandalId(mandalName);
    }

    public LiveData<Integer> getWorksCnt() {
        return worksRepository.getWorksCnt();
    }


    public LiveData<List<WorkDetail>> getSelWorkDetails(String distId, String mandId) {
        return worksRepository.getSelWorkDetails(distId, mandId);
    }


}

