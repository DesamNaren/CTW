package com.cgg.twdinspection.inspection.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.databinding.ActivityAcademicBinding;
import com.cgg.twdinspection.inspection.room.repository.AcademicRepository;
import com.cgg.twdinspection.inspection.source.academic_overview.AcademicEntity;
import com.cgg.twdinspection.inspection.source.academic_overview.AcademicGradeEntity;

import java.util.List;

public class AcademicViewModel extends ViewModel {

    private Context application;
    private ActivityAcademicBinding binding;
    private AcademicRepository mRepository;

    public AcademicViewModel(ActivityAcademicBinding binding, Context application) {
        this.binding = binding;
        this.application = application;
        mRepository = new AcademicRepository(application);

    }


    public AcademicViewModel(Context application) {
        this.application = application;
        mRepository = new AcademicRepository(application);

    }

    public long insertAcademicInfo(AcademicEntity AcademicEntity) {
        return mRepository.insertAcademicInfo(AcademicEntity);
    }

    public long deleteGradeInfo() {
        return mRepository.deleteGradeInfo();
    }

    public void insertAcademicGradeInfo(List<AcademicGradeEntity> academicGradeEntities) {
        mRepository.insertAcademicGradeInfo(academicGradeEntities);
    }

    public LiveData<List<AcademicGradeEntity>> getAcademicGradeInfo(String inst_id) {
        return mRepository.getAcademicGradeInfo(inst_id);
    }

}
