package com.cgg.twdinspection.gcc.source.inspections.godown;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrGodownInsp {

    @SerializedName("stock_details")
    @Expose
    private DrGodownStockDetails stockDetails;
    @SerializedName("register_book_certificates")
    @Expose
    private DrGodownRegisterBookCertificates registerBookCertificates;
    @SerializedName("general_findings")
    @Expose
    private DrGodownGeneralFindings generalFindings;

    public DrGodownStockDetails getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(DrGodownStockDetails stockDetails) {
        this.stockDetails = stockDetails;
    }

    public DrGodownRegisterBookCertificates getRegisterBookCertificates() {
        return registerBookCertificates;
    }

    public void setRegisterBookCertificates(DrGodownRegisterBookCertificates registerBookCertificates) {
        this.registerBookCertificates = registerBookCertificates;
    }

    public DrGodownGeneralFindings getGeneralFindings() {
        return generalFindings;
    }

    public void setGeneralFindings(DrGodownGeneralFindings generalFindings) {
        this.generalFindings = generalFindings;
    }

}
