package com.example.twdinspection.schemes.source.schemes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class SchemeEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SchemeEntity() {
    }

    public SchemeEntity(boolean selection, String schemeType, String schemeId) {
        this.selection = selection;
        this.schemeType = schemeType;
        this.schemeId = schemeId;
    }

    @Ignore
    private boolean selection;

    public boolean isSelection() {
        return selection;
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

    @SerializedName("scheme_type")
    @Expose
    private String schemeType;
    @SerializedName("scheme_id")
    @Expose
    private String schemeId;

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }


}
