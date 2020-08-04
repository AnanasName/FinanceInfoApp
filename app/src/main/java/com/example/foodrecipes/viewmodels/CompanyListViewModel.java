package com.example.foodrecipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodrecipes.model.CompanyInfo;
import com.example.foodrecipes.repositories.CompanyRepository;

import java.util.List;

public class CompanyListViewModel extends ViewModel {

    private CompanyRepository mCompanyRepository;

    public CompanyListViewModel(){
        mCompanyRepository = CompanyRepository.getInstance();
    }

    public LiveData<List<CompanyInfo>> getCompanies(){
        return mCompanyRepository.getCompanies();
    }

    public void searchCompaniesApi(String symbols){
        mCompanyRepository.searchCompanies(symbols);
    }

}
