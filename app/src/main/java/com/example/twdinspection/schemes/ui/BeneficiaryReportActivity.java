package com.example.twdinspection.schemes.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.R;
import com.example.twdinspection.common.ErrorHandler;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.CustomProgressDialog;
import com.example.twdinspection.databinding.ActivityBeneficiaryReportBinding;
import com.example.twdinspection.schemes.adapter.BenReportAdapter;
import com.example.twdinspection.schemes.adapter.SchemeInfoAdapter;
import com.example.twdinspection.schemes.interfaces.BenClickCallback;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.example.twdinspection.schemes.interfaces.SchemeClickCallback;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customProgressDialog = new CustomProgressDialog(this);

        beneficiaryDetailsMain = new ArrayList<>();
        tempBeneficiaryDetails = new ArrayList<>();
        schemesInfoEntitiesMain = new ArrayList<>();
        schemeValues = new ArrayList<>();

        beneficiaryReportBinding = DataBindingUtil.setContentView(this, R.layout.activity_beneficiary_report);

        try {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getResources().getString(R.string.ben_report));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
                getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>BENEFICIARY REPORT</font>"));
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
        beneficiaryRequest.setDistId(1);
        beneficiaryRequest.setMandalId(3);
        beneficiaryRequest.setVillageId(2);
        StringBuilder str = new StringBuilder();
        str.append("'");
        beneficiaryRequest.setFinYearId(str + "2017-18" + str);

        customProgressDialog.show();
        viewModel.getBeneficiaryInfo(beneficiaryRequest).observe(this, beneficiaryDetails -> {

            customProgressDialog.hide();
            if (beneficiaryDetails != null && beneficiaryDetails.getStatusCode() != null) {
                if (beneficiaryDetails.getStatusCode() != null && beneficiaryDetails.getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
                    beneficiaryDetailsMain = beneficiaryDetails.getBeneficiaryDetails();
                    tempBeneficiaryDetails.addAll(beneficiaryDetailsMain);
                    if (tempBeneficiaryDetails.size() > 0) {
                        beneficiaryReportBinding.tvEmpty.setVisibility(View.GONE);
                        beneficiaryReportBinding.recyclerView.setVisibility(View.VISIBLE);
                        adapter = new BenReportAdapter(this, tempBeneficiaryDetails);
                        beneficiaryReportBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        beneficiaryReportBinding.recyclerView.setAdapter(adapter);
                    } else {
                        beneficiaryReportBinding.tvEmpty.setVisibility(View.VISIBLE);
                        beneficiaryReportBinding.recyclerView.setVisibility(View.GONE);
                    }
                } else if (beneficiaryDetails.getStatusCode() != null && beneficiaryDetails.getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {

                    Snackbar.make(beneficiaryReportBinding.root, beneficiaryDetails.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                } else {
                    callSnackBar(getString(R.string.something));
                }
            } else {
                callSnackBar(getString(R.string.something));

            }
        });

        viewModel.getSchemeInfo().observe(this, schemesInfoEntities -> {
            schemesInfoEntitiesMain = schemesInfoEntities;
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
                adapter.notifyDataSetChanged();
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
        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.search_hint) + "</font>"));

        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
