package com.example.twdinspection.Room.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.twdinspection.Room.Dao.DistrictDao;
import com.example.twdinspection.source.DistManVillage.DistrictEntity;
import com.example.twdinspection.ui.BasicDetailsActivity;

import java.io.File;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {DistrictEntity.class}, version = 2,  exportSchema = false)
public abstract class DistrictDatabase extends RoomDatabase {

    public abstract DistrictDao distDao();

    private static DistrictDatabase INSTANCE;

    public static DistrictDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DistrictDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DistrictDatabase.class, "TWDDatabase.db")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
//                            .createFromFile(new File("database/districts.json"))
                            .createFromAsset("database/TWD.db")
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
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
        }
    };

}
