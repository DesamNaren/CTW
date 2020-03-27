package com.example.twdinspection.engineering_works.source;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "grantSchemes")
public class GrantScheme {


    @SerializedName("scheme_name")
    @Expose
    private String schemeName;

    @PrimaryKey
    @SerializedName("scheme_id")
    @Expose
    private Integer schemeId;

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public Integer getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Integer schemeId) {
        this.schemeId = schemeId;
    }

}
