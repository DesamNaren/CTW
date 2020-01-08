package com.example.twdinspection.schemes.room.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.twdinspection.schemes.room.dao.InspectionRemarksDao;
import com.example.twdinspection.schemes.room.database.SchemesDatabase;
import com.example.twdinspection.schemes.source.remarks.InspectionRemarksEntity;

import java.util.List;

public class InspectionRemarksRepository {

    private InspectionRemarksDao remarksDao;

    public InspectionRemarksRepository(Context application) {
        SchemesDatabase db = SchemesDatabase.getDatabase(application);
        remarksDao = db.inspectionRemarksDao();
    }

    public LiveData<List<InspectionRemarksEntity>> getInspRemarks() {
        return remarksDao.getInspectionRemarks();
    }


    public LiveData<String> getRemarkId(String remType){
        return remarksDao.getRemarkId(remType);
    }
}
