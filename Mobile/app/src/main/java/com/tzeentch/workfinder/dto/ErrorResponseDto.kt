package com.tzeentch.workfinder.dto


import com.tzeentch.workfinder.remote.ErrorResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(

    @SerialName("cod") val statusCode: Int,

    @SerialName("message") val statusMessage: String
)

fun ErrorResponseDto.toDomain(): ErrorResponse {
    return ErrorResponse(
        success = false, statusCode = this.statusCode, statusMessage = this.statusMessage
    )
}