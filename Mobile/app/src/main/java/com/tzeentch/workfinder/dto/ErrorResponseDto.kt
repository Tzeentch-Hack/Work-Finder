package com.tzeentch.workfinder.dto


import com.tzeentch.workfinder.remote.ErrorResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(
    @SerialName("detail") val message: String
)

fun ErrorResponseDto.toDomain(): ErrorResponse {
    return ErrorResponse(
        statusMessage = this.message
    )
}