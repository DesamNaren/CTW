package com.example.twdinspection.inspection.interfaces;

import com.example.twdinspection.inspection.source.inst_master.MasterClassInfo;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;

public interface StudAttendInterface {
    void openBottomSheet(StudAttendInfoEntity masterClassInfo);
}
