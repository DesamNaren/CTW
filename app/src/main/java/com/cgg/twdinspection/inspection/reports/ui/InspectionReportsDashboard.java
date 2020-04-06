package com.cgg.twdinspection.inspection.reports.ui;

import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.inspection.reports.adapter.InspectionReportAdapter;
import com.cgg.twdinspection.inspection.reports.interfaces.InspReportClickCallback;
import com.cgg.twdinspection.inspection.reports.source.InspReportData;
import com.cgg.twdinspection.inspection.reports.source.InspReportResponse;
import com.cgg.twdinspection.inspection.viewmodel.InspectionReportsViewModel;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.cgg.twdinspection.databinding.ActivityInspReportBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class InspectionReportsDashboard extends AppCompatActivity implements ErrorHandlerInterface, InspReportClickCallback {

    ActivityInspReportBinding binding;
    InspectionReportsViewModel viewModel;
    CustomProgressDialog customProgressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String officerId;
    List<InspReportData> inspReportDataList;
    InspectionReportAdapter adapter;
    private Menu mMenu = null;
    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insp_report);

        try {
            if (getSupportActionBar() != null) {
                TextView tv = new TextView(getApplicationContext());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                        RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
                tv.setLayoutParams(lp);
                tv.setText(getResources().getString(R.string.insp_reports));
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

        viewModel = new InspectionReportsViewModel(InspectionReportsDashboard.this, getApplication());
        customProgressDialog = new CustomProgressDialog(this);
        inspReportDataList = new ArrayList<>();
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        if (Utils.checkInternetConnection(InspectionReportsDashboard.this)) {
            customProgressDialog.show();
            LiveData<InspReportResponse> inspectionReports = viewModel.getInspectionReports(officerId);
            inspectionReports.observe(InspectionReportsDashboard.this, new Observer<InspReportResponse>() {
                @Override
                public void onChanged(InspReportResponse inspReportResponse) {
                    customProgressDialog.hide();
                    inspectionReports.removeObservers(InspectionReportsDashboard.this);
                    if (inspReportResponse != null) {
                        if (inspReportResponse.getData() != null && inspReportResponse.getData().size() > 0) {
                            inspReportDataList.addAll(inspReportResponse.getData());

                            if (inspReportDataList != null && inspReportDataList.size() > 0) {
                                binding.recyclerView.setVisibility(View.VISIBLE);
                                binding.tvEmpty.setVisibility(View.GONE);
                                adapter = new InspectionReportAdapter(InspectionReportsDashboard.this, inspReportDataList);
                                binding.recyclerView.setLayoutManager(new LinearLayoutManager(InspectionReportsDashboard.this));
                                binding.recyclerView.setAdapter(adapter);
                                if(mMenu!=null)
                                mMenu.findItem(R.id.action_search).setVisible(true);

                            } else {
                                binding.recyclerView.setVisibility(View.GONE);
                                binding.tvEmpty.setVisibility(View.VISIBLE);
                                callSnackBar("No data available");
                                if(mMenu!=null)
                                mMenu.findItem(R.id.action_search).setVisible(false);
                            }

                        } else if (inspReportResponse.getData() != null && inspReportResponse.getData().size() == 0) {
                            callSnackBar("No data available");
                        } else {
                            callSnackBar(getString(R.string.something));
                        }
                    } else {
                        callSnackBar(getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customWarningAlert(InspectionReportsDashboard.this, getResources().getString(R.string.app_name), "Please check internet");
        }
    }

    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        callSnackBar(errMsg);
    }


    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.root, msg, Snackbar.LENGTH_INDEFINITE);
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
    public void onItemClick(InspReportData reportData) {
        Gson gson = new Gson();
        String data = gson.toJson(reportData);
        editor.putString(AppConstants.INSP_REP_DATA, data);
        editor.commit();
        startActivity(new Intent(this, InstReportsMenuActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        mMenu = menu;
        mMenu.findItem(R.id.mi_filter).setVisible(false);
        MenuItem mSearch = mMenu.findItem(R.id.action_search);
        mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.search_by_inst) + "</font>"));
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
