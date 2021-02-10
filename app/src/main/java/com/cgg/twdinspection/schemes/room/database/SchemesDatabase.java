package com.cgg.twdinspection.schemes.room.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cgg.twdinspection.schemes.room.dao.InspectionRemarksDao;
import com.cgg.twdinspection.schemes.room.dao.SchemeDmvDao;
import com.cgg.twdinspection.schemes.room.dao.SchemeSyncDao;
import com.cgg.twdinspection.schemes.room.dao.SchemesInfoDao;
import com.cgg.twdinspection.schemes.source.dmv.SchemeDistrict;
import com.cgg.twdinspection.schemes.source.dmv.SchemeMandal;
import com.cgg.twdinspection.schemes.source.dmv.SchemeVillage;
import com.cgg.twdinspection.schemes.source.finyear.FinancialYearsEntity;
import com.cgg.twdinspection.schemes.source.remarks.InspectionRemarksEntity;
import com.cgg.twdinspection.schemes.source.schemes.SchemeEntity;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {SchemeEntity.class, FinancialYearsEntity.class,
        InspectionRemarksEntity.class, SchemeDistrict.class, SchemeMandal.class, SchemeVillage.class},
        version = 1, exportSchema = false)
public abstract class SchemesDatabase extends RoomDatabase {

    public abstract SchemesInfoDao schemesInfoDao();

    public abstract SchemeDmvDao schemeDmvDao();

    public abstract SchemeSyncDao schemeSyncDao();

    public abstract InspectionRemarksDao inspectionRemarksDao();


    private static SchemesDatabase INSTANCE;

    public static SchemesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SchemesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SchemesDatabase.class, "schemes.db")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
//                            .createFromFile(new File("database/districts.json"))
                            .createFromAsset("database/schemes.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
