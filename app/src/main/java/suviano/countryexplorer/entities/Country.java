package suviano.countryexplorer.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country implements Parcelable {
    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };
    public static final String VISIT = "COUNTRY_VISIT";
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("iso")
    @Expose
    private String iso;
    @SerializedName("shortname")
    @Expose
    private String shortName;
    @SerializedName("longname")
    @Expose
    private String longName;
    @SerializedName("callingCode")
    @Expose
    private String callingCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("culture")
    @Expose
    private String culture;

    protected Country(Parcel in) {
        id = in.readString();
        iso = in.readString();
        shortName = in.readString();
        longName = in.readString();
        callingCode = in.readString();
        status = in.readString();
        culture = in.readString();
    }

    public String getFlagUrl(String base_url) {
        return String.format("%s/world/countries/%s/flag", base_url, getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(iso);
        dest.writeString(shortName);
        dest.writeString(longName);
        dest.writeString(callingCode);
        dest.writeString(status);
        dest.writeString(culture);
    }
}
