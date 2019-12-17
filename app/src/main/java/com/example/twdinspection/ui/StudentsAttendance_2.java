package com.example.twdinspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.twdinspection.R;
import com.example.twdinspection.application.TWDApplication;
import com.example.twdinspection.source.studentAttendenceInfo.StudAttendInfoEntity;
import com.example.twdinspection.adapter.StudentsAttAdapter;
import com.example.twdinspection.databinding.ActivityStudentsAttendanceBinding;
import com.example.twdinspection.utils.AppConstants;
import com.example.twdinspection.viewmodel.StudAttndCustomViewModel;
import com.example.twdinspection.viewmodel.StudentsAttndViewModel;

import java.util.List;

public class StudentsAttendance_2 extends AppCompatActivity {
    ActivityStudentsAttendanceBinding binding;
    StudentsAttAdapter adapter;
    private MutableLiveData<List<StudAttendInfoEntity>> MutableLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStudentsAttendanceBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_students_attendance);
        SharedPreferences sharedPreferences = TWDApplication.get(this).getPreferences();
        MutableLiveData=new MutableLiveData<>();
        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText("Students Attendance");
        StudentsAttndViewModel studentsAttndViewModel =
                ViewModelProviders.of(StudentsAttendance_2.this,
                        new StudAttndCustomViewModel(binding, this,getApplication())).get(StudentsAttndViewModel.class);
        binding.setViewModel(studentsAttndViewModel);




        studentsAttndViewModel.getClassInfo( TWDApplication.get(this).getPreferences().getString(AppConstants.InstId,"")).observe(this, new Observer<List<StudAttendInfoEntity>>() {
            @Override
            public void onChanged(List<StudAttendInfoEntity> studentsAttendanceBeans) {
                adapter = new StudentsAttAdapter(StudentsAttendance_2.this, studentsAttendanceBeans);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(StudentsAttendance_2.this));
                binding.recyclerView.setAdapter(adapter);
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentsAttendance_2.this, StaffAttendActivity.class));
            }
        });
    }
}
