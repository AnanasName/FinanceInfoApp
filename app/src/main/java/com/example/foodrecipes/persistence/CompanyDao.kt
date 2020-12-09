package com.example.foodrecipes.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import com.example.foodrecipes.model.CompanyInfo

@Dao
interface CompanyDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertCompanies(companyInfos: List<CompanyInfo>)

    @Insert(onConflict = REPLACE)
    suspend fun upsertCompanyInfo(companyInfo: CompanyInfo)

    @Query("SELECT * FROM companyInfos WHERE symbol " +
            "LIKE '%' || :symbol || '%' OR name LIKE '%' || :symbol || '%' ORDER BY price")
    suspend fun searchCompanies(symbol: String): List<CompanyInfo>

    @Query("SELECT * FROM companyInfos WHERE symbol = :symbol")
    suspend fun getCompanyInfo(symbol: String): CompanyInfo


}