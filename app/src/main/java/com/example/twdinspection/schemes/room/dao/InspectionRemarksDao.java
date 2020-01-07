package com.example.twdinspection.schemes.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.twdinspection.schemes.source.remarks.InspectionRemarksEntity;

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
public interface InspectionRemarksDao {


    @Query("SELECT * from InspectionRemarksEntity")
    LiveData<List<InspectionRemarksEntity>> getInspectionRemarks();

    @Query("SELECT remark_id from InspectionRemarksEntity where remark_type LIKE :rem_type")
    LiveData<String> getRemarkId(String rem_type);
}
