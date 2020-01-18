package com.example.twdinspection.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityUploadedPhotoBinding;

public class UploadedPhotoActivity extends BaseActivity {

    ActivityUploadedPhotoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_uploaded_photo, getResources().getString(R.string.title_uploaded_photo));
        binding.btnLayout.btnNext.setText(getResources().getString(R.string.completed));
        binding.btnLayout.btnPrevious.setVisibility(View.GONE);
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UploadedPhotoActivity.this, "Completed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
