package com.example.foodrecipes.di

import android.content.Context
import androidx.room.Room
import com.example.foodrecipes.persistence.CompanyDao
import com.example.foodrecipes.persistence.CompanyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideCompanyDb(@ApplicationContext context: Context): CompanyDatabase =
            CompanyDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideCompanyDao(companyDatabase: CompanyDatabase): CompanyDao =
            companyDatabase.companyDao()
}