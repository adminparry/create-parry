package com.example.androidapidemo.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.androidapidemo.ApiDemoApplication
import com.example.androidapidemo.data.ApiRepository
import com.example.androidapidemo.model.ApiItem
import kotlinx.coroutines.launch

class ApiDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ApiRepository = (application as ApiDemoApplication).repository

    private val _apiDetail = MutableLiveData<ApiItem>()
    val apiDetail: LiveData<ApiItem> = _apiDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadApiDetail(apiId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                val apiItem = repository.getApiItemById(apiId)
                if (apiItem != null) {
                    _apiDetail.value = apiItem
                } else {
                    _error.value = "API not found"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
} 