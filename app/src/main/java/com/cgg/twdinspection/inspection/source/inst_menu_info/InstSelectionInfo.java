package com.cgg.twdinspection.inspection.source.inst_menu_info;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "inst_selection_info")
public class InstSelectionInfo {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String inst_id;
    private String inst_name;
    private String dist_id;
    private String man_id;
    private String vil_id;
    private String dist_name;
    private String man_name;
    private String vil_name;
    private String inst_lat;
    private String inst_lng;
    private String inst_address;

    public InstSelectionInfo(String inst_id, String inst_name, String dist_id, String man_id,
                             String vil_id, String dist_name, String man_name,
                             String vil_name, String inst_lat, String inst_lng, String inst_address) {
        this.inst_id = inst_id;
        this.inst_name = inst_name;
        this.dist_id = dist_id;
        this.man_id = man_id;
        this.vil_id = vil_id;
        this.dist_name = dist_name;
        this.man_name = man_name;
        this.vil_name = vil_name;
        this.inst_lat = inst_lat;
        this.inst_lng = inst_lng;
        this.inst_address = inst_address;
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

    public String getInst_lat() {
        return inst_lat;
    }

    public void setInst_lat(String inst_lat) {
        this.inst_lat = inst_lat;
    }

    public String getInst_lng() {
        return inst_lng;
    }

    public void setInst_lng(String inst_lng) {
        this.inst_lng = inst_lng;
    }

    public String getInst_address() {
        return inst_address;
    }

    public void setInst_address(String inst_address) {
        this.inst_address = inst_address;
    }
}
