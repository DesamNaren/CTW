package com.cgg.twdinspection.gcc.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LpgReportStockDetails {

    @SerializedName("stock_value_as_per_system")
    @Expose
    private String stockValueAsPerSystem;
    @SerializedName("stock_value_as_per_physical")
    @Expose
    private String stockValueAsPerPhysical;
    @SerializedName("deficit")
    @Expose
    private String deficit;
    @SerializedName("deficit_reason")
    @Expose
    private String deficitReason;

    public String getStockValueAsPerSystem() {
        return stockValueAsPerSystem;
    }

    public void setStockValueAsPerSystem(String stockValueAsPerSystem) {
        this.stockValueAsPerSystem = stockValueAsPerSystem;
    }

    public String getStockValueAsPerPhysical() {
        return stockValueAsPerPhysical;
    }

    public void setStockValueAsPerPhysical(String stockValueAsPerPhysical) {
        this.stockValueAsPerPhysical = stockValueAsPerPhysical;
    }

    public String getDeficit() {
        return deficit;
    }

    public void setDeficit(String deficit) {
        this.deficit = deficit;
    }

    public String getDeficitReason() {
        return deficitReason;
    }

    public void setDeficitReason(String deficitReason) {
        this.deficitReason = deficitReason;
    }

}
