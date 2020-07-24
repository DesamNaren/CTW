package com.cgg.twdinspection.gcc.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.databinding.ActivityGccReportBinding;
import com.cgg.twdinspection.gcc.reports.adapter.GCCReportAdapter;
import com.cgg.twdinspection.gcc.reports.interfaces.ReportClickCallback;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.cgg.twdinspection.schemes.reports.source.SchemeReportData;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GCCReportActivity extends AppCompatActivity implements ReportClickCallback {

    GCCReportAdapter adapter;
    ActivityGccReportBinding gccReportBinding;
    private CustomProgressDialog customProgressDialog;
    SearchView mSearchView;
    SharedPreferences sharedPreferences;
    List<ReportData> reportData;
    SharedPreferences.Editor editor;
    private List<ReportData> tempReportData;
    private Menu mMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customProgressDialog = new CustomProgressDialog(this,"");
        sharedPreferences = TWDApplication.get(this).getPreferences();
        editor = sharedPreferences.edit();

        gccReportBinding = DataBindingUtil.setContentView(this, R.layout.activity_gcc_report);
        gccReportBinding.executePendingBindings();
        gccReportBinding.header.headerTitle.setText(getString(R.string.gcc_reports));
        gccReportBinding.header.ivHome.setVisibility(View.GONE);

        reportData = new ArrayList<>();
        tempReportData = new ArrayList<>();

        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.Selected_Supp_Report, "");
        Type type = new TypeToken<List<ReportData>>() {
        }.getType();
        reportData = gson.fromJson(data, type);


        try {
            if (getSupportActionBar() != null) {
                TextView tv = new TextView(getApplicationContext());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                        RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
                tv.setLayoutParams(lp);

                if (reportData.get(0).getSupplierType().equalsIgnoreCase(AppConstants.REPORT_GODOWN)) {
                    tv.setText("DR GODOWN REPORT");
                }
                if (reportData.get(0).getSupplierType().equalsIgnoreCase(AppConstants.REPORT_DEPOT_REP)) {
                    tv.setText("DR DEPOT REPORT");
                }
                if (reportData.get(0).getSupplierType().equalsIgnoreCase(AppConstants.REPORT_MFP_GODOWN_REP)) {
                    tv.setText("MFP GODOWN REPORT");
                }
                if (reportData.get(0).getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PUNIT_REP)) {
                    tv.setText("PROCESSING UNIT REPORT");
                }
                if (reportData.get(0).getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PETROL_REP)) {
                    tv.setText("PETROL PUMP REPORT");
                }
                if (reportData.get(0).getSupplierType().equalsIgnoreCase(AppConstants.REPORT_LPG_REP)) {
                    tv.setText("LPG REPORT");
                }
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(tv);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
                getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_btn_rounded));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        gccReportBinding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        mMenu = menu;

        if (reportData != null && reportData.size() > 0) {

            tempReportData.addAll(reportData);
            mMenu.findItem(R.id.action_search).setVisible(true);
            mMenu.findItem(R.id.mi_filter).setVisible(false);

            gccReportBinding.recyclerView.setVisibility(View.VISIBLE);
            gccReportBinding.tvEmpty.setVisibility(View.GONE);

            adapter = new GCCReportAdapter(this, reportData);
            gccReportBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            gccReportBinding.recyclerView.setAdapter(adapter);
        } else {
            mMenu.findItem(R.id.action_search).setVisible(false);
            mMenu.findItem(R.id.mi_filter).setVisible(false);

            gccReportBinding.recyclerView.setVisibility(View.GONE);
            gccReportBinding.tvEmpty.setVisibility(View.VISIBLE);
            callSnackBar("No data available");
        }

        MenuItem mSearch = mMenu.findItem(R.id.action_search);
        mSearchView = (SearchView) mSearch.getActionView();
        String hint = null;
        if (reportData.get(0).getSupplierType().equalsIgnoreCase(AppConstants.REPORT_GODOWN)) {
            hint = getResources().getString(R.string.godown_search_hint);
        }
        if (reportData.get(0).getSupplierType().equalsIgnoreCase(AppConstants.REPORT_DEPOT_REP)) {
            hint = getResources().getString(R.string.depot_search_hint);
        }
        if (reportData.get(0).getSupplierType().equalsIgnoreCase(AppConstants.REPORT_MFP_GODOWN_REP)) {
            hint = getResources().getString(R.string.mfp_search_hint);
        }
        if (reportData.get(0).getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PUNIT_REP)) {
            hint = getResources().getString(R.string.unit_search_hint);
        }
        if (reportData.get(0).getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PETROL_REP)) {
            hint = getResources().getString(R.string.petrol_search_hint);
        }
        if (reportData.get(0).getSupplierType().equalsIgnoreCase(AppConstants.REPORT_LPG_REP)) {
            hint = getResources().getString(R.string.lpg_search_hint);
        }

        mSearchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + hint + "</font>"));
        mSearchView.setInputType(InputType.TYPE_CLASS_TEXT);
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = mSearchView.findViewById(id);
        textView.setTextColor(Color.WHITE);
        mSearchView.setGravity(Gravity.CENTER);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
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


    @Override
    public void onItemClick(ReportData reportData) {
        Gson gson = new Gson();
        String data = gson.toJson(reportData);
        editor.putString(AppConstants.REP_DATA, data);
        editor.commit();
        startActivity(new Intent(this, ReportStockDetailsActivity.class));
    }

    @Override
    public void onItemClick(SchemeReportData schemeReportData) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GCCReportActivity.this, GCCReportsDashboard.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
}
