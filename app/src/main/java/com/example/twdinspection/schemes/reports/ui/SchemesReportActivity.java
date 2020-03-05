package com.example.twdinspection.schemes.reports.ui;

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
import android.widget.LinearLayout;
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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.R;
import com.example.twdinspection.common.ErrorHandler;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.CustomProgressDialog;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivitySchemeReportBinding;
import com.example.twdinspection.gcc.adapter.SchemeReportAdapter;
import com.example.twdinspection.gcc.reports.interfaces.ReportClickCallback;
import com.example.twdinspection.gcc.reports.source.ReportData;
import com.example.twdinspection.gcc.viewmodel.SchemeReportsViewModel;
import com.example.twdinspection.schemes.adapter.SchemeInfoAdapter;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.example.twdinspection.schemes.interfaces.SchemeClickCallback;
import com.example.twdinspection.schemes.reports.source.SchemeReportData;
import com.example.twdinspection.schemes.reports.source.SchemeReportResponse;
import com.example.twdinspection.schemes.source.schemes.SchemeEntity;
import com.example.twdinspection.schemes.viewmodel.BenCustomReportViewModel;
import com.example.twdinspection.schemes.viewmodel.BenReportViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SchemesReportActivity extends AppCompatActivity implements ReportClickCallback, SchemeClickCallback, ErrorHandlerInterface {

    SchemeReportAdapter adapter;
    private ActivitySchemeReportBinding schemeReportBinding;
    private CustomProgressDialog customProgressDialog;
    SearchView mSearchView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String officerId;
    private SchemeReportsViewModel schemeReportsViewModel;
    BenReportViewModel viewModel;
    private List<SchemeEntity> schemesInfoEntitiesMain;
    private BottomSheetDialog dialog;
    private Menu mMenu = null;
    private List<SchemeReportData> tempBeneficiaryDetails;
    private List<SchemeReportData> beneficiaryDetailsMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customProgressDialog = new CustomProgressDialog(this);
        schemeReportBinding = DataBindingUtil.setContentView(this, R.layout.activity_scheme_report);
        schemeReportBinding.executePendingBindings();
        schemesInfoEntitiesMain = new ArrayList<>();
        tempBeneficiaryDetails = new ArrayList<>();
        beneficiaryDetailsMain = new ArrayList<>();

        try {
            if (getSupportActionBar() != null) {
                TextView tv = new TextView(getApplicationContext());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                        RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
                tv.setLayoutParams(lp);
                tv.setText(getResources().getString(R.string.scheme_report));
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


        viewModel =
                ViewModelProviders.of(this,
                        new BenCustomReportViewModel(schemeReportBinding, this)).get(BenReportViewModel.class);

        schemeReportsViewModel = new SchemeReportsViewModel(SchemesReportActivity.this, getApplication());

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        viewModel.getSchemeInfo().observe(this, new Observer<List<SchemeEntity>>() {
            @Override
            public void onChanged(List<SchemeEntity> schemeEntities) {
                schemesInfoEntitiesMain = schemeEntities;
            }
        });

        if (Utils.checkInternetConnection(SchemesReportActivity.this)) {
            customProgressDialog.show();
            LiveData<SchemeReportResponse> schemeReports = schemeReportsViewModel.getSchemeReports(officerId);
            schemeReports.observe(SchemesReportActivity.this, new Observer<SchemeReportResponse>() {
                @Override
                public void onChanged(SchemeReportResponse schemeReportResponse) {
                    customProgressDialog.hide();
                    schemeReports.removeObservers(SchemesReportActivity.this);
                    if (schemeReportResponse != null) {
                        if (schemeReportResponse.getSchemeReportData() != null && schemeReportResponse.getSchemeReportData().size() > 0) {
                            beneficiaryDetailsMain = schemeReportResponse.getSchemeReportData();
                            tempBeneficiaryDetails.addAll(schemeReportResponse.getSchemeReportData());
                            mMenu.findItem(R.id.action_search).setVisible(true);
                            mMenu.findItem(R.id.mi_filter).setVisible(true);
                            schemeReportBinding.recyclerView.setVisibility(View.VISIBLE);
                            schemeReportBinding.tvEmpty.setVisibility(View.GONE);
                            adapter = new SchemeReportAdapter(SchemesReportActivity.this, tempBeneficiaryDetails);
                            schemeReportBinding.recyclerView.setLayoutManager(new LinearLayoutManager(SchemesReportActivity.this));
                            schemeReportBinding.recyclerView.setAdapter(adapter);
                        } else if (schemeReportResponse.getSchemeReportData() != null && schemeReportResponse.getSchemeReportData().size() == 0) {
                            mMenu.findItem(R.id.action_search).setVisible(false);
                            mMenu.findItem(R.id.mi_filter).setVisible(false);

                            schemeReportBinding.recyclerView.setVisibility(View.GONE);
                            schemeReportBinding.tvEmpty.setVisibility(View.VISIBLE);
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
            Utils.customWarningAlert(SchemesReportActivity.this, getResources().getString(R.string.app_name), "Please check internet");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        mMenu = menu;
        MenuItem mSearch = mMenu.findItem(R.id.action_search);
        mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.search_hint) + "</font>"));
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
        if (item.getItemId() == R.id.mi_filter) {
            if (!mSearchView.isIconified()) {
                mSearchView.onActionViewCollapsed();
            }
            if (schemesInfoEntitiesMain.size() > 0) {
                showSchemeDetails(schemesInfoEntitiesMain);
            }
        }

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(schemeReportBinding.root, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }


    public void showSchemeDetails(List<SchemeEntity> schemesInfoEntitiesMain) {
        View view = getLayoutInflater().inflate(R.layout.scheme_bottom_sheet, null);
        RecyclerView filterRecyclerView = view.findViewById(R.id.schemeRV);
        dialog = new BottomSheetDialog(SchemesReportActivity.this);
        LinearLayout scheme_entries = view.findViewById(R.id.scheme_entries);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(scheme_entries);
        bottomSheetBehavior.setPeekHeight(1500);
        dialog.setContentView(view);
        dialog.show();
        SchemeInfoAdapter schemeInfoAdapter = new SchemeInfoAdapter(this, schemesInfoEntitiesMain);
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        filterRecyclerView.setAdapter(schemeInfoAdapter);
    }

    @Override
    public void onItemClick(ReportData reportData) {

    }

    @Override
    public void onItemClick(SchemeReportData schemeReportData) {
        Gson gson = new Gson();
        String data = gson.toJson(schemeReportData);
        editor.putString(AppConstants.SCHEME_REP_DATA, data);
        editor.apply();
        startActivity(new Intent(this, SchemeReportDetailsActivity.class));
    }

    @Override
    public void onItemClick(String schemeType) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        tempBeneficiaryDetails.clear();

        if (beneficiaryDetailsMain.size() > 0) {
            if (schemeType.equalsIgnoreCase("All")) {
                tempBeneficiaryDetails.addAll(beneficiaryDetailsMain);
            } else {
                for (SchemeReportData beneficiaryDetail : beneficiaryDetailsMain) {
                    if (schemeType.equals(beneficiaryDetail.getSchemeType())) {
                        tempBeneficiaryDetails.add(beneficiaryDetail);
                    }
                }
            }

            if (tempBeneficiaryDetails.size() > 0) {
                schemeReportBinding.tvEmpty.setVisibility(View.GONE);
                schemeReportBinding.recyclerView.setVisibility(View.VISIBLE);
                adapter.setData(tempBeneficiaryDetails);
            } else {
                schemeReportBinding.tvEmpty.setVisibility(View.VISIBLE);
                schemeReportBinding.recyclerView.setVisibility(View.GONE);
            }
        } else {
            schemeReportBinding.tvEmpty.setVisibility(View.VISIBLE);
            schemeReportBinding.recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        callSnackBar(errMsg);
    }

}
