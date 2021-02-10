package com.cgg.twdinspection.inspection.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityHomeBinding;
import com.cgg.twdinspection.engineering_works.ui.EngineeringDashboardActivity;
import com.cgg.twdinspection.gcc.ui.gcc.GCCDashboardActivity;
import com.cgg.twdinspection.inspection.reports.ui.ReportActivity;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstMenuInfoEntity;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstSelectionInfo;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstSelectionViewModel;
import com.cgg.twdinspection.offline.GCCOfflineDashboard;
import com.cgg.twdinspection.schemes.ui.SchemesDMVActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class DashboardMenuActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private AppBarConfiguration mAppBarConfiguration;
    private InstMainViewModel instMainViewModel;
    private ActivityHomeBinding binding;
    private MenuItem nav_home, nav_schemes, nav_school, nav_gcc, nav_eng, nav_reports, nav_my_insp, nav_logout;
    private Context context;
    private InstSelectionViewModel instSelectionViewModel;
    private String instId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = DashboardMenuActivity.this;
        instSelectionViewModel = new InstSelectionViewModel(getApplication());
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        instMainViewModel = new InstMainViewModel(getApplication());

        binding.appBarLayout.ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.customExitAlert(DashboardMenuActivity.this,
                        getResources().getString(R.string.app_name),
                        getString(R.string.logout));
//                Utils.customLogoutAlert(DashboardMenuActivity.this, getResources().getString(R.string.app_name),
//                        getString(R.string.logout), instMainViewModel, editor);
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_schemes, R.id.nav_school, R.id.nav_gcc, R.id.nav_eng, R.id.nav_reports, R.id.nav_my_insp, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        nav_home = navigationView.getMenu().findItem(R.id.nav_home);
        nav_schemes = navigationView.getMenu().findItem(R.id.nav_schemes);
        nav_school = navigationView.getMenu().findItem(R.id.nav_school);
        nav_gcc = navigationView.getMenu().findItem(R.id.nav_gcc);
        nav_eng = navigationView.getMenu().findItem(R.id.nav_eng);
        nav_reports = navigationView.getMenu().findItem(R.id.nav_reports);
        nav_my_insp = navigationView.getMenu().findItem(R.id.nav_my_insp);
        nav_logout = navigationView.getMenu().findItem(R.id.nav_logout);

        nav_home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent newIntent = new Intent(context, DashboardMenuActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);
                //do as you want with the button click

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });
        nav_schemes.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent newIntent = new Intent(context, SchemesDMVActivity.class);
                startActivity(newIntent);
                //do as you want with the button click

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        nav_school.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                instSelectionViewModel.getSelectedInst().observe(DashboardMenuActivity.this, new Observer<InstSelectionInfo>() {
                    @Override
                    public void onChanged(InstSelectionInfo instSelectionInfo) {
                        if (instSelectionInfo != null) {
                            instId = instSelectionInfo.getInst_id();
                            if (!TextUtils.isEmpty(instId)) {
                                instMainViewModel.getAllSections().observe(DashboardMenuActivity.this, new Observer<List<InstMenuInfoEntity>>() {
                                    @Override
                                    public void onChanged(List<InstMenuInfoEntity> instMenuInfoEntities) {
                                        if (instMenuInfoEntities != null && instMenuInfoEntities.size() > 0) {

                                            boolean flag = false;
                                            for (int i = 0; i < instMenuInfoEntities.size(); i++) {
                                                if (instMenuInfoEntities.get(i).getFlag_completed() == 1) {
                                                    flag = true;
                                                    break;
                                                }
                                            }
                                            if (flag) {
                                                startActivity(new Intent(DashboardMenuActivity.this, InstMenuMainActivity.class));
                                                finish();
                                            } else {
                                                startActivity(new Intent(DashboardMenuActivity.this, DMVSelectionActivity.class));
                                            }
                                        } else {
                                            startActivity(new Intent(DashboardMenuActivity.this, DMVSelectionActivity.class));
                                        }
                                    }

                                });
                            } else {
                                startActivity(new Intent(DashboardMenuActivity.this, DMVSelectionActivity.class));
                            }
                        } else {
                            startActivity(new Intent(DashboardMenuActivity.this, DMVSelectionActivity.class));
                        }
                    }
                });
                return true;
            }
        });

        nav_gcc.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent newIntent = new Intent(context, GCCDashboardActivity.class);
                startActivity(newIntent);
                //do as you want with the button click

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });
        nav_eng.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent newIntent = new Intent(context, EngineeringDashboardActivity.class);
                startActivity(newIntent);
                //do as you want with the button click

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });
        nav_reports.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent newIntent = new Intent(context, ReportActivity.class);
                startActivity(newIntent);
                //do as you want with the button click

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });
        nav_my_insp.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent newIntent = new Intent(context, GCCOfflineDashboard.class);
                startActivity(newIntent);
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });
        nav_logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Utils.customExitAlert(DashboardMenuActivity.this,
                        getResources().getString(R.string.app_name),
                        getString(R.string.exit_msg));
                //do as you want with the button click

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {

        try {
            boolean isAutomatic = Utils.isTimeAutomatic(this);
            if (!isAutomatic) {
                Utils.customTimeAlert(this,
                        getResources().getString(R.string.app_name),
                        getString(R.string.date_time));
                return;
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        Utils.customExitAlert(this,
                getResources().getString(R.string.app_name),
                getString(R.string.exit_msg));
    }
}