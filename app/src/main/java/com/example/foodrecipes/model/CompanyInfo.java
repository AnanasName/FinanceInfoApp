package com.example.foodrecipes.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "companyInfos")
public class CompanyInfo implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "symbol")
    @NonNull
    private String Symbol;

    @ColumnInfo(name = "assetType")
    private String AssetType;

    @ColumnInfo(name = "name")
    private String Name;

    @ColumnInfo(name = "description")
    private String Description;

    @ColumnInfo(name = "sector")
    private String Sector;

    @ColumnInfo(name = "country")
    private String Country;

    @ColumnInfo(name = "analystTargetPrice")
    private String AnalystTargetPrice;

    @ColumnInfo(name = "dividendPerShare")
    private String DividendPerShare;

    @ColumnInfo(name = "dividendDate")
    private String DividendDate;

    @ColumnInfo(name = "urlOfSymbol")
    private String urlOfSymbol;

    @ColumnInfo(name = "price")
    private float price;

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
        urlOfSymbol = in.readString();
        price = in.readFloat();
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Symbol);
        parcel.writeString(AssetType);
        parcel.writeString(Name);
        parcel.writeString(Description);
        parcel.writeString(Sector);
        parcel.writeString(Country);
        parcel.writeString(AnalystTargetPrice);
        parcel.writeString(DividendPerShare);
        parcel.writeString(DividendDate);
        parcel.writeString(urlOfSymbol);
        parcel.writeFloat(price);
    }
}
