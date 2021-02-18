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

    public LiveData<GeneralInfoEntity> getGeneralInfo() {
        return menuSectionsDao.getGeneralInfo();
    }

    public LiveData<List<StudAttendInfoEntity>> getStudAttendInfo() {
        return menuSectionsDao.getStudAttendInfo();
    }

    public LiveData<List<StaffAttendanceEntity>> getStaffInfo() {
        return menuSectionsDao.getStaffAttendInfo();
    }

    public LiveData<MedicalInfoEntity> getMedicalInfo() {
        return menuSectionsDao.getMedicalInfo();
    }

    public LiveData<DietIssuesEntity> getDietInfo() {
        return menuSectionsDao.getDietInfo();
    }

    public LiveData<InfraStructureEntity> getInfrastructureInfo() {
        return menuSectionsDao.getInfraInfo();
    }

    public LiveData<AcademicEntity> getAcademicInfo() {
        return menuSectionsDao.getAcademicInfo();
    }

    public LiveData<CoCurricularEntity> getCocurricularInfo() {
        return menuSectionsDao.getCocurricularInfo();
    }

    public LiveData<EntitlementsEntity> getEntitlementInfo() {
        return menuSectionsDao.getEntitlementInfo();
    }

    public LiveData<RegistersEntity> getRegistersInfo() {
        return menuSectionsDao.getRegisterInfo();
    }

    public LiveData<GeneralCommentsEntity> getGeneralCommentsInfo() {
        return menuSectionsDao.getGeneralCommentsInfo();
    }

    public LiveData<List<InstMenuInfoEntity>> getSections() {
        return menuSectionsDao.getSections();
    }

    public LiveData<Integer> getMenuRecordsCount() {
        return menuSectionsDao.getMenuRecordsCount();
    }

    public long insertMenuSections(List<InstMenuInfoEntity> menuInfoEntities) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Long> emitter) throws Exception {
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


    public void deleteAllInspectionData() {
        Log.i("DELETE", "deleteAllInspectionData: ");
        menuSectionsDao.deleteInstSelectionInfo();
        menuSectionsDao.deleteAcademicEntity();
        menuSectionsDao.deleteAcademicGradeEntity();
        menuSectionsDao.deleteCallHealthInfoEntity();
        menuSectionsDao.deleteClassInfo();
        menuSectionsDao.deleteCoCurricularEntity();
        menuSectionsDao.deleteDietIssuesInfo();
        menuSectionsDao.deleteDietListInfo();
        menuSectionsDao.deleteEntitlementsInfo();
        menuSectionsDao.deleteGeneralCommentsInfo();
        menuSectionsDao.deleteGeneralInfo();
        menuSectionsDao.deleteInfraStructureInfo();
        menuSectionsDao.deleteInstMenuInfoEntity();
        menuSectionsDao.deleteMedicalDetailsBean();
        menuSectionsDao.deleteMedicalInfoEntity();
        menuSectionsDao.deletePlantsEntity();
        menuSectionsDao.deleteRegistersInfo();
        menuSectionsDao.deleteStaff_Info();
        menuSectionsDao.deleteStudAchievementEntity();
        menuSectionsDao.deletePhotos();
    }

    public void deleteMenuData() {
        menuSectionsDao.deleteInstMenuInfoEntity();
    }

}
