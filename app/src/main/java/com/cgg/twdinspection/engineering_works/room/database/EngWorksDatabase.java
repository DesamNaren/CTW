package com.cgg.twdinspection.engineering_works.room.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cgg.twdinspection.engineering_works.room.dao.EngWorksSyncDao;
import com.cgg.twdinspection.engineering_works.room.dao.GrantSchemeDao;
import com.cgg.twdinspection.engineering_works.room.dao.SectorsDao;
import com.cgg.twdinspection.engineering_works.room.dao.WorksDao;
import com.cgg.twdinspection.engineering_works.source.GrantScheme;
import com.cgg.twdinspection.engineering_works.source.SectorsEntity;
import com.cgg.twdinspection.engineering_works.source.WorkDetail;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {SectorsEntity.class, GrantScheme.class, WorkDetail.class},
        version = 2, exportSchema = false)
public abstract class EngWorksDatabase extends RoomDatabase {

    private static EngWorksDatabase INSTANCE;

    public abstract SectorsDao sectorsDao();
    public abstract WorksDao worksDao();
    public abstract GrantSchemeDao schemeDao();
    public abstract EngWorksSyncDao engWorksSyncDao();

    public static EngWorksDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EngWorksDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EngWorksDatabase.class, "EngWorks.db")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
//                            .createFromFile(new File("database/districts.json"))
                            .createFromAsset("database/EngWorks.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
