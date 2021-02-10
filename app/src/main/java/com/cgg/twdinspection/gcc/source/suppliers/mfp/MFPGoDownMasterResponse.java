package com.cgg.twdinspection.gcc.source.suppliers.mfp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MFPGoDownMasterResponse {

    @SerializedName("supplier_info")
    @Expose
    private List<MFPGoDowns> mfpGoDowns = null;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;

    public List<MFPGoDowns> getMfpGoDowns() {
        return mfpGoDowns;
    }

    public void setMfpGoDowns(List<MFPGoDowns> mfpGoDowns) {
        this.mfpGoDowns = mfpGoDowns;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}

