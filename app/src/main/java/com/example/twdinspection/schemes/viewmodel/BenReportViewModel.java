package com.example.twdinspection.schemes.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.inspection.adapter.StudentsAttAdapter;
import com.example.twdinspection.inspection.ui.StudentsAttendance_2;
import com.example.twdinspection.schemes.room.repository.SchemesDMVRepository;

public class BenReportViewModel extends AndroidViewModel {
    public BenReportViewModel(Application application) {
        super(application);

    }
}
