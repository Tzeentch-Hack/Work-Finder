package com.tzeentch.workfinder.remote


import com.tzeentch.workfinder.dto.ErrorResponseDto
import com.tzeentch.workfinder.dto.toDomain
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend fun <T : Any?> safeApiCall(apiCall: suspend () -> T): NetworkResultState<T> {
    return try {
        NetworkResultState.Loading
        val result = apiCall.invoke()
        NetworkResultState.Success(result)
    } catch (e: Exception) {
        NetworkResultState.Failure(e)
    }
}

/**Generate [Exception] from network or system error when making network calls
 *
 * @throws [Exception]
 * */
internal suspend fun parseNetworkError(
    errorResponse: HttpResponse? = null,
    exception: Exception? = null
): Exception {
    throw errorResponse?.body<ErrorResponseDto>()?.toDomain() ?: ErrorResponse(
        success = false,
        statusCode = 0,
        statusMessage = exception?.message ?: "Error"
    )
}
