package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.databinding.InstMainActivityBinding;
import com.example.twdinspection.inspection.room.repository.MenuSectionsRepository;
import com.example.twdinspection.inspection.interfaces.InstSubmitInterface;
import com.example.twdinspection.inspection.source.academic_overview.AcademicEntity;
import com.example.twdinspection.inspection.source.entitlements_distribution.EntitlementsEntity;
import com.example.twdinspection.inspection.source.general_comments.GeneralCommentsEntity;
import com.example.twdinspection.inspection.source.general_information.GeneralInfoEntity;
import com.example.twdinspection.inspection.source.infra_maintenance.InfraStructureEntity;
import com.example.twdinspection.inspection.source.registers_upto_date.RegistersEntity;
import com.example.twdinspection.inspection.source.cocurriular_activities.CoCurricularEntity;
import com.example.twdinspection.inspection.source.diet_issues.DietIssuesEntity;
import com.example.twdinspection.inspection.source.inst_menu_info.InstMenuInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;
import com.example.twdinspection.inspection.source.staff_attendance.StaffAttendanceEntity;
import com.example.twdinspection.inspection.source.student_attendence_info.StudAttendInfoEntity;
import com.example.twdinspection.inspection.source.submit.InstSubmitRequest;
import com.example.twdinspection.inspection.source.submit.InstSubmitResponse;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstMainViewModel extends AndroidViewModel {
    private MenuSectionsRepository mRepository;
    private LiveData<List<InstMenuInfoEntity>> instMenuInfoEntities;
    private InstSubmitInterface instSubmitInterface;
    InstMainActivityBinding binding;
    private LiveData<GeneralInfoEntity> generalInfoEntityLiveData;
    private LiveData<List<StudAttendInfoEntity>> studAttendInfo;
    private LiveData<List<StaffAttendanceEntity>> staffAttendInfo;
    private LiveData<MedicalInfoEntity> medicalInfoEntityLiveData;
    private LiveData<DietIssuesEntity> dietIssuesEntityLiveData;
    private LiveData<InfraStructureEntity> infraStructureEntityLiveData;
    private LiveData<AcademicEntity> academicEntityLiveData;
    private LiveData<CoCurricularEntity> coCurricularEntityLiveData;
    private LiveData<EntitlementsEntity> entitlementsEntityLiveData;
    private LiveData<RegistersEntity> registersEntityLiveData;
    private LiveData<GeneralCommentsEntity> generalCommentsEntityLiveData;
    private ErrorHandlerInterface errorHandlerInterface;
    Context context;

    public InstMainViewModel(Application application) {
        super(application);
        mRepository = new MenuSectionsRepository(application);
        setDefaults();
    }

    private void setDefaults() {
        generalInfoEntityLiveData = new MutableLiveData<>();
        studAttendInfo = new MutableLiveData<>();
        staffAttendInfo = new MutableLiveData<>();
        medicalInfoEntityLiveData = new MutableLiveData<>();
        dietIssuesEntityLiveData = new MutableLiveData<>();
        infraStructureEntityLiveData = new MutableLiveData<>();
        academicEntityLiveData = new MutableLiveData<>();
        coCurricularEntityLiveData = new MutableLiveData<>();
        entitlementsEntityLiveData = new MutableLiveData<>();
        registersEntityLiveData = new MutableLiveData<>();
        generalCommentsEntityLiveData = new MutableLiveData<>();
        instMenuInfoEntities = new MutableLiveData<>();

        try {
            errorHandlerInterface = (ErrorHandlerInterface) context;
            instSubmitInterface = (InstSubmitInterface) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InstMainViewModel(InstMainActivityBinding binding, Application application, Context context) {
        super(application);
        this.context = context;
        this.binding = binding;
        setDefaults();
        mRepository = new MenuSectionsRepository(application);
    }

    public void insertMenuSections(List<InstMenuInfoEntity> menuInfoEntities) {
        mRepository.insertMenuSections(menuInfoEntities);
    }

    public LiveData<List<InstMenuInfoEntity>> getAllSections() {
        if (instMenuInfoEntities != null) {
            instMenuInfoEntities = mRepository.getSections();
        }
        return instMenuInfoEntities;
    }

    public void submitInstDetails(InstSubmitRequest instSubmitRequest) {
        try {
            TWDService twdService = TWDService.Factory.create("school");
            Gson gson = new Gson();
            String request = gson.toJson(instSubmitRequest);
            Log.i("request", "" + request);
            Log.i("request_inst_id", "" + instSubmitRequest.getInstitute_id());
            twdService.getInstSubmitResponse(instSubmitRequest).enqueue(new Callback<InstSubmitResponse>() {
                @Override
                public void onResponse(@NotNull Call<InstSubmitResponse> call, @NotNull Response<InstSubmitResponse> response) {
                    binding.appbar.progress.setVisibility(View.GONE);
                    instSubmitInterface.getSubmitData(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<InstSubmitResponse> call, @NotNull Throwable t) {
                    errorHandlerInterface.handleError(t, context);
                }
            });
        } catch (Exception e) {
            errorHandlerInterface.handleError(e, context);
            e.printStackTrace();
        }

    }

    public LiveData<GeneralInfoEntity> getGeneralInfoData() {
        if (generalInfoEntityLiveData != null) {
            generalInfoEntityLiveData = mRepository.getGeneralInfo();
        }
        return generalInfoEntityLiveData;
    }

    public LiveData<List<StudAttendInfoEntity>> getStudAttendInfoData() {
        if (studAttendInfo != null) {
            studAttendInfo = mRepository.getStudAttendInfo();
        }
        return studAttendInfo;
    }

    public LiveData<List<StaffAttendanceEntity>> getStaffInfoData() {
        if (staffAttendInfo != null) {
            staffAttendInfo = mRepository.getStaffInfo();
        }
        return staffAttendInfo;
    }

    public LiveData<MedicalInfoEntity> getMedicalInfo() {
        if (medicalInfoEntityLiveData != null) {
            medicalInfoEntityLiveData = mRepository.getMedicalInfo();
        }
        return medicalInfoEntityLiveData;
    }

    public LiveData<DietIssuesEntity> getDietInfoData() {
        if (dietIssuesEntityLiveData != null) {
            dietIssuesEntityLiveData = mRepository.getDietInfo();
        }
        return dietIssuesEntityLiveData;
    }

    public LiveData<InfraStructureEntity> getInfrastructureInfoData() {
        if (infraStructureEntityLiveData != null) {
            infraStructureEntityLiveData = mRepository.getInfrastructureInfo();
        }
        return infraStructureEntityLiveData;
    }

    public LiveData<AcademicEntity> getAcademicInfoData() {
        if (academicEntityLiveData != null) {
            academicEntityLiveData = mRepository.getAcademicInfo();
        }
        return academicEntityLiveData;
    }

    public LiveData<CoCurricularEntity> getCocurricularInfoData() {
        if (coCurricularEntityLiveData != null) {
            coCurricularEntityLiveData = mRepository.getCocurricularInfo();
        }
        return coCurricularEntityLiveData;
    }

    public LiveData<EntitlementsEntity> getEntitlementInfoData() {
        if (entitlementsEntityLiveData != null) {
            entitlementsEntityLiveData = mRepository.getEntitlementInfo();
        }
        return entitlementsEntityLiveData;
    }

    public LiveData<RegistersEntity> getRegistersInfoData() {
        if (registersEntityLiveData != null) {
            registersEntityLiveData = mRepository.getRegistersInfo();
        }
        return registersEntityLiveData;
    }

    public LiveData<GeneralCommentsEntity> getGeneralCommentsInfoData() {
        if (generalCommentsEntityLiveData != null) {
            generalCommentsEntityLiveData = mRepository.getGeneralCommentsInfo();
        }
        return generalCommentsEntityLiveData;
    }

    public long updateSectionInfo(String time, int id, String instId) {
        return mRepository.updateSectionInfo(time, id, instId);
    }

    public LiveData<Integer> getMenuRecordsCount() {
        return mRepository.getMenuRecordsCount();
    }


    public LiveData<Integer> getSectionId(String name) {
        return mRepository.getSectionId(name);
    }


    public void deleteAllInspectionData() {
        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                mRepository.deleteAllInspectionData();
            }
        });

        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
            }


            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public void deleteMenuData() {
        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                mRepository.deleteMenuData();
            }
        });

        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
            }


            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

}
