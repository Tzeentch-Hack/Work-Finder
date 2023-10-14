package com.tzeentch.workfinder.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("username") val userName: String,
    @SerialName("has_questionary") val hasQuestionary: Boolean
)