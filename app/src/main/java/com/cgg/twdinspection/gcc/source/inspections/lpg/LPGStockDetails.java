package com.cgg.twdinspection.gcc.source.inspections.lpg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LPGStockDetails {

    @SerializedName("stock_value_as_per_system")
    @Expose
    private Double stockValueAsPerSystem;
    @SerializedName("stock_value_as_per_physical")
    @Expose
    private Double stockValueAsPerPhysical;
    @SerializedName("deficit")
    @Expose
    private Double deficit;
    @SerializedName("deficit_reason")
    @Expose
    private String deficitReason;

    public Double getStockValueAsPerSystem() {
        return stockValueAsPerSystem;
    }

    public void setStockValueAsPerSystem(Double stockValueAsPerSystem) {
        this.stockValueAsPerSystem = stockValueAsPerSystem;
    }

    public Double getStockValueAsPerPhysical() {
        return stockValueAsPerPhysical;
    }

    public void setStockValueAsPerPhysical(Double stockValueAsPerPhysical) {
        this.stockValueAsPerPhysical = stockValueAsPerPhysical;
    }

    public Double getDeficit() {
        return deficit;
    }

    public void setDeficit(Double deficit) {
        this.deficit = deficit;
    }

    public String getDeficitReason() {
        return deficitReason;
    }

    public void setDeficitReason(String deficitReason) {
        this.deficitReason = deficitReason;
    }

}
