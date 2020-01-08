package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.twdinspection.R;
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
                if (selctedItem == R.id.highest_class_syllabus_completed_yes)
                    highest_class_syllabus_completed = "YES";
                else
                    highest_class_syllabus_completed = "NO";
            }
        });
        binding.rgStrengthAccomodatedAsperInfrastructure.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStrengthAccomodatedAsperInfrastructure.getCheckedRadioButtonId();
                if (selctedItem == R.id.strength_accomodated_asper_infrastructure_yes)
                    strength_accomodated_asper_infrastructure = "YES";
                else
                    strength_accomodated_asper_infrastructure = "NO";
            }
        });
        binding.rgStaffAccomodatedAsperStudStrength.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStaffAccomodatedAsperStudStrength.getCheckedRadioButtonId();
                if (selctedItem == R.id.staff_accomodated_asper_stud_strength_yes)
                    staff_accomodated_asper_stud_strength = "YES";
                else
                    staff_accomodated_asper_stud_strength = "NO";
            }
        });
        binding.rgPlanPrepared.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPlanPrepared.getCheckedRadioButtonId();
                if (selctedItem == R.id.plan_prepared_yes)
                    plan_prepared = "YES";
                else
                    plan_prepared = "NO";
            }
        });
        binding.rgAssessmentTestConducted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAssessmentTestConducted.getCheckedRadioButtonId();
                if (selctedItem == R.id.assessment_test_conducted_yes)
                    assessment_test_conducted = "YES";
                else
                    assessment_test_conducted = "NO";
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

                startActivity(new Intent(AcademicActivity.this, CoCurricularActivity.class));
            }
        });
    }
}
