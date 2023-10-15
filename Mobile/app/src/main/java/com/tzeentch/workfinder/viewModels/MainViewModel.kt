package com.tzeentch.workfinder.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tzeentch.workfinder.dto.ProfileDto
import com.tzeentch.workfinder.dto.UserDto
import com.tzeentch.workfinder.local.PreferenceManager
import com.tzeentch.workfinder.remote.isLoading
import com.tzeentch.workfinder.remote.onFailure
import com.tzeentch.workfinder.remote.onSuccess
import com.tzeentch.workfinder.repositories.MainRepository
import com.tzeentch.workfinder.ui.GreetingStates
import com.tzeentch.workfinder.ui.MainScreenStates
import com.tzeentch.workfinder.ui.ProfileScreenStates
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel constructor(
    private val repository: MainRepository,
    private val prefs: PreferenceManager
) : ViewModel() {

    private var token: String = ""

    lateinit var user: UserDto

    private val coroutineExceptionHandler =
        Dispatchers.IO + CoroutineExceptionHandler { _, thr ->
            Log.d("smt", thr.message.toString())
            _greetingState.value = GreetingStates.Form("Unexpected Error")
        }

    private val _greetingState = MutableStateFlow<GreetingStates>(GreetingStates.Loading)
    val greetingState = _greetingState.asStateFlow()

    private val _profileState = MutableStateFlow<ProfileScreenStates>(ProfileScreenStates.Initial)
    val profileState = _profileState.asStateFlow()

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

    fun getUserData() {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.getUser(token).collect { result ->
                result.isLoading {
                    _profileState.value = ProfileScreenStates.Loading
                }.onSuccess {
                    user = it
                    _profileState.value = ProfileScreenStates.UserProfile(it)
                }.onFailure {

                }
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

    fun quit() {
        viewModelScope.launch(coroutineExceptionHandler) {
            prefs.setAuthData("", "")
            _greetingState.value = GreetingStates.Initial
        }
    }

    fun sendProfileData(profileDto: ProfileDto) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.sendProfileData(profileDto, token).collect {
                it.isLoading {
                    _greetingState.value = GreetingStates.Loading
                }.onSuccess {
                    _greetingState.value = GreetingStates.GoToMainScreen
                }.onFailure {

                }
            }
        }

    }

    fun setState(states: GreetingStates) {
        _greetingState.value = states
    }

    fun getCoursesAndVacancies(query: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.getCourses(token, query).collect { result ->
                result.isLoading {
                    _mainState.value = MainScreenStates.Loading
                }.onSuccess { courses ->
                    viewModelScope.launch(coroutineExceptionHandler) {
                        repository.getVacancies(token).collect {
                            it.isLoading {
                                _mainState.value = MainScreenStates.Loading
                            }.onSuccess { vacancies ->
                                _mainState.value = MainScreenStates.Content(vacancies, courses)
                            }
                        }
                    }
                }.onFailure {

                }
            }
        }
    }

    fun uploadPhoto(photo: ByteArray) {
        _profileState.value = ProfileScreenStates.Loading
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.uploadPhoto(token, photo).collect { result ->
                result.isLoading {
                    _profileState.value = ProfileScreenStates.Loading
                }.onSuccess {
                    _profileState.value = ProfileScreenStates.UserProfile(user, it.url)
                }.onFailure {
                    _profileState.value = ProfileScreenStates.UserProfile(user, "")
                }
            }
        }
    }
}