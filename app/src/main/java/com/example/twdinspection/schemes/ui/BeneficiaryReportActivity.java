package com.example.twdinspection.schemes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.R;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityBeneficiaryReportBinding;
import com.example.twdinspection.schemes.adapter.BenReportAdapter;
import com.example.twdinspection.schemes.adapter.SchemeInfoAdapter;
import com.example.twdinspection.schemes.interfaces.BenClickCallback;
import com.example.twdinspection.schemes.interfaces.SchemeClickCallback;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryRequest;
import com.example.twdinspection.schemes.source.schemes.SchemeEntity;
import com.example.twdinspection.schemes.viewmodel.BenReportViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class BeneficiaryReportActivity extends AppCompatActivity implements SchemeClickCallback, BenClickCallback {

    BenReportViewModel viewModel;
    BenReportAdapter adapter;
    ActivityBeneficiaryReportBinding beneficiaryReportBinding;
    private List<BeneficiaryDetail> beneficiaryDetailsMain;
    private List<BeneficiaryDetail> tempBeneficiaryDetails;
    private List<SchemeEntity> schemesInfoEntitiesMain;
    private BottomSheetDialog dialog;
    ArrayList<String> schemeValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beneficiaryDetailsMain = new ArrayList<>();
        tempBeneficiaryDetails = new ArrayList<>();
        schemesInfoEntitiesMain = new ArrayList<>();
        schemeValues = new ArrayList<>();

        beneficiaryReportBinding = DataBindingUtil.setContentView(this, R.layout.activity_beneficiary_report);
        beneficiaryReportBinding.header.headerTitle.setText(getResources().getString(R.string.ben_report));
        beneficiaryReportBinding.header.filetIv.setVisibility(View.VISIBLE);
        beneficiaryReportBinding.header.filetIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schemesInfoEntitiesMain.size() > 0) {
                    showSchemeDetails(schemesInfoEntitiesMain);
                } else {
                    //no data tag
                }
            }
        });

        viewModel = new BenReportViewModel(getApplication());
        beneficiaryReportBinding.setViewModel(viewModel);
        beneficiaryReportBinding.executePendingBindings();

        BeneficiaryRequest beneficiaryRequest = new BeneficiaryRequest();
        beneficiaryRequest.setDistId(1);
        beneficiaryRequest.setMandalId(3);
        beneficiaryRequest.setVillageId(2);
        StringBuilder str = new StringBuilder();
        str.append("'");
        beneficiaryRequest.setFinYearId(str + "2017-18" + str);

        viewModel.getBeneficiaryInfo(beneficiaryRequest).observe(this, beneficiaryDetails -> {
            beneficiaryDetailsMain = beneficiaryDetails;
            tempBeneficiaryDetails.addAll(beneficiaryDetailsMain);
            adapter = new BenReportAdapter(this, tempBeneficiaryDetails);
            beneficiaryReportBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            beneficiaryReportBinding.recyclerView.setAdapter(adapter);
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
//        dialog.setCancelable(false);
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
                beneficiaryReportBinding.recyclerView.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            } else {
                beneficiaryReportBinding.recyclerView.setVisibility(View.GONE);
                //no data tag
            }
        } else {
            //no data tag
        }
    }

    @Override
    public void onItemClick(BeneficiaryDetail beneficiaryDetail) {
        startActivity(new Intent(this, BenDetailsActivity.class)
                .putExtra(AppConstants.BEN_DETAIL, beneficiaryDetail));
    }
}
