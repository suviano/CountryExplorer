package suviano.countryexplorer.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {
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
}
