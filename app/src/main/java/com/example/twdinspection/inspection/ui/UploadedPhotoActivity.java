package com.example.twdinspection.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityUploadedPhotoBinding;

public class UploadedPhotoActivity extends AppCompatActivity {

    ActivityUploadedPhotoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_uploaded_photo);
        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText("Uploaded Photographs");
        binding.btnLayout.btnNext.setText(getResources().getString(R.string.completed));
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UploadedPhotoActivity.this, "Completed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}