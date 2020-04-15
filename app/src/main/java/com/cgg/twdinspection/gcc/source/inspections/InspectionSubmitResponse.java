package com.cgg.twdinspection.gcc.source.inspections;

import com.cgg.twdinspection.gcc.source.inspections.DrDepot.DrDepotInsp;
import com.cgg.twdinspection.gcc.source.inspections.MFPGodowns.MfpGodownsInsp;
import com.cgg.twdinspection.gcc.source.inspections.godown.DrGodownInsp;
import com.cgg.twdinspection.gcc.source.inspections.lpg.LPGIns;
import com.cgg.twdinspection.gcc.source.inspections.petrol_pump.PetrolPumpIns;
import com.cgg.twdinspection.gcc.source.inspections.processingUnit.PUnitInsp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InspectionSubmitResponse {

    @SerializedName("dr_godown")
    @Expose
    private DrGodownInsp drGodown;
    @SerializedName("dr_depot")
    @Expose
    private DrDepotInsp drDepot;
    @SerializedName("mfp_commodities")
    @Expose
    private MfpGodownsInsp mfpGodowns;
    @SerializedName("processing_unit")
    @Expose
    private PUnitInsp processingUnit;
    @SerializedName("petrol_pump")
    @Expose
    private PetrolPumpIns petrolPump;
    @SerializedName("lpg")
    @Expose
    private LPGIns lpg;


    public PetrolPumpIns getPetrolPump() {
        return petrolPump;
    }

    public void setPetrolPump(PetrolPumpIns petrolPump) {
        this.petrolPump = petrolPump;
    }

    public LPGIns getLpg() {
        return lpg;
    }

    public void setLpg(LPGIns lpg) {
        this.lpg = lpg;
    }

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
