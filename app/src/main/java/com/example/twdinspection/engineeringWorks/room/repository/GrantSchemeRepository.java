package com.example.twdinspection.engineeringWorks.room.repository;

import android.app.Application;

import com.example.twdinspection.engineeringWorks.room.dao.GrantSchemeDao;
import com.example.twdinspection.engineeringWorks.room.database.EngWorksDatabase;
import com.example.twdinspection.gcc.room.database.GCCDatabase;

public class GrantSchemeRepository {

    GrantSchemeDao grantSchemeDao;

    public GrantSchemeRepository(Application application) {
        EngWorksDatabase db = EngWorksDatabase.getDatabase(application);
        grantSchemeDao = db.grantSchemeDao();
    }

}
