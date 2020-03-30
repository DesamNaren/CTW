package com.cgg.twdinspection.inspection.interfaces;

import com.cgg.twdinspection.inspection.source.academic_overview.AcademicGradeEntity;

import java.util.List;

public interface AcademicGradeInterface {
    void submitData(List<AcademicGradeEntity> academicGradeEntities);
}
