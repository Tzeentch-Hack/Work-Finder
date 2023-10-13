package com.tzeentch.workfinder.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzeentch.workfinder.remote.isLoading
import com.tzeentch.workfinder.remote.onFailure
import com.tzeentch.workfinder.remote.onSuccess
import com.tzeentch.workfinder.repositories.MainRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {

    private val coroutineExceptionHandler =
        Dispatchers.IO + CoroutineExceptionHandler { _, exception ->

        }

    fun loginUser(name: String, password: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.loginUser(name, password).collect { result ->
                result.isLoading {

                }.onSuccess {

                }.onFailure {

                }
            }
        }
    }


    fun registerUser(name: String, password: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.loginUser(name, password).collect { result ->
                result.isLoading {

                }.onSuccess {

                }.onFailure {

                }
            }
        }
    }
}