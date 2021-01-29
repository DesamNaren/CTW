package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.cgg.twdinspection.databinding.ActivityGeneralCommentsBinding;
import com.cgg.twdinspection.inspection.room.repository.GenCommentsRepository;
import com.cgg.twdinspection.inspection.source.diet_issues.DietIssuesEntity;
import com.cgg.twdinspection.inspection.source.general_comments.GeneralCommentsEntity;

public class GenCommentsViewModel extends ViewModel {

    private Application application;
    private ActivityGeneralCommentsBinding binding;
    private GenCommentsRepository mRepository;

    public GenCommentsViewModel(ActivityGeneralCommentsBinding binding, Application application) {
        this.binding = binding;
        this.application = application;
        mRepository = new GenCommentsRepository(application);

    }

    public long insertGeneralCommentsInfo(GeneralCommentsEntity generalCommentsEntity) {
        long flag = mRepository.insertGeneralCommentsInfo(generalCommentsEntity);
        return flag;
    }

    public long updateGeneralCommentsInfo(GeneralCommentsEntity generalCommentsEntity) {
        long flag = mRepository.updateGeneralCommentsInfo(generalCommentsEntity);
        return flag;
    }
}
