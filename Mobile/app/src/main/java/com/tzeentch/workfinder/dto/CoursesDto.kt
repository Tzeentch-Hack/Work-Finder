package com.tzeentch.workfinder.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoursesDto(
    @SerialName("title") val title: String?,
    @SerialName("preview_image_url") val image: String?,
    @SerialName("href") val url: String?,
    @SerialName("sub_title") val subTitle: String?
)
