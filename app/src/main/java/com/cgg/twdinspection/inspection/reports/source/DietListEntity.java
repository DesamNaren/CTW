package com.cgg.twdinspection.inspection.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DietListEntity {

    @SerializedName("ground_bal")
    @Expose
    private String groundBal;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("book_bal")
    @Expose
    private String bookBal;

    public String getGroundBal() {
        return groundBal;
    }

    public void setGroundBal(String groundBal) {
        this.groundBal = groundBal;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getBookBal() {
        return bookBal;
    }

    public void setBookBal(String bookBal) {
        this.bookBal = bookBal;
    }

}
