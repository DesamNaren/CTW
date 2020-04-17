package com.cgg.twdinspection.gcc.reports.source;

import com.cgg.twdinspection.gcc.source.inspections.lpg.LPGGeneralFindings;
import com.cgg.twdinspection.gcc.source.inspections.lpg.LPGRegisterBookCertificates;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LpgInspRep {

    @SerializedName("general_findings")
    @Expose
    private LPGGeneralFindings generalFindings;
    @SerializedName("register_book_certificates")
    @Expose
    private LPGRegisterBookCertificates registerBookCertificates;
    @SerializedName("stock_details")
    @Expose
    private LpgReportStockDetails stockDetails;

    public LPGGeneralFindings getGeneralFindings() {
        return generalFindings;
    }

    public void setGeneralFindings(LPGGeneralFindings generalFindings) {
        this.generalFindings = generalFindings;
    }

    public LPGRegisterBookCertificates getRegisterBookCertificates() {
        return registerBookCertificates;
    }

    public void setRegisterBookCertificates(LPGRegisterBookCertificates registerBookCertificates) {
        this.registerBookCertificates = registerBookCertificates;
    }

    public LpgReportStockDetails getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(LpgReportStockDetails stockDetails) {
        this.stockDetails = stockDetails;
    }
}

