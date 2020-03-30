package com.cgg.twdinspection.gcc.interfaces;

import com.cgg.twdinspection.gcc.source.submit.GCCPhotoSubmitResponse;
import com.cgg.twdinspection.gcc.source.submit.GCCSubmitResponse;

public interface GCCSubmitInterface {
    void getData(GCCSubmitResponse gccSubmitResponse);
    void getPhotoData(GCCPhotoSubmitResponse gccPhotoSubmitResponse);
}
