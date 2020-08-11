package com.example.foodrecipes.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodrecipes.AppExecutors;
import com.example.foodrecipes.model.CompanyInfo;
import com.example.foodrecipes.model.CompanySearchObject;
import com.example.foodrecipes.requests.responses.CompanyInfoResponse;
import com.example.foodrecipes.requests.responses.CompanyLogoResponse;
import com.example.foodrecipes.requests.responses.CompanySearchResponse;
import com.example.foodrecipes.requests.responses.PriceResponse;
import com.example.foodrecipes.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.foodrecipes.util.Constants.NETWORK_TIMEOUT;
import static com.example.foodrecipes.util.Constants.SEARCH_FUNCTION;

public class CompanyApiClient {

    private static final String TAG = "CompanyApiClient";

    private static CompanyApiClient instance;
    private MutableLiveData<List<CompanyInfo>> mCompanies;
    private RetrieveCompaniesRunnable mRetrieveCompaniesRunnable;
    private RetrieveCompanyRunnable mRetrieveCompanyRunnable;
    private MutableLiveData<Boolean> updating;
    private MutableLiveData<CompanyInfo> mCompany;
    private MutableLiveData<Boolean> mCompanyRequestTimeout = new MutableLiveData<>();

    public static CompanyApiClient getInstance() {
        if (instance == null) {
            instance = new CompanyApiClient();
        }
        return instance;
    }

    private CompanyApiClient() {
        mCompanies = new MutableLiveData<>();
        updating = new MutableLiveData<>();
        mCompany = new MutableLiveData<>();
    }

    public LiveData<List<CompanyInfo>> getCompanies() {
        return mCompanies;
    }

    public LiveData<CompanyInfo> getCompany(){
        return mCompany;
    }

    public LiveData<Boolean> isUpdating() {
        return updating;
    }

    public LiveData<Boolean> isCompanyRequestTimeout(){
        return mCompanyRequestTimeout;
    }

    public void searchCompaniesApi(String symbols) {
        if (mRetrieveCompaniesRunnable != null) {
            mRetrieveCompaniesRunnable = null;
        }
        mRetrieveCompaniesRunnable = new RetrieveCompaniesRunnable(symbols);
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveCompaniesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void getCompanyInfo(String symbol){
        if (mRetrieveCompanyRunnable != null){
            mRetrieveCompanyRunnable = null;
        }
        mRetrieveCompanyRunnable = new RetrieveCompanyRunnable(symbol);
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveCompanyRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                mCompanyRequestTimeout.postValue(true);
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveCompaniesRunnable implements Runnable {

        private String symbols;
        boolean cancelRequest;

        public RetrieveCompaniesRunnable(String symbols) {
            this.symbols = symbols;
            cancelRequest = false;
        }

        @Override
        public void run() {
            updating.postValue(true);
            List<CompanyInfo> companyInfos = new ArrayList<>();
            try {
                Response<CompanySearchResponse> response = getCompanies(symbols).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<CompanySearchObject> list = new ArrayList<>(((response.body())).getLabels());

                    for (CompanySearchObject label : list) {
                        CompanyInfo info = new CompanyInfo();
                        info.setSymbol(label.getSymbol());
                        info.setName(label.getName());
                        Log.d(TAG, "run: " + label.getSymbol() + " " + label.getName());
                        Response<CompanyLogoResponse> imageResponse = getImageUrl(label.getSymbol()).execute();
                        if (label.getSymbol().indexOf(".") != -1) {
                            info.setUrlOfSymbol("https://storage.googleapis.com/iexcloud-hl37opg/api/logos/IBML.png");
                        } else if (imageResponse.body().getUrl() != null) {
                            info.setUrlOfSymbol((imageResponse.body()).getUrl());
                            Log.d(TAG, "run: " + (imageResponse.body()).getUrl());
                        } else {
                            info.setUrlOfSymbol("https://storage.googleapis.com/iexcloud-hl37opg/api/logos/IBML.png");
                        }
                        if (label.getSymbol().indexOf(".") == -1) {
                            companyInfos.add(info);
                        }
                    }

                    updating.postValue(false);
                    mCompanies.postValue(companyInfos);
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run:");
                    updating.postValue(false);
                    mCompanies.postValue(null);
                }
            } catch (IOException io) {
                io.printStackTrace();
                updating.postValue(false);
                mCompanies.postValue(null);
            }
        }

        private Call<CompanySearchResponse> getCompanies(String symbols) {
            return ServiceGenerator.getInfoApi().searchCompanies(
                    SEARCH_FUNCTION, symbols, Constants.API_KEY_FOR_INFO
            );
        }

        private Call<CompanyLogoResponse> getImageUrl(String symbols) {
            return ServiceGenerator.getImageApi().getImageUrl(
                    symbols, Constants.API_KEY_FOR_IMAGE
            );
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the search request");
            cancelRequest = true;
        }
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
            updating.postValue(true);
            CompanyInfo companyInfo = new CompanyInfo();
            try {
                Response<CompanyInfoResponse> response = getCompany(symbols).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    Response<PriceResponse> priceResponse = getPrice(symbols).execute();
                    companyInfo.setPrice(priceResponse.body().getPrice());
                    companyInfo.setDescription(response.body().getDescription());
                    companyInfo.setAnalystTargetPrice(response.body().getAnalystTargetPrice());
                    companyInfo.setCountry(response.body().getCountry());
                    companyInfo.setName(response.body().getName());
                    companyInfo.setAssetType(response.body().getAssetType());
                    companyInfo.setDividendPerShare(response.body().getDividendPerShare());
                    companyInfo.setDividendDate(response.body().getDividendDate());
                    companyInfo.setSector(response.body().getSector());
                    //TODO Добавить цену

                    updating.postValue(false);
                    mCompanyRequestTimeout.postValue(false);
                    mCompany.postValue(companyInfo);
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run:");
                    updating.postValue(false);
                    mCompanyRequestTimeout.postValue(false);
                    mCompany.postValue(null);
                }
            } catch (IOException io) {
                io.printStackTrace();
                updating.postValue(false);
                mCompanyRequestTimeout.postValue(false);
                mCompany.postValue(null);
            }
        }

        private Call<CompanyInfoResponse> getCompany(String symbols) {
            return ServiceGenerator.getInfoApi().getInfo(
                    Constants.OVERVIEW_FUNCTION, symbols, Constants.API_KEY_FOR_INFO
            );
        }

        private Call<PriceResponse> getPrice(String symbols) {
            return ServiceGenerator.getImageApi().getPrice(
                    symbols, Constants.API_KEY_FOR_IMAGE
            );
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the search request");
            cancelRequest = true;
        }
    }
}
