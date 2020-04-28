package com.cgg.twdinspection.gcc.source.inspections.DrDepot;

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
    private String deficit_reason;


    @SerializedName("stock_value_as_per_sys_insp")
    @Expose
    private String stockValueAsPerSystemInsp;
    @SerializedName("stock_value_as_per_sys_not_insp")
    @Expose
    private String stockValueAsPerSystemNotInsp;

    public String getStockValueAsPerSystemInsp() {
        return stockValueAsPerSystemInsp;
    }

    public void setStockValueAsPerSystemInsp(String stockValueAsPerSystemInsp) {
        this.stockValueAsPerSystemInsp = stockValueAsPerSystemInsp;
    }

    public String getStockValueAsPerSystemNotInsp() {
        return stockValueAsPerSystemNotInsp;
    }

    public void setStockValueAsPerSystemNotInsp(String stockValueAsPerSystemNotInsp) {
        this.stockValueAsPerSystemNotInsp = stockValueAsPerSystemNotInsp;
    }

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

    public String getDeficit_reason() {
        return deficit_reason;
    }

    public void setDeficit_reason(String deficit_reason) {
        this.deficit_reason = deficit_reason;
    }
}
