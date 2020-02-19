package com.example.twdinspection.gcc.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportSubmitReqCommodities {

    @SerializedName("com_type")
    @Expose
    private String comType;
    @SerializedName("com_code")
    @Expose
    private String comCode;
    @SerializedName("units")
    @Expose
    private String units;
    @SerializedName("system_qty")
    @Expose
    private String systemQty;
    @SerializedName("system_rate")
    @Expose
    private String systemRate;
    @SerializedName("system_value")
    @Expose
    private String systemValue;
    @SerializedName("physiacal_qty")
    @Expose
    private String physiacalQty;
    @SerializedName("physical_rate")
    @Expose
    private String physicalRate;
    @SerializedName("physical_value")
    @Expose
    private String physicalValue;

    public String getComType() {
        return comType;
    }

    public void setComType(String comType) {
        this.comType = comType;
    }

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getSystemQty() {
        return systemQty;
    }

    public void setSystemQty(String systemQty) {
        this.systemQty = systemQty;
    }

    public String getSystemRate() {
        return systemRate;
    }

    public void setSystemRate(String systemRate) {
        this.systemRate = systemRate;
    }

    public String getSystemValue() {
        return systemValue;
    }

    public void setSystemValue(String systemValue) {
        this.systemValue = systemValue;
    }

    public String getPhysiacalQty() {
        return physiacalQty;
    }

    public void setPhysiacalQty(String physiacalQty) {
        this.physiacalQty = physiacalQty;
    }

    public String getPhysicalRate() {
        return physicalRate;
    }

    public void setPhysicalRate(String physicalRate) {
        this.physicalRate = physicalRate;
    }

    public String getPhysicalValue() {
        return physicalValue;
    }

    public void setPhysicalValue(String physicalValue) {
        this.physicalValue = physicalValue;
    }
}
