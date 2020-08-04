package com.example.foodrecipes.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodrecipes.model.CompanyInfo;
import com.example.foodrecipes.requests.CompanyApiClient;

import java.util.List;

public class CompanyRepository {

    private static CompanyRepository instance;
    private CompanyApiClient mCompanyApiClient;

    public static CompanyRepository getInstance(){
        if (instance == null){
            instance = new CompanyRepository();
        }
        return instance;
    }

    private CompanyRepository(){
       mCompanyApiClient = CompanyApiClient.getInstance();
    }

    public LiveData<List<CompanyInfo>> getCompanies(){
        return mCompanyApiClient.getCompanies();
    }

    public void searchCompanies(String symbols){
        mCompanyApiClient.searchCompaniesApi(symbols);
    }
}
