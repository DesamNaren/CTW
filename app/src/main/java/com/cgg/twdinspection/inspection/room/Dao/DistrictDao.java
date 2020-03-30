package com.cgg.twdinspection.inspection.room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.cgg.twdinspection.inspection.source.dmv.SchoolDistrict;
import com.cgg.twdinspection.inspection.source.dmv.SchoolMandal;
import com.cgg.twdinspection.inspection.source.dmv.SchoolVillage;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;

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
    @Query("SELECT * from districts_info")
    LiveData<List<SchoolDistrict>> getDistricts();

    @Query("SELECT * from mandal_info where distId LIKE :dist_id")
    LiveData<List<SchoolMandal>> getMandals(int dist_id);

    @Query("SELECT * from village_info where mandalID LIKE :mandalId AND distId LIKE :distId")
    LiveData<List<SchoolVillage>> getVillages(int mandalId, int distId);

    @Query("SELECT distId from districts_info where distName LIKE :dist_name")
    LiveData<String> getDistId(String dist_name);

    @Query("SELECT * from master_inst_info where instId LIKE :instId")
    LiveData<MasterInstituteInfo> getInstituteInfo(String instId);

    @Query("SELECT instId from master_inst_info where instName LIKE :inst_name and districtID LIKE :districtId")
    LiveData<Integer> getInstId(String inst_name, int districtId);

    @Query("SELECT * from master_inst_info where districtID LIKE :districtId")
    LiveData<List<MasterInstituteInfo>> getInstitutes(int districtId);

    @Query("SELECT count(*) from districts_info")
    int getCount();
}
