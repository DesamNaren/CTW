package com.cgg.twdinspection.gcc.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityReportStockDetailsBinding;
import com.cgg.twdinspection.gcc.reports.fragments.DailyReportFragment;
import com.cgg.twdinspection.gcc.reports.fragments.EmptiesReportFragment;
import com.cgg.twdinspection.gcc.reports.fragments.EssentialReportFragment;
import com.cgg.twdinspection.gcc.reports.fragments.LPGlReportFragment;
import com.cgg.twdinspection.gcc.reports.fragments.MFPReportFragment;
import com.cgg.twdinspection.gcc.reports.fragments.PUnitReportFragment;
import com.cgg.twdinspection.gcc.reports.fragments.PetrollReportFragment;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.cgg.twdinspection.gcc.ui.fragment.DailyFragment;
import com.cgg.twdinspection.gcc.ui.fragment.EmptiesFragment;
import com.cgg.twdinspection.gcc.ui.fragment.EssentialFragment;
import com.cgg.twdinspection.gcc.ui.fragment.MFPFragment;
import com.cgg.twdinspection.gcc.ui.fragment.PUnitFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReportStockDetailsActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ActivityReportStockDetailsBinding binding;
    private List<String> mFragmentTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    ReportData reportData;
    private boolean punit_flag, dailyreq_flag, emp_flag, ess_flag, mfp_flag, petrol_flag, lpg_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_stock_details);
        binding.bottomLl.btnNext.setText("Next");
        EssentialFragment.commonCommodities = null;
        DailyFragment.commonCommodities = null;
        EmptiesFragment.commonCommodities = null;
        MFPFragment.commonCommodities = null;
        PUnitFragment.commonCommodities = null;

        sharedPreferences = TWDApplication.get(ReportStockDetailsActivity.this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.REP_DATA, "");
        reportData = gson.fromJson(data, ReportData.class);

        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_GODOWN)) {
            binding.header.headerTitle.setText("Dr Godown");
        }
        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_DEPOT_REP)) {
            binding.header.headerTitle.setText("Dr Depot");
        }
        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_MFP_GODOWN_REP)) {
            binding.header.headerTitle.setText("MFP Godown");
        }
        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PUNIT_REP)) {
            binding.header.headerTitle.setText("Processing Unit");
        }
        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PETROL_REP)) {
            binding.header.headerTitle.setText(getResources().getString(R.string.petrol_pump_title));
        }
        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_LPG_REP)) {
            binding.header.headerTitle.setText(getResources().getString(R.string.lpg_title));
        }

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
            if (reportData != null) {
                binding.includeBasicLayout.divName.setText(reportData.getDivisionName());
                binding.includeBasicLayout.socName.setText(reportData.getSocietyName());
                if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_GODOWN)) {
                    binding.includeBasicLayout.drGodownNameTV.setText("Dr Godown");
                }
                if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_DEPOT_REP)) {
                    binding.includeBasicLayout.drGodownNameTV.setText("Dr Depot");
                }
                if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_MFP_GODOWN_REP)) {
                    binding.includeBasicLayout.drGodownNameTV.setText("MFP Godown");
                }
                if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PUNIT_REP)) {
                    binding.includeBasicLayout.drGodownNameTV.setText("Processing Unit");
                }
                if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PETROL_REP)) {
                    binding.includeBasicLayout.drGodownNameTV.setText(getResources().getString(R.string.petrol_pump_title));
                }
                if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_LPG_REP)) {
                    binding.includeBasicLayout.drGodownNameTV.setText(getResources().getString(R.string.lpg_title));
                }
                binding.includeBasicLayout.drGodownName.setText(reportData.getGodownName());
                binding.includeBasicLayout.inchargeName.setText(reportData.getInchargeName());
                binding.includeBasicLayout.dateTv.setText(reportData.getInspectionTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reportData.getInspectionFindings().getDrGodown() != null) {
                    Intent intent = new Intent(ReportStockDetailsActivity.this, DrGodownInspRepActivity.class);
                    startActivity(intent);
                } else if (reportData.getInspectionFindings().getDrDepot() != null) {
                    Intent intent = new Intent(ReportStockDetailsActivity.this, DrDepotInspRepActivity.class);
                    startActivity(intent);
                } else if (reportData.getInspectionFindings().getMfpGodowns() != null) {
                    Intent intent = new Intent(ReportStockDetailsActivity.this, MfpGodownInspRepActivity.class);
                    startActivity(intent);
                } else if (reportData.getInspectionFindings().getProcessingUnit() != null) {
                    Intent intent = new Intent(ReportStockDetailsActivity.this, PUnitInspRepActivity.class);
                    startActivity(intent);
                } else if (reportData.getInspectionFindings().getProcessingUnit() != null) {
                    Intent intent = new Intent(ReportStockDetailsActivity.this, PUnitInspRepActivity.class);
                    startActivity(intent);
                } else if (reportData.getInspectionFindings().getPetrolPump() != null) {
                    Intent intent = new Intent(ReportStockDetailsActivity.this, PetrolpumpInspRepActivity.class);
                    startActivity(intent);
                } else if (reportData.getInspectionFindings().getLpg() != null) {
                    Intent intent = new Intent(ReportStockDetailsActivity.this, LPGInspRepActivity.class);
                    startActivity(intent);
                } else {
                    callSnackBar("No Inspection data found");
                }
            }

        });


        if (reportData != null && !TextUtils.isEmpty(reportData.getSupplierType()) &&
                reportData.getSupplierType().equalsIgnoreCase(getString(R.string.dr_depot_req))
                && !TextUtils.isEmpty(reportData.getShopAvail())
                && reportData.getShopAvail().equalsIgnoreCase(AppConstants.close)) {
            binding.viewPager.setVisibility(View.GONE);
            binding.tabs.setVisibility(View.GONE);
            binding.llShopClose.setVisibility(View.VISIBLE);
            if (reportData.getPhotos() != null && reportData.getPhotos().size() > 0) {
                for (int z = 0; z < reportData.getPhotos().size(); z++) {
                    if (!TextUtils.isEmpty(reportData.getPhotos().get(z).getFileName())
                            && reportData.getPhotos().get(z).getFileName().contains(AppConstants.SHOP_CLOSED)) {
                        Glide.with(ReportStockDetailsActivity.this)
                                .load(reportData.getPhotos().get(z).getFilePath())
                                .error(R.drawable.no_image)
                                .placeholder(R.drawable.camera)
                                .listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        binding.pbar.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        binding.pbar.setVisibility(View.GONE);
                                        return false;
                                    }
                                })
                                .into(binding.ivShopCam);
                        break;
                    }

                    int pos = z;
                    binding.ivShopCam.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.displayPhotoDialogBox(reportData.getPhotos().get(pos).getFilePath(),
                                    ReportStockDetailsActivity.this, reportData.getPhotos().get(pos).getFileName(), true);
                        }
                    });
                }
            } else {
                Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            }
        }

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
                ess_flag = true;
            } else {
                ess_flag = false;
            }

            if (reportData.getStockDetails().getDailyRequirements() != null && reportData.getStockDetails().getDailyRequirements().size() > 0) {
                DailyReportFragment dailyFragment = new DailyReportFragment();
                String essentialComm = gson.toJson(reportData.getStockDetails().getDailyRequirements());
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.dailyRepReq, essentialComm);
                dailyFragment.setArguments(bundle);
                adapter.addFrag(dailyFragment, "Daily Requirements");
                dailyreq_flag = true;
            } else {
                dailyreq_flag = false;
            }

            if (reportData.getStockDetails().getEmpties() != null && reportData.getStockDetails().getEmpties().size() > 0) {
                EmptiesReportFragment emptiesFragment = new EmptiesReportFragment();
                String essentialComm = gson.toJson(reportData.getStockDetails().getEmpties());
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.emptiesRep, essentialComm);
                emptiesFragment.setArguments(bundle);
                adapter.addFrag(emptiesFragment, "Empties");
                emp_flag = true;
            } else {
                emp_flag = false;
            }


            if (reportData.getStockDetails().getMfpCommodities() != null && reportData.getStockDetails().getMfpCommodities().size() > 0) {
                MFPReportFragment mfpFragment = new MFPReportFragment();
                String essentialComm = gson.toJson(reportData.getStockDetails().getMfpCommodities());
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.mfpRep, essentialComm);
                mfpFragment.setArguments(bundle);
                adapter.addFrag(mfpFragment, "MFP Commodities");
                mfp_flag = true;
            } else {
                mfp_flag = false;
            }

            if (reportData.getStockDetails().getProcessingUnits() != null && reportData.getStockDetails().getProcessingUnits().size() > 0) {
                PUnitReportFragment pUnitFragment = new PUnitReportFragment();
                String essentialComm = gson.toJson(reportData.getStockDetails().getProcessingUnits());
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.punitRep, essentialComm);
                pUnitFragment.setArguments(bundle);
                adapter.addFrag(pUnitFragment, "Processing Units");
                punit_flag = true;
            } else {
                punit_flag = false;
            }

            if (reportData.getStockDetails().getPetrolCommodities() != null && reportData.getStockDetails().getPetrolCommodities().size() > 0) {
                PetrollReportFragment petrollReportFragment = new PetrollReportFragment();
                String petrolComm = gson.toJson(reportData.getStockDetails().getPetrolCommodities());
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.petrolPumpRep, petrolComm);
                petrollReportFragment.setArguments(bundle);
                adapter.addFrag(petrollReportFragment, "Petrol Commodities");
                petrol_flag = true;
            } else {
                petrol_flag = false;
            }

            if (reportData.getStockDetails().getLpgCommodities() != null && reportData.getStockDetails().getLpgCommodities().size() > 0) {
                LPGlReportFragment lpGlReportFragment = new LPGlReportFragment();
                String lpgComm = gson.toJson(reportData.getStockDetails().getLpgCommodities());
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.lpgRep, lpgComm);
                lpGlReportFragment.setArguments(bundle);
                adapter.addFrag(lpGlReportFragment, "LPG Commodities");
                lpg_flag = true;
            } else {
                lpg_flag = false;
            }
            binding.tabs.setupWithViewPager(binding.viewPager);
            binding.viewPager.setAdapter(adapter);

        }


        if (reportData != null && (reportData.getStockDetails() != null && !punit_flag &&
                !dailyreq_flag && !emp_flag && !ess_flag && !mfp_flag && !petrol_flag && !lpg_flag)) {
            binding.viewPager.setVisibility(View.GONE);
            binding.tabs.setVisibility(View.GONE);
            binding.noDataTv.setVisibility(View.VISIBLE);
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
