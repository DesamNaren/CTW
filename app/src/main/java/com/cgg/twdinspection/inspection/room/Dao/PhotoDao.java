package com.cgg.twdinspection.inspection.room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolSupplierInfo;
import com.cgg.twdinspection.inspection.source.upload_photo.UploadPhoto;

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
public interface PhotoDao {
    @Query("SELECT * from photos where institute_id LIKE :inst_id")
    LiveData<List<UploadPhoto>> getPhotos(String inst_id);

    @Query("SELECT * from photos where photo_name=:name and institute_id LIKE :inst_id")
    LiveData<UploadPhoto> getPhotoData(String name,String inst_id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPhotos(List<UploadPhoto> uploadPhotos);

    @Query("Delete from photos where institute_id LIKE:inst_id")
    void deletePhotos(String inst_id);
}
