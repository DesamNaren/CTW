package com.example.twdinspection.schemes.source.bendetails;

public class BeneficiaryRequest {
    private int distId;
    private int mandalId;
    private int villageId;
    private String finYearId;

    public int getDistId() {
        return distId;
    }

    public void setDistId(int distId) {
        this.distId = distId;
    }

    public int getMandalId() {
        return mandalId;
    }

    public void setMandalId(int mandalId) {
        this.mandalId = mandalId;
    }

    public int getVillageId() {
        return villageId;
    }

    public void setVillageId(int villageId) {
        this.villageId = villageId;
    }

    public String getFinYearId() {
        return finYearId;
    }

    public void setFinYearId(String finYearId) {
        this.finYearId = finYearId;
    }
}
