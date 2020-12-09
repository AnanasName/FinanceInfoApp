package com.example.foodrecipes.di

import com.example.foodrecipes.requests.ServiceGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideServiceGenerator() = ServiceGenerator
}