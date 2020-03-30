package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cgg.twdinspection.databinding.ActivityCocurricularAchDetailsBinding;
import com.cgg.twdinspection.databinding.ActivityPlantsInfoBinding;


public class PlantsInfoCustomViewModel implements ViewModelProvider.Factory {
    private ActivityPlantsInfoBinding binding;
    private Context context;
    Application application;

    public PlantsInfoCustomViewModel(ActivityPlantsInfoBinding binding, Context context, Application application) {
        this.binding = binding;
        this.context = context;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PlantsInfoViewModel(binding, application);
    }
}
