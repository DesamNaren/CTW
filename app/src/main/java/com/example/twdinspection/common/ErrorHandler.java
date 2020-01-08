package com.example.twdinspection.common;

import android.content.Context;

import com.example.twdinspection.R;
import com.example.twdinspection.common.utils.Utils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public abstract class ErrorHandler {
    public static String handleError(Throwable e, Context context) {
        String msg = "";
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            msg = Utils.getErrorMessage(responseBody);
        } else if (e instanceof SocketTimeoutException) {
            msg = context.getString(R.string.con_time_out);
        } else if (e instanceof IOException) {
            msg = context.getString(R.string.something) +
                    context.getString(R.string.io_exe);
        } else {
            msg = context.getString(R.string.server_not)
                    + ": " + e.getMessage();
        }
        return msg;
    }
}
