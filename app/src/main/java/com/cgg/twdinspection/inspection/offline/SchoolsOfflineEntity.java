package com.cgg.twdinspection.inspection.offline;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Schools_Offline")
public class SchoolsOfflineEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String inst_id;
    private String inst_name;
    private String dist_id;
    private String man_id;
    private String vil_id;
    private String dist_name;
    private String man_name;
    private String vil_name;
    private String inst_time;
    private String officer_id;

    public String getOfficer_id() {
        return officer_id;
    }

    public void setOfficer_id(String officer_id) {
        this.officer_id = officer_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInst_id() {
        return inst_id;
    }

    public void setInst_id(String inst_id) {
        this.inst_id = inst_id;
    }

    public String getInst_name() {
        return inst_name;
    }

    public void setInst_name(String inst_name) {
        this.inst_name = inst_name;
    }

    public String getDist_id() {
        return dist_id;
    }

    public void setDist_id(String dist_id) {
        this.dist_id = dist_id;
    }

    public String getMan_id() {
        return man_id;
    }

    public void setMan_id(String man_id) {
        this.man_id = man_id;
    }

    public String getVil_id() {
        return vil_id;
    }

    public void setVil_id(String vil_id) {
        this.vil_id = vil_id;
    }

    public String getDist_name() {
        return dist_name;
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
    }

    public String getMan_name() {
        return man_name;
    }

    public void setMan_name(String man_name) {
        this.man_name = man_name;
    }

    public String getVil_name() {
        return vil_name;
    }

    public void setVil_name(String vil_name) {
        this.vil_name = vil_name;
    }

    public String getInst_time() {
        return inst_time;
    }

    public void setInst_time(String inst_time) {
        this.inst_time = inst_time;
    }
}
