package com.cgg.twdinspection.engineering_works.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cgg.twdinspection.engineering_works.source.SectorsEntity;
import com.cgg.twdinspection.engineering_works.source.WorkDetail;

import java.util.List;

@Dao
public interface WorksDao {
    @Insert
    void insertWorks(List<WorkDetail> workDetails);

    @Query("SELECT * from workdetail where distId LIKE :distId AND mandId LIKE :mandalId")
    LiveData<List<WorkDetail>> getWorkDetails(String distId,String mandalId);

    @Query("SELECT COUNT(*) FROM workdetail")
    LiveData<Integer> getWorksCount();

    @Query("SELECT COUNT(*) FROM workdetail")
    int getInsertedWorksCount();

    @Query("SELECT sectorId from sectorsentity where sectorName LIKE :sectorName")
    LiveData<Integer> getSectorId(String sectorName);

    @Query("SELECT DISTINCT distName from WorkDetail")
    LiveData<List<String>> getDistricts();

    @Query("SELECT DISTINCT mandName from WorkDetail where distId LIKE :distId")
    LiveData<List<String>> getMandalList(String distId);

    @Query("SELECT distId from WorkDetail where distName LIKE :distName")
    LiveData<String> getDistrictId(String distName);

    @Query("SELECT mandId from WorkDetail where mandName LIKE :mandName")
    LiveData<String> getMandalId(String mandName);

}
