package com.cgg.twdinspection.inspection.viewmodel;

import android.view.View;

public class SerialNumber {

    public static int SL_NO;

    public static String setSlNo() {
        SL_NO = SL_NO + 1;
        return SL_NO + ".";
    }

    public static void resetValue(int slno) {
        SL_NO = slno;
    }

}
