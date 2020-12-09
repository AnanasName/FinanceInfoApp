package com.example.foodrecipes.persistence

import android.content.Context
import androidx.room.*
import com.example.foodrecipes.model.CompanyInfo

const val DATABASE_NAME = "company_db"

@Database(entities = [CompanyInfo::class], version = 1)
@TypeConverters(Converters::class)
abstract class CompanyDatabase : RoomDatabase() {
    abstract fun companyDao(): CompanyDao


    companion object {
        @Volatile
        private var instance: CompanyDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        CompanyDatabase::class.java, DATABASE_NAME)
                        .build()
    }

}
