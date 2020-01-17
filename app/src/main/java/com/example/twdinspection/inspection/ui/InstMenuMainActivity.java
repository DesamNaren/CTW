package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.InstMainActivityBinding;
import com.example.twdinspection.inspection.adapter.MenuSectionsAdapter;
import com.example.twdinspection.inspection.source.instMenuInfo.InstMenuInfoEntity;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class InstMenuMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    InstMainActivityBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String instId;
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

        LiveData<List<InstMenuInfoEntity>> arrayListLiveData = instMainViewModel.getAllSections();
        sharedPreferences = TWDApplication.get(this).getPreferences();
        editor = sharedPreferences.edit();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");

        arrayListLiveData.observe(this, new Observer<List<InstMenuInfoEntity>>() {
            @Override
            public void onChanged(List<InstMenuInfoEntity> menuInfoEntities) {
                arrayListLiveData.removeObservers(InstMenuMainActivity.this);

                if (menuInfoEntities != null && menuInfoEntities.size() > 0) {
                    setAdapter(menuInfoEntities);
                } else {
                    String[] stringArray = getResources().getStringArray(R.array.inst_sections);
                    ArrayList<String> sections = new ArrayList<String>(Arrays.asList(stringArray));
                    if (sections.size() > 0) {
                        menuInfoEntities = new ArrayList<>();
                        for (int x=0;x<sections.size();x++) {
                            menuInfoEntities.add(new InstMenuInfoEntity(instId,x+1, 0, sections.get(x), null));
                        }
                        if (menuInfoEntities.size() > 0) {
                            instMainViewModel.insertMenuSections(menuInfoEntities);
                            setAdapter(menuInfoEntities);
                        }
                    }

                }
            }
        });


        binding.appbar.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InstMenuMainActivity.this, GeneralInfoActivity.class));
            }
        });
    }

    private void setAdapter(List<InstMenuInfoEntity> menuInfoEntities) {
        MenuSectionsAdapter adapter = new MenuSectionsAdapter(InstMenuMainActivity.this, menuInfoEntities);
        binding.appbar.includeMenuLayout.rvMenu.setLayoutManager(new LinearLayoutManager(InstMenuMainActivity.this));
        binding.appbar.includeMenuLayout.rvMenu.setAdapter(adapter);
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

