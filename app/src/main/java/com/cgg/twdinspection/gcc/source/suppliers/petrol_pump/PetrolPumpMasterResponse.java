package com.cgg.twdinspection.gcc.source.suppliers.petrol_pump;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PetrolPumpMasterResponse {

    @SerializedName("supplier_info")
    @Expose
    private List<PetrolSupplierInfo> petrolSupplierInfos = null;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;

    public List<PetrolSupplierInfo> getPetrolSupplierInfos() {
        return petrolSupplierInfos;
    }

    public void setPetrolSupplierInfos(List<PetrolSupplierInfo> petrolSupplierInfos) {
        this.petrolSupplierInfos = petrolSupplierInfos;
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

