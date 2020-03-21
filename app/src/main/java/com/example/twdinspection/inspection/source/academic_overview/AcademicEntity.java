package com.example.twdinspection.inspection.source.academic_overview;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.twdinspection.inspection.source.converters.AcademicGradeConverter;
import com.example.twdinspection.inspection.source.converters.CallHealthConverter;
import com.example.twdinspection.inspection.source.converters.MedicalRecordsConverter;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalDetailsBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@TypeConverters({AcademicGradeConverter.class})
@Entity(tableName = "academic_info")
public class AcademicEntity {

    @ColumnInfo()
    private int id;

    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @NonNull
    @PrimaryKey
    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String highest_class_syllabus_completed;

    @ColumnInfo()
    private String plan_syll_comp_prepared;

    @ColumnInfo()
    private String strength_accomodated_asper_infrastructure;

    @ColumnInfo()
    private String staff_accomodated_asper_stud_strength;

    @ColumnInfo()
    private String plan_prepared;

    @ColumnInfo()
    private String highest_class_gradeA;

    @ColumnInfo()
    private String highest_class_gradeB;


    @ColumnInfo()
    private String highest_class_gradeC;


    @ColumnInfo()
    private String highest_class_total;

    @ColumnInfo()
    private String assessment_test_conducted;

    @ColumnInfo
    private String textbooks_rec;

    @ColumnInfo
    private String last_yr_ssc_percent;
    @ColumnInfo
    private String punadi_books_supplied;
    @ColumnInfo
    private String sufficient_books_supplied;
    @ColumnInfo
    private String punadiPrgmConducted;
    @ColumnInfo
    private String punadiPrgmReason;
    @ColumnInfo
    private String punadi2_testmarks_entered;
    @ColumnInfo
    private String punadi2TestmarksReason;
    @ColumnInfo
    private String kara_dipath_prgm_cond;
    @ColumnInfo
    private String karaDipathPrgmCondReason;
    @ColumnInfo
    private String labManuals_received;
    @ColumnInfo
    private String properly_using_manuals;
    @ColumnInfo
    private String labroom_available;
    @ColumnInfo
    private String labInchargeName;
    @ColumnInfo
    private String labMobileNo;
    @ColumnInfo
    private String lab_mat_entered_reg;
    @ColumnInfo
    private String lab_mat_entered_reg_reason;

    @ColumnInfo
    private String noOfBooks;
    @ColumnInfo
    private String nameLibraryIncharge;
    @ColumnInfo
    private String libraryMobileNo;
    @ColumnInfo
    private String maint_accession_reg;
    @ColumnInfo
    private String proper_light_fan;
    @ColumnInfo
    private String big_tv_rot_avail;

    @ColumnInfo
    private String TvRotWorkingStatus;
    @ColumnInfo
    private String tabs_stud_using_as_per_sched;
    @ColumnInfo
    private String punadi_books_req;

    public String getPunadi_books_req() {
        return punadi_books_req;
    }

    public void setPunadi_books_req(String punadi_books_req) {
        this.punadi_books_req = punadi_books_req;
    }

    @ColumnInfo(name = "grade_info")
    private List<AcademicGradeEntity> academicGradeEntities = null;

    public List<AcademicGradeEntity> getAcademicGradeEntities() {
        return academicGradeEntities;
    }

    public void setAcademicGradeEntities(List<AcademicGradeEntity> academicGradeEntities) {
        this.academicGradeEntities = academicGradeEntities;
    }

    public String getTabs_stud_using_as_per_sched() {
        return tabs_stud_using_as_per_sched;
    }

    public void setTabs_stud_using_as_per_sched(String tabs_stud_using_as_per_sched) {
        this.tabs_stud_using_as_per_sched = tabs_stud_using_as_per_sched;
    }

    public String getLab_mat_entered_reg_reason() {
        return lab_mat_entered_reg_reason;
    }

    public void setLab_mat_entered_reg_reason(String lab_mat_entered_reg_reason) {
        this.lab_mat_entered_reg_reason = lab_mat_entered_reg_reason;
    }

    @ColumnInfo
    private String mana_tv_lessons_shown;
    @ColumnInfo
    private String manaTvLessonsReason;
    @ColumnInfo
    private String manaTvInchargeName;
    @ColumnInfo
    private String manaTvMobileNo;

    @ColumnInfo
    private String comp_lab_avail;
    @ColumnInfo
    private String noOfComputersAvailable;
    @ColumnInfo
    private String compWorkingStatus;

    @ColumnInfo
    private String ict_instr_avail;
    @ColumnInfo
    private String nameIctInstr;
    @ColumnInfo
    private String mobNoIctInstr;
    @ColumnInfo
    private String timetable_disp;

    @ColumnInfo
    private String comp_syll_completed;
    @ColumnInfo
    private String comp_lab_cond;
    @ColumnInfo
    private String digital_content_used;

    @ColumnInfo
    public String eLearning_avail;

    @ColumnInfo
    private String volSchoolCoordName;

    @ColumnInfo
    private String volSchoolCoordMobNo;

    @ColumnInfo
    public String eLearningInchargeName;

    @ColumnInfo
    public String eLearningMobNum;
    @ColumnInfo
    private String separate_timetable_disp;
    @ColumnInfo
    private String tabs_supplied;
    @ColumnInfo
    private String noOfTabs;
    @ColumnInfo
    private String tabInchargeName;
    @ColumnInfo
    private String tabInchargeMblno;

    @ColumnInfo
    public String eLearning_stud_using_as_per_sched;



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

    public String getHighest_class_syllabus_completed() {
        return highest_class_syllabus_completed;
    }

    public void setHighest_class_syllabus_completed(String highest_class_syllabus_completed) {
        this.highest_class_syllabus_completed = highest_class_syllabus_completed;
    }

    public String getPlan_syll_comp_prepared() {
        return plan_syll_comp_prepared;
    }

    public void setPlan_syll_comp_prepared(String plan_syll_comp_prepared) {
        this.plan_syll_comp_prepared = plan_syll_comp_prepared;
    }

    public String getStrength_accomodated_asper_infrastructure() {
        return strength_accomodated_asper_infrastructure;
    }

    public void setStrength_accomodated_asper_infrastructure(String strength_accomodated_asper_infrastructure) {
        this.strength_accomodated_asper_infrastructure = strength_accomodated_asper_infrastructure;
    }

    public String getStaff_accomodated_asper_stud_strength() {
        return staff_accomodated_asper_stud_strength;
    }

    public void setStaff_accomodated_asper_stud_strength(String staff_accomodated_asper_stud_strength) {
        this.staff_accomodated_asper_stud_strength = staff_accomodated_asper_stud_strength;
    }

    public String getPlan_prepared() {
        return plan_prepared;
    }

    public void setPlan_prepared(String plan_prepared) {
        this.plan_prepared = plan_prepared;
    }

    public String getHighest_class_gradeA() {
        return highest_class_gradeA;
    }

    public void setHighest_class_gradeA(String highest_class_gradeA) {
        this.highest_class_gradeA = highest_class_gradeA;
    }

    public String getHighest_class_gradeB() {
        return highest_class_gradeB;
    }

    public void setHighest_class_gradeB(String highest_class_gradeB) {
        this.highest_class_gradeB = highest_class_gradeB;
    }

    public String getHighest_class_gradeC() {
        return highest_class_gradeC;
    }

    public void setHighest_class_gradeC(String highest_class_gradeC) {
        this.highest_class_gradeC = highest_class_gradeC;
    }

    public String getHighest_class_total() {
        return highest_class_total;
    }

    public void setHighest_class_total(String highest_class_total) {
        this.highest_class_total = highest_class_total;
    }

    public String getAssessment_test_conducted() {
        return assessment_test_conducted;
    }

    public void setAssessment_test_conducted(String assessment_test_conducted) {
        this.assessment_test_conducted = assessment_test_conducted;
    }

    public String getTextbooks_rec() {
        return textbooks_rec;
    }

    public void setTextbooks_rec(String textbooks_rec) {
        this.textbooks_rec = textbooks_rec;
    }

    public String getLast_yr_ssc_percent() {
        return last_yr_ssc_percent;
    }

    public void setLast_yr_ssc_percent(String last_yr_ssc_percent) {
        this.last_yr_ssc_percent = last_yr_ssc_percent;
    }

    public String getPunadi_books_supplied() {
        return punadi_books_supplied;
    }

    public void setPunadi_books_supplied(String punadi_books_supplied) {
        this.punadi_books_supplied = punadi_books_supplied;
    }

    public String getSufficient_books_supplied() {
        return sufficient_books_supplied;
    }

    public void setSufficient_books_supplied(String sufficient_books_supplied) {
        this.sufficient_books_supplied = sufficient_books_supplied;
    }

    public String getPunadiPrgmConducted() {
        return punadiPrgmConducted;
    }

    public void setPunadiPrgmConducted(String punadiPrgmConducted) {
        this.punadiPrgmConducted = punadiPrgmConducted;
    }

    public String getPunadiPrgmReason() {
        return punadiPrgmReason;
    }

    public void setPunadiPrgmReason(String punadiPrgmReason) {
        this.punadiPrgmReason = punadiPrgmReason;
    }

    public String getPunadi2_testmarks_entered() {
        return punadi2_testmarks_entered;
    }

    public void setPunadi2_testmarks_entered(String punadi2_testmarks_entered) {
        this.punadi2_testmarks_entered = punadi2_testmarks_entered;
    }

    public String getPunadi2TestmarksReason() {
        return punadi2TestmarksReason;
    }

    public void setPunadi2TestmarksReason(String punadi2TestmarksReason) {
        this.punadi2TestmarksReason = punadi2TestmarksReason;
    }

    public String getKara_dipath_prgm_cond() {
        return kara_dipath_prgm_cond;
    }

    public void setKara_dipath_prgm_cond(String kara_dipath_prgm_cond) {
        this.kara_dipath_prgm_cond = kara_dipath_prgm_cond;
    }

    public String getKaraDipathPrgmCondReason() {
        return karaDipathPrgmCondReason;
    }

    public void setKaraDipathPrgmCondReason(String karaDipathPrgmCondReason) {
        this.karaDipathPrgmCondReason = karaDipathPrgmCondReason;
    }

    public String getLabManuals_received() {
        return labManuals_received;
    }

    public void setLabManuals_received(String labManuals_received) {
        this.labManuals_received = labManuals_received;
    }

    public String getProperly_using_manuals() {
        return properly_using_manuals;
    }

    public void setProperly_using_manuals(String properly_using_manuals) {
        this.properly_using_manuals = properly_using_manuals;
    }

    public String getLabroom_available() {
        return labroom_available;
    }

    public void setLabroom_available(String labroom_available) {
        this.labroom_available = labroom_available;
    }

    public String getLabInchargeName() {
        return labInchargeName;
    }

    public void setLabInchargeName(String labInchargeName) {
        this.labInchargeName = labInchargeName;
    }

    public String getLabMobileNo() {
        return labMobileNo;
    }

    public void setLabMobileNo(String labMobileNo) {
        this.labMobileNo = labMobileNo;
    }

    public String getLab_mat_entered_reg() {
        return lab_mat_entered_reg;
    }

    public void setLab_mat_entered_reg(String lab_mat_entered_reg) {
        this.lab_mat_entered_reg = lab_mat_entered_reg;
    }

    public String getNoOfBooks() {
        return noOfBooks;
    }

    public void setNoOfBooks(String noOfBooks) {
        this.noOfBooks = noOfBooks;
    }

    public String getNameLibraryIncharge() {
        return nameLibraryIncharge;
    }

    public void setNameLibraryIncharge(String nameLibraryIncharge) {
        this.nameLibraryIncharge = nameLibraryIncharge;
    }

    public String getLibraryMobileNo() {
        return libraryMobileNo;
    }

    public void setLibraryMobileNo(String libraryMobileNo) {
        this.libraryMobileNo = libraryMobileNo;
    }

    public String getMaint_accession_reg() {
        return maint_accession_reg;
    }

    public void setMaint_accession_reg(String maint_accession_reg) {
        this.maint_accession_reg = maint_accession_reg;
    }

    public String getProper_light_fan() {
        return proper_light_fan;
    }

    public void setProper_light_fan(String proper_light_fan) {
        this.proper_light_fan = proper_light_fan;
    }

    public String getBig_tv_rot_avail() {
        return big_tv_rot_avail;
    }

    public void setBig_tv_rot_avail(String big_tv_rot_avail) {
        this.big_tv_rot_avail = big_tv_rot_avail;
    }

    public String getTvRotWorkingStatus() {
        return TvRotWorkingStatus;
    }

    public void setTvRotWorkingStatus(String tvRotWorkingStatus) {
        TvRotWorkingStatus = tvRotWorkingStatus;
    }

    public String getMana_tv_lessons_shown() {
        return mana_tv_lessons_shown;
    }

    public void setMana_tv_lessons_shown(String mana_tv_lessons_shown) {
        this.mana_tv_lessons_shown = mana_tv_lessons_shown;
    }

    public String getManaTvLessonsReason() {
        return manaTvLessonsReason;
    }

    public void setManaTvLessonsReason(String manaTvLessonsReason) {
        this.manaTvLessonsReason = manaTvLessonsReason;
    }

    public String getManaTvInchargeName() {
        return manaTvInchargeName;
    }

    public void setManaTvInchargeName(String manaTvInchargeName) {
        this.manaTvInchargeName = manaTvInchargeName;
    }

    public String getManaTvMobileNo() {
        return manaTvMobileNo;
    }

    public void setManaTvMobileNo(String manaTvMobileNo) {
        this.manaTvMobileNo = manaTvMobileNo;
    }

    public String getComp_lab_avail() {
        return comp_lab_avail;
    }

    public void setComp_lab_avail(String comp_lab_avail) {
        this.comp_lab_avail = comp_lab_avail;
    }

    public String getNoOfComputersAvailable() {
        return noOfComputersAvailable;
    }

    public void setNoOfComputersAvailable(String noOfComputersAvailable) {
        this.noOfComputersAvailable = noOfComputersAvailable;
    }

    public String getCompWorkingStatus() {
        return compWorkingStatus;
    }

    public void setCompWorkingStatus(String compWorkingStatus) {
        this.compWorkingStatus = compWorkingStatus;
    }

    public String getIct_instr_avail() {
        return ict_instr_avail;
    }

    public void setIct_instr_avail(String ict_instr_avail) {
        this.ict_instr_avail = ict_instr_avail;
    }

    public String getNameIctInstr() {
        return nameIctInstr;
    }

    public void setNameIctInstr(String nameIctInstr) {
        this.nameIctInstr = nameIctInstr;
    }

    public String getMobNoIctInstr() {
        return mobNoIctInstr;
    }

    public void setMobNoIctInstr(String mobNoIctInstr) {
        this.mobNoIctInstr = mobNoIctInstr;
    }

    public String getTimetable_disp() {
        return timetable_disp;
    }

    public void setTimetable_disp(String timetable_disp) {
        this.timetable_disp = timetable_disp;
    }

    public String getComp_syll_completed() {
        return comp_syll_completed;
    }

    public void setComp_syll_completed(String comp_syll_completed) {
        this.comp_syll_completed = comp_syll_completed;
    }

    public String getComp_lab_cond() {
        return comp_lab_cond;
    }

    public void setComp_lab_cond(String comp_lab_cond) {
        this.comp_lab_cond = comp_lab_cond;
    }

    public String getDigital_content_used() {
        return digital_content_used;
    }

    public void setDigital_content_used(String digital_content_used) {
        this.digital_content_used = digital_content_used;
    }


    public String getVolSchoolCoordName() {
        return volSchoolCoordName;
    }

    public void setVolSchoolCoordName(String volSchoolCoordName) {
        this.volSchoolCoordName = volSchoolCoordName;
    }

    public String getVolSchoolCoordMobNo() {
        return volSchoolCoordMobNo;
    }

    public void setVolSchoolCoordMobNo(String volSchoolCoordMobNo) {
        this.volSchoolCoordMobNo = volSchoolCoordMobNo;
    }

    public String geteLearningInchargeName() {
        return eLearningInchargeName;
    }

    public void seteLearningInchargeName(String eLearningInchargeName) {
        this.eLearningInchargeName = eLearningInchargeName;
    }



    public String getSeparate_timetable_disp() {
        return separate_timetable_disp;
    }

    public void setSeparate_timetable_disp(String separate_timetable_disp) {
        this.separate_timetable_disp = separate_timetable_disp;
    }

    public String getTabs_supplied() {
        return tabs_supplied;
    }

    public void setTabs_supplied(String tabs_supplied) {
        this.tabs_supplied = tabs_supplied;
    }

    public String getNoOfTabs() {
        return noOfTabs;
    }

    public void setNoOfTabs(String noOfTabs) {
        this.noOfTabs = noOfTabs;
    }

    public String getTabInchargeName() {
        return tabInchargeName;
    }

    public void setTabInchargeName(String tabInchargeName) {
        this.tabInchargeName = tabInchargeName;
    }

    public String getTabInchargeMblno() {
        return tabInchargeMblno;
    }

    public void setTabInchargeMblno(String tabInchargeMblno) {
        this.tabInchargeMblno = tabInchargeMblno;
    }

    public String geteLearning_avail() {
        return eLearning_avail;
    }

    public void seteLearning_avail(String eLearning_avail) {
        this.eLearning_avail = eLearning_avail;
    }

    public String geteLearningMobNum() {
        return eLearningMobNum;
    }

    public void seteLearningMobNum(String eLearningMobNum) {
        this.eLearningMobNum = eLearningMobNum;
    }

    public String geteLearning_stud_using_as_per_sched() {
        return eLearning_stud_using_as_per_sched;
    }

    public void seteLearning_stud_using_as_per_sched(String eLearning_stud_using_as_per_sched) {
        this.eLearning_stud_using_as_per_sched = eLearning_stud_using_as_per_sched;
    }
}
