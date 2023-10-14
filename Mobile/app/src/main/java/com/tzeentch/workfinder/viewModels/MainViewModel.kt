package com.tzeentch.workfinder.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzeentch.workfinder.local.PreferenceManager
import com.tzeentch.workfinder.remote.isLoading
import com.tzeentch.workfinder.remote.onFailure
import com.tzeentch.workfinder.remote.onSuccess
import com.tzeentch.workfinder.repositories.MainRepository
import com.tzeentch.workfinder.ui.GreetingStates
import com.tzeentch.workfinder.ui.MainScreenStates
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel constructor(
    private val repository: MainRepository,
    private val prefs: PreferenceManager
) : ViewModel() {

    private var token: String = ""

    private val coroutineExceptionHandler =
        Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
            _greetingState.value = GreetingStates.Form("Unexpected Error")
        }

    private val _greetingState = MutableStateFlow<GreetingStates>(GreetingStates.Loading)
    val greetingState = _greetingState.asStateFlow()

    private val _mainState = MutableStateFlow<MainScreenStates>(MainScreenStates.Initial)
    val mainState = _mainState.asStateFlow()

    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            val (name, password) = prefs.getAuthData()
            if (name.isEmpty() || password.isEmpty()) {
                _greetingState.value = GreetingStates.Initial
            } else {
                loginUser(name, password)
            }
        }
    }

    fun loginUser(name: String, password: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.loginUser(name, password).collect { result ->
                result.isLoading {
                    _greetingState.value = GreetingStates.Loading
                }.onSuccess {
                    viewModelScope.launch(coroutineExceptionHandler) {
                        prefs.setAuthData(name, password)
                        token = it.token
                        repository.getUser(token).collect { result ->
                            result.isLoading {
                                _greetingState.value = GreetingStates.Loading
                            }.onSuccess {
                                if (it.hasQuestionary) {
                                    _greetingState.value = GreetingStates.Registered
                                } else {
                                    _greetingState.value = GreetingStates.FillQuestionary
                                }
                            }.onFailure {
                                _greetingState.value = GreetingStates.Form(it)
                            }
                        }

                    }
                }.onFailure {
                    viewModelScope.launch(coroutineExceptionHandler) {
                        prefs.setAuthData("", "")
                        _greetingState.value =
                            GreetingStates.Form(it)
                    }
                }
            }
        }
    }

    fun registerUser(
        name: String,
        password: String
    ) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.registerUser(name, password).collect { result ->
                result.isLoading {
                    _greetingState.value = GreetingStates.Loading
                }.onSuccess {
                    viewModelScope.launch(coroutineExceptionHandler) {
                        prefs.setAuthData(name, password)
                        token = it.token
                        _greetingState.value = GreetingStates.FillQuestionary
                    }
                }.onFailure {
                    _greetingState.value =
                        GreetingStates.Form("SmtWrong")
                }
            }
        }
    }

    fun setState(states: GreetingStates) {
        _greetingState.value = states
    }

    fun getCoursesAndVacancies() {
        Log.e("sssss","sss")
        if (_mainState.value !is MainScreenStates.Initial) return
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.getCourses(token, "cleaning").collect { result ->
                result.isLoading {
                    _mainState.value = MainScreenStates.Loading
                }.onSuccess {
                    _mainState.value = MainScreenStates.Content(emptyList(), it)
                }.onFailure {

                }
            }
        }
    }
}