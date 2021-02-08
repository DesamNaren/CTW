package com.cgg.twdinspection.offline.interfaces;

import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;

public interface GCCOfflineSubmitInterface {
    void submitRecord(GccOfflineEntity entity);
    void deleteRecord(GccOfflineEntity entity);
}