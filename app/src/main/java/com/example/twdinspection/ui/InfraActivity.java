package com.example.twdinspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityInfrastructureBinding;

public class InfraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInfrastructureBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_infrastructure);
        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText("Infrastructure & Maintenance");

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InfraActivity.this, AcademicActivity.class));
            }
        });
    }
}
