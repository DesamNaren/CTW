package com.example.twdinspection.inspection.source.InfrastructureAndMaintenance;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class InfrastuctureEntity {
    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String drinking_water_facility;

    @ColumnInfo()
    private String ro_plant_woking;


    @ColumnInfo()
    private String drinking_water_source;


    @ColumnInfo()
    private String lighting_facility;

    @ColumnInfo()
    private String enough_fans;


    @ColumnInfo()
    private String ceilingfans_count;

  @ColumnInfo()
    private String ceilingfans_working;


    @ColumnInfo()
    private String ceilingfans_nonworking;


    @ColumnInfo()
    private String dininghall_available;


    @ColumnInfo()
    private String dininghall_used;


    @ColumnInfo()
    private String buildings_dilapidatedState;

    @ColumnInfo()
    private String transformer_available;

    @ColumnInfo()
    private String powerConnection_type;

    @ColumnInfo()
    private String individual_connection;

    @ColumnInfo()
    private String runningWater_source;

    @ColumnInfo()
    private String road_required;

    @ColumnInfo()
    private String compWall_required;

    @ColumnInfo()
    private String gate_required;

    @ColumnInfo()
    private String pathway_required;

    @ColumnInfo()
    private String sump_required;

    @ColumnInfo()
    private String sewage_allowed;

    @ColumnInfo()
    private String sewage_raise_request;

    @ColumnInfo()
    private String drainage_functioning;

    @ColumnInfo()
    private String heater_available;

    @ColumnInfo()
    private String heater_workingStatus;

    @ColumnInfo()
    private String additional_infra_required;

    public String getDrinking_water_facility() {
        return drinking_water_facility;
    }

    public void setDrinking_water_facility(String drinking_water_facility) {
        this.drinking_water_facility = drinking_water_facility;
    }

    public String getRo_plant_woking() {
        return ro_plant_woking;
    }

    public void setRo_plant_woking(String ro_plant_woking) {
        this.ro_plant_woking = ro_plant_woking;
    }

    public String getDrinking_water_source() {
        return drinking_water_source;
    }

    public void setDrinking_water_source(String drinking_water_source) {
        this.drinking_water_source = drinking_water_source;
    }

    public String getLighting_facility() {
        return lighting_facility;
    }

    public void setLighting_facility(String lighting_facility) {
        this.lighting_facility = lighting_facility;
    }

    public String getEnough_fans() {
        return enough_fans;
    }

    public void setEnough_fans(String enough_fans) {
        this.enough_fans = enough_fans;
    }

    public String getCeilingfans_count() {
        return ceilingfans_count;
    }

    public void setCeilingfans_count(String ceilingfans_count) {
        this.ceilingfans_count = ceilingfans_count;
    }

    public String getCeilingfans_working() {
        return ceilingfans_working;
    }

    public void setCeilingfans_working(String ceilingfans_working) {
        this.ceilingfans_working = ceilingfans_working;
    }

    public String getCeilingfans_nonworking() {
        return ceilingfans_nonworking;
    }

    public void setCeilingfans_nonworking(String ceilingfans_nonworking) {
        this.ceilingfans_nonworking = ceilingfans_nonworking;
    }

    public String getDininghall_available() {
        return dininghall_available;
    }

    public void setDininghall_available(String dininghall_available) {
        this.dininghall_available = dininghall_available;
    }

    public String getDininghall_used() {
        return dininghall_used;
    }

    public void setDininghall_used(String dininghall_used) {
        this.dininghall_used = dininghall_used;
    }

    public String getBuildings_dilapidatedState() {
        return buildings_dilapidatedState;
    }

    public void setBuildings_dilapidatedState(String buildings_dilapidatedState) {
        this.buildings_dilapidatedState = buildings_dilapidatedState;
    }

    public String getTransformer_available() {
        return transformer_available;
    }

    public void setTransformer_available(String transformer_available) {
        this.transformer_available = transformer_available;
    }

    public String getPowerConnection_type() {
        return powerConnection_type;
    }

    public void setPowerConnection_type(String powerConnection_type) {
        this.powerConnection_type = powerConnection_type;
    }

    public String getIndividual_connection() {
        return individual_connection;
    }

    public void setIndividual_connection(String individual_connection) {
        this.individual_connection = individual_connection;
    }

    public String getRunningWater_source() {
        return runningWater_source;
    }

    public void setRunningWater_source(String runningWater_source) {
        this.runningWater_source = runningWater_source;
    }

    public String getRoad_required() {
        return road_required;
    }

    public void setRoad_required(String road_required) {
        this.road_required = road_required;
    }

    public String getCompWall_required() {
        return compWall_required;
    }

    public void setCompWall_required(String compWall_required) {
        this.compWall_required = compWall_required;
    }

    public String getGate_required() {
        return gate_required;
    }

    public void setGate_required(String gate_required) {
        this.gate_required = gate_required;
    }

    public String getPathway_required() {
        return pathway_required;
    }

    public void setPathway_required(String pathway_required) {
        this.pathway_required = pathway_required;
    }

    public String getSump_required() {
        return sump_required;
    }

    public void setSump_required(String sump_required) {
        this.sump_required = sump_required;
    }

    public String getSewage_allowed() {
        return sewage_allowed;
    }

    public void setSewage_allowed(String sewage_allowed) {
        this.sewage_allowed = sewage_allowed;
    }

    public String getSewage_raise_request() {
        return sewage_raise_request;
    }

    public void setSewage_raise_request(String sewage_raise_request) {
        this.sewage_raise_request = sewage_raise_request;
    }

    public String getDrainage_functioning() {
        return drainage_functioning;
    }

    public void setDrainage_functioning(String drainage_functioning) {
        this.drainage_functioning = drainage_functioning;
    }

    public String getHeater_available() {
        return heater_available;
    }

    public void setHeater_available(String heater_available) {
        this.heater_available = heater_available;
    }

    public String getHeater_workingStatus() {
        return heater_workingStatus;
    }

    public void setHeater_workingStatus(String heater_workingStatus) {
        this.heater_workingStatus = heater_workingStatus;
    }

    public String getAdditional_infra_required() {
        return additional_infra_required;
    }

    public void setAdditional_infra_required(String additional_infra_required) {
        this.additional_infra_required = additional_infra_required;
    }
}
