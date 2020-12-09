package com.example.foodrecipes.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import com.example.foodrecipes.model.CompanyInfo

@Dao
interface CompanyDao {

    @Insert(onConflict = REPLACE)
    fun insertCompanies(companyInfos: List<CompanyInfo>)

    @Insert(onConflict = REPLACE)
    fun upsertCompanyInfo(companyInfo: CompanyInfo)

    @Query("SELECT * FROM companyInfos WHERE symbol " +
            "LIKE '%' || :symbol || '%' OR name LIKE '%' || :symbol || '%' ORDER BY price")
    fun searchCompanies(symbol: String): LiveData<List<CompanyInfo>>

    @Query("SELECT * FROM companyInfos WHERE symbol = :symbol")
    fun getCompanyInfo(symbol: String): CompanyInfo


}