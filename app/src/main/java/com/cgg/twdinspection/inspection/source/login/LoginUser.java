package com.cgg.twdinspection.inspection.source.login;

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
