package com.example.foodrecipes.requests.responses;

import com.example.foodrecipes.model.CompanySearchObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompanySearchResponse {

    @SerializedName("bestMatches")
    @Expose
    private List<CompanySearchObject> labels;

    public List<CompanySearchObject> getLabels() {
        return labels;
    }

    @Override
    public String toString() {
        return "CompanySearchResponse{" +
                "labels=" + labels +
                '}';
    }
}
