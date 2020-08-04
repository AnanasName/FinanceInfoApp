package com.example.foodrecipes.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyInfoResponse {

    @SerializedName("Symbol")
    @Expose
    private String symbol;

    @SerializedName("AssetType")
    @Expose
    private String assetType;

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("Description")
    @Expose
    private String description;

    @SerializedName("Country")
    @Expose
    private String country;

    @SerializedName("Sector")
    @Expose
    private String sector;

    @SerializedName("DividendPerShare")
    @Expose
    private String dividendPerShare;

    @SerializedName("AnalystTargetPrice")
    @Expose
    private String analystTargetPrice;

    @SerializedName("DividendDate")
    @Expose
    private String dividendDate;


    public String getSymbol() {
        return symbol;
    }

    public String getAssetType() {
        return assetType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSector() {
        return sector;
    }

    public String getCountry() {
        return country;
    }

    public String getAnalystTargetPrice() {
        return analystTargetPrice;
    }

    public String getDividendPerShare() {
        return dividendPerShare;
    }

    public String getDividendDate() {
        return dividendDate;
    }

    @Override
    public String toString() {
        return "CompanyInfoResponse{" +
                "symbol='" + symbol + '\'' +
                ", assetType='" + assetType + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sector='" + sector + '\'' +
                ", country='" + country + '\'' +
                ", analystTargetPrice='" + analystTargetPrice + '\'' +
                ", dividendPerShare='" + dividendPerShare + '\'' +
                ", dividendDate='" + dividendDate + '\'' +
                '}';
    }
}
