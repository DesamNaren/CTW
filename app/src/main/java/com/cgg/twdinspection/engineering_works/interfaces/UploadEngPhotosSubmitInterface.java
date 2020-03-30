package com.cgg.twdinspection.engineering_works.interfaces;

import com.cgg.twdinspection.gcc.source.submit.GCCPhotoSubmitResponse;
import com.cgg.twdinspection.gcc.source.submit.GCCSubmitResponse;

public interface UploadEngPhotosSubmitInterface {
    void getData(GCCSubmitResponse gccSubmitResponse);
    void getPhotoData(GCCPhotoSubmitResponse gccPhotoSubmitResponse);
}
