package com.example.foodrecipes.repositories

import com.example.foodrecipes.model.CompanyInfo
import com.example.foodrecipes.persistence.CompanyDao
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

    override suspend fun getCompanies(symbol: String): Flow<Resource<List<CompanyInfo>>> = flow {
        emit(Resource.loading<List<CompanyInfo>>(null))
        infoNetworkDataSource.searchCompanies(symbol)
        val networkData = infoNetworkDataSource.companies
        if (networkData.data != null) {
            val success = Resource.success(networkData.data)
            companyDao.insertCompanies(networkData.data)
            emit(success)
        } else {
            try{
            val databaseData = companyDao.searchCompanies(symbol)
                val success = Resource.success(databaseData)
                emit(success)
            } catch (e: Exception) {
                emit(Resource.error<List<CompanyInfo>>("Error occurred", null))
            }
        }
    }


    override suspend fun getCompanyInfo(symbol: String): Flow<Resource<CompanyInfo>> = flow {
        emit(Resource.loading<CompanyInfo>(null))
        infoNetworkDataSource.fetchInfo(symbol)
        val networkData = infoNetworkDataSource.company
        if (networkData.data != null){
            val success = Resource.success(networkData.data)
            companyDao.upsertCompanyInfo(networkData.data)
            emit(success)
        }else{
            try{
                val databaseData = companyDao.getCompanyInfo(symbol)
                val success = Resource.success(databaseData)
                emit(success)
            }catch (e: Exception){
                emit(Resource.error<CompanyInfo>("Error occurred", null))
            }
        }
    }
}