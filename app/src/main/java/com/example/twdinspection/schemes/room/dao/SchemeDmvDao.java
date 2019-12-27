package com.example.twdinspection.schemes.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.twdinspection.inspection.source.DistManVillage.Districts;
import com.example.twdinspection.inspection.source.DistManVillage.Mandals;
import com.example.twdinspection.inspection.source.DistManVillage.Villages;
import com.example.twdinspection.inspection.source.GeneralInformation.InstitutesEntity;

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
public interface SchemeDmvDao {
    @Query("SELECT * from Districts")
    LiveData<List<Districts>> getDistricts();

    @Query("SELECT * from Mandals where dist_id LIKE :dist_id")
    LiveData<List<Mandals>> getMandals(int dist_id);

    @Query("SELECT * from Villages where mandal_id LIKE :mandalId AND dist_id LIKE :distId")
    LiveData<List<Villages>> getVillages(int mandalId, int distId);

    @Query("SELECT dist_id from Districts where dist_name LIKE :dist_name")
    LiveData<Integer> getDistId(String dist_name);

    @Query("SELECT mandal_id from Mandals where mandal_name LIKE :mandalName AND dist_id LIKE :distId")
    LiveData<Integer> getMandalId(String mandalName, int distId);

    @Query("SELECT village_id from Villages where village_name LIKE :villageName AND mandal_id LIKE :manId AND dist_id LIKE :distId")
    LiveData<Integer> getVillageId(String villageName, int manId, int distId);

    @Query("SELECT Inst_Id from InstInfo where Inst_Name LIKE :inst_name")
    LiveData<String> getInstId(String inst_name);

    @Query("SELECT * from InstInfo")
    LiveData<List<InstitutesEntity>> getInstitutes();

    @Query("SELECT count(*) from Districts")
    int getCount();
}
