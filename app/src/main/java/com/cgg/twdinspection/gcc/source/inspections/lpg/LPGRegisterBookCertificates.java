package com.cgg.twdinspection.gcc.source.inspections.lpg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LPGRegisterBookCertificates {

    @SerializedName("stock_register")
    @Expose
    private String stockRegister;
    @SerializedName("insurance_certificate")
    @Expose
    private String insuranceCertificate;
    @SerializedName("cash_book")
    @Expose
    private String cashBook;
    @SerializedName("daily_sales_register")
    @Expose
    private String dailySalesRegister;
    @SerializedName("insurance_company")
    @Expose
    private String insuranceCompany;
    @SerializedName("weight_measure_certificate")
    @Expose
    private String weightMeasureCertificate;
    @SerializedName("godown_liability_register")
    @Expose
    private String godownLiabilityRegister;
    @SerializedName("insurance_validity")
    @Expose
    private String insuranceValidity;
    @SerializedName("fire_dept_noc")
    @Expose
    private String fireDeptNoc;
    @SerializedName("weight_measure_validity")
    @Expose
    private String weightMeasureValidity;
    @SerializedName("daily_remittance")
    @Expose
    private String dailyRemittance;
    @SerializedName("purchase_register")
    @Expose
    private String purchaseRegister;
    @SerializedName("remittance")
    @Expose
    private String remittance;

    public String getStockRegister() {
        return stockRegister;
    }

    public void setStockRegister(String stockRegister) {
        this.stockRegister = stockRegister;
    }

    public String getInsuranceCertificate() {
        return insuranceCertificate;
    }

    public void setInsuranceCertificate(String insuranceCertificate) {
        this.insuranceCertificate = insuranceCertificate;
    }

    public String getCashBook() {
        return cashBook;
    }

    public void setCashBook(String cashBook) {
        this.cashBook = cashBook;
    }

    public String getDailySalesRegister() {
        return dailySalesRegister;
    }

    public void setDailySalesRegister(String dailySalesRegister) {
        this.dailySalesRegister = dailySalesRegister;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public String getWeightMeasureCertificate() {
        return weightMeasureCertificate;
    }

    public void setWeightMeasureCertificate(String weightMeasureCertificate) {
        this.weightMeasureCertificate = weightMeasureCertificate;
    }

    public String getGodownLiabilityRegister() {
        return godownLiabilityRegister;
    }

    public void setGodownLiabilityRegister(String godownLiabilityRegister) {
        this.godownLiabilityRegister = godownLiabilityRegister;
    }

    public String getInsuranceValidity() {
        return insuranceValidity;
    }

    public void setInsuranceValidity(String insuranceValidity) {
        this.insuranceValidity = insuranceValidity;
    }

    public String getFireDeptNoc() {
        return fireDeptNoc;
    }

    public void setFireDeptNoc(String fireDeptNoc) {
        this.fireDeptNoc = fireDeptNoc;
    }

    public String getWeightMeasureValidity() {
        return weightMeasureValidity;
    }

    public void setWeightMeasureValidity(String weightMeasureValidity) {
        this.weightMeasureValidity = weightMeasureValidity;
    }

    public String getDailyRemittance() {
        return dailyRemittance;
    }

    public void setDailyRemittance(String dailyRemittance) {
        this.dailyRemittance = dailyRemittance;
    }

    public String getPurchaseRegister() {
        return purchaseRegister;
    }

    public void setPurchaseRegister(String purchaseRegister) {
        this.purchaseRegister = purchaseRegister;
    }

    public String getRemittance() {
        return remittance;
    }

    public void setRemittance(String remittance) {
        this.remittance = remittance;
    }
}
