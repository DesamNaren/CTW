package com.example.twdinspection.engineering_works.room.repository;

import android.app.Application;

import com.example.twdinspection.engineering_works.room.dao.GrantSchemeDao;
import com.example.twdinspection.engineering_works.room.database.EngWorksDatabase;

public class GrantSchemeRepository {

    GrantSchemeDao grantSchemeDao;

    public GrantSchemeRepository(Application application) {
        EngWorksDatabase db = EngWorksDatabase.getDatabase(application);
        grantSchemeDao = db.grantSchemeDao();
    }

}