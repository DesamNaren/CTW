package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.twdinspection.R;

import java.util.ArrayList;
import java.util.Arrays;

public class InstMainViewModel extends AndroidViewModel {
    private ArrayList<String> sections;
    private Application application;

    public InstMainViewModel(Application application) {
        super(application);
        this.application = application;
        sections = new ArrayList<>();
    }

    public ArrayList<String> getAllSections() {
        if (sections != null) {
            String[] stringArray = application.getResources().getStringArray(R.array.inst_sections);
            sections = new ArrayList<String>(Arrays.asList(stringArray));
        }
        return sections;
    }
}
