package com.cgg.twdinspection.gcc.ui.punit;

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
import com.cgg.twdinspection.databinding.ActivityPUnitBinding;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.cgg.twdinspection.gcc.source.suppliers.punit.PUnits;
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

public class PUnitActivity extends AppCompatActivity implements ErrorHandlerInterface {
    private SharedPreferences.Editor editor;
    ActivityPUnitBinding binding;
    CustomProgressDialog customProgressDialog;
    private StockDetailsResponse stockDetailsResponseMain;
    private PUnits pUnits;
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private boolean punit_flag, dailyreq_flag, emp_flag, ess_flag, mfp_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_p_unit);
        binding.header.headerTitle.setText(getResources().getString(R.string.gcc_p_unit));

        stockDetailsResponseMain = null;
        EssentialFragment.commonCommodities = null;
        DailyFragment.commonCommodities = null;
        EmptiesFragment.commonCommodities = null;
        MFPFragment.commonCommodities = null;
        PUnitFragment.commonCommodities = null;

        customProgressDialog = new CustomProgressDialog(this);
        StockViewModel viewModel = new StockViewModel(getApplication(), this);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        binding.header.ivHome.setVisibility(View.GONE);
        SharedPreferences sharedPreferences = TWDApplication.get(this).getPreferences();
        binding.includeBasicLayout.drGodownNameTV.setText(getString(R.string.man_unit_name));
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.includeBasicLayout.drGodownNameTV.setText(getString(R.string.man_unit_name));
        binding.includeBasicLayout.drGodownLL.setVisibility(View.VISIBLE);
        binding.includeBasicLayout.inchargeLL.setVisibility(View.VISIBLE);

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            Gson gson = new Gson();
            String str = sharedPreferences.getString(AppConstants.P_UNIT_DATA, "");
            pUnits = gson.fromJson(str, PUnits.class);
            if (pUnits != null) {
                binding.includeBasicLayout.drGodownName.setText(pUnits.getGodownName());
                binding.includeBasicLayout.inchargeName.setText(pUnits.getIncharge());
                binding.includeBasicLayout.dateTv.setText(Utils.getCurrentDateTimeDisplay());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        GCCOfflineViewModel gccOfflineViewModel = new GCCOfflineViewModel(getApplication());
        LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(
                pUnits.getDivisionId(), pUnits.getSocietyId(), pUnits.getGodownId());

        drGodownLiveData.observe(PUnitActivity.this, new Observer<GccOfflineEntity>() {
            @Override
            public void onChanged(GccOfflineEntity gccOfflineEntity) {
                binding.header.ivMode.setVisibility(View.VISIBLE);
                if (gccOfflineEntity != null) {
                    binding.header.ivMode.setBackground(getResources().getDrawable(R.drawable.offline_mode));
                } else {
                    binding.header.ivMode.setBackground(getResources().getDrawable(R.drawable.online_mode));
                }
            }
        });

        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean existFlag = false;
                stockDetailsResponseMain.setEssential_commodities(EssentialFragment.commonCommodities);
                stockDetailsResponseMain.setDialy_requirements(DailyFragment.commonCommodities);
                stockDetailsResponseMain.setEmpties(EmptiesFragment.commonCommodities);
                stockDetailsResponseMain.setMfp_commodities(MFPFragment.commonCommodities);
                stockDetailsResponseMain.setProcessing_units(PUnitFragment.commonCommodities);
                if (EssentialFragment.commonCommodities != null && EssentialFragment.commonCommodities.size() > 0) {

                    for (int z = 0; z < stockDetailsResponseMain.getEssential_commodities().size(); z++) {
                        if (!TextUtils.isEmpty(stockDetailsResponseMain.getEssential_commodities().get(z).getPhyQuant())) {
                            existFlag = true;
                            break;
                        }
                    }
                }
                if (!existFlag && DailyFragment.commonCommodities != null && DailyFragment.commonCommodities.size() > 0) {

                    for (int z = 0; z < stockDetailsResponseMain.getDialy_requirements().size(); z++) {
                        if (!TextUtils.isEmpty(stockDetailsResponseMain.getDialy_requirements().get(z).getPhyQuant())) {
                            existFlag = true;
                            break;
                        }
                    }
                }

                if (!existFlag && EmptiesFragment.commonCommodities != null && EmptiesFragment.commonCommodities.size() > 0) {

                    for (int z = 0; z < stockDetailsResponseMain.getEmpties().size(); z++) {
                        if (!TextUtils.isEmpty(stockDetailsResponseMain.getEmpties().get(z).getPhyQuant())) {
                            existFlag = true;
                            break;
                        }
                    }
                }

                if (!existFlag && MFPFragment.commonCommodities != null && MFPFragment.commonCommodities.size() > 0) {

                    for (int z = 0; z < stockDetailsResponseMain.getMfp_commodities().size(); z++) {
                        if (!TextUtils.isEmpty(stockDetailsResponseMain.getMfp_commodities().get(z).getPhyQuant())) {
                            existFlag = true;
                            break;
                        }
                    }
                }

                if (!existFlag && PUnitFragment.commonCommodities != null && PUnitFragment.commonCommodities.size() > 0) {

                    for (int z = 0; z < stockDetailsResponseMain.getProcessing_units().size(); z++) {
                        if (!TextUtils.isEmpty(stockDetailsResponseMain.getProcessing_units().get(z).getPhyQuant())) {
                            existFlag = true;
                            break;
                        }
                    }
                }

                if (existFlag) {
                    Gson gson = new Gson();
                    String stockData = gson.toJson(stockDetailsResponseMain);
                    try {
                        editor = TWDApplication.get(PUnitActivity.this).getPreferences().edit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    editor.putString(AppConstants.stockData, stockData);
                    editor.commit();
                    Intent intent = new Intent(PUnitActivity.this, PUnitsFindingsActivity.class);
                    startActivity(intent);
                } else {
                    Utils.customErrorAlert(PUnitActivity.this, getResources().getString(R.string.app_name), getString(R.string.one_record));
                }
            }

        });

        if (Utils.checkInternetConnection(PUnitActivity.this)) {
            if (pUnits != null && pUnits.getGodownId() != null) {

                customProgressDialog.show();
                LiveData<StockDetailsResponse> officesResponseLiveData = viewModel.getStockData(pUnits.getGodownId());
                officesResponseLiveData.observe(PUnitActivity.this, new Observer<StockDetailsResponse>() {
                    @Override
                    public void onChanged(StockDetailsResponse stockDetailsResponse) {

                        customProgressDialog.hide();
                        officesResponseLiveData.removeObservers(PUnitActivity.this);
                        stockDetailsResponseMain = stockDetailsResponse;

                        if (stockDetailsResponse != null && stockDetailsResponse.getStatusCode() != null) {
                            if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {


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


                                if (stockDetailsResponse.getMfp_commodities() != null && stockDetailsResponse.getMfp_commodities().size() > 0) {
                                    mfp_flag = true;
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
                                    punit_flag = true;
                                    stockDetailsResponse.getProcessing_units().get(0).setComHeader("Processing Units");
                                    PUnitFragment pUnitFragment = new PUnitFragment();
                                    Gson gson = new Gson();
                                    String essentialComm = gson.toJson(stockDetailsResponse.getProcessing_units());
                                    Bundle bundle = new Bundle();
                                    bundle.putString(AppConstants.punit, essentialComm);
                                    pUnitFragment.setArguments(bundle);
                                    adapter.addFrag(pUnitFragment, "Processing Units");
                                }
                                if (ess_flag || dailyreq_flag || emp_flag || mfp_flag || punit_flag) {
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
                                    binding.noDataTv.setText(getString(R.string.no_data_found));
                                    callSnackBar("No data found");
                                }

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
                Utils.customWarningAlert(PUnitActivity.this, getResources().getString(R.string.app_name), getString(R.string.something));
            }
        } else {
            Utils.customWarningAlert(PUnitActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
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
        if (stockDetailsResponseMain != null && stockDetailsResponseMain.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)
                && (ess_flag || dailyreq_flag || emp_flag || mfp_flag || punit_flag)) {
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
