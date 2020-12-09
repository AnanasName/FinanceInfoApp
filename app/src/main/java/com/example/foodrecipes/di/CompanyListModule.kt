package com.example.foodrecipes.di

import com.example.foodrecipes.CompanyListActivity
import com.example.foodrecipes.adapters.CompaniesRecyclerAdapter
import com.example.foodrecipes.adapters.OnCompanyListener
import com.example.foodrecipes.util.VerticalSpacingItemDecorator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class CompanyListModule {

    @Singleton
    @Provides
    fun provideVerticalSpacingItemDecorator() = VerticalSpacingItemDecorator(30)
}