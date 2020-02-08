package com.example.twdinspection.gcc.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.twdinspection.gcc.ui.fragment.DailyFragment;
import com.example.twdinspection.gcc.ui.fragment.EmptiesFragment;
import com.example.twdinspection.gcc.ui.fragment.EssentialFragment;
import com.example.twdinspection.gcc.ui.fragment.MFPFragment;
import com.example.twdinspection.gcc.ui.fragment.PUnitFragment;
import com.example.twdinspection.inspection.viewmodel.StockViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DRDepotActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private StockViewModel viewModel;
    ActivityDrDepotBinding binding;
    CustomProgressDialog customProgressDialog;
    private StockDetailsResponse stockDetailsResponse;
    private List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_depot);
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

        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stockDetailsResponse != null && stockDetailsResponse.getStatusCode() != null) {
                    if (stockDetailsResponse.getEssential_commodities() != null && stockDetailsResponse.getEssential_commodities().size() > 0) {
                        for (int z = 0; z < stockDetailsResponse.getEssential_commodities().size(); z++) {
                            if (TextUtils.isEmpty(stockDetailsResponse.getEssential_commodities().get(z).getPhyQuant())) {
                                String header = stockDetailsResponse.getEssential_commodities().get(0).getComHeader();
                                setFragPos(header, z);
                                return;
                            }
                        }
                    }

                    if (stockDetailsResponse.getDialy_requirements() != null && stockDetailsResponse.getDialy_requirements().size() > 0) {
                        for (int z = 0; z < stockDetailsResponse.getDialy_requirements().size(); z++) {
                            if (TextUtils.isEmpty(stockDetailsResponse.getDialy_requirements().get(z).getPhyQuant())) {
                                String header = stockDetailsResponse.getDialy_requirements().get(0).getComHeader();
                                setFragPos(header, z);
                                return;
                            }
                        }
                    }

                    if (stockDetailsResponse.getEmpties() != null && stockDetailsResponse.getEmpties().size() > 0) {
                        for (int z = 0; z < stockDetailsResponse.getEmpties().size(); z++) {
                            if (TextUtils.isEmpty(stockDetailsResponse.getEmpties().get(z).getPhyQuant())) {
                                String header = stockDetailsResponse.getEmpties().get(0).getComHeader();
                                setFragPos(header, z);
                                return;
                            }
                        }
                    }

                    if (stockDetailsResponse.getMfp_commodities() != null && stockDetailsResponse.getMfp_commodities().size() > 0) {
                        for (int z = 0; z < stockDetailsResponse.getMfp_commodities().size(); z++) {
                            if (TextUtils.isEmpty(stockDetailsResponse.getMfp_commodities().get(z).getPhyQuant())) {
                                String header = stockDetailsResponse.getMfp_commodities().get(0).getComHeader();
                                setFragPos(header, z);
                                return;
                            }
                        }
                    }

                    if (stockDetailsResponse.getProcessing_units() != null && stockDetailsResponse.getProcessing_units().size() > 0) {
                        for (int z = 0; z < stockDetailsResponse.getProcessing_units().size(); z++) {
                            if (TextUtils.isEmpty(stockDetailsResponse.getProcessing_units().get(z).getPhyQuant())) {
                                String header = stockDetailsResponse.getProcessing_units().get(0).getComHeader();
                                setFragPos(header, z);
                                return;
                            }
                        }
                    }

                    startActivity(new Intent(DRDepotActivity.this, DRDepotFindingsActivity.class));
                }
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
                    DRDepotActivity.this.stockDetailsResponse = stockDetailsResponse;

                    if (stockDetailsResponse != null && stockDetailsResponse.getStatusCode() != null) {
                        if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

                            if (stockDetailsResponse.getEssential_commodities() != null && stockDetailsResponse.getEssential_commodities().size() > 0) {
                                stockDetailsResponse.getEssential_commodities().get(0).setComHeader("Essential Commodities");
                                adapter.addFrag(new EssentialFragment().newInstance((ArrayList<CommonCommodity>) stockDetailsResponse.getEssential_commodities()), "Essential Commodities");
                            }

                            if (stockDetailsResponse.getDialy_requirements() != null && stockDetailsResponse.getDialy_requirements().size() > 0) {
                                stockDetailsResponse.getDialy_requirements().get(0).setComHeader("Daily Requirements");
                                adapter.addFrag(new DailyFragment().newInstance((ArrayList<CommonCommodity>) stockDetailsResponse.getDialy_requirements()), "Daily Requirements");
                            }

                            if (stockDetailsResponse.getEmpties() != null && stockDetailsResponse.getEmpties().size() > 0) {
                                stockDetailsResponse.getEmpties().get(0).setComHeader("Empties");
                                adapter.addFrag(new EmptiesFragment().newInstance((ArrayList<CommonCommodity>) stockDetailsResponse.getEmpties()), "Empties");
                            }
                            if (stockDetailsResponse.getMfp_commodities() != null && stockDetailsResponse.getMfp_commodities().size() > 0) {
                                stockDetailsResponse.getMfp_commodities().get(0).setComHeader("MFP Commodities");
                                adapter.addFrag(new MFPFragment().newInstance((ArrayList<CommonCommodity>) stockDetailsResponse.getMfp_commodities()), "MFP Commodities");
                            }
                            if (stockDetailsResponse.getProcessing_units() != null && stockDetailsResponse.getProcessing_units().size() > 0) {
                                stockDetailsResponse.getProcessing_units().get(0).setComHeader("Processing Units");
                                adapter.addFrag(new PUnitFragment().newInstance((ArrayList<CommonCommodity>) stockDetailsResponse.getProcessing_units()), "Processing Units");
                            }

                            binding.tabs.setupWithViewPager(binding.viewpager);
                            binding.viewpager.setAdapter(adapter);

                        } else if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                            callSnackBar(stockDetailsResponse.getStatusMessage());
                        } else {
                            callSnackBar(getString(R.string.something));
                        }
                    } else {
                        callSnackBar(getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customWarningAlert(DRDepotActivity.this, getResources().getString(R.string.app_name), "Please check internet");
        }

    }


    void setFragPos(String header, int pos) {
        for (int x = 0; x < mFragmentTitleList.size(); x++) {
            if (header.equalsIgnoreCase(mFragmentTitleList.get(x))) {
                callSnackBar("Submit all records in "+header);
                binding.viewpager.setCurrentItem(x);
                if (header.contains("Essential Commodities")) {
                    ((EssentialFragment) mFragmentList.get(x)).setPos(pos);
                } else if (header.equalsIgnoreCase("Daily Requirements")) {
                    ((DailyFragment) mFragmentList.get(x)).setPos(pos);
                } else if (header.equalsIgnoreCase("Empties")) {
                    ((EmptiesFragment) mFragmentList.get(x)).setPos(pos);
                } else if (header.equalsIgnoreCase("MFP Commodities")) {
                    ((MFPFragment) mFragmentList.get(x)).setPos(pos);
                } else if (header.equalsIgnoreCase("Processing Units")) {
                    ((PUnitFragment) mFragmentList.get(x)).setPos(pos);
                }
                break;
            }
        }
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        finish();
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
