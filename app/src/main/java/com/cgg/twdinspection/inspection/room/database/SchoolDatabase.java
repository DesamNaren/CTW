package com.cgg.twdinspection.inspection.room.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cgg.twdinspection.inspection.room.Dao.AcademicInfoDao;
import com.cgg.twdinspection.inspection.room.Dao.ClassInfoDao;
import com.cgg.twdinspection.inspection.room.Dao.CocurricularDao;
import com.cgg.twdinspection.inspection.room.Dao.DietIssuesInfoDao;
import com.cgg.twdinspection.inspection.room.Dao.DistrictDao;
import com.cgg.twdinspection.inspection.room.Dao.EntitlementsInfoDao;
import com.cgg.twdinspection.inspection.room.Dao.GenCommentsInfoDao;
import com.cgg.twdinspection.inspection.room.Dao.GeneralInfoDao;
import com.cgg.twdinspection.inspection.room.Dao.InfraStructureInfoDao;
import com.cgg.twdinspection.inspection.room.Dao.InstSelectionDao;
import com.cgg.twdinspection.inspection.room.Dao.MedicalInfoDao;
import com.cgg.twdinspection.inspection.room.Dao.MenuSectionsDao;
import com.cgg.twdinspection.inspection.room.Dao.PhotoDao;
import com.cgg.twdinspection.inspection.room.Dao.PlantsInfoDao;
import com.cgg.twdinspection.inspection.room.Dao.RegistersInfoDao;
import com.cgg.twdinspection.inspection.room.Dao.SchoolSyncDao;
import com.cgg.twdinspection.inspection.room.Dao.StaffInfoDao;
import com.cgg.twdinspection.inspection.room.Dao.StudAchDao;
import com.cgg.twdinspection.inspection.source.academic_overview.AcademicEntity;
import com.cgg.twdinspection.inspection.source.academic_overview.AcademicGradeEntity;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.CoCurricularEntity;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.PlantsEntity;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.StudAchievementEntity;
import com.cgg.twdinspection.inspection.source.diet_issues.DietIssuesEntity;
import com.cgg.twdinspection.inspection.source.diet_issues.DietListEntity;
import com.cgg.twdinspection.inspection.source.dmv.SchoolDistrict;
import com.cgg.twdinspection.inspection.source.dmv.SchoolMandal;
import com.cgg.twdinspection.inspection.source.dmv.SchoolVillage;
import com.cgg.twdinspection.inspection.source.entitlements_distribution.EntitlementsEntity;
import com.cgg.twdinspection.inspection.source.general_comments.GeneralCommentsEntity;
import com.cgg.twdinspection.inspection.source.general_information.GeneralInfoEntity;
import com.cgg.twdinspection.inspection.source.infra_maintenance.InfraStructureEntity;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstMenuInfoEntity;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstSelectionInfo;
import com.cgg.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalDetailsBean;
import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;
import com.cgg.twdinspection.inspection.source.registers_upto_date.RegistersEntity;
import com.cgg.twdinspection.inspection.source.staff_attendance.StaffAttendanceEntity;
import com.cgg.twdinspection.inspection.source.student_attendence_info.StudAttendInfoEntity;
import com.cgg.twdinspection.inspection.source.upload_photo.UploadPhoto;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {StudAttendInfoEntity.class,
        StaffAttendanceEntity.class, MedicalInfoEntity.class, DietListEntity.class
        , DietIssuesEntity.class,
        InfraStructureEntity.class, AcademicEntity.class, EntitlementsEntity.class,
        RegistersEntity.class, GeneralCommentsEntity.class, GeneralInfoEntity.class, CallHealthInfoEntity.class
        , SchoolDistrict.class, SchoolMandal.class, SchoolVillage.class, MasterInstituteInfo.class, PlantsEntity.class
        , InstMenuInfoEntity.class, StudAchievementEntity.class, MedicalDetailsBean.class
        , CoCurricularEntity.class, UploadPhoto.class, AcademicGradeEntity.class
        , InstSelectionInfo.class}, version = 1, exportSchema = false)
public abstract class SchoolDatabase extends RoomDatabase {

    public abstract DistrictDao distDao();

    public abstract StudAchDao studAchDao();

    public abstract PlantsInfoDao plantsInfoDao();

    public abstract ClassInfoDao classInfoDao();

    public abstract CocurricularDao cocurricularDao();

    public abstract MedicalInfoDao medicalInfoDao();

    public abstract InfraStructureInfoDao infraStructureInfoDao();

    public abstract DietIssuesInfoDao dietIssuesInfoDao();

    public abstract AcademicInfoDao academicInfoDao();

    public abstract EntitlementsInfoDao entitlementsInfoDao();

    public abstract RegistersInfoDao registersInfoDao();

    public abstract GenCommentsInfoDao genCommentsInfoDao();

    public abstract StaffInfoDao staffInfoDao();

    public abstract GeneralInfoDao generalInfoDao();

    public abstract MenuSectionsDao menuSectionsDao();

    public abstract SchoolSyncDao schoolSyncDao();

    public abstract InstSelectionDao instSelectionDao();

    public abstract PhotoDao photoDao();

    private static SchoolDatabase INSTANCE1;

    public static SchoolDatabase getDatabase(final Context context) {
        if (INSTANCE1 == null) {
            synchronized (SchoolDatabase.class) {
                if (INSTANCE1 == null) {
                    INSTANCE1 = Room.databaseBuilder(context.getApplicationContext(),
                            SchoolDatabase.class, "Schools.db")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
//                            .createFromFile(f)
                            .createFromAsset("database/Schools.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE1;
    }
}
