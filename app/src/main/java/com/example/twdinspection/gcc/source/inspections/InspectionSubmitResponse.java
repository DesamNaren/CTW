package com.example.twdinspection.gcc.source.inspections;

import com.example.twdinspection.gcc.source.inspections.DrDepot.DrDepotInsp;
import com.example.twdinspection.gcc.source.inspections.MFPGodowns.MfpGodownsInsp;
import com.example.twdinspection.gcc.source.inspections.godown.DrGodownInsp;
import com.example.twdinspection.gcc.source.inspections.processingUnit.PUnitInsp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InspectionSubmitResponse {

    @SerializedName("dr_godown")
    @Expose
    private DrGodownInsp drGodown;
    @SerializedName("dr_depot")
    @Expose
    private DrDepotInsp drDepot;
    @SerializedName("mfp_godowns")
    @Expose
    private MfpGodownsInsp mfpGodowns;
    @SerializedName("processing_unit")
    @Expose
    private PUnitInsp processingUnit;

    public DrGodownInsp getDrGodown() {
        return drGodown;
    }

    public void setDrGodown(DrGodownInsp drGodown) {
        this.drGodown = drGodown;
    }

    public DrDepotInsp getDrDepot() {
        return drDepot;
    }

    public void setDrDepot(DrDepotInsp drDepot) {
        this.drDepot = drDepot;
    }

    public MfpGodownsInsp getMfpGodowns() {
        return mfpGodowns;
    }

    public void setMfpGodowns(MfpGodownsInsp mfpGodowns) {
        this.mfpGodowns = mfpGodowns;
    }

    public PUnitInsp getProcessingUnit() {
        return processingUnit;
    }

    public void setProcessingUnit(PUnitInsp processingUnit) {
        this.processingUnit = processingUnit;
    }

}
