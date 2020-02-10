package com.example.twdinspection.gcc.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.twdinspection.R;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityGccPhotoCaptureBinding;
import com.example.twdinspection.inspection.ui.LocBaseActivity;

public class GCCPhotoActivity extends LocBaseActivity {

    ActivityGccPhotoCaptureBinding binding;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gcc_photo_capture);
        binding.header.headerTitle.setText(getString(R.string.upload_photos));

    }
}
