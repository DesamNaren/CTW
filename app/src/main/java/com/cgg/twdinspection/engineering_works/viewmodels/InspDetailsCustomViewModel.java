package com.cgg.twdinspection.engineering_works.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class InspDetailsCustomViewModel implements ViewModelProvider.Factory {
    private Context context;
    private Application application;

    public InspDetailsCustomViewModel(Context context, Application application) {
        this.context = context;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new InspDetailsViewModel(context,application);
    }
}
