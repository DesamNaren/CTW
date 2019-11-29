package com.example.twdinspection.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.source.LoginUser;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> errorName = new MutableLiveData<>();
    public MutableLiveData<String> errorPassword = new MutableLiveData<>();

    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<Integer> busy;

    private MutableLiveData<LoginUser> userMutableLiveData;

    public MutableLiveData<Integer> getBusy() {
        if (busy == null) {
            busy = new MutableLiveData<>();
            busy.setValue(8);
        }
        return busy;
    }


    public LoginViewModel() {

    }

    public LiveData<LoginUser> getUser() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }


    public void onBtnClick() {

        getBusy().setValue(0); //View.VISIBLE
        LoginUser user = new LoginUser(username.getValue(), password.getValue());
        if (TextUtils.isEmpty(user.getEmail())) {
            errorName.setValue("Please enter username");
            busy.setValue(8); //8 == View.GONE
        } else {
            errorName.setValue(null);
        }
        if (TextUtils.isEmpty(user.getPassword())) {
            errorPassword.setValue("Please enter password");
            busy.setValue(8); //8 == View.GONE
        } else {
            errorPassword.setValue(null);
        }
        userMutableLiveData.setValue(user);
    }
}
