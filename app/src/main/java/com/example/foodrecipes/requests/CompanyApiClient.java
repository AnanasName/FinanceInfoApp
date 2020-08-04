package com.example.foodrecipes.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodrecipes.AppExecutors;
import com.example.foodrecipes.model.CompanyInfo;
import com.example.foodrecipes.model.CompanySearchObject;
import com.example.foodrecipes.requests.responses.CompanyLogoResponse;
import com.example.foodrecipes.requests.responses.CompanySearchResponse;
import com.example.foodrecipes.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.foodrecipes.util.Constants.NETWORK_TIMEOUT;

public class CompanyApiClient {

    private static final String TAG = "CompanyApiClient";

    private static CompanyApiClient instance;
    private MutableLiveData<List<CompanyInfo>> mCompanies;
    private RetrieveCompanyRunnable mRetrieveCompanyRunnable;

    public static CompanyApiClient getInstance() {
        if (instance == null) {
            instance = new CompanyApiClient();
        }
        return instance;
    }

    private CompanyApiClient() {
        mCompanies = new MutableLiveData<>();
    }

    public LiveData<List<CompanyInfo>> getCompanies() {
        return mCompanies;
    }

    public void searchCompaniesApi(String symbols) {
        if (mRetrieveCompanyRunnable != null){
            mRetrieveCompanyRunnable = null;
        }
        mRetrieveCompanyRunnable = new RetrieveCompanyRunnable(symbols);
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveCompanyRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveCompanyRunnable implements Runnable {

        private String symbols;
        boolean cancelRequest;

        public RetrieveCompanyRunnable(String symbols) {
            this.symbols = symbols;
            cancelRequest = false;
        }

        @Override
        public void run() {
            List<CompanyInfo> companyInfos = new ArrayList<>();
            try {
                Response<CompanySearchResponse> response = getCompanies(symbols).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200){
                    List<CompanySearchObject> list = new ArrayList<>(((response.body())).getLabels());

                    for (CompanySearchObject label : list){
                        CompanyInfo info = new CompanyInfo();
                        info.setSymbol(label.getSymbol());
                        info.setName(label.getName());
                        Log.d(TAG, "run: " + label.getSymbol() + " " + label.getName());
                        Response<CompanyLogoResponse> imageResponse = getImageUrl(label.getSymbol()).execute();
                        if (imageResponse.body().getUrl() != null) {
                            info.setUrlOfSymbol((imageResponse.body()).getUrl());
                            Log.d(TAG,"run: " + (imageResponse.body()).getUrl());
                        }
                        companyInfos.add(info);
                    }

                    //mCompanies.postValue(companyInfos);
                }else{
                    String error = response.errorBody().string();
                    Log.e(TAG, "run:");
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
            mCompanies.postValue(companyInfos);
        }

        private Call<CompanySearchResponse> getCompanies(String symbols) {
            return ServiceGenerator.getInfoApi().searchCompanies(
                    Constants.SEARCH_FUNCTION, symbols, Constants.API_KEY_FOR_INFO
            );
        }

        private Call<CompanyLogoResponse> getImageUrl(String symbols){
            return ServiceGenerator.getImageApi().getImageUrl(
                    symbols, Constants.API_KEY_FOR_IMAGE
            );
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the search request");
            cancelRequest = true;
        }
    }
}
