package com.example.twdinspection.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.adapter.StudentsAttAdapter;
import com.example.twdinspection.application.TWDApplication;
import com.example.twdinspection.databinding.InstMainActivityBinding;
import com.example.twdinspection.source.studentAttendenceInfo.StudAttendInfoEntity;
import com.example.twdinspection.utils.AppConstants;
import com.example.twdinspection.viewmodel.InstMainViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class InstMenuMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    InstMainActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this, R.layout.inst_main_activity);
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


        instMainViewModel.getAllSections();
//        studAttendInfoEntities = studentsAttendanceBeans;
//        adapter = new StudentsAttAdapter(InstMenuMainActivity.this, studAttendInfoEntities);
//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(StudentsAttendance_2.this));
//        binding.recyclerView.setAdapter(adapter);
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

