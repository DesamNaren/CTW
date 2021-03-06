package com.cgg.twdinspection.inspection.reports.source;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InfraMaintenance {
    @SerializedName("lights_working")
    private String lights_working;

    @SerializedName("lights_nonworking")
    private String lights_nonworking;

    @SerializedName("lights_required")
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


    @SerializedName("bigSchoolNameBoard")
    @Expose
    private String bigSchoolNameBoard;
    @SerializedName("pathway_required")
    @Expose
    private String pathwayRequired;
    @SerializedName("heater_workingStatus")
    @Expose
    private String heaterWorkingStatus;
    @SerializedName("gate_required")
    @Expose
    private String gateRequired;
    @SerializedName("total_toilets")
    @Expose
    private String totalToilets;
    @SerializedName("heater_available")
    @Expose
    private String heaterAvailable;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("compWall_required")
    @Expose
    private String compWallRequired;
    @SerializedName("construction_part_type")
    @Expose
    private String constructionPartType;
    @SerializedName("compWall_cnt")
    @Expose
    private String compWall_cnt;
    @SerializedName("transformer_available")
    @Expose
    private String transformerAvailable;
    @SerializedName("add_dormitory_required_cnt")
    @Expose
    private String addDormitoryRequiredCnt;
    @SerializedName("add_req")
    @Expose
    private String addReq;
    @SerializedName("separate_kitchen_room_available")
    @Expose
    private String separateKitchenRoomAvailable;
    @SerializedName("add_toilets_required_cnt")
    @Expose
    private String addToiletsRequiredCnt;
    @SerializedName("functioning_bathrooms")
    @Expose
    private String functioningBathrooms;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("kitchen_good_condition")
    @Expose
    private String kitchenGoodCondition;
    @SerializedName("sump_required")
    @Expose
    private String sumpRequired;
    @SerializedName("powerConnection_type")
    @Expose
    private String powerConnectionType;
    @SerializedName("door_window_repairs")
    @Expose
    private String doorWindowRepairs;
    @SerializedName("cc_cameras")
    @Expose
    private String ccCameras;
    @SerializedName("dininghall_available")
    @Expose
    private String dininghallAvailable;
    @SerializedName("dininghall_add_req")
    @Expose
    private String dininghall_add_req;
    @SerializedName("dininghall_avail_construction")
    @Expose
    private String dininghall_avail_construction;
    @SerializedName("add_bathrooms_required_cnt")
    @Expose
    private String addBathroomsRequiredCnt;
    @SerializedName("drinking_water_facility")
    @Expose
    private String drinkingWaterFacility;
    @SerializedName("ceilingfans_working")
    @Expose
    private String ceilingfansWorking;
    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("drinking_water_source")
    @Expose
    private String drinkingWaterSource;
    @SerializedName("sewage_allowed")
    @Expose
    private String sewageAllowed;
    @SerializedName("mountedfans_required")
    @Expose
    private String mountedfansRequired;
    @SerializedName("construct_kitchen_room")
    @Expose
    private String constructKitchenRoom;
    @SerializedName("inverter_available")
    @Expose
    private String inverterAvailable;
    @SerializedName("repairs_req_bathrooms")
    @Expose
    private String repairsReqBathrooms;
    @SerializedName("ro_plant_woking")
    @Expose
    private String roPlantWoking;
    @SerializedName("mountedfans_working")
    @Expose
    private String mountedfansWorking;
    @SerializedName("electricity_wiring")
    @Expose
    private String electricityWiring;
    @SerializedName("ceilingfans_required")
    @Expose
    private String ceilingfansRequired;
    @SerializedName("lighting_facility")
    @Expose
    private String lightingFacility;
    @SerializedName("enough_fans")
    @Expose
    private String enoughFans;
    @SerializedName("sewage_raise_request")
    @Expose
    private String sewageRaiseRequest;
    @SerializedName("dininghall_used")
    @Expose
    private String dininghallUsed;
    @SerializedName("individual_connection")
    @Expose
    private String individualConnection;
    @SerializedName("kitchen_repair_required")
    @Expose
    private String kitchenRepairRequired;
    @SerializedName("how_many_buildings")
    @Expose
    private String howManyBuildings;
    @SerializedName("running_water_facility")
    @Expose
    private String runningWaterFacility;
    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("ceilingfans_nonworking")
    @Expose
    private String ceilingfansNonworking;
    @SerializedName("total_bathrooms")
    @Expose
    private String totalBathrooms;
    @SerializedName("drainage_functioning")
    @Expose
    private String drainageFunctioning;
    @SerializedName("add_dining_required_cnt")
    @Expose
    private String addDiningRequiredCnt;
    @SerializedName("inverterWorkingStatus")
    @Expose
    private String inverterWorkingStatus;
    @SerializedName("runningWater_source")
    @Expose
    private String runningWaterSource;
    @SerializedName("mountedfans_nonworking")
    @Expose
    private String mountedfansNonworking;
    @SerializedName("road_required")
    @Expose
    private String roadRequired;
    @SerializedName("painting")
    @Expose
    private String painting;
    @SerializedName("ro_plant_reason")
    @Expose
    private String roPlantReason;
    @SerializedName("steam_cooking")
    @Expose
    private String steamCooking;
    @SerializedName("repairs_req_toilets")
    @Expose
    private String repairsReqToilets;
    @SerializedName("add_class_required_cnt")
    @Expose
    private String addClassRequiredCnt;
    @SerializedName("functioning_toilets")
    @Expose
    private String functioningToilets;
    @SerializedName("bunker_beds")
    @Expose
    private String bunkerBeds;
    @SerializedName("bunker_beds_cnt")
    @Expose
    private String bunker_beds_cnt;

    public String getBigSchoolNameBoard() {
        return bigSchoolNameBoard;
    }

    public void setBigSchoolNameBoard(String bigSchoolNameBoard) {
        this.bigSchoolNameBoard = bigSchoolNameBoard;
    }

    public String getPathwayRequired() {
        return pathwayRequired;
    }

    public void setPathwayRequired(String pathwayRequired) {
        this.pathwayRequired = pathwayRequired;
    }

    public String getHeaterWorkingStatus() {
        return heaterWorkingStatus;
    }

    public void setHeaterWorkingStatus(String heaterWorkingStatus) {
        this.heaterWorkingStatus = heaterWorkingStatus;
    }

    public String getDininghall_add_req() {
        return dininghall_add_req;
    }

    public void setDininghall_add_req(String dininghall_add_req) {
        this.dininghall_add_req = dininghall_add_req;
    }

    public String getDininghall_avail_construction() {
        return dininghall_avail_construction;
    }

    public void setDininghall_avail_construction(String dininghall_avail_construction) {
        this.dininghall_avail_construction = dininghall_avail_construction;
    }

    public String getGateRequired() {
        return gateRequired;
    }

    public void setGateRequired(String gateRequired) {
        this.gateRequired = gateRequired;
    }

    public String getTotalToilets() {
        return totalToilets;
    }

    public void setTotalToilets(String totalToilets) {
        this.totalToilets = totalToilets;
    }

    public String getHeaterAvailable() {
        return heaterAvailable;
    }

    public void setHeaterAvailable(String heaterAvailable) {
        this.heaterAvailable = heaterAvailable;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getCompWallRequired() {
        return compWallRequired;
    }

    public void setCompWallRequired(String compWallRequired) {
        this.compWallRequired = compWallRequired;
    }

    public String getConstructionPartType() {
        return constructionPartType;
    }

    public void setConstructionPartType(String constructionPartType) {
        this.constructionPartType = constructionPartType;
    }

    public String getCompWall_cnt() {
        return compWall_cnt;
    }

    public void setCompWall_cnt(String compWall_cnt) {
        this.compWall_cnt = compWall_cnt;
    }

    public String getTransformerAvailable() {
        return transformerAvailable;
    }

    public void setTransformerAvailable(String transformerAvailable) {
        this.transformerAvailable = transformerAvailable;
    }

    public String getAddDormitoryRequiredCnt() {
        return addDormitoryRequiredCnt;
    }

    public void setAddDormitoryRequiredCnt(String addDormitoryRequiredCnt) {
        this.addDormitoryRequiredCnt = addDormitoryRequiredCnt;
    }

    public String getAddReq() {
        return addReq;
    }

    public void setAddReq(String addReq) {
        this.addReq = addReq;
    }

    public String getSeparateKitchenRoomAvailable() {
        return separateKitchenRoomAvailable;
    }

    public void setSeparateKitchenRoomAvailable(String separateKitchenRoomAvailable) {
        this.separateKitchenRoomAvailable = separateKitchenRoomAvailable;
    }

    public String getAddToiletsRequiredCnt() {
        return addToiletsRequiredCnt;
    }

    public void setAddToiletsRequiredCnt(String addToiletsRequiredCnt) {
        this.addToiletsRequiredCnt = addToiletsRequiredCnt;
    }

    public String getFunctioningBathrooms() {
        return functioningBathrooms;
    }

    public void setFunctioningBathrooms(String functioningBathrooms) {
        this.functioningBathrooms = functioningBathrooms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKitchenGoodCondition() {
        return kitchenGoodCondition;
    }

    public void setKitchenGoodCondition(String kitchenGoodCondition) {
        this.kitchenGoodCondition = kitchenGoodCondition;
    }

    public String getSumpRequired() {
        return sumpRequired;
    }

    public void setSumpRequired(String sumpRequired) {
        this.sumpRequired = sumpRequired;
    }

    public String getPowerConnectionType() {
        return powerConnectionType;
    }

    public void setPowerConnectionType(String powerConnectionType) {
        this.powerConnectionType = powerConnectionType;
    }

    public String getDoorWindowRepairs() {
        return doorWindowRepairs;
    }

    public void setDoorWindowRepairs(String doorWindowRepairs) {
        this.doorWindowRepairs = doorWindowRepairs;
    }

    public String getCcCameras() {
        return ccCameras;
    }

    public void setCcCameras(String ccCameras) {
        this.ccCameras = ccCameras;
    }

    public String getDininghallAvailable() {
        return dininghallAvailable;
    }

    public void setDininghallAvailable(String dininghallAvailable) {
        this.dininghallAvailable = dininghallAvailable;
    }

    public String getAddBathroomsRequiredCnt() {
        return addBathroomsRequiredCnt;
    }

    public void setAddBathroomsRequiredCnt(String addBathroomsRequiredCnt) {
        this.addBathroomsRequiredCnt = addBathroomsRequiredCnt;
    }

    public String getDrinkingWaterFacility() {
        return drinkingWaterFacility;
    }

    public void setDrinkingWaterFacility(String drinkingWaterFacility) {
        this.drinkingWaterFacility = drinkingWaterFacility;
    }

    public String getCeilingfansWorking() {
        return ceilingfansWorking;
    }

    public void setCeilingfansWorking(String ceilingfansWorking) {
        this.ceilingfansWorking = ceilingfansWorking;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getDrinkingWaterSource() {
        return drinkingWaterSource;
    }

    public void setDrinkingWaterSource(String drinkingWaterSource) {
        this.drinkingWaterSource = drinkingWaterSource;
    }

    public String getSewageAllowed() {
        return sewageAllowed;
    }

    public void setSewageAllowed(String sewageAllowed) {
        this.sewageAllowed = sewageAllowed;
    }

    public String getMountedfansRequired() {
        return mountedfansRequired;
    }

    public void setMountedfansRequired(String mountedfansRequired) {
        this.mountedfansRequired = mountedfansRequired;
    }

    public String getConstructKitchenRoom() {
        return constructKitchenRoom;
    }

    public void setConstructKitchenRoom(String constructKitchenRoom) {
        this.constructKitchenRoom = constructKitchenRoom;
    }

    public String getInverterAvailable() {
        return inverterAvailable;
    }

    public void setInverterAvailable(String inverterAvailable) {
        this.inverterAvailable = inverterAvailable;
    }

    public String getRepairsReqBathrooms() {
        return repairsReqBathrooms;
    }

    public void setRepairsReqBathrooms(String repairsReqBathrooms) {
        this.repairsReqBathrooms = repairsReqBathrooms;
    }

    public String getRoPlantWoking() {
        return roPlantWoking;
    }

    public void setRoPlantWoking(String roPlantWoking) {
        this.roPlantWoking = roPlantWoking;
    }

    public String getMountedfansWorking() {
        return mountedfansWorking;
    }

    public void setMountedfansWorking(String mountedfansWorking) {
        this.mountedfansWorking = mountedfansWorking;
    }

    public String getElectricityWiring() {
        return electricityWiring;
    }

    public void setElectricityWiring(String electricityWiring) {
        this.electricityWiring = electricityWiring;
    }

    public String getCeilingfansRequired() {
        return ceilingfansRequired;
    }

    public void setCeilingfansRequired(String ceilingfansRequired) {
        this.ceilingfansRequired = ceilingfansRequired;
    }

    public String getLightingFacility() {
        return lightingFacility;
    }

    public void setLightingFacility(String lightingFacility) {
        this.lightingFacility = lightingFacility;
    }

    public String getEnoughFans() {
        return enoughFans;
    }

    public void setEnoughFans(String enoughFans) {
        this.enoughFans = enoughFans;
    }

    public String getSewageRaiseRequest() {
        return sewageRaiseRequest;
    }

    public void setSewageRaiseRequest(String sewageRaiseRequest) {
        this.sewageRaiseRequest = sewageRaiseRequest;
    }

    public String getDininghallUsed() {
        return dininghallUsed;
    }

    public void setDininghallUsed(String dininghallUsed) {
        this.dininghallUsed = dininghallUsed;
    }

    public String getIndividualConnection() {
        return individualConnection;
    }

    public void setIndividualConnection(String individualConnection) {
        this.individualConnection = individualConnection;
    }

    public String getKitchenRepairRequired() {
        return kitchenRepairRequired;
    }

    public void setKitchenRepairRequired(String kitchenRepairRequired) {
        this.kitchenRepairRequired = kitchenRepairRequired;
    }

    public String getHowManyBuildings() {
        return howManyBuildings;
    }

    public void setHowManyBuildings(String howManyBuildings) {
        this.howManyBuildings = howManyBuildings;
    }

    public String getRunningWaterFacility() {
        return runningWaterFacility;
    }

    public void setRunningWaterFacility(String runningWaterFacility) {
        this.runningWaterFacility = runningWaterFacility;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getCeilingfansNonworking() {
        return ceilingfansNonworking;
    }

    public void setCeilingfansNonworking(String ceilingfansNonworking) {
        this.ceilingfansNonworking = ceilingfansNonworking;
    }

    public String getTotalBathrooms() {
        return totalBathrooms;
    }

    public void setTotalBathrooms(String totalBathrooms) {
        this.totalBathrooms = totalBathrooms;
    }

    public String getDrainageFunctioning() {
        return drainageFunctioning;
    }

    public void setDrainageFunctioning(String drainageFunctioning) {
        this.drainageFunctioning = drainageFunctioning;
    }

    public String getAddDiningRequiredCnt() {
        return addDiningRequiredCnt;
    }

    public void setAddDiningRequiredCnt(String addDiningRequiredCnt) {
        this.addDiningRequiredCnt = addDiningRequiredCnt;
    }

    public String getInverterWorkingStatus() {
        return inverterWorkingStatus;
    }

    public void setInverterWorkingStatus(String inverterWorkingStatus) {
        this.inverterWorkingStatus = inverterWorkingStatus;
    }

    public String getRunningWaterSource() {
        return runningWaterSource;
    }

    public void setRunningWaterSource(String runningWaterSource) {
        this.runningWaterSource = runningWaterSource;
    }

    public String getMountedfansNonworking() {
        return mountedfansNonworking;
    }

    public void setMountedfansNonworking(String mountedfansNonworking) {
        this.mountedfansNonworking = mountedfansNonworking;
    }

    public String getRoadRequired() {
        return roadRequired;
    }

    public void setRoadRequired(String roadRequired) {
        this.roadRequired = roadRequired;
    }

    public String getPainting() {
        return painting;
    }

    public void setPainting(String painting) {
        this.painting = painting;
    }

    public String getRoPlantReason() {
        return roPlantReason;
    }

    public void setRoPlantReason(String roPlantReason) {
        this.roPlantReason = roPlantReason;
    }

    public String getSteamCooking() {
        return steamCooking;
    }

    public void setSteamCooking(String steamCooking) {
        this.steamCooking = steamCooking;
    }

    public String getRepairsReqToilets() {
        return repairsReqToilets;
    }

    public void setRepairsReqToilets(String repairsReqToilets) {
        this.repairsReqToilets = repairsReqToilets;
    }

    public String getAddClassRequiredCnt() {
        return addClassRequiredCnt;
    }

    public void setAddClassRequiredCnt(String addClassRequiredCnt) {
        this.addClassRequiredCnt = addClassRequiredCnt;
    }

    public String getFunctioningToilets() {
        return functioningToilets;
    }

    public void setFunctioningToilets(String functioningToilets) {
        this.functioningToilets = functioningToilets;
    }

    public String getBunkerBeds() {
        return bunkerBeds;
    }

    public void setBunkerBeds(String bunkerBeds) {
        this.bunkerBeds = bunkerBeds;
    }

    public String getBunker_beds_cnt() {
        return bunker_beds_cnt;
    }

    public void setBunker_beds_cnt(String bunker_beds_cnt) {
        this.bunker_beds_cnt = bunker_beds_cnt;
    }
}
