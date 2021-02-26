package com.cgg.twdinspection.gcc.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.screenshot.ItextMerge;
import com.cgg.twdinspection.common.screenshot.PDFUtil;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityReportStockDetailsBinding;
import com.cgg.twdinspection.gcc.reports.adapter.CommCommodityAdapter;
import com.cgg.twdinspection.gcc.reports.adapter.ViewPhotoAdapterPdf;
import com.cgg.twdinspection.gcc.reports.fragments.DailyReportFragment;
import com.cgg.twdinspection.gcc.reports.fragments.EmptiesReportFragment;
import com.cgg.twdinspection.gcc.reports.fragments.EssentialReportFragment;
import com.cgg.twdinspection.gcc.reports.fragments.LPGlReportFragment;
import com.cgg.twdinspection.gcc.reports.fragments.MFPReportFragment;
import com.cgg.twdinspection.gcc.reports.fragments.PUnitReportFragment;
import com.cgg.twdinspection.gcc.reports.fragments.PetrollReportFragment;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.cgg.twdinspection.gcc.reports.source.ReportSubmitReqCommodities;
import com.cgg.twdinspection.gcc.ui.fragment.DailyFragment;
import com.cgg.twdinspection.gcc.ui.fragment.EmptiesFragment;
import com.cgg.twdinspection.gcc.ui.fragment.EssentialFragment;
import com.cgg.twdinspection.gcc.ui.fragment.MFPFragment;
import com.cgg.twdinspection.gcc.ui.fragment.PUnitFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportStockDetailsActivity extends AppCompatActivity implements PDFUtil.PDFUtilListener, ItextMerge.PDFMergeListener {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ActivityReportStockDetailsBinding binding;
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Fragment> mFragmentList = new ArrayList<>();
    ReportData reportData;
    private boolean punit_flag, dailyreq_flag, emp_flag, ess_flag, mfp_flag, petrol_flag, lpg_flag;
    int pos = -1;
    CustomProgressDialog customProgressDialog;
    String directory_path, filePath, filePath1, filePath2, filePath_temp;
    ViewPhotoAdapterPdf adapter;
    private static final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private List<ReportSubmitReqCommodities> essentialList, dailyreqList, emptiesList, mfpList, punitList, petrolList, lpgList;
    Gson gson;
    String folder = AppConstants.GCC_FOLDER;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_stock_details);
        binding.header.ivPdf.setVisibility(View.VISIBLE);
        customProgressDialog = new CustomProgressDialog(this);
        binding.bottomLl.btnNext.setText(getString(R.string.next));

        EssentialFragment.commonCommodities = null;
        DailyFragment.commonCommodities = null;
        EmptiesFragment.commonCommodities = null;
        MFPFragment.commonCommodities = null;
        PUnitFragment.commonCommodities = null;

        sharedPreferences = TWDApplication.get(ReportStockDetailsActivity.this).getPreferences();
        gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.REP_DATA, "");
        reportData = gson.fromJson(data, ReportData.class);

        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_GODOWN)) {
            binding.header.headerTitle.setText(getString(R.string.dr_godown_rep));
        }
        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_DEPOT_REP)) {
            binding.header.headerTitle.setText(getString(R.string.dr_dep));
        }
        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_MFP_GODOWN_REP)) {
            binding.header.headerTitle.setText(R.string.mfp_rep);
        }
        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PUNIT_REP)) {
            binding.header.headerTitle.setText(getString(R.string.p_unit_rep));
        }
        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PETROL_REP)) {
            binding.header.headerTitle.setText(getString(R.string.petrol_rep));
        }
        if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_LPG_REP)) {
            binding.header.headerTitle.setText(getString(R.string.lpg_rep));
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

                    setDetails(binding.drGodownDivName, binding.drGodownSocName, binding.drGodownName,
                            binding.drGodownInchargeName, binding.drGodownTvDate);
                    setPhotosAdapter(binding.drGodownRecyclerView);
                    setRepairImage(binding.drGodownIvRepairsPdf);
                    addCommoditiesListData();

                } else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_DEPOT_REP)) {

                    if (reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getDrDepot() != null)
                        binding.setDrDepot(reportData.getInspectionFindings().getDrDepot());

                    setDetails(binding.drDepotDivName, binding.drDepotSocName, binding.drDepotName,
                            binding.drDepotInchargeName, binding.drDepotTvDate);
                    setPhotosAdapter(binding.drDepotRecyclerView);
                    setRepairImage(binding.drDepotIvRepairsPdf);
                    addCommoditiesListData();

                } else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_MFP_GODOWN_REP)) {

                    if (reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getMfpGodowns() != null)
                        binding.setMfp(reportData.getInspectionFindings().getMfpGodowns());

                    setDetails(binding.mfpDivName, binding.mfpName, binding.mfpInchargeName, binding.mfpTvDate);
                    setPhotosAdapter(binding.mfpRecyclerView);
                    setRepairImage(binding.mfpIvRepairsPdf);
                    addCommoditiesListData();

                } else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PUNIT_REP)) {

                    if (reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getProcessingUnit() != null)
                        binding.setPUnit(reportData.getInspectionFindings().getProcessingUnit());

                    setDetails(binding.punitDivName, binding.punitSocName, binding.punitName,
                            binding.punitInchargeName, binding.punitTvDate);

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

                    setPhotosAdapter(binding.punitRecyclerView);
                    addCommoditiesListData();

                } else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PETROL_REP)) {

                    if (reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getPetrolPump() != null)
                        binding.setPetrolpump(reportData.getInspectionFindings().getPetrolPump());

                    setDetails(binding.petrolPumpDivName, binding.petrolPumpSocName, binding.petrolPumpName,
                            binding.petrolPumpInchargeName, binding.petrolPumpTvDate);
                    setPhotosAdapter(binding.petrolPumpRecyclerView);
                    setRepairImage(binding.petrolPumpIvRepairsPdf);
                    addCommoditiesListData();

                } else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_LPG_REP)) {
                    if (reportData.getInspectionFindings() != null && reportData.getInspectionFindings().getLpg() != null)
                        binding.setLpg(reportData.getInspectionFindings().getLpg());

                    setDetails(binding.lpgDivName, binding.lpgSocName, binding.lpgName,
                            binding.lpgInchargeName, binding.lpgTvDate);
                    setPhotosAdapter(binding.lpgRecyclerView);
                    setRepairImage(binding.lpgIvRepairsPdf);
                    addCommoditiesListData();

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
                    binding.includeBasicLayout.drGodownNameTV.setText(getString(R.string.dr_godown));
                }
                if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_DEPOT_REP)) {
                    binding.includeBasicLayout.drGodownNameTV.setText(getString(R.string.dr_depot));
                }
                if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_MFP_GODOWN_REP)) {
                    binding.includeBasicLayout.drGodownNameTV.setText(getString(R.string.mfp_godown));
                    binding.includeBasicLayout.socLL.setVisibility(View.GONE);
                }
                if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PUNIT_REP)) {
                    binding.includeBasicLayout.drGodownNameTV.setText(getString(R.string.p_unit));
                }
                if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PETROL_REP)) {
                    binding.includeBasicLayout.drGodownNameTV.setText(getString(R.string.petrol_pump_title));
                }
                if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_LPG_REP)) {
                    binding.includeBasicLayout.drGodownNameTV.setText(getString(R.string.lpg_title));
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
                } else if (reportData.getInspectionFindings().getPetrolPump() != null) {
                    Intent intent = new Intent(ReportStockDetailsActivity.this, PetrolpumpInspRepActivity.class);
                    startActivity(intent);
                } else if (reportData.getInspectionFindings().getLpg() != null) {
                    Intent intent = new Intent(ReportStockDetailsActivity.this, LPGInspRepActivity.class);
                    startActivity(intent);
                } else {
                    callSnackBar();
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
            binding.header.ivPdf.setVisibility(View.GONE);

            if (reportData.getPhotos() != null && reportData.getPhotos().size() > 0) {
                for (int z = 0; z < reportData.getPhotos().size(); z++) {

                    if (!TextUtils.isEmpty(reportData.getPhotos().get(z).getFileName())
                            && reportData.getPhotos().get(z).getFileName().contains(AppConstants.SHOP_CLOSED)) {
                        Glide.with(ReportStockDetailsActivity.this)
                                .load(reportData.getPhotos().get(z).getFilePath())
                                .error(R.drawable.no_image)
                                .placeholder(R.drawable.camera)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        binding.pbar.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
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
                adapter.addFrag(essentialFragment, getString(R.string.ess_com));
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
                adapter.addFrag(dailyFragment, getString(R.string.dai_re));
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
                adapter.addFrag(emptiesFragment, getString(R.string.empties));
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
                adapter.addFrag(mfpFragment, getString(R.string.mfp_com));
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
                adapter.addFrag(pUnitFragment, getString(R.string.p_units));
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
                adapter.addFrag(petrollReportFragment, getString(R.string.petrol_com));
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
                adapter.addFrag(lpGlReportFragment, getString(R.string.lpg_com));
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
                    type = getString(R.string.dr_godown_type);
                    createPdf(binding.drGownTitlePdf, null,
                            binding.drGodownGeneralPdf, binding.drGodownPhotosPdf);
                } else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_DEPOT_REP)
                        && !TextUtils.isEmpty(reportData.getShopAvail())
                        && reportData.getShopAvail().equalsIgnoreCase(AppConstants.close)) {
                    type = getString(R.string.dr_depot_type);
                    createPdf(binding.drDepotCloseTitlePdf, null,
                            null, null);
                }else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_DEPOT_REP)) {
                    type = getString(R.string.dr_depot_type);
                    createPdf(binding.drDepotTitlePdf, binding.drDepotMfpPdf,
                            binding.drDepotGeneralPdf, binding.drDepotPhotosPdf);
                }  else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_MFP_GODOWN_REP)) {
                    type = getString(R.string.mfp_type);
                    createPdf(binding.mfpTitlePdf, null,
                            binding.mfpGeneralPdf, binding.mfpPhotosPdf);
                } else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PUNIT_REP)) {
                    type = getString(R.string.p_unit_type);
                    createPdf(binding.punitRegistersPdf, null,
                            binding.punitGeneralPdf, binding.punitPhotosPdf);
                } else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PETROL_REP)) {
                    type = getString(R.string.petrol_type);
                    createPdf(binding.petrolPumpTitlePdf, null,
                            binding.petrolPumpGeneralPdf, binding.petrolPumpPhotosPdf);
                } else if (reportData.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_LPG_REP)) {
                    type = getString(R.string.lpg_type);
                    createPdf(binding.lpgTitlePdf, null,
                            binding.lpgGeneralPdf, binding.lpgPhotosPdf);
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

    private void createPdf(LinearLayout titlePdf, LinearLayout mfpPdf,
                           LinearLayout generalPdf, LinearLayout photosPdf) {

        customProgressDialog.show();
        customProgressDialog.addText(getString(R.string.plz_wait));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    List<View> views = new ArrayList<>();
                    views.add(titlePdf);
                    if (mfpPdf != null)
//                        if (type.equalsIgnoreCase("Dr_Depot"))
                        views.add(mfpPdf);
                    if (generalPdf != null)
                        views.add(generalPdf);
                    if (photosPdf != null)
                        views.add(photosPdf);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                        filePath1 = PDFUtil.createPdfFile(ReportStockDetailsActivity.this,
                                type + "_" + reportData.getOfficerId() + "_" + reportData.getInspectionTime()
                                        + "_1" + ".pdf", folder);
                    } else {
                        directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                + "/" + "CTW/GCC/";
                        filePath = directory_path + type + "_" + reportData.getOfficerId() + "_" + reportData.getInspectionTime();
                        filePath1 = filePath + "_1" + ".pdf";
                    }

                    PDFUtil.getInstance(ReportStockDetailsActivity.this).generatePDF(views,
                            filePath1, ReportStockDetailsActivity.this);

                } catch (Exception e) {
                    if (customProgressDialog.isShowing())
                        customProgressDialog.hide();
                    Toast.makeText(ReportStockDetailsActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                }
            }
        }, 10000);
    }

    private void setRepairImage(ImageView imageView) {
        if (reportData.getPhotos() != null && reportData.getPhotos().size() > 0) {
            for (int z = 0; z < reportData.getPhotos().size(); z++) {

                if (!TextUtils.isEmpty(reportData.getPhotos().get(z).getFileName())
                        && reportData.getPhotos().get(z).getFileName().contains(AppConstants.REPAIR)) {

                    Glide.with(ReportStockDetailsActivity.this)
                            .load(reportData.getPhotos().get(z).getFilePath())
                            .error(R.drawable.no_image)
                            .placeholder(R.drawable.camera)
                            .into(imageView);
                    break;
                }
            }
        }
    }

    private void setPhotosAdapter(RecyclerView recyclerView) {
        String jsonObject = gson.toJson(reportData.getPhotos());
        if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("[]")) {
            adapter = new ViewPhotoAdapterPdf(this, reportData.getPhotos());
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            recyclerView.setAdapter(adapter);
        }
    }

    private void setDetails(TextView divName, TextView socName, TextView godownName, TextView inchargeName, TextView date) {
        divName.setText(reportData.getDivisionName());
        socName.setText(reportData.getSocietyName());
        godownName.setText(reportData.getGodownName());
        inchargeName.setText(reportData.getInchargeName());
        date.setText(reportData.getInspectionTime());
    }

    private void setDetails(TextView divName, TextView godownName, TextView inchargeName, TextView date) {
        divName.setText(reportData.getDivisionName());
        godownName.setText(reportData.getGodownName());
        inchargeName.setText(reportData.getInchargeName());
        date.setText(reportData.getInspectionTime());
    }

    private void addCommoditiesListData() {

        if (reportData != null && reportData.getStockDetails() != null) {
            if (reportData.getStockDetails().getEssentialCommodities() != null &&
                    reportData.getStockDetails().getEssentialCommodities().size() > 0) {

                String essential = gson.toJson(reportData.getStockDetails().getEssentialCommodities());
                if (!TextUtils.isEmpty(essential) && !essential.equalsIgnoreCase("{}")) {
                    essentialList = reportData.getStockDetails().getEssentialCommodities();
                    if (essentialList != null && essentialList.size() > 0) {
                        setAdapter(essentialList);
                    }
                }
            }
            if (reportData.getStockDetails().getDailyRequirements() != null &&
                    reportData.getStockDetails().getDailyRequirements().size() > 0) {
                String daily = gson.toJson(reportData.getStockDetails().getDailyRequirements());
                if (!TextUtils.isEmpty(daily) && !daily.equalsIgnoreCase("{}")) {
                    dailyreqList = reportData.getStockDetails().getDailyRequirements();
                    if (dailyreqList != null && dailyreqList.size() > 0) {
                        setAdapter(dailyreqList);
                    }
                }
            }
            if (reportData.getStockDetails().getEmpties() != null &&
                    reportData.getStockDetails().getEmpties().size() > 0) {
                String empties = gson.toJson(reportData.getStockDetails().getEmpties());
                if (!TextUtils.isEmpty(empties) && !empties.equalsIgnoreCase("{}")) {
                    emptiesList = reportData.getStockDetails().getEmpties();
                    if (emptiesList != null && emptiesList.size() > 0) {
                        setAdapter(emptiesList);
                    }
                }
            }
            if (reportData.getStockDetails().getMfpCommodities() != null &&
                    reportData.getStockDetails().getMfpCommodities().size() > 0) {
                String mfp = gson.toJson(reportData.getStockDetails().getMfpCommodities());
                if (!TextUtils.isEmpty(mfp) && !mfp.equalsIgnoreCase("{}")) {
                    mfpList = reportData.getStockDetails().getMfpCommodities();
                    if (mfpList != null && mfpList.size() > 0) {
                        setAdapter(mfpList);
                    }
                }
            }
            if (reportData.getStockDetails().getProcessingUnits() != null &&
                    reportData.getStockDetails().getProcessingUnits().size() > 0) {
                String punit = gson.toJson(reportData.getStockDetails().getProcessingUnits());
                if (!TextUtils.isEmpty(punit) && !punit.equalsIgnoreCase("{}")) {
                    punitList = reportData.getStockDetails().getProcessingUnits();
                    if (punitList != null && punitList.size() > 0) {
                        setAdapter(punitList);
                    }
                }
            }
            if (reportData.getStockDetails().getPetrolCommodities() != null &&
                    reportData.getStockDetails().getPetrolCommodities().size() > 0) {
                String petrolpump = gson.toJson(reportData.getStockDetails().getPetrolCommodities());
                if (!TextUtils.isEmpty(petrolpump) && !petrolpump.equalsIgnoreCase("{}")) {
                    petrolList = reportData.getStockDetails().getPetrolCommodities();
                    if (petrolList != null && petrolList.size() > 0) {
                        setAdapter(petrolList);
                    }
                }
            }
            if (reportData.getStockDetails().getLpgCommodities() != null &&
                    reportData.getStockDetails().getLpgCommodities().size() > 0) {
                String lpg = gson.toJson(reportData.getStockDetails().getLpgCommodities());
                if (!TextUtils.isEmpty(lpg) && !lpg.equalsIgnoreCase("{}")) {
                    lpgList = reportData.getStockDetails().getLpgCommodities();
                    if (lpgList != null && lpgList.size() > 0) {
                        setAdapter(lpgList);
                    }
                }
            }
        }

    }

    private void setAdapter(List<ReportSubmitReqCommodities> list) {

        CommCommodityAdapter stockSubAdapter = new CommCommodityAdapter(this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.commodities.groupRV.setLayoutManager(layoutManager);
        binding.commodities.groupRV.setAdapter(stockSubAdapter);
    }

    void callSnackBar() {
        Snackbar snackbar = Snackbar.make(binding.cl, getString(R.string.no_ins_data_found), Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    @Override
    public void pdfGenerationSuccess(File savedPDFFile) {
        customProgressDialog.hide();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            filePath2 = PDFUtil.createPdfFile(ReportStockDetailsActivity.this,
                    type + "_" + reportData.getOfficerId() + "_" + reportData.getInspectionTime()
                            + "_2" + ".pdf", folder);
        } else {
            filePath2 = filePath + "_2" + ".pdf";
        }

        try {

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(this.filePath2));
            document.open();

            if (essentialList != null || dailyreqList != null || emptiesList != null || mfpList != null ||
                    punitList != null || petrolList != null || lpgList != null) {

                customProgressDialog.show();
                customProgressDialog.addText(getString(R.string.plz_wait));

                if (essentialList != null && essentialList.size() > 0)
                    addCommoditiesContent(document, getString(R.string.ess_com), essentialList);
                if (dailyreqList != null && dailyreqList.size() > 0)
                    addCommoditiesContent(document, getString(R.string.dai_re), dailyreqList);
                if (emptiesList != null && emptiesList.size() > 0)
                    addCommoditiesContent(document, getString(R.string.empties), emptiesList);
                if (mfpList != null && mfpList.size() > 0)
                    addCommoditiesContent(document, getString(R.string.mfp_com), mfpList);
                if (punitList != null && punitList.size() > 0)
                    addCommoditiesContent(document, getString(R.string.p_units), punitList);
                if (petrolList != null && petrolList.size() > 0)
                    addCommoditiesContent(document, getString(R.string.petrol_com), petrolList);
                if (lpgList != null && lpgList.size() > 0)
                    addCommoditiesContent(document, getString(R.string.lpg_com), lpgList);
            }

            document.close();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                filePath_temp = PDFUtil.createPdfFile(ReportStockDetailsActivity.this,
                        type + "_" + reportData.getOfficerId() + "_" + reportData.getInspectionTime()
                                + "_temp" + ".pdf", folder);

                new ItextMerge(filePath_temp, filePath1, filePath2, ReportStockDetailsActivity.this);

            } else {
                new ItextMerge(filePath + "_temp", filePath1, filePath2, ReportStockDetailsActivity.this);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCommoditiesContent(Document document, String label, List<ReportSubmitReqCommodities> list)
            throws DocumentException {
        Paragraph paragraph1 = new Paragraph(label, catFont);
        document.add(paragraph1);
        addLineSeperator(document);
        createTable(document, list);
        addLineSeperator(document);
    }

    private void createFooter(Document document) {

        PdfPTable pdfPTable = new PdfPTable(1);
        pdfPTable.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

        PdfPTable pdfPTable1 = new PdfPTable(1);
        pdfPTable1.setWidthPercentage(80);
        pdfPTable1.addCell(getCell(reportData.getOfficerId(), PdfPCell.ALIGN_RIGHT));

        PdfPTable pdfPTable2 = new PdfPTable(1);
        pdfPTable2.setWidthPercentage(80);
        pdfPTable2.addCell(getCell(sharedPreferences.getString(AppConstants.OFFICER_DES, ""), PdfPCell.ALIGN_RIGHT));

        try {

            document.add(pdfPTable1);
            document.add(pdfPTable);
            document.add(pdfPTable2);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    private void addLineSeperator(Document document) {
        try {
            LineSeparator separator = new LineSeparator();
            separator.setPercentage(100);
            separator.setLineColor(BaseColor.WHITE);
            Chunk linebreak = new Chunk(separator);
            document.add(linebreak);

        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    private void createTable(Document document, List<ReportSubmitReqCommodities> list) {

        PdfPTable table = new PdfPTable(8);
        table.setTotalWidth(550);
        table.setLockedWidth(true);

        createCell(getString(R.string.comm_code), table);
        createCell(getString(R.string.comm_name), table);
        createCell(getString(R.string.qty_sys), table);
        createCell(getString(R.string.sys_rate), table);
        createCell(getString(R.string.sys_val), table);
        createCell(getString(R.string.phy_rate), table);
        createCell(getString(R.string.phy_val), table);
        createCell(getString(R.string.phy_ava_qty), table);

        table.setHeaderRows(1);

        try {

            for (int i = 0; i < list.size(); i++) {

                createCell(list.get(i).getComCode(), table);
                createCell(list.get(i).getComType(), table);

                PdfPCell c2;
                if (list.get(i).getUnits() != null && !list.get(i).getUnits().contains("No")) {
                    c2 = new PdfPCell(new Phrase(list.get(i).getSystemQty() + " " + list.get(i).getUnits()));
                } else {
                    c2 = new PdfPCell(new Phrase(String.valueOf(list.get(i).getSystemQty())));
                }
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                c2.setPaddingTop(5);
                c2.setPaddingBottom(5);
                table.addCell(c2);

                createCell("Rs" + list.get(i).getSystemRate(), table);
                createCell(String.valueOf(list.get(i).getSystemValue()), table);
                createCell("Rs" + list.get(i).getPhysicalRate(), table);
                createCell(String.valueOf(list.get(i).getPhysicalValue()), table);
                createCell(String.valueOf(list.get(i).getPhysiacalQty()), table);

            }
            table.setSplitLate(true);
            document.add(table);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createCell(String label, PdfPTable table) {
        PdfPCell c1 = new PdfPCell(new Phrase(label));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setPaddingTop(5);
        c1.setPaddingBottom(5);
        table.addCell(c1);
    }

    @Override
    public void pdfGenerationFailure(Exception exception) {
        customProgressDialog.hide();

        Utils.customErrorAlert(ReportStockDetailsActivity.this, getString(R.string.app_name), getString(R.string.something) + " " + exception.getMessage());
    }

    public void pdfMergeSuccess() {

        File savedPDFFile = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            filePath = PDFUtil.createPdfFile(ReportStockDetailsActivity.this,
                    type + "_" + reportData.getOfficerId() + "_" + reportData.getInspectionTime()
                            + ".pdf", folder);

            savedPDFFile = new File(filePath);

        } else {
            savedPDFFile = new File(filePath + ".pdf");
        }

        customProgressDialog.hide();
        try {
            addWatermark(savedPDFFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void pdfMergeFailure(Exception exception) {
        customProgressDialog.hide();
        Utils.customErrorAlert(ReportStockDetailsActivity.this, getString(R.string.app_name), getString(R.string.something) + " " + exception.getMessage());
    }


    public void addWatermark(File savedPDFFile) throws IOException, DocumentException {
        PdfStamper stamper;
        PdfReader reader;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            reader = new PdfReader(filePath_temp);
            stamper = new PdfStamper(reader, new FileOutputStream(filePath));
        } else {
            reader = new PdfReader(filePath + "_temp" + ".pdf");
            stamper = new PdfStamper(reader, new FileOutputStream(filePath + ".pdf"));
        }

        Font f = new Font(Font.FontFamily.HELVETICA, 11);
        f.setColor(BaseColor.GRAY);
        int n = reader.getNumberOfPages();

        for (int i = 1; i <= n; i++) {
            PdfContentByte over = stamper.getOverContent(i);
            Phrase p1 = new Phrase(reportData.getOfficerId() + ", " + sharedPreferences.getString(AppConstants.OFFICER_DES, ""), f);
            ColumnText.showTextAligned(over, Element.ALIGN_RIGHT, p1, 550, 30, 0);
            Phrase p2 = new Phrase("Inspection Report-GCC" + ", " + reportData.getInspectionTime(), f);
            ColumnText.showTextAligned(over, Element.ALIGN_RIGHT, p2, 550, 15, 0);
            over.saveState();
            PdfGState gs1 = new PdfGState();
            gs1.setFillOpacity(0.5f);
            over.setGState(gs1);
            over.restoreState();
        }
        stamper.close();
        reader.close();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            File file = new File(filePath_temp);
            file.delete();
        } else {
            File file = new File(filePath + "_temp" + ".pdf");
            file.delete();
        }
        Utils.customPDFAlert(ReportStockDetailsActivity.this, getString(R.string.app_name),
                "PDF File Generated Successfully. \n File saved at "
                        + savedPDFFile + "\n Do you want open it?", savedPDFFile);

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
