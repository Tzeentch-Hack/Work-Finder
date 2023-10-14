package com.tzeentch.workfinder.remote

data class ErrorResponse(
    val statusMessage: String
) : Exception()
