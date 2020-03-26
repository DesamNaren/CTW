package com.example.twdinspection.engineering_works.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.twdinspection.engineering_works.source.GrantScheme;

import java.util.List;

@Dao
public interface GrantSchemeDao {

    @Insert
    void insertSchemes(List<GrantScheme> grantSchemes);

    @Query("SELECT * from grantSchemes")
    LiveData<List<GrantScheme>> getGrantSchemes();

    @Query("SELECT COUNT(*) FROM grantSchemes")
    int schemesCount();
}
