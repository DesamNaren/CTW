package com.cgg.twdinspection.engineering_works.room.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.engineering_works.room.dao.GrantSchemeDao;
import com.cgg.twdinspection.engineering_works.room.database.EngWorksDatabase;
import com.cgg.twdinspection.engineering_works.source.GrantScheme;

import java.util.List;

public class GrantSchemeRepository {

    GrantSchemeDao grantSchemeDao;
    private LiveData<List<GrantScheme>> schemesLiveData = new MutableLiveData<>();
    private int x;

    public GrantSchemeRepository(Application application) {
        EngWorksDatabase db = EngWorksDatabase.getDatabase(application);
        grantSchemeDao = db.schemeDao();
    }
    public LiveData<List<GrantScheme>> getGrantSchemes() {
        if(schemesLiveData !=null){
            schemesLiveData = grantSchemeDao.getGrantSchemes();
        }
        return schemesLiveData;
    }

    public int insertSchemes(List<GrantScheme> schemes){
        new InsertSchemesAsyncTask(schemes).execute();
        return x;
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertSchemesAsyncTask extends AsyncTask<Void, Void, Integer> {
        List<GrantScheme> grantSchemes;

        InsertSchemesAsyncTask(List<GrantScheme> grantSchemes) {
            this.grantSchemes = grantSchemes;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            grantSchemeDao.insertSchemes(grantSchemes);
            return grantSchemeDao.schemesCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            x=integer;
        }
    }
    public LiveData<Integer> getSchemeId(String schemeName) {
        return grantSchemeDao.getSchemeId(schemeName);
    }
}
