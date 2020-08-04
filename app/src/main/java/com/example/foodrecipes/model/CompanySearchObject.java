package com.example.foodrecipes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanySearchObject {

    @SerializedName("1. symbol")
    @Expose
    private String symbol;

    @SerializedName("2. name")
    @Expose
    private String name;

    public String getSymbol() {
        return symbol;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return "CompanySearchObject{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
