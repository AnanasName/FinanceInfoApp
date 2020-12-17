package com.example.foodrecipes.di

import com.example.foodrecipes.requests.InfoNetworkDataSource
import com.example.foodrecipes.requests.InfoNetworkDataSourceImpl
import com.example.foodrecipes.requests.ServiceGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun provideDataSource(serviceGenerator: ServiceGenerator): InfoNetworkDataSource{
        return InfoNetworkDataSourceImpl(serviceGenerator)
    }
}