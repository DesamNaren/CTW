package com.cgg.twdinspection.gcc.reports.source;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.cgg.twdinspection.R;
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
