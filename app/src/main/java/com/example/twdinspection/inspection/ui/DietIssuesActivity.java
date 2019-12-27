package com.example.twdinspection.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityDietIssuesBinding;
import com.example.twdinspection.databinding.ActivityDietIssuesTempBinding;

public class DietIssuesActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDietIssuesTempBinding binding=DataBindingUtil.setContentView(this,R.layout.activity_diet_issues_temp);
        TextView tv_title=findViewById(R.id.header_title);
        tv_title.setText("Diet Issues");
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DietIssuesActivity.this, InfraActivity.class));
            }
        });
    }
}
