package com.andrew.checkoff.core.network

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(ApiConstants.TODOS_ENDPOINT + "{id}")
    suspend fun getTaskById(@Path("id") id: Int): TaskItemResponse
}