package com.cgg.twdinspection.gcc.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrDepotInspStockDetails {

    @SerializedName("stock_value_as_per_system")
    @Expose
    private String stockValueAsPerSystem;
    @SerializedName("stock_value_as_per_physical")
    @Expose
    private String stockValueAsPerPhysical;
    @SerializedName("difference")
    @Expose
    private String difference;
    @SerializedName("cash_bal_as_per_cash_book")
    @Expose
    private String cashBalAsPerCashBook;
    @SerializedName("physical_cash")
    @Expose
    private String physicalCash;
    @SerializedName("vouchers")
    @Expose
    private String vouchers;
    @SerializedName("liability_balance")
    @Expose
    private String liabilityBalance;
    @SerializedName("deficit")
    @Expose
    private String deficit;
    @SerializedName("deficit_reason")
    @Expose
    private String deficit_reason;

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

    public String getDifference() {
        return difference;
    }

    public void setDifference(String difference) {
        this.difference = difference;
    }

    public String getCashBalAsPerCashBook() {
        return cashBalAsPerCashBook;
    }

    public void setCashBalAsPerCashBook(String cashBalAsPerCashBook) {
        this.cashBalAsPerCashBook = cashBalAsPerCashBook;
    }

    public String getPhysicalCash() {
        return physicalCash;
    }

    public void setPhysicalCash(String physicalCash) {
        this.physicalCash = physicalCash;
    }

    public String getVouchers() {
        return vouchers;
    }

    public void setVouchers(String vouchers) {
        this.vouchers = vouchers;
    }

    public String getLiabilityBalance() {
        return liabilityBalance;
    }

    public void setLiabilityBalance(String liabilityBalance) {
        this.liabilityBalance = liabilityBalance;
    }

    public String getDeficit() {
        return deficit;
    }

    public void setDeficit(String deficit) {
        this.deficit = deficit;
    }

    public String getDeficit_reason() {
        return deficit_reason;
    }

    public void setDeficit_reason(String deficit_reason) {
        this.deficit_reason = deficit_reason;
    }
}
