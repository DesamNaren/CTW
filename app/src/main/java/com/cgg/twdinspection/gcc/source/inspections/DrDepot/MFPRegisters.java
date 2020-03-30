package com.cgg.twdinspection.gcc.source.inspections.DrDepot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MFPRegisters {

    @SerializedName("mfp_stock")
    @Expose
    private String mfpStock;
    @SerializedName("mfp_purchase")
    @Expose
    private String mfpPurchase;
    @SerializedName("bill_abstract")
    @Expose
    private String billAbstract;
    @SerializedName("abstract_accnt")
    @Expose
    private String abstractAccnt;
    @SerializedName("advance_accnt")
    @Expose
    private String advanceAccnt;
    @SerializedName("mfp_liability")
    @Expose
    private String mfpLiability;

    public String getMfpStock() {
        return mfpStock;
    }

    public void setMfpStock(String mfpStock) {
        this.mfpStock = mfpStock;
    }

    public String getMfpPurchase() {
        return mfpPurchase;
    }

    public void setMfpPurchase(String mfpPurchase) {
        this.mfpPurchase = mfpPurchase;
    }

    public String getBillAbstract() {
        return billAbstract;
    }

    public void setBillAbstract(String billAbstract) {
        this.billAbstract = billAbstract;
    }

    public String getAbstractAccnt() {
        return abstractAccnt;
    }

    public void setAbstractAccnt(String abstractAccnt) {
        this.abstractAccnt = abstractAccnt;
    }

    public String getAdvanceAccnt() {
        return advanceAccnt;
    }

    public void setAdvanceAccnt(String advanceAccnt) {
        this.advanceAccnt = advanceAccnt;
    }

    public String getMfpLiability() {
        return mfpLiability;
    }

    public void setMfpLiability(String mfpLiability) {
        this.mfpLiability = mfpLiability;
    }
}
