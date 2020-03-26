package com.example.twdinspection.engineering_works.room.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.twdinspection.engineering_works.room.dao.GrantSchemeDao;
import com.example.twdinspection.engineering_works.room.dao.SectorsDao;
import com.example.twdinspection.engineering_works.source.GrantSchemeEntity;
import com.example.twdinspection.engineering_works.source.SectorsEntity;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {SectorsEntity.class},
        version = 1, exportSchema = false)
public abstract class EngWorksDatabase extends RoomDatabase {

    private static EngWorksDatabase INSTANCE;

    public abstract SectorsDao sectorsDao();

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
