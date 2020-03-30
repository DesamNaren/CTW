package com.cgg.twdinspection.gcc.reports.source;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.gcc.source.inspections.godown.DrGodownGeneralFindings;
import com.cgg.twdinspection.gcc.source.inspections.godown.DrGodownRegisterBookCertificates;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrGodownInspReport {

    @SerializedName("stock_details")
    @Expose
    private DrGodownReportStockDetails stockDetails;
    @SerializedName("register_book_certificates")
    @Expose
    private DrGodownRegisterBookCertificates registerBookCertificates;
    @SerializedName("general_findings")
    @Expose
    private DrGodownGeneralFindings generalFindings;

    public DrGodownReportStockDetails getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(DrGodownReportStockDetails stockDetails) {
        this.stockDetails = stockDetails;
    }

    public DrGodownRegisterBookCertificates getRegisterBookCertificates() {
        return registerBookCertificates;
    }

    public void setRegisterBookCertificates(DrGodownRegisterBookCertificates registerBookCertificates) {
        this.registerBookCertificates = registerBookCertificates;
    }

    public DrGodownGeneralFindings getGeneralFindings() {
        return generalFindings;
    }

    public void setGeneralFindings(DrGodownGeneralFindings generalFindings) {
        this.generalFindings = generalFindings;
    }

    @BindingAdapter("profileImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.camera)
                .error(R.drawable.ic_menu_camera)
                .into(view);
    }

}
