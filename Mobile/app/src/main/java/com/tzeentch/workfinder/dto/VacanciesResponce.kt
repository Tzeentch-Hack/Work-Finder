package com.tzeentch.workfinder.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VacanciesResponce(
    @SerialName("vacancies") val vacanciesDto: List<VacanciesDto>
)
