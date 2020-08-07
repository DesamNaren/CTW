package com.cgg.twdinspection.gcc.ui.drdepot;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.cgg.twdinspection.BuildConfig;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.custom.CustomFontEditText;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityDrDepotFindingsBinding;
import com.cgg.twdinspection.gcc.source.inspections.DrDepot.DrDepotInsp;
import com.cgg.twdinspection.gcc.source.inspections.DrDepot.GeneralFindings;
import com.cgg.twdinspection.gcc.source.inspections.DrDepot.HoardingsBoards;
import com.cgg.twdinspection.gcc.source.inspections.DrDepot.MFPRegisters;
import com.cgg.twdinspection.gcc.source.inspections.DrDepot.RegisterBookCertificates;
import com.cgg.twdinspection.gcc.source.inspections.DrDepot.StockDetails;
import com.cgg.twdinspection.gcc.source.inspections.InspectionSubmitResponse;
import com.cgg.twdinspection.gcc.source.stock.CommonCommodity;
import com.cgg.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.cgg.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.cgg.twdinspection.gcc.ui.gcc.GCCPhotoActivity;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class DRDepotFindingsActivity extends LocBaseActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ActivityDrDepotFindingsBinding binding;
    String PIC_NAME, PIC_TYPE;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public Uri fileUri;
    public static final String IMAGE_DIRECTORY_NAME = "GCC_IMAGES";
    String FilePath, checkUpDate;
    Bitmap bm;
    File file;
    private String officerID, divId, suppId;
    double physVal = 0, sysVal = 0, difference = 0, insSysVal = 0, notInsSysVal = 0;
    private StockDetailsResponse stockDetailsResponse;
    private String ecsStock, drStock, emptyStock, abstractSales, depotCashBook, liabilityReg, visitorsBook, saleBook, weightsMeasurements, certIssueDate;
    private String depotAuthCert, mfpStock, mfpPurchase, billAbstract, abstractAccnt, advanceAccnt, mfpLiability, depotNameBoard, gccObjPrinc;
    private String depotTimimg, mfpComm, ecComm, drComm, stockBal, valuesAsPerSale, valuesAsPerPurchasePrice, qualVerified, depotMaintHygeine, repairsReq, repairsType;
    private String cashBal, phyCash, vocBills, liaBal, deficitBal, reason, remarks, feedback;
    private int repairsFlag = 0;
    private String randomNum;
    private List<CommonCommodity> finalDaiCom, finalEssCom, finalEmpCom;
    private StockDetailsResponse finalStockDetailsResponse;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_depot_findings);
        binding.header.headerTitle.setText(getString(R.string.dr_depot_ins_off_fin));
        binding.header.ivHome.setVisibility(View.GONE);
        binding.bottomLl.btnNext.setText(getString(R.string.saveandnext));
        randomNum = Utils.getRandomNumberString();

        finalDaiCom = new ArrayList<>();
        finalEssCom = new ArrayList<>();
        finalEmpCom = new ArrayList<>();

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String stockData = sharedPreferences.getString(AppConstants.stockData, "");
        officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        Gson gson = new Gson();
        stockDetailsResponse = gson.fromJson(stockData, StockDetailsResponse.class);
        String depotData = sharedPreferences.getString(AppConstants.DR_DEPOT_DATA, "");
        DRDepots drDepot = gson.fromJson(depotData, DRDepots.class);
        divId = drDepot.getDivisionId();
        suppId = drDepot.getGodownId();

        if (stockDetailsResponse != null) {
            if (stockDetailsResponse.getEssential_commodities() != null && stockDetailsResponse.getEssential_commodities().size() > 0) {
                for (int i = 0; i < stockDetailsResponse.getEssential_commodities().size(); i++) {
                    if (!TextUtils.isEmpty(stockDetailsResponse.getEssential_commodities().get(i).getPhyQuant())) {
                        physVal += Double.parseDouble(stockDetailsResponse.getEssential_commodities().get(i).getPhyQuant()) * stockDetailsResponse.getEssential_commodities().get(i).getRate();
                        insSysVal += stockDetailsResponse.getEssential_commodities().get(i).getQty() * stockDetailsResponse.getEssential_commodities().get(i).getRate();
                        finalEssCom.add(stockDetailsResponse.getEssential_commodities().get(i));
                    }
                    sysVal += stockDetailsResponse.getEssential_commodities().get(i).getQty() * stockDetailsResponse.getEssential_commodities().get(i).getRate();
                }
            }
            if (stockDetailsResponse.getDialy_requirements() != null && stockDetailsResponse.getDialy_requirements().size() > 0) {
                for (int i = 0; i < stockDetailsResponse.getDialy_requirements().size(); i++) {
                    if (!TextUtils.isEmpty(stockDetailsResponse.getDialy_requirements().get(i).getPhyQuant())) {
                        physVal += Double.parseDouble(stockDetailsResponse.getDialy_requirements().get(i).getPhyQuant()) * stockDetailsResponse.getDialy_requirements().get(i).getRate();
                        insSysVal += stockDetailsResponse.getDialy_requirements().get(i).getQty() * stockDetailsResponse.getDialy_requirements().get(i).getRate();
                        finalDaiCom.add(stockDetailsResponse.getDialy_requirements().get(i));
                    }
                    sysVal += stockDetailsResponse.getDialy_requirements().get(i).getQty() * stockDetailsResponse.getDialy_requirements().get(i).getRate();
                }
            }
            if (stockDetailsResponse.getEmpties() != null && stockDetailsResponse.getEmpties().size() > 0) {
                for (int i = 0; i < stockDetailsResponse.getEmpties().size(); i++) {
                    if (!TextUtils.isEmpty(stockDetailsResponse.getEmpties().get(i).getPhyQuant())) {
                        physVal += Double.parseDouble(stockDetailsResponse.getEmpties().get(i).getPhyQuant()) * stockDetailsResponse.getEmpties().get(i).getRate();
                        insSysVal += stockDetailsResponse.getEmpties().get(i).getQty() * stockDetailsResponse.getEmpties().get(i).getRate();
                        finalEmpCom.add(stockDetailsResponse.getEmpties().get(i));
                    }
                    sysVal += stockDetailsResponse.getEmpties().get(i).getQty() * stockDetailsResponse.getEmpties().get(i).getRate();
                }
            }

            sysVal = Double.valueOf(String.format("%.2f", sysVal));
            physVal = Double.valueOf(String.format("%.2f", physVal));
            binding.tvSysVal.setText(String.format("%.2f", sysVal));
            binding.tvPhysVal.setText(String.format("%.2f", physVal));
            notInsSysVal = sysVal - insSysVal;
            notInsSysVal = Double.valueOf(String.format("%.2f", notInsSysVal));
            binding.tvSysValIns.setText(String.format("%.2f", insSysVal));
            binding.tvSysValNotIns.setText(String.format("%.2f", notInsSysVal));
            difference = insSysVal - physVal;
            difference = Double.valueOf(String.format("%.2f", difference));
            binding.tvDiffVal.setText(String.format("%.2f", difference));

        }


        binding.rgWeightMeasCert.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.weight_meas_cert_rb_yes) {
                    binding.weightCertIssueDate.setVisibility(View.VISIBLE);
                    weightsMeasurements = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.weight_meas_cert_rb_no) {
                    weightsMeasurements = AppConstants.No;
                    binding.weightCertIssueDate.setVisibility(View.GONE);
                }
            }
        });
        binding.etWeightCertIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                certIssueDateSelection();
            }
        });
        binding.rgRepairsReq.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.repairs_req_yes_rb) {
                    repairsReq = AppConstants.Yes;
                    binding.insLl.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.repairs_req_no_rb) {
                    repairsReq = AppConstants.No;
                    binding.insLl.setVisibility(View.GONE);
                }
            }
        });
        binding.ivRepairsCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {

                    PIC_TYPE = AppConstants.REPAIR;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });

        binding.rgEcsStock.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgEcsStock.getCheckedRadioButtonId();
                if (selctedItem == R.id.ecs_stock_yes_rb)
                    ecsStock = AppConstants.Yes;
                else
                    ecsStock = AppConstants.No;
            }
        });

        binding.rgDrStock.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDrStock.getCheckedRadioButtonId();
                if (selctedItem == R.id.dr_stock_rb_yes)
                    drStock = AppConstants.Yes;
                else
                    drStock = AppConstants.No;
            }
        });

        binding.rgEmptiesStock.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgEmptiesStock.getCheckedRadioButtonId();
                if (selctedItem == R.id.empties_stock_rb_yes)
                    emptyStock = AppConstants.Yes;
                else
                    emptyStock = AppConstants.No;
            }
        });

        binding.rgAbstractSales.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAbstractSales.getCheckedRadioButtonId();
                if (selctedItem == R.id.abstract_sales_rb_yes)
                    abstractSales = AppConstants.Yes;
                else
                    abstractSales = AppConstants.No;
            }
        });

        binding.rgDepotCashBook.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDepotCashBook.getCheckedRadioButtonId();
                if (selctedItem == R.id.depot_cash_book_yes_rb)
                    depotCashBook = AppConstants.Yes;
                else
                    depotCashBook = AppConstants.No;
            }
        });

        binding.rgLiabilityReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgLiabilityReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.liability_reg_rb_yes)
                    liabilityReg = AppConstants.Yes;
                else
                    liabilityReg = AppConstants.No;
            }
        });

        binding.rgVisitBookDepot.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgVisitBookDepot.getCheckedRadioButtonId();
                if (selctedItem == R.id.visit_book_depot_rb_yes)
                    visitorsBook = AppConstants.Yes;
                else
                    visitorsBook = AppConstants.No;
            }
        });

        binding.rgSaleBillBook.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSaleBillBook.getCheckedRadioButtonId();
                if (selctedItem == R.id.sale_bill_book_rb_yes)
                    saleBook = AppConstants.Yes;
                else
                    saleBook = AppConstants.No;
            }
        });

        binding.rgDepotAuthCert.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDepotAuthCert.getCheckedRadioButtonId();
                if (selctedItem == R.id.depot_auth_cert_rb_yes)
                    depotAuthCert = AppConstants.Yes;
                else
                    depotAuthCert = AppConstants.No;
            }
        });

        binding.rgMfpStock.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMfpStock.getCheckedRadioButtonId();
                if (selctedItem == R.id.mfp_stock_yes_rb)
                    mfpStock = AppConstants.Yes;
                else
                    mfpStock = AppConstants.No;
            }
        });

        binding.rgMfpPurchase.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMfpPurchase.getCheckedRadioButtonId();
                if (selctedItem == R.id.mfp_purchase_rb_yes)
                    mfpPurchase = AppConstants.Yes;
                else
                    mfpPurchase = AppConstants.No;
            }
        });

        binding.rgBillAbstract.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBillAbstract.getCheckedRadioButtonId();
                if (selctedItem == R.id.bill_abstract_rb_yes)
                    billAbstract = AppConstants.Yes;
                else
                    billAbstract = AppConstants.No;
            }
        });

        binding.rgAbstractAccntBook.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAbstractAccntBook.getCheckedRadioButtonId();
                if (selctedItem == R.id.abstract_accnt_book_rb_yes)
                    abstractAccnt = AppConstants.Yes;
                else
                    abstractAccnt = AppConstants.No;
            }
        });

        binding.rgAdvanceAccntBook.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAdvanceAccntBook.getCheckedRadioButtonId();
                if (selctedItem == R.id.advance_accnt_book_yes_rb)
                    advanceAccnt = AppConstants.Yes;
                else
                    advanceAccnt = AppConstants.No;
            }
        });

        binding.rgMfpLiabilityReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMfpLiabilityReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.mfp_liability_reg_rb_yes)
                    mfpLiability = AppConstants.Yes;
                else
                    mfpLiability = AppConstants.No;
            }
        });

        binding.rgDepotNameBoard.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDepotNameBoard.getCheckedRadioButtonId();
                if (selctedItem == R.id.depot_name_board_yes_rb)
                    depotNameBoard = AppConstants.Yes;
                else
                    depotNameBoard = AppConstants.No;
            }
        });

        binding.rgObjPrinc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgObjPrinc.getCheckedRadioButtonId();
                if (selctedItem == R.id.obj_princ_rb_yes)
                    gccObjPrinc = AppConstants.Yes;
                else
                    gccObjPrinc = AppConstants.No;
            }
        });

        binding.rgDepotTimimg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDepotTimimg.getCheckedRadioButtonId();
                if (selctedItem == R.id.depot_timimg_rb_yes)
                    depotTimimg = AppConstants.Yes;
                else
                    depotTimimg = AppConstants.No;
            }
        });

        binding.rgCommRate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCommRate.getCheckedRadioButtonId();
                if (selctedItem == R.id.comm_rate_rb_yes)
                    mfpComm = AppConstants.Yes;
                else
                    mfpComm = AppConstants.No;
            }
        });

        binding.rgEcCommRate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgEcCommRate.getCheckedRadioButtonId();
                if (selctedItem == R.id.ec_comm_rate_yes_rb)
                    ecComm = AppConstants.Yes;
                else
                    ecComm = AppConstants.No;
            }
        });

        binding.rgDrCommRate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDrCommRate.getCheckedRadioButtonId();
                if (selctedItem == R.id.dr_comm_rate_rb_yes)
                    drComm = AppConstants.Yes;
                else
                    drComm = AppConstants.No;
            }
        });

        binding.rgStockBal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStockBal.getCheckedRadioButtonId();
                if (selctedItem == R.id.stock_bal_rb_yes)
                    stockBal = AppConstants.Yes;
                else
                    stockBal = AppConstants.No;
            }
        });

        binding.rgValuesAsPerSalePrice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgValuesAsPerSalePrice.getCheckedRadioButtonId();
                if (selctedItem == R.id.values_as_per_sale_price_yes_rb)
                    valuesAsPerSale = AppConstants.Yes;
                else
                    valuesAsPerSale = AppConstants.No;
            }
        });

        binding.rgValuesAsPerPurchasePrice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgValuesAsPerPurchasePrice.getCheckedRadioButtonId();
                if (selctedItem == R.id.values_as_per_purchase_price_rb_yes)
                    valuesAsPerPurchasePrice = AppConstants.Yes;
                else
                    valuesAsPerPurchasePrice = AppConstants.No;
            }
        });

        binding.rgQualVerified.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgQualVerified.getCheckedRadioButtonId();
                if (selctedItem == R.id.qual_verified_rb_yes)
                    qualVerified = AppConstants.Yes;
                else
                    qualVerified = AppConstants.No;
            }
        });

        binding.rgDepotMaintHygeine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDepotMaintHygeine.getCheckedRadioButtonId();
                if (selctedItem == R.id.depot_maint_hygeine_rb_yes)
                    depotMaintHygeine = AppConstants.Yes;
                else
                    depotMaintHygeine = AppConstants.No;
            }
        });
        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                repairsType = binding.etRepairsType.getText().toString().trim();
                remarks = binding.etRemarks.getText().toString().trim();
                cashBal = binding.etCashBal.getText().toString().trim();
                phyCash = binding.etPhysCash.getText().toString().trim();
                vocBills = binding.etAdjVouch.getText().toString().trim();
                liaBal = binding.etLiabBal.getText().toString().trim();
                deficitBal = binding.tvDefExc.getText().toString().trim();
                reason = binding.etReason.getText().toString().trim();
                feedback = binding.etFeedback.getText().toString().trim();

                if (validate()) {

                    DrDepotInsp drDepotInspection = new DrDepotInsp();

                    StockDetails stockDetails = new StockDetails();
                    stockDetails.setStockValueAsPerSystem(sysVal);
                    stockDetails.setStockValueAsPerPhysical(physVal);
                    stockDetails.setDifference(difference);
                    stockDetails.setCashBalAsPerCashBook(Double.valueOf(cashBal));
                    stockDetails.setPhysicalCash(Double.valueOf(phyCash));
                    stockDetails.setVouchers(Double.valueOf(vocBills));
                    stockDetails.setPhysicalCash(Double.valueOf(phyCash));
                    stockDetails.setLiabilityBalance(Double.valueOf(liaBal));
                    stockDetails.setDeficit(Double.valueOf(deficitBal));
                    stockDetails.setDeficit_reason(reason);
                    stockDetails.setStockValueAsPerSystemInsp(String.valueOf(insSysVal));
                    stockDetails.setStockValueAsPerSystemNotInsp(String.valueOf(notInsSysVal));
                    drDepotInspection.setStockDetails(stockDetails);

                    RegisterBookCertificates registerBookCertificates = new RegisterBookCertificates();
                    registerBookCertificates.setEcsStockRegister(ecsStock);
                    registerBookCertificates.setDrsStockRegister(drStock);
                    registerBookCertificates.setEmptiesRegister(emptyStock);
                    registerBookCertificates.setAbstractSalesRegister(abstractSales);
                    registerBookCertificates.setLiabilityRegister(liabilityReg);
                    registerBookCertificates.setVisitorsNoteBook(visitorsBook);
                    registerBookCertificates.setSaleBillBook(saleBook);
                    registerBookCertificates.setCashBook(depotCashBook);
                    registerBookCertificates.setWeightMeasureCertificate(weightsMeasurements);
                    registerBookCertificates.setWeightMeasureValidity(binding.etWeightCertIssue.getText().toString().trim());
                    registerBookCertificates.setDepotAuthCertificate(depotAuthCert);
                    drDepotInspection.setRegisterBookCertificates(registerBookCertificates);

                    MFPRegisters mfpRegisters = new MFPRegisters();
                    mfpRegisters.setMfpStock(mfpStock);
                    mfpRegisters.setMfpPurchase(mfpPurchase);
                    mfpRegisters.setBillAbstract(billAbstract);
                    mfpRegisters.setAbstractAccnt(abstractAccnt);
                    mfpRegisters.setAdvanceAccnt(advanceAccnt);
                    mfpRegisters.setMfpLiability(mfpLiability);
                    drDepotInspection.setMfpRegisters(mfpRegisters);

                    HoardingsBoards hoardingsBoards = new HoardingsBoards();
                    hoardingsBoards.setDepotNameBoard(depotNameBoard);
                    hoardingsBoards.setGccObjPrinciples(gccObjPrinc);
                    hoardingsBoards.setDepotTiming(depotTimimg);
                    hoardingsBoards.setMfpCommodities(mfpComm);
                    hoardingsBoards.setEcCommodities(ecComm);
                    hoardingsBoards.setDrCommodities(drComm);
                    hoardingsBoards.setStockBal(stockBal);
                    drDepotInspection.setHoardingsBoards(hoardingsBoards);

                    GeneralFindings generalFindings = new GeneralFindings();
                    generalFindings.setValuesAsPerSalePrice(valuesAsPerSale);
                    generalFindings.setValuesAsPerPurchasePrice(valuesAsPerPurchasePrice);
                    generalFindings.setStockQualityVerified(qualVerified);
                    generalFindings.setHygienicCondition(depotMaintHygeine);
                    generalFindings.setRepairsRequired(repairsReq);
                    generalFindings.setRepairType(repairsType);
                    generalFindings.setRemarks(remarks);
                    generalFindings.setFeedback(feedback);
                    drDepotInspection.setGeneralFindings(generalFindings);

                    InspectionSubmitResponse inspectionSubmitResponse = new InspectionSubmitResponse();
                    inspectionSubmitResponse.setDrDepot(drDepotInspection);
                    try {
                        editor = sharedPreferences.edit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    editor.putString(AppConstants.repairsPath, FilePath);
                    editor.putString(AppConstants.TOTAL_PHYVAL, String.valueOf(physVal));
                    editor.putString(AppConstants.TOTAL_SYSVAL, String.valueOf(sysVal));
                    String inspectionDetails = gson.toJson(inspectionSubmitResponse);
                    editor.putString(AppConstants.InspectionDetails, inspectionDetails);
                    editor.putString(AppConstants.randomNum, randomNum);

                    finalStockDetailsResponse = new StockDetailsResponse();
                    finalStockDetailsResponse.setEssential_commodities(finalEssCom);
                    finalStockDetailsResponse.setDialy_requirements(finalDaiCom);
                    finalStockDetailsResponse.setEmpties(finalEmpCom);
                    finalStockDetailsResponse.setMfp_commodities(null);
                    finalStockDetailsResponse.setProcessing_units(null);

                    String stockData = gson.toJson(finalStockDetailsResponse);
                    editor.putString(AppConstants.finalStockData, stockData);

                    editor.commit();

                    startActivity(new Intent(DRDepotFindingsActivity.this, GCCPhotoActivity.class)
                            .putExtra(AppConstants.TITLE, getString(R.string.dr_depot_upload_photos)));
                }
            }
        });

        RxTextView
                .textChangeEvents(binding.etPhysCash)
                .debounce(100, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TextViewTextChangeEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {

                        cashBal = textViewTextChangeEvent.text().toString();
                        if (!TextUtils.isEmpty(cashBal) && !cashBal.equals(".")) {
                            if (!TextUtils.isEmpty(vocBills) && !vocBills.equals(".")) {
                                if (!TextUtils.isEmpty(liaBal) && !liaBal.equals(".")) {
                                    double defBal = calcDef(cashBal, vocBills, liaBal);
                                    binding.tvDefExc.setText(String.valueOf(defBal));
                                }
                            }
                        } else {
                            binding.tvDefExc.setText("");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        RxTextView
                .textChangeEvents(binding.etAdjVouch)
                .debounce(100, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TextViewTextChangeEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {

                        vocBills = textViewTextChangeEvent.text().toString();
                        if (!TextUtils.isEmpty(vocBills) && !vocBills.equals(".")) {
                            if (!TextUtils.isEmpty(cashBal) && !cashBal.equals(".")) {
                                if (!TextUtils.isEmpty(liaBal) && !liaBal.equals(".")) {
                                    double defBal = calcDef(cashBal, vocBills, liaBal);
                                    binding.tvDefExc.setText(String.valueOf(defBal));
                                }
                            }
                        } else {
                            binding.tvDefExc.setText("");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        RxTextView
                .textChangeEvents(binding.etLiabBal)
                .debounce(100, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TextViewTextChangeEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {

                        liaBal = textViewTextChangeEvent.text().toString();
                        if (!TextUtils.isEmpty(liaBal) && !liaBal.equals(".")) {
                            if (!TextUtils.isEmpty(cashBal) && !cashBal.equals(".")) {
                                if (!TextUtils.isEmpty(vocBills) && !vocBills.equals(".")) {
                                    double defBal = calcDef(cashBal, vocBills, liaBal);
                                    binding.tvDefExc.setText(String.valueOf(defBal));
                                }
                            }
                        } else {
                            binding.tvDefExc.setText("");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private double calcDef(String cashBal, String vocBills, String liaBal) {
        double cashBalDbl = Double.valueOf(cashBal);
        double adjBalDbl = Double.valueOf(vocBills);
        double liaBalDbl = Double.valueOf(liaBal);
        return cashBalDbl + adjBalDbl + physVal - liaBalDbl;
    }

    private boolean validate() {
        boolean returnFlag = true;
        if (TextUtils.isEmpty(cashBal)) {
            returnFlag = false;
            showSnackBar("Enter cash balance as per cash book");
            ScrollToViewEditText(binding.etCashBal, "Enter cash balance as per cash book");
        } else if (TextUtils.isEmpty(phyCash)) {
            returnFlag = false;
            showSnackBar("Enter physical cash");
            ScrollToViewEditText(binding.etPhysCash, "Enter physical cash");
        } else if (TextUtils.isEmpty(vocBills)) {
            returnFlag = false;
            showSnackBar("Enter adjustment vouchers / bills");
            ScrollToViewEditText(binding.etAdjVouch, "Enter adjustment vouchers / bills");
        } else if (TextUtils.isEmpty(liaBal)) {
            returnFlag = false;
            showSnackBar("Enter liability balance as per liability register");
            ScrollToViewEditText(binding.etLiabBal, "Enter liability balance as per liability register");
        } else if (TextUtils.isEmpty(reason)) {
            returnFlag = false;
            showSnackBar("Enter reason");
            ScrollToViewEditText(binding.etReason, "Enter reason");
        } else if (TextUtils.isEmpty(ecsStock)) {
            returnFlag = false;
            showSnackBar("Please check Ecs stock register");
            ScrollToView(binding.rgEcsStock);
        } else if (TextUtils.isEmpty(drStock)) {
            returnFlag = false;
            showSnackBar("Please check DRs stock register");
            ScrollToView(binding.rgDrStock);
        } else if (TextUtils.isEmpty(emptyStock)) {
            returnFlag = false;
            showSnackBar("Please check empties stock register");
            ScrollToView(binding.rgEmptiesStock);
        } else if (TextUtils.isEmpty(abstractSales)) {
            returnFlag = false;
            showSnackBar("Please check abstract sales register");
            ScrollToView(binding.rgAbstractSales);
        } else if (TextUtils.isEmpty(depotCashBook)) {
            returnFlag = false;
            showSnackBar("Please check Cash book");
            ScrollToView(binding.rgDepotCashBook);
        } else if (TextUtils.isEmpty(liabilityReg)) {
            returnFlag = false;
            showSnackBar("Please check liability register");
            ScrollToView(binding.rgDepotCashBook);
        } else if (TextUtils.isEmpty(visitorsBook)) {
            returnFlag = false;
            showSnackBar("Please check visitors note book");
            ScrollToView(binding.rgVisitBookDepot);
        } else if (TextUtils.isEmpty(saleBook)) {
            returnFlag = false;
            showSnackBar("Please check sale bill book");
            ScrollToView(binding.rgSaleBillBook);
        } else if (TextUtils.isEmpty(weightsMeasurements)) {
            returnFlag = false;
            showSnackBar("Please check weights and measurements certificate issued by legal metrology");
            ScrollToView(binding.rgWeightMeasCert);
        } else if (!TextUtils.isEmpty(weightsMeasurements) && weightsMeasurements.equals(AppConstants.Yes) && !binding.etWeightCertIssue.getText().toString().contains("/")) {
            returnFlag = false;
            showSnackBar("Please select certificate issue date");
            ScrollToView(binding.etWeightCertIssue);
        } else if (TextUtils.isEmpty(depotAuthCert)) {
            returnFlag = false;
            showSnackBar("Please check depot authorisation certificate issued by revenue authorities");
            ScrollToView(binding.rgWeightMeasCert);
        } else if (TextUtils.isEmpty(mfpStock)) {
            returnFlag = false;
            showSnackBar("Please check MFP stock register");
            ScrollToView(binding.rgMfpStock);
        } else if (TextUtils.isEmpty(mfpPurchase)) {
            returnFlag = false;
            showSnackBar("Please check MFP purchase bill book");
            ScrollToView(binding.rgMfpPurchase);
        } else if (TextUtils.isEmpty(billAbstract)) {
            returnFlag = false;
            showSnackBar("Please check bill abstract book");
            ScrollToView(binding.rgBillAbstract);
        } else if (TextUtils.isEmpty(abstractAccnt)) {
            returnFlag = false;
            showSnackBar("Please check abstract account book");
            ScrollToView(binding.rgAbstractAccntBook);
        } else if (TextUtils.isEmpty(advanceAccnt)) {
            returnFlag = false;
            showSnackBar("Please check advance  account book");
            ScrollToView(binding.rgAdvanceAccntBook);
        } else if (TextUtils.isEmpty(mfpLiability)) {
            returnFlag = false;
            showSnackBar("Please check MFP liability register");
            ScrollToView(binding.rgMfpLiabilityReg);
        } else if (TextUtils.isEmpty(depotNameBoard)) {
            returnFlag = false;
            showSnackBar("Please check depot name board");
            ScrollToView(binding.rgDepotNameBoard);
        } else if (TextUtils.isEmpty(gccObjPrinc)) {
            returnFlag = false;
            showSnackBar("Please check GCC objectives/principles board");
            ScrollToView(binding.rgObjPrinc);
        } else if (TextUtils.isEmpty(depotTimimg)) {
            returnFlag = false;
            showSnackBar("Please check depot timing board");
            ScrollToView(binding.rgDepotTimimg);
        } else if (TextUtils.isEmpty(mfpComm)) {
            returnFlag = false;
            showSnackBar("Please check MFP commodities rate board");
            ScrollToView(binding.rgCommRate);
        } else if (TextUtils.isEmpty(ecComm)) {
            returnFlag = false;
            showSnackBar("Please check EC commodities rate board");
            ScrollToView(binding.rgEcCommRate);
        } else if (TextUtils.isEmpty(drComm)) {
            returnFlag = false;
            showSnackBar("Please check DR commodities rate board");
            ScrollToView(binding.rgDrCommRate);
        } else if (TextUtils.isEmpty(stockBal)) {
            returnFlag = false;
            showSnackBar("Please check stock balance board");
            ScrollToView(binding.rgStockBal);
        } else if (TextUtils.isEmpty(valuesAsPerSale)) {
            returnFlag = false;
            showSnackBar("Please check whether the values are as per sale price");
            ScrollToView(binding.rgValuesAsPerSalePrice);
        } else if (TextUtils.isEmpty(valuesAsPerPurchasePrice)) {
            returnFlag = false;
            showSnackBar("Please check whether the values are as per purchase price");
            ScrollToView(binding.rgValuesAsPerPurchasePrice);
        } else if (TextUtils.isEmpty(qualVerified)) {
            returnFlag = false;
            showSnackBar("Please check the quality of the stocks was verified");
            ScrollToView(binding.rgQualVerified);
        } else if (TextUtils.isEmpty(depotMaintHygeine)) {
            returnFlag = false;
            showSnackBar("Please check whether the depot is maintained in hygienic condition");
            ScrollToView(binding.rgDepotMaintHygeine);
        } else if (TextUtils.isEmpty(repairsReq)) {
            returnFlag = false;
            showSnackBar("Please check any repairs required for Dr Godown");
            ScrollToView(binding.rgRepairsReq);
        } else if (!(TextUtils.isEmpty(repairsReq)) && repairsReq.equals(AppConstants.Yes) && TextUtils.isEmpty(binding.etRepairsType.getText().toString().trim())) {
            returnFlag = false;
            showSnackBar("Please enter repair type");
            ScrollToView(binding.etRepairsType);
        } else if (!TextUtils.isEmpty(repairsReq) && repairsReq.equals(AppConstants.Yes) && !TextUtils.isEmpty(binding.etRepairsType.getText().toString()) && repairsFlag == 0) {
            returnFlag = false;
            showSnackBar("Please capture repair");
            ScrollToView(binding.ivRepairsCam);
        } else if (TextUtils.isEmpty(binding.etFeedback.getText().toString().trim())) {
            returnFlag = false;
            showSnackBar("Please enter feedback of card holders");
            ScrollToViewEditText(binding.etFeedback, "Enter feedback");
        } else if (TextUtils.isEmpty(binding.etRemarks.getText().toString().trim())) {
            returnFlag = false;
            showSnackBar("Please enter remarks");
            ScrollToViewEditText(binding.etRemarks, "Enter remarks");
        }
        return returnFlag;
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
    }

    private void ScrollToView(View view) {
        view.getParent().requestChildFocus(view, view);
    }


    private void ScrollToViewEditText(View view, String reason) {
        CustomFontEditText editText = (CustomFontEditText) view;
        editText.setError(reason);
        editText.requestFocus();
    }

    private void certIssueDateSelection() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        checkUpDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        binding.etWeightCertIssue.setText(checkUpDate);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        FilePath = getExternalFilesDir(null)
                + "/" + IMAGE_DIRECTORY_NAME;

        String Image_name = PIC_NAME;
        FilePath = FilePath + "/" + Image_name;

//        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return FilePath;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                FilePath = getExternalFilesDir(null)
                        + "/" + IMAGE_DIRECTORY_NAME;

                String Image_name = PIC_TYPE + ".png";
                FilePath = FilePath + "/" + Image_name;

                FilePath = compressImage(FilePath);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bm = BitmapFactory.decodeFile(FilePath, options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                file = new File(FilePath);
                Glide.with(DRDepotFindingsActivity.this).load(file).into(binding.ivRepairsCam);
                repairsFlag = 1;

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        File imageFile = getOutputMediaFile(type);
        Uri imageUri = null;
        if (imageFile != null) {
            imageUri = FileProvider.getUriForFile(
                    DRDepotFindingsActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    imageFile);
        }
        return imageUri;
    }

    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("TAG", "Oops! Failed create " + "Android File Upload"
                        + " directory");
                return null;
            }
        }
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            PIC_NAME = PIC_TYPE + "~" + officerID + "~" + divId + "~" + suppId + "~" + Utils.getCurrentDateTimeFormat() + "~" + Utils.getDeviceID(DRDepotFindingsActivity.this)
                    + "~" + Utils.getVersionName(DRDepotFindingsActivity.this) + "~" + randomNum + ".png";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + PIC_TYPE + ".png");
        } else {
            return null;
        }

        return mediaFile;
    }


}
