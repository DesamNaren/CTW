package com.cgg.twdinspection.inspection.source.infra_maintenance;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "infrastructure_info")
public class InfraStructureEntity {

    @ColumnInfo()
    private int id;

    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @NotNull
    @PrimaryKey
    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String bigSchoolNameBoard;

    @ColumnInfo()
    private String drinking_water_facility;

    @ColumnInfo()
    private String drinking_water_source;

    @ColumnInfo()
    private String ro_plant_woking;

    @ColumnInfo()
    private String ro_plant_reason;

    @ColumnInfo()
    private String running_water_facility;

    @ColumnInfo()
    private String runningWater_source;


    @ColumnInfo()
    private String inverter_available;

    @ColumnInfo()
    private String inverterWorkingStatus;

    @ColumnInfo()
    private String electricity_wiring;

    @ColumnInfo()
    private String enough_fans;

    @ColumnInfo()
    private String ceilingfans_count;

    @ColumnInfo()
    private String ceilingfans_working;

    @ColumnInfo()
    private String ceilingfans_nonworking;

    @ColumnInfo()
    private String ceilingfans_required;

    @ColumnInfo()
    private String mountedfans_count;

    @ColumnInfo()
    private String mountedfans_working;

    @ColumnInfo()
    private String mountedfans_nonworking;

    @ColumnInfo()
    private String mountedfans_required;

    @ColumnInfo()
    private String lights_working;

    @ColumnInfo()
    private String lights_nonworking;

    @ColumnInfo()
    private String lights_required;

    public String getLights_working() {
        return lights_working;
    }

    public void setLights_working(String lights_working) {
        this.lights_working = lights_working;
    }

    public String getLights_nonworking() {
        return lights_nonworking;
    }

    public void setLights_nonworking(String lights_nonworking) {
        this.lights_nonworking = lights_nonworking;
    }

    public String getLights_required() {
        return lights_required;
    }

    public void setLights_required(String lights_required) {
        this.lights_required = lights_required;
    }

    @ColumnInfo()
    private String dininghall_available;

    @ColumnInfo()
    private String dininghall_used;

    @ColumnInfo()
    private String dininghall_add_req;

    @ColumnInfo()
    private String dininghall_avail_construction;

    @ColumnInfo()
    private String separate_kitchen_room_available;

    @ColumnInfo()
    private String construct_kitchen_room;

    @ColumnInfo()
    private String kitchen_good_condition;

    @ColumnInfo()
    private String kitchen_repair_required;

    @ColumnInfo()
    private String how_many_buildings;

    @ColumnInfo()
    private String transformer_available;

    @ColumnInfo()
    private String powerConnection_type;

    @ColumnInfo()
    private String individual_connection;

    @ColumnInfo()
    private String road_required;

    @ColumnInfo()
    private String compWall_required;

    @ColumnInfo()
    private String construction_part_type;

    @ColumnInfo()
    private String compWall_cnt;

    @ColumnInfo()
    private String cc_cameras;

    @ColumnInfo()
    private String steam_cooking;

    @ColumnInfo()
    private String bunker_beds;

    @ColumnInfo()
    private String bunker_beds_cnt;

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
    private String add_req;

    @ColumnInfo()
    private String add_class_required_cnt;

    @ColumnInfo()
    private String add_dining_required_cnt;

    @ColumnInfo()
    private String add_dormitory_required_cnt;

    @ColumnInfo()
    private String add_toilets_required_cnt;

    @ColumnInfo()
    private String add_bathrooms_required_cnt;

    public String getAdd_toilets_required_cnt() {
        return add_toilets_required_cnt;
    }

    public void setAdd_toilets_required_cnt(String add_toilets_required_cnt) {
        this.add_toilets_required_cnt = add_toilets_required_cnt;
    }

    public String getAdd_bathrooms_required_cnt() {
        return add_bathrooms_required_cnt;
    }

    public void setAdd_bathrooms_required_cnt(String add_bathrooms_required_cnt) {
        this.add_bathrooms_required_cnt = add_bathrooms_required_cnt;
    }

    public String getCc_cameras() {
        return cc_cameras;
    }

    public void setCc_cameras(String cc_cameras) {
        this.cc_cameras = cc_cameras;
    }

    public String getSteam_cooking() {
        return steam_cooking;
    }

    public void setSteam_cooking(String steam_cooking) {
        this.steam_cooking = steam_cooking;
    }

    public String getBunker_beds() {
        return bunker_beds;
    }

    public void setBunker_beds(String bunker_beds) {
        this.bunker_beds = bunker_beds;
    }

    public String getBunker_beds_cnt() {
        return bunker_beds_cnt;
    }

    public void setBunker_beds_cnt(String bunker_beds_cnt) {
        this.bunker_beds_cnt = bunker_beds_cnt;
    }

    public String getCeilingfans_required() {
        return ceilingfans_required;
    }

    public void setCeilingfans_required(String ceilingfans_required) {
        this.ceilingfans_required = ceilingfans_required;
    }

    public String getMountedfans_required() {
        return mountedfans_required;
    }

    public void setMountedfans_required(String mountedfans_required) {
        this.mountedfans_required = mountedfans_required;
    }

    public String getRunning_water_facility() {
        return running_water_facility;
    }

    public void setRunning_water_facility(String running_water_facility) {
        this.running_water_facility = running_water_facility;
    }

    public String getKitchen_good_condition() {
        return kitchen_good_condition;
    }

    public void setKitchen_good_condition(String kitchen_good_condition) {
        this.kitchen_good_condition = kitchen_good_condition;
    }


    public String getAdd_class_required_cnt() {
        return add_class_required_cnt;
    }

    public void setAdd_class_required_cnt(String add_class_required_cnt) {
        this.add_class_required_cnt = add_class_required_cnt;
    }

    public String getAdd_dining_required_cnt() {
        return add_dining_required_cnt;
    }

    public void setAdd_dining_required_cnt(String add_dining_required_cnt) {
        this.add_dining_required_cnt = add_dining_required_cnt;
    }


    public String getAdd_dormitory_required_cnt() {
        return add_dormitory_required_cnt;
    }

    public void setAdd_dormitory_required_cnt(String add_dormitory_required_cnt) {
        this.add_dormitory_required_cnt = add_dormitory_required_cnt;
    }

    public String getCeilingfans_count() {
        return ceilingfans_count;
    }

    public void setCeilingfans_count(String ceilingfans_count) {
        this.ceilingfans_count = ceilingfans_count;
    }

    public String getMountedfans_count() {
        return mountedfans_count;
    }

    public void setMountedfans_count(String mountedfans_count) {
        this.mountedfans_count = mountedfans_count;
    }

    public String getDininghall_avail_construction() {
        return dininghall_avail_construction;
    }

    public void setDininghall_avail_construction(String dininghall_avail_construction) {
        this.dininghall_avail_construction = dininghall_avail_construction;
    }

    public String getDininghall_used() {
        return dininghall_used;
    }

    public void setDininghall_used(String dininghall_used) {
        this.dininghall_used = dininghall_used;
    }

    public String getDininghall_add_req() {
        return dininghall_add_req;
    }

    public void setDininghall_add_req(String dininghall_add_req) {
        this.dininghall_add_req = dininghall_add_req;
    }

    public String getInverterWorkingStatus() {
        return inverterWorkingStatus;
    }

    public void setInverterWorkingStatus(String inverterWorkingStatus) {
        this.inverterWorkingStatus = inverterWorkingStatus;
    }

    public String getRo_plant_reason() {
        return ro_plant_reason;
    }

    public void setRo_plant_reason(String ro_plant_reason) {
        this.ro_plant_reason = ro_plant_reason;
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

    @NotNull
    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(@NotNull String institute_id) {
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

    public String getConstruction_part_type() {
        return construction_part_type;
    }

    public void setConstruction_part_type(String construction_part_type) {
        this.construction_part_type = construction_part_type;
    }

    public String getCompWall_cnt() {
        return compWall_cnt;
    }

    public void setCompWall_cnt(String compWall_cnt) {
        this.compWall_cnt = compWall_cnt;
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

    public String getAdd_req() {
        return add_req;
    }

    public void setAdd_req(String add_req) {
        this.add_req = add_req;
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
        return kitchen_good_condition;
    }

    public void setIs_it_in_good_condition(String kitchen_good_condition) {
        this.kitchen_good_condition = kitchen_good_condition;
    }

    public String getKitchen_repair_required() {
        return kitchen_repair_required;
    }

    public void setKitchen_repair_required(String kitchen_repair_required) {
        this.kitchen_repair_required = kitchen_repair_required;
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

}
