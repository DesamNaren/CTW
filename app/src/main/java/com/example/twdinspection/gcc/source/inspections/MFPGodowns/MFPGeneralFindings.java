package com.example.twdinspection.gcc.source.inspections.MFPGodowns;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MFPGeneralFindings {

    @SerializedName("stock_quality_verified")
    @Expose
    private String stockQualityVerified;
    @SerializedName("stack_cards_disp")
    @Expose
    private String stackCardsDisp;
    @SerializedName("hygienic_condition")
    @Expose
    private String hygienicCondition;
    @SerializedName("driage_prop_submitted")
    @Expose
    private String driagePropSubmitted;
    @SerializedName("generator_avail")
    @Expose
    private String generatorAvail;
    @SerializedName("tray_dryer_avail")
    @Expose
    private String trayDryerAvail;
    @SerializedName("div_manager_insp_date")
    @Expose
    private String divManagerInspDate;
    @SerializedName("repair_req")
    @Expose
    private String repairReq;
    @SerializedName("repair_type")
    @Expose
    private String repairType;
    @SerializedName("remarks")
    @Expose
    private String remarks;

    public String getStockQualityVerified() {
        return stockQualityVerified;
    }

    public void setStockQualityVerified(String stockQualityVerified) {
        this.stockQualityVerified = stockQualityVerified;
    }

    public String getStackCardsDisp() {
        return stackCardsDisp;
    }

    public void setStackCardsDisp(String stackCardsDisp) {
        this.stackCardsDisp = stackCardsDisp;
    }

    public String getHygienicCondition() {
        return hygienicCondition;
    }

    public void setHygienicCondition(String hygienicCondition) {
        this.hygienicCondition = hygienicCondition;
    }

    public String getDriagePropSubmitted() {
        return driagePropSubmitted;
    }

    public void setDriagePropSubmitted(String driagePropSubmitted) {
        this.driagePropSubmitted = driagePropSubmitted;
    }

    public String getGeneratorAvail() {
        return generatorAvail;
    }

    public void setGeneratorAvail(String generatorAvail) {
        this.generatorAvail = generatorAvail;
    }

    public String getTrayDryerAvail() {
        return trayDryerAvail;
    }

    public void setTrayDryerAvail(String trayDryerAvail) {
        this.trayDryerAvail = trayDryerAvail;
    }

    public String getDivManagerInspDate() {
        return divManagerInspDate;
    }

    public void setDivManagerInspDate(String divManagerInspDate) {
        this.divManagerInspDate = divManagerInspDate;
    }

    public String getRepairReq() {
        return repairReq;
    }

    public void setRepairReq(String repairReq) {
        this.repairReq = repairReq;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
