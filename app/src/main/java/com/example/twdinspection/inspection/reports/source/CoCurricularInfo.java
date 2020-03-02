package com.example.twdinspection.inspection.reports.source;

import com.example.twdinspection.inspection.source.cocurriularActivities.PlantsEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoCurricularInfo {

    @SerializedName("smc_ele_status")
    @Expose
    private String smcEleStatus;
    @SerializedName("stock_entry_register")
    @Expose
    private String stockEntryRegister;
    @SerializedName("kitchen_garden_status")
    @Expose
    private String kitchenGardenStatus;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("land_mea_value")
    @Expose
    private String landMeaValue;
    @SerializedName("stu_com_display_status")
    @Expose
    private String stuComDisplayStatus;
    @SerializedName("kitchen_garden_in_charge_contact")
    @Expose
    private String kitchenGardenInChargeContact;
    @SerializedName("kitchen_in_charge_event_name")
    @Expose
    private String kitchenInChargeEventName;
    @SerializedName("ncc_teacher_contact")
    @Expose
    private String nccTeacherContact;
    @SerializedName("kitchen_garden_in_charge_name")
    @Expose
    private String kitchenGardenInChargeName;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("harita_haram_status")
    @Expose
    private String haritaHaramStatus;
    @SerializedName("stu_cou_date")
    @Expose
    private String stuCouDate;
    @SerializedName("pd_pft_name")
    @Expose
    private String pdPftName;
    @SerializedName("smc_ele_chairman_name")
    @Expose
    private String smcEleChairmanName;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("plan_to_prc_land")
    @Expose
    private String planToPrcLand;
    @SerializedName("play_ground_avail")
    @Expose
    private String playGroundAvail;
    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("stu_cou_captain_name")
    @Expose
    private String stuCouCaptainName;
    @SerializedName("smc_ele_chairman_contact")
    @Expose
    private String smcEleChairmanContact;
    @SerializedName("ncc_impl_status")
    @Expose
    private String nccImplStatus;
    @SerializedName("ground_level_status")
    @Expose
    private String groundLevelStatus;
    @SerializedName("ncc_teacher_battalion_num")
    @Expose
    private String nccTeacherBattalionNum;
    @SerializedName("pd_pft_contact")
    @Expose
    private String pdPftContact;
    @SerializedName("scout_guide_captain_contact")
    @Expose
    private String scoutGuideCaptainContact;
    @SerializedName("ncc_teacher_name")
    @Expose
    private String nccTeacherName;
    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("kitchen_garden_types")
    @Expose
    private String kitchenGardenTypes;
    @SerializedName("game_sport_room_avail")
    @Expose
    private String gameSportRoomAvail;
    @SerializedName("sport_mat_received")
    @Expose
    private String sportMatReceived;
    @SerializedName("scouts_guides_impl_status")
    @Expose
    private String scoutsGuidesImplStatus;
    @SerializedName("stu_cou_ele_status")
    @Expose
    private String stuCouEleStatus;
    @SerializedName("smc_ele_con_parent_meeting")
    @Expose
    private String smcEleConParentMeeting;
    @SerializedName("scout_guide_captain_name")
    @Expose
    private String scoutGuideCaptainName;

    @SerializedName("plantsEntities")
    @Expose
    private List<PlantsEntity> plantsEntities;
    @SerializedName("studAchievementEntities")
    @Expose
    private List<StudAchievementEntity> studAchievementEntities;

    public List<PlantsEntity> getPlantsEntities() {
        return plantsEntities;
    }

    public void setPlantsEntities(List<PlantsEntity> plantsEntities) {
        this.plantsEntities = plantsEntities;
    }

    public List<StudAchievementEntity> getStudAchievementEntities() {
        return studAchievementEntities;
    }

    public void setStudAchievementEntities(List<StudAchievementEntity> studAchievementEntities) {
        this.studAchievementEntities = studAchievementEntities;
    }

    @SerializedName("studAchievementEntities")




    public String getSmcEleStatus() {
        return smcEleStatus;
    }

    public void setSmcEleStatus(String smcEleStatus) {
        this.smcEleStatus = smcEleStatus;
    }

    public String getStockEntryRegister() {
        return stockEntryRegister;
    }

    public void setStockEntryRegister(String stockEntryRegister) {
        this.stockEntryRegister = stockEntryRegister;
    }

    public String getKitchenGardenStatus() {
        return kitchenGardenStatus;
    }

    public void setKitchenGardenStatus(String kitchenGardenStatus) {
        this.kitchenGardenStatus = kitchenGardenStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLandMeaValue() {
        return landMeaValue;
    }

    public void setLandMeaValue(String landMeaValue) {
        this.landMeaValue = landMeaValue;
    }

    public String getStuComDisplayStatus() {
        return stuComDisplayStatus;
    }

    public void setStuComDisplayStatus(String stuComDisplayStatus) {
        this.stuComDisplayStatus = stuComDisplayStatus;
    }

    public String getKitchenGardenInChargeContact() {
        return kitchenGardenInChargeContact;
    }

    public void setKitchenGardenInChargeContact(String kitchenGardenInChargeContact) {
        this.kitchenGardenInChargeContact = kitchenGardenInChargeContact;
    }

    public String getKitchenInChargeEventName() {
        return kitchenInChargeEventName;
    }

    public void setKitchenInChargeEventName(String kitchenInChargeEventName) {
        this.kitchenInChargeEventName = kitchenInChargeEventName;
    }

    public String getNccTeacherContact() {
        return nccTeacherContact;
    }

    public void setNccTeacherContact(String nccTeacherContact) {
        this.nccTeacherContact = nccTeacherContact;
    }

    public String getKitchenGardenInChargeName() {
        return kitchenGardenInChargeName;
    }

    public void setKitchenGardenInChargeName(String kitchenGardenInChargeName) {
        this.kitchenGardenInChargeName = kitchenGardenInChargeName;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getHaritaHaramStatus() {
        return haritaHaramStatus;
    }

    public void setHaritaHaramStatus(String haritaHaramStatus) {
        this.haritaHaramStatus = haritaHaramStatus;
    }

    public String getStuCouDate() {
        return stuCouDate;
    }

    public void setStuCouDate(String stuCouDate) {
        this.stuCouDate = stuCouDate;
    }

    public String getPdPftName() {
        return pdPftName;
    }

    public void setPdPftName(String pdPftName) {
        this.pdPftName = pdPftName;
    }

    public String getSmcEleChairmanName() {
        return smcEleChairmanName;
    }

    public void setSmcEleChairmanName(String smcEleChairmanName) {
        this.smcEleChairmanName = smcEleChairmanName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanToPrcLand() {
        return planToPrcLand;
    }

    public void setPlanToPrcLand(String planToPrcLand) {
        this.planToPrcLand = planToPrcLand;
    }

    public String getPlayGroundAvail() {
        return playGroundAvail;
    }

    public void setPlayGroundAvail(String playGroundAvail) {
        this.playGroundAvail = playGroundAvail;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getStuCouCaptainName() {
        return stuCouCaptainName;
    }

    public void setStuCouCaptainName(String stuCouCaptainName) {
        this.stuCouCaptainName = stuCouCaptainName;
    }

    public String getSmcEleChairmanContact() {
        return smcEleChairmanContact;
    }

    public void setSmcEleChairmanContact(String smcEleChairmanContact) {
        this.smcEleChairmanContact = smcEleChairmanContact;
    }

    public String getNccImplStatus() {
        return nccImplStatus;
    }

    public void setNccImplStatus(String nccImplStatus) {
        this.nccImplStatus = nccImplStatus;
    }

    public String getGroundLevelStatus() {
        return groundLevelStatus;
    }

    public void setGroundLevelStatus(String groundLevelStatus) {
        this.groundLevelStatus = groundLevelStatus;
    }

    public String getNccTeacherBattalionNum() {
        return nccTeacherBattalionNum;
    }

    public void setNccTeacherBattalionNum(String nccTeacherBattalionNum) {
        this.nccTeacherBattalionNum = nccTeacherBattalionNum;
    }

    public String getPdPftContact() {
        return pdPftContact;
    }

    public void setPdPftContact(String pdPftContact) {
        this.pdPftContact = pdPftContact;
    }

    public String getScoutGuideCaptainContact() {
        return scoutGuideCaptainContact;
    }

    public void setScoutGuideCaptainContact(String scoutGuideCaptainContact) {
        this.scoutGuideCaptainContact = scoutGuideCaptainContact;
    }

    public String getNccTeacherName() {
        return nccTeacherName;
    }

    public void setNccTeacherName(String nccTeacherName) {
        this.nccTeacherName = nccTeacherName;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getKitchenGardenTypes() {
        return kitchenGardenTypes;
    }

    public void setKitchenGardenTypes(String kitchenGardenTypes) {
        this.kitchenGardenTypes = kitchenGardenTypes;
    }

    public String getGameSportRoomAvail() {
        return gameSportRoomAvail;
    }

    public void setGameSportRoomAvail(String gameSportRoomAvail) {
        this.gameSportRoomAvail = gameSportRoomAvail;
    }

    public String getSportMatReceived() {
        return sportMatReceived;
    }

    public void setSportMatReceived(String sportMatReceived) {
        this.sportMatReceived = sportMatReceived;
    }

    public String getScoutsGuidesImplStatus() {
        return scoutsGuidesImplStatus;
    }

    public void setScoutsGuidesImplStatus(String scoutsGuidesImplStatus) {
        this.scoutsGuidesImplStatus = scoutsGuidesImplStatus;
    }

    public String getStuCouEleStatus() {
        return stuCouEleStatus;
    }

    public void setStuCouEleStatus(String stuCouEleStatus) {
        this.stuCouEleStatus = stuCouEleStatus;
    }

    public String getSmcEleConParentMeeting() {
        return smcEleConParentMeeting;
    }

    public void setSmcEleConParentMeeting(String smcEleConParentMeeting) {
        this.smcEleConParentMeeting = smcEleConParentMeeting;
    }

    public String getScoutGuideCaptainName() {
        return scoutGuideCaptainName;
    }

    public void setScoutGuideCaptainName(String scoutGuideCaptainName) {
        this.scoutGuideCaptainName = scoutGuideCaptainName;
    }

}
