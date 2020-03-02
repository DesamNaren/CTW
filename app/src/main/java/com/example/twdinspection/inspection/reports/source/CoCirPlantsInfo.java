package com.example.twdinspection.inspection.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoCirPlantsInfo {

    @SerializedName("sl_no")
    @Expose
    private String slNo;
    @SerializedName("plant_cnt")
    @Expose
    private String plantCnt;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("plantType")
    @Expose
    private String plantType;

    public String getSlNo() {
        return slNo;
    }

    public void setSlNo(String slNo) {
        this.slNo = slNo;
    }

    public String getPlantCnt() {
        return plantCnt;
    }

    public void setPlantCnt(String plantCnt) {
        this.plantCnt = plantCnt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

}
