package com.example.twdinspection.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class Utils {
    public static void hideKeyboard(Context context){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
