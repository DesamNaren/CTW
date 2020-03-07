package com.example.twdinspection.schemes.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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
import com.example.twdinspection.databinding.ActivityBeneficiaryReportBinding;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.example.twdinspection.schemes.adapter.BenReportAdapter;
import com.example.twdinspection.schemes.adapter.SchemeInfoAdapter;
import com.example.twdinspection.schemes.interfaces.BenClickCallback;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.example.twdinspection.schemes.interfaces.SchemeClickCallback;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryReport;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryRequest;
import com.example.twdinspection.schemes.source.schemes.SchemeEntity;
import com.example.twdinspection.schemes.viewmodel.BenCustomReportViewModel;
import com.example.twdinspection.schemes.viewmodel.BenReportViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class BeneficiaryReportActivity extends AppCompatActivity implements SchemeClickCallback, BenClickCallback, ErrorHandlerInterface {

    BenReportViewModel viewModel;
    BenReportAdapter adapter;
    ActivityBeneficiaryReportBinding beneficiaryReportBinding;
    private List<BeneficiaryDetail> beneficiaryDetailsMain;
    private List<BeneficiaryDetail> tempBeneficiaryDetails;
    private List<SchemeEntity> schemesInfoEntitiesMain;
    private BottomSheetDialog dialog;
    ArrayList<String> schemeValues;
    private CustomProgressDialog customProgressDialog;
    SearchView mSearchView;
    Menu mMenu = null;
    private String cacheDate, currentDate;
    InstMainViewModel instMainViewModel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int distId, mandId, villId;
    String finYr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customProgressDialog = new CustomProgressDialog(this);
        sharedPreferences = TWDApplication.get(this).getPreferences();
        editor = sharedPreferences.edit();
        distId = Integer.parseInt(sharedPreferences.getString(AppConstants.SCHEME_DIST_ID, ""));
        mandId = Integer.parseInt(sharedPreferences.getString(AppConstants.SCHEME_MAN_ID, ""));
        villId = Integer.parseInt(sharedPreferences.getString(AppConstants.SCHEME_VIL_ID, ""));
        finYr = sharedPreferences.getString(AppConstants.SCHEME_FIN_YEAR, "");
        beneficiaryDetailsMain = new ArrayList<>();
        tempBeneficiaryDetails = new ArrayList<>();
        schemesInfoEntitiesMain = new ArrayList<>();
        schemeValues = new ArrayList<>();
        instMainViewModel = new InstMainViewModel(getApplication());

        beneficiaryReportBinding = DataBindingUtil.setContentView(this, R.layout.activity_beneficiary_report);

        try {
            if (getSupportActionBar() != null) {
                TextView tv = new TextView(getApplicationContext());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                        RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
                tv.setLayoutParams(lp);
                tv.setText(getResources().getString(R.string.ben_report));
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(tv);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
                getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_btn_rounded));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewModel =
                ViewModelProviders.of(this,
                        new BenCustomReportViewModel(beneficiaryReportBinding, this)).get(BenReportViewModel.class);

        beneficiaryReportBinding.setViewModel(viewModel);
        beneficiaryReportBinding.executePendingBindings();

        BeneficiaryRequest beneficiaryRequest = new BeneficiaryRequest();
        beneficiaryRequest.setDistId(distId);
        beneficiaryRequest.setMandalId(mandId);
        beneficiaryRequest.setVillageId(villId);
        StringBuilder str = new StringBuilder();
        str.append("'");
        beneficiaryRequest.setFinYearId(str + finYr + str);

        customProgressDialog.show();
        viewModel.getBeneficiaryInfo(beneficiaryRequest).observe(this, new Observer<BeneficiaryReport>() {
            @Override
            public void onChanged(BeneficiaryReport beneficiaryDetails) {
                customProgressDialog.hide();
                if (beneficiaryDetails != null && beneficiaryDetails.getStatusCode() != null) {
                    if (beneficiaryDetails.getStatusCode() != null && beneficiaryDetails.getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
                        beneficiaryDetailsMain = beneficiaryDetails.getBeneficiaryDetails();
                        tempBeneficiaryDetails.addAll(beneficiaryDetailsMain);
                        if (tempBeneficiaryDetails.size() > 0) {
                            beneficiaryReportBinding.tvEmpty.setVisibility(View.GONE);
                            beneficiaryReportBinding.recyclerView.setVisibility(View.VISIBLE);
                            mMenu.findItem(R.id.action_search).setVisible(true);
                            mMenu.findItem(R.id.mi_filter).setVisible(true);
                            beneficiaryReportBinding.tvEmpty.setVisibility(View.GONE);
                            beneficiaryReportBinding.recyclerView.setVisibility(View.VISIBLE);
                            adapter = new BenReportAdapter(BeneficiaryReportActivity.this, tempBeneficiaryDetails);
                            beneficiaryReportBinding.recyclerView.setLayoutManager(new LinearLayoutManager(BeneficiaryReportActivity.this));
                            beneficiaryReportBinding.recyclerView.setAdapter(adapter);
                        } else {
                            mMenu.findItem(R.id.action_search).setVisible(false);
                            mMenu.findItem(R.id.mi_filter).setVisible(false);
                            beneficiaryReportBinding.tvEmpty.setVisibility(View.VISIBLE);
                            beneficiaryReportBinding.recyclerView.setVisibility(View.GONE);
                        }
                    } else if (beneficiaryDetails.getStatusCode() != null && beneficiaryDetails.getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {
                        beneficiaryReportBinding.tvEmpty.setVisibility(View.VISIBLE);
                        beneficiaryReportBinding.recyclerView.setVisibility(View.GONE);
                        Snackbar.make(beneficiaryReportBinding.root, beneficiaryDetails.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                    } else {
                        beneficiaryReportBinding.tvEmpty.setVisibility(View.VISIBLE);
                        beneficiaryReportBinding.recyclerView.setVisibility(View.GONE);
                        callSnackBar(getString(R.string.something));
                    }
                } else {
                    beneficiaryReportBinding.tvEmpty.setVisibility(View.VISIBLE);
                    beneficiaryReportBinding.recyclerView.setVisibility(View.GONE);
                    callSnackBar(getString(R.string.something));

                }
            }
        });
        viewModel.getSchemeInfo().observe(this, new Observer<List<SchemeEntity>>() {
            @Override
            public void onChanged(List<SchemeEntity> schemesInfoEntities) {
                schemesInfoEntitiesMain = schemesInfoEntities;
            }
        });
    }

    public void showSchemeDetails(List<SchemeEntity> schemesInfoEntitiesMain) {
        View view = getLayoutInflater().inflate(R.layout.scheme_bottom_sheet, null);
        RecyclerView filterRecyclerView = view.findViewById(R.id.schemeRV);
        dialog = new BottomSheetDialog(BeneficiaryReportActivity.this);
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
    public void onItemClick(String schemeID) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        tempBeneficiaryDetails.clear();

        if (beneficiaryDetailsMain.size() > 0) {
            if (schemeID.equals("-1")) {
                tempBeneficiaryDetails.addAll(beneficiaryDetailsMain);
            } else {
                for (BeneficiaryDetail beneficiaryDetail : beneficiaryDetailsMain) {
                    if (schemeID.equals(beneficiaryDetail.getSchemeId())) {
                        tempBeneficiaryDetails.add(beneficiaryDetail);
                    }
                }
            }

            if (tempBeneficiaryDetails.size() > 0) {
                beneficiaryReportBinding.tvEmpty.setVisibility(View.GONE);
                beneficiaryReportBinding.recyclerView.setVisibility(View.VISIBLE);
                adapter.setData(tempBeneficiaryDetails);
            } else {
                beneficiaryReportBinding.tvEmpty.setVisibility(View.VISIBLE);
                beneficiaryReportBinding.recyclerView.setVisibility(View.GONE);
            }
        } else {
            beneficiaryReportBinding.tvEmpty.setVisibility(View.VISIBLE);
            beneficiaryReportBinding.recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(BeneficiaryDetail beneficiaryDetail) {
        startActivity(new Intent(this, BenDetailsActivity.class)
                .putExtra(AppConstants.BEN_DETAIL, beneficiaryDetail));
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(beneficiaryReportBinding.root, msg, Snackbar.LENGTH_INDEFINITE);
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
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        callSnackBar(errMsg);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        mMenu = menu;
        MenuItem mSearch = mMenu.findItem(R.id.action_search);
        mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.search_hint) + "</font>"));
        mSearchView.setInputType(InputType.TYPE_CLASS_NUMBER);
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
        switch (item.getItemId()) {
            case R.id.mi_filter:
                if (!mSearchView.isIconified()) {
                    mSearchView.onActionViewCollapsed();
                }
                showSchemeDetails(schemesInfoEntitiesMain);
                break;

            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            boolean isAutomatic = Utils.isTimeAutomatic(this);
            if (!isAutomatic) {
                Utils.customTimeAlert(this,
                        getResources().getString(R.string.app_name),
                        getString(R.string.date_time));
                return;
            }

            currentDate = Utils.getCurrentDate();
            cacheDate = sharedPreferences.getString(AppConstants.CACHE_DATE, "");

            if (!TextUtils.isEmpty(cacheDate)) {
                if (!cacheDate.equalsIgnoreCase(currentDate)) {
                    editor.clear();
                    editor.commit();
                    instMainViewModel.deleteAllInspectionData();
                    Utils.ShowDeviceSessionAlert(this,
                            getResources().getString(R.string.app_name),
                            getString(R.string.ses_expire_re));
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cacheDate = currentDate;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.CACHE_DATE, cacheDate);
        editor.commit();
    }
}
