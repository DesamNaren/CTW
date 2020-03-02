package com.example.twdinspection.inspection.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoCirStuInfo {

    @SerializedName("sl_no")
    @Expose
    private String slNo;
    @SerializedName("studclass")
    @Expose
    private String studclass;
    @SerializedName("participated_win")
    @Expose
    private String participatedWin;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("win_location")
    @Expose
    private String winLocation;
    @SerializedName("studname")
    @Expose
    private String studname;

    public String getSlNo() {
        return slNo;
    }

    public void setSlNo(String slNo) {
        this.slNo = slNo;
    }

    public String getStudclass() {
        return studclass;
    }

    public void setStudclass(String studclass) {
        this.studclass = studclass;
    }

    public String getParticipatedWin() {
        return participatedWin;
    }

    public void setParticipatedWin(String participatedWin) {
        this.participatedWin = participatedWin;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getWinLocation() {
        return winLocation;
    }

    public void setWinLocation(String winLocation) {
        this.winLocation = winLocation;
    }

    public String getStudname() {
        return studname;
    }

    public void setStudname(String studname) {
        this.studname = studname;
    }

}
