package com.example.twdinspection.inspection.source;

public class LoginUser {
    private String mName;
    private String mPassword;


    public LoginUser(String name, String password) {
        mName = name;
        mPassword = password;
    }

    public String getEmail() {
        if (mName == null) {
            return "";
        }
        return mName;
    }


    public String getPassword() {

        if (mPassword == null) {
            return "";
        }
        return mPassword;
    }
}
