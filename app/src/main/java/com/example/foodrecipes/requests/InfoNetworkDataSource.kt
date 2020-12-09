package com.example.foodrecipes.requests

import androidx.lifecycle.LiveData
import com.example.foodrecipes.model.CompanyInfo
import com.example.foodrecipes.util.Resource

interface InfoNetworkDataSource {
    val companies: LiveData<Resource<List<CompanyInfo>>>
    val company: LiveData<Resource<CompanyInfo>>


    suspend fun fetchImageUrl(
            symbol: String
    ): String?

    suspend fun fetchPrice(
            symbol: String
    ): Float?

    suspend fun fetchInfo(
            symbol: String
    )

    suspend fun searchCompanies(
            symbol: String
    )


}