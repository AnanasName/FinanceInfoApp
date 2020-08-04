package com.example.foodrecipes.requests;

import com.example.foodrecipes.requests.responses.CompanyInfoResponse;
import com.example.foodrecipes.requests.responses.CompanyLogoResponse;
import com.example.foodrecipes.requests.responses.CompanySearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InfoApi {

    @GET("query")
    Call<CompanyInfoResponse> getInfo(
            @Query("function") String function,
            @Query("symbol") String symbol,
            @Query("apikey") String apikey
    );

    @GET("query")
    Call<CompanySearchResponse> searchCompanies(
            @Query("function") String function,
            @Query("keywords") String keywords,
            @Query("apikey") String apikey
    );
}
