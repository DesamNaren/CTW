package com.cgg.twdinspection.gcc.source.inspections.processingUnit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PUnitGeneralFindings {

    @SerializedName("stock_quality_verified")
    @Expose
    private String stockQualityVerified;
    @SerializedName("hygienic_condition")
    @Expose
    private String hygienicCondition;
    @SerializedName("repair_req")
    @Expose
    private String repairReq;
    @SerializedName("remarks")
    @Expose
    private String remarks;

    public String getStockQualityVerified() {
        return stockQualityVerified;
    }

    public void setStockQualityVerified(String stockQualityVerified) {
        this.stockQualityVerified = stockQualityVerified;
    }

    public String getHygienicCondition() {
        return hygienicCondition;
    }

    public void setHygienicCondition(String hygienicCondition) {
        this.hygienicCondition = hygienicCondition;
    }

    public String getRepairReq() {
        return repairReq;
    }

    public void setRepairReq(String repairReq) {
        this.repairReq = repairReq;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
