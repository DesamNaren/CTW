package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityAcademicBinding;
import com.example.twdinspection.inspection.Room.repository.AcademicRepository;
import com.example.twdinspection.inspection.source.AcademicOverview.AcademicOveriewEntity;

public class AcademicViewModel extends ViewModel {

    private Application application;
    private ActivityAcademicBinding binding;
    private AcademicRepository mRepository;

    public AcademicViewModel(ActivityAcademicBinding binding, Application application) {
        this.binding = binding;
        this.application = application;
        mRepository = new AcademicRepository(application);

    }

    public long insertAcademicInfo(AcademicOveriewEntity academicOveriewEntity) {
        long flag = mRepository.insertAcademicInfo(academicOveriewEntity);
        return flag;
    }
}
