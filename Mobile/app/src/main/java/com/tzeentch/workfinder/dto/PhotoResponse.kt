package com.tzeentch.workfinder.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoResponse(
    @SerialName("url") val url: String
)
