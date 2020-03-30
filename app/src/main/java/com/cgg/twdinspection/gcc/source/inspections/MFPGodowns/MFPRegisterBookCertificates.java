package com.cgg.twdinspection.gcc.source.inspections.MFPGodowns;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MFPRegisterBookCertificates {

    @SerializedName("mfp_stock_register")
    @Expose
    private String mfpStockRegister;
    @SerializedName("insurance_certificate")
    @Expose
    private String insuranceCertificate;
    @SerializedName("insurance_company")
    @Expose
    private String insuranceCompany;
    @SerializedName("insurance_validity")
    @Expose
    private String insuranceValidity;
    @SerializedName("fire_noc")
    @Expose
    private String fireNoc;
    @SerializedName("weight_measure_certificate")
    @Expose
    private String weightMeasureCertificate;
    @SerializedName("weight_measure_validity")
    @Expose
    private String weightMeasureValidity;

    public String getMfpStockRegister() {
        return mfpStockRegister;
    }

    public void setMfpStockRegister(String mfpStockRegister) {
        this.mfpStockRegister = mfpStockRegister;
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

    public String getFireNoc() {
        return fireNoc;
    }

    public void setFireNoc(String fireNoc) {
        this.fireNoc = fireNoc;
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
