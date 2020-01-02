package com.example.twdinspection.inspection.source.cocurriularActivities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class CocurricularStudListEntity {

    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String id;

    @ColumnInfo()
    private String name;

    @ColumnInfo()
    private String studclass;

    @ColumnInfo()
    private String level;

    @ColumnInfo()
    private String event;


    @ColumnInfo()
    private String participated_win;

    public String getOfficer_id() {
        return officer_id;
    }

    public void setOfficer_id(String officer_id) {
        this.officer_id = officer_id;
    }

    public String getInspection_time() {
        return inspection_time;
    }

    public void setInspection_time(String inspection_time) {
        this.inspection_time = inspection_time;
    }

    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(String institute_id) {
        this.institute_id = institute_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
