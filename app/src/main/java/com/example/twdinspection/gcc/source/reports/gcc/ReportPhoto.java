package com.example.twdinspection.gcc.source.reports.gcc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportPhoto {

    @SerializedName("photo")
    @Expose
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
