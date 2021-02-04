package com.cgg.twdinspection.gcc.ui.drgodown;

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

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityDrGodownBinding;
import com.cgg.twdinspection.gcc.source.offline.drgodown.DrGodownOffline;
import com.cgg.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.cgg.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.cgg.twdinspection.gcc.ui.fragment.DailyFragment;
import com.cgg.twdinspection.gcc.ui.fragment.EmptiesFragment;
import com.cgg.twdinspection.gcc.ui.fragment.EssentialFragment;
import com.cgg.twdinspection.gcc.ui.fragment.MFPFragment;
import com.cgg.twdinspection.gcc.ui.fragment.PUnitFragment;
import com.cgg.twdinspection.gcc.viewmodel.GCCOfflineViewModel;
import com.cgg.twdinspection.inspection.viewmodel.StockViewModel;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
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
    private boolean dailyreq_flag, emp_flag, ess_flag;
    private GCCOfflineViewModel gccOfflineViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_godown);
        customProgressDialog = new CustomProgressDialog(this);
        gccOfflineViewModel = new GCCOfflineViewModel(getApplication());
        stockDetailsResponsemain = null;
        EssentialFragment.commonCommodities = null;
        DailyFragment.commonCommodities = null;
        EmptiesFragment.commonCommodities = null;
        MFPFragment.commonCommodities = null;
        PUnitFragment.commonCommodities = null;

        binding.header.headerTitle.setText(getResources().getString(R.string.gcc_dr_godown));
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
                boolean existFlag = false;

                stockDetailsResponsemain.setEssential_commodities(EssentialFragment.commonCommodities);
                stockDetailsResponsemain.setDialy_requirements(DailyFragment.commonCommodities);
                stockDetailsResponsemain.setEmpties(EmptiesFragment.commonCommodities);

                if (EssentialFragment.commonCommodities != null && EssentialFragment.commonCommodities.size() > 0) {

                    for (int z = 0; z < stockDetailsResponsemain.getEssential_commodities().size(); z++) {
                        if (!TextUtils.isEmpty(stockDetailsResponsemain.getEssential_commodities().get(z).getPhyQuant())) {
//                            String header = stockDetailsResponsemain.getEssential_commodities().get(0).getComHeader();
                            existFlag = true;
//                            setFragPos(header, z);
                            break;
                        }
                    }
                }
                if (!existFlag && DailyFragment.commonCommodities != null && DailyFragment.commonCommodities.size() > 0) {

                    for (int z = 0; z < stockDetailsResponsemain.getDialy_requirements().size(); z++) {
                        if (!TextUtils.isEmpty(stockDetailsResponsemain.getDialy_requirements().get(z).getPhyQuant())) {
//                            String header = stockDetailsResponsemain.getDialy_requirements().get(0).getComHeader();
                            existFlag = true;
//                            setFragPos(header, z);
                            break;
                        }
                    }
                }


                if (!existFlag && EmptiesFragment.commonCommodities != null && EmptiesFragment.commonCommodities.size() > 0) {

                    for (int z = 0; z < stockDetailsResponsemain.getEmpties().size(); z++) {
                        if (!TextUtils.isEmpty(stockDetailsResponsemain.getEmpties().get(z).getPhyQuant())) {
//                            String header = stockDetailsResponsemain.getEmpties().get(0).getComHeader();
                            existFlag = true;
//                            setFragPos(header, z);
                            break;
                        }
                    }
                }

                if (existFlag) {

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


                } else {
                    Utils.customErrorAlert(DRGodownActivity.this, getResources().getString(R.string.app_name), getString(R.string.one_record));
                }
            }

        });


        LiveData<DrGodownOffline> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(
                drGodowns.getDivisionId(), drGodowns.getSocietyId(), drGodowns.getGodownId());
        drGodownLiveData.observe(DRGodownActivity.this, new Observer<DrGodownOffline>() {
            @Override
            public void onChanged(DrGodownOffline drGodowns) {
                drGodownLiveData.removeObservers(DRGodownActivity.this);
                String strStock = sharedPreferences.getString(AppConstants.StockDetailsResponse, "");
                StockDetailsResponse stockDetailsResponse = new Gson().fromJson(strStock, StockDetailsResponse.class);
                stockDetailsResponsemain = stockDetailsResponse;

                if (stockDetailsResponse != null) {

                    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

                    if (stockDetailsResponse.getEssential_commodities() != null && stockDetailsResponse.getEssential_commodities().size() > 0) {
                        ess_flag = true;
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
                        dailyreq_flag = true;
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
                        emp_flag = true;
                        stockDetailsResponse.getEmpties().get(0).setComHeader("Empties");
                        EmptiesFragment emptiesFragment = new EmptiesFragment();
                        Gson gson = new Gson();
                        String essentialComm = gson.toJson(stockDetailsResponse.getEmpties());
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.empties, essentialComm);
                        emptiesFragment.setArguments(bundle);
                        adapter.addFrag(emptiesFragment, "Empties");
                    }

                    if (ess_flag || dailyreq_flag || emp_flag) {
                        binding.viewPager.setVisibility(View.VISIBLE);
                        binding.tabs.setVisibility(View.VISIBLE);
                        binding.noDataTv.setVisibility(View.GONE);
                        binding.bottomLl.btnLayout.setVisibility(View.VISIBLE);
                        binding.tabs.setupWithViewPager(binding.viewPager);
                        binding.viewPager.setAdapter(adapter);
                    } else {
                        binding.viewPager.setVisibility(View.GONE);
                        binding.tabs.setVisibility(View.GONE);
                        binding.noDataTv.setVisibility(View.VISIBLE);
                        binding.bottomLl.btnLayout.setVisibility(View.GONE);
                        binding.noDataTv.setText("No data found");
                        callSnackBar("No data found");
                    }

                } else {
                    binding.viewPager.setVisibility(View.GONE);
                    binding.tabs.setVisibility(View.GONE);
                    binding.noDataTv.setVisibility(View.VISIBLE);
                    binding.bottomLl.btnLayout.setVisibility(View.GONE);
                    binding.noDataTv.setText(stockDetailsResponse.getStatusMessage());
                    callSnackBar(stockDetailsResponse.getStatusMessage());
                }
                if (!dailyreq_flag && !emp_flag && !ess_flag) {
                    binding.viewPager.setVisibility(View.GONE);
                    binding.tabs.setVisibility(View.GONE);
                    binding.noDataTv.setVisibility(View.VISIBLE);
                    binding.bottomLl.btnLayout.setVisibility(View.GONE);
                    binding.noDataTv.setText(stockDetailsResponse.getStatusMessage());
                    callSnackBar(stockDetailsResponse.getStatusMessage());
                }

            }
        });

    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }


    @Override
    public void onBackPressed() {
        Utils.customDiscardAlert(this,
                getResources().getString(R.string.app_name),
                getString(R.string.are_go_back));
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
