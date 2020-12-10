package com.example.foodrecipes.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.foodrecipes.model.CompanyInfo
import com.example.foodrecipes.repositories.CompanyRepositoryImpl
import com.example.foodrecipes.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CompanyInfoViewModel
@ViewModelInject
constructor(
        private val repository: CompanyRepositoryImpl,
        @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _company: MutableLiveData<Resource<CompanyInfo>> = MutableLiveData()
    val company: LiveData<Resource<CompanyInfo>>
        get() = _company

    @ExperimentalCoroutinesApi
    fun getCompanyInfo(symbol: String){
        viewModelScope.launch {
            repository.getCompanyInfo(symbol).onEach { resource ->
                _company.value = resource
            }.launchIn(viewModelScope)
        }
    }
}