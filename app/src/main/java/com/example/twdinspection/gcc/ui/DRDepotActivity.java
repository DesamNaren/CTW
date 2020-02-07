package com.example.twdinspection.gcc.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.CustomProgressDialog;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityDrDepotBinding;
import com.example.twdinspection.gcc.source.stock.CommonCommodity;
import com.example.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.example.twdinspection.inspection.viewmodel.StockViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DRDepotActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private StockViewModel viewModel;
    ActivityDrDepotBinding binding;
    CustomProgressDialog customProgressDialog;

    List<CommonCommodity> listDataChild;

    private ArrayList<String> tabs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_depot);
        listDataChild = new ArrayList<>();
        tabs = new ArrayList<>();

        binding.header.headerTitle.setText(getResources().getString(R.string.dr_depot));
        customProgressDialog = new CustomProgressDialog(this);
        viewModel = new StockViewModel(getApplication());
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        binding.includeBasicLayout.drGodownNameTV.setText(getString(R.string.dr_depo_name));
        binding.includeBasicLayout.salesLL.setVisibility(View.VISIBLE);
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Utils.checkInternetConnection(DRDepotActivity.this)) {
            customProgressDialog.show();
            LiveData<StockDetailsResponse> officesResponseLiveData = viewModel.getStockData("2054");
            officesResponseLiveData.observe(DRDepotActivity.this, new Observer<StockDetailsResponse>() {
                @Override
                public void onChanged(StockDetailsResponse stockDetailsResponse) {
                    customProgressDialog.hide();
                    officesResponseLiveData.removeObservers(DRDepotActivity.this);


                    if (stockDetailsResponse != null && stockDetailsResponse.getStatusCode() != null) {
                        if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

                            if (stockDetailsResponse.getEcsCommodities() != null && stockDetailsResponse.getEcsCommodities().size() > 0) {
                                tabs.add("Essential Commodities");
                                adapter.addFrag(new EssentialFragment().newInstance((ArrayList<CommonCommodity>) stockDetailsResponse.getEcsCommodities()), "Essential Commodities");
                                stockDetailsResponse.getEcsCommodities().get(0).setComHeader("Essential Commodities");
                                listDataChild.addAll(stockDetailsResponse.getEcsCommodities());
                            }


                            if (stockDetailsResponse.getDrsCommodities() != null && stockDetailsResponse.getDrsCommodities().size() > 0) {
                                adapter.addFrag(new DailyFragment().newInstance((ArrayList<CommonCommodity>) stockDetailsResponse.getDrsCommodities()), "Daily Requirements");
                                stockDetailsResponse.getDrsCommodities().get(0).setComHeader("Daily Requirements");
                                listDataChild.addAll(stockDetailsResponse.getDrsCommodities());
                            }

                            if (stockDetailsResponse.getEmptiesCommodities() != null && stockDetailsResponse.getEmptiesCommodities().size() > 0) {
                                adapter.addFrag(new EssentialFragment(), "Empties");
                                stockDetailsResponse.getEmptiesCommodities().get(0).setComHeader("Empties");
                                listDataChild.addAll(stockDetailsResponse.getEmptiesCommodities());
                            }
                            if (stockDetailsResponse.getMfpCommodities() != null && stockDetailsResponse.getMfpCommodities().size() > 0) {
                                adapter.addFrag(new EssentialFragment(), "MFP Commodities");
                                stockDetailsResponse.getMfpCommodities().get(0).setComHeader("MFP Commodities");
                                listDataChild.addAll(stockDetailsResponse.getMfpCommodities());
                            }
                            if (stockDetailsResponse.getApCommodities() != null && stockDetailsResponse.getApCommodities().size() > 0) {
                                adapter.addFrag(new EssentialFragment(), "Processing Units");
                                stockDetailsResponse.getApCommodities().get(0).setComHeader("Processing Units");
                                listDataChild.addAll(stockDetailsResponse.getApCommodities());
                            }

                            binding.tabs.setupWithViewPager(binding.viewpager);
                            binding.viewpager.setAdapter(adapter);


//                            StockSubAdapter stockSubAdapter = new StockSubAdapter(DRDepotActivity.this, listDataChild);
//                            binding.stockRV.setLayoutManager(new LinearLayoutManager(DRDepotActivity.this));
//                            binding.stockRV.setAdapter(stockSubAdapter);

                        } else if (stockDetailsResponse.getStatusCode().

                                equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
//                            Snackbar.make(binding.header, stockDetailsResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                        } else {
//                            callSnackBar(getString(R.string.something));
                        }
                    } else {
//                        callSnackBar(getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customWarningAlert(DRDepotActivity.this, getResources().getString(R.string.app_name), "Please check internet");
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

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
