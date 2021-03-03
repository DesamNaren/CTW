package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.databinding.InstMainActivityBinding;
import com.cgg.twdinspection.inspection.interfaces.InstSubmitInterface;
import com.cgg.twdinspection.inspection.room.repository.MenuSectionsRepository;
import com.cgg.twdinspection.inspection.source.academic_overview.AcademicEntity;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.CoCurricularEntity;
import com.cgg.twdinspection.inspection.source.diet_issues.DietIssuesEntity;
import com.cgg.twdinspection.inspection.source.entitlements_distribution.EntitlementsEntity;
import com.cgg.twdinspection.inspection.source.general_comments.GeneralCommentsEntity;
import com.cgg.twdinspection.inspection.source.general_information.GeneralInfoEntity;
import com.cgg.twdinspection.inspection.source.infra_maintenance.InfraStructureEntity;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstMenuInfoEntity;
import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;
import com.cgg.twdinspection.inspection.source.registers_upto_date.RegistersEntity;
import com.cgg.twdinspection.inspection.source.staff_attendance.StaffAttendanceEntity;
import com.cgg.twdinspection.inspection.source.student_attendence_info.StudAttendInfoEntity;
import com.cgg.twdinspection.inspection.source.submit.InstSubmitRequest;
import com.cgg.twdinspection.inspection.source.submit.InstSubmitResponse;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
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

    public InstMainViewModel( Application application, Context context) {
        super(application);
        this.context = context;
        setDefaults();
        mRepository = new MenuSectionsRepository(application);
    }

    public void insertMenuSections(List<InstMenuInfoEntity> menuInfoEntities) {
        mRepository.insertMenuSections(menuInfoEntities);
    }

    public LiveData<List<InstMenuInfoEntity>> getAllSections(String inst_id) {
        if (instMenuInfoEntities != null) {
            instMenuInfoEntities = mRepository.getSections(inst_id);
        }
        return instMenuInfoEntities;
    }

    public void submitInstDetails(InstSubmitRequest instSubmitRequest) {
        try {
            TWDService twdService = TWDService.Factory.create("school");
            Gson gson = new Gson();
            twdService.getInstSubmitResponse(instSubmitRequest).enqueue(new Callback<InstSubmitResponse>() {
                @Override
                public void onResponse(@NotNull Call<InstSubmitResponse> call, @NotNull Response<InstSubmitResponse> response) {
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

    public LiveData<GeneralInfoEntity> getGeneralInfoData(String inst_id) {
        if (generalInfoEntityLiveData != null) {
            generalInfoEntityLiveData = mRepository.getGeneralInfo(inst_id);
        }
        return generalInfoEntityLiveData;
    }

    public LiveData<List<StudAttendInfoEntity>> getStudAttendInfoData(String inst_id) {
        if (studAttendInfo != null) {
            studAttendInfo = mRepository.getStudAttendInfo(inst_id);
        }
        return studAttendInfo;
    }

    public LiveData<List<StaffAttendanceEntity>> getStaffInfoData(String inst_id) {
        if (staffAttendInfo != null) {
            staffAttendInfo = mRepository.getStaffInfo(inst_id);
        }
        return staffAttendInfo;
    }

    public LiveData<MedicalInfoEntity> getMedicalInfo(String inst_id) {
        if (medicalInfoEntityLiveData != null) {
            medicalInfoEntityLiveData = mRepository.getMedicalInfo(inst_id);
        }
        return medicalInfoEntityLiveData;
    }

    public LiveData<DietIssuesEntity> getDietInfoData(String inst_id) {
        if (dietIssuesEntityLiveData != null) {
            dietIssuesEntityLiveData = mRepository.getDietInfo(inst_id);
        }
        return dietIssuesEntityLiveData;
    }

    public LiveData<InfraStructureEntity> getInfrastructureInfoData(String inst_id) {
        if (infraStructureEntityLiveData != null) {
            infraStructureEntityLiveData = mRepository.getInfrastructureInfo(inst_id);
        }
        return infraStructureEntityLiveData;
    }

    public LiveData<AcademicEntity> getAcademicInfoData(String inst_id) {
        if (academicEntityLiveData != null) {
            academicEntityLiveData = mRepository.getAcademicInfo(inst_id);
        }
        return academicEntityLiveData;
    }

    public LiveData<CoCurricularEntity> getCocurricularInfoData(String inst_id) {
        if (coCurricularEntityLiveData != null) {
            coCurricularEntityLiveData = mRepository.getCocurricularInfo(inst_id);
        }
        return coCurricularEntityLiveData;
    }

    public LiveData<EntitlementsEntity> getEntitlementInfoData(String inst_id) {
        if (entitlementsEntityLiveData != null) {
            entitlementsEntityLiveData = mRepository.getEntitlementInfo(inst_id);
        }
        return entitlementsEntityLiveData;
    }

    public LiveData<RegistersEntity> getRegistersInfoData(String inst_id) {
        if (registersEntityLiveData != null) {
            registersEntityLiveData = mRepository.getRegistersInfo(inst_id);
        }
        return registersEntityLiveData;
    }

    public LiveData<GeneralCommentsEntity> getGeneralCommentsInfoData(String inst_id) {
        if (generalCommentsEntityLiveData != null) {
            generalCommentsEntityLiveData = mRepository.getGeneralCommentsInfo(inst_id);
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


    public void deleteAllInspectionData(String inst_id) {
        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                mRepository.deleteAllInspectionData(inst_id);
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

    public void deleteMenuData(String inst_id) {
        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                mRepository.deleteMenuData( inst_id);
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
