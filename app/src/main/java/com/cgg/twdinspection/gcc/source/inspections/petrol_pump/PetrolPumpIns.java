package com.cgg.twdinspection.gcc.source.inspections.petrol_pump;

import com.cgg.twdinspection.gcc.source.inspections.godown.DrGodownGeneralFindings;
import com.cgg.twdinspection.gcc.source.inspections.godown.DrGodownRegisterBookCertificates;
import com.cgg.twdinspection.gcc.source.inspections.godown.DrGodownStockDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PetrolPumpIns {
    @SerializedName("stock_details")
    @Expose
    private PetrolPumpStockDetails stockDetails;
    @SerializedName("register_book_certificates")
    @Expose
    private PetrolPumpRegisterBookCertificates registerBookCertificates;
    @SerializedName("general_findings")
    @Expose
    private PetrolPumpGeneralFindings generalFindings;

    public PetrolPumpStockDetails getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(PetrolPumpStockDetails stockDetails) {
        this.stockDetails = stockDetails;
    }

    public PetrolPumpRegisterBookCertificates getRegisterBookCertificates() {
        return registerBookCertificates;
    }

    public void setRegisterBookCertificates(PetrolPumpRegisterBookCertificates registerBookCertificates) {
        this.registerBookCertificates = registerBookCertificates;
    }

    public PetrolPumpGeneralFindings getGeneralFindings() {
        return generalFindings;
    }

    public void setGeneralFindings(PetrolPumpGeneralFindings generalFindings) {
        this.generalFindings = generalFindings;
    }
}
