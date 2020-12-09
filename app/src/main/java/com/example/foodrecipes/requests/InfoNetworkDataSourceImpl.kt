package com.example.foodrecipes.requests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodrecipes.DEBUG
import com.example.foodrecipes.model.CompanyInfo
import com.example.foodrecipes.util.Constants
import com.example.foodrecipes.util.Resource
import java.lang.Exception

class InfoNetworkDataSourceImpl
    constructor(private val serviceGenerator: ServiceGenerator)
    : InfoNetworkDataSource {

    private val _companies = MutableLiveData<Resource<List<CompanyInfo>>>()
    override val companies: LiveData<Resource<List<CompanyInfo>>>
        get() = _companies

    private val _company = MutableLiveData<Resource<CompanyInfo>>()
    override val company: LiveData<Resource<CompanyInfo>>
        get() = _company

    override suspend fun fetchImageUrl(symbol: String): String? {
        try {
           val imageUrl = serviceGenerator.imageApi.getImageUrl(symbol, Constants.API_KEY_FOR_IMAGE)?.await()
            return imageUrl?.url
        }catch (e: Exception){
            _companies.value = (Resource.error(e.message.toString(), null))
            return null
        }
    }

    override suspend fun fetchPrice(symbol: String): Float? {
        return serviceGenerator.imageApi.getPrice(symbol, Constants.API_KEY_FOR_IMAGE)?.await()?.price
    }

    override suspend fun fetchInfo(symbol: String) {
        try {
            val fetchedCompanyInfo = serviceGenerator.infoApi.getInfo(Constants.OVERVIEW_FUNCTION, symbol, Constants.API_KEY_FOR_INFO)?.await()
            if (fetchedCompanyInfo != null) {
                val companyInfo = CompanyInfo()
                companyInfo.symbol = symbol
                companyInfo.name = fetchedCompanyInfo.name
                companyInfo.analystTargetPrice = fetchedCompanyInfo.analystTargetPrice
                companyInfo.sector = fetchedCompanyInfo.sector
                companyInfo.dividendDate = fetchedCompanyInfo.dividendDate
                companyInfo.description = fetchedCompanyInfo.description
                companyInfo.assetType = fetchedCompanyInfo.assetType
                companyInfo.country = fetchedCompanyInfo.country
                try {
                    companyInfo.urlOfSymbol = fetchImageUrl(symbol)
                    val price = fetchPrice(symbol)
                    if (price != null) {
                        companyInfo.price = price
                    }else{
                        companyInfo.price = 0.0f
                    }
                }catch (e: Exception){
                    _company.value = Resource.error(e.message.toString(), null)
                }
                _company.value = Resource.success(companyInfo)
            } else {
                _company.value = Resource.error("Error occurred", null)
            }
        }catch (e: Exception){
            _company.value = Resource.error(e.message.toString(), null)
        }
    }

    override suspend fun searchCompanies(symbol: String) {
        try {
            val fetchedCompanies = serviceGenerator.infoApi
                    .searchCompanies(Constants.SEARCH_FUNCTION, symbol, Constants.API_KEY_FOR_INFO)
                    ?.await()

            if (fetchedCompanies != null) {
                val companiesList = mutableListOf<CompanyInfo>()
                for (companySearchObject in fetchedCompanies.labels){
                    val companyInfo = CompanyInfo()
                    companyInfo.name = companySearchObject.name
                    companyInfo.symbol = companySearchObject.symbol
                    val urlOfImage = fetchImageUrl(companySearchObject.symbol)
                    if (urlOfImage != null){
                        companyInfo.urlOfSymbol = urlOfImage
                    }else{
                        _companies.value = Resource.error("Error occurred", null)
                    }
                    companiesList.add(companyInfo)
                }
                _companies.value = Resource.success(companiesList)
            }else{
                _companies.value = Resource.error("Error occurred", null)
            }

        }catch (e: Exception){
            _companies.value = Resource.error(e.message.toString(), null)
        }
    }
}