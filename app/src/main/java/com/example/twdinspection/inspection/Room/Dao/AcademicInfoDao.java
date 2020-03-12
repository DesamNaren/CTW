package com.example.twdinspection.inspection.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.twdinspection.inspection.source.academic_overview.AcademicEntity;
import com.example.twdinspection.inspection.source.academic_overview.AcademicGradeEntity;

import java.util.List;

@Dao
public interface AcademicInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAcademicInfo(AcademicEntity AcademicEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAcademicGradeInfo(List<AcademicGradeEntity> academicGradeEntities);


    @Query("SELECT * from academic_grade where institute_id LIKE :inst_id")
    LiveData<List<AcademicGradeEntity>> getAcademicGradeInfo(String inst_id);
}
