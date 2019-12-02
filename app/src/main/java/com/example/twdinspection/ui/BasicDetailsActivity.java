package com.example.twdinspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityBasicDetailsBinding;
import com.example.twdinspection.viewmodel.BasicDetailsViewModel;

public class BasicDetailsActivity extends AppCompatActivity {

    private static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBasicDetailsBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_basic_details);
        activityMainBinding.setViewModel(new BasicDetailsViewModel());
        activityMainBinding.executePendingBindings();
        mContext=this;

        activityMainBinding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BasicDetailsActivity.this, InfoActivity.class));
            }
        });

    }
}
