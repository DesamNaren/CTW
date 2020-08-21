package com.cgg.twdinspection.gcc.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.screenshot.PDFUtil;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityReportStockDetailsBinding;
import com.cgg.twdinspection.gcc.reports.adapter.ViewPhotoAdapterPdf;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReportStockDetailsActivity extends AppCompatActivity implements PDFUtil.PDFUtilListener {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ActivityReportStockDetailsBinding binding;
    private List<String> mFragmentTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    ReportData reportData;
    private boolean punit_flag, dailyreq_flag, emp_flag, ess_flag, mfp_flag, petrol_flag, lpg_flag;
    int pos = -1;
    CustomProgressDialog customProgressDialog;
    String directory_path, filePath;
    ViewPhotoAdapterPdf adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_stock_details);
        binding.header.ivPdf.setVisibility(View.VISIBLE);
        customProgressDialog = new CustomProgressDialog(this);
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
            binding.header.headerTitle.setText("GCC - DR GODOWN REPORT");
        }
        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_DEPOT_REP)) {
            binding.header.headerTitle.setText("GCC- DR DEPOT REPORT");
        }
        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_MFP_GODOWN_REP)) {
            binding.header.headerTitle.setText("GCC - MFP GODOWN REPORT");
        }
        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PUNIT_REP)) {
            binding.header.headerTitle.setText("GCC - PROCESSING UNIT REPORT");
        }
        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PETROL_REP)) {
            binding.header.headerTitle.setText("GCC - PETROL PUMP REPORT");
        }
        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_LPG_REP)) {
            binding.header.headerTitle.setText("GCC - LPG REPORT");
        }

        binding.includeBasicLayout.divLL.setVisibility(View.VISIBLE);
        binding.includeBasicLayout.socLL.setVisibility(View.VISIBLE);
        binding.includeBasicLayout.drGodownLL.setVisibility(View.VISIBLE);
        binding.includeBasicLayout.inchargeLL.setVisibility(View.VISIBLE);

        //Pdf
        try {

            if (reportData != null) {

                if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_GODOWN)) {

                    if (reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getDrGodown() != null)
                        binding.setDrgodown(reportData.getInspectionFindings().getDrGodown());


                    binding.drGodownDivName.setText(reportData.getDivisionName());
                    binding.drGodownSocName.setText(reportData.getSocietyName());
                    binding.drGodownName.setText(reportData.getGodownName());
                    binding.drGodownInchargeName.setText(reportData.getInchargeName());
                    binding.drGodownTvDate.setText(reportData.getInspectionTime());
                    binding.drGodownTvOfficerName.setText(reportData.getOfficerId());
                    binding.drGodownTvOfficerDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));

                    String jsonObject = gson.toJson(reportData.getPhotos());
                    if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("[]")) {
                        adapter = new ViewPhotoAdapterPdf(this, reportData.getPhotos());
                        binding.drGodownRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                        binding.drGodownRecyclerView.setAdapter(adapter);
                    }

                    if (reportData.getPhotos() != null && reportData.getPhotos().size() > 0) {
                        for (int z = 0; z < reportData.getPhotos().size(); z++) {

                            if (!TextUtils.isEmpty(reportData.getPhotos().get(z).getFileName())
                                    && reportData.getPhotos().get(z).getFileName().contains(AppConstants.REPAIR)) {

                                Glide.with(ReportStockDetailsActivity.this)
                                        .load(reportData.getPhotos().get(z).getFilePath())
                                        .error(R.drawable.no_image)
                                        .placeholder(R.drawable.camera)
                                        .into(binding.drGodownIvRepairsPdf);
                                break;
                            }
                        }
                    }

                }

                else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_DEPOT_REP)) {

                    if (reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getDrDepot() != null) {
                        binding.setDrDepot(reportData.getInspectionFindings().getDrDepot());
                    }

                    binding.drDepotDivName.setText(reportData.getDivisionName());
                    binding.drDepotSocName.setText(reportData.getSocietyName());
                    binding.drDepotName.setText(reportData.getGodownName());
                    binding.drDepotInchargeName.setText(reportData.getInchargeName());
                    binding.drDepotTvDate.setText(reportData.getInspectionTime());
                    binding.drDepotTvOfficerName.setText(reportData.getOfficerId());
                    binding.drDepotTvOfficerDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));

                    String jsonObject = gson.toJson(reportData.getPhotos());
                    if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("[]")) {
                        adapter = new ViewPhotoAdapterPdf(this, reportData.getPhotos());
                        binding.drDepotRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                        binding.drDepotRecyclerView.setAdapter(adapter);
                    }

                    if (reportData.getPhotos() != null && reportData.getPhotos().size() > 0) {
                        for (int z = 0; z < reportData.getPhotos().size(); z++) {

                            if (!TextUtils.isEmpty(reportData.getPhotos().get(z).getFileName())
                                    && reportData.getPhotos().get(z).getFileName().contains(AppConstants.REPAIR)) {

                                Glide.with(ReportStockDetailsActivity.this)
                                        .load(reportData.getPhotos().get(z).getFilePath())
                                        .error(R.drawable.no_image)
                                        .placeholder(R.drawable.camera)
                                        .into(binding.drDepotIvRepairsPdf);
                                break;
                            }
                        }
                    }

                }

                else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_MFP_GODOWN_REP)) {

                    if (reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getMfpGodowns() != null) {
                        binding.setMfp(reportData.getInspectionFindings().getMfpGodowns());
                    }

                    binding.mfpDivName.setText(reportData.getDivisionName());
                    binding.mfpName.setText(reportData.getGodownName());
                    binding.mfpInchargeName.setText(reportData.getInchargeName());
                    binding.mfpTvDate.setText(reportData.getInspectionTime());
                    binding.mfpTvOfficerName.setText(reportData.getOfficerId());
                    binding.mfpTvOfficerDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));

                    String jsonObject = gson.toJson(reportData.getPhotos());
                    if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("[]")) {
                        adapter = new ViewPhotoAdapterPdf(this, reportData.getPhotos());
                        binding.mfpRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                        binding.mfpRecyclerView.setAdapter(adapter);
                    }

                    if (reportData.getPhotos() != null && reportData.getPhotos().size() > 0) {
                        for (int z = 0; z < reportData.getPhotos().size(); z++) {

                            if (!TextUtils.isEmpty(reportData.getPhotos().get(z).getFileName())
                                    && reportData.getPhotos().get(z).getFileName().contains(AppConstants.REPAIR)) {

                                Glide.with(ReportStockDetailsActivity.this)
                                        .load(reportData.getPhotos().get(z).getFilePath())
                                        .error(R.drawable.no_image)
                                        .placeholder(R.drawable.camera)
                                        .into(binding.mfpIvRepairsPdf);
                                break;
                            }
                        }
                    }

                }

                else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PUNIT_REP)) {

                    if (reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getProcessingUnit() != null)
                        binding.setPUnit(reportData.getInspectionFindings().getProcessingUnit());

                    binding.punitDivName.setText(reportData.getDivisionName());
                    binding.punitSocName.setText(reportData.getSocietyName());
                    binding.punitName.setText(reportData.getGodownName());
                    binding.punitInchargeName.setText(reportData.getInchargeName());
                    binding.punitTvDate.setText(reportData.getInspectionTime());
                    binding.punitTvOfficerName.setText(reportData.getOfficerId());
                    binding.punitTvOfficerDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));

                    if (reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getProcessingUnit() != null
                            && reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates() != null) {

                        binding.remarksStockPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getRawMatStockRegisterRemarks());
                        binding.remarksProcessingPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getProcessingRegisterRemarks());
                        binding.remarksInwardPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getInwardRegisterRemarks());
                        binding.remarksOutwardPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getOutwardRegisterRemarks());
                        binding.remarksSaleInvPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getSaleInvoiceBookRemarks());
                        binding.remarksLabAttPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getLabourAttendRegisterRemarks());
                        binding.remarksFireNocPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getFireDeptRemarks());
                        binding.remarksAmcPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getAmcMachinaryRemarks());
                        binding.remarksAgmarkPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getAgmarkCertRemarks());
                        binding.remarksFsaaiPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getFsaaiCertRemarks());
                        binding.remarksEmptiesPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getEmptiesRegisterRemarks());
                        binding.remarksBarrelsPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getBarrelsAlumnCansRemarks());
                        binding.remarksCashBookPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getCashBookRemarks());
                        binding.remarksCashBankPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getCashBankBalRemarks());
                        binding.remarksVehLogPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getRegisterBookCertificates().getVehLogBookRemarks());
                        binding.remarksPdf.etRemarks.setText(reportData.getInspectionFindings().getProcessingUnit().getGeneralFindings().getRemarks());

                    } else {
                        Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                    }

                    String jsonObject = gson.toJson(reportData.getPhotos());
                    if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("[]")) {
                        adapter = new ViewPhotoAdapterPdf(this, reportData.getPhotos());
                        binding.punitRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                        binding.punitRecyclerView.setAdapter(adapter);
                    }

                }

                else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PETROL_REP)) {

                    if (reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getPetrolPump() != null)
                        binding.setPetrolpump(reportData.getInspectionFindings().getPetrolPump());

                    binding.petrolPumpDivName.setText(reportData.getDivisionName());
                    binding.petrolPumpSocName.setText(reportData.getSocietyName());
                    binding.petrolPumpName.setText(reportData.getGodownName());
                    binding.petrolPumpInchargeName.setText(reportData.getInchargeName());
                    binding.petrolPumpTvDate.setText(reportData.getInspectionTime());
                    binding.petrolPumpTvOfficerName.setText(reportData.getOfficerId());
                    binding.petrolPumpTvOfficerDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));

                    String jsonObject = gson.toJson(reportData.getPhotos());
                    if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("[]")) {
                        adapter = new ViewPhotoAdapterPdf(this, reportData.getPhotos());
                        binding.petrolPumpRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                        binding.petrolPumpRecyclerView.setAdapter(adapter);
                    }

                    if (reportData.getPhotos() != null && reportData.getPhotos().size() > 0) {
                        for (int z = 0; z < reportData.getPhotos().size(); z++) {

                            if (!TextUtils.isEmpty(reportData.getPhotos().get(z).getFileName())
                                    && reportData.getPhotos().get(z).getFileName().contains(AppConstants.REPAIR)) {

                                Glide.with(ReportStockDetailsActivity.this)
                                        .load(reportData.getPhotos().get(z).getFilePath())
                                        .error(R.drawable.no_image)
                                        .placeholder(R.drawable.camera)
                                        .into(binding.petrolPumpIvRepairsPdf);
                                break;
                            }
                        }
                    }

                }

                else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_LPG_REP)) {
                    if (reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getLpg() != null)
                        binding.setLpg(reportData.getInspectionFindings().getLpg());

                    binding.lpgDivName.setText(reportData.getDivisionName());
                    binding.lpgSocName.setText(reportData.getSocietyName());
                    binding.lpgName.setText(reportData.getGodownName());
                    binding.lpgInchargeName.setText(reportData.getInchargeName());
                    binding.lpgTvDate.setText(reportData.getInspectionTime());
                    binding.lpgTvOfficerName.setText(reportData.getOfficerId());
                    binding.lpgTvOfficerDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));

                    String jsonObject = gson.toJson(reportData.getPhotos());
                    if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("[]")) {
                        adapter = new ViewPhotoAdapterPdf(this, reportData.getPhotos());
                        binding.lpgRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                        binding.lpgRecyclerView.setAdapter(adapter);
                    }

                    if (reportData.getPhotos() != null && reportData.getPhotos().size() > 0) {
                        for (int z = 0; z < reportData.getPhotos().size(); z++) {

                            if (!TextUtils.isEmpty(reportData.getPhotos().get(z).getFileName())
                                    && reportData.getPhotos().get(z).getFileName().contains(AppConstants.REPAIR)) {

                                Glide.with(ReportStockDetailsActivity.this)
                                        .load(reportData.getPhotos().get(z).getFilePath())
                                        .error(R.drawable.no_image)
                                        .placeholder(R.drawable.camera)
                                        .into(binding.lpgIvRepairsPdf);
                                break;
                            }
                        }
                    }

                }

                }
        } catch (Exception e) {
            e.printStackTrace();
        }


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
                    binding.includeBasicLayout.socLL.setVisibility(View.GONE);
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

        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportStockDetailsActivity.this, GCCReportsDashboard.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reportData.getInspectionFindings().getDrGodown() != null) {
                    Intent intent = new Intent(ReportStockDetailsActivity.this, ReportStockDetailsActivity.class);
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
                        pos = z;
                        break;
                    }
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

        //Pdf
        binding.header.ivPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_GODOWN)) {

                    customProgressDialog.show();
                    customProgressDialog.addText("Please wait...Downloading Pdf");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                                    directory_path = getExternalFilesDir(null)
                                            + "/" + "CTW/GCC/";
                                } else {
                                    directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                            + "/" + "CTW/GCC/";
                                }

                                filePath = directory_path + "Dr_Godown_" + reportData.getOfficerId() + "_" + reportData.getInspectionTime() + ".pdf";
                                List<View> views = new ArrayList<>();
                                views.add(binding.drGownTitlePdf);
                                views.add(binding.drGodownGeneralPdf);
                                views.add(binding.drGodownPhotosPdf);

                                PDFUtil.getInstance(ReportStockDetailsActivity.this).generatePDF(views, filePath, ReportStockDetailsActivity.this, "schemes", "GCC");


                            } catch (Exception e) {
                                if (customProgressDialog.isShowing())
                                    customProgressDialog.hide();
                                Toast.makeText(ReportStockDetailsActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 10000);

                } else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_DEPOT_REP)) {

                    customProgressDialog.show();
                    customProgressDialog.addText("Please wait...Downloading Pdf");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                                    directory_path = getExternalFilesDir(null)
                                            + "/" + "CTW/GCC/";
                                } else {
                                    directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                            + "/" + "CTW/GCC/";
                                }

                                filePath = directory_path + "Dr_Depot_" + reportData.getOfficerId() + "_" + reportData.getInspectionTime() + ".pdf";
                                File file = new File(filePath);
                                List<View> views = new ArrayList<>();
                                views.add(binding.drDepotTitlePdf);
                                views.add(binding.drDepotMfpPdf);
                                views.add(binding.drDepotGeneralPdf);
                                views.add(binding.drDepotPhotosPdf);

                                PDFUtil.getInstance(ReportStockDetailsActivity.this).generatePDF(views, filePath, ReportStockDetailsActivity.this, "schemes", "GCC");

                            } catch (Exception e) {
                                if (customProgressDialog.isShowing())
                                    customProgressDialog.hide();

                                Toast.makeText(ReportStockDetailsActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 10000);

                } else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_MFP_GODOWN_REP)) {
                    customProgressDialog.show();
                    customProgressDialog.addText("Please wait...Downloading Pdf");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            try {

                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                                    directory_path = getExternalFilesDir(null)
                                            + "/" + "CTW/GCC/";
                                } else {
                                    directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                            + "/" + "CTW/GCC/";
                                }

                                filePath = directory_path + "MFP_" + reportData.getOfficerId() + "_" + reportData.getInspectionTime() + ".pdf";
                                File file = new File(filePath);
                                List<View> views = new ArrayList<>();
                                views.add(binding.mfpTitlePdf);
                                views.add(binding.mfpGeneralPdf);
                                views.add(binding.mfpPhotosPdf);

                                PDFUtil.getInstance(ReportStockDetailsActivity.this).generatePDF(views, filePath, ReportStockDetailsActivity.this, "schemes", "GCC");

                            } catch (Exception e) {
                                if (customProgressDialog.isShowing())
                                    customProgressDialog.hide();

                                Toast.makeText(ReportStockDetailsActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 10000);

                } else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PUNIT_REP)) {
                    customProgressDialog.show();
                    customProgressDialog.addText("Please wait...Downloading Pdf");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                                    directory_path = getExternalFilesDir(null)
                                            + "/" + "CTW/GCC/";
                                } else {
                                    directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                            + "/" + "CTW/GCC/";
                                }

                                filePath = directory_path + "Processing_Unit_" + reportData.getOfficerId() + "_" + reportData.getInspectionTime() + ".pdf";
                                File file = new File(filePath);
                                List<View> views = new ArrayList<>();
                                views.add(binding.punitRegistersPdf);
                                views.add(binding.punitGeneralPdf);
                                views.add(binding.punitPhotosPdf);

                                PDFUtil.getInstance(ReportStockDetailsActivity.this).generatePDF(views, filePath, ReportStockDetailsActivity.this, "schemes", "GCC");

                            } catch (Exception e) {
                                if (customProgressDialog.isShowing())
                                    customProgressDialog.hide();

                                Toast.makeText(ReportStockDetailsActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 10000);

                } else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PETROL_REP)) {

                    customProgressDialog.show();
                    customProgressDialog.addText("Please wait...Downloading Pdf");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                                    directory_path = getExternalFilesDir(null)
                                            + "/" + "CTW/GCC/";
                                } else {
                                    directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                            + "/" + "CTW/GCC/";
                                }

                                filePath = directory_path + "Petrol_Pump_" + reportData.getOfficerId() + "_" + reportData.getInspectionTime() + ".pdf";
                                List<View> views = new ArrayList<>();
                                views.add(binding.petrolPumpTitlePdf);
                                views.add(binding.petrolPumpGeneralPdf);
                                views.add(binding.petrolPumpPhotosPdf);

                                PDFUtil.getInstance(ReportStockDetailsActivity.this).generatePDF(views, filePath, ReportStockDetailsActivity.this, "schemes", "GCC");

                            } catch (Exception e) {
                                if (customProgressDialog.isShowing())
                                    customProgressDialog.hide();

                                Toast.makeText(ReportStockDetailsActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 10000);

                } else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_LPG_REP)) {
                    customProgressDialog.show();
                    customProgressDialog.addText("Please wait...Downloading Pdf");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            try {

                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                                    directory_path = getExternalFilesDir(null)
                                            + "/" + "CTW/GCC/";
                                } else {
                                    directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                            + "/" + "CTW/GCC/";
                                }

                                filePath = directory_path + "LPG_" + reportData.getOfficerId() + "_" + reportData.getInspectionTime() + ".pdf";
                                File file = new File(filePath);
                                List<View> views = new ArrayList<>();
                                views.add(binding.lpgTitlePdf);
                                views.add(binding.lpgGeneralPdf);
                                views.add(binding.lpgPhotosPdf);

                                PDFUtil.getInstance(ReportStockDetailsActivity.this).generatePDF(views, filePath, ReportStockDetailsActivity.this, "schemes", "GCC");

                            } catch (Exception e) {
                                if (customProgressDialog.isShowing())
                                    customProgressDialog.hide();

                                Toast.makeText(ReportStockDetailsActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, 10000);

                } else {
                    customProgressDialog.hide();
                    Toast.makeText(ReportStockDetailsActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (reportData != null && (reportData.getStockDetails() != null && !punit_flag &&
                !dailyreq_flag && !emp_flag && !ess_flag && !mfp_flag && !petrol_flag && !lpg_flag)) {
            binding.viewPager.setVisibility(View.GONE);
            binding.tabs.setVisibility(View.GONE);
            binding.noDataTv.setVisibility(View.VISIBLE);

        }

        binding.ivShopCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos != -1) {
                    Utils.displayPhotoDialogBox(reportData.getPhotos().get(pos).getFilePath(),
                            ReportStockDetailsActivity.this, reportData.getPhotos().get(pos).getFileName(), true);
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
    public void pdfGenerationSuccess(File savedPDFFile) {
        customProgressDialog.hide();

        Utils.customPDFAlert(ReportStockDetailsActivity.this, getString(R.string.app_name),
                "PDF File Generated Successfully. \n File saved at " + savedPDFFile + "\n Do you want open it?", savedPDFFile);
    }

    @Override
    public void pdfGenerationFailure(Exception exception) {
        customProgressDialog.hide();

        Utils.customErrorAlert(ReportStockDetailsActivity.this, getString(R.string.app_name), getString(R.string.something) + " " + exception.getMessage());
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
