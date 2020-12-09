package com.example.foodrecipes.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodrecipes.DEBUG
import com.example.foodrecipes.model.CompanyInfo
import com.example.foodrecipes.persistence.CompanyDao
import com.example.foodrecipes.requests.InfoNetworkDataSource
import com.example.foodrecipes.requests.InfoNetworkDataSourceImpl
import com.example.foodrecipes.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class CompanyRepositoryImpl constructor(
        private val companyDao: CompanyDao,
        private val infoNetworkDataSource: InfoNetworkDataSourceImpl
) : CompanyRepository {

    init {
        infoNetworkDataSource.companies.observeForever { newCompanies ->

            if (newCompanies?.data != null) {
                if (newCompanies.status == Resource.Status.SUCCESS) {
                    Log.d(DEBUG, newCompanies.data.get(0).urlOfSymbol)
                    persistFetchedCompanies(newCompanies.data)
                }
            }
        }

        infoNetworkDataSource.company.observeForever { newCompany ->
            if (newCompany?.data != null) {
                if (newCompany.status == Resource.Status.SUCCESS) {
                    persistFetchedCompany(newCompany.data)
                }
            }
        }
    }

    private fun persistFetchedCompany(newCompany: CompanyInfo) {
        GlobalScope.launch(Dispatchers.IO) {
            companyDao.upsertCompanyInfo(newCompany)
        }
    }


    private fun persistFetchedCompanies(newCompanies: List<CompanyInfo>) {
        GlobalScope.launch(Dispatchers.IO) {
            companyDao.insertCompanies(newCompanies)
            Log.d(DEBUG, "SUCESS")
        }

    }


    override suspend fun getCompanies(symbol: String): Flow<Resource<List<CompanyInfo>>> = flow {
        emit(Resource.loading<List<CompanyInfo>>(null))
        infoNetworkDataSource.searchCompanies(symbol)
        if(infoNetworkDataSource.companies.value != null){
            return
        }
        Log.d(DEBUG, "searchCompanies repository")
        val data = companyDao.searchCompanies(symbol).value
        Log.d(DEBUG, "Success 2")
        if (data != null) {
            val success = Resource.success(data)
            emit(success)
        } else {
            emit(Resource.error<List<CompanyInfo>>("Error occurred", null))
        }
    }


    override suspend fun getCompanyInfo(symbol: String): Flow<Resource<CompanyInfo>> = flow {
        emit(Resource.loading<CompanyInfo>(null))
        val data = companyDao.getCompanyInfo(symbol)
        if (data != null) {
            val success = Resource.success(data)
            emit(success)
        } else {
            emit(Resource.error<CompanyInfo>("Error occurred", null))
        }
    }
}