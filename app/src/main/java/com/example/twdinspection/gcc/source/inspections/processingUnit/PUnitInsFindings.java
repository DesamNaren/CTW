package com.example.twdinspection.gcc.source.inspections.processingUnit;

import com.example.twdinspection.gcc.source.inspections.DrDepot.GeneralFindings;
import com.example.twdinspection.gcc.source.inspections.DrDepot.RegisterBookCertificates;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PUnitInsFindings {

    @SerializedName("processing_unit")
    @Expose
    private PUnitInsp processingUnit;

    public PUnitInsp getProcessingUnit() {
        return processingUnit;
    }

    public void setProcessingUnit(PUnitInsp processingUnit) {
        this.processingUnit = processingUnit;
    }

}

