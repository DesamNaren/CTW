package com.example.twdinspection.inspection.Room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.twdinspection.inspection.Room.Dao.StaffInfoDao;
import com.example.twdinspection.inspection.Room.database.DistrictDatabase;
import com.example.twdinspection.inspection.source.staffAttendance.StaffAttendanceEntity;

import java.util.List;

public class StaffInfoRepository {

    public StaffInfoDao staffInfoDao;
    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public StaffInfoRepository(Application application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        staffInfoDao = db.staffInfoDao();

    }

    public LiveData<List<StaffAttendanceEntity>> getStaffInfoList(String inst_id) {
        LiveData<List<StaffAttendanceEntity>> classIdList= staffInfoDao.getStaffInfoList(inst_id);
        return classIdList;
    }
}
