package com.cgg.twdinspection.gcc.room.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cgg.twdinspection.gcc.room.dao.GCCDao;
import com.cgg.twdinspection.gcc.room.dao.GCCDaoOffline;
import com.cgg.twdinspection.gcc.room.dao.GCCSyncDao;
import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.cgg.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.cgg.twdinspection.gcc.source.suppliers.lpg.LPGSupplierInfo;
import com.cgg.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolSupplierInfo;
import com.cgg.twdinspection.gcc.source.suppliers.punit.PUnits;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {DivisionsInfo.class, DRDepots.class, DrGodowns.class, MFPGoDowns.class,
        PUnits.class, PetrolSupplierInfo.class, LPGSupplierInfo.class, GccOfflineEntity.class},
        version = 3, exportSchema = false)
public abstract class GCCDatabase extends RoomDatabase {
    public abstract GCCDao gccDao();

    public abstract GCCSyncDao gccSyncDao();

    public abstract GCCDaoOffline gccOfflineDao();

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
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
