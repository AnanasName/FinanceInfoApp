package com.example.foodrecipes.viewmodels

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.foodrecipes.DEBUG
import com.example.foodrecipes.model.CompanyInfo
import com.example.foodrecipes.repositories.CompanyRepository
import com.example.foodrecipes.repositories.CompanyRepositoryImpl
import com.example.foodrecipes.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols

class CompanyListViewModel
@ViewModelInject
constructor(
        private val repository: CompanyRepositoryImpl,
        @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _companies: MutableLiveData<Resource<List<CompanyInfo>>> = MutableLiveData()

    val companies: LiveData<Resource<List<CompanyInfo>>>
            get() = _companies

    @ExperimentalCoroutinesApi
    fun searchCompanies(symbol: String){
        viewModelScope.launch {

            //_companies.value = repository.getCompanies(symbol).asLiveData().value
            repository.getCompanies(symbol).onEach { resource ->
                _companies.value = resource
            }.launchIn(viewModelScope)
        }
    }




}