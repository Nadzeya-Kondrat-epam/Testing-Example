package com.example.testexampleapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class DataViewModel(
    coroutineDispatchers: CoroutineDispatchersWrapper,
    val repository: RepositoryImulator
) : ViewModel() {

    private val coroutineScope = CoroutineScope(coroutineDispatchers.main)

    private val _loadedData = MutableLiveData<LoadedData>()
    val loadedData: LiveData<LoadedData>
        get() = _loadedData

    private val _loading = MutableLiveData<Boolean>()

    val loading: LiveData<Boolean>
        get() = _loading

    private val _errorData = MutableLiveData<String>()
    val erroroData: LiveData<String>
        get() = _errorData

    fun loadData() {
        coroutineScope.launch {
            _loading.value = true
            try {
                val response = repository.loadData()
                _loading.value = false
                _loadedData.value = response
            } catch (e: Exception) {
                _loading.value = false
                _errorData.value = e.message ?: "Unexpected error"
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (coroutineScope.isActive) {
            coroutineScope.cancel()
        }
    }

    class Factory(
        private val coroutineDispatchers: CoroutineDispatchersWrapper,
        private val repositoryImulator: RepositoryImulator
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DataViewModel(
                coroutineDispatchers,
                repositoryImulator
            ) as T
        }
    }
}