package com.cgg.twdinspection.inspection.source.inst_menu_info;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "inst_latest_time_info")
public class InstLatestTimeInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String inst_id;
    private String inst_name;
    private String inst_time;

    public InstLatestTimeInfo(String inst_id, String inst_name, String inst_time) {
        this.inst_id = inst_id;
        this.inst_name = inst_name;
        this.inst_time = inst_time;
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

    public String getInst_time() {
        return inst_time;
    }

    public void setInst_time(String inst_time) {
        this.inst_time = inst_time;
    }
}
