package com.example.twdinspection.inspection.Room.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.twdinspection.inspection.Room.Dao.MedicalInfoDao;
import com.example.twdinspection.inspection.Room.Dao.StudAchDao;
import com.example.twdinspection.inspection.Room.database.DistrictDatabase;
import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StudAchievementsRepository {

    public StudAchDao studAchDao;
    private String tag= StudAchievementsRepository.class.getSimpleName();

    public StudAchievementsRepository(Context application) {
        DistrictDatabase db = DistrictDatabase.getDatabase(application);
        studAchDao = db.studAchDao();

    }

    public LiveData<List<StudAchievementEntity>> getCallListLiveData() {
            return studAchDao.getStudAchievements();
    }
}
