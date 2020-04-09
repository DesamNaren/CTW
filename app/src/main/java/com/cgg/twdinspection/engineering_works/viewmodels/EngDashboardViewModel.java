package com.cgg.twdinspection.engineering_works.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.engineering_works.room.repository.SectorsRepository;
import com.cgg.twdinspection.engineering_works.room.repository.WorksRepository;
import com.cgg.twdinspection.engineering_works.source.GrantScheme;
import com.cgg.twdinspection.engineering_works.source.SectorsEntity;
import com.cgg.twdinspection.engineering_works.source.SectorsResponse;
import com.cgg.twdinspection.engineering_works.source.WorkDetail;
import com.cgg.twdinspection.engineering_works.source.WorksMasterResponse;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EngDashboardViewModel extends AndroidViewModel {

    private MutableLiveData<SectorsResponse> sectorsResponseLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    private WorksRepository worksRepository;
    private LiveData<List<SectorsEntity>> sectorsListLiveData;


    public EngDashboardViewModel(Context context, Application application) {
        super(application);
        worksRepository =new WorksRepository(application);
        sectorsResponseLiveData = new MutableLiveData<>();
        sectorsListLiveData = new MutableLiveData<>();
        this.context = context;
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }

    public int insertWorksInfo(List<WorkDetail> workDetails) {
        return worksRepository.insertWorks(workDetails);
    }

    public LiveData<Integer> getWorksCnt() {
        return worksRepository.getWorksCnt();
    }


    public LiveData<WorkDetail> getSelWorkDetails(int workId) {
        return worksRepository.getSelWorkDetails(workId);
    }






}

