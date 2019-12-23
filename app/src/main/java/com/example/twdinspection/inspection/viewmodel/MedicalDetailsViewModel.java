package com.example.twdinspection.inspection.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityStudentsAttendanceBinding;
import com.example.twdinspection.inspection.source.StudentsAttendanceBean;

import java.util.List;

public class MedicalDetailsViewModel extends ViewModel {

    private MutableLiveData<List<StudentsAttendanceBean>> studentAttndLiveData;
    private Context context;
    private ActivityStudentsAttendanceBinding binding;

    public MedicalDetailsViewModel(ActivityStudentsAttendanceBinding binding, Context context) {
        this.binding = binding;
        this.context = context;

    }

}
