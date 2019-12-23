package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityRegistersBinding;

public class RegistersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegistersBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_registers);
        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText("Registers Up to date");

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistersActivity.this, GeneralCommentsActivity.class));
            }
        });
    }
}
