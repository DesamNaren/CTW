package com.example.twdinspection.gcc.source.inspections.DrDepot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterBookCertificates {

    @SerializedName("ecs_stock_register")
    @Expose
    private String ecsStockRegister;
    @SerializedName("drs_stock_register")
    @Expose
    private String drsStockRegister;
    @SerializedName("empties_register")
    @Expose
    private String emptiesRegister;
    @SerializedName("abstract_sales_register")
    @Expose
    private String abstractSalesRegister;
    @SerializedName("cash_book")
    @Expose
    private String cashBook;
    @SerializedName("liability_register")
    @Expose
    private String liabilityRegister;
    @SerializedName("visitors_note_book")
    @Expose
    private String visitorsNoteBook;
    @SerializedName("sale_bill_book")
    @Expose
    private String saleBillBook;
    @SerializedName("weight_measure_certificate")
    @Expose
    private String weightMeasureCertificate;
    @SerializedName("weight_measure_validity")
    @Expose
    private String weightMeasureValidity;
    @SerializedName("depot_auth_certificate")
    @Expose
    private String depotAuthCertificate;

    public String getEcsStockRegister() {
        return ecsStockRegister;
    }

    public void setEcsStockRegister(String ecsStockRegister) {
        this.ecsStockRegister = ecsStockRegister;
    }

    public String getDrsStockRegister() {
        return drsStockRegister;
    }

    public void setDrsStockRegister(String drsStockRegister) {
        this.drsStockRegister = drsStockRegister;
    }

    public String getEmptiesRegister() {
        return emptiesRegister;
    }

    public void setEmptiesRegister(String emptiesRegister) {
        this.emptiesRegister = emptiesRegister;
    }

    public String getAbstractSalesRegister() {
        return abstractSalesRegister;
    }

    public void setAbstractSalesRegister(String abstractSalesRegister) {
        this.abstractSalesRegister = abstractSalesRegister;
    }

    public String getCashBook() {
        return cashBook;
    }

    public void setCashBook(String cashBook) {
        this.cashBook = cashBook;
    }

    public String getLiabilityRegister() {
        return liabilityRegister;
    }

    public void setLiabilityRegister(String liabilityRegister) {
        this.liabilityRegister = liabilityRegister;
    }

    public String getVisitorsNoteBook() {
        return visitorsNoteBook;
    }

    public void setVisitorsNoteBook(String visitorsNoteBook) {
        this.visitorsNoteBook = visitorsNoteBook;
    }

    public String getSaleBillBook() {
        return saleBillBook;
    }

    public void setSaleBillBook(String saleBillBook) {
        this.saleBillBook = saleBillBook;
    }

    public String getWeightMeasureCertificate() {
        return weightMeasureCertificate;
    }

    public void setWeightMeasureCertificate(String weightMeasureCertificate) {
        this.weightMeasureCertificate = weightMeasureCertificate;
    }

    public String getWeightMeasureValidity() {
        return weightMeasureValidity;
    }

    public void setWeightMeasureValidity(String weightMeasureValidity) {
        this.weightMeasureValidity = weightMeasureValidity;
    }

    public String getDepotAuthCertificate() {
        return depotAuthCertificate;
    }

    public void setDepotAuthCertificate(String depotAuthCertificate) {
        this.depotAuthCertificate = depotAuthCertificate;
    }

}