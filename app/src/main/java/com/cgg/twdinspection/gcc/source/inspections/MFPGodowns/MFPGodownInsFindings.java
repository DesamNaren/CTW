package com.cgg.twdinspection.gcc.source.inspections.MFPGodowns;

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


