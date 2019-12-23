package com.example.twdinspection.inspection.source.EntitlementsDistribution;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class EntitlementsEntity {
    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String entitlements_provided;

    @ColumnInfo()
    private String dress_count;


    @ColumnInfo()
    private String hait_cut_complted;


    @ColumnInfo()
    private String last_haircut_date;

    @ColumnInfo()
    private String cosmetic_distributed;

    public String getEntitlements_provided() {
        return entitlements_provided;
    }

    public void setEntitlements_provided(String entitlements_provided) {
        this.entitlements_provided = entitlements_provided;
    }

    public String getDress_count() {
        return dress_count;
    }

    public void setDress_count(String dress_count) {
        this.dress_count = dress_count;
    }

    public String getHait_cut_complted() {
        return hait_cut_complted;
    }

    public void setHait_cut_complted(String hait_cut_complted) {
        this.hait_cut_complted = hait_cut_complted;
    }

    public String getLast_haircut_date() {
        return last_haircut_date;
    }

    public void setLast_haircut_date(String last_haircut_date) {
        this.last_haircut_date = last_haircut_date;
    }

    public String getCosmetic_distributed() {
        return cosmetic_distributed;
    }

    public void setCosmetic_distributed(String cosmetic_distributed) {
        this.cosmetic_distributed = cosmetic_distributed;
    }
}
