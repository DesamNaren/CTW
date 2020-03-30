package com.cgg.twdinspection.inspection.reports.source;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.cgg.twdinspection.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DietIssues {

    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("maintaining_register")
    @Expose
    private String maintainingRegister;
    @SerializedName("menu_chart_served")
    @Expose
    private String menuChartServed;
    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("discussed_with_committee")
    @Expose
    private String discussedWithCommittee;
    @SerializedName("matching_with_samples")
    @Expose
    private String matchingWithSamples;
    @SerializedName("food_provisions")
    @Expose
    private String foodProvisions;
    @SerializedName("menu_chart_painted")
    @Expose
    private String menuChartPainted;
    @SerializedName("menu_served")
    @Expose
    private String menuServed;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("committee_exist")
    @Expose
    private String committeeExist;

    @BindingAdapter("profileImage")
    public static void loadImage(ImageView view, String imageUrl) {

        Glide.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.loader_black1)
                .into(view);
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getMaintainingRegister() {
        return maintainingRegister;
    }

    public void setMaintainingRegister(String maintainingRegister) {
        this.maintainingRegister = maintainingRegister;
    }

    public String getMenuChartServed() {
        return menuChartServed;
    }

    public void setMenuChartServed(String menuChartServed) {
        this.menuChartServed = menuChartServed;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getDiscussedWithCommittee() {
        return discussedWithCommittee;
    }

    public void setDiscussedWithCommittee(String discussedWithCommittee) {
        this.discussedWithCommittee = discussedWithCommittee;
    }

    public String getMatchingWithSamples() {
        return matchingWithSamples;
    }

    public void setMatchingWithSamples(String matchingWithSamples) {
        this.matchingWithSamples = matchingWithSamples;
    }

    public String getFoodProvisions() {
        return foodProvisions;
    }

    public void setFoodProvisions(String foodProvisions) {
        this.foodProvisions = foodProvisions;
    }

    public String getMenuChartPainted() {
        return menuChartPainted;
    }

    public void setMenuChartPainted(String menuChartPainted) {
        this.menuChartPainted = menuChartPainted;
    }

    public String getMenuServed() {
        return menuServed;
    }

    public void setMenuServed(String menuServed) {
        this.menuServed = menuServed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getCommitteeExist() {
        return committeeExist;
    }

    public void setCommitteeExist(String committeeExist) {
        this.committeeExist = committeeExist;
    }

}
