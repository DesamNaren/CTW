package com.cgg.twdinspection.gcc.source.inspections.godown;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrGodownRegisterBookCertificates {

    @SerializedName("stock_register")
    @Expose
    private String stockRegister;
    @SerializedName("purchase_register")
    @Expose
    private String purchaseRegister;
    @SerializedName("sale_price_fixation_register")
    @Expose
    private String salePriceFixationRegister;
    @SerializedName("godown_liability_register")
    @Expose
    private String godownLiabilityRegister;
    @SerializedName("insurance_certificate")
    @Expose
    private String insuranceCertificate;
    @SerializedName("insurance_company")
    @Expose
    private String insuranceCompany;
    @SerializedName("insurance_validity")
    @Expose
    private String insuranceValidity;
    @SerializedName("fire_dept_noc")
    @Expose
    private String fireDeptNoc;
    @SerializedName("weight_measure_certificate")
    @Expose
    private String weightMeasureCertificate;
    @SerializedName("weight_measure_validity")
    @Expose
    private String weightMeasureValidity;

    public String getStockRegister() {
        return stockRegister;
    }

    public void setStockRegister(String stockRegister) {
        this.stockRegister = stockRegister;
    }

    public String getPurchaseRegister() {
        return purchaseRegister;
    }

    public void setPurchaseRegister(String purchaseRegister) {
        this.purchaseRegister = purchaseRegister;
    }

    public String getSalePriceFixationRegister() {
        return salePriceFixationRegister;
    }

    public void setSalePriceFixationRegister(String salePriceFixationRegister) {
        this.salePriceFixationRegister = salePriceFixationRegister;
    }

    public String getGodownLiabilityRegister() {
        return godownLiabilityRegister;
    }

    public void setGodownLiabilityRegister(String godownLiabilityRegister) {
        this.godownLiabilityRegister = godownLiabilityRegister;
    }

    public String getInsuranceCertificate() {
        return insuranceCertificate;
    }

    public void setInsuranceCertificate(String insuranceCertificate) {
        this.insuranceCertificate = insuranceCertificate;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
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

    public String getWeightMeasureCertificate() {
        return weightMeasureCertificate;
    }

    public void setWeightMeasureCertificate(String weightMeasureCertificate) {
        this.weightMeasureCertificate = weightMeasureCertificate;
    }

    public String getWeightMeasureValidity() {
        return weightMeasureValidity;
    }

    public void setWeightMeasureValidity(String weightMeasureValidity) {
        this.weightMeasureValidity = weightMeasureValidity;
    }

}
