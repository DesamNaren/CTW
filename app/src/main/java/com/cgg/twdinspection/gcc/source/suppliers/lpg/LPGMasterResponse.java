package com.cgg.twdinspection.gcc.source.suppliers.lpg;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LPGMasterResponse {

    @SerializedName("supplier_info")
    @Expose
    private List<LPGSupplierInfo> lpgSupplierInfos = null;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;


    public List<LPGSupplierInfo> getLpgSupplierInfos() {
        return lpgSupplierInfos;
    }

    public void setLpgSupplierInfos(List<LPGSupplierInfo> lpgSupplierInfos) {
        this.lpgSupplierInfos = lpgSupplierInfos;
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

