package com.cgg.twdinspection.schemes.interfaces;

import com.cgg.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;
import com.cgg.twdinspection.schemes.source.submit.SchemeSubmitResponse;

public interface SchemeSubmitInterface {
    void getData(SchemeSubmitResponse schemeSubmitResponse);

    void getPhotoData(SchemePhotoSubmitResponse schemePhotoSubmitResponse);
}
