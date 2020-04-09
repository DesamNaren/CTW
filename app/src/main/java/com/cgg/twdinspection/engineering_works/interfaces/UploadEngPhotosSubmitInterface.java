package com.cgg.twdinspection.engineering_works.interfaces;

import com.cgg.twdinspection.engineering_works.source.SubmitEngWorksResponse;
import com.cgg.twdinspection.gcc.source.submit.GCCPhotoSubmitResponse;
import com.cgg.twdinspection.gcc.source.submit.GCCSubmitResponse;

public interface UploadEngPhotosSubmitInterface {
    void getData(SubmitEngWorksResponse gccSubmitResponse);
    void getPhotoData(GCCPhotoSubmitResponse gccPhotoSubmitResponse);
}
