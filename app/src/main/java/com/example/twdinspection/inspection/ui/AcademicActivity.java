package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.twdinspection.R;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityAcademicBinding;
import com.example.twdinspection.inspection.source.AcademicOverview.AcademicOveriewEntity;
import com.example.twdinspection.inspection.source.InfrastructureAndMaintenance.InfraStructureEntity;
import com.example.twdinspection.inspection.viewmodel.AcademicCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.AcademicViewModel;
import com.example.twdinspection.inspection.viewmodel.InfraCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.InfraViewModel;

public class AcademicActivity extends AppCompatActivity {
    ActivityAcademicBinding binding;
    AcademicViewModel academicViewModel;
    AcademicOveriewEntity academicOveriewEntity;
    String highest_class_syllabus_completed, strength_accomodated_asper_infrastructure, staff_accomodated_asper_stud_strength, plan_prepared, assessment_test_conducted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_academic);
        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText("Academic Overview");

        academicViewModel = ViewModelProviders.of(AcademicActivity.this,
                new AcademicCustomViewModel(binding, this, getApplication())).get(AcademicViewModel.class);
        binding.setViewModel(academicViewModel);

        binding.rgHighestClassSyllabusCompleted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHighestClassSyllabusCompleted.getCheckedRadioButtonId();
                if (selctedItem == R.id.highest_class_syllabus_completed_yes){
                    highest_class_syllabus_completed = AppConstants.Yes;
                    binding.llPlanCompSyll.setVisibility(View.GONE);
                } else {
                    binding.llPlanCompSyll.setVisibility(View.VISIBLE);
                    highest_class_syllabus_completed = AppConstants.No;
                }
            }
        });
        binding.rgPunadiBooksSupplied.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPunadiBooksSupplied.getCheckedRadioButtonId();
                if (selctedItem == R.id.punadi_books_supplied_yes){
                    binding.llSufficientBooksSupplied.setVisibility(View.VISIBLE);
                } else {
                    binding.llSufficientBooksSupplied.setVisibility(View.GONE);
                }
            }
        });
        binding.rgPunadiPrgmConducted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPunadiPrgmConducted.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_punadi_prgm_conducted_yes){
                    binding.punadiPrgmReasonEt.setVisibility(View.GONE);
                } else if(selctedItem == R.id.rb_punadi_prgm_conducted_no) {
                    binding.punadiPrgmReasonEt.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.rgPunadi2TestmarksEntered.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPunadi2TestmarksEntered.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_punadi2_testmarks_entered_yes){
                    binding.punadi2TestmarksReasonEt.setVisibility(View.GONE);
                } else {
                    binding.punadi2TestmarksReasonEt.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.rgKaraDipathPrgmCond.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgKaraDipathPrgmCond.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_kara_dipath_prgm_cond_yes){
                    binding.karaDipathPrgmCondEt.setVisibility(View.GONE);
                } else {
                    binding.karaDipathPrgmCondEt.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.rgStrengthAccomodatedAsperInfrastructure.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStrengthAccomodatedAsperInfrastructure.getCheckedRadioButtonId();
                if (selctedItem == R.id.strength_accomodated_asper_infrastructure_yes)
                    strength_accomodated_asper_infrastructure = AppConstants.Yes;
                else
                    strength_accomodated_asper_infrastructure = AppConstants.No;
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
                else
                    plan_prepared = AppConstants.No;
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
                if (selctedItem == R.id.rb_labManuals_received_yes)
                    binding.llProperlyUsingManuals.setVisibility(View.VISIBLE);
                else
                    binding.llProperlyUsingManuals.setVisibility(View.GONE);
            }
        });
        binding.rgLabRoomAvailable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgLabRoomAvailable.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_labroom_available_yes)
                    binding.llNameScienceLab.setVisibility(View.VISIBLE);
                else
                    binding.llNameScienceLab.setVisibility(View.GONE);
            }
        });
        binding.rgLabMatEnteredReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgLabMatEnteredReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_lab_mat_entered_reg_yes)
                    binding.matEnterRegReasonEt.setVisibility(View.GONE);
                else
                    binding.matEnterRegReasonEt.setVisibility(View.VISIBLE);
            }
        });

        binding.rgLibraryRoomAvailable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgLibraryRoomAvailable.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_library_room_available_yes)
                    binding.llNoBooksAvailable.setVisibility(View.VISIBLE);
                else
                    binding.llNoBooksAvailable.setVisibility(View.GONE);
            }
        });

        binding.rgBigTvRotAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBigTvRotAvail.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_big_tv_rot_avail_yes)
                    binding.tvRotWorkingStatusEt.setVisibility(View.VISIBLE);
                else
                    binding.tvRotWorkingStatusEt.setVisibility(View.GONE);
            }
        });
        binding.rgManaTvLessonsShown.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgManaTvLessonsShown.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_mana_tv_lessons_shown_yes)
                    binding.llManaTvLessonsReason.setVisibility(View.GONE);
                else
                    binding.llManaTvLessonsReason.setVisibility(View.VISIBLE);
            }
        });
        binding.rgCompLabAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCompLabAvail.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_comp_lab_avail_yes)
                    binding.llComputerLab.setVisibility(View.VISIBLE);
                else
                    binding.llComputerLab.setVisibility(View.GONE);
            }
        });
        binding.rgIctInstrAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgIctInstrAvail.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_ict_instr_avail_yes)
                    binding.llIctInstr.setVisibility(View.VISIBLE);
                else
                    binding.llIctInstr.setVisibility(View.GONE);
            }
        });
        binding.rgELearningAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgELearningAvail.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_eLearning_avail_yes)
                    binding.llShowingToStud.setVisibility(View.VISIBLE);
                else
                    binding.llShowingToStud.setVisibility(View.GONE);
            }
        });
        binding.rgShowingStud.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgShowingStud.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_showing_stud_yes)
                    binding.llNameVolSchool.setVisibility(View.VISIBLE);
                else
                    binding.llNameVolSchool.setVisibility(View.GONE);
            }
        });
        binding.rgTabsSupplied.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgTabsSupplied.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_tabs_supplied_yes)
                    binding.noOfTabsEt.setVisibility(View.VISIBLE);
                else
                    binding.noOfTabsEt.setVisibility(View.GONE);
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String highestClassGradeA = binding.highestClassGradeA.getText().toString().trim();
                String highestClassGradeB= binding.highestClassGradeB.getText().toString().trim();
                String highestClassGradeC = binding.highestClassGradeC.getText().toString().trim();
                String highestClassGradeTotal = binding.highestClassTotal.getText().toString().trim();

                academicOveriewEntity=new AcademicOveriewEntity();
                academicOveriewEntity.setHighest_class_syllabus_completed(highest_class_syllabus_completed);
                academicOveriewEntity.setStrength_accomodated_asper_infrastructure(strength_accomodated_asper_infrastructure);
                academicOveriewEntity.setStaff_accomodated_asper_stud_strength(staff_accomodated_asper_stud_strength);
                academicOveriewEntity.setPlan_prepared(plan_prepared);
                academicOveriewEntity.setHighest_class_gradeA(highestClassGradeA);
                academicOveriewEntity.setHighest_class_gradeB(highestClassGradeB);
                academicOveriewEntity.setHighest_class_gradeC(highestClassGradeC);
                academicOveriewEntity.setHighest_class_total(highestClassGradeTotal);
                academicOveriewEntity.setAssessment_test_conducted(assessment_test_conducted);

                long x = academicViewModel.insertAcademicInfo(academicOveriewEntity);
//                Toast.makeText(AcademicActivity.this, "Inserted " + x, Toast.LENGTH_SHORT).show();


                startActivity(new Intent(AcademicActivity.this, CoCurricularActivity.class));
            }
        });
    }
}
