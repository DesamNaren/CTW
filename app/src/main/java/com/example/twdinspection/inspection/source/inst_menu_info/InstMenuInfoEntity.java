package com.example.twdinspection.inspection.source.inst_menu_info;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "inst_menu_info")
public class InstMenuInfoEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String instId;
    private int section_id;
    private int flag_completed;
    private String section_name;
    private String section_time;
    private String section_short_name;

    public InstMenuInfoEntity(String instId, int section_id, int flag_completed, String section_name, String section_time, String section_short_name) {
        this.instId = instId;
        this.section_id = section_id;
        this.flag_completed = flag_completed;
        this.section_name = section_name;
        this.section_time = section_time;
        this.section_short_name = section_short_name;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public int getFlag_completed() {
        return flag_completed;
    }

    public void setFlag_completed(int flag_completed) {
        this.flag_completed = flag_completed;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getSection_time() {
        return section_time;
    }

    public void setSection_time(String section_time) {
        this.section_time = section_time;
    }

    public String getSection_short_name() {
        return section_short_name;
    }

    public void setSection_short_name(String section_short_name) {
        this.section_short_name = section_short_name;
    }
}
