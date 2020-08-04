package com.example.foodrecipes.requests;

import com.example.foodrecipes.requests.responses.CompanyLogoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ImageApi {

    @GET("/stable/stock/{symbol}/logo")
    Call<CompanyLogoResponse> getImageUrl(
            @Path("symbol") String symbol,
            @Query("token") String token
    );
}
