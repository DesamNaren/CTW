package com.example.twdinspection.inspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.twdinspection.inspection.source.GeneralInformation.InstitutesEntity;
import com.example.twdinspection.inspection.source.dmv.SchoolDistrict;
import com.example.twdinspection.inspection.source.dmv.SchoolMandal;
import com.example.twdinspection.inspection.source.dmv.SchoolVillage;

import java.util.List;

/**
 * The Room Magic is in this file, where you map file_provider_paths Java method call to an SQL query.
 * <p>
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */

@Dao
public interface DistrictDao {
    @Query("SELECT * from SchoolDistrict")
    LiveData<List<SchoolDistrict>> getDistricts();

    @Query("SELECT * from SchoolMandal where distId LIKE :dist_id")
    LiveData<List<SchoolMandal>> getMandals(int dist_id);

    @Query("SELECT * from SchoolVillage where mandalID LIKE :mandalId AND distId LIKE :distId")
    LiveData<List<SchoolVillage>> getVillages(int mandalId, int distId);

    @Query("SELECT distId from SchoolDistrict where distName LIKE :dist_name")
    LiveData<String> getDistId(String dist_name);

    @Query("SELECT mandalID from SchoolMandal where mandalName LIKE :mandalName AND distId LIKE :distId")
    LiveData<String> getMandalId(String mandalName, int distId);

    @Query("SELECT villageID from SchoolVillage where villageName LIKE :villageName AND mandalID LIKE :manId AND distId LIKE :distId")
    LiveData<String> getVillageId(String villageName, int manId,  int distId);

    @Query("SELECT Inst_Id from InstInfo where Inst_Name LIKE :inst_name")
    LiveData<String> getInstId(String inst_name);

    @Query("SELECT * from InstInfo")
    LiveData<List<InstitutesEntity>> getInstitutes();

    @Query("SELECT count(*) from SchoolDistrict")
    int getCount();
}
