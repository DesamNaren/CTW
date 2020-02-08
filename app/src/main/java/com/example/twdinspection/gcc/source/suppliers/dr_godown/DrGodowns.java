package com.example.twdinspection.gcc.source.suppliers.dr_godown;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "DR_GoDown")
public class DrGodowns implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("godownName")
    @Expose
    private String godownName;
    @SerializedName("godownId")
    @Expose
    private String godownId;
    @SerializedName("incharge")
    @Expose
    private String incharge;
    @SerializedName("divisionName")
    @Expose
    private String divisionName;
    @SerializedName("societyName")
    @Expose
    private String societyName;
    @SerializedName("divisionId")
    @Expose
    private String divisionId;
    @SerializedName("societyId")
    @Expose
    private String societyId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGodownName() {
        return godownName;
    }

    public void setGodownName(String godownName) {
        this.godownName = godownName;
    }

    public String getGodownId() {
        return godownId;
    }

    public void setGodownId(String godownId) {
        this.godownId = godownId;
    }

    public String getIncharge() {
        return incharge;
    }

    public void setIncharge(String incharge) {
        this.incharge = incharge;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    public String getSocietyId() {
        return societyId;
    }

    public void setSocietyId(String societyId) {
        this.societyId = societyId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.godownName);
        dest.writeString(this.godownId);
        dest.writeString(this.incharge);
        dest.writeString(this.divisionName);
        dest.writeString(this.societyName);
        dest.writeString(this.divisionId);
        dest.writeString(this.societyId);
    }

    public DrGodowns() {
    }

    protected DrGodowns(Parcel in) {
        this.id = in.readInt();
        this.godownName = in.readString();
        this.godownId = in.readString();
        this.incharge = in.readString();
        this.divisionName = in.readString();
        this.societyName = in.readString();
        this.divisionId = in.readString();
        this.societyId = in.readString();
    }

    public static final Parcelable.Creator<DrGodowns> CREATOR = new Parcelable.Creator<DrGodowns>() {
        @Override
        public DrGodowns createFromParcel(Parcel source) {
            return new DrGodowns(source);
        }

        @Override
        public DrGodowns[] newArray(int size) {
            return new DrGodowns[size];
        }
    };
}
