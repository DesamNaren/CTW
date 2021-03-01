package com.cgg.twdinspection.inspection.room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cgg.twdinspection.inspection.source.inst_menu_info.InstLatestTimeInfo;

import java.util.List;

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
public interface InstLastestTimeDao {

    @Insert
    void insertInstLatestTime(InstLatestTimeInfo instLatestTimeInfo);

    @Query("Update inst_latest_time_info set  inst_time=:time where  inst_id=:instId")
    void updateTimeInfo(String time, String instId);

    @Query("SELECT * from inst_latest_time_info")
    LiveData<List<InstLatestTimeInfo>> getLatestTimeInfo();

    @Query("DELETE  from inst_latest_time_info where  inst_id=:instId")
    void deleteTimeInfo(String instId);
}
