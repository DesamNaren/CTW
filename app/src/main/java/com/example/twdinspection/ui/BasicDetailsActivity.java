package com.example.twdinspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityBasicDetailsBinding;

public class BasicDetailsActivity extends AppCompatActivity {

    private static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBasicDetailsBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_basic_details);
        activityMainBinding.setViewModel(new BasicDetailsViewModel());
        activityMainBinding.executePendingBindings();
        mContext=this;
    }
}
