package com.cgg.twdinspection.inspection.source.dmv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SchoolDMVResponse {

    @SerializedName("status_Code")
    @Expose
    private String statusCode;
    @SerializedName("status_Message")
    @Expose
    private String statusMessage;
    @SerializedName("Mandals")
    @Expose
    private List<SchoolMandal> mandals = null;
    @SerializedName("Districts")
    @Expose
    private List<SchoolDistrict> districts = null;
    @SerializedName("Villages")
    @Expose
    private List<SchoolVillage> villages = null;

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

    public List<SchoolMandal> getMandals() {
        return mandals;
    }

    public void setMandals(List<SchoolMandal> mandals) {
        this.mandals = mandals;
    }

    public List<SchoolDistrict> getDistricts() {
        return districts;
    }

    public void setDistricts(List<SchoolDistrict> districts) {
        this.districts = districts;
    }

    public List<SchoolVillage> getVillages() {
        return villages;
    }

    public void setVillages(List<SchoolVillage> villages) {
        this.villages = villages;
    }

}
