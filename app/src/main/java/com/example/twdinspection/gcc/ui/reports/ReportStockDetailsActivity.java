package com.example.twdinspection.gcc.ui.reports;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.twdinspection.R;
import com.example.twdinspection.common.ErrorHandler;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.CustomProgressDialog;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityDrGodownBinding;
import com.example.twdinspection.databinding.ActivityReportStockDetailsBinding;
import com.example.twdinspection.gcc.source.reports.ReportData;
import com.example.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.example.twdinspection.gcc.ui.drgodown.DRGodownFindingsActivity;
import com.example.twdinspection.gcc.ui.fragment.DailyFragment;
import com.example.twdinspection.gcc.ui.fragment.DailyReportFragment;
import com.example.twdinspection.gcc.ui.fragment.EmptiesFragment;
import com.example.twdinspection.gcc.ui.fragment.EmptiesReportFragment;
import com.example.twdinspection.gcc.ui.fragment.EssentialFragment;
import com.example.twdinspection.gcc.ui.fragment.EssentialReportFragment;
import com.example.twdinspection.gcc.ui.fragment.MFPFragment;
import com.example.twdinspection.gcc.ui.fragment.MFPReportFragment;
import com.example.twdinspection.gcc.ui.fragment.PUnitFragment;
import com.example.twdinspection.gcc.ui.fragment.PUnitReportFragment;
import com.example.twdinspection.inspection.viewmodel.StockViewModel;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReportStockDetailsActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ActivityReportStockDetailsBinding binding;
    private DrGodowns drGodowns;
    private List<String> mFragmentTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    ReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_stock_details);
        EssentialFragment.commonCommodities = null;
        DailyFragment.commonCommodities = null;
        EmptiesFragment.commonCommodities = null;
        MFPFragment.commonCommodities = null;
        PUnitFragment.commonCommodities = null;

        sharedPreferences=TWDApplication.get(ReportStockDetailsActivity.this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.REP_DATA, "");
        reportData = gson.fromJson(data, ReportData.class);

        binding.header.headerTitle.setText(getResources().getString(R.string.dr_godown));
        binding.header.ivHome.setVisibility(View.GONE);
        binding.includeBasicLayout.divLL.setVisibility(View.VISIBLE);
        binding.includeBasicLayout.socLL.setVisibility(View.VISIBLE);
        binding.includeBasicLayout.drGodownLL.setVisibility(View.VISIBLE);
        binding.includeBasicLayout.inchargeLL.setVisibility(View.VISIBLE);

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            String str = sharedPreferences.getString(AppConstants.DR_GODOWN_DATA, "");
            drGodowns = gson.fromJson(str, DrGodowns.class);
            if (drGodowns != null) {
                binding.includeBasicLayout.divName.setText(drGodowns.getDivisionName());
                binding.includeBasicLayout.socName.setText(drGodowns.getSocietyName());
                binding.includeBasicLayout.drGodownName.setText(drGodowns.getGodownName());
                binding.includeBasicLayout.inchargeName.setText(drGodowns.getIncharge());
                binding.includeBasicLayout.dateTv.setText(Utils.getCurrentDateTimeDisplay());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reportData.getInspectionFindings().getDrGodown()!=null){
                    Intent intent = new Intent(ReportStockDetailsActivity.this, DrGodownInspRepActivity.class);
                    startActivity(intent);
                }else if(reportData.getInspectionFindings().getDrDepot()!=null){
                    Intent intent = new Intent(ReportStockDetailsActivity.this, DRGodownFindingsActivity.class);
                    startActivity(intent);
                }else if(reportData.getInspectionFindings().getMfpGodowns()!=null){
                    Intent intent = new Intent(ReportStockDetailsActivity.this, DRGodownFindingsActivity.class);
                    startActivity(intent);
                }else if(reportData.getInspectionFindings().getProcessingUnit()!=null){
                    Intent intent = new Intent(ReportStockDetailsActivity.this, DRGodownFindingsActivity.class);
                    startActivity(intent);
                }else{
                    callSnackBar("No Inspection data found");
                }
            }

        });

        if (reportData != null && reportData.getStockDetails() != null) {
            binding.viewPager.setVisibility(View.VISIBLE);
            binding.tabs.setVisibility(View.VISIBLE);
            binding.noDataTv.setVisibility(View.GONE);
            binding.bottomLl.btnLayout.setVisibility(View.VISIBLE);
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

            if (reportData.getStockDetails().getEssentialCommodities() != null && reportData.getStockDetails().getEssentialCommodities().size() > 0) {
                EssentialReportFragment essentialFragment = new EssentialReportFragment();
                String essentialComm = gson.toJson(reportData.getStockDetails().getEssentialCommodities());
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.essRepComm, essentialComm);
                essentialFragment.setArguments(bundle);
                adapter.addFrag(essentialFragment, "Essential Commodities");
            }

            if (reportData.getStockDetails().getDailyRequirements() != null && reportData.getStockDetails().getDailyRequirements().size() > 0) {
                DailyReportFragment dailyFragment = new DailyReportFragment();
                String essentialComm = gson.toJson(reportData.getStockDetails().getDailyRequirements());
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.dailyRepReq, essentialComm);
                dailyFragment.setArguments(bundle);
                adapter.addFrag(dailyFragment, "Daily Requirements");
            }

            if (reportData.getStockDetails().getEmpties() != null && reportData.getStockDetails().getEmpties().size() > 0) {
                EmptiesReportFragment emptiesFragment = new EmptiesReportFragment();
                String essentialComm = gson.toJson(reportData.getStockDetails().getEmpties());
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.emptiesRep, essentialComm);
                emptiesFragment.setArguments(bundle);
                adapter.addFrag(emptiesFragment, "Empties");
            }


            if (reportData.getStockDetails().getMfpCommodities() != null && reportData.getStockDetails().getMfpCommodities().size() > 0) {
                MFPReportFragment mfpFragment = new MFPReportFragment();
                String essentialComm = gson.toJson(reportData.getStockDetails().getMfpCommodities());
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.mfpRep, essentialComm);
                mfpFragment.setArguments(bundle);
                adapter.addFrag(mfpFragment, "MFP Commodities");
            }

            if (reportData.getStockDetails().getProcessingUnits() != null && reportData.getStockDetails().getProcessingUnits().size() > 0) {
                PUnitReportFragment pUnitFragment = new PUnitReportFragment();
                String essentialComm = gson.toJson(reportData.getStockDetails().getProcessingUnits());
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.punitRep, essentialComm);
                pUnitFragment.setArguments(bundle);
                adapter.addFrag(pUnitFragment, "Processing Units");
            }

            binding.tabs.setupWithViewPager(binding.viewPager);
            binding.viewPager.setAdapter(adapter);

        } else if (reportData != null ) {
            binding.viewPager.setVisibility(View.GONE);
            binding.tabs.setVisibility(View.GONE);
            binding.noDataTv.setVisibility(View.VISIBLE);
            binding.bottomLl.btnLayout.setVisibility(View.GONE);
            callSnackBar(getString(R.string.something));

        } else {
            callSnackBar(getString(R.string.something));
        }

}


    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

class ViewPagerAdapter extends FragmentPagerAdapter {


    ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}

}
