package com.cgg.twdinspection.gcc.source.submit;

import com.cgg.twdinspection.gcc.source.inspections.InspectionSubmitResponse;
import com.cgg.twdinspection.gcc.source.stock.StockSubmitRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GCCSubmitRequest {

    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("godown_id")
    @Expose
    private String godownId;
    @SerializedName("godown_name")
    @Expose
    private String godown_name;
    @SerializedName("division_id")
    @Expose
    private String divisionId;
    @SerializedName("shop_avail")
    @Expose
    private String shop_avail;
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
    @SerializedName("Device_Id")
    @Expose
    private String deviceId;

    @SerializedName("Version_No")
    @Expose
    private String versionNo;


    @SerializedName("photo_key_id")
    @Expose
    private String photo_key_id;

    public String getPhoto_key_id() {
        return photo_key_id;
    }

    public void setPhoto_key_id(String photo_key_id) {
        this.photo_key_id = photo_key_id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getShop_avail() {
        return shop_avail;
    }

    public void setShop_avail(String shop_avail) {
        this.shop_avail = shop_avail;
    }

    public String getGodownId() {
        return godownId;
    }

    public void setGodownId(String godownId) {
        this.godownId = godownId;
    }

    public String getGodown_name() {
        return godown_name;
    }

    public void setGodown_name(String godown_name) {
        this.godown_name = godown_name;
    }

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