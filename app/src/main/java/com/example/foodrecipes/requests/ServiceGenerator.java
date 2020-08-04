package com.example.foodrecipes.requests;

import com.example.foodrecipes.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilderForInfo =
            new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_INFO)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofitForInfo = retrofitBuilderForInfo.build();

    private static InfoApi infoApi = retrofitForInfo.create(InfoApi.class);

    public static InfoApi getInfoApi() {
        return infoApi;
    }

    private static Retrofit.Builder retrofitBuilderForImage =
            new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_IMAGE)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofitForImage = retrofitBuilderForImage.build();

    private static ImageApi imageApi = retrofitForImage.create(ImageApi.class);

    public static ImageApi getImageApi(){
        return imageApi;
    }
}
