package com.example.twdinspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.twdinspection.source.DistManVillage.Districts;
import com.example.twdinspection.source.DistManVillage.Mandals;

import java.util.List;

/**
 * The Room Magic is in this file, where you map a Java method call to an SQL query.
 * <p>
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */

@Dao
public interface DistrictDao {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from Districts")
    LiveData<List<Districts>> getDistricts();

    @Query("SELECT * from Mandals where dist_id LIKE :dist_id")
    LiveData<List<Mandals>> getMandals(int dist_id);

    @Query("SELECT dist_id from Districts where dist_name LIKE :dist_name")
    LiveData<Integer> getDistId(String dist_name);

    @Query("SELECT count(*) from Districts")
    int getCount();
}
