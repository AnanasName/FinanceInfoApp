package com.example.foodrecipes.di

import com.example.foodrecipes.persistence.CompanyDao
import com.example.foodrecipes.repositories.CompanyRepository
import com.example.foodrecipes.repositories.CompanyRepositoryImpl
import com.example.foodrecipes.requests.InfoNetworkDataSource
import com.example.foodrecipes.requests.InfoNetworkDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideCompanyRepository(
        companyDao: CompanyDao,
        infoNetworkDataSource: InfoNetworkDataSourceImpl
    ): CompanyRepositoryImpl = CompanyRepositoryImpl(
        companyDao,
        infoNetworkDataSource
    )
}