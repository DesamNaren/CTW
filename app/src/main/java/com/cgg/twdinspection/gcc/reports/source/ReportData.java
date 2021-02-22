package com.cgg.twdinspection.gcc.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReportData {

    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("division_id")
    @Expose
    private String divisionId;
    @SerializedName("division_name")
    @Expose
    private String divisionName;
    @SerializedName("society_id")
    @Expose
    private String societyId;
    @SerializedName("society_name")
    @Expose
    private String societyName;
    @SerializedName("incharge_name")
    @Expose
    private String inchargeName;
    @SerializedName("supplier_type")
    @Expose
    private String supplierType;
    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("shop_avail")
    @Expose
    private String shopAvail;
    @SerializedName("godown_id")
    @Expose
    private String godownId;
    @SerializedName("godown_name")
    @Expose
    private String godownName;
    @SerializedName("stock_details")
    @Expose
    private ReportStockDetails stockDetails;
    @SerializedName("inspection_findings")
    @Expose
    private InspectionReportResponse inspectionFindings;
    @SerializedName("photos")
    @Expose
    private ArrayList<ReportPhoto> photos = null;

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getSocietyId() {
        return societyId;
    }

    public void setSocietyId(String societyId) {
        this.societyId = societyId;
    }

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public String getInchargeName() {
        return inchargeName;
    }

    public void setInchargeName(String inchargeName) {
        this.inchargeName = inchargeName;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getShopAvail() {
        return shopAvail;
    }

    public void setShopAvail(String shopAvail) {
        this.shopAvail = shopAvail;
    }

    public String getGodownId() {
        return godownId;
    }

    public void setGodownId(String godownId) {
        this.godownId = godownId;
    }

    public String getGodownName() {
        return godownName;
    }

    public void setGodownName(String godownName) {
        this.godownName = godownName;
    }

    public ReportStockDetails getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(ReportStockDetails stockDetails) {
        this.stockDetails = stockDetails;
    }

    public InspectionReportResponse getInspectionFindings() {
        return inspectionFindings;
    }

    public void setInspectionFindings(InspectionReportResponse inspectionFindings) {
        this.inspectionFindings = inspectionFindings;
    }

    public ArrayList<ReportPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<ReportPhoto> photos) {
        this.photos = photos;
    }

}