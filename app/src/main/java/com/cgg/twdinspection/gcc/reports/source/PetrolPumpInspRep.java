package com.cgg.twdinspection.gcc.reports.source;

import com.cgg.twdinspection.gcc.source.inspections.petrol_pump.PetrolPumpGeneralFindings;
import com.cgg.twdinspection.gcc.source.inspections.petrol_pump.PetrolPumpRegisterBookCertificates;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PetrolPumpInspRep {

    @SerializedName("general_findings")
    @Expose
    private PetrolPumpGeneralFindings generalFindings;
    @SerializedName("register_book_certificates")
    @Expose
    private PetrolPumpRegisterBookCertificates registerBookCertificates;
    @SerializedName("stock_details")
    @Expose
    private PetrolPumpReportStockDetails stockDetails;

    public PetrolPumpGeneralFindings getGeneralFindings() {
        return generalFindings;
    }

    public void setGeneralFindings(PetrolPumpGeneralFindings generalFindings) {
        this.generalFindings = generalFindings;
    }

    public PetrolPumpRegisterBookCertificates getRegisterBookCertificates() {
        return registerBookCertificates;
    }

    public void setRegisterBookCertificates(PetrolPumpRegisterBookCertificates registerBookCertificates) {
        this.registerBookCertificates = registerBookCertificates;
    }

    public PetrolPumpReportStockDetails getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(PetrolPumpReportStockDetails stockDetails) {
        this.stockDetails = stockDetails;
    }
}

