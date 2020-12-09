package com.example.foodrecipes.requests

import com.example.foodrecipes.requests.responses.CompanyInfoResponse
import com.example.foodrecipes.requests.responses.CompanySearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface InfoApi {

    @GET("query")
    fun getInfo(
            @Query("function") function: String,
            @Query("symbol") symbol: String?,
            @Query("apikey") apikey: String
    ): Deferred<CompanyInfoResponse?>?

    @GET("query")
    fun searchCompanies(
    @Query("function") function: String,
    @Query("keywords") keywords: String?,
    @Query("apikey") apikey: String): Deferred<CompanySearchResponse?>?
}
