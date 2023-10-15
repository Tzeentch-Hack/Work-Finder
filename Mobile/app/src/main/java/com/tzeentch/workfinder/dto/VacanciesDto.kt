package com.tzeentch.workfinder.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VacanciesDto(
    @SerialName("url") val url: String?,
    @SerialName("name") val title: String?,
    @SerialName("employment") val empl: String?,
    @SerialName("specialization") val spec: String?
)
