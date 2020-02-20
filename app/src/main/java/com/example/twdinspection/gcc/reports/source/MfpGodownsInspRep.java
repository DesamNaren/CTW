package com.example.twdinspection.gcc.reports.source;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.twdinspection.gcc.source.inspections.MFPGodowns.MFPGeneralFindings;
import com.example.twdinspection.gcc.source.inspections.MFPGodowns.MFPRegisterBookCertificates;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MfpGodownsInspRep {

    @SerializedName("register_book_certificates")
    @Expose
    private MFPRegisterBookCertificates registerBookCertificates;
    @SerializedName("general_findings")
    @Expose
    private MFPGeneralFindings generalFindings;

    public MFPRegisterBookCertificates getRegisterBookCertificates() {
        return registerBookCertificates;
    }

    public void setRegisterBookCertificates(MFPRegisterBookCertificates registerBookCertificates) {
        this.registerBookCertificates = registerBookCertificates;
    }

    public MFPGeneralFindings getGeneralFindings() {
        return generalFindings;
    }

    public void setGeneralFindings(MFPGeneralFindings generalFindings) {
        this.generalFindings = generalFindings;
    }

    @BindingAdapter("profileImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }
}
