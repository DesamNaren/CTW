package com.example.twdinspection.Room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.Room.Dao.ClassInfoDao;
import com.example.twdinspection.Room.database.DistrictDatabase;
import com.example.twdinspection.source.DistManVillage.Districts;
import com.example.twdinspection.source.DistManVillage.Mandals;
import com.example.twdinspection.source.studentAttendenceInfo.StudAttendInfoEntity;

import java.util.List;

public class ClassInfoRepository {

    public ClassInfoDao classInfoDao;
    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ClassInfoRepository(Application application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        classInfoDao = db.classInfoDao();

    }

    public LiveData<List<StudAttendInfoEntity>> getClassIdsList(String inst_id) {
        LiveData<List<StudAttendInfoEntity>> classIdList=classInfoDao.getClassIdList(inst_id);
        return classIdList;
    }
}
