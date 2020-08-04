package com.example.foodrecipes.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;

public class CompanyLogoResponse {

    @SerializedName("url")
    @Expose()
    private String url;

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "CompanyLogoResponse{" +
                "url='" + url + '\'' +
                '}';
    }
}
