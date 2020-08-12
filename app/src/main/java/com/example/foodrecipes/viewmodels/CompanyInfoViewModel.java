package com.example.foodrecipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodrecipes.model.CompanyInfo;
import com.example.foodrecipes.repositories.CompanyRepository;

public class CompanyInfoViewModel extends ViewModel {

    private CompanyRepository mCompanyRepository;

    public CompanyInfoViewModel(){
        mCompanyRepository = CompanyRepository.getInstance();
    }

    public LiveData<CompanyInfo> getCompany(){
        return mCompanyRepository.getCompany();
    }

    public void getCompanyInfo(String symbol){
        mCompanyRepository.getCompanyInfo(symbol);
    }

    public LiveData<Boolean> isUpdating(){
        return mCompanyRepository.isUpdating();
    }

    public LiveData<Boolean> hasRetrieveError(){
        return mCompanyRepository.hasRetrieveError();
    }

    public void setHasNotRetrieveError(){
        mCompanyRepository.setHasNotRetrieveError();
    }
}
