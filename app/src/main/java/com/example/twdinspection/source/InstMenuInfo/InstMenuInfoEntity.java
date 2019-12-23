package com.example.twdinspection.source.InstMenuInfo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "InstMenuEntitiy")
public class InstMenuInfoEntity {
    @PrimaryKey
    public int id;

    @ColumnInfo()
    private int section_id;

    @ColumnInfo()
    private int flag_completed;

    @ColumnInfo()
    private String section_name;

    @ColumnInfo()
    private String section_time;

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
}
