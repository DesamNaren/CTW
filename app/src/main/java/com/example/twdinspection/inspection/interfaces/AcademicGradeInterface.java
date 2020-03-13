package com.example.twdinspection.inspection.interfaces;

import com.example.twdinspection.inspection.source.academic_overview.AcademicGradeEntity;
import com.example.twdinspection.inspection.source.student_attendence_info.StudAttendInfoEntity;

import java.util.List;

public interface AcademicGradeInterface {
    void submitData(List<AcademicGradeEntity> academicGradeEntities);
}
