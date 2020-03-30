package com.cgg.twdinspection.gcc.source.stock;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonCommodity implements Parcelable {

    private boolean flag;
    private String comHeader;
    private String phyQuant;
    private String comType;

    public String getPhyQuant() {
        return phyQuant;
    }

    public void setPhyQuant(String phyQuant) {
        this.phyQuant = phyQuant;
    }

    @SerializedName("commName")
    @Expose
    private String commName;
    @SerializedName("commCode")
    @Expose
    private String commCode;
    @SerializedName("units")
    @Expose
    private String units;
    @SerializedName("qty")
    @Expose
    private Double qty;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("amount")
    @Expose
    private Double amount;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getComHeader() {
        return comHeader;
    }

    public void setComHeader(String comHeader) {
        this.comHeader = comHeader;
    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public String getCommCode() {
        return commCode;
    }

    public void setCommCode(String commCode) {
        this.commCode = commCode;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.flag ? (byte) 1 : (byte) 0);
        dest.writeString(this.comHeader);
        dest.writeString(this.commName);
        dest.writeString(this.commCode);
        dest.writeString(this.units);
        dest.writeValue(this.qty);
        dest.writeValue(this.rate);
        dest.writeValue(this.amount);
    }

    public CommonCommodity() {
    }

    protected CommonCommodity(Parcel in) {
        this.flag = in.readByte() != 0;
        this.comHeader = in.readString();
        this.commName = in.readString();
        this.commCode = in.readString();
        this.units = in.readString();
        this.qty = (Double) in.readValue(Double.class.getClassLoader());
        this.rate = (Double) in.readValue(Double.class.getClassLoader());
        this.amount = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<CommonCommodity> CREATOR = new Parcelable.Creator<CommonCommodity>() {
        @Override
        public CommonCommodity createFromParcel(Parcel source) {
            return new CommonCommodity(source);
        }

        @Override
        public CommonCommodity[] newArray(int size) {
            return new CommonCommodity[size];
        }
    };
}
