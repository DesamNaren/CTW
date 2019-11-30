package com.example.twdinspection.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.StudentsAttendanceBean;
import com.example.twdinspection.databinding.ActivityStudentsAttendanceBinding;

import java.util.ArrayList;
import java.util.List;

public class StudentsAttndViewModel extends ViewModel {

    private MutableLiveData<List<StudentsAttendanceBean>> studentAttndLiveData;
    private Context context;
    private ActivityStudentsAttendanceBinding binding;

    public StudentsAttndViewModel(ActivityStudentsAttendanceBinding binding, Context context) {
        this.binding = binding;
        this.context = context;

    }



    public LiveData<List<StudentsAttendanceBean>> geListLiveData() {
        studentAttndLiveData = new MutableLiveData<>();
        ArrayList<StudentsAttendanceBean> list=new ArrayList<>();
        StudentsAttendanceBean bean=new StudentsAttendanceBean("23","yes","","","");
        list.add(bean);
        list.add(bean);
        list.add(bean);
        studentAttndLiveData.setValue(list);
        return studentAttndLiveData;
    }

}
