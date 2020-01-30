package com.example.twdinspection.inspection.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityAcademicBinding;
import com.example.twdinspection.inspection.interfaces.SaveListener;
import com.example.twdinspection.inspection.source.AcademicOverview.AcademicEntity;
import com.example.twdinspection.inspection.viewmodel.AcademicCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.AcademicViewModel;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.google.android.material.snackbar.Snackbar;

public class AcademicActivity extends BaseActivity implements SaveListener {
    ActivityAcademicBinding binding;
    AcademicViewModel academicViewModel;
    AcademicEntity AcademicEntity;
    String highest_class_syllabus_completed, strength_accomodated_asper_infrastructure, staff_accomodated_asper_stud_strength,
            plan_prepared, textbooks_rec, assessment_test_conducted, punadiPrgmConducted, punadi2_testmarks_entered,
            kara_dipath_prgm_cond, labManuals_received, labroom_available, lab_mat_entered_reg, library_room_available,
            big_tv_rot_avail, mana_tv_lessons_shown, comp_lab_avail, ict_instr_avail, eLearning_avail, showing_stud, tabs_supplied,
            punadi_books_supplied, properly_using_manuals, plan_syll_comp_prepared, sufficient_books_supplied;
    String highestClassGradeA, highestClassGradeB, highestClassGradeC, highestClassGradeTotal, last_yr_ssc_percent,
            punadiPrgmReason, punadi2TestmarksReason, karaDipathPrgmCondReason, labName, labInchargeName, labMobileNo,
            noOfBooks, nameLibraryIncharge, libraryMobileNo, matEnterRegReason, TvRotWorkingStatus,
            maint_accession_reg, proper_light_fan, manaTvLessonsReason, manaTvInchargeName, manaTvMobileNo,
            noOfComputersAvailable, compWorkingStatus, workingStatusProjector, nameIctInstr, mobNoIctInstr,
            timetable_disp, comp_syll_completed, comp_lab_cond, digital_content_used, noOfTabs, tabInchargeName, tabInchargeMblno,
            stud_using_as_per_sched, tabs_timetable_disp, volSchoolCoordName, volSchoolCoordMobNo, eLearningInchrgName,
            eLearningInchrgMobileNo, separate_timetable_disp;
    private InstMainViewModel instMainViewModel;
    private SharedPreferences sharedPreferences;
    private String instId, officerId, insTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_academic, getString(R.string.title_academic));
        instMainViewModel = new InstMainViewModel(getApplication());

        academicViewModel = ViewModelProviders.of(AcademicActivity.this,
                new AcademicCustomViewModel(binding, this, getApplication())).get(AcademicViewModel.class);
        binding.setViewModel(academicViewModel);

        sharedPreferences = TWDApplication.get(this).getPreferences();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        insTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");

        binding.rgHighestClassSyllabusCompleted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHighestClassSyllabusCompleted.getCheckedRadioButtonId();
                if (selctedItem == R.id.highest_class_syllabus_completed_yes) {
                    highest_class_syllabus_completed = AppConstants.Yes;
                    binding.llPlanCompSyll.setVisibility(View.GONE);
                } else if (selctedItem == R.id.highest_class_syllabus_completed_no) {
                    binding.llPlanCompSyll.setVisibility(View.VISIBLE);
                    highest_class_syllabus_completed = AppConstants.No;
                } else {
                    binding.llPlanCompSyll.setVisibility(View.GONE);
                    highest_class_syllabus_completed = null;
                }
            }
        });
        binding.rgPlanSyllCompPrepared.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPlanSyllCompPrepared.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_plan_syll_comp_prepared_yes) {
                    plan_syll_comp_prepared = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_plan_syll_comp_prepared_no) {
                    plan_syll_comp_prepared = AppConstants.No;
                } else {
                    plan_syll_comp_prepared = null;
                }
            }
        });
        binding.rgPunadiBooksSupplied.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPunadiBooksSupplied.getCheckedRadioButtonId();
                if (selctedItem == R.id.punadi_books_supplied_yes) {
                    binding.llSufficientBooksSupplied.setVisibility(View.VISIBLE);
                    punadi_books_supplied = AppConstants.Yes;
                } else if (selctedItem == R.id.punadi_books_supplied_no) {
                    binding.llSufficientBooksSupplied.setVisibility(View.GONE);
                    punadi_books_supplied = AppConstants.No;
                } else {
                    binding.llSufficientBooksSupplied.setVisibility(View.GONE);
                    punadi_books_supplied = null;
                }
            }
        });
        binding.rgSufficientBooksSupplied.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSufficientBooksSupplied.getCheckedRadioButtonId();
                if (selctedItem == R.id.sufficient_books_supplied_yes) {
                    sufficient_books_supplied = AppConstants.Yes;
                } else if (selctedItem == R.id.sufficient_books_supplied_no) {
                    sufficient_books_supplied = AppConstants.No;
                } else {
                    sufficient_books_supplied = null;
                }
            }
        });
        binding.rgPunadiPrgmConducted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPunadiPrgmConducted.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_punadi_prgm_conducted_yes) {
                    binding.tPunadiPrgmReason.setVisibility(View.GONE);
                    punadiPrgmConducted = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_punadi_prgm_conducted_no) {
                    binding.tPunadiPrgmReason.setVisibility(View.VISIBLE);
                    punadiPrgmConducted = AppConstants.No;
                } else {
                    binding.tPunadiPrgmReason.setVisibility(View.GONE);
                    punadiPrgmConducted = null;
                }
            }
        });
        binding.rgPunadi2TestmarksEntered.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPunadi2TestmarksEntered.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_punadi2_testmarks_entered_yes) {
                    binding.tPunadi2TestmarksReason.setVisibility(View.GONE);
                    punadi2_testmarks_entered = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_punadi2_testmarks_entered_no) {
                    binding.tPunadi2TestmarksReason.setVisibility(View.VISIBLE);
                    punadi2_testmarks_entered = AppConstants.No;
                } else {
                    binding.tPunadi2TestmarksReason.setVisibility(View.GONE);
                    punadi2_testmarks_entered = null;
                }
            }
        });
        binding.rgKaraDipathPrgmCond.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgKaraDipathPrgmCond.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_kara_dipath_prgm_cond_yes) {
                    binding.tKaraDipathPrgmCond.setVisibility(View.GONE);
                    kara_dipath_prgm_cond = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_kara_dipath_prgm_cond_no) {
                    binding.tKaraDipathPrgmCond.setVisibility(View.VISIBLE);
                    kara_dipath_prgm_cond = AppConstants.No;
                } else {
                    binding.tKaraDipathPrgmCond.setVisibility(View.GONE);
                    kara_dipath_prgm_cond = null;
                }
            }
        });
        binding.rgStrengthAccomodatedAsperInfrastructure.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStrengthAccomodatedAsperInfrastructure.getCheckedRadioButtonId();
                if (selctedItem == R.id.strength_accomodated_asper_infrastructure_yes) {
                    strength_accomodated_asper_infrastructure = AppConstants.Yes;
                } else if (selctedItem == R.id.strength_accomodated_asper_infrastructure_no) {
                    strength_accomodated_asper_infrastructure = AppConstants.No;
                } else {
                    strength_accomodated_asper_infrastructure = null;
                }
            }
        });
        binding.rgStaffAccomodatedAsperStudStrength.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStaffAccomodatedAsperStudStrength.getCheckedRadioButtonId();
                if (selctedItem == R.id.staff_accomodated_asper_stud_strength_yes)
                    staff_accomodated_asper_stud_strength = AppConstants.Yes;
                else
                    staff_accomodated_asper_stud_strength = AppConstants.No;
            }
        });
        binding.rgPlanPrepared.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPlanPrepared.getCheckedRadioButtonId();
                if (selctedItem == R.id.plan_prepared_yes)
                    plan_prepared = AppConstants.Yes;
                else if (selctedItem == R.id.plan_prepared_no)
                    plan_prepared = AppConstants.No;
                else
                    plan_prepared = null;
            }
        });
        binding.rgTextbooksRec.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgTextbooksRec.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_textbooks_rec_yes)
                    textbooks_rec = AppConstants.Yes;
                else if (selctedItem == R.id.rb_textbooks_rec_no)
                    textbooks_rec = AppConstants.No;
                else
                    textbooks_rec = null;
            }
        });
        binding.rgAssessmentTestConducted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAssessmentTestConducted.getCheckedRadioButtonId();
                if (selctedItem == R.id.assessment_test_conducted_yes)
                    assessment_test_conducted = AppConstants.Yes;
                else
                    assessment_test_conducted = AppConstants.No;
            }
        });
        binding.rgLabManualsReceived.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgLabManualsReceived.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_labManuals_received_yes) {
                    binding.llProperlyUsingManuals.setVisibility(View.VISIBLE);
                    labManuals_received = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_labManuals_received_no) {
                    binding.llProperlyUsingManuals.setVisibility(View.GONE);
                    labManuals_received = AppConstants.No;
                } else {
                    binding.llProperlyUsingManuals.setVisibility(View.GONE);
                    labManuals_received = null;
                }
            }
        });
        binding.rgProperlyUsingLabManuals.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgProperlyUsingLabManuals.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_properly_using_manuals_yes) {
                    properly_using_manuals = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_properly_using_manuals_no) {
                    properly_using_manuals = AppConstants.No;
                } else {
                    properly_using_manuals = null;
                }
            }
        });
        binding.rgLabRoomAvailable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgLabRoomAvailable.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_labroom_available_yes) {
                    binding.llNameScienceLab.setVisibility(View.VISIBLE);
                    labroom_available = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_labroom_available_no) {
                    binding.llNameScienceLab.setVisibility(View.GONE);
                    labroom_available = AppConstants.No;
                } else {
                    binding.llNameScienceLab.setVisibility(View.GONE);
                    labroom_available = null;
                }
            }
        });
        binding.rgMaintAccessionReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMaintAccessionReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_maint_accession_reg_yes) {
                    maint_accession_reg = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_maint_accession_reg_no) {
                    maint_accession_reg = AppConstants.No;
                } else {
                    maint_accession_reg = null;
                }
            }
        });
        binding.rgProperLightFan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgProperLightFan.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_proper_light_fan_yes) {
                    proper_light_fan = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_proper_light_fan_no) {
                    proper_light_fan = AppConstants.No;
                } else {
                    proper_light_fan = null;
                }
            }
        });
        binding.rgLabMatEnteredReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgLabMatEnteredReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_lab_mat_entered_reg_yes) {
                    binding.tMatEnterRegReason.setVisibility(View.GONE);
                    lab_mat_entered_reg = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_lab_mat_entered_reg_no) {
                    binding.tMatEnterRegReason.setVisibility(View.VISIBLE);
                    lab_mat_entered_reg = AppConstants.No;
                } else {
                    binding.tMatEnterRegReason.setVisibility(View.GONE);
                    lab_mat_entered_reg = null;
                }
            }
        });

        binding.rgLibraryRoomAvailable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgLibraryRoomAvailable.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_library_room_available_yes) {
                    binding.llNoBooksAvailable.setVisibility(View.VISIBLE);
                    library_room_available = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_library_room_available_no) {
                    binding.llNoBooksAvailable.setVisibility(View.GONE);
                    library_room_available = AppConstants.No;
                } else {
                    binding.llNoBooksAvailable.setVisibility(View.GONE);
                    library_room_available = null;
                }
            }
        });

        binding.rgBigTvRotAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBigTvRotAvail.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_big_tv_rot_avail_yes) {
                    binding.tTvRotWorkingStatus.setVisibility(View.VISIBLE);
                    big_tv_rot_avail = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_big_tv_rot_avail_no) {
                    binding.tTvRotWorkingStatus.setVisibility(View.GONE);
                    big_tv_rot_avail = AppConstants.No;
                } else {
                    binding.tTvRotWorkingStatus.setVisibility(View.GONE);
                    big_tv_rot_avail = null;
                }
            }
        });
        binding.rgManaTvLessonsShown.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgManaTvLessonsShown.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_mana_tv_lessons_shown_yes) {
                    binding.llManaTvLessonsReason.setVisibility(View.GONE);
                    mana_tv_lessons_shown = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_mana_tv_lessons_shown_no) {
                    binding.llManaTvLessonsReason.setVisibility(View.VISIBLE);
                    mana_tv_lessons_shown = AppConstants.No;
                } else {
                    binding.llManaTvLessonsReason.setVisibility(View.GONE);
                    mana_tv_lessons_shown = null;
                }
            }
        });
        binding.rgCompLabAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCompLabAvail.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_comp_lab_avail_yes) {
                    binding.llComputerLab.setVisibility(View.VISIBLE);
                    comp_lab_avail = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_comp_lab_avail_no) {
                    binding.llComputerLab.setVisibility(View.GONE);
                    comp_lab_avail = AppConstants.No;
                } else {
                    binding.llComputerLab.setVisibility(View.GONE);
                    comp_lab_avail = null;
                }
            }
        });
        binding.rgIctInstrAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgIctInstrAvail.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_ict_instr_avail_yes) {
                    binding.llIctInstr.setVisibility(View.VISIBLE);
                    ict_instr_avail = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_ict_instr_avail_no) {
                    binding.llIctInstr.setVisibility(View.GONE);
                    ict_instr_avail = AppConstants.No;
                } else {
                    binding.llIctInstr.setVisibility(View.GONE);
                    ict_instr_avail = null;
                }
            }
        });
        binding.rgTimetableDisp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgTimetableDisp.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_timetable_disp_yes) {
                    timetable_disp = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_timetable_disp_no) {
                    timetable_disp = AppConstants.No;
                } else {
                    timetable_disp = null;
                }
            }
        });
        binding.rgCompSyllCompleted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCompSyllCompleted.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_comp_syll_completed_yes) {
                    comp_syll_completed = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_comp_syll_completed_no) {
                    comp_syll_completed = AppConstants.No;
                } else {
                    comp_syll_completed = null;
                }
            }
        });

        binding.rgCompLabCond.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCompLabCond.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_comp_lab_cond_yes) {
                    comp_lab_cond = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_comp_lab_cond_no) {
                    comp_lab_cond = AppConstants.No;
                } else {
                    comp_lab_cond = null;
                }
            }
        });
        binding.rgDigitalContentUsed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDigitalContentUsed.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_digital_content_used_yes) {
                    digital_content_used = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_digital_content_used_no) {
                    digital_content_used = AppConstants.No;
                } else {
                    digital_content_used = null;
                }
            }
        });
        binding.rgELearningAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgELearningAvail.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_eLearning_avail_yes) {
                    binding.llElearning.setVisibility(View.VISIBLE);
                    eLearning_avail = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_eLearning_avail_no) {
                    binding.llElearning.setVisibility(View.GONE);
                    eLearning_avail = AppConstants.No;
                } else {
                    binding.llElearning.setVisibility(View.GONE);
                    eLearning_avail = null;
                }
            }
        });
        binding.rgShowingStud.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgShowingStud.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_showing_stud_yes) {
                    binding.llNameVolSchool.setVisibility(View.VISIBLE);
                    showing_stud = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_showing_stud_no) {
                    binding.llNameVolSchool.setVisibility(View.GONE);
                    showing_stud = AppConstants.No;
                } else {
                    binding.llNameVolSchool.setVisibility(View.GONE);
                    showing_stud = null;
                }
            }
        });
        binding.rgSeparateTimetableDisp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSeparateTimetableDisp.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_separate_timetable_disp_yes) {
                    separate_timetable_disp = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_separate_timetable_disp_no) {
                    separate_timetable_disp = AppConstants.No;
                } else {
                    separate_timetable_disp = null;
                }
            }
        });
        binding.rgTabsSupplied.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgTabsSupplied.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_tabs_supplied_yes) {
                    binding.llTabs.setVisibility(View.VISIBLE);
                    tabs_supplied = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_tabs_supplied_no) {
                    binding.llTabs.setVisibility(View.GONE);
                    tabs_supplied = AppConstants.No;
                } else {
                    binding.llTabs.setVisibility(View.GONE);
                    tabs_supplied = null;
                }
            }
        });
        binding.rgStudUsingAsPerSched.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStudUsingAsPerSched.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_stud_using_as_per_sched_yes) {
                    stud_using_as_per_sched = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_stud_using_as_per_sched_no) {
                    stud_using_as_per_sched = AppConstants.No;
                } else {
                    stud_using_as_per_sched = null;
                }
            }
        });
        binding.rgTabsTimetableDisp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgTabsTimetableDisp.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_tabs_timetable_disp_yes) {
                    tabs_timetable_disp = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_tabs_timetable_disp_no) {
                    tabs_timetable_disp = AppConstants.No;
                } else {
                    tabs_timetable_disp = null;
                }
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                highestClassGradeA = binding.highestClassGradeA.getText().toString().trim();
                highestClassGradeB = binding.highestClassGradeB.getText().toString().trim();
                highestClassGradeC = binding.highestClassGradeC.getText().toString().trim();
                highestClassGradeTotal = binding.highestClassTotal.getText().toString().trim();
                last_yr_ssc_percent = binding.lastYrSscPercentEt.getText().toString().trim();
                punadiPrgmReason = binding.etPunadiPrgmReason.getText().toString().trim();
                punadi2TestmarksReason = binding.etPunadi2TestmarksReason.getText().toString().trim();
                karaDipathPrgmCondReason = binding.etKaraDipathPrgmCond.getText().toString().trim();

                labName = binding.etLabName.getText().toString().trim();
                labInchargeName = binding.etLabInchargeName.getText().toString().trim();
                labMobileNo = binding.etLabMobileNo.getText().toString().trim();
                matEnterRegReason = binding.etMatEnterRegReason.getText().toString().trim();

                noOfBooks = binding.etNoOfBooks.getText().toString().trim();
                nameLibraryIncharge = binding.etNameLibraryIncharge.getText().toString().trim();
                libraryMobileNo = binding.etLibraryMobileNo.getText().toString().trim();
                TvRotWorkingStatus = binding.etTvRotWorkingStatus.getText().toString().trim();

                manaTvLessonsReason = binding.etManaTvLessonsReason.getText().toString().trim();
                manaTvInchargeName = binding.etManaTvInchargeName.getText().toString().trim();
                manaTvMobileNo = binding.etManaTvMobileNo.getText().toString().trim();

                noOfComputersAvailable = binding.etNoOfComputersAvailable.getText().toString().trim();
                compWorkingStatus = binding.etCompWorkingStatus.getText().toString().trim();
                workingStatusProjector = binding.etWorkingStatusProjector.getText().toString().trim();

                nameIctInstr = binding.etNameIctInstr.getText().toString().trim();
                mobNoIctInstr = binding.etMobNoIctInstr.getText().toString().trim();

                volSchoolCoordName = binding.etVolSchoolCoordName.getText().toString().trim();
                volSchoolCoordMobNo = binding.etVolSchoolCoordMobNo.getText().toString().trim();

                eLearningInchrgName = binding.etELearningInchrgName.getText().toString().trim();
                eLearningInchrgMobileNo = binding.etELearningInchrgMobileNo.getText().toString().trim();

                noOfTabs = binding.etNoOfTabs.getText().toString().trim();
                tabInchargeName = binding.etTabInchargeName.getText().toString().trim();
                tabInchargeMblno = binding.etTabInchargeMblno.getText().toString().trim();

                if (validate()) {
                    AcademicEntity = new AcademicEntity();
                    AcademicEntity.setOfficer_id(officerId);
                    AcademicEntity.setInstitute_id(instId);
                    AcademicEntity.setInspection_time(Utils.getCurrentDateTime());
                    AcademicEntity.setHighest_class_syllabus_completed(highest_class_syllabus_completed);
                    AcademicEntity.setPlan_syll_comp_prepared(plan_syll_comp_prepared);
                    AcademicEntity.setStrength_accomodated_asper_infrastructure(strength_accomodated_asper_infrastructure);
                    AcademicEntity.setStaff_accomodated_asper_stud_strength(staff_accomodated_asper_stud_strength);
                    AcademicEntity.setPlan_prepared(plan_prepared);
                    AcademicEntity.setTextbooks_rec(textbooks_rec);
                    AcademicEntity.setAssessment_test_conducted(assessment_test_conducted);

                    AcademicEntity.setHighest_class_gradeA(highestClassGradeA);
                    AcademicEntity.setHighest_class_gradeB(highestClassGradeB);
                    AcademicEntity.setHighest_class_gradeC(highestClassGradeC);
                    AcademicEntity.setHighest_class_total(highestClassGradeTotal);

                    AcademicEntity.setLast_yr_ssc_percent(last_yr_ssc_percent);
                    AcademicEntity.setPunadi_books_supplied(punadi_books_supplied);
                    AcademicEntity.setSufficient_books_supplied(sufficient_books_supplied);
                    AcademicEntity.setPunadiPrgmConducted(punadiPrgmConducted);
                    AcademicEntity.setPunadiPrgmReason(punadiPrgmReason);
                    AcademicEntity.setPunadi2_testmarks_entered(punadi2_testmarks_entered);
                    AcademicEntity.setPunadi2TestmarksReason(punadi2TestmarksReason);
                    AcademicEntity.setPunadi2_testmarks_entered(kara_dipath_prgm_cond);
                    AcademicEntity.setKaraDipathPrgmCondReason(karaDipathPrgmCondReason);
                    AcademicEntity.setLabManuals_received(labManuals_received);
                    AcademicEntity.setProperly_using_manuals(properly_using_manuals);
                    AcademicEntity.setLabroom_available(labroom_available);
                    AcademicEntity.setLabName(labName);
                    AcademicEntity.setLabInchargeName(labInchargeName);
                    AcademicEntity.setLabMobileNo(labMobileNo);
                    AcademicEntity.setLab_mat_entered_reg(lab_mat_entered_reg);

                    AcademicEntity.setLibrary_room_available(library_room_available);
                    AcademicEntity.setNoOfBooks(noOfBooks);
                    AcademicEntity.setNameLibraryIncharge(nameLibraryIncharge);
                    AcademicEntity.setLibraryMobileNo(libraryMobileNo);
                    AcademicEntity.setMaint_accession_reg(maint_accession_reg);
                    AcademicEntity.setProper_light_fan(proper_light_fan);

                    AcademicEntity.setBig_tv_rot_avail(big_tv_rot_avail);
                    AcademicEntity.setTvRotWorkingStatus(TvRotWorkingStatus);

                    AcademicEntity.setMana_tv_lessons_shown(mana_tv_lessons_shown);
                    AcademicEntity.setManaTvLessonsReason(manaTvLessonsReason);
                    AcademicEntity.setManaTvInchargeName(manaTvInchargeName);
                    AcademicEntity.setManaTvMobileNo(manaTvMobileNo);


                    AcademicEntity.setComp_lab_avail(comp_lab_avail);
                    AcademicEntity.setNoOfComputersAvailable(noOfComputersAvailable);
                    AcademicEntity.setCompWorkingStatus(compWorkingStatus);
                    AcademicEntity.setWorkingStatusProjector(workingStatusProjector);


                    AcademicEntity.setIct_instr_avail(ict_instr_avail);
                    AcademicEntity.setNameIctInstr(nameIctInstr);
                    AcademicEntity.setMobNoIctInstr(mobNoIctInstr);


                    AcademicEntity.setTimetable_disp(timetable_disp);
                    AcademicEntity.setComp_syll_completed(comp_syll_completed);
                    AcademicEntity.setComp_lab_cond(comp_lab_cond);
                    AcademicEntity.setDigital_content_used(digital_content_used);


                    AcademicEntity.setLea_avail(eLearning_avail);
                    AcademicEntity.setStud_using_as_per_sched(stud_using_as_per_sched);
                    AcademicEntity.setVolSchoolCoordName(volSchoolCoordName);
                    AcademicEntity.setVolSchoolCoordMobNo(volSchoolCoordMobNo);
                    AcademicEntity.setLeaInchargeName(eLearningInchrgName);
                    AcademicEntity.setLeaMobNum(eLearningInchrgMobileNo);
                    AcademicEntity.setSeparate_timetable_disp(separate_timetable_disp);
                    AcademicEntity.setTabs_supplied(tabs_supplied);
                    AcademicEntity.setNoOfTabs(noOfTabs);
                    AcademicEntity.setStud_using_as_per_sched(stud_using_as_per_sched);
                    AcademicEntity.setTabInchargeName(tabInchargeName);
                    AcademicEntity.setTabInchargeMblno(tabInchargeMblno);

                    Utils.customSaveAlert(AcademicActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));

                }
            }
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(highest_class_syllabus_completed)) {
            showSnackBar("Select Highest Calss Syllabus completed");
            return false;
        }
        if (highest_class_syllabus_completed.equals(AppConstants.No) && TextUtils.isEmpty(plan_syll_comp_prepared)) {
            showSnackBar("Select Plan for completing the syllabus prepared");
            return false;
        }
        if (TextUtils.isEmpty(strength_accomodated_asper_infrastructure)) {
            showSnackBar("Select Is the strength accommodated");
            return false;
        }
        if (TextUtils.isEmpty(staff_accomodated_asper_stud_strength)) {
            showSnackBar("Select does staff strength accommodate");
            return false;
        }
        if (TextUtils.isEmpty(plan_prepared)) {
            showSnackBar("Select school plan for next academic year is prepared");
            return false;
        }
        if (TextUtils.isEmpty(textbooks_rec)) {
            showSnackBar("Select sufficient text books received");
            return false;
        }
        if (TextUtils.isEmpty(assessment_test_conducted)) {
            showSnackBar("Select assessment tests conducted");
            return false;
        }
        if (TextUtils.isEmpty(highestClassGradeA)) {
            showSnackBar("Enter GradeA");
            return false;
        }
        if (TextUtils.isEmpty(highestClassGradeB)) {
            showSnackBar("Enter GradeB");
            return false;
        }
        if (TextUtils.isEmpty(highestClassGradeC)) {
            showSnackBar("Enter GradeC");
            return false;
        }
        if (TextUtils.isEmpty(highestClassGradeTotal)) {
            showSnackBar("Enter Total");
            return false;
        }
        if (TextUtils.isEmpty(last_yr_ssc_percent)) {
            showSnackBar("Enter Last year SSC results Percentage");
            return false;
        }
        if (TextUtils.isEmpty(punadi_books_supplied)) {
            showSnackBar("Select Punadi-2 books supplied");
            return false;
        }
        if (punadi_books_supplied.equals(AppConstants.Yes) && TextUtils.isEmpty(sufficient_books_supplied)) {
            showSnackBar("Select Sufficient books supplied");
            return false;
        }
        if (TextUtils.isEmpty(punadiPrgmConducted)) {
            showSnackBar("Select Punadi program is conducted");
            return false;
        }
        if (punadiPrgmConducted.equals(AppConstants.No) && TextUtils.isEmpty(punadiPrgmReason)) {
            showSnackBar("Enter Punadi program Reason");
            return false;
        }

        if (TextUtils.isEmpty(punadi2_testmarks_entered)) {
            showSnackBar("Select Punadi-2 test marks entered");
            return false;
        }

        if (punadi2_testmarks_entered.equals(AppConstants.No) && TextUtils.isEmpty(punadi2TestmarksReason)) {
            showSnackBar("Enter Punadi-2 test marks Reason");
            return false;
        }

        if (TextUtils.isEmpty(kara_dipath_prgm_cond)) {
            showSnackBar("Select Kara dipath program is conducted");
            return false;
        }
        if (kara_dipath_prgm_cond.equals(AppConstants.No) && TextUtils.isEmpty(kara_dipath_prgm_cond)) {
            showSnackBar("Enter Kara dipath program Reason");
            return false;
        }
        if (TextUtils.isEmpty(labManuals_received)) {
            showSnackBar("Enter science lab manuals received");
            return false;
        }
        if (labManuals_received.equals(AppConstants.Yes) && TextUtils.isEmpty(properly_using_manuals)) {
            showSnackBar("Select properly using the science lab manuals");
            return false;
        }
        if (TextUtils.isEmpty(labroom_available)) {
            showSnackBar("Select science lab room is available");
            return false;
        }
        if (labroom_available.equals(AppConstants.Yes) && TextUtils.isEmpty(labName)) {
            showSnackBar("Enter Name of the science lab");
            return false;
        }
        if (labroom_available.equals(AppConstants.Yes) && TextUtils.isEmpty(labInchargeName)) {
            showSnackBar("Enter Name of the science lab incharge teacher");
            return false;
        }

        if (labroom_available.equals(AppConstants.Yes) && TextUtils.isEmpty(labMobileNo)) {
            showSnackBar("Enter lab mobile number");
            return false;
        }


        if (labroom_available.equals(AppConstants.Yes) && labMobileNo.length() != 10) {
            showSnackBar("Enter valid lab mobile number");
            return false;
        }


        if (labroom_available.equals(AppConstants.Yes)
                && !(labMobileNo.startsWith("9") || labMobileNo.startsWith("8") || labMobileNo.startsWith("7") ||
                labMobileNo.startsWith("6"))) {
            showSnackBar("Enter valid lab mobile number");
            return false;
        }


        if (TextUtils.isEmpty(lab_mat_entered_reg)) {
            showSnackBar("Select Lab material entered in the stock entry register");
            return false;
        }
        if (lab_mat_entered_reg.equals(AppConstants.No) && TextUtils.isEmpty(matEnterRegReason)) {
            showSnackBar("Enter Lab material Reason");
            return false;
        }


        if (TextUtils.isEmpty(library_room_available)) {
            showSnackBar("Select library room is available");
            return false;
        }
        if (library_room_available.equals(AppConstants.Yes) && TextUtils.isEmpty(noOfBooks)) {
            showSnackBar("Enter no of books");
            return false;
        }
        if (library_room_available.equals(AppConstants.Yes) && TextUtils.isEmpty(nameLibraryIncharge)) {
            showSnackBar("Enter name of the library incharge teacher");
            return false;
        }
        if (library_room_available.equals(AppConstants.Yes) && TextUtils.isEmpty(libraryMobileNo)) {
            showSnackBar("Enter library mobile number");
            return false;
        }

        if (library_room_available.equals(AppConstants.Yes) && libraryMobileNo.length() != 10) {
            showSnackBar("Enter valid library mobile number");
            return false;
        }


        if (library_room_available.equals(AppConstants.Yes) && !TextUtils.isEmpty(libraryMobileNo)
                && !(libraryMobileNo.startsWith("9") || libraryMobileNo.startsWith("8") || libraryMobileNo.startsWith("7") ||
                libraryMobileNo.startsWith("6"))) {
            showSnackBar("Enter valid library mobile number");
            return false;
        }
        if (library_room_available.equals(AppConstants.Yes) && TextUtils.isEmpty(maint_accession_reg)) {
            showSnackBar("Select Maintaining the accession register");
            return false;
        }
        if (TextUtils.isEmpty(proper_light_fan)) {
            showSnackBar("Select proper lighting & Fans available");
            return false;
        }

        if (TextUtils.isEmpty(big_tv_rot_avail)) {
            showSnackBar("Select Big TV & ROT available");
            return false;
        }
        if (big_tv_rot_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(TvRotWorkingStatus)) {
            showSnackBar("Select Big TV & ROT Working Status");
            return false;
        }

        if (TextUtils.isEmpty(mana_tv_lessons_shown)) {
            showSnackBar("Select  MANA TV lessons are shown to students");
            return false;
        }
        if (mana_tv_lessons_shown.equals(AppConstants.No) && TextUtils.isEmpty(manaTvLessonsReason)) {
            showSnackBar("Enter MANA TV Reason");
            return false;
        }
        if (mana_tv_lessons_shown.equals(AppConstants.No) && TextUtils.isEmpty(manaTvInchargeName)) {
            showSnackBar("Select  MANA TV incharge teacher name");
            return false;
        }
        if (mana_tv_lessons_shown.equals(AppConstants.No) && TextUtils.isEmpty(manaTvMobileNo)) {
            showSnackBar("Enter MANA TV mobile number");
            return false;
        }

        if (mana_tv_lessons_shown.equals(AppConstants.No)
                && manaTvMobileNo.length() != 10) {
            showSnackBar("Enter valid MANA TV mobile number");
            return false;
        }

        if (mana_tv_lessons_shown.equals(AppConstants.No)
                && !(manaTvMobileNo.startsWith("9") || manaTvMobileNo.startsWith("8") || manaTvMobileNo.startsWith("7") ||
                manaTvMobileNo.startsWith("6"))) {
            showSnackBar("Enter valid MANA TV mobile number");
            return false;
        }

        if (TextUtils.isEmpty(comp_lab_avail)) {
            showSnackBar("Select computer lab is available");
            return false;
        }
        if (comp_lab_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(noOfComputersAvailable)) {
            showSnackBar("Enter No of Computers");
            return false;
        }
        if (comp_lab_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(compWorkingStatus)) {
            showSnackBar("Enter Working Status of Computers");
            return false;
        }
        if (comp_lab_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(workingStatusProjector)) {
            showSnackBar("Enter Working Status of Projector");
            return false;
        }
        if (comp_lab_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(ict_instr_avail)) {
            showSnackBar("Select ICT instructor available");
            return false;
        }
        if (comp_lab_avail.equals(AppConstants.Yes) && ict_instr_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(nameIctInstr)) {
            showSnackBar("Enter Name of the ICT instructor");
            return false;
        }
        if (comp_lab_avail.equals(AppConstants.Yes) && ict_instr_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(mobNoIctInstr)) {
            showSnackBar("Enter ICT instructor Mobile No");
            return false;
        }

        if (comp_lab_avail.equals(AppConstants.Yes) && ict_instr_avail.equals(AppConstants.Yes) && mobNoIctInstr.length()!=10) {
            showSnackBar("Enter valid ICT instructor Mobile No");
            return false;
        }

        if (comp_lab_avail.equals(AppConstants.Yes) &&  ict_instr_avail.equals(AppConstants.Yes)
                && !(mobNoIctInstr.startsWith("9") || mobNoIctInstr.startsWith("8") || mobNoIctInstr.startsWith("7") ||
                mobNoIctInstr.startsWith("6"))) {
            showSnackBar("Enter valid ICT instructor Mobile No");
            return false;
        }

        if (comp_lab_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(timetable_disp)) {
            showSnackBar("Select Timetable");
            return false;
        }
        if (comp_lab_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(comp_syll_completed)) {
            showSnackBar("Select computer syllabus completed");
            return false;
        }
        if (comp_lab_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(comp_lab_cond)) {
            showSnackBar("Select computer lab test conducted");
            return false;
        }
        if (comp_lab_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(digital_content_used)) {
            showSnackBar("Select digital content used by students");
            return false;
        }


        if (TextUtils.isEmpty(eLearning_avail)) {
            showSnackBar("Select e-Learning classes available");
            return false;
        }
        if (eLearning_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(showing_stud)) {
            showSnackBar("Select Showing to students");
            return false;
        }
        if (eLearning_avail.equals(AppConstants.Yes) && showing_stud.equals(AppConstants.Yes) && TextUtils.isEmpty(volSchoolCoordName)) {
            showSnackBar("Enter School Coordinator Name");
            return false;
        }
        if (eLearning_avail.equals(AppConstants.Yes) && showing_stud.equals(AppConstants.Yes)
                && TextUtils.isEmpty(volSchoolCoordMobNo)) {
            showSnackBar("Enter School Coordinator Mobile Number");
            return false;
        }

        if (eLearning_avail.equals(AppConstants.Yes) && showing_stud.equals(AppConstants.Yes)  && volSchoolCoordMobNo.length()!=10) {
            showSnackBar("Enter valid School Coordinator Mobile Number");
            return false;
        }

        if (eLearning_avail.equals(AppConstants.Yes) && showing_stud.equals(AppConstants.Yes)
                && !(volSchoolCoordMobNo.startsWith("9") || volSchoolCoordMobNo.startsWith("8") || volSchoolCoordMobNo.startsWith("7") ||
                volSchoolCoordMobNo.startsWith("6"))) {
            showSnackBar("Enter valid School Coordinator Mobile Number");
            return false;
        }

        if (eLearning_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(eLearningInchrgName)) {
            showSnackBar("Enter eLearning incharge Name");
            return false;
        }
        if (eLearning_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(eLearningInchrgMobileNo)) {
            showSnackBar("Enter eLearning incharge Mobile Number");
            return false;
        }

        if (eLearning_avail.equals(AppConstants.Yes) && eLearningInchrgMobileNo.length()!=10) {
            showSnackBar("Enter eLearning incharge Mobile Number");
            return false;
        }

        if (eLearning_avail.equals(AppConstants.Yes)
                && !(eLearningInchrgMobileNo.startsWith("9") || eLearningInchrgMobileNo.startsWith("8") || eLearningInchrgMobileNo.startsWith("7") ||
                eLearningInchrgMobileNo.startsWith("6"))) {
            showSnackBar("Enter valid eLearning incharge Mobile Number");
            return false;
        }


        if (eLearning_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(separate_timetable_disp)) {
            showSnackBar("Select Separate time table displayed");
            return false;
        }

        if (eLearning_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(tabs_supplied)) {
            showSnackBar("Select tabs supplied");
            return false;
        }
        if (eLearning_avail.equals(AppConstants.Yes) && tabs_supplied.equals(AppConstants.Yes) && TextUtils.isEmpty(noOfTabs)) {
            showSnackBar("Enter No of tabs");
            return false;
        }
        if (eLearning_avail.equals(AppConstants.Yes) && tabs_supplied.equals(AppConstants.Yes) && TextUtils.isEmpty(stud_using_as_per_sched)) {
            showSnackBar("Select Students are using");
            return false;
        }

        if (eLearning_avail.equals(AppConstants.Yes) && tabs_supplied.equals(AppConstants.Yes) && TextUtils.isEmpty(tabInchargeName)) {
            showSnackBar("Enter Name of the tab lab incharge");
            return false;
        }
        if (eLearning_avail.equals(AppConstants.Yes) && tabs_supplied.equals(AppConstants.Yes) && TextUtils.isEmpty(tabInchargeMblno)) {
            showSnackBar("Enter tab lab incharge Mobile number");
            return false;
        }


        if (eLearning_avail.equals(AppConstants.Yes) && tabs_supplied.equals(AppConstants.Yes) && eLearningInchrgMobileNo.length()!=10) {
            showSnackBar("Enter valid tab lab incharge Mobile number");
            return false;
        }

        if (eLearning_avail.equals(AppConstants.Yes) && tabs_supplied.equals(AppConstants.Yes)
                && !(eLearningInchrgMobileNo.startsWith("9") || eLearningInchrgMobileNo.startsWith("8") || eLearningInchrgMobileNo.startsWith("7") ||
                eLearningInchrgMobileNo.startsWith("6"))) {
            showSnackBar("Enter valid tab lab incharge Mobile number");
            return false;
        }

        return true;
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void submitData() {
        long x = academicViewModel.insertAcademicInfo(AcademicEntity);
        if (x >= 0) {
            final long[] z = {0};
            try {
                LiveData<Integer> liveData = instMainViewModel.getSectionId("Academic");
                liveData.observe(AcademicActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer id) {
                        if (id != null) {
                            z[0] = instMainViewModel.updateSectionInfo(Utils.getCurrentDateTime(), id, instId);

                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z[0] >= 0) {
                Utils.customSectionSaveAlert(AcademicActivity.this, getString(R.string.data_saved), getString(R.string.app_name));
            } else {
                showSnackBar(getString(R.string.failed));
            }
        } else {
            showSnackBar(getString(R.string.failed));
        }
    }

    @Override
    public void onBackPressed() {
        super.callBack();
    }
}
