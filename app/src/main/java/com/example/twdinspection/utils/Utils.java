package com.example.twdinspection.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utils {
    public static void hideKeyboard(Context context, View mView){
        try {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
