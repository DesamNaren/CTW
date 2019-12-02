package com.example.twdinspection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityAcademicBinding;

public class AcademicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAcademicBinding binding=DataBindingUtil.setContentView(this,R.layout.activity_academic);
        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText("Academic Overview");

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AcademicActivity.this, EntitlementsActivity.class));
            }
        });
    }
}
