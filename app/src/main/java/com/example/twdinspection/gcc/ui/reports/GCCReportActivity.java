package com.example.twdinspection.gcc.ui.reports;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.common.ErrorHandler;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.CustomProgressDialog;
import com.example.twdinspection.databinding.ActivityGccReportBinding;
import com.example.twdinspection.gcc.adapter.GCCReportAdapter;
import com.example.twdinspection.gcc.interfaces.ReportClickCallback;
import com.example.twdinspection.gcc.source.reports.ReportData;
import com.example.twdinspection.schemes.interfaces.BenClickCallback;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.example.twdinspection.schemes.interfaces.SchemeClickCallback;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;
import com.example.twdinspection.schemes.ui.BenDetailsActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customProgressDialog = new CustomProgressDialog(this);
        sharedPreferences= TWDApplication.get(this).getPreferences();
        editor=sharedPreferences.edit();

        gccReportBinding = DataBindingUtil.setContentView(this, R.layout.activity_gcc_report);
        gccReportBinding.executePendingBindings();
        gccReportBinding.header.headerTitle.setText(getString(R.string.gcc_reports));
        gccReportBinding.header.ivHome.setVisibility(View.GONE);

        reportData =new ArrayList<>();

        Gson gson=new Gson();
        String data=sharedPreferences.getString(AppConstants.Selected_Supp_Report,"");
        Type type = new TypeToken<List<ReportData>>() {}.getType();
        reportData = gson.fromJson(data, type);

        if(reportData!=null && reportData.size()>0){
            gccReportBinding.recyclerView.setVisibility(View.VISIBLE);
            gccReportBinding.tvEmpty.setVisibility(View.GONE);
            adapter = new GCCReportAdapter(this,reportData );
            gccReportBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            gccReportBinding.recyclerView.setAdapter(adapter);
        }else{
            gccReportBinding.recyclerView.setVisibility(View.GONE);
            gccReportBinding.tvEmpty.setVisibility(View.VISIBLE);
            callSnackBar("No data available");
        }

//        try {
//            if (getSupportActionBar() != null) {
////                getSupportActionBar().setTitle(getResources().getString(R.string.ben_report));
//                TextView tv = new TextView(getApplicationContext());
//                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//                        RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
//                        RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
//                tv.setLayoutParams(lp);
//                tv.setText(getResources().getString(R.string.ben_report));
//                tv.setGravity(Gravity.CENTER);
//                tv.setTextColor(Color.WHITE);
//                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//                getSupportActionBar().setCustomView(tv);
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
//                getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_btn_rounded));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

//    @Override
//    public void onItemClick(String schemeID) {
//
//        if (reportData.size() > 0) {
//            if (schemeID.equals("-1")) {
//                tempBeneficiaryDetails.addAll(beneficiaryDetailsMain);
//            } else {
//                for (BeneficiaryDetail beneficiaryDetail : beneficiaryDetailsMain) {
//                    if (schemeID.equals(beneficiaryDetail.getSchemeId())) {
//                        tempBeneficiaryDetails.add(beneficiaryDetail);
//                    }
//                }
//            }
//
//            if (tempBeneficiaryDetails.size() > 0) {
//                beneficiaryReportBinding.tvEmpty.setVisibility(View.GONE);
//                beneficiaryReportBinding.recyclerView.setVisibility(View.VISIBLE);
//                adapter.setData(tempBeneficiaryDetails);
//            } else {
//                beneficiaryReportBinding.tvEmpty.setVisibility(View.VISIBLE);
//                beneficiaryReportBinding.recyclerView.setVisibility(View.GONE);
//            }
//        } else {
//            beneficiaryReportBinding.tvEmpty.setVisibility(View.VISIBLE);
//            beneficiaryReportBinding.recyclerView.setVisibility(View.GONE);
//        }
//    }

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
        Gson gson=new Gson();
        String data=gson.toJson(reportData);
        editor.putString(AppConstants.REP_DATA,data);
        editor.apply();
        startActivity(new Intent(this, ReportStockDetailsActivity.class));
    }
}
