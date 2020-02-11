package com.example.twdinspection.gcc.source.inspections.godown;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DRGodownInsFindings {

    @SerializedName("dr_godown")
    @Expose
    private DrGodownInsp drGodown;

    public DrGodownInsp getDrGodown() {
        return drGodown;
    }

    public void setDrGodown(DrGodownInsp drGodown) {
        this.drGodown = drGodown;
    }

}


