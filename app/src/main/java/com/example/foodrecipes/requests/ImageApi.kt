package com.example.foodrecipes.requests

import com.example.foodrecipes.requests.responses.CompanyLogoResponse
import com.example.foodrecipes.requests.responses.PriceResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageApi {
    @GET("/stable/stock/{symbol}/logo")
    fun getImageUrl(
            @Path("symbol") symbol: String?,
            @Query("token") token: String?
    ): Deferred<CompanyLogoResponse?>?

    @GET("/stable/stock/{symbol}/book")
    fun getPrice(@Path("symbol") symbol: String?,
                 @Query("token") token: String?): Deferred<PriceResponse?>?
}