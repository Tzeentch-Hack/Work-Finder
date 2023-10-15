package com.tzeentch.workfinder.repositories

import android.provider.ContactsContract.CommonDataKinds.Email
import com.tzeentch.workfinder.Constants
import com.tzeentch.workfinder.dto.AuthResultDto
import com.tzeentch.workfinder.dto.CoursesDto
import com.tzeentch.workfinder.dto.PhotoResponse
import com.tzeentch.workfinder.dto.ProfileDto
import com.tzeentch.workfinder.dto.UserDto
import com.tzeentch.workfinder.dto.VacanciesDto
import com.tzeentch.workfinder.remote.NetworkResultState
import com.tzeentch.workfinder.remote.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MainRepository constructor(
    private val httpClient: HttpClient
) {
    suspend fun getUser(
        token: String
    ): Flow<NetworkResultState<UserDto>> {
        return flowOf(
            safeApiCall {
                httpClient.get(urlString = Constants.GET_USER) {
                    header("Authorization", "Bearer $token")
                }.body()
            }
        )
    }

    suspend fun getVacancies(
        token: String
    ): Flow<NetworkResultState<List<VacanciesDto>>> {
        return flowOf(
            safeApiCall {
                httpClient.get(urlString = Constants.GET_VACANCIES) {
                    header("Authorization", "Bearer $token")
                }.body()
            }
        )
    }

    suspend fun sendProfileData(
        profileDto: ProfileDto,
        token: String
    ): Flow<NetworkResultState<Any>> {
        return flowOf(
            safeApiCall {
                httpClient.post(urlString = Constants.SEND_PROFILE) {
                    header("Authorization", "Bearer $token")
                    setBody(
                        profileDto
                    )
                }.body()
            }
        )
    }

    suspend fun getCourses(
        token: String,
        searchQuery: String
    ): Flow<NetworkResultState<List<CoursesDto>>> {
        return flowOf(
            safeApiCall {
                httpClient.get(urlString = Constants.GET_COURSES) {
                    parameter("search_params", searchQuery)
                    header("Authorization", "Bearer $token")
                }.body()
            }
        )
    }

    suspend fun uploadPhoto(
        token: String,
        file: ByteArray
    ): Flow<NetworkResultState<PhotoResponse>> {
        return flowOf(
            safeApiCall {
                httpClient.post(urlString = Constants.GENERATE_CV) {
                    parameter("pdf", false)
                    header("Authorization", "Bearer $token")
                    setBody(MultiPartFormDataContent(
                        formData {
                            append("file", file, Headers.build {
                                append(HttpHeaders.ContentType, "image/jpeg")
                                append(HttpHeaders.ContentDisposition, "filename=image.jpg")
                            })
                        }
                    ))
                }.body()
            }
        )
    }


    suspend fun loginUser(
        user: String,
        password: String
    ): Flow<NetworkResultState<AuthResultDto>> {
        return flowOf(
            safeApiCall {
                httpClient.post(urlString = Constants.LOGIN_USER) {
                    setBody(
                        FormDataContent(Parameters.build {
                            append("username", user)
                            append("password", password)
                        })
                    )
                }.body()
            }
        )
    }

    suspend fun registerUser(
        user: String,
        password: String
    ): Flow<NetworkResultState<AuthResultDto>> {
        return flowOf(
            safeApiCall {
                httpClient.post(urlString = Constants.REGISTER_USER) {
                    setBody(
                        FormDataContent(Parameters.build {
                            append("username", user)
                            append("password", password)
                        })
                    )
                }.body()
            }
        )

    }
}