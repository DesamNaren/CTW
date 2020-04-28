package com.cgg.twdinspection.gcc.ui.petrolpump;

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
import com.cgg.twdinspection.databinding.ActivityPetrolPumpBinding;
import com.cgg.twdinspection.gcc.source.stock.PetrolStockDetailsResponse;
import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolSupplierInfo;
import com.cgg.twdinspection.gcc.ui.fragment.PLPGFragment;
import com.cgg.twdinspection.inspection.viewmodel.StockViewModel;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PetrolPumpActivity extends AppCompatActivity implements ErrorHandlerInterface {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private StockViewModel viewModel;
    ActivityPetrolPumpBinding binding;
    private PetrolSupplierInfo petrolSupplierInfo;
    CustomProgressDialog customProgressDialog;
    private PetrolStockDetailsResponse petrolStockDetailsResponseMain;
    private List<String> mFragmentTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_petrol_pump);
        customProgressDialog = new CustomProgressDialog(this);
        petrolStockDetailsResponseMain = null;
        PLPGFragment.commonCommodities = null;

        binding.header.headerTitle.setText(getResources().getString(R.string.gcc_petrol_pump));
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
            String str = sharedPreferences.getString(AppConstants.PETROL_PUMP_DATA, "");
            petrolSupplierInfo = gson.fromJson(str, PetrolSupplierInfo.class);
            if (petrolSupplierInfo != null) {
                binding.includeBasicLayout.divName.setText(petrolSupplierInfo.getDivisionName());
                binding.includeBasicLayout.drGodownNameTV.setText("Petrol Pump");
                binding.includeBasicLayout.socName.setText(petrolSupplierInfo.getSocietyName());
                binding.includeBasicLayout.drGodownName.setText(petrolSupplierInfo.getGodownName());
                binding.includeBasicLayout.inchargeName.setText(petrolSupplierInfo.getIncharge());
                binding.includeBasicLayout.dateTv.setText(Utils.getCurrentDateTimeDisplay());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean existFlag=false;
                if (PLPGFragment.commonCommodities != null && PLPGFragment.commonCommodities.size() > 0) {
                    petrolStockDetailsResponseMain.setCommonCommodities(PLPGFragment.commonCommodities);
                    for (int z = 0; z < petrolStockDetailsResponseMain.getCommonCommodities().size(); z++) {
                        if (!TextUtils.isEmpty(petrolStockDetailsResponseMain.getCommonCommodities().get(z).getPhyQuant())) {
                            existFlag=true;
//                            String header = petrolStockDetailsResponseMain.getCommonCommodities().get(0).getComHeader();
//                            setFragPos(header, z);
                            break;
                        }
                    }
                }
                if(existFlag) {
                    Gson gson = new Gson();
                    String stockData = gson.toJson(petrolStockDetailsResponseMain);
                    try {
                        editor = TWDApplication.get(PetrolPumpActivity.this).getPreferences().edit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    editor.putString(AppConstants.stockData, stockData);
                    editor.commit();
                    Intent intent = new Intent(PetrolPumpActivity.this, PetrolPumpFindingsActivity.class);
                    startActivity(intent);
                }else {
                    Utils.customErrorAlert(PetrolPumpActivity.this, getResources().getString(R.string.app_name), getString(R.string.one_record));
                }
            }

        });

        if (Utils.checkInternetConnection(PetrolPumpActivity.this)) {
            if (petrolSupplierInfo != null && petrolSupplierInfo.getGodownId() != null) {
                customProgressDialog.show();
                LiveData<PetrolStockDetailsResponse> officesResponseLiveData = viewModel.getPLPGStockData(petrolSupplierInfo.getGodownId());
                officesResponseLiveData.observe(PetrolPumpActivity.this, new Observer<PetrolStockDetailsResponse>() {
                    @Override
                    public void onChanged(PetrolStockDetailsResponse petrolStockDetailsResponse) {

                        customProgressDialog.hide();
                        officesResponseLiveData.removeObservers(PetrolPumpActivity.this);
                        petrolStockDetailsResponseMain = petrolStockDetailsResponse;

                        if (petrolStockDetailsResponse != null && petrolStockDetailsResponse.getStatusCode() != null) {
                            if (petrolStockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                binding.viewPager.setVisibility(View.VISIBLE);
                                binding.tabs.setVisibility(View.VISIBLE);
                                binding.noDataTv.setVisibility(View.GONE);
                                binding.bottomLl.btnLayout.setVisibility(View.VISIBLE);
                                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

                                if (petrolStockDetailsResponse.getCommonCommodities() != null && petrolStockDetailsResponse.getCommonCommodities().size() > 0) {
                                    petrolStockDetailsResponse.getCommonCommodities().get(0).setComHeader("Petrol Commodities");
                                    PLPGFragment plpgFragment = new PLPGFragment();
                                    Gson gson = new Gson();
                                    String petrolComm = gson.toJson(petrolStockDetailsResponse.getCommonCommodities());
                                    Bundle bundle = new Bundle();
                                    bundle.putString(AppConstants.petComm, petrolComm);
                                    plpgFragment.setArguments(bundle);
                                    adapter.addFrag(plpgFragment, "Petrol Commodities");
                                }else {
                                    binding.viewPager.setVisibility(View.GONE);
                                    binding.tabs.setVisibility(View.GONE);
                                    binding.noDataTv.setVisibility(View.VISIBLE);
                                    binding.bottomLl.btnLayout.setVisibility(View.GONE);
                                    binding.noDataTv.setText(petrolStockDetailsResponse.getStatusMessage());
                                    callSnackBar(petrolStockDetailsResponse.getStatusMessage());
                                }

                                binding.tabs.setupWithViewPager(binding.viewPager);
                                binding.viewPager.setAdapter(adapter);

                            } else if (petrolStockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                binding.viewPager.setVisibility(View.GONE);
                                binding.tabs.setVisibility(View.GONE);
                                binding.noDataTv.setVisibility(View.VISIBLE);
                                binding.bottomLl.btnLayout.setVisibility(View.GONE);
                                binding.noDataTv.setText(petrolStockDetailsResponse.getStatusMessage());
                                callSnackBar(petrolStockDetailsResponse.getStatusMessage());
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        } else {
                            callSnackBar(getString(R.string.something));
                        }

                    }

                });
            } else {
                Utils.customWarningAlert(PetrolPumpActivity.this, getResources().getString(R.string.app_name), getString(R.string.something));
            }
        } else {
            Utils.customErrorAlert(PetrolPumpActivity.this, getResources().getString(R.string.app_name), "Please check internet");
        }


    }

    void setFragPos(String header, int pos) {
        for (int x = 0; x < mFragmentTitleList.size(); x++) {
            if (header.equalsIgnoreCase(mFragmentTitleList.get(x))) {
                callSnackBar("Submit all records in " + header);
                binding.viewPager.setCurrentItem(x);
                if (header.contains("Petrol Commodities")) {
                    ((PLPGFragment) mFragmentList.get(x)).setPos(pos);
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
        if (petrolStockDetailsResponseMain!=null && petrolStockDetailsResponseMain.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
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
