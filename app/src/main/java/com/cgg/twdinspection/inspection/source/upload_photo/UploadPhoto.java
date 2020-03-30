package com.cgg.twdinspection.inspection.source.upload_photo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity (tableName = "Photos")
public class UploadPhoto {
    private int id;
    private String institute_id;
    @NotNull
    @PrimaryKey
    private String photo_name;
    private String photo_path;
    private String section_id;
    private String timeStamp;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(@NotNull String institute_id) {
        this.institute_id = institute_id;
    }

    public String getPhoto_name() {
        return photo_name;
    }

    public void setPhoto_name(String photo_name) {
        this.photo_name = photo_name;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }
}
