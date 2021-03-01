package com.cgg.twdinspection.inspection.room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cgg.twdinspection.inspection.source.inst_menu_info.InstLatestTimeInfo;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstSelectionInfo;

/**
 * The Room Magic is in this file, where you map file_provider_paths Java method call to an SQL query.
 * <p>
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */
//

@Dao
public interface InstSelectionDao {

    @Query("DELETE FROM inst_selection_info where inst_id LIKE :instId")
    void deleteInstSelection(String instId);

    @Insert
    void insertInstSelection(InstSelectionInfo instSelectionInfo);

    @Query("SELECT * from inst_selection_info where inst_id LIKE :instId")
    LiveData<InstSelectionInfo> getInstSelection(String instId);

    @Query("SELECT randomNo from inst_selection_info where inst_id LIKE :instid")
    LiveData<String> getRandomNo(String instid);

    @Query("SELECT COUNT(*) FROM inst_selection_info")
    int instSelCount();
}
