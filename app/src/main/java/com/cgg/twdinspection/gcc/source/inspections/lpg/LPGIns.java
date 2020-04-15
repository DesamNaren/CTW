package com.cgg.twdinspection.gcc.source.inspections.lpg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LPGIns {
    @SerializedName("stock_details")
    @Expose
    private LPGStockDetails stockDetails;
    @SerializedName("register_book_certificates")
    @Expose
    private LPGRegisterBookCertificates registerBookCertificates;
    @SerializedName("general_findings")
    @Expose
    private LPGGeneralFindings generalFindings;

    public LPGStockDetails getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(LPGStockDetails stockDetails) {
        this.stockDetails = stockDetails;
    }

    public LPGRegisterBookCertificates getRegisterBookCertificates() {
        return registerBookCertificates;
    }

    public void setRegisterBookCertificates(LPGRegisterBookCertificates registerBookCertificates) {
        this.registerBookCertificates = registerBookCertificates;
    }

    public LPGGeneralFindings getGeneralFindings() {
        return generalFindings;
    }

    public void setGeneralFindings(LPGGeneralFindings generalFindings) {
        this.generalFindings = generalFindings;
    }
}
