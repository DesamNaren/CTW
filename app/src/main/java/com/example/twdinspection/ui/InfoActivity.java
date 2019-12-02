package com.example.twdinspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityInfoBinding;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_info);

        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText("General Information");
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InfoActivity.this, StudentsAttendance_2.class));
            }
        });
    }
}
