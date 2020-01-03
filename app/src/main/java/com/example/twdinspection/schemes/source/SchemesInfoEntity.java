package com.example.twdinspection.schemes.source;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "schemesInfo")
public class SchemesInfoEntity {
    @PrimaryKey
    @ColumnInfo()
    private int id;

    @ColumnInfo()
    private int scheme_id;

    @ColumnInfo()
    private String scheme_type;

    @Ignore
    private boolean selection;

    public boolean isSelection() {
        return selection;
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

    public SchemesInfoEntity(int scheme_id, String scheme_type) {
        this.scheme_id = scheme_id;
        this.scheme_type = scheme_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScheme_id() {
        return scheme_id;
    }

    public void setScheme_id(int scheme_id) {
        this.scheme_id = scheme_id;
    }

    public String getScheme_type() {
        return scheme_type;
    }

    public void setScheme_type(String scheme_type) {
        this.scheme_type = scheme_type;
    }
}
