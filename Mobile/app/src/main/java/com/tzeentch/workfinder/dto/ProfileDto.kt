package com.tzeentch.workfinder.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    @SerialName("email") val email: String,
    @SerialName("phone_number") val phone: String,
    @SerialName("age") val age: String,
    @SerialName("gender") val gender: String,
    @SerialName("education") val education: String,
    @SerialName("residence") val residence: String,
    @SerialName("remote_or_local") val remoteOrLocal: String,
    @SerialName("labor_preference") val laborPreferences: String,
    @SerialName("work_mode") val workMode: String,
    @SerialName("preferred_specialization") val spec: String,
    @SerialName("preferences") val pref: String
)