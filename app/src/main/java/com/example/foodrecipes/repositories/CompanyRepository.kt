package com.example.foodrecipes.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodrecipes.model.CompanyInfo
import com.example.foodrecipes.util.Resource
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {
    suspend fun getCompanies(symbol: String): Flow<Resource<List<CompanyInfo>>>

    suspend fun getCompanyInfo(symbol: String): Flow<Resource<CompanyInfo>>
}