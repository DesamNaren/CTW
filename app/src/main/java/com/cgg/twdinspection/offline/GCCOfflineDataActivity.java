package com.cgg.twdinspection.offline;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.databinding.ActivityGccReportBinding;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.gcc.viewmodel.GCCOfflineViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class GCCOfflineDataActivity extends AppCompatActivity {

    ActivityGccReportBinding gccReportBinding;
    private CustomProgressDialog customProgressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customProgressDialog = new CustomProgressDialog(this);
        sharedPreferences = TWDApplication.get(this).getPreferences();
        editor = sharedPreferences.edit();

        gccReportBinding = DataBindingUtil.setContentView(this, R.layout.activity_gcc_report);
        gccReportBinding.executePendingBindings();
        gccReportBinding.header.headerTitle.setText(getString(R.string.gcc_reports_offline_data));
        gccReportBinding.header.ivHome.setVisibility(View.GONE);

        gccReportBinding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        GCCOfflineViewModel gccOfflineViewModel = new GCCOfflineViewModel(getApplication());

        LiveData<List<GccOfflineEntity>> drGodownLiveData = gccOfflineViewModel.getGoDownsOfflineCount(AppConstants.OFFLINE_DR_GODOWN);
        drGodownLiveData.observe(GCCOfflineDataActivity.this, new Observer<List<GccOfflineEntity>>() {
            @Override
            public void onChanged(List<GccOfflineEntity> drGodownData) {
                drGodownLiveData.removeObservers(GCCOfflineDataActivity.this);
                if (drGodownData != null && drGodownData.size() > 0) {
                    GCCOfflineDataAdapter adapter = new GCCOfflineDataAdapter(GCCOfflineDataActivity.this, drGodownData);
                    gccReportBinding.recyclerView.setLayoutManager(new LinearLayoutManager(GCCOfflineDataActivity.this));
                    gccReportBinding.recyclerView.setAdapter(adapter);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(gccReportBinding.root, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }
}
