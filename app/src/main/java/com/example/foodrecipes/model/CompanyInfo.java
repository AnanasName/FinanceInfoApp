package com.example.foodrecipes.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CompanyInfo implements Parcelable {

    private String Symbol;
    private String AssetType;
    private String Name;
    private String Description;
    private String Sector;
    private String Country;
    private String AnalystTargetPrice;
    private String DividendPerShare;
    private String DividendDate;
    private String urlOfSymbol;
    private float price;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUrlOfSymbol() {
        return urlOfSymbol;
    }

    public void setUrlOfSymbol(String urlOfSymbol) {
        this.urlOfSymbol = urlOfSymbol;
    }

    public CompanyInfo() {
    }

    public CompanyInfo(String symbol, String assetType, String name, String description, String sector, String country, String analystTargetPrice, String dividendPerShare, String dividendDate) {
        Symbol = symbol;
        AssetType = assetType;
        Name = name;
        Description = description;
        Sector = sector;
        Country = country;
        AnalystTargetPrice = analystTargetPrice;
        DividendPerShare = dividendPerShare;
        DividendDate = dividendDate;
    }

    protected CompanyInfo(Parcel in) {
        Symbol = in.readString();
        AssetType = in.readString();
        Name = in.readString();
        Description = in.readString();
        Sector = in.readString();
        Country = in.readString();
        AnalystTargetPrice = in.readString();
        DividendPerShare = in.readString();
        DividendDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Symbol);
        dest.writeString(AssetType);
        dest.writeString(Name);
        dest.writeString(Description);
        dest.writeString(Sector);
        dest.writeString(Country);
        dest.writeString(AnalystTargetPrice);
        dest.writeString(DividendPerShare);
        dest.writeString(DividendDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CompanyInfo> CREATOR = new Creator<CompanyInfo>() {
        @Override
        public CompanyInfo createFromParcel(Parcel in) {
            return new CompanyInfo(in);
        }

        @Override
        public CompanyInfo[] newArray(int size) {
            return new CompanyInfo[size];
        }
    };

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public String getAssetType() {
        return AssetType;
    }

    public void setAssetType(String assetType) {
        AssetType = assetType;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String sector) {
        Sector = sector;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAnalystTargetPrice() {
        return AnalystTargetPrice;
    }

    public void setAnalystTargetPrice(String analystTargetPrice) {
        AnalystTargetPrice = analystTargetPrice;
    }

    public String getDividendPerShare() {
        return DividendPerShare;
    }

    public void setDividendPerShare(String dividendPerShare) {
        DividendPerShare = dividendPerShare;
    }

    public String getDividendDate() {
        return DividendDate;
    }

    public void setDividendDate(String dividendDate) {
        DividendDate = dividendDate;
    }
}
