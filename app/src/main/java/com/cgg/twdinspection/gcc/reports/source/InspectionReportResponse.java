package com.cgg.twdinspection.gcc.reports.source;

import com.cgg.twdinspection.gcc.source.inspections.processingUnit.PUnitInsp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InspectionReportResponse {

    @SerializedName("dr_godown")
    @Expose
    private DrGodownInspReport drGodown;
    @SerializedName("dr_depot")
    @Expose
    private DrDepotInspReport drDepot;
    @SerializedName("mfp_commodities")
    @Expose
    private MfpGodownsInspRep mfpGodowns;
    @SerializedName("processing_unit")
    @Expose
    private PUnitInsp processingUnit;
    @SerializedName("petrol_pump")
    @Expose
    private PetrolPumpInspRep petrolPump;

    @SerializedName("lpg")
    @Expose
    private LpgInspRep lpg;

    public DrGodownInspReport getDrGodown() {
        return drGodown;
    }

    public void setDrGodown(DrGodownInspReport drGodown) {
        this.drGodown = drGodown;
    }

    public DrDepotInspReport getDrDepot() {
        return drDepot;
    }

    public void setDrDepot(DrDepotInspReport drDepot) {
        this.drDepot = drDepot;
    }

    public MfpGodownsInspRep getMfpGodowns() {
        return mfpGodowns;
    }

    public void setMfpGodowns(MfpGodownsInspRep mfpGodowns) {
        this.mfpGodowns = mfpGodowns;
    }

    public PUnitInsp getProcessingUnit() {
        return processingUnit;
    }

    public void setProcessingUnit(PUnitInsp processingUnit) {
        this.processingUnit = processingUnit;
    }

    public PetrolPumpInspRep getPetrolPump() {
        return petrolPump;
    }

    public void setPetrolPump(PetrolPumpInspRep petrolPump) {
        this.petrolPump = petrolPump;
    }

    public LpgInspRep getLpg() {
        return lpg;
    }

    public void setLpg(LpgInspRep lpg) {
        this.lpg = lpg;
    }
}
