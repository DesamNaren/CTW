package com.example.twdinspection.engineering_works.source;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

@Entity
public class SectorsEntity {

    @PrimaryKey
    @NonNull
    @SerializedName("sector_id")
    @Expose
    private Integer sectorId;
    @SerializedName("sector_name")
    @Expose
    private String sectorName;

    @NotNull
    public Integer getSectorId() {
        return sectorId;
    }

    public void setSectorId(@NotNull Integer sectorId) {
        this.sectorId = sectorId;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

}
