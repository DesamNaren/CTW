package com.example.twdinspection.engineering_works.interfaces;

import com.example.twdinspection.gcc.source.submit.GCCPhotoSubmitResponse;
import com.example.twdinspection.gcc.source.submit.GCCSubmitResponse;

public interface UploadEngPhotosSubmitInterface {
    void getData(GCCSubmitResponse gccSubmitResponse);
    void getPhotoData(GCCPhotoSubmitResponse gccPhotoSubmitResponse);
}
