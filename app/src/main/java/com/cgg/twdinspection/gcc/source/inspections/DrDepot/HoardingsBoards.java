package com.cgg.twdinspection.gcc.source.inspections.DrDepot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HoardingsBoards {

    @SerializedName("depot_name_board")
    @Expose
    private String depotNameBoard;
    @SerializedName("gcc_obj_principles")
    @Expose
    private String gccObjPrinciples;
    @SerializedName("depot_timing")
    @Expose
    private String depotTiming;
    @SerializedName("mfp_commodities")
    @Expose
    private String mfpCommodities;
    @SerializedName("ec_commodities")
    @Expose
    private String ecCommodities;
    @SerializedName("dr_commodities")
    @Expose
    private String drCommodities;
    @SerializedName("stock_bal")
    @Expose
    private String stockBal;

    public String getDepotNameBoard() {
        return depotNameBoard;
    }

    public void setDepotNameBoard(String depotNameBoard) {
        this.depotNameBoard = depotNameBoard;
    }

    public String getGccObjPrinciples() {
        return gccObjPrinciples;
    }

    public void setGccObjPrinciples(String gccObjPrinciples) {
        this.gccObjPrinciples = gccObjPrinciples;
    }

    public String getDepotTiming() {
        return depotTiming;
    }

    public void setDepotTiming(String depotTiming) {
        this.depotTiming = depotTiming;
    }

    public String getMfpCommodities() {
        return mfpCommodities;
    }

    public void setMfpCommodities(String mfpCommodities) {
        this.mfpCommodities = mfpCommodities;
    }

    public String getEcCommodities() {
        return ecCommodities;
    }

    public void setEcCommodities(String ecCommodities) {
        this.ecCommodities = ecCommodities;
    }

    public String getDrCommodities() {
        return drCommodities;
    }

    public void setDrCommodities(String drCommodities) {
        this.drCommodities = drCommodities;
    }

    public String getStockBal() {
        return stockBal;
    }

    public void setStockBal(String stockBal) {
        this.stockBal = stockBal;
    }

}
