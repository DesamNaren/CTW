package com.example.twdinspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.TextView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityDietIssuesBinding;

public class DietIssuesActivity extends AppCompatActivity {

    LruCache<String,String> chachememory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDietIssuesBinding binding=DataBindingUtil.setContentView(this,R.layout.activity_diet_issues);
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
