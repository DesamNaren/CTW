package com.example.twdinspection.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ProfileLayoutBinding;
import com.example.twdinspection.viewmodel.BasicDetailsViewModel;

import java.util.ArrayList;

public class BasicDetailsActivity extends AppCompatActivity {
    BasicDetailsViewModel viewModel;
    ProfileLayoutBinding profileLayoutBinding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = BasicDetailsActivity.this;

        profileLayoutBinding = DataBindingUtil.setContentView(this, R.layout.profile_layout);
        viewModel = new BasicDetailsViewModel(getApplication());
        profileLayoutBinding.setViewModel(viewModel);
        profileLayoutBinding.executePendingBindings();

        viewModel.getAllDistricts().observe(this, districts -> {
            if (districts != null && districts.size() > 0) {
                ArrayList<String> distNames = new ArrayList<>();
                for (int i = 0; i < districts.size(); i++) {
                    distNames.add(districts.get(i).getDist_name());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_dropdown_item, distNames
                );
                profileLayoutBinding.spDist.setAdapter(adapter);
            }
        });

        profileLayoutBinding.btnProceed.setOnClickListener(
                view -> startActivity(new Intent(BasicDetailsActivity.this, InfoActivity.class)));

    }
}
