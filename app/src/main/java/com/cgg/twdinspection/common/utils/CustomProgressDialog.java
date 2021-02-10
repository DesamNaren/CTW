package com.cgg.twdinspection.common.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cgg.twdinspection.R;

public class CustomProgressDialog extends Dialog {
    private CustomProgressDialog mDialog;
    TextView tv_loading;

    public CustomProgressDialog(Context context) {
        super(context);
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            View view = LayoutInflater.from(context).inflate(R.layout.custom_progress_layout, null);
            ImageView imageprogress = view.findViewById(R.id.imageprogress);
            tv_loading = view.findViewById(R.id.tv_loading);
            Glide.with(context).load(R.drawable.loader_black1).into(imageprogress);
            //  customProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(view);
            if (getWindow() != null)
                this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setCancelable(false);
            setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addText(String msg) {
        tv_loading.setText(msg);
    }

}
