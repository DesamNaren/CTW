package com.cgg.twdinspection.gcc.source.inspections.lpg;

import com.cgg.twdinspection.gcc.source.inspections.godown.DrGodownInsp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LPGInsFindings {

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


