package com.example.twdinspection.gcc.source.inspections.DrDepot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockDetails {

    @SerializedName("stock_value_as_per_system")
    @Expose
    private Double stockValueAsPerSystem;
    @SerializedName("stock_value_as_per_physical")
    @Expose
    private Double stockValueAsPerPhysical;
    @SerializedName("difference")
    @Expose
    private Double difference;
    @SerializedName("cash_bal_as_per_cash_book")
    @Expose
    private Double cashBalAsPerCashBook;
    @SerializedName("physical_cash")
    @Expose
    private Double physicalCash;
    @SerializedName("vouchers")
    @Expose
    private Double vouchers;
    @SerializedName("liability_balance")
    @Expose
    private Double liabilityBalance;
    @SerializedName("deficit")
    @Expose
    private Double deficit;
    @SerializedName("reason")
    @Expose
    private String reason;

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

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }

    public Double getCashBalAsPerCashBook() {
        return cashBalAsPerCashBook;
    }

    public void setCashBalAsPerCashBook(Double cashBalAsPerCashBook) {
        this.cashBalAsPerCashBook = cashBalAsPerCashBook;
    }

    public Double getPhysicalCash() {
        return physicalCash;
    }

    public void setPhysicalCash(Double physicalCash) {
        this.physicalCash = physicalCash;
    }

    public Double getVouchers() {
        return vouchers;
    }

    public void setVouchers(Double vouchers) {
        this.vouchers = vouchers;
    }

    public Double getLiabilityBalance() {
        return liabilityBalance;
    }

    public void setLiabilityBalance(Double liabilityBalance) {
        this.liabilityBalance = liabilityBalance;
    }

    public Double getDeficit() {
        return deficit;
    }

    public void setDeficit(Double deficit) {
        this.deficit = deficit;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
