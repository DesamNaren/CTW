package com.cgg.twdinspection.gcc.ui.mfpgodown;

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
import com.cgg.twdinspection.databinding.ActivityMfpGodownBinding;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.cgg.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.cgg.twdinspection.gcc.ui.drdepot.DRDepotActivity;
import com.cgg.twdinspection.gcc.ui.drgodown.DRGodownFindingsActivity;
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

public class MFPGodownActivity extends AppCompatActivity implements ErrorHandlerInterface {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private StockViewModel viewModel;
    ActivityMfpGodownBinding binding;
    CustomProgressDialog customProgressDialog;
    private StockDetailsResponse stockDetailsResponsemain;
    private MFPGoDowns mfpGoDowns;
    private List<String> mFragmentTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    private boolean mfp_flag, emp_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_mfp_godown);
        binding.header.headerTitle.setText(getResources().getString(R.string.gcc_mfp_godown));
        stockDetailsResponsemain = null;
        EssentialFragment.commonCommodities = null;
        DailyFragment.commonCommodities = null;
        EmptiesFragment.commonCommodities = null;
        MFPFragment.commonCommodities = null;
        PUnitFragment.commonCommodities = null;

        customProgressDialog = new CustomProgressDialog(this);
        viewModel = new StockViewModel(getApplication(), this);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        binding.header.ivHome.setVisibility(View.GONE);
        sharedPreferences = TWDApplication.get(this).getPreferences();
        binding.includeBasicLayout.drGodownNameTV.setText(getString(R.string.mfp_godown_name));
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        binding.includeBasicLayout.divLL.setVisibility(View.VISIBLE);
        binding.includeBasicLayout.drGodownNameTV.setText(getString(R.string.mfp_godown_name));
        binding.includeBasicLayout.drGodownLL.setVisibility(View.VISIBLE);
        binding.includeBasicLayout.inchargeLL.setVisibility(View.VISIBLE);

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            Gson gson = new Gson();
            String str = sharedPreferences.getString(AppConstants.MFP_DEPOT_DATA, "");
            mfpGoDowns = gson.fromJson(str, MFPGoDowns.class);
            if (mfpGoDowns != null) {
                binding.includeBasicLayout.divName.setText(mfpGoDowns.getDivisionName());
//                binding.includeBasicLayout.socName.setText(mfpGoDowns.getSocietyName());
                binding.includeBasicLayout.drGodownName.setText(mfpGoDowns.getGodownName());
                binding.includeBasicLayout.inchargeName.setText(mfpGoDowns.getIncharge());
                binding.includeBasicLayout.dateTv.setText(Utils.getCurrentDateTimeDisplay());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        GCCOfflineViewModel gccOfflineViewModel = new GCCOfflineViewModel(getApplication());
        LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(
                mfpGoDowns.getDivisionId(), mfpGoDowns.getSocietyId(), mfpGoDowns.getGodownId());

        drGodownLiveData.observe(MFPGodownActivity.this, new Observer<GccOfflineEntity>() {
            @Override
            public void onChanged(GccOfflineEntity gccOfflineEntity) {
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
                stockDetailsResponsemain.setMfp_commodities(MFPFragment.commonCommodities);
                stockDetailsResponsemain.setEmpties(EmptiesFragment.commonCommodities);
                if (MFPFragment.commonCommodities != null && MFPFragment.commonCommodities.size() > 0) {

                    for (int z = 0; z < stockDetailsResponsemain.getMfp_commodities().size(); z++) {
                        if (!TextUtils.isEmpty(stockDetailsResponsemain.getMfp_commodities().get(z).getPhyQuant())) {
                            existFlag= true;
                            break;
                        }
                    }
                }

                if (!existFlag && EmptiesFragment.commonCommodities != null && EmptiesFragment.commonCommodities.size() > 0) {

                    for (int z = 0; z < stockDetailsResponsemain.getEmpties().size(); z++) {
                        if (!TextUtils.isEmpty(stockDetailsResponsemain.getEmpties().get(z).getPhyQuant())) {
                            existFlag = true;
                            break;
                        }
                    }
                }


                if (existFlag) {
                    Gson gson = new Gson();
                    String stockData = gson.toJson(stockDetailsResponsemain);
                    try {
                        editor = TWDApplication.get(MFPGodownActivity.this).getPreferences().edit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    editor.putString(AppConstants.stockData, stockData);
                    editor.commit();
                    Intent intent = new Intent(MFPGodownActivity.this, MFPGodownFindingsActivity.class);
                    startActivity(intent);
                } else {
                    Utils.customErrorAlert(MFPGodownActivity.this, getResources().getString(R.string.app_name), getString(R.string.one_record));
                }
            }

        });

        if (Utils.checkInternetConnection(MFPGodownActivity.this)) {
            if (mfpGoDowns != null && mfpGoDowns.getGodownId() != null) {

                customProgressDialog.show();
                LiveData<StockDetailsResponse> officesResponseLiveData = viewModel.getStockData(mfpGoDowns.getGodownId());
                officesResponseLiveData.observe(MFPGodownActivity.this, new Observer<StockDetailsResponse>() {
                    @Override
                    public void onChanged(StockDetailsResponse stockDetailsResponse) {

                        customProgressDialog.hide();
                        officesResponseLiveData.removeObservers(MFPGodownActivity.this);
                        stockDetailsResponsemain = stockDetailsResponse;

                        if (stockDetailsResponse != null && stockDetailsResponse.getStatusCode() != null) {
                            if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {


                                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

                                if (stockDetailsResponse.getMfp_commodities() != null && stockDetailsResponse.getMfp_commodities().size() > 0) {
                                    mfp_flag=true;
                                    stockDetailsResponse.getMfp_commodities().get(0).setComHeader("MFP Commodities");
                                    MFPFragment mfpFragment = new MFPFragment();
                                    Gson gson = new Gson();
                                    String essentialComm = gson.toJson(stockDetailsResponse.getMfp_commodities());
                                    Bundle bundle = new Bundle();
                                    bundle.putString(AppConstants.mfp, essentialComm);
                                    mfpFragment.setArguments(bundle);
                                    adapter.addFrag(mfpFragment, "MFP Commodities");

                                    binding.viewPager.setVisibility(View.VISIBLE);
                                    binding.tabs.setVisibility(View.VISIBLE);
                                    binding.noDataTv.setVisibility(View.GONE);
                                    binding.bottomLl.btnLayout.setVisibility(View.VISIBLE);
                                    binding.tabs.setupWithViewPager(binding.viewPager);
                                    binding.viewPager.setAdapter(adapter);
                                }if (stockDetailsResponse.getEmpties() != null && stockDetailsResponse.getEmpties().size() > 0) {
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

                                if(mfp_flag|| emp_flag)
                                {
                                    binding.viewPager.setVisibility(View.VISIBLE);
                                    binding.tabs.setVisibility(View.VISIBLE);
                                    binding.noDataTv.setVisibility(View.GONE);
                                    binding.bottomLl.btnLayout.setVisibility(View.VISIBLE);
                                    binding.tabs.setupWithViewPager(binding.viewPager);
                                    binding.viewPager.setAdapter(adapter);
                                }else {
                                    binding.viewPager.setVisibility(View.GONE);
                                    binding.tabs.setVisibility(View.GONE);
                                    binding.noDataTv.setVisibility(View.VISIBLE);
                                    binding.bottomLl.btnLayout.setVisibility(View.GONE);
                                    binding.noDataTv.setText("No data found");
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
                            if (!mfp_flag && !emp_flag ) {
                                binding.viewPager.setVisibility(View.GONE);
                                binding.tabs.setVisibility(View.GONE);
                                binding.noDataTv.setVisibility(View.VISIBLE);
                                binding.bottomLl.btnLayout.setVisibility(View.GONE);
                                binding.noDataTv.setText(stockDetailsResponse.getStatusMessage());
                                callSnackBar(stockDetailsResponse.getStatusMessage());
                            }
                        } else {
                            callSnackBar(getString(R.string.something));
                        }
                    }
                });
            } else {
                Utils.customWarningAlert(MFPGodownActivity.this, getResources().getString(R.string.app_name), getString(R.string.something));
            }
        } else {
            Utils.customWarningAlert(MFPGodownActivity.this, getResources().getString(R.string.app_name), "Please check internet");
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
        if (stockDetailsResponsemain!=null && stockDetailsResponsemain.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)
                && (mfp_flag|| emp_flag)) {
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
