package com.tzeentch.workfinder.ui

import com.tzeentch.workfinder.dto.CoursesDto

sealed class GreetingStates {

    object Initial : GreetingStates()

    object Loading : GreetingStates()

    data class Form(val error: String) : GreetingStates()

    object Registered : GreetingStates()

    object FillQuestionary : GreetingStates()
}


sealed class MainScreenStates {

    object Initial : MainScreenStates()

    object Loading : MainScreenStates()

    object Error : MainScreenStates()

    class Content(val vacancies: List<Int>, val courses: List<CoursesDto>) : MainScreenStates()
}