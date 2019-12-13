package com.example.twdinspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ProfileLayoutBinding;
import com.example.twdinspection.source.DistManVillage.DistrictEntity;
import com.example.twdinspection.viewmodel.BasicDetailsViewModel;

import java.util.List;

public class BasicDetailsActivity extends AppCompatActivity {

    LiveData<List<DistrictEntity>> districtResponse;
    private static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProfileLayoutBinding profileLayoutBinding = DataBindingUtil.setContentView(this, R.layout.profile_layout);
        profileLayoutBinding.setViewModel(new BasicDetailsViewModel(getApplication(),profileLayoutBinding));
        profileLayoutBinding.executePendingBindings();
        mContext=this;

        profileLayoutBinding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BasicDetailsActivity.this, InfoActivity.class));
            }
        });

    }
}
