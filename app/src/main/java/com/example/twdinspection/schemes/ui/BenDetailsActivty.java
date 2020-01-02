package com.example.twdinspection.schemes.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.TextView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityBenDetailsActivtyBinding;

public class BenDetailsActivty extends AppCompatActivity {

    ActivityBenDetailsActivtyBinding benDetailsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        benDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_ben_details_activty);
        benDetailsBinding.header.headerTitle.setText(getString(R.string.ben_details));
    }
}
