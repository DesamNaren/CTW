package com.cgg.twdinspection.inspection.room.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.inspection.room.Dao.MenuSectionsDao;
import com.cgg.twdinspection.inspection.room.database.SchoolDatabase;
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

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MenuSectionsRepository {

    private final MenuSectionsDao menuSectionsDao;
    private long x;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public MenuSectionsRepository(Application application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        menuSectionsDao = db.menuSectionsDao();
    }

    public LiveData<GeneralInfoEntity> getGeneralInfo(String inst_id) {
        return menuSectionsDao.getGeneralInfo(inst_id);
    }

    public LiveData<List<StudAttendInfoEntity>> getStudAttendInfo(String inst_id) {
        return menuSectionsDao.getStudAttendInfo(inst_id);
    }

    public LiveData<List<StaffAttendanceEntity>> getStaffInfo(String inst_id) {
        return menuSectionsDao.getStaffAttendInfo(inst_id);
    }

    public LiveData<MedicalInfoEntity> getMedicalInfo(String inst_id) {
        return menuSectionsDao.getMedicalInfo(inst_id);
    }

    public LiveData<DietIssuesEntity> getDietInfo(String inst_id) {
        return menuSectionsDao.getDietInfo(inst_id);
    }

    public LiveData<InfraStructureEntity> getInfrastructureInfo(String inst_id) {
        return menuSectionsDao.getInfraInfo(inst_id);
    }

    public LiveData<AcademicEntity> getAcademicInfo(String inst_id) {
        return menuSectionsDao.getAcademicInfo(inst_id);
    }

    public LiveData<CoCurricularEntity> getCocurricularInfo(String inst_id) {
        return menuSectionsDao.getCocurricularInfo(inst_id);
    }

    public LiveData<EntitlementsEntity> getEntitlementInfo(String inst_id) {
        return menuSectionsDao.getEntitlementInfo(inst_id);
    }

    public LiveData<RegistersEntity> getRegistersInfo(String inst_id) {
        return menuSectionsDao.getRegisterInfo(inst_id);
    }

    public LiveData<GeneralCommentsEntity> getGeneralCommentsInfo(String inst_id) {
        return menuSectionsDao.getGeneralCommentsInfo(inst_id);
    }

    public LiveData<List<InstMenuInfoEntity>> getSections(String inst_id) {
        return menuSectionsDao.getSections(inst_id);
    }

    public LiveData<Integer> getMenuRecordsCount() {
        return menuSectionsDao.getMenuRecordsCount();
    }

    public long insertMenuSections(List<InstMenuInfoEntity> menuInfoEntities) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                menuSectionsDao.deleteInstMenuInfoEntity(menuInfoEntities.get(0).getInstId());
                menuSectionsDao.insertMenuSections(menuInfoEntities);
            }
        });

        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
            }

            @Override
            public void onNext(@NotNull Long aLong) {
                x = aLong;
            }


            @Override
            public void onError(@NotNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
        return x;
    }

    public long updateSectionInfo(String time, int id, String instId) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
                menuSectionsDao.updateSectionInfo(time, id, instId);
            }
        });

        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
            }

            @Override
            public void onNext(@NotNull Long aLong) {
                x = aLong;
            }

            @Override
            public void onError(@NotNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
        return x;
    }

    public LiveData<Integer> getSectionId(String name) {
        return menuSectionsDao.getSectionId(name);
    }


    public void deleteAllInspectionData(String inst_id) {
        Log.i("DELETE", "deleteAllInspectionData: ");
        menuSectionsDao.deleteInstSelectionInfo(inst_id);
        menuSectionsDao.deleteAcademicEntity(inst_id);
        menuSectionsDao.deleteAcademicGradeEntity(inst_id);
        menuSectionsDao.deleteCallHealthInfoEntity();
        menuSectionsDao.deleteClassInfo(inst_id);
        menuSectionsDao.deleteCoCurricularEntity(inst_id);
        menuSectionsDao.deleteDietIssuesInfo(inst_id);
        menuSectionsDao.deleteDietListInfo(inst_id);
        menuSectionsDao.deleteEntitlementsInfo(inst_id);
        menuSectionsDao.deleteGeneralCommentsInfo(inst_id);
        menuSectionsDao.deleteGeneralInfo(inst_id);
        menuSectionsDao.deleteInfraStructureInfo(inst_id);
        menuSectionsDao.deleteInstMenuInfoEntity(inst_id);
        menuSectionsDao.deleteMedicalDetailsBean();
        menuSectionsDao.deleteMedicalInfoEntity(inst_id);
        menuSectionsDao.deletePlantsEntity();
        menuSectionsDao.deleteRegistersInfo(inst_id);
        menuSectionsDao.deleteStaff_Info(inst_id);
        menuSectionsDao.deleteStudAchievementEntity();
        menuSectionsDao.deletePhotos(inst_id);
    }

    public void deleteMenuData(String inst_id) {
        menuSectionsDao.deleteInstMenuInfoEntity(inst_id);
    }

}
