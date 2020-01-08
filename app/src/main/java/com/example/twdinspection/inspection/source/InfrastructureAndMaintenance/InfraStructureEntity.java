package com.example.twdinspection.inspection.source.InfrastructureAndMaintenance;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "InfraStructureInfo")
public class InfraStructureEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    private int id;

    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String drinking_water_facility;

    @ColumnInfo()
    private String bigSchoolNameBoard;

    @ColumnInfo()
    private String ro_plant_woking;

    @ColumnInfo()
    private String drinking_water_source;

    @ColumnInfo()
    private String inverter_available;

    @ColumnInfo()
    private String inverterWorkingStatus;

    @ColumnInfo()
    private String lighting_facility;

    @ColumnInfo()
    private String electricity_wiring;

    @ColumnInfo()
    private String enough_fans;

    @ColumnInfo()
    private String ceilingfans_working;

    @ColumnInfo()
    private String ceilingfans_nonworking;

    @ColumnInfo()
    private String mountedfans_working;

    @ColumnInfo()
    private String mountedfans_nonworking;

    @ColumnInfo()
    private String dininghall_available;

    @ColumnInfo()
    private String separate_kitchen_room_available;

    @ColumnInfo()
    private String construct_kitchen_room;

    @ColumnInfo()
    private String is_it_in_good_condition;

    @ColumnInfo()
    private String repair_required;

    @ColumnInfo()
    private String how_many_buildings;

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
    //
    @ColumnInfo()
    private String sewage_raise_request;

    @ColumnInfo()
    private String drainage_functioning;

    @ColumnInfo()
    private String heater_available;

    @ColumnInfo()
    private String heater_workingStatus;
    //
    @ColumnInfo()
    private String additional_infra_required;

    @ColumnInfo()
    private String total_toilets;

    @ColumnInfo()
    private String total_bathrooms;

    @ColumnInfo()
    private String functioning_toilets;

    @ColumnInfo()
    private String functioning_bathrooms;

    @ColumnInfo()
    private String repairs_req_toilets;

    @ColumnInfo()
    private String repairs_req_bathrooms;

    @ColumnInfo()
    private String door_window_repairs;

    @ColumnInfo()
    private String painting;

    @ColumnInfo()
    private String color;

    public String getInverterWorkingStatus() {
        return inverterWorkingStatus;
    }

    public void setInverterWorkingStatus(String inverterWorkingStatus) {
        this.inverterWorkingStatus = inverterWorkingStatus;
    }

    public String getTotal_toilets() {
        return total_toilets;
    }

    public void setTotal_toilets(String total_toilets) {
        this.total_toilets = total_toilets;
    }

    public String getTotal_bathrooms() {
        return total_bathrooms;
    }

    public void setTotal_bathrooms(String total_bathrooms) {
        this.total_bathrooms = total_bathrooms;
    }

    public String getFunctioning_toilets() {
        return functioning_toilets;
    }

    public void setFunctioning_toilets(String functioning_toilets) {
        this.functioning_toilets = functioning_toilets;
    }

    public String getFunctioning_bathrooms() {
        return functioning_bathrooms;
    }

    public void setFunctioning_bathrooms(String functioning_bathrooms) {
        this.functioning_bathrooms = functioning_bathrooms;
    }

    public String getRepairs_req_toilets() {
        return repairs_req_toilets;
    }

    public void setRepairs_req_toilets(String repairs_req_toilets) {
        this.repairs_req_toilets = repairs_req_toilets;
    }

    public String getRepairs_req_bathrooms() {
        return repairs_req_bathrooms;
    }

    public void setRepairs_req_bathrooms(String repairs_req_bathrooms) {
        this.repairs_req_bathrooms = repairs_req_bathrooms;
    }

    public String getDoor_window_repairs() {
        return door_window_repairs;
    }

    public void setDoor_window_repairs(String door_window_repairs) {
        this.door_window_repairs = door_window_repairs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOfficer_id() {
        return officer_id;
    }

    public void setOfficer_id(String officer_id) {
        this.officer_id = officer_id;
    }

    public String getInspection_time() {
        return inspection_time;
    }

    public void setInspection_time(String inspection_time) {
        this.inspection_time = inspection_time;
    }

    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(String institute_id) {
        this.institute_id = institute_id;
    }

    public String getBigSchoolNameBoard() {
        return bigSchoolNameBoard;
    }

    public void setBigSchoolNameBoard(String bigSchoolNameBoard) {
        this.bigSchoolNameBoard = bigSchoolNameBoard;
    }

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

    public String getInverter_available() {
        return inverter_available;
    }

    public void setInverter_available(String inverter_available) {
        this.inverter_available = inverter_available;
    }

    public String getElectricity_wiring() {
        return electricity_wiring;
    }

    public void setElectricity_wiring(String electricity_wiring) {
        this.electricity_wiring = electricity_wiring;
    }

    public String getMountedfans_working() {
        return mountedfans_working;
    }

    public void setMountedfans_working(String mountedfans_working) {
        this.mountedfans_working = mountedfans_working;
    }

    public String getMountedfans_nonworking() {
        return mountedfans_nonworking;
    }

    public void setMountedfans_nonworking(String mountedfans_nonworking) {
        this.mountedfans_nonworking = mountedfans_nonworking;
    }

    public String getSeparate_kitchen_room_available() {
        return separate_kitchen_room_available;
    }

    public void setSeparate_kitchen_room_available(String separate_kitchen_room_available) {
        this.separate_kitchen_room_available = separate_kitchen_room_available;
    }

    public String getConstruct_kitchen_room() {
        return construct_kitchen_room;
    }

    public void setConstruct_kitchen_room(String construct_kitchen_room) {
        this.construct_kitchen_room = construct_kitchen_room;
    }

    public String getIs_it_in_good_condition() {
        return is_it_in_good_condition;
    }

    public void setIs_it_in_good_condition(String is_it_in_good_condition) {
        this.is_it_in_good_condition = is_it_in_good_condition;
    }

    public String getRepair_required() {
        return repair_required;
    }

    public void setRepair_required(String repair_required) {
        this.repair_required = repair_required;
    }

    public String getHow_many_buildings() {
        return how_many_buildings;
    }

    public void setHow_many_buildings(String how_many_buildings) {
        this.how_many_buildings = how_many_buildings;
    }

    public String getPainting() {
        return painting;
    }

    public void setPainting(String painting) {
        this.painting = painting;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
