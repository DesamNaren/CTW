package com.example.twdinspection.gcc.source.submit;

import com.example.twdinspection.gcc.source.inspections.DrDepot.StockDetails;
import com.example.twdinspection.gcc.source.inspections.InspectionSubmitResponse;
import com.example.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.example.twdinspection.gcc.source.stock.StockSubmitRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GCCSubmitRequest {

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
    @SerializedName("inspection_findings")
    @Expose
    private InspectionSubmitResponse inspectionFindings;
    @SerializedName("stock_details")
    @Expose
    private StockSubmitRequest stockDetails;

    public StockSubmitRequest getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(StockSubmitRequest stockDetails) {
        this.stockDetails = stockDetails;
    }

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

    public InspectionSubmitResponse getInspectionFindings() {
        return inspectionFindings;
    }

    public void setInspectionFindings(InspectionSubmitResponse inspectionFindings) {
        this.inspectionFindings = inspectionFindings;
    }

}