package com.cgg.twdinspection.gcc.source.inspections.MFPGodowns;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MfpGodownsInsp {
    @SerializedName("stock_details")
    @Expose
    private MFPStockDetails stockDetails;
    @SerializedName("register_book_certificates")
    @Expose
    private MFPRegisterBookCertificates registerBookCertificates;
    @SerializedName("general_findings")
    @Expose
    private MFPGeneralFindings generalFindings;


    public MFPStockDetails getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(MFPStockDetails stockDetails) {
        this.stockDetails = stockDetails;
    }

    public MFPRegisterBookCertificates getRegisterBookCertificates() {
        return registerBookCertificates;
    }

    public void setRegisterBookCertificates(MFPRegisterBookCertificates registerBookCertificates) {
        this.registerBookCertificates = registerBookCertificates;
    }

    public MFPGeneralFindings getGeneralFindings() {
        return generalFindings;
    }

    public void setGeneralFindings(MFPGeneralFindings generalFindings) {
        this.generalFindings = generalFindings;
    }

}
