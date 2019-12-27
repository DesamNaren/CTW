package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.inspection.adapter.InstMenuAdapter;
import com.example.twdinspection.databinding.InstMainActivityBinding;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class InstMenuMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    InstMainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.inst_main_activity);
        InstMainViewModel instMainViewModel = new InstMainViewModel(getApplication());
        binding.setViewmodel(instMainViewModel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ArrayList<String> sections = instMainViewModel.getAllSections();
        if (sections != null && sections.size() > 0) {
            InstMenuAdapter adapter = new InstMenuAdapter(InstMenuMainActivity.this, sections);
            binding.appbar.includeMenuLayout.rvMenu.setLayoutManager(new LinearLayoutManager(InstMenuMainActivity.this));
            binding.appbar.includeMenuLayout.rvMenu.setAdapter(adapter);
        }
        binding.appbar.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InstMenuMainActivity.this, GeneralInfoActivity.class));
            }
        });
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {

        try {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {

            }
        } catch (Exception e) {
            InstMenuMainActivity.this.finish();
        }

    }

}

