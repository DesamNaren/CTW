package com.cgg.twdinspection.common;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private final MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private final LiveData<String> mText = Transformations.map(mIndex, input -> {
        String urlValue;
        if (input == 1) {
            urlValue = "https://www.cgg.gov.in/mgov-privacy-policy/?depot_name=Centre for Good Governance (CGG), Govt. of Telangana"; //Privacy Policy
        } else if (input == 2) {
            urlValue = "https://www.cgg.gov.in/mgov-terms-conditions/?depot_name=Centre for Good Governance (CGG), Govt. of Telangana&capital=Hyderabad, Telangana";//Terms & Conditions
        } else if (input == 3) {
            urlValue = "https://www.cgg.gov.in/mgov-copyright-policy/?depot_name=Centre for Good Governance (CGG), Govt. of Telangana&depot_email=info@cgg.gov.in";//Copyright Policy
        } else {
            urlValue = "https://www.cgg.gov.in/mgov-privacy-policy/?depot_name=Centre for Good Governance (CGG), Govt. of Telangana"; //Privacy Policy
        }
        return urlValue;
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}