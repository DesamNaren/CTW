package com.example.twdinspection.schemes.room.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.twdinspection.schemes.room.dao.InspectionRemarksDao;
import com.example.twdinspection.schemes.room.dao.SchemeDmvDao;
import com.example.twdinspection.schemes.room.dao.SchemeSyncDao;
import com.example.twdinspection.schemes.room.dao.SchemesInfoDao;
import com.example.twdinspection.schemes.source.DMV.SchemeDistrict;
import com.example.twdinspection.schemes.source.DMV.SchemeMandal;
import com.example.twdinspection.schemes.source.DMV.SchemeVillage;
import com.example.twdinspection.schemes.source.finyear.FinancialYrsEntity;
import com.example.twdinspection.schemes.source.InspectionRemarksEntity;
import com.example.twdinspection.schemes.source.SchemesInfoEntity;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {SchemesInfoEntity.class, FinancialYrsEntity.class,
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
                            SchemesDatabase.class, "schemesDb.db")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
//                            .createFromFile(new File("database/districts.json"))
                            .createFromAsset("database/schemesDb.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     */
    private static Callback sRoomDatabaseCallback = new Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

}
