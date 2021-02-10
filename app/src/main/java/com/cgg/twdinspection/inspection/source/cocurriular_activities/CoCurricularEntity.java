package com.cgg.twdinspection.inspection.source.cocurriular_activities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cgg.twdinspection.inspection.source.converters.PlantInfoConverter;
import com.cgg.twdinspection.inspection.source.converters.StudentAchConverter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@TypeConverters({StudentAchConverter.class, PlantInfoConverter.class})
@Entity(tableName = "cocurricular_info")
public class CoCurricularEntity {


    private int id;
    private String officer_id;
    private String inspection_time;
    @NotNull
    @PrimaryKey
    private String institute_id;

    private String sport_mat_received;
    private String stock_entry_register;
    private String game_sport_room_avail;
    private String pd_pft_name;
    private String pd_pft_contact;

    private String district_level;
    private String state_level;
    private String national_level;

    private List<StudAchievementEntity> studAchievementEntities = null;

    private String play_ground_avail;
    private String land_mea_value;
    private String plan_to_prc_land;

    private String ground_level_status;

    private String scouts_guides_impl_status;
    private String scout_guide_captain_name;
    private String scout_guide_captain_contact;

    private String ncc_impl_status;
    private String ncc_teacher_name;
    private String ncc_teacher_contact;
    private String ncc_teacher_battalion_num;

    private String smc_ele_status;
    private String smc_ele_chairman_name;
    private String smc_ele_chairman_contact;
    private String smc_ele_con_parent_meeting;
    private String smc_ele_resolution;

    private String kitchen_garden_status;
    private String kitchen_garden_types;
    private String kitchen_garden_in_charge_name;
    private String kitchen_garden_in_charge_contact;
    private String plants_cnt;

    private String stu_cou_ele_status;
    private String stu_com_display_status;
    private String reason;
    private String stu_cou_date;

    public String getPlants_cnt() {
        return plants_cnt;
    }

    public void setPlants_cnt(String plants_cnt) {
        this.plants_cnt = plants_cnt;
    }

    public String getSmc_ele_resolution() {
        return smc_ele_resolution;
    }

    public void setSmc_ele_resolution(String smc_ele_resolution) {
        this.smc_ele_resolution = smc_ele_resolution;
    }

    public String getDistrict_level() {
        return district_level;
    }

    public void setDistrict_level(String district_level) {
        this.district_level = district_level;
    }

    public String getState_level() {
        return state_level;
    }

    public void setState_level(String state_level) {
        this.state_level = state_level;
    }

    public String getNational_level() {
        return national_level;
    }

    public void setNational_level(String national_level) {
        this.national_level = national_level;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getSport_mat_received() {
        return sport_mat_received;
    }

    public void setSport_mat_received(String sport_mat_received) {
        this.sport_mat_received = sport_mat_received;
    }

    public String getStock_entry_register() {
        return stock_entry_register;
    }

    public void setStock_entry_register(String stock_entry_register) {
        this.stock_entry_register = stock_entry_register;
    }

    public String getGame_sport_room_avail() {
        return game_sport_room_avail;
    }

    public void setGame_sport_room_avail(String game_sport_room_avail) {
        this.game_sport_room_avail = game_sport_room_avail;
    }

    public String getPd_pft_name() {
        return pd_pft_name;
    }

    public void setPd_pft_name(String pd_pft_name) {
        this.pd_pft_name = pd_pft_name;
    }

    public String getPd_pft_contact() {
        return pd_pft_contact;
    }

    public void setPd_pft_contact(String pd_pft_contact) {
        this.pd_pft_contact = pd_pft_contact;
    }

    public List<StudAchievementEntity> getStudAchievementEntities() {
        return studAchievementEntities;
    }

    public void setStudAchievementEntities(List<StudAchievementEntity> studAchievementEntities) {
        this.studAchievementEntities = studAchievementEntities;
    }

    public String getPlay_ground_avail() {
        return play_ground_avail;
    }

    public void setPlay_ground_avail(String play_ground_avail) {
        this.play_ground_avail = play_ground_avail;
    }

    public String getLand_mea_value() {
        return land_mea_value;
    }

    public void setLand_mea_value(String land_mea_value) {
        this.land_mea_value = land_mea_value;
    }

    public String getPlan_to_prc_land() {
        return plan_to_prc_land;
    }

    public void setPlan_to_prc_land(String plan_to_prc_land) {
        this.plan_to_prc_land = plan_to_prc_land;
    }

    public String getGround_level_status() {
        return ground_level_status;
    }

    public void setGround_level_status(String ground_level_status) {
        this.ground_level_status = ground_level_status;
    }

    public String getScouts_guides_impl_status() {
        return scouts_guides_impl_status;
    }

    public void setScouts_guides_impl_status(String scouts_guides_impl_status) {
        this.scouts_guides_impl_status = scouts_guides_impl_status;
    }

    public String getScout_guide_captain_name() {
        return scout_guide_captain_name;
    }

    public void setScout_guide_captain_name(String scout_guide_captain_name) {
        this.scout_guide_captain_name = scout_guide_captain_name;
    }

    public String getScout_guide_captain_contact() {
        return scout_guide_captain_contact;
    }

    public void setScout_guide_captain_contact(String scout_guide_captain_contact) {
        this.scout_guide_captain_contact = scout_guide_captain_contact;
    }

    public String getNcc_impl_status() {
        return ncc_impl_status;
    }

    public void setNcc_impl_status(String ncc_impl_status) {
        this.ncc_impl_status = ncc_impl_status;
    }

    public String getNcc_teacher_name() {
        return ncc_teacher_name;
    }

    public void setNcc_teacher_name(String ncc_teacher_name) {
        this.ncc_teacher_name = ncc_teacher_name;
    }

    public String getNcc_teacher_contact() {
        return ncc_teacher_contact;
    }

    public void setNcc_teacher_contact(String ncc_teacher_contact) {
        this.ncc_teacher_contact = ncc_teacher_contact;
    }

    public String getNcc_teacher_battalion_num() {
        return ncc_teacher_battalion_num;
    }

    public void setNcc_teacher_battalion_num(String ncc_teacher_battalion_num) {
        this.ncc_teacher_battalion_num = ncc_teacher_battalion_num;
    }

    public String getSmc_ele_status() {
        return smc_ele_status;
    }

    public void setSmc_ele_status(String smc_ele_status) {
        this.smc_ele_status = smc_ele_status;
    }

    public String getSmc_ele_chairman_name() {
        return smc_ele_chairman_name;
    }

    public void setSmc_ele_chairman_name(String smc_ele_chairman_name) {
        this.smc_ele_chairman_name = smc_ele_chairman_name;
    }

    public String getSmc_ele_chairman_contact() {
        return smc_ele_chairman_contact;
    }

    public void setSmc_ele_chairman_contact(String smc_ele_chairman_contact) {
        this.smc_ele_chairman_contact = smc_ele_chairman_contact;
    }

    public String getSmc_ele_con_parent_meeting() {
        return smc_ele_con_parent_meeting;
    }

    public void setSmc_ele_con_parent_meeting(String smc_ele_con_parent_meeting) {
        this.smc_ele_con_parent_meeting = smc_ele_con_parent_meeting;
    }

    public String getKitchen_garden_status() {
        return kitchen_garden_status;
    }

    public void setKitchen_garden_status(String kitchen_garden_status) {
        this.kitchen_garden_status = kitchen_garden_status;
    }

    public String getKitchen_garden_types() {
        return kitchen_garden_types;
    }

    public void setKitchen_garden_types(String kitchen_garden_types) {
        this.kitchen_garden_types = kitchen_garden_types;
    }

    public String getKitchen_garden_in_charge_name() {
        return kitchen_garden_in_charge_name;
    }

    public void setKitchen_garden_in_charge_name(String kitchen_garden_in_charge_name) {
        this.kitchen_garden_in_charge_name = kitchen_garden_in_charge_name;
    }

    public String getKitchen_garden_in_charge_contact() {
        return kitchen_garden_in_charge_contact;
    }

    public void setKitchen_garden_in_charge_contact(String kitchen_garden_in_charge_contact) {
        this.kitchen_garden_in_charge_contact = kitchen_garden_in_charge_contact;
    }

    public String getStu_cou_ele_status() {
        return stu_cou_ele_status;
    }

    public void setStu_cou_ele_status(String stu_cou_ele_status) {
        this.stu_cou_ele_status = stu_cou_ele_status;
    }

    public String getStu_com_display_status() {
        return stu_com_display_status;
    }

    public void setStu_com_display_status(String stu_com_display_status) {
        this.stu_com_display_status = stu_com_display_status;
    }


    public String getStu_cou_date() {
        return stu_cou_date;
    }

    public void setStu_cou_date(String stu_cou_date) {
        this.stu_cou_date = stu_cou_date;
    }
}
