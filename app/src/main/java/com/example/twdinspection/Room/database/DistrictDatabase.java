package com.example.twdinspection.Room.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.twdinspection.Room.Dao.ClassInfoDao;
import com.example.twdinspection.Room.Dao.DistrictDao;
import com.example.twdinspection.source.DistManVillage.Districts;
import com.example.twdinspection.source.DistManVillage.Mandals;
import com.example.twdinspection.source.DistManVillage.Villages;
import com.example.twdinspection.source.GeneralInformation.InstitutesEntity;
import com.example.twdinspection.source.studentAttendenceInfo.StudAttendInfoEntity;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {Districts.class, Mandals.class, Villages.class, StudAttendInfoEntity.class, InstitutesEntity.class}, version = 1, exportSchema = false)
public abstract class DistrictDatabase extends RoomDatabase {

    public abstract DistrictDao distDao();
    public abstract ClassInfoDao classInfoDao();

    private static DistrictDatabase INSTANCE;

    public static DistrictDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DistrictDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DistrictDatabase.class, "TWD_NEW.db")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
//                            .createFromFile(new File("database/districts.json"))
                            .createFromAsset("database/TWD_NEW.db")
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
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

}
