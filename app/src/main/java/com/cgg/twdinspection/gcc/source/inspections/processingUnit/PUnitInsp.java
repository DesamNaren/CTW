package com.cgg.twdinspection.gcc.source.inspections.processingUnit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PUnitInsp {

    @SerializedName("register_book_certificates")
    @Expose
    private PUnitRegisterBookCertificates registerBookCertificates;
    @SerializedName("general_findings")
    @Expose
    private PUnitGeneralFindings generalFindings;

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
