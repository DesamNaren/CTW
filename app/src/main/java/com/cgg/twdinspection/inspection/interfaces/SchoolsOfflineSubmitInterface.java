package com.cgg.twdinspection.inspection.interfaces;

import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.inspection.offline.SchoolsOfflineEntity;

public interface SchoolsOfflineSubmitInterface {
    void submitRecord(SchoolsOfflineEntity entity);

    void deleteRecord(SchoolsOfflineEntity entity);
}