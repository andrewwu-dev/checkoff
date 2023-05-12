package com.andrew.checkoff.core.network

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET(ApiConstants.TODOS_ENDPOINT + "/{id}")
    suspend fun getTaskById(@Path("id") id: Int): TaskItemResponse

    @POST(ApiConstants.TODOS_ENDPOINT)
    suspend fun addTask(@Body task: TaskItemResponse)

    @PATCH(ApiConstants.TODOS_ENDPOINT + "/{id}")
    suspend fun updateTask(@Path("id") id: Int, @Body task: TaskItemResponse)

    @DELETE(ApiConstants.TODOS_ENDPOINT + "/{id}")
    suspend fun deleteTask(@Path("id") id: Int)
}