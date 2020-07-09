package com.cgg.twdinspection.gcc.source.inspections.processingUnit;

import com.cgg.twdinspection.gcc.source.inspections.MFPGodowns.MFPStockDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PUnitInsp {
    @SerializedName("stock_details")
    @Expose
    private PUStockDetails stockDetails;
    @SerializedName("register_book_certificates")
    @Expose
    private PUnitRegisterBookCertificates registerBookCertificates;
    @SerializedName("general_findings")
    @Expose
    private PUnitGeneralFindings generalFindings;


    public PUStockDetails getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(PUStockDetails stockDetails) {
        this.stockDetails = stockDetails;
    }

    public PUnitRegisterBookCertificates getRegisterBookCertificates() {
        return registerBookCertificates;
    }

    public void setRegisterBookCertificates(PUnitRegisterBookCertificates registerBookCertificates) {
        this.registerBookCertificates = registerBookCertificates;
    }

    public PUnitGeneralFindings getGeneralFindings() {
        return generalFindings;
    }

    public void setGeneralFindings(PUnitGeneralFindings generalFindings) {
        this.generalFindings = generalFindings;
    }

}
