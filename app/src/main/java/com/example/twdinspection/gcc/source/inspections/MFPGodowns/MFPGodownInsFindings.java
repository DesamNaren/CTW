package com.example.twdinspection.gcc.source.inspections.MFPGodowns;

import com.example.twdinspection.gcc.source.inspections.DrDepot.GeneralFindings;
import com.example.twdinspection.gcc.source.inspections.DrDepot.RegisterBookCertificates;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MFPGodownInsFindings {

    @SerializedName("mfp_godowns")
    @Expose
    private MfpGodownsInsp mfpGodowns;

    public MfpGodownsInsp getMfpGodowns() {
        return mfpGodowns;
    }

    public void setMfpGodowns(MfpGodownsInsp mfpGodowns) {
        this.mfpGodowns = mfpGodowns;
    }

}


