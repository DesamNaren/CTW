package com.example.twdinspection.gcc.ui.drgodown;

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
import com.example.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.example.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.example.twdinspection.gcc.ui.fragment.DailyFragment;
import com.example.twdinspection.gcc.ui.fragment.EmptiesFragment;
import com.example.twdinspection.gcc.ui.fragment.EssentialFragment;
import com.example.twdinspection.gcc.ui.fragment.MFPFragment;
import com.example.twdinspection.gcc.ui.fragment.PUnitFragment;
import com.example.twdinspection.inspection.viewmodel.StockViewModel;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DRGodownActivity extends AppCompatActivity implements ErrorHandlerInterface {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private StockViewModel viewModel;
    ActivityDrGodownBinding binding;
    private DrGodowns drGodowns;
    CustomProgressDialog customProgressDialog;
    private StockDetailsResponse stockDetailsResponsemain;
    private List<String> mFragmentTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_godown);
        customProgressDialog = new CustomProgressDialog(this);
        stockDetailsResponsemain = null;
        EssentialFragment.commonCommodities = null;
        DailyFragment.commonCommodities = null;
        EmptiesFragment.commonCommodities = null;
        MFPFragment.commonCommodities = null;
        PUnitFragment.commonCommodities = null;

        binding.header.headerTitle.setText(getResources().getString(R.string.dr_godown));
        binding.header.ivHome.setVisibility(View.GONE);
        viewModel = new StockViewModel(getApplication(), this);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

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
            Gson gson = new Gson();
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
                if (EssentialFragment.commonCommodities != null && EssentialFragment.commonCommodities.size() > 0) {
                    stockDetailsResponsemain.setEssential_commodities(EssentialFragment.commonCommodities);
                    for (int z = 0; z < stockDetailsResponsemain.getEssential_commodities().size(); z++) {
                        if (TextUtils.isEmpty(stockDetailsResponsemain.getEssential_commodities().get(z).getPhyQuant())) {
                            String header = stockDetailsResponsemain.getEssential_commodities().get(0).getComHeader();
                            setFragPos(header, z);
                            return;
                        }
                    }
                }
                if (DailyFragment.commonCommodities != null && DailyFragment.commonCommodities.size() > 0) {
                    stockDetailsResponsemain.setDialy_requirements(DailyFragment.commonCommodities);
                    for (int z = 0; z < stockDetailsResponsemain.getDialy_requirements().size(); z++) {
                        if (TextUtils.isEmpty(stockDetailsResponsemain.getDialy_requirements().get(z).getPhyQuant())) {
                            String header = stockDetailsResponsemain.getDialy_requirements().get(0).getComHeader();
                            setFragPos(header, z);
                            return;
                        }
                    }
                }


                if (EmptiesFragment.commonCommodities != null && EmptiesFragment.commonCommodities.size() > 0) {
                    stockDetailsResponsemain.setEmpties(EmptiesFragment.commonCommodities);
                    for (int z = 0; z < stockDetailsResponsemain.getEmpties().size(); z++) {
                        if (TextUtils.isEmpty(stockDetailsResponsemain.getEmpties().get(z).getPhyQuant())) {
                            String header = stockDetailsResponsemain.getEmpties().get(0).getComHeader();
                            setFragPos(header, z);
                            return;
                        }
                    }
                }


                if (MFPFragment.commonCommodities != null && MFPFragment.commonCommodities.size() > 0) {
                    stockDetailsResponsemain.setMfp_commodities(MFPFragment.commonCommodities);
                    for (int z = 0; z < stockDetailsResponsemain.getMfp_commodities().size(); z++) {
                        if (TextUtils.isEmpty(stockDetailsResponsemain.getMfp_commodities().get(z).getPhyQuant())) {
                            String header = stockDetailsResponsemain.getMfp_commodities().get(0).getComHeader();
                            setFragPos(header, z);
                            return;
                        }
                    }
                }

                if (PUnitFragment.commonCommodities != null && PUnitFragment.commonCommodities.size() > 0) {
                    stockDetailsResponsemain.setProcessing_units(PUnitFragment.commonCommodities);
                    for (int z = 0; z < stockDetailsResponsemain.getProcessing_units().size(); z++) {
                        if (TextUtils.isEmpty(stockDetailsResponsemain.getProcessing_units().get(z).getPhyQuant())) {
                            String header = stockDetailsResponsemain.getProcessing_units().get(0).getComHeader();
                            setFragPos(header, z);
                            return;
                        }
                    }
                }

                Gson gson = new Gson();
                String stockData = gson.toJson(stockDetailsResponsemain);
                try {
                    editor = TWDApplication.get(DRGodownActivity.this).getPreferences().edit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                editor.putString(AppConstants.stockData, stockData);
                editor.commit();
                Intent intent = new Intent(DRGodownActivity.this, DRGodownFindingsActivity.class);
                startActivity(intent);
            }

        });

        if (Utils.checkInternetConnection(DRGodownActivity.this)) {
            if (drGodowns != null && drGodowns.getGodownId() != null) {
                customProgressDialog.show();
                LiveData<StockDetailsResponse> officesResponseLiveData = viewModel.getStockData(drGodowns.getGodownId());
                officesResponseLiveData.observe(DRGodownActivity.this, new Observer<StockDetailsResponse>() {
                    @Override
                    public void onChanged(StockDetailsResponse stockDetailsResponse) {

                        customProgressDialog.hide();
                        officesResponseLiveData.removeObservers(DRGodownActivity.this);
                        stockDetailsResponsemain = stockDetailsResponse;

                        if (stockDetailsResponse != null && stockDetailsResponse.getStatusCode() != null) {
                            if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                binding.viewPager.setVisibility(View.VISIBLE);
                                binding.tabs.setVisibility(View.VISIBLE);
                                binding.noDataTv.setVisibility(View.GONE);
                                binding.bottomLl.btnLayout.setVisibility(View.VISIBLE);
                                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

                                if (stockDetailsResponse.getEssential_commodities() != null && stockDetailsResponse.getEssential_commodities().size() > 0) {
                                    stockDetailsResponse.getEssential_commodities().get(0).setComHeader("Essential Commodities");
                                    EssentialFragment essentialFragment = new EssentialFragment();
                                    Gson gson = new Gson();
                                    String essentialComm = gson.toJson(stockDetailsResponse.getEssential_commodities());
                                    Bundle bundle = new Bundle();
                                    bundle.putString(AppConstants.essComm, essentialComm);
                                    essentialFragment.setArguments(bundle);
                                    adapter.addFrag(essentialFragment, "Essential Commodities");
                                }

                                if (stockDetailsResponse.getDialy_requirements() != null && stockDetailsResponse.getDialy_requirements().size() > 0) {
                                    stockDetailsResponse.getDialy_requirements().get(0).setComHeader("Daily Requirements");
                                    DailyFragment dailyFragment = new DailyFragment();
                                    Gson gson = new Gson();
                                    String essentialComm = gson.toJson(stockDetailsResponse.getDialy_requirements());
                                    Bundle bundle = new Bundle();
                                    bundle.putString(AppConstants.dailyReq, essentialComm);
                                    dailyFragment.setArguments(bundle);
                                    adapter.addFrag(dailyFragment, "Daily Requirements");
                                }

                                if (stockDetailsResponse.getEmpties() != null && stockDetailsResponse.getEmpties().size() > 0) {
                                    stockDetailsResponse.getEmpties().get(0).setComHeader("Empties");
                                    EmptiesFragment emptiesFragment = new EmptiesFragment();
                                    Gson gson = new Gson();
                                    String essentialComm = gson.toJson(stockDetailsResponse.getEmpties());
                                    Bundle bundle = new Bundle();
                                    bundle.putString(AppConstants.empties, essentialComm);
                                    emptiesFragment.setArguments(bundle);
                                    adapter.addFrag(emptiesFragment, "Empties");
                                }


                                if (stockDetailsResponse.getMfp_commodities() != null && stockDetailsResponse.getMfp_commodities().size() > 0) {
                                    stockDetailsResponse.getMfp_commodities().get(0).setComHeader("MFP Commodities");
                                    MFPFragment mfpFragment = new MFPFragment();
                                    Gson gson = new Gson();
                                    String essentialComm = gson.toJson(stockDetailsResponse.getMfp_commodities());
                                    Bundle bundle = new Bundle();
                                    bundle.putString(AppConstants.mfp, essentialComm);
                                    mfpFragment.setArguments(bundle);
                                    adapter.addFrag(mfpFragment, "MFP Commodities");
                                }

                                if (stockDetailsResponse.getProcessing_units() != null && stockDetailsResponse.getProcessing_units().size() > 0) {
                                    stockDetailsResponse.getProcessing_units().get(0).setComHeader("Processing Units");
                                    PUnitFragment pUnitFragment = new PUnitFragment();
                                    Gson gson = new Gson();
                                    String essentialComm = gson.toJson(stockDetailsResponse.getProcessing_units());
                                    Bundle bundle = new Bundle();
                                    bundle.putString(AppConstants.punit, essentialComm);
                                    pUnitFragment.setArguments(bundle);
                                    adapter.addFrag(pUnitFragment, "Processing Units");
                                }

                                binding.tabs.setupWithViewPager(binding.viewPager);
                                binding.viewPager.setAdapter(adapter);

                            } else if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                binding.viewPager.setVisibility(View.GONE);
                                binding.tabs.setVisibility(View.GONE);
                                binding.noDataTv.setVisibility(View.VISIBLE);
                                binding.bottomLl.btnLayout.setVisibility(View.GONE);
                                binding.noDataTv.setText(stockDetailsResponse.getStatusMessage());
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
                Utils.customWarningAlert(DRGodownActivity.this, getResources().getString(R.string.app_name), getString(R.string.something));
            }
        } else {
            Utils.customWarningAlert(DRGodownActivity.this, getResources().getString(R.string.app_name), "Please check internet");
        }


    }

    void setFragPos(String header, int pos) {
        for (int x = 0; x < mFragmentTitleList.size(); x++) {
            if (header.equalsIgnoreCase(mFragmentTitleList.get(x))) {
                callSnackBar("Submit all records in " + header);
                binding.viewPager.setCurrentItem(x);
                if (header.contains("Essential Commodities")) {
                    ((EssentialFragment) mFragmentList.get(x)).setPos(pos);
                }
                if (header.equalsIgnoreCase("Daily Requirements")) {
                    ((DailyFragment) mFragmentList.get(x)).setPos(pos);
                }
                if (header.equalsIgnoreCase("Empties")) {
                    ((EmptiesFragment) mFragmentList.get(x)).setPos(pos);
                }
                if (header.equalsIgnoreCase("MFP Commodities")) {
                    ((MFPFragment) mFragmentList.get(x)).setPos(pos);
                }
                if (header.equalsIgnoreCase("Processing Units")) {
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
        if (stockDetailsResponsemain.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
            Utils.customDiscardAlert(this,
                    getResources().getString(R.string.app_name),
                    getString(R.string.are_go_back));
        } else {
            finish();
        }
    }

    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        Log.i("MSG", "handleError: " + errMsg);
        callSnackBar(errMsg);
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