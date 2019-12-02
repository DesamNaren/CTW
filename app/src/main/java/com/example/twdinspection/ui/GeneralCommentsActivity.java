package com.example.twdinspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityGeneralCommentsBinding;

public class GeneralCommentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGeneralCommentsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_general_comments);

        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText("General Comments");

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GeneralCommentsActivity.this, UploadedPhotoActivity.class));
            }
        });

    }
}
