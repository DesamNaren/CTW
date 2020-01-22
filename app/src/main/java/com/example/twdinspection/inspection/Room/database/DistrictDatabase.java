package com.example.twdinspection.inspection.Room.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.twdinspection.inspection.Room.Dao.AcademicInfoDao;
import com.example.twdinspection.inspection.Room.Dao.ClassInfoDao;
import com.example.twdinspection.inspection.Room.Dao.CocurricularDao;
import com.example.twdinspection.inspection.Room.Dao.DietIssuesInfoDao;
import com.example.twdinspection.inspection.Room.Dao.DistrictDao;
import com.example.twdinspection.inspection.Room.Dao.EntitlementsInfoDao;
import com.example.twdinspection.inspection.Room.Dao.GenCommentsInfoDao;
import com.example.twdinspection.inspection.Room.Dao.GeneralInfoDao;
import com.example.twdinspection.inspection.Room.Dao.InfraStructureInfoDao;
import com.example.twdinspection.inspection.Room.Dao.MedicalInfoDao;
import com.example.twdinspection.inspection.Room.Dao.MenuSectionsDao;
import com.example.twdinspection.inspection.Room.Dao.PlantsInfoDao;
import com.example.twdinspection.inspection.Room.Dao.RegistersInfoDao;
import com.example.twdinspection.inspection.Room.Dao.SchoolSyncDao;
import com.example.twdinspection.inspection.Room.Dao.StaffInfoDao;
import com.example.twdinspection.inspection.Room.Dao.StudAchDao;
import com.example.twdinspection.inspection.source.AcademicOverview.AcademicOveriewEntity;
import com.example.twdinspection.inspection.source.DiestIssues.DietIssuesEntity;
import com.example.twdinspection.inspection.source.EntitlementsDistribution.EntitlementsEntity;
import com.example.twdinspection.inspection.source.GeneralComments.GeneralCommentsEntity;
import com.example.twdinspection.inspection.source.GeneralInformation.GeneralInfoEntity;
import com.example.twdinspection.inspection.source.InfrastructureAndMaintenance.InfraStructureEntity;
import com.example.twdinspection.inspection.source.MedicalDetailsBean;
import com.example.twdinspection.inspection.source.RegistersUptoDate.RegistersEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.PlantsEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.example.twdinspection.inspection.source.dmv.SchoolDistrict;
import com.example.twdinspection.inspection.source.dmv.SchoolMandal;
import com.example.twdinspection.inspection.source.dmv.SchoolVillage;
import com.example.twdinspection.inspection.source.instMenuInfo.InstMenuInfoEntity;
import com.example.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;
import com.example.twdinspection.inspection.source.staffAttendance.StaffAttendanceEntity;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {StudAttendInfoEntity.class,
        StaffAttendanceEntity.class, MedicalInfoEntity.class, DietIssuesEntity.class,
        InfraStructureEntity.class, AcademicOveriewEntity.class, EntitlementsEntity.class,
        RegistersEntity.class, GeneralCommentsEntity.class, GeneralInfoEntity.class, CallHealthInfoEntity.class
        , SchoolDistrict.class, SchoolMandal.class, SchoolVillage.class, MasterInstituteInfo.class, PlantsEntity.class
        , InstMenuInfoEntity.class, StudAchievementEntity.class, MedicalDetailsBean.class}, version = 1, exportSchema = false)
public abstract class DistrictDatabase extends RoomDatabase {

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

    private static DistrictDatabase INSTANCE1;

    public static DistrictDatabase getDatabase(final Context context) {
        if (INSTANCE1 == null) {
            synchronized (DistrictDatabase.class) {
                if (INSTANCE1 == null) {
                    INSTANCE1 = Room.databaseBuilder(context.getApplicationContext(),
                            DistrictDatabase.class, "TWD_NEW.db")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
//                            .createFromFile(new File("database/districts.json"))
                            .createFromAsset("database/TWD_NEW.db")
                            .build();
                }
            }
        }
        return INSTANCE1;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

}
