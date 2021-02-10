package com.cgg.twdinspection.gcc.ui.punit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.custom.CustomFontEditText;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityGccPunitFindingsBinding;
import com.cgg.twdinspection.gcc.source.inspections.InspectionSubmitResponse;
import com.cgg.twdinspection.gcc.source.inspections.processingUnit.PUStockDetails;
import com.cgg.twdinspection.gcc.source.inspections.processingUnit.PUnitGeneralFindings;
import com.cgg.twdinspection.gcc.source.inspections.processingUnit.PUnitInsp;
import com.cgg.twdinspection.gcc.source.inspections.processingUnit.PUnitRegisterBookCertificates;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.gcc.source.stock.CommonCommodity;
import com.cgg.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.cgg.twdinspection.gcc.source.suppliers.punit.PUnits;
import com.cgg.twdinspection.gcc.ui.gcc.GCCPhotoActivity;
import com.cgg.twdinspection.gcc.viewmodel.GCCOfflineViewModel;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PUnitsFindingsActivity extends LocBaseActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ActivityGccPunitFindingsBinding binding;
    private String officerID, divId, suppId;
    private StockDetailsResponse stockDetailsResponse;
    double physVal = 0, sysVal = 0, notInsSysVal = 0, insSysVal = 0, difference = 0;
    private String remarks, insComName, insComDate, insCer, weightDate, weightMea, difReason;
    private String fireNOC, qualityStock, godownHyg, repairsReq;
    private String rawStock, proReg, inwardReg, outwardReg, saleReg, labAttReg, amcMac, agmarkCer, fsaaiCer;
    private String empReg, barrelCans, cashBook, cashBankBal, vehLogBook;
    private String stockRemarks, proRemarks, inwardRemarks, outwardRemarks, saleRemarks, labRemarks, fireRemarks, amcRemarks, agmarkRemarks,
            fsaaiRemarks, emptyRemarks, barralesRemarks, cahBookRemarks, cashBankRemarks, vehlogRemarks;
    private String randomNum;
    private List<CommonCommodity> finalPUnitCCom, finalEssCom, finalDaiCom, finalEmpCom, finalMFPCom;
    private StockDetailsResponse finalStockDetailsResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gcc_punit_findings);
        binding.header.headerTitle.setText(getString(R.string.p_unit_ins_off_fin));
        binding.header.ivHome.setVisibility(View.GONE);
        sharedPreferences = TWDApplication.get(this).getPreferences();
        String stockData = sharedPreferences.getString(AppConstants.stockData, "");
        officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        binding.bottomLl.btnNext.setText(getString(R.string.saveandnext));
        randomNum = Utils.getRandomNumberString();

        finalEssCom = new ArrayList<>();
        finalDaiCom = new ArrayList<>();
        finalMFPCom = new ArrayList<>();
        finalEmpCom = new ArrayList<>();
        finalPUnitCCom = new ArrayList<>();

        Gson gson = new Gson();
        stockDetailsResponse = gson.fromJson(stockData, StockDetailsResponse.class);
        String pUnitData = sharedPreferences.getString(AppConstants.P_UNIT_DATA, "");
        PUnits pUnits = gson.fromJson(pUnitData, PUnits.class);
        divId = pUnits.getDivisionId();
        suppId = pUnits.getGodownId();

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        GCCOfflineViewModel gccOfflineViewModel = new GCCOfflineViewModel(getApplication());
        LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(
                pUnits.getDivisionId(), pUnits.getSocietyId(), pUnits.getGodownId());

        drGodownLiveData.observe(PUnitsFindingsActivity.this, new androidx.lifecycle.Observer<GccOfflineEntity>() {
            @Override
            public void onChanged(GccOfflineEntity gccOfflineEntity) {
                if (gccOfflineEntity != null) {
                    binding.header.ivMode.setBackground(getResources().getDrawable(R.drawable.offline_mode));
                } else {
                    binding.header.ivMode.setBackground(getResources().getDrawable(R.drawable.online_mode));
                }
            }
        });

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
            if (stockDetailsResponse.getMfp_commodities() != null && stockDetailsResponse.getMfp_commodities().size() > 0) {
                for (int i = 0; i < stockDetailsResponse.getMfp_commodities().size(); i++) {
                    if (!TextUtils.isEmpty(stockDetailsResponse.getMfp_commodities().get(i).getPhyQuant())) {
                        physVal += Double.parseDouble(stockDetailsResponse.getMfp_commodities().get(i).getPhyQuant()) * stockDetailsResponse.getMfp_commodities().get(i).getRate();
                        insSysVal += stockDetailsResponse.getMfp_commodities().get(i).getQty() * stockDetailsResponse.getMfp_commodities().get(i).getRate();
                        finalMFPCom.add(stockDetailsResponse.getMfp_commodities().get(i));
                    }
                    sysVal += stockDetailsResponse.getMfp_commodities().get(i).getQty() * stockDetailsResponse.getMfp_commodities().get(i).getRate();
                }
            }
            if (stockDetailsResponse.getProcessing_units() != null && stockDetailsResponse.getProcessing_units().size() > 0) {
                for (int i = 0; i < stockDetailsResponse.getProcessing_units().size(); i++) {
                    if (!TextUtils.isEmpty(stockDetailsResponse.getProcessing_units().get(i).getPhyQuant())) {
                        physVal += Double.parseDouble(stockDetailsResponse.getProcessing_units().get(i).getPhyQuant()) * stockDetailsResponse.getProcessing_units().get(i).getRate();
                        insSysVal += stockDetailsResponse.getProcessing_units().get(i).getQty() * stockDetailsResponse.getProcessing_units().get(i).getRate();
                        finalPUnitCCom.add(stockDetailsResponse.getProcessing_units().get(i));
                    }
                    sysVal += stockDetailsResponse.getProcessing_units().get(i).getQty() * stockDetailsResponse.getProcessing_units().get(i).getRate();
                }
            }

            sysVal = Double.valueOf(String.format("%.2f", sysVal));
            physVal = Double.valueOf(String.format("%.2f", physVal));
            binding.tvSysVal.setText(String.format("%.2f", sysVal));
            binding.tvPhysVal.setText(String.format("%.2f", physVal));

            notInsSysVal = sysVal - insSysVal;
            notInsSysVal = Double.valueOf(String.format("%.2f", notInsSysVal));
            binding.tvInsSysVal.setText(String.format("%.2f", insSysVal));
            binding.tvSysValNotIns.setText(String.format("%.2f", notInsSysVal));
            difference = insSysVal - physVal;
            difference = Double.valueOf(String.format("%.2f", difference));
            binding.tvDiffVal.setText(String.format("%.2f", difference));

        }

        binding.bottomLl.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                difReason = binding.etReason.getText().toString().trim();
                remarks = binding.remarks.etRemarks.getText().toString().trim();
                insComName = binding.etComName.getText().toString().trim();
                insComDate = binding.etInsDate.getText().toString().trim();
                weightDate = binding.etLegalMetDate.getText().toString().trim();


                stockRemarks = binding.remarksStock.etRemarks.getText().toString().trim();
                proRemarks = binding.remarksProcessing.etRemarks.getText().toString().trim();
                inwardRemarks = binding.remarksInward.etRemarks.getText().toString().trim();
                outwardRemarks = binding.remarksOutward.etRemarks.getText().toString().trim();
                saleRemarks = binding.remarksSaleInv.etRemarks.getText().toString().trim();
                labRemarks = binding.remarksLabAtt.etRemarks.getText().toString().trim();
                fireRemarks = binding.remarksFireNoc.etRemarks.getText().toString().trim();
                amcRemarks = binding.remarksAmc.etRemarks.getText().toString().trim();
                agmarkRemarks = binding.remarksAgmark.etRemarks.getText().toString().trim();
                fsaaiRemarks = binding.remarksFsaai.etRemarks.getText().toString().trim();
                emptyRemarks = binding.remarksEmpties.etRemarks.getText().toString().trim();
                barralesRemarks = binding.remarksBarrels.etRemarks.getText().toString().trim();
                cahBookRemarks = binding.remarksCashBook.etRemarks.getText().toString().trim();
                cashBankRemarks = binding.remarksCashBank.etRemarks.getText().toString().trim();
                vehlogRemarks = binding.remarksVehLog.etRemarks.getText().toString().trim();

                if (validate()) {
                    PUnitInsp pUnitInsp = new PUnitInsp();


                    PUStockDetails stockDetails = new PUStockDetails();
                    stockDetails.setStockValueAsPerSystem(sysVal);
                    stockDetails.setStockValueAsPerPhysical(physVal);
                    stockDetails.setDeficit(difference);
                    stockDetails.setDeficitReason(difReason);
                    stockDetails.setStockValueAsPerSystemInsp(String.valueOf(insSysVal));
                    stockDetails.setStockValueAsPerSystemNotInsp(String.valueOf(notInsSysVal));

                    pUnitInsp.setStockDetails(stockDetails);


                    PUnitRegisterBookCertificates registerBookCertificates = new PUnitRegisterBookCertificates();
                    registerBookCertificates.setRawMatStockRegisterType(rawStock);
                    registerBookCertificates.setProcessingRegisterType(proReg);
                    registerBookCertificates.setInwardRegisterType(inwardReg);
                    registerBookCertificates.setOutwardRegisterType(outwardReg);
                    registerBookCertificates.setSaleInvoiceBookType(saleReg);
                    registerBookCertificates.setLabourAttendRegisterType(labAttReg);
                    registerBookCertificates.setInsuranceCertType(insCer);
                    registerBookCertificates.setInsCompName(insComName);
                    registerBookCertificates.setInsValidity(insComDate);
                    registerBookCertificates.setFireDeptType(fireNOC);
                    registerBookCertificates.setWeightMeasureCertificateType(weightMea);
                    registerBookCertificates.setWeightMeasureValidityType(weightDate);
                    registerBookCertificates.setAmcMachinaryType(amcMac);
                    registerBookCertificates.setAgmarkCertType(agmarkCer);
                    registerBookCertificates.setFsaaiCertType(fsaaiCer);
                    registerBookCertificates.setEmptiesRegisterType(empReg);
                    registerBookCertificates.setBarrelsAlumnCansType(barrelCans);
                    registerBookCertificates.setCashBookType(cashBook);
                    registerBookCertificates.setCashBankBalType(cashBankBal);
                    registerBookCertificates.setVehLogBookType(vehLogBook);

                    registerBookCertificates.setRawMatStockRegisterRemarks(stockRemarks);
                    registerBookCertificates.setProcessingRegisterRemarks(proRemarks);
                    registerBookCertificates.setInwardRegisterRemarks(inwardRemarks);
                    registerBookCertificates.setOutwardRegisterRemarks(outwardRemarks);
                    registerBookCertificates.setSaleInvoiceBookRemarks(saleRemarks);
                    registerBookCertificates.setLabourAttendRegisterRemarks(labRemarks);
                    registerBookCertificates.setFireDeptRemarks(fireRemarks);
                    registerBookCertificates.setAmcMachinaryRemarks(amcRemarks);
                    registerBookCertificates.setAgmarkCertRemarks(agmarkRemarks);
                    registerBookCertificates.setFsaaiCertRemarks(fsaaiRemarks);
                    registerBookCertificates.setEmptiesRegisterRemarks(emptyRemarks);
                    registerBookCertificates.setBarrelsAlumnCansRemarks(barralesRemarks);
                    registerBookCertificates.setCashBookRemarks(cahBookRemarks);
                    registerBookCertificates.setCashBankBalRemarks(cashBankRemarks);
                    registerBookCertificates.setVehLogBookRemarks(vehlogRemarks);


                    pUnitInsp.setRegisterBookCertificates(registerBookCertificates);

                    PUnitGeneralFindings generalFindings = new PUnitGeneralFindings();
                    generalFindings.setStockQualityVerified(qualityStock);
                    generalFindings.setHygienicCondition(godownHyg);
                    generalFindings.setRepairReq(repairsReq);
                    generalFindings.setRemarks(remarks);
                    pUnitInsp.setGeneralFindings(generalFindings);

                    InspectionSubmitResponse inspectionSubmitResponse = new InspectionSubmitResponse();
                    inspectionSubmitResponse.setProcessingUnit(pUnitInsp);
                    try {
                        editor = sharedPreferences.edit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String inspectionDetails = gson.toJson(inspectionSubmitResponse);
                    sysVal = Double.valueOf(String.format("%.2f", sysVal));
                    physVal = Double.valueOf(String.format("%.2f", physVal));
                    editor.putString(AppConstants.TOTAL_PHYVAL, String.valueOf(physVal));
                    editor.putString(AppConstants.TOTAL_SYSVAL, String.valueOf(sysVal));
                    editor.putString(AppConstants.InspectionDetails, inspectionDetails);
                    editor.putString(AppConstants.randomNum, randomNum);

                    finalStockDetailsResponse = new StockDetailsResponse();
                    finalStockDetailsResponse.setEssential_commodities(finalEssCom);
                    finalStockDetailsResponse.setDialy_requirements(finalDaiCom);
                    finalStockDetailsResponse.setEmpties(finalEmpCom);
                    finalStockDetailsResponse.setMfp_commodities(finalMFPCom);
                    finalStockDetailsResponse.setProcessing_units(finalPUnitCCom);

                    String stockData = gson.toJson(finalStockDetailsResponse);
                    editor.putString(AppConstants.finalStockData, stockData);

                    editor.commit();

                    startActivity(new Intent(PUnitsFindingsActivity.this, GCCPhotoActivity.class)
                            .putExtra(AppConstants.TITLE, AppConstants.OFFLINE_P_UNIT));
                }
            }
        });


        binding.rgStock.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.stock_yes_rb) {
                    rawStock = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.stock_no_rb) {
                    rawStock = AppConstants.No;
                }
            }
        });

        binding.rgProcessing.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.pro_yes_rb) {
                    proReg = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.pro_no_rb) {
                    proReg = AppConstants.No;
                }
            }
        });
        binding.rgInward.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.inward_yes_rb) {
                    inwardReg = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.inward_no_rb) {
                    inwardReg = AppConstants.No;
                }
            }
        });

        binding.rgOutward.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.outward_yes_rb) {
                    outwardReg = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.outward_no_rb) {
                    outwardReg = AppConstants.No;
                }
            }
        });

        binding.rgSaleInv.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.sale_inv_yes_rb) {
                    saleReg = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.sale_inv_no_rb) {
                    saleReg = AppConstants.No;
                }
            }
        });


        binding.rgLabAtt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.lab_att_yes_rb) {
                    labAttReg = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.lab_att_no_rb) {
                    labAttReg = AppConstants.No;
                }
            }
        });


        binding.rgInsurance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.insurance_yes_rb) {
                    insCer = AppConstants.Yes;
                    binding.insLl.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.insurance_no_rb) {
                    insCer = AppConstants.No;
                    binding.insLl.setVisibility(View.GONE);
                }
            }
        });


        binding.rgFireNoc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.fire_noc_rb_yes) {
                    fireNOC = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.fire_noc_rb_no) {
                    fireNOC = AppConstants.No;
                }
            }
        });


        binding.rgWeight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.weight_rb_yes) {
                    weightMea = AppConstants.Yes;
                    binding.legalLl.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.weight_rb_no) {
                    weightMea = AppConstants.No;
                    binding.legalLl.setVisibility(View.GONE);
                }
            }
        });


        binding.rgAmc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.amc_yes_rb) {
                    amcMac = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.amc_no_rb) {
                    amcMac = AppConstants.No;
                }
            }
        });


        binding.rgAgmark.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.agmark_yes_rb) {
                    agmarkCer = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.agmark_no_rb) {
                    agmarkCer = AppConstants.No;
                }
            }
        });


        binding.rgFsaai.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.fsaai_yes_rb) {
                    fsaaiCer = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.fsaai_no_rb) {
                    fsaaiCer = AppConstants.No;
                }
            }
        });

        binding.rgEmpties.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.empties_yes_rb) {
                    empReg = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.empties_no_rb) {
                    empReg = AppConstants.No;
                }
            }
        });


        binding.rgBarrels.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.barrels_yes_rb) {
                    barrelCans = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.barrels_no_rb) {
                    barrelCans = AppConstants.No;
                }
            }
        });


        binding.rgCashBook.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.cash_book_yes_rb) {
                    cashBook = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.cash_book_no_rb) {
                    cashBook = AppConstants.No;
                }
            }
        });


        binding.rgCashBank.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.cash_bank_yes_rb) {
                    cashBankBal = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.cash_bank_no_rb) {
                    cashBankBal = AppConstants.No;
                }
            }
        });


        binding.rgVehLog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.veh_log_yes_rb) {
                    vehLogBook = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.veh_log_no_rb) {
                    vehLogBook = AppConstants.No;
                }
            }
        });


        binding.rgStockQua.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_stock_yes) {
                    qualityStock = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_stock_no) {
                    qualityStock = AppConstants.No;
                }
            }
        });


        binding.rgGodownHyg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_hyg_yes) {
                    godownHyg = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_hyg_no) {
                    godownHyg = AppConstants.No;
                }
            }
        });


        binding.rgRepairsReq.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.repairs_req_rb_yes) {
                    repairsReq = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.repairs_req_rb_no) {
                    repairsReq = AppConstants.No;
                }
            }
        });


        binding.etInsDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                certIssueDateSelection();
            }
        });

        binding.etLegalMetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                legalMetDateSelection();
            }
        });

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
                        String checkUpDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        binding.etInsDate.setText(checkUpDate);
                        insComDate = checkUpDate;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    private void legalMetDateSelection() {
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
                        String checkUpDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        binding.etLegalMetDate.setText(checkUpDate);
                        weightDate = checkUpDate;

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
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

    private boolean validate() {
        boolean returnFlag = true;
        if (TextUtils.isEmpty(rawStock)) {
            returnFlag = false;
            ScrollToView(binding.rgStock);
            showSnackBar("Please check raw material stock register");
        } else if (TextUtils.isEmpty(proReg)) {
            returnFlag = false;
            ScrollToView(binding.rgProcessing);
            showSnackBar("Please check processing register");
        } else if (TextUtils.isEmpty(inwardReg)) {
            returnFlag = false;
            ScrollToView(binding.rgInward);
            showSnackBar("Please check inward register");
        } else if (TextUtils.isEmpty(outwardReg)) {
            returnFlag = false;
            ScrollToView(binding.rgOutward);
            showSnackBar("Please check outward register");
        } else if (TextUtils.isEmpty(saleReg)) {
            returnFlag = false;
            ScrollToView(binding.rgSaleInv);
            showSnackBar("Please check sale/invoice register");
        } else if (TextUtils.isEmpty(labAttReg)) {
            returnFlag = false;
            ScrollToView(binding.rgLabAtt);
            showSnackBar("Please check labour attendance register");
        } else if (TextUtils.isEmpty(insCer)) {
            returnFlag = false;
            ScrollToView(binding.rgInsurance);
            showSnackBar("Please check insurance certificate");
        } else if (insCer.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(insComName)) {
            returnFlag = false;
            ScrollToViewEditText(binding.etComName, "Enter insurance company name");
        } else if (insCer.equalsIgnoreCase(AppConstants.Yes) && !insComDate.contains("/")) {
            returnFlag = false;
            ScrollToViewEditText(binding.etInsDate, "Enter insurance validity");
        } else if (TextUtils.isEmpty(fireNOC)) {
            returnFlag = false;
            ScrollToView(binding.rgFireNoc);
            showSnackBar("Please check fire department NOC");
        } else if (TextUtils.isEmpty(weightMea)) {
            returnFlag = false;
            ScrollToView(binding.rgWeight);
            showSnackBar("Please check weight measure by legal metrology");
        } else if (weightMea.equalsIgnoreCase(AppConstants.Yes) && !weightDate.contains("/")) {
            returnFlag = false;
            ScrollToViewEditText(binding.etLegalMetDate, "Enter weight measure validity date");
        } else if (TextUtils.isEmpty(amcMac)) {
            returnFlag = false;
            ScrollToView(binding.rgAmc);
            showSnackBar("Please check AMC machinery");
        } else if (TextUtils.isEmpty(agmarkCer)) {
            returnFlag = false;
            ScrollToView(binding.rgAgmark);
            showSnackBar("Please check agmark certificate");
        } else if (TextUtils.isEmpty(fsaaiCer)) {
            returnFlag = false;
            ScrollToView(binding.rgFsaai);
            showSnackBar("Please check tray FSAAI certificate");
        } else if (TextUtils.isEmpty(empReg)) {
            returnFlag = false;
            ScrollToView(binding.rgEmpties);
            showSnackBar("Please check empties register");
        } else if (TextUtils.isEmpty(barrelCans)) {
            returnFlag = false;
            ScrollToView(binding.rgBarrels);
            showSnackBar("Please check Barrels / Aluminium cans");
        } else if (TextUtils.isEmpty(cashBook)) {
            returnFlag = false;
            ScrollToView(binding.rgCashBook);
            showSnackBar("Please check cash book");
        } else if (TextUtils.isEmpty(cashBankBal)) {
            returnFlag = false;
            ScrollToView(binding.rgCashBank);
            showSnackBar("Please check cash and bank balance");
        } else if (TextUtils.isEmpty(vehLogBook)) {
            returnFlag = false;
            ScrollToView(binding.rgVehLog);
            showSnackBar("Please check vehicle log book");
        } else if (TextUtils.isEmpty(qualityStock)) {
            returnFlag = false;
            ScrollToView(binding.rgStockQua);
            showSnackBar("Please check quality of stock");
        } else if (TextUtils.isEmpty(godownHyg)) {
            returnFlag = false;
            ScrollToView(binding.rgGodownHyg);
            showSnackBar("Please check godown is hygienic");
        } else if (TextUtils.isEmpty(repairsReq)) {
            returnFlag = false;
            ScrollToView(binding.rgRepairsReq);
            showSnackBar("Please check repairs required");
        } else if (TextUtils.isEmpty(remarks)) {
            returnFlag = false;
            showSnackBar("Please enter remarks");
            ScrollToViewEditText(binding.remarks.etRemarks, "Enter remarks");
        }
        return returnFlag;
    }


}
