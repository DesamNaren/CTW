package com.example.twdinspection.schemes.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.TextView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityBenDetailsActivtyBinding;
import com.example.twdinspection.inspection.ui.BaseActivity;

public class BenDetailsActivity extends BaseActivity {

    ActivityBenDetailsActivtyBinding benDetailsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        benDetailsBinding = putContentView(R.layout.activity_ben_details_activty, getString(R.string.ben_report));
    }
}
