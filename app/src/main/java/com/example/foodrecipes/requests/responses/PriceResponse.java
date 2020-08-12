package com.example.foodrecipes.requests.responses;

import com.example.foodrecipes.model.QuoteObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceResponse {

    @SerializedName("quote")
    @Expose
    private QuoteObject quoteObject;

    public float getPrice(){
        return quoteObject.getLatestPrice();
    }
}
