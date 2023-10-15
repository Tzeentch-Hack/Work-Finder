package com.tzeentch.workfinder.ui

import com.tzeentch.workfinder.dto.CoursesDto
import com.tzeentch.workfinder.dto.UserDto
import com.tzeentch.workfinder.dto.VacanciesDto

sealed class GreetingStates {

    object Initial : GreetingStates()

    object Loading : GreetingStates()

    data class Form(val error: String) : GreetingStates()

    object Registered : GreetingStates()

    object FillQuestionary : GreetingStates()

    object GoToMainScreen : GreetingStates()
}


sealed class MainScreenStates {

    object Initial : MainScreenStates()

    object Loading : MainScreenStates()

    object Error : MainScreenStates()

    class Content(val vacancies: List<VacanciesDto>, val courses: List<CoursesDto>) :
        MainScreenStates()
}


sealed class ProfileScreenStates {

    object Initial : ProfileScreenStates()

    object Loading : ProfileScreenStates()

    object Error : ProfileScreenStates()

    class UserProfile(val user: UserDto, val url: String = "") : ProfileScreenStates()
}