package com.example.twdinspection.gcc.source.inspections;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrDepotInspection {

    private String ecsStock;
    private String drStock;
    private String abstractSales;
    private String depotCashBook;
    private String liabilityReg;
    private String visitorsBook;
    private String saleBook;
    private String weightsMeasurements;
    private String certIssueDate;
    private String emptyStock;
    private String depotAuthCert;
    private String mfpStock;
    private String mfpPurchase;
    private String billAbstract;
    private String abstractAccnt;
    private String advanceAccnt;
    private String mfpLiability;
    private String depotNameBoard;
    private String gccObjPrinc;
    private String depotTimimg;
    private String mfpComm;
    private String ecComm;
    private String drComm;
    private String stockBal;
    private String valuesAsPerSale;
    private String valuesAsPerPurchasePrice;
    private String qualVerified;
    private String depotMaintHygeine;
    private String repairsReq;
    private String repairsType;
    private String repairsPath;
    private String feedback;
    private String remarks;

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

    public String getRepairsPath() {
        return repairsPath;
    }

    public void setRepairsPath(String repairsPath) {
        this.repairsPath = repairsPath;
    }

    public String getEcsStock() {
        return ecsStock;
    }

    public void setEcsStock(String ecsStock) {
        this.ecsStock = ecsStock;
    }

    public String getDrStock() {
        return drStock;
    }

    public void setDrStock(String drStock) {
        this.drStock = drStock;
    }

    public String getAbstractSales() {
        return abstractSales;
    }

    public void setAbstractSales(String abstractSales) {
        this.abstractSales = abstractSales;
    }

    public String getDepotCashBook() {
        return depotCashBook;
    }

    public void setDepotCashBook(String depotCashBook) {
        this.depotCashBook = depotCashBook;
    }

    public String getLiabilityReg() {
        return liabilityReg;
    }

    public void setLiabilityReg(String liabilityReg) {
        this.liabilityReg = liabilityReg;
    }

    public String getVisitorsBook() {
        return visitorsBook;
    }

    public void setVisitorsBook(String visitorsBook) {
        this.visitorsBook = visitorsBook;
    }

    public String getSaleBook() {
        return saleBook;
    }

    public void setSaleBook(String saleBook) {
        this.saleBook = saleBook;
    }

    public String getWeightsMeasurements() {
        return weightsMeasurements;
    }

    public void setWeightsMeasurements(String weightsMeasurements) {
        this.weightsMeasurements = weightsMeasurements;
    }

    public String getCertIssueDate() {
        return certIssueDate;
    }

    public void setCertIssueDate(String certIssueDate) {
        this.certIssueDate = certIssueDate;
    }

    public String getEmptyStock() {
        return emptyStock;
    }

    public void setEmptyStock(String emptyStock) {
        this.emptyStock = emptyStock;
    }

    public String getDepotAuthCert() {
        return depotAuthCert;
    }

    public void setDepotAuthCert(String depotAuthCert) {
        this.depotAuthCert = depotAuthCert;
    }

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

    public String getDepotNameBoard() {
        return depotNameBoard;
    }

    public void setDepotNameBoard(String depotNameBoard) {
        this.depotNameBoard = depotNameBoard;
    }

    public String getGccObjPrinc() {
        return gccObjPrinc;
    }

    public void setGccObjPrinc(String gccObjPrinc) {
        this.gccObjPrinc = gccObjPrinc;
    }

    public String getDepotTimimg() {
        return depotTimimg;
    }

    public void setDepotTimimg(String depotTimimg) {
        this.depotTimimg = depotTimimg;
    }

    public String getMfpComm() {
        return mfpComm;
    }

    public void setMfpComm(String mfpComm) {
        this.mfpComm = mfpComm;
    }

    public String getEcComm() {
        return ecComm;
    }

    public void setEcComm(String ecComm) {
        this.ecComm = ecComm;
    }

    public String getDrComm() {
        return drComm;
    }

    public void setDrComm(String drComm) {
        this.drComm = drComm;
    }

    public String getStockBal() {
        return stockBal;
    }

    public void setStockBal(String stockBal) {
        this.stockBal = stockBal;
    }

    public String getValuesAsPerSale() {
        return valuesAsPerSale;
    }

    public void setValuesAsPerSale(String valuesAsPerSale) {
        this.valuesAsPerSale = valuesAsPerSale;
    }

    public String getValuesAsPerPurchasePrice() {
        return valuesAsPerPurchasePrice;
    }

    public void setValuesAsPerPurchasePrice(String valuesAsPerPurchasePrice) {
        this.valuesAsPerPurchasePrice = valuesAsPerPurchasePrice;
    }

    public String getQualVerified() {
        return qualVerified;
    }

    public void setQualVerified(String qualVerified) {
        this.qualVerified = qualVerified;
    }

    public String getDepotMaintHygeine() {
        return depotMaintHygeine;
    }

    public void setDepotMaintHygeine(String depotMaintHygeine) {
        this.depotMaintHygeine = depotMaintHygeine;
    }

    public String getRepairsReq() {
        return repairsReq;
    }

    public void setRepairsReq(String repairsReq) {
        this.repairsReq = repairsReq;
    }

    public String getRepairsType() {
        return repairsType;
    }

    public void setRepairsType(String repairsType) {
        this.repairsType = repairsType;
    }
}
