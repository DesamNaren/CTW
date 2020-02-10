//package com.example.twdinspection.gcc.ui;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.databinding.DataBindingUtil;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.Observer;
//
//import com.example.twdinspection.R;
//import com.example.twdinspection.common.application.TWDApplication;
//import com.example.twdinspection.common.utils.AppConstants;
//import com.example.twdinspection.common.utils.CustomProgressDialog;
//import com.example.twdinspection.common.utils.Utils;
//import com.example.twdinspection.databinding.ActivityDrGodownBinding;
//import com.example.twdinspection.gcc.source.stock.StockDetailsResponse;
//import com.example.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
//import com.example.twdinspection.gcc.ui.fragment.DailyFragment;
//import com.example.twdinspection.gcc.ui.fragment.EmptiesFragment;
//import com.example.twdinspection.gcc.ui.fragment.EssentialFragment;
//import com.example.twdinspection.gcc.ui.fragment.MFPFragment;
//import com.example.twdinspection.gcc.ui.fragment.PUnitFragment;
//import com.example.twdinspection.inspection.viewmodel.StockViewModel;
//import com.google.gson.Gson;
//
//public class DRGodownActivity extends AppCompatActivity {
//    private SharedPreferences sharedPreferences;
//    private SharedPreferences.Editor editor;
//    private StockViewModel viewModel;
//    ActivityDrGodownBinding binding;
//    private DrGodowns drGodowns;
//    CustomProgressDialog customProgressDialog;
//    private StockDetailsResponse stockDetailsResponsemain;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_godown);
//        customProgressDialog = new CustomProgressDialog(this);
//        binding.header.headerTitle.setText(getResources().getString(R.string.dr_godown));
//
//        viewModel = new StockViewModel(getApplication());
//        binding.setViewModel(viewModel);
//        binding.executePendingBindings();
//
//        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//        try {
//            sharedPreferences = TWDApplication.get(this).getPreferences();
//            Gson gson = new Gson();
//            String str = sharedPreferences.getString(AppConstants.DR_GODOWN_DATA,"");
//            drGodowns = gson.fromJson(str, DrGodowns.class);
//            if(drGodowns!=null) {
//                binding.includeBasicLayout.divName.setText(drGodowns.getDivisionName());
//                binding.includeBasicLayout.socName.setText(drGodowns.getSocietyName());
//                binding.includeBasicLayout.drGodownName.setText(drGodowns.getGodownName());
//                binding.includeBasicLayout.inchargeName.setText(drGodowns.getIncharge());
//                binding.includeBasicLayout.dateTv.setText(Utils.getCurrentDateTimeDisplay());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (Utils.checkInternetConnection(DRGodownActivity.this)) {
//            customProgressDialog.show();
//            LiveData<StockDetailsResponse> officesResponseLiveData = viewModel.getStockData("2054");
//            officesResponseLiveData.observe(DRGodownActivity.this, new Observer<StockDetailsResponse>() {
//                @Override
//                public void onChanged(StockDetailsResponse stockDetailsResponse) {
//
//                    customProgressDialog.hide();
//                    officesResponseLiveData.removeObservers(DRGodownActivity.this);
//                    stockDetailsResponsemain = stockDetailsResponse;
//
//                    if (stockDetailsResponse != null && stockDetailsResponse.getStatusCode() != null) {
//                        if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
//                            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//
//                            if (stockDetailsResponse.getEssential_commodities() != null && stockDetailsResponse.getEssential_commodities().size() > 0) {
//                                stockDetailsResponse.getEssential_commodities().get(0).setComHeader("Essential Commodities");
//                                EssentialFragment essentialFragment = new EssentialFragment();
//                                Gson gson = new Gson();
//                                String essentialComm = gson.toJson(stockDetailsResponse.getEssential_commodities());
//                                Bundle bundle = new Bundle();
//                                bundle.putString(AppConstants.essComm, essentialComm);
//                                essentialFragment.setArguments(bundle);
//                                adapter.addFrag(essentialFragment, "Essential Commodities");
//                            }
//
//                            if (stockDetailsResponse.getDialy_requirements() != null && stockDetailsResponse.getDialy_requirements().size() > 0) {
//                                stockDetailsResponse.getDialy_requirements().get(0).setComHeader("Daily Requirements");
//                                DailyFragment dailyFragment = new DailyFragment();
//                                Gson gson = new Gson();
//                                String essentialComm = gson.toJson(stockDetailsResponse.getDialy_requirements());
//                                Bundle bundle = new Bundle();
//                                bundle.putString(AppConstants.dailyReq, essentialComm);
//                                dailyFragment.setArguments(bundle);
//                                adapter.addFrag(dailyFragment, "Daily Requirements");
//                            }
//
//                            if (stockDetailsResponse.getEmpties() != null && stockDetailsResponse.getEmpties().size() > 0) {
//                                stockDetailsResponse.getEmpties().get(0).setComHeader("Empties");
//                                EmptiesFragment emptiesFragment = new EmptiesFragment();
//                                Gson gson = new Gson();
//                                String essentialComm = gson.toJson(stockDetailsResponse.getEmpties());
//                                Bundle bundle = new Bundle();
//                                bundle.putString(AppConstants.empties, essentialComm);
//                                emptiesFragment.setArguments(bundle);
//                                adapter.addFrag(emptiesFragment, "Empties");
//                            }
//
//
//                            if (stockDetailsResponse.getMfp_commodities() != null && stockDetailsResponse.getMfp_commodities().size() > 0) {
//                                stockDetailsResponse.getMfp_commodities().get(0).setComHeader("MFP Commodities");
//                                MFPFragment mfpFragment = new MFPFragment();
//                                Gson gson = new Gson();
//                                String essentialComm = gson.toJson(stockDetailsResponse.getEmpties());
//                                Bundle bundle = new Bundle();
//                                bundle.putString(AppConstants.mfp, essentialComm);
//                                mfpFragment.setArguments(bundle);
//                                adapter.addFrag(mfpFragment, "MFP Commodities");
//                            }
//
//                            if (stockDetailsResponse.getProcessing_units() != null && stockDetailsResponse.getProcessing_units().size() > 0) {
//                                stockDetailsResponse.getProcessing_units().get(0).setComHeader("Processing Units");
//                                PUnitFragment pUnitFragment = new PUnitFragment();
//                                Gson gson = new Gson();
//                                String essentialComm = gson.toJson(stockDetailsResponse.getEmpties());
//                                Bundle bundle = new Bundle();
//                                bundle.putString(AppConstants.punit, essentialComm);
//                                pUnitFragment.setArguments(bundle);
//                                adapter.addFrag(pUnitFragment, "Processing Units");
//                            }
//
//                            binding.tabs.setupWithViewPager(binding.viewpager);
//                            binding.viewpager.setAdapter(adapter);
//
//                        } else if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
//                            callSnackBar(stockDetailsResponse.getStatusMessage());
//                        } else {
//                            callSnackBar(getString(R.string.something));
//                        }
//                    } else {
//                        callSnackBar(getString(R.string.something));
//                    }
//                }
//            });
//        }
//        else {
//            Utils.customWarningAlert(DRGodownActivity.this, getResources().getString(R.string.app_name), "Please check internet");
//        }
//
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        finish();
//    }
//}
