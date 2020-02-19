package com.example.twdinspection.inspection.source.reports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DietIssues {
    @SerializedName("committee_exist")
    @Expose
    private String committeeExist;
    @SerializedName("food_provisions")
    @Expose
    private String foodProvisions;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("matching_with_samples")
    @Expose
    private String matchingWithSamples;
    @SerializedName("menu_chart_painted")
    @Expose
    private String menuChartPainted;
    @SerializedName("menu_chart_served")
    @Expose
    private String menuChartServed;
    @SerializedName("menu_served")
    @Expose
    private String menuServed;
    @SerializedName("officer_id")
    @Expose
    private String officerId;

    public String getCommitteeExist() {
        return committeeExist;
    }

    public void setCommitteeExist(String committeeExist) {
        this.committeeExist = committeeExist;
    }

    public String getFoodProvisions() {
        return foodProvisions;
    }

    public void setFoodProvisions(String foodProvisions) {
        this.foodProvisions = foodProvisions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getMatchingWithSamples() {
        return matchingWithSamples;
    }

    public void setMatchingWithSamples(String matchingWithSamples) {
        this.matchingWithSamples = matchingWithSamples;
    }

    public String getMenuChartPainted() {
        return menuChartPainted;
    }

    public void setMenuChartPainted(String menuChartPainted) {
        this.menuChartPainted = menuChartPainted;
    }

    public String getMenuChartServed() {
        return menuChartServed;
    }

    public void setMenuChartServed(String menuChartServed) {
        this.menuChartServed = menuChartServed;
    }

    public String getMenuServed() {
        return menuServed;
    }

    public void setMenuServed(String menuServed) {
        this.menuServed = menuServed;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }
}
