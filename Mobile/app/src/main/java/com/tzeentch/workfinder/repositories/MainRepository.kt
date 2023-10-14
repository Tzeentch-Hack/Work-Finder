package com.tzeentch.workfinder.repositories

import com.tzeentch.workfinder.Constants
import com.tzeentch.workfinder.dto.BaseDto
import com.tzeentch.workfinder.local.PreferenceManager
import com.tzeentch.workfinder.remote.NetworkResultState
import com.tzeentch.workfinder.remote.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MainRepository constructor(
    private val httpClient: HttpClient,
    private val prefs: PreferenceManager
) {


    suspend fun loginUser(user: String, password: String): Flow<NetworkResultState<BaseDto>> {
        return flowOf(
            safeApiCall {
                httpClient.get(urlString = Constants.LOGIN_USER) {
                    url {
                        parameters.append("user", user)
                        parameters.append("password", password)
                    }
                }.body()
            }
        )
    }

    suspend fun registerNewUser(user: String, password: String): Flow<NetworkResultState<BaseDto>> {
        return flowOf(
            safeApiCall {
                httpClient.get(urlString = Constants.LOGIN_USER) {
                    url {
                        parameters.append("user", user)
                        parameters.append("password", password)
                    }
                }.body()
            }
        )
    }
}