package com.example.foodrecipes.requests

import com.example.foodrecipes.util.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {

    private val retrofitBuilderForInfo = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_INFO)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())

    private val retrofitForInfo = retrofitBuilderForInfo.build()

    val infoApi = retrofitForInfo.create(InfoApi::class.java)

    private val retrofitBuilderForImage = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_IMAGE)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())

    private val retrofitForImage = retrofitBuilderForImage.build()

    val imageApi = retrofitForImage.create(ImageApi::class.java)
}