package com.cgg.twdinspection.schemes.source.finyear;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class FinancialYearsEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @SerializedName("fin_year")
    @Expose
    private String finYear;
    @SerializedName("fin_id")
    @Expose
    private String finId;

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }

    public String getFinId() {
        return finId;
    }

    public void setFinId(String finId) {
        this.finId = finId;
    }
}

