package com.example.foodrecipes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuoteObject {

    @SerializedName("latestPrice")
    @Expose
    private float latestPrice;

    public float getLatestPrice() {
        return latestPrice;
    }
}
