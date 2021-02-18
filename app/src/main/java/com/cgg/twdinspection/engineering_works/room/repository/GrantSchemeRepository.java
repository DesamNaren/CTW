package com.cgg.twdinspection.engineering_works.room.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.engineering_works.room.dao.GrantSchemeDao;
import com.cgg.twdinspection.engineering_works.room.database.EngWorksDatabase;
import com.cgg.twdinspection.engineering_works.source.GrantScheme;

import java.util.List;

public class GrantSchemeRepository {

    private final GrantSchemeDao grantSchemeDao;
    private LiveData<List<GrantScheme>> schemesLiveData = new MutableLiveData<>();
    private int x;

    public GrantSchemeRepository(Application application) {
        EngWorksDatabase db = EngWorksDatabase.getDatabase(application);
        grantSchemeDao = db.schemeDao();
    }

    public LiveData<List<GrantScheme>> getGrantSchemes() {
        if (schemesLiveData != null) {
            schemesLiveData = grantSchemeDao.getGrantSchemes();
        }
        return schemesLiveData;
    }

    public int insertSchemes(List<GrantScheme> schemes) {
        TWDApplication.getExecutorService().execute(() -> {
            grantSchemeDao.insertSchemes(schemes);
            x = grantSchemeDao.schemesCount();
            //Background work here
        });
        return x;
    }

    public LiveData<Integer> getSchemeId(String schemeName) {
        return grantSchemeDao.getSchemeId(schemeName);
    }
}
