package com.example.twdinspection.schemes.source.dmv;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SchemeDMVResponse {

    @SerializedName("status_Code")
    @Expose
    private String statusCode;
    @SerializedName("status_Message")
    @Expose
    private String statusMessage;
    @SerializedName("mandals")
    @Expose
    private List<SchemeMandal> mandals = null;
    @SerializedName("districts")
    @Expose
    private List<SchemeDistrict> districts = null;
    @SerializedName("villages")
    @Expose
    private List<SchemeVillage> villages = null;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<SchemeMandal> getMandals() {
        return mandals;
    }

    public void setMandals(List<SchemeMandal> mandals) {
        this.mandals = mandals;
    }

    public List<SchemeDistrict> getDistricts() {
        return districts;
    }

    public void setDistricts(List<SchemeDistrict> districts) {
        this.districts = districts;
    }

    public List<SchemeVillage> getVillages() {
        return villages;
    }

    public void setVillages(List<SchemeVillage> villages) {
        this.villages = villages;
    }

}

