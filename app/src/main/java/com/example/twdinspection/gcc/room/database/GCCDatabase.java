package com.example.twdinspection.gcc.room.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.twdinspection.gcc.room.dao.GCCDao;
import com.example.twdinspection.gcc.room.dao.GCCSyncDao;
import com.example.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.example.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.example.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.example.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.example.twdinspection.gcc.source.suppliers.punit.PUnits;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {DivisionsInfo.class, DRDepots.class, DrGodowns.class, MFPGoDowns.class, PUnits.class},
        version = 1, exportSchema = false)
public abstract class GCCDatabase extends RoomDatabase {
    public abstract GCCDao gccDao();
    public abstract GCCSyncDao gccSyncDao();


    private static GCCDatabase INSTANCE;

    public static GCCDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GCCDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GCCDatabase.class, "GCC.db")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
//                            .createFromFile(new File("database/districts.json"))
                            .createFromAsset("database/GCC.db")
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
