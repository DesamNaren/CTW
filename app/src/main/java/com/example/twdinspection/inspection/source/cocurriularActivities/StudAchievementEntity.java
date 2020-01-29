package com.example.twdinspection.inspection.source.cocurriularActivities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "stud_achievements_info")
public class StudAchievementEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    private int id;

    @Ignore
    @ColumnInfo()
    private int sl_no;

    @ColumnInfo()
    private String studname;

    @ColumnInfo()
    private String studclass;

    @ColumnInfo()
    private String level;

    @ColumnInfo()
    private String event;


    @ColumnInfo()
    private String win_location;


    @ColumnInfo()
    private String participated_win;

    public int getSl_no() {
        return sl_no;
    }

    public void setSl_no(int sl_no) {
        this.sl_no = sl_no;
    }

    public String getWin_location() {
        return win_location;
    }

    public void setWin_location(String win_location) {
        this.win_location = win_location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudname() {
        return studname;
    }

    public void setStudname(String studname) {
        this.studname = studname;
    }

    public String getStudclass() {
        return studclass;
    }

    public void setStudclass(String studclass) {
        this.studclass = studclass;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getParticipated_win() {
        return participated_win;
    }

    public void setParticipated_win(String participated_win) {
        this.participated_win = participated_win;
    }
}
