package com.cgg.twdinspection.gcc.source.stock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitReqCommodities {

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
    private Double systemQty;
    @SerializedName("system_rate")
    @Expose
    private Double systemRate;
    @SerializedName("system_value")
    @Expose
    private Double systemValue;
    @SerializedName("physiacal_qty")
    @Expose
    private Double physiacalQty;
    @SerializedName("physical_rate")
    @Expose
    private Double physicalRate;
    @SerializedName("physical_value")
    @Expose
    private Double physicalValue;

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

    public Double getSystemQty() {
        return systemQty;
    }

    public void setSystemQty(Double systemQty) {
        this.systemQty = systemQty;
    }

    public Double getSystemRate() {
        return systemRate;
    }

    public void setSystemRate(Double systemRate) {
        this.systemRate = systemRate;
    }

    public Double getSystemValue() {
        return systemValue;
    }

    public void setSystemValue(Double systemValue) {
        this.systemValue = systemValue;
    }

    public Double getPhysiacalQty() {
        return physiacalQty;
    }

    public void setPhysiacalQty(Double physiacalQty) {
        this.physiacalQty = physiacalQty;
    }

    public Double getPhysicalRate() {
        return physicalRate;
    }

    public void setPhysicalRate(Double physicalRate) {
        this.physicalRate = physicalRate;
    }

    public Double getPhysicalValue() {
        return physicalValue;
    }

    public void setPhysicalValue(Double physicalValue) {
        this.physicalValue = physicalValue;
    }
}
