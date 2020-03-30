package com.cgg.twdinspection.gcc.source.inspections.godown;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrGodownGeneralFindings {

    @SerializedName("stock_register_entries")
    @Expose
    private String stockRegisterEntries;
    @SerializedName("values_as_per_sale_price")
    @Expose
    private String valuesAsPerSalePrice;
    @SerializedName("stock_quality_verified")
    @Expose
    private String stockQualityVerified;
    @SerializedName("stock_card_display")
    @Expose
    private String stockCardDisplay;
    @SerializedName("computerized_system")
    @Expose
    private String computerizedSystem;
    @SerializedName("hygienic_condition")
    @Expose
    private String hygienicCondition;
    @SerializedName("fire_saftey_avail")
    @Expose
    private String fireSafteyAvail;
    @SerializedName("society_manager_inspection")
    @Expose
    private String societyManagerInspection;
    @SerializedName("society_manager_inspection_date")
    @Expose
    private String societyManagerInspectionDate;
    @SerializedName("division_manager_inspection")
    @Expose
    private String divisionManagerInspection;
    @SerializedName("division_manager_inspection_date")
    @Expose
    private String divisionManagerInspectionDate;
    @SerializedName("repairs_required")
    @Expose
    private String repairsRequired;
    @SerializedName("repair_type")
    @Expose
    private String repairType;
    @SerializedName("fire_dept_noc")
    @Expose
    private String fireDeptNoc;
    @SerializedName("weight_measure_certificate")
    @Expose
    private String weightMeasureCertificate;
    @SerializedName("weight_measure_validity")
    @Expose
    private String weightMeasureValidity;
    @SerializedName("remarks")
    @Expose
    private String remarks;

    public String getStockRegisterEntries() {
        return stockRegisterEntries;
    }

    public void setStockRegisterEntries(String stockRegisterEntries) {
        this.stockRegisterEntries = stockRegisterEntries;
    }

    public String getValuesAsPerSalePrice() {
        return valuesAsPerSalePrice;
    }

    public void setValuesAsPerSalePrice(String valuesAsPerSalePrice) {
        this.valuesAsPerSalePrice = valuesAsPerSalePrice;
    }

    public String getStockQualityVerified() {
        return stockQualityVerified;
    }

    public void setStockQualityVerified(String stockQualityVerified) {
        this.stockQualityVerified = stockQualityVerified;
    }

    public String getStockCardDisplay() {
        return stockCardDisplay;
    }

    public void setStockCardDisplay(String stockCardDisplay) {
        this.stockCardDisplay = stockCardDisplay;
    }

    public String getComputerizedSystem() {
        return computerizedSystem;
    }

    public void setComputerizedSystem(String computerizedSystem) {
        this.computerizedSystem = computerizedSystem;
    }

    public String getHygienicCondition() {
        return hygienicCondition;
    }

    public void setHygienicCondition(String hygienicCondition) {
        this.hygienicCondition = hygienicCondition;
    }

    public String getFireSafteyAvail() {
        return fireSafteyAvail;
    }

    public void setFireSafteyAvail(String fireSafteyAvail) {
        this.fireSafteyAvail = fireSafteyAvail;
    }

    public String getSocietyManagerInspection() {
        return societyManagerInspection;
    }

    public void setSocietyManagerInspection(String societyManagerInspection) {
        this.societyManagerInspection = societyManagerInspection;
    }

    public String getSocietyManagerInspectionDate() {
        return societyManagerInspectionDate;
    }

    public void setSocietyManagerInspectionDate(String societyManagerInspectionDate) {
        this.societyManagerInspectionDate = societyManagerInspectionDate;
    }

    public String getDivisionManagerInspection() {
        return divisionManagerInspection;
    }

    public void setDivisionManagerInspection(String divisionManagerInspection) {
        this.divisionManagerInspection = divisionManagerInspection;
    }

    public String getDivisionManagerInspectionDate() {
        return divisionManagerInspectionDate;
    }

    public void setDivisionManagerInspectionDate(String divisionManagerInspectionDate) {
        this.divisionManagerInspectionDate = divisionManagerInspectionDate;
    }

    public String getRepairsRequired() {
        return repairsRequired;
    }

    public void setRepairsRequired(String repairsRequired) {
        this.repairsRequired = repairsRequired;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public String getFireDeptNoc() {
        return fireDeptNoc;
    }

    public void setFireDeptNoc(String fireDeptNoc) {
        this.fireDeptNoc = fireDeptNoc;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
