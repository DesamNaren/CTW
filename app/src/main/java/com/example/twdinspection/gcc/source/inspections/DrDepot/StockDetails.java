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
    private Integer difference;
    @SerializedName("cash_bal_as_per_cash_book")
    @Expose
    private Integer cashBalAsPerCashBook;
    @SerializedName("physical_cash")
    @Expose
    private Integer physicalCash;
    @SerializedName("vouchers")
    @Expose
    private Integer vouchers;
    @SerializedName("liability_balance")
    @Expose
    private Integer liabilityBalance;
    @SerializedName("deficit")
    @Expose
    private Integer deficit;
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

    public Integer getDifference() {
        return difference;
    }

    public void setDifference(Integer difference) {
        this.difference = difference;
    }

    public Integer getCashBalAsPerCashBook() {
        return cashBalAsPerCashBook;
    }

    public void setCashBalAsPerCashBook(Integer cashBalAsPerCashBook) {
        this.cashBalAsPerCashBook = cashBalAsPerCashBook;
    }

    public Integer getPhysicalCash() {
        return physicalCash;
    }

    public void setPhysicalCash(Integer physicalCash) {
        this.physicalCash = physicalCash;
    }

    public Integer getVouchers() {
        return vouchers;
    }

    public void setVouchers(Integer vouchers) {
        this.vouchers = vouchers;
    }

    public Integer getLiabilityBalance() {
        return liabilityBalance;
    }

    public void setLiabilityBalance(Integer liabilityBalance) {
        this.liabilityBalance = liabilityBalance;
    }

    public Integer getDeficit() {
        return deficit;
    }

    public void setDeficit(Integer deficit) {
        this.deficit = deficit;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
