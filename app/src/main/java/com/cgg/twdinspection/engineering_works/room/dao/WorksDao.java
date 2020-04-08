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

    @Query("SELECT * from workdetail where workId LIKE :workId")
    LiveData<WorkDetail> getWorkDetails(int workId);

    @Query("SELECT COUNT(*) FROM workdetail")
    LiveData<Integer> getWorksCount();

    @Query("SELECT COUNT(*) FROM workdetail")
    int getInsertedWorksCount();

    @Query("SELECT sectorId from sectorsentity where sectorName LIKE :sectorName")
    LiveData<Integer> getSectorId(String sectorName);

}
