package com.example.twdinspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityGenerateMpinBinding;
import com.example.twdinspection.databinding.ActivityUploadedPhotoBinding;

public class UploadedPhotoActivity extends AppCompatActivity {

    ActivityUploadedPhotoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_uploaded_photo);
    }
}
