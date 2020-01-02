package com.example.twdinspection.schemes.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.twdinspection.inspection.source.DistManVillage.Districts;
import com.example.twdinspection.inspection.source.DistManVillage.Mandals;
import com.example.twdinspection.inspection.source.DistManVillage.Villages;
import com.example.twdinspection.inspection.source.GeneralInformation.InstitutesEntity;
import com.example.twdinspection.schemes.source.FinancialYrsEntity;
import com.example.twdinspection.schemes.source.SchemesInfoEntity;

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
public interface SchemesInfoDao {
    @Query("SELECT * from schemesInfo")
    LiveData<List<SchemesInfoEntity>> getSchemesInfo();

    @Query("SELECT * from financialyears")
    LiveData<List<FinancialYrsEntity>> getFinancialYrs();
}
