package com.cgg.twdinspection.inspection.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityAcademicBinding;
import com.cgg.twdinspection.inspection.interfaces.SaveListener;
import com.cgg.twdinspection.inspection.source.academic_overview.AcademicEntity;
import com.cgg.twdinspection.inspection.source.academic_overview.AcademicGradeEntity;
import com.cgg.twdinspection.inspection.source.inst_master.MasterClassInfo;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.source.student_attendence_info.StudAttendInfoEntity;
import com.cgg.twdinspection.inspection.viewmodel.AcademicCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.AcademicViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.StudentsAttndViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class AcademicActivity extends BaseActivity implements SaveListener {
    ActivityAcademicBinding binding;
    AcademicViewModel academicViewModel;
    AcademicEntity academicEntity;
    String highest_class_syllabus_completed, strength_accomodated_asper_infrastructure, staff_accomodated_asper_stud_strength,
            plan_prepared, textbooks_rec, assessment_test_conducted, punadiPrgmConducted, punadi2_testmarks_entered,
            kara_dipath_prgm_cond, labManuals_received, labroom_available, lab_mat_entered_reg,
            big_tv_rot_avail, mana_tv_lessons_shown, comp_lab_avail, ict_instr_avail, eLearning_avail, showing_stud, tabs_supplied,
            punadi_books_supplied, properly_using_manuals, plan_syll_comp_prepared, sufficient_books_supplied;
    String highestClassGradeA, highestClassGradeB, highestClassGradeC, highestClassGradeTotal, last_yr_ssc_percent,
            punadiPrgmReason, punadiBooksReq, punadi2TestmarksReason, karaDipathPrgmCondReason, labInchargeName, labMobileNo,
            noOfBooks, nameLibraryIncharge, libraryMobileNo, TvRotWorkingStatus,
            maint_accession_reg, proper_light_fan, manaTvLessonsReason, manaTvInchargeName, manaTvMobileNo,
            noOfComputersAvailable, compWorkingStatus, nameIctInstr, mobNoIctInstr,
            timetable_disp, comp_syll_completed, comp_lab_cond, digital_content_used, noOfTabs, tabInchargeName, tabInchargeMblno,
            stud_using_as_per_sched, tabs_timetable_disp, volSchoolCoordName, volSchoolCoordMobNo, eLearningInchrgName,
            eLearningInchrgMobileNo, separate_timetable_disp, labMatEnteredReason;
    private InstMainViewModel instMainViewModel;
    private SharedPreferences sharedPreferences;
    private String instId, officerId, insTime;
    private int localFlag = -1;
    private List<AcademicGradeEntity> academicGradeEntities;
    private StudentsAttndViewModel studentsAttndViewModel;
    private int highClassStrength;

    private void getHighClassStrength() {

        LiveData<MasterInstituteInfo> masterInstituteInfoLiveData = studentsAttndViewModel.getMasterClassInfo(
                instId);
        masterInstituteInfoLiveData.observe(AcademicActivity.this, new Observer<MasterInstituteInfo>() {
            @Override
            public void onChanged(MasterInstituteInfo masterInstituteInfos) {
                masterInstituteInfoLiveData.removeObservers(AcademicActivity.this);
                if(masterInstituteInfos!=null && masterInstituteInfos.getClassInfo()!=null && masterInstituteInfos.getClassInfo().size()>0){
                List<MasterClassInfo> masterClassInfos = masterInstituteInfos.getClassInfo();
                if (masterClassInfos != null && masterClassInfos.size() > 0) {
                    for (int i = masterClassInfos.size(); i > 0; i--) {
                        if (masterClassInfos.get(i - 1).getClassId() > 0) {
                            highClassStrength = masterClassInfos.get(i - 1).getStudentCount();
                            binding.highClassStrength.setText("Highest Class Strength: " + highClassStrength);
                            return;
                        }
                    }
                }
                }else {
                    Toast.makeText(AcademicActivity.this, "No master institute data found", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_academic, getString(R.string.title_academic));
        instMainViewModel = new InstMainViewModel(getApplication());
        sharedPreferences = TWDApplication.get(this).getPreferences();
        studentsAttndViewModel = new StudentsAttndViewModel(getApplication());


        academicViewModel = ViewModelProviders.of(AcademicActivity.this,
                new AcademicCustomViewModel(binding, this, getApplication())).get(AcademicViewModel.class);
        binding.setViewModel(academicViewModel);

        instId = sharedPreferences.getString(AppConstants.INST_ID, null);
        officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, null);
        insTime = sharedPreferences.getString(AppConstants.INSP_TIME, null);
        try {
            localFlag = getIntent().getIntExtra(AppConstants.LOCAL_FLAG, -1);
            if (localFlag == 1) {
                //get local record & set to data binding
                LiveData<AcademicEntity> academicInfoData = instMainViewModel.getAcademicInfoData();
                academicInfoData.observe(AcademicActivity.this, new Observer<AcademicEntity>() {
                    @Override
                    public void onChanged(AcademicEntity generalInfoEntity) {
                        academicInfoData.removeObservers(AcademicActivity.this);
                        if (generalInfoEntity != null) {
                            binding.setInspData(generalInfoEntity);
                            binding.executePendingBindings();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        getHighClassStrength();

        binding.highestClassGradeA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int cnt = Integer.valueOf(s.toString());
                    if (cnt > highClassStrength) {
                        binding.highestClassGradeA.setText("");
                        binding.highestClassGradeA.setError("Entered count should not exceed the high class strength");
                        binding.highestClassGradeA.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.highestClassGradeB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int cnt = Integer.valueOf(s.toString());
                    if (cnt > highClassStrength) {
                        binding.highestClassGradeB.setText("");
                        binding.highestClassGradeB.setError("Entered count should not exceed the high class strength");
                        binding.highestClassGradeB.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.highestClassGradeC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int cnt = Integer.valueOf(s.toString());
                    if (cnt > highClassStrength) {
                        binding.highestClassGradeC.setText("");
                        binding.highestClassGradeC.setError("Entered count should not exceed the high class strength");
                        binding.highestClassGradeC.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.highestClassTotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int cnt = Integer.valueOf(s.toString());
                    if (cnt > highClassStrength) {
                        binding.highestClassTotal.setText("");
                        binding.highestClassTotal.setError("Entered count should not exceed the high class strength");
                        binding.highestClassTotal.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        academicViewModel.getAcademicGradeInfo(instId).observe(AcademicActivity.this, new Observer<List<AcademicGradeEntity>>() {
            @Override
            public void onChanged(List<AcademicGradeEntity> academicGradeEntities) {
                AcademicActivity.this.academicGradeEntities = academicGradeEntities;
                if (academicGradeEntities != null && academicGradeEntities.size() > 0) {
                    binding.btnAddStud.setBackground(getResources().getDrawable(R.drawable.btn_background1));
                    binding.btnAddStud.setText(getString(R.string.capture_view));
                } else {
                    binding.btnAddStud.setBackground(getResources().getDrawable(R.drawable.btn_background));
                    binding.btnAddStud.setText(getString(R.string.capture));
                }
            }
        });

        binding.rgHighestClassSyllabusCompleted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHighestClassSyllabusCompleted.getCheckedRadioButtonId();
                if (selctedItem == R.id.highest_class_syllabus_completed_yes) {
                    binding.rgPlanSyllCompPrepared.clearCheck();
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
                    binding.tvClasswiseBooksReq.setVisibility(View.GONE);
                    binding.etClasswiseBooksReq.setText(null);
                    punadi_books_supplied = AppConstants.Yes;
                } else if (selctedItem == R.id.punadi_books_supplied_no) {
                    binding.rgSufficientBooksSupplied.clearCheck();
                    binding.llSufficientBooksSupplied.setVisibility(View.GONE);
                    binding.tvClasswiseBooksReq.setVisibility(View.VISIBLE);
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
                    binding.etPunadiPrgmReason.setText(null);
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
                    binding.etPunadi2TestmarksReason.setText(null);
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
                    binding.etKaraDipathPrgmCond.setText(null);
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
                    binding.rgProperlyUsingLabManuals.clearCheck();
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
                    binding.etLabInchargeName.setText(null);
                    binding.etLabMobileNo.setText(null);
                    binding.llNameScienceLab.setVisibility(View.GONE);
                    labroom_available = AppConstants.No;
                } else {
                    binding.llNameScienceLab.setVisibility(View.GONE);
                    labroom_available = null;
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
                    binding.etMatEnterRegReason.setText(null);
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

        binding.rgMaintLibReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMaintLibReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_maint_accession_reg_yes) {
                    binding.llNoBooksAvailable.setVisibility(View.VISIBLE);
                    maint_accession_reg = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_maint_accession_reg_no) {
                    binding.etNoOfBooks.setText(null);
                    binding.llNoBooksAvailable.setVisibility(View.GONE);
                    maint_accession_reg = AppConstants.No;
                } else {
                    binding.llNoBooksAvailable.setVisibility(View.GONE);
                    maint_accession_reg = null;
                }
            }
        });

        binding.rgBigTvRotAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBigTvRotAvail.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_big_tv_rot_avail_yes) {
                    binding.bigTvLl.setVisibility(View.VISIBLE);
                    big_tv_rot_avail = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_big_tv_rot_avail_no) {
                    binding.rgBigTvRotAvailCon.clearCheck();
                    binding.bigTvLl.setVisibility(View.GONE);
                    big_tv_rot_avail = AppConstants.No;
                } else {
                    binding.bigTvLl.setVisibility(View.GONE);
                    big_tv_rot_avail = null;
                }
            }
        });

        binding.rgBigTvRotAvailCon.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBigTvRotAvailCon.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_big_tv_rot_avail_con_yes) {
                    TvRotWorkingStatus = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_big_tv_rot_avail_con_no) {
                    TvRotWorkingStatus = AppConstants.No;
                } else {
                    TvRotWorkingStatus = null;
                }
            }
        });
        binding.rgManaTvLessonsShown.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgManaTvLessonsShown.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_mana_tv_lessons_shown_yes) {
                    binding.etManaTvLessonsReason.setText(null);
                    binding.llManaTvLessonsReason.setVisibility(View.GONE);
                    mana_tv_lessons_shown = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_mana_tv_lessons_shown_no) {
                    binding.llManaTvLessonsReason.setVisibility(View.VISIBLE);
                    mana_tv_lessons_shown = AppConstants.No;
                } else {
                    binding.etManaTvLessonsReason.setText(null);
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
                    binding.etNoOfComputersAvailable.setText(null);
                    binding.etCompWorkingStatus.setText(null);
                    binding.rgIctInstrAvail.clearCheck();
                    binding.rgTimetableDisp.clearCheck();
                    binding.etNameIctInstr.setText(null);
                    binding.etMobNoIctInstr.setText(null);
                    binding.rgCompSyllCompleted.clearCheck();
                    binding.rgCompLabCond.clearCheck();
                    binding.rgDigitalContentUsed.clearCheck();
                    binding.llComputerLab.setVisibility(View.GONE);
                    comp_lab_avail = AppConstants.No;
                } else {
                    binding.etNoOfComputersAvailable.setText(null);
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
                    binding.etNameIctInstr.setText(null);
                    binding.etMobNoIctInstr.setText(null);
                    binding.llIctInstr.setVisibility(View.GONE);
                    ict_instr_avail = AppConstants.No;
                } else {
                    binding.etNameIctInstr.setText(null);
                    binding.etMobNoIctInstr.setText(null);
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
                    binding.rgShowingStud.clearCheck();
                    binding.rgSeparateTimetableDisp.clearCheck();
                    binding.rgTabsSupplied.clearCheck();
                    binding.etELearningInchrgName.setText(null);
                    binding.etELearningInchrgMobileNo.setText(null);
                    binding.etVolSchoolCoordName.setText(null);
                    binding.etVolSchoolCoordMobNo.setText(null);
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
                    binding.etVolSchoolCoordName.setText(null);
                    binding.etVolSchoolCoordMobNo.setText(null);
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
        binding.rgTabsSupplied.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgTabsSupplied.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_tabs_supplied_yes) {
                    binding.llTabs.setVisibility(View.VISIBLE);
                    tabs_supplied = AppConstants.Yes;
                } else if (selctedItem == R.id.rb_tabs_supplied_no) {
                    binding.etNoOfTabs.setText(null);
                    binding.etTabInchargeName.setText(null);
                    binding.etTabInchargeMblno.setText(null);
                    binding.rgStudUsingAsPerSched.clearCheck();
                    binding.llTabs.setVisibility(View.GONE);
                    tabs_supplied = AppConstants.No;
                } else {
                    binding.etNoOfTabs.setText(null);
                    binding.etTabInchargeName.setText(null);
                    binding.etTabInchargeMblno.setText(null);
                    binding.rgStudUsingAsPerSched.clearCheck();
                    binding.llTabs.setVisibility(View.GONE);
                    tabs_supplied = null;
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

        binding.btnAddStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AcademicActivity.this, AcademicGradeActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
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
                punadiBooksReq = binding.etClasswiseBooksReq.getText().toString().trim();
                punadi2TestmarksReason = binding.etPunadi2TestmarksReason.getText().toString().trim();
                karaDipathPrgmCondReason = binding.etKaraDipathPrgmCond.getText().toString().trim();

                labInchargeName = binding.etLabInchargeName.getText().toString().trim();
                labMobileNo = binding.etLabMobileNo.getText().toString().trim();

                noOfBooks = binding.etNoOfBooks.getText().toString().trim();
                nameLibraryIncharge = binding.etNameLibraryIncharge.getText().toString().trim();
                libraryMobileNo = binding.etLibraryMobileNo.getText().toString().trim();

                labMatEnteredReason = binding.etMatEnterRegReason.getText().toString().trim();

                manaTvLessonsReason = binding.etManaTvLessonsReason.getText().toString().trim();
                manaTvInchargeName = binding.etManaTvInchargeName.getText().toString().trim();
                manaTvMobileNo = binding.etManaTvMobileNo.getText().toString().trim();

                noOfComputersAvailable = binding.etNoOfComputersAvailable.getText().toString().trim();
                compWorkingStatus = binding.etCompWorkingStatus.getText().toString().trim();

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
                    academicEntity = new AcademicEntity();
                    academicEntity.setOfficer_id(officerId);
                    academicEntity.setInstitute_id(instId);
                    academicEntity.setInspection_time(Utils.getCurrentDateTime());
                    academicEntity.setHighest_class_syllabus_completed(highest_class_syllabus_completed);
                    academicEntity.setPlan_syll_comp_prepared(plan_syll_comp_prepared);
                    academicEntity.setStrength_accomodated_asper_infrastructure(strength_accomodated_asper_infrastructure);
                    academicEntity.setStaff_accomodated_asper_stud_strength(staff_accomodated_asper_stud_strength);
                    academicEntity.setPlan_prepared(plan_prepared);
                    academicEntity.setTextbooks_rec(textbooks_rec);
                    academicEntity.setAssessment_test_conducted(assessment_test_conducted);

                    academicEntity.setHighest_class_gradeA(highestClassGradeA);
                    academicEntity.setHighest_class_gradeB(highestClassGradeB);
                    academicEntity.setHighest_class_gradeC(highestClassGradeC);
                    academicEntity.setHighest_class_total(highestClassGradeTotal);

                    academicEntity.setLast_yr_ssc_percent(last_yr_ssc_percent);
                    academicEntity.setPunadi_books_supplied(punadi_books_supplied);
                    academicEntity.setSufficient_books_supplied(sufficient_books_supplied);
                    academicEntity.setPunadiPrgmConducted(punadiPrgmConducted);
                    academicEntity.setPunadi_books_req(punadiBooksReq);
                    academicEntity.setPunadiPrgmReason(punadiPrgmReason);
                    academicEntity.setPunadi2_testmarks_entered(punadi2_testmarks_entered);
                    academicEntity.setPunadi2TestmarksReason(punadi2TestmarksReason);
                    academicEntity.setKara_dipath_prgm_cond(kara_dipath_prgm_cond);
                    academicEntity.setKaraDipathPrgmCondReason(karaDipathPrgmCondReason);
                    academicEntity.setLabManuals_received(labManuals_received);
                    academicEntity.setProperly_using_manuals(properly_using_manuals);
                    academicEntity.setLabroom_available(labroom_available);
                    academicEntity.setLabInchargeName(labInchargeName);
                    academicEntity.setLabMobileNo(labMobileNo);
                    academicEntity.setLab_mat_entered_reg(lab_mat_entered_reg);
                    academicEntity.setLab_mat_entered_reg_reason(labMatEnteredReason);

                    academicEntity.setNoOfBooks(noOfBooks);
                    academicEntity.setNameLibraryIncharge(nameLibraryIncharge);
                    academicEntity.setLibraryMobileNo(libraryMobileNo);
                    academicEntity.setMaint_accession_reg(maint_accession_reg);
                    academicEntity.setProper_light_fan(proper_light_fan);

                    academicEntity.setBig_tv_rot_avail(big_tv_rot_avail);
                    academicEntity.setTvRotWorkingStatus(TvRotWorkingStatus);

                    academicEntity.setMana_tv_lessons_shown(mana_tv_lessons_shown);
                    academicEntity.setManaTvLessonsReason(manaTvLessonsReason);
                    academicEntity.setManaTvInchargeName(manaTvInchargeName);
                    academicEntity.setManaTvMobileNo(manaTvMobileNo);


                    academicEntity.setComp_lab_avail(comp_lab_avail);
                    academicEntity.setNoOfComputersAvailable(noOfComputersAvailable);
                    academicEntity.setCompWorkingStatus(compWorkingStatus);


                    academicEntity.setIct_instr_avail(ict_instr_avail);
                    academicEntity.setNameIctInstr(nameIctInstr);
                    academicEntity.setMobNoIctInstr(mobNoIctInstr);


                    academicEntity.setTimetable_disp(timetable_disp);
                    academicEntity.setComp_syll_completed(comp_syll_completed);
                    academicEntity.setComp_lab_cond(comp_lab_cond);
                    academicEntity.setDigital_content_used(digital_content_used);


                    academicEntity.seteLearning_avail(eLearning_avail);
                    academicEntity.setVolSchoolCoordName(volSchoolCoordName);
                    academicEntity.setVolSchoolCoordMobNo(volSchoolCoordMobNo);
                    academicEntity.seteLearningInchargeName(eLearningInchrgName);
                    academicEntity.seteLearningMobNum(eLearningInchrgMobileNo);
                    academicEntity.setSeparate_timetable_disp(separate_timetable_disp);
                    academicEntity.setTabs_supplied(tabs_supplied);
                    academicEntity.setNoOfTabs(noOfTabs);
                    academicEntity.setTabs_stud_using_as_per_sched(stud_using_as_per_sched);
                    academicEntity.setTabInchargeName(tabInchargeName);
                    academicEntity.setTabInchargeMblno(tabInchargeMblno);
                    academicEntity.seteLearning_stud_using_as_per_sched(showing_stud);
                    academicEntity.setAcademicGradeEntities(academicGradeEntities);

                    Utils.customSaveAlert(AcademicActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));

                }
            }
        });
    }


    private boolean validate() {
        if (TextUtils.isEmpty(highest_class_syllabus_completed)) {
            showSnackBar(getString(R.string.high_class_syl));
            ScrollToView(binding.rgHighestClassSyllabusCompleted);
            return false;
        }
        if (highest_class_syllabus_completed.equals(AppConstants.No) && TextUtils.isEmpty(plan_syll_comp_prepared)) {
            showSnackBar(getString(R.string.sel_plan));
            ScrollToView(binding.rgPlanSyllCompPrepared);
            return false;
        }
        if (TextUtils.isEmpty(strength_accomodated_asper_infrastructure)) {
            showSnackBar(getString(R.string.sel_is_strength));
            ScrollToView(binding.rgStrengthAccomodatedAsperInfrastructure);
            return false;
        }
        if (TextUtils.isEmpty(staff_accomodated_asper_stud_strength)) {
            showSnackBar(getString(R.string.select_does_staff));
            ScrollToView(binding.rgStaffAccomodatedAsperStudStrength);
            return false;
        }
        if (TextUtils.isEmpty(plan_prepared)) {
            ScrollToView(binding.rgPlanPrepared);
            showSnackBar(getString(R.string.select_school_plan));
            return false;
        }
        if (TextUtils.isEmpty(textbooks_rec)) {
            ScrollToView(binding.rgTextbooksRec);
            showSnackBar(getString(R.string.sel_suff_books));
            return false;
        }
        if (TextUtils.isEmpty(assessment_test_conducted)) {
            ScrollToView(binding.rgAssessmentTestConducted);
            showSnackBar(getString(R.string.sel_assessments));
            return false;
        }
        if (TextUtils.isEmpty(highestClassGradeA)) {
            binding.highestClassGradeA.requestFocus();
            showSnackBar(getString(R.string.enter_gradea));
            return false;
        }
        if (TextUtils.isEmpty(highestClassGradeB)) {
            binding.highestClassGradeB.requestFocus();
            showSnackBar(getString(R.string.enter_gradeb));
            return false;
        }
        if (TextUtils.isEmpty(highestClassGradeC)) {
            binding.highestClassGradeC.requestFocus();
            showSnackBar(getString(R.string.enter_gradec));
            return false;
        }
        if (TextUtils.isEmpty(highestClassGradeTotal)) {
            binding.highestClassTotal.requestFocus();
            showSnackBar(getString(R.string.enter_total));
            return false;
        }
        if (TextUtils.isEmpty(last_yr_ssc_percent)) {
            binding.lastYrSscPercentEt.requestFocus();
            showSnackBar(getString(R.string.last_year_ssc));
            return false;
        }
        if (TextUtils.isEmpty(punadi_books_supplied)) {
            ScrollToView(binding.rgPunadiBooksSupplied);
            showSnackBar(getString(R.string.sel_pun_books));
            return false;
        }
        if (punadi_books_supplied.equals(AppConstants.Yes) && TextUtils.isEmpty(sufficient_books_supplied)) {
            ScrollToView(binding.rgSufficientBooksSupplied);
            showSnackBar(getString(R.string.sel_suff_books_suplied));
            return false;
        }
        if (TextUtils.isEmpty(punadiPrgmConducted)) {
            ScrollToView(binding.rgPunadiPrgmConducted);
            showSnackBar(getString(R.string.sel_pun2_con));
            return false;
        }
        if (punadiPrgmConducted.equals(AppConstants.No) && TextUtils.isEmpty(punadiPrgmReason)) {
            binding.etPunadiPrgmReason.requestFocus();
            showSnackBar(getString(R.string.punadi_reason));
            return false;
        }

        if (TextUtils.isEmpty(punadi2_testmarks_entered)) {
            ScrollToView(binding.rgPunadi2TestmarksEntered);
            showSnackBar(getString(R.string.sel_punadi2_test_marks));
            return false;
        }

        if (punadi2_testmarks_entered.equals(AppConstants.No) && TextUtils.isEmpty(punadi2TestmarksReason)) {
            binding.etPunadi2TestmarksReason.requestFocus();
            showSnackBar(getString(R.string.enter_punadi2_reason));
            return false;
        }

        if (TextUtils.isEmpty(kara_dipath_prgm_cond)) {
            ScrollToView(binding.rgKaraDipathPrgmCond);
            showSnackBar(getString(R.string.sel_kara));
            return false;
        }
        if (kara_dipath_prgm_cond.equals(AppConstants.No) && TextUtils.isEmpty(kara_dipath_prgm_cond)) {
            binding.etKaraDipathPrgmCond.requestFocus();
            showSnackBar(getString(R.string.enter_kara_reason));
            return false;
        }
        if (TextUtils.isEmpty(labManuals_received)) {
            ScrollToView(binding.rgLabManualsReceived);
            showSnackBar(getString(R.string.enter_ssci_lab));
            return false;
        }
        if (labManuals_received.equals(AppConstants.Yes) && TextUtils.isEmpty(properly_using_manuals)) {
            ScrollToView(binding.rgProperlyUsingLabManuals);
            showSnackBar(getString(R.string.sel_pro_using_sci_lab));
            return false;
        }
        if (TextUtils.isEmpty(labroom_available)) {
            ScrollToView(binding.rgLabRoomAvailable);
            showSnackBar(getString(R.string.sel_sci_lab_avail));
            return false;
        }
        if (labroom_available.equals(AppConstants.Yes) && TextUtils.isEmpty(labInchargeName)) {
            binding.etLabInchargeName.requestFocus();
            showSnackBar(getString(R.string.name_sci_lab_incharge));
            return false;
        }

        if (labroom_available.equals(AppConstants.Yes) && TextUtils.isEmpty(labMobileNo)) {
            binding.etLabMobileNo.requestFocus();
            showSnackBar(getString(R.string.lab_mob_num));
            return false;
        }


        if (labroom_available.equals(AppConstants.Yes) && labMobileNo.length() != 10) {
            binding.etLabMobileNo.requestFocus();
            showSnackBar(getString(R.string.enter_valid_sci_lab_number));
            return false;
        }


        if (labroom_available.equals(AppConstants.Yes)
                && !(labMobileNo.startsWith("9") || labMobileNo.startsWith("8") || labMobileNo.startsWith("7") ||
                labMobileNo.startsWith("6"))) {
            binding.etLabMobileNo.requestFocus();
            showSnackBar(getString(R.string.enter_valid_sci_lab_number));
            return false;
        }


        if (TextUtils.isEmpty(lab_mat_entered_reg)) {
            ScrollToView(binding.rgLabMatEnteredReg);
            showSnackBar(getString(R.string.sel_lab_mat_entered));
            return false;
        }
        if (lab_mat_entered_reg.equals(AppConstants.No) && TextUtils.isEmpty(labMatEnteredReason)) {
            binding.etMatEnterRegReason.requestFocus();
            showSnackBar(getString(R.string.lab_mat_reason));
            return false;
        }
        if (TextUtils.isEmpty(maint_accession_reg)) {
            ScrollToView(binding.rgMaintLibReg);
            showSnackBar(getString(R.string.mai_ass_reg));
            return false;
        }
        if (maint_accession_reg.equals(AppConstants.Yes) && TextUtils.isEmpty(noOfBooks)) {
            binding.etNoOfBooks.requestFocus();
            showSnackBar(getString(R.string.enter_num_of_books));
            return false;
        }
        if (TextUtils.isEmpty(nameLibraryIncharge)) {
            binding.etNameLibraryIncharge.requestFocus();
            showSnackBar(getString(R.string.library_incharge));
            return false;
        }
        if (TextUtils.isEmpty(libraryMobileNo)) {
            binding.etLibraryMobileNo.requestFocus();
            showSnackBar(getString(R.string.library_mob_num));
            return false;
        }

        if (libraryMobileNo.length() != 10) {
            binding.etLibraryMobileNo.requestFocus();
            showSnackBar(getString(R.string.valid_lib_mob_num));
            return false;
        }


        if (!TextUtils.isEmpty(libraryMobileNo)
                && !(libraryMobileNo.startsWith("9") || libraryMobileNo.startsWith("8") || libraryMobileNo.startsWith("7") ||
                libraryMobileNo.startsWith("6"))) {
            binding.etLibraryMobileNo.requestFocus();
            showSnackBar(getString(R.string.valid_lib_mob_num));
            return false;
        }

        if (TextUtils.isEmpty(proper_light_fan)) {
            ScrollToView(binding.rgProperLightFan);
            showSnackBar(getString(R.string.light_fan_avail));
            return false;
        }

        if (TextUtils.isEmpty(big_tv_rot_avail)) {
            ScrollToView(binding.rgBigTvRotAvail);
            showSnackBar(getString(R.string.big_tv_avail));
            return false;
        }
        if (big_tv_rot_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(TvRotWorkingStatus)) {
            ScrollToView(binding.rgBigTvRotAvailCon);
            showSnackBar(getString(R.string.big_tv_status));
            return false;
        }

        if (TextUtils.isEmpty(mana_tv_lessons_shown)) {
            ScrollToView(binding.rgManaTvLessonsShown);
            showSnackBar(getString(R.string.mana_tv_lessions));
            return false;
        }
        if (mana_tv_lessons_shown.equals(AppConstants.No) && TextUtils.isEmpty(manaTvLessonsReason)) {
            binding.etManaTvLessonsReason.requestFocus();
            showSnackBar(getString(R.string.mana_tv_reason));
            return false;
        }
        if (TextUtils.isEmpty(manaTvInchargeName)) {
            binding.etManaTvInchargeName.requestFocus();
            showSnackBar(getString(R.string.mana_tv_incharge));
            return false;
        }
        if (TextUtils.isEmpty(manaTvMobileNo)) {
            binding.etManaTvMobileNo.requestFocus();
            showSnackBar(getString(R.string.mana_tv_mob_num));
            return false;
        }

        if (manaTvMobileNo.length() != 10) {
            binding.etManaTvMobileNo.requestFocus();
            showSnackBar(getString(R.string.valid_mana_tv_mob_num));
            return false;
        }

        if (!(manaTvMobileNo.startsWith("9") || manaTvMobileNo.startsWith("8") || manaTvMobileNo.startsWith("7") ||
                manaTvMobileNo.startsWith("6"))) {
            binding.etManaTvMobileNo.requestFocus();
            showSnackBar(getString(R.string.valid_mana_tv_mob_num));
            return false;
        }

        if (TextUtils.isEmpty(comp_lab_avail)) {
            ScrollToView(binding.rgCompLabAvail);
            showSnackBar(getString(R.string.com_lab_avail));
            return false;
        }

        if (comp_lab_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(noOfComputersAvailable)) {
            binding.etNoOfComputersAvailable.requestFocus();
            showSnackBar(getString(R.string.num_of_com));
            return false;
        }
        if (comp_lab_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(compWorkingStatus)) {
            binding.etCompWorkingStatus.requestFocus();
            showSnackBar(getString(R.string.com_status));
            return false;
        }
        if (comp_lab_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(ict_instr_avail)) {
            ScrollToView(binding.rgIctInstrAvail);
            showSnackBar(getString(R.string.ict_avail));
            return false;
        }
        if (comp_lab_avail.equals(AppConstants.Yes) && ict_instr_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(nameIctInstr)) {
            binding.etNameIctInstr.requestFocus();
            showSnackBar(getString(R.string.ict_name));
            return false;
        }
        if (comp_lab_avail.equals(AppConstants.Yes) && ict_instr_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(mobNoIctInstr)) {
            binding.etMobNoIctInstr.requestFocus();
            showSnackBar(getString(R.string.ict_mob));
            return false;
        }

        if (comp_lab_avail.equals(AppConstants.Yes) && ict_instr_avail.equals(AppConstants.Yes) && mobNoIctInstr.length() != 10) {
            binding.etMobNoIctInstr.requestFocus();
            showSnackBar(getString(R.string.valid_ict_mob));
            return false;
        }

        if (comp_lab_avail.equals(AppConstants.Yes) && ict_instr_avail.equals(AppConstants.Yes)
                && !(mobNoIctInstr.startsWith("9") || mobNoIctInstr.startsWith("8") || mobNoIctInstr.startsWith("7") ||
                mobNoIctInstr.startsWith("6"))) {
            binding.etMobNoIctInstr.requestFocus();
            showSnackBar(getString(R.string.valid_ict_mob));
            return false;
        }

        if (comp_lab_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(timetable_disp)) {
            ScrollToView(binding.rgTimetableDisp);
            showSnackBar(getString(R.string.sel_timetable));
            return false;
        }
        if (comp_lab_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(comp_syll_completed)) {
            ScrollToView(binding.rgCompSyllCompleted);
            showSnackBar(getString(R.string.com_syll_com));
            return false;
        }
        if (comp_lab_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(comp_lab_cond)) {
            ScrollToView(binding.rgCompLabCond);
            showSnackBar(getString(R.string.com_lab_status));
            return false;
        }
        if (comp_lab_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(digital_content_used)) {
            ScrollToView(binding.rgDigitalContentUsed);
            showSnackBar(getString(R.string.sel_digital_content));
            return false;
        }

        if (TextUtils.isEmpty(eLearning_avail)) {
            ScrollToView(binding.rgELearningAvail);
            showSnackBar(getString(R.string.eLe_avail));
            return false;
        }
        if (eLearning_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(showing_stud)) {
            ScrollToView(binding.rgShowingStud);
            showSnackBar(getString(R.string.sel_sho_stu));
            return false;
        }
        if (eLearning_avail.equals(AppConstants.Yes) && showing_stud.equals(AppConstants.Yes) && TextUtils.isEmpty(volSchoolCoordName)) {
            binding.etVolSchoolCoordName.requestFocus();
            showSnackBar(getString(R.string.sch_cor_name));
            return false;
        }
        if (eLearning_avail.equals(AppConstants.Yes) && showing_stud.equals(AppConstants.Yes)
                && TextUtils.isEmpty(volSchoolCoordMobNo)) {
            binding.etVolSchoolCoordMobNo.requestFocus();
            showSnackBar(getString(R.string.sch_cor_mob));
            return false;
        }

        if (eLearning_avail.equals(AppConstants.Yes) && showing_stud.equals(AppConstants.Yes) && volSchoolCoordMobNo.length() != 10) {
            binding.etVolSchoolCoordMobNo.requestFocus();
            showSnackBar(getString(R.string.valid_sch_cor_mob_num));
            return false;
        }

        if (eLearning_avail.equals(AppConstants.Yes) && showing_stud.equals(AppConstants.Yes)
                && !(volSchoolCoordMobNo.startsWith("9") || volSchoolCoordMobNo.startsWith("8") || volSchoolCoordMobNo.startsWith("7") ||
                volSchoolCoordMobNo.startsWith("6"))) {
            binding.etVolSchoolCoordMobNo.requestFocus();
            showSnackBar(getString(R.string.valid_sch_cor_mob_num));
            return false;
        }

        if (eLearning_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(eLearningInchrgName)) {
            binding.etELearningInchrgName.requestFocus();
            showSnackBar(getString(R.string.eLe_incha_name));
            return false;
        }
        if (eLearning_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(eLearningInchrgMobileNo)) {
            binding.etELearningInchrgMobileNo.requestFocus();
            showSnackBar(getString(R.string.eLe_inch_mob));
            return false;
        }

        if (eLearning_avail.equals(AppConstants.Yes) && eLearningInchrgMobileNo.length() != 10) {
            binding.etELearningInchrgMobileNo.requestFocus();
            showSnackBar(getString(R.string.valid_eLe_inch_mob));
            return false;
        }

        if (eLearning_avail.equals(AppConstants.Yes)
                && !(eLearningInchrgMobileNo.startsWith("9") || eLearningInchrgMobileNo.startsWith("8") || eLearningInchrgMobileNo.startsWith("7") ||
                eLearningInchrgMobileNo.startsWith("6"))) {
            binding.etELearningInchrgMobileNo.requestFocus();
            showSnackBar(getString(R.string.valid_eLe_inch_mob));
            return false;
        }


        if (eLearning_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(separate_timetable_disp)) {
            ScrollToView(binding.rgSeparateTimetableDisp);
            showSnackBar(getString(R.string.sel_sep_timetable));
            return false;
        }

        if (eLearning_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(tabs_supplied)) {
            ScrollToView(binding.rgTabsSupplied);
            showSnackBar(getString(R.string.sel_tabs_supplied));
            return false;
        }
        if (eLearning_avail.equals(AppConstants.Yes) && tabs_supplied.equals(AppConstants.Yes) && TextUtils.isEmpty(noOfTabs)) {
            binding.etNoOfTabs.requestFocus();
            showSnackBar(getString(R.string.num_of_tabs));
            return false;
        }
        if (eLearning_avail.equals(AppConstants.Yes) && tabs_supplied.equals(AppConstants.Yes) && TextUtils.isEmpty(stud_using_as_per_sched)) {
            ScrollToView(binding.rgStudUsingAsPerSched);
            showSnackBar(getString(R.string.sel_stu_using));
            return false;
        }

        if (eLearning_avail.equals(AppConstants.Yes) && tabs_supplied.equals(AppConstants.Yes) && TextUtils.isEmpty(tabInchargeName)) {
            binding.etTabInchargeName.requestFocus();
            showSnackBar(getString(R.string.tab_incharge));
            return false;
        }
        if (eLearning_avail.equals(AppConstants.Yes) && tabs_supplied.equals(AppConstants.Yes) && TextUtils.isEmpty(tabInchargeMblno)) {
            binding.etTabInchargeMblno.requestFocus();
            showSnackBar(getString(R.string.tab_mob_num));
            return false;
        }


        if (eLearning_avail.equals(AppConstants.Yes) && tabs_supplied.equals(AppConstants.Yes) && eLearningInchrgMobileNo.length() != 10) {
            binding.etTabInchargeMblno.requestFocus();
            showSnackBar(getString(R.string.valid_incharge_mob_num));
            return false;
        }

        if (eLearning_avail.equals(AppConstants.Yes) && tabs_supplied.equals(AppConstants.Yes)
                && !(eLearningInchrgMobileNo.startsWith("9") || eLearningInchrgMobileNo.startsWith("8") || eLearningInchrgMobileNo.startsWith("7") ||
                eLearningInchrgMobileNo.startsWith("6"))) {
            binding.etTabInchargeMblno.requestFocus();
            showSnackBar(getString(R.string.valid_incharge_mob_num));
            return false;
        }

        return true;
    }

    private void ScrollToView(View view) {
        view.getParent().requestChildFocus(view, view);

    }


    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void submitData() {
        long x = academicViewModel.insertAcademicInfo(academicEntity);
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
        if (academicGradeEntities != null && academicGradeEntities.size() > 0 && !(localFlag == 1)) {
            customExitAlert(AcademicActivity.this, getString(R.string.app_name), getString(R.string.data_lost));
        } else {
            super.callBack();
        }
    }

    private void customExitAlert(Activity activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_exit);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button exit = dialog.findViewById(R.id.btDialogExit);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        academicViewModel.deleteGradeInfo();
                        finish();
                    }
                });

                Button cancel = dialog.findViewById(R.id.btDialogCancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
}
