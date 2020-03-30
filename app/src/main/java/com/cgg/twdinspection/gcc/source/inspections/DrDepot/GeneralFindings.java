package com.cgg.twdinspection.gcc.source.inspections.DrDepot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralFindings {

    @SerializedName("values_as_per_sale_price")
    @Expose
    private String valuesAsPerSalePrice;
    @SerializedName("values_as_per_purchase_price")
    @Expose
    private String valuesAsPerPurchasePrice;
    @SerializedName("stock_quality_verified")
    @Expose
    private String stockQualityVerified;
    @SerializedName("hygienic_condition")
    @Expose
    private String hygienicCondition;
    @SerializedName("repairs_required")
    @Expose
    private String repairsRequired;
    @SerializedName("repair_type")
    @Expose
    private String repairType;
    @SerializedName("feedback")
    @Expose
    private String feedback;
    @SerializedName("remarks")
    @Expose
    private String remarks;

    public String getValuesAsPerSalePrice() {
        return valuesAsPerSalePrice;
    }

    public void setValuesAsPerSalePrice(String valuesAsPerSalePrice) {
        this.valuesAsPerSalePrice = valuesAsPerSalePrice;
    }

    public String getValuesAsPerPurchasePrice() {
        return valuesAsPerPurchasePrice;
    }

    public void setValuesAsPerPurchasePrice(String valuesAsPerPurchasePrice) {
        this.valuesAsPerPurchasePrice = valuesAsPerPurchasePrice;
    }

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

    public String getRepairsRequired() {
        return repairsRequired;
    }

    public void setRepairsRequired(String repairsRequired) {
        this.repairsRequired = repairsRequired;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}