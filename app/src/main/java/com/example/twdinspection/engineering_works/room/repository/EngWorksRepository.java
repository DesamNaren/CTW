//package com.example.twdinspection.engineeringWorks.room.repository;
//
//import android.annotation.SuppressLint;
//import android.app.Application;
//import android.os.AsyncTask;
//
//import com.example.twdinspection.engineeringWorks.room.dao.SectorsDao;
//import com.example.twdinspection.engineering_works.source.GrantScheme;
//import com.example.twdinspection.engineeringWorks.source.sectors.SectorsEntity;
//import com.example.twdinspection.gcc.interfaces.GCCDivisionInterface;
//
//import java.util.List;
//
//public class EngWorksRepository {
//
//    SectorsDao sectorsDao;
//    public EngWorksRepository(Application application) {
////        EngWorksDatabase database=EngWorksDatabase.getDatabase(application);
////        sectorsDao=database.sectorsDao();
//    }
//
//    public void insertSectors(List<SectorsEntity> sectorsEntities){
//        new InsertSectorsAsyncTask(sectorsEntities).execute();
//    }
//
//    public void insertSchemes(List<GrantScheme> grantSchemes){
//
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private class InsertSectorsAsyncTask extends AsyncTask<Void, Void, Integer> {
//        List<SectorsEntity> sectorsEntities;
//        GCCDivisionInterface dmvInterface;
//
//        InsertSectorsAsyncTask(List<SectorsEntity> sectorsEntities) {
//            this.sectorsEntities = sectorsEntities;
//            this.dmvInterface = dmvInterface;
//        }
//
//        @Override
//        protected Integer doInBackground(Void... voids) {
//            sectorsDao.insertSectors(sectorsEntities);
//            return sectorsDao.sectorCount();
//        }
//
//        @Override
//        protected void onPostExecute(Integer integer) {
//            super.onPostExecute(integer);
////            dmvInterface.divisionCount(integer);
//        }
//    }
//
//}
