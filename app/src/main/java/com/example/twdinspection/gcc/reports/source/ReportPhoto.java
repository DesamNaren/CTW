package com.example.twdinspection.gcc.reports.source;

import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.CustomProgressDialog;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportPhoto {

    @SerializedName("file_path")
    @Expose
    private String filePath;
    @SerializedName("file_name")
    @Expose
    private String fileName;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @BindingAdapter("profileImage")
    public static void loadImage(ImageView view, String imageUrl) {

        Glide.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.loader_black1)
                .into(view);
    }
}
