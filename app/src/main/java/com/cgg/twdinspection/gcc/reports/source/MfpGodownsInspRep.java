package com.cgg.twdinspection.gcc.reports.source;

import com.cgg.twdinspection.gcc.source.inspections.MFPGodowns.MFPGeneralFindings;
import com.cgg.twdinspection.gcc.source.inspections.MFPGodowns.MFPRegisterBookCertificates;
import com.cgg.twdinspection.gcc.source.inspections.MFPGodowns.MFPStockDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MfpGodownsInspRep {

    @SerializedName("register_book_certificates")
    @Expose
    private MFPRegisterBookCertificates registerBookCertificates;
    @SerializedName("general_findings")
    @Expose
    private MFPGeneralFindings generalFindings;
    @SerializedName("stock_details")
    @Expose
    private MFPStockDetails stockDetails;

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
